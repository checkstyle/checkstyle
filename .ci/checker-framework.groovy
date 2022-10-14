import groovy.transform.Field
import groovy.transform.Immutable
import groovy.util.slurpersupport.GPathResult
import groovy.util.slurpersupport.NodeChildren
import groovy.xml.XmlUtil

import java.util.regex.Matcher
import java.util.regex.Pattern

@Field static final String USAGE_STRING = "Usage groovy " +
        ".${File.separator}.ci${File.separator}checker-framework.groovy" +
        " [profile] [--list] [-g | --generate-suppression]\n"

int exitCode = 1
if (args.length == 2) {
    exitCode = parseArgumentAndExecute(args[0], args[1])
}
else if (args.length == 1) {
    exitCode = parseArgumentAndExecute(args[0], null)
}
else {
    throw new IllegalArgumentException(USAGE_STRING)
}
System.exit(exitCode)

/**
 * Parse the command line arguments passed in and execute the branch based on the arguments.
 *
 * @param argument command line argument
 * @return {@code 0} if command executes successfully, {@code 1} otherwise
 */
private int parseArgumentAndExecute(String argument, String flag) {
    final int exitCode
    final Set<String> profiles = getCheckerFrameworkProfiles()
    if (profiles.contains(argument)) {
        if (flag != null && flag != "-g" && flag != "--generate-suppression") {
            final String exceptionMessage = "Unexpected flag: '${flag}'\n" + USAGE_STRING
            throw new IllegalArgumentException(exceptionMessage)
        }
        exitCode = checkCheckerFrameworkReport(argument, flag)
    }
    else if (argument == "--list") {
        println "Supported profiles:"
        profiles.each { println it }
        exitCode = 0
    }
    else {
        final String exceptionMessage = "Unexpected argument: '${argument}'\n" + USAGE_STRING
        throw new IllegalArgumentException(exceptionMessage)
    }
    return exitCode
}

/**
 * Parse the pom.xml file to get all the available checker framework profiles.
 *
 * @return A set of all available checker framework profiles
 */
private static Set<String> getCheckerFrameworkProfiles() {
    final GPathResult mainNode = new XmlSlurper().parse(".${File.separator}pom.xml")
    final NodeChildren ids = mainNode.profiles.profile.id as NodeChildren
    final Set<String> profiles = new HashSet<>()
    ids.each { node ->
        final GPathResult id = node as GPathResult
        final String idText = id.text()
        if (idText.startsWith("checker-framework-")) {
            profiles.add(idText)
        }
    }
    return profiles
}

/**
 * Check the generated checker framework report. Parse the errors and compare them with
 * the suppressed errors.
 *
 * @param profile the checker framework profile to execute
 * @param flag command line argument flag to determine output format
 * @return {@code 0} if checker framework report is as expected, {@code 1} otherwise
 */
private static int checkCheckerFrameworkReport(String profile, String flag) {
    final XmlParser xmlParser = new XmlParser()
    final String suppressedErrorsFileUri =
            ".${File.separator}.ci${File.separator}" +
                    "checker-framework-suppressions${File.separator}${profile}-suppressions.xml"
    final List<String> checkerFrameworkErrors = getCheckerFrameworkErrors(profile)
    Set<CheckerFrameworkError> errors = Collections.emptySet()
    if (!checkerFrameworkErrors.isEmpty()) {
        errors = getErrorFromText(checkerFrameworkErrors)
    }
    final File suppressionFile = new File(suppressedErrorsFileUri)
    Set<CheckerFrameworkError> suppressedErrors = Collections.emptySet()
    if (suppressionFile.exists()) {
        final Node suppressedErrorsNode = xmlParser.parse(suppressedErrorsFileUri)
        suppressedErrors = getSuppressedErrors(suppressedErrorsNode)
    }
    return compareErrors(errors, suppressedErrors, flag)
}

/**
 * Generates the checker framework report and filters out the errors.
 *
 * @param profile the checker framework profile to execute
 * @return A set of errors
 */
private static List<List<String>> getCheckerFrameworkErrors(String profile) {
    final List<String> checkerFrameworkLines = [] as ArrayList
    final String command = "mvn -e --no-transfer-progress clean compile -P${profile}"
    final Process process = getOsSpecificCmd(command).execute()
    process.in.eachLine { line ->
        checkerFrameworkLines.add(line)
        println(line)
    }
    process.waitFor()
    final List<List<String>> checkerFrameworkErrors = [][] as ArrayList
    for (int index = 0; index < checkerFrameworkLines.size(); index++) {
        final String line = checkerFrameworkLines.get(index)
        if (line.startsWith("[WARNING]")) {
            final List<String> error = [] as ArrayList
            error.add(line)
            int currentErrorIndex = index + 1
            while (currentErrorIndex < checkerFrameworkLines.size()) {
                final String currentLine = checkerFrameworkLines.get(currentErrorIndex)
                if (currentLine.startsWith("[ERROR]")
                        || currentLine.startsWith("[INFO]")
                        || currentLine.startsWith("[WARNING]")) {
                    break
                }
                error.add(currentLine)
                currentErrorIndex++
            }
            checkerFrameworkErrors.add(error)
        }
    }
    return checkerFrameworkErrors
}

/**
 * Get OS specific command.
 *
 * @param cmd input command
 * @return OS specific command
 */
private static String getOsSpecificCmd(String cmd) {
    final String osSpecificCmd
    if (System.getProperty("os.name").toLowerCase().contains('windows')) {
        osSpecificCmd = "cmd /c ${cmd}"
    }
    else {
        osSpecificCmd = cmd
    }
    return osSpecificCmd
}

/**
 * Get a set of {@link CheckerFrameworkError} from text.
 *
 * @param errorsText errors in text format
 * @return A set of errors
 */
private static Set<CheckerFrameworkError> getErrorFromText(List<List<String>> errorsList) {
    final Set<CheckerFrameworkError> errors = new HashSet<>()
    final Pattern errorExtractingPattern = Pattern
        .compile(".*[\\\\/](checkstyle[\\\\/]src.*\\.java):\\[(\\d+)[^]]*][^\\[]*\\[([^]]*)](.*)")
    final Pattern filePathExtractingPattern = Pattern.compile("\\[WARNING] (.*\\.java)")
    final int fileNameGroup = 1
    final int lineNumberGroup = 2
    final int specifierGroup = 3
    final int messageGroup = 4
    errorsList.each { errorList ->
        final String error = errorList.get(0)
        final Matcher matcher = errorExtractingPattern.matcher(error)
        final List<String> details = [] as ArrayList
        String fileName = null
        String specifier = null
        String message = null
        String lineContent = null
        int lineNumber = 0
        if (matcher.matches()) {
            fileName = matcher.group(fileNameGroup)
            lineNumber = Integer.parseInt(matcher.group(lineNumberGroup))
            specifier = XmlUtil.escapeXml(matcher.group(specifierGroup).trim())
            message = XmlUtil.escapeXml(matcher.group(messageGroup).trim())

            final Matcher filePathMatcher = filePathExtractingPattern.matcher(error)
            if (filePathMatcher.find()) {
                final String absoluteFilePath = filePathMatcher.group(1)
                final File file = new File(absoluteFilePath)
                lineContent = XmlUtil.escapeXml(file.readLines().get(lineNumber - 1).trim())
            }

            if (errorList.size() > 1) {
                for (int index = 1; index < errorList.size(); index++) {
                    final String errorDetail = XmlUtil.escapeXml(errorList.get(index).trim())
                    if (!errorDetail.isEmpty()) {
                        details.add(errorDetail)
                    }
                }
            }

            // Errors extracted from Checker Framework Report are by default considered stable.
            final boolean isUnstable = false
            final CheckerFrameworkError checkerFrameworkError = new CheckerFrameworkError(
                    fileName, specifier, message, details, lineContent, lineNumber, isUnstable)
            errors.add(checkerFrameworkError)
        }
    }
    return errors
}

/**
 * Get the suppressed error. All child nodes of the main {@code suppressedErrors} node
 * are parsed.
 *
 * @param mainNode the main {@code suppressedErrors} node
 * @return A set of suppressed errors
 */
private static Set<CheckerFrameworkError> getSuppressedErrors(Node mainNode) {
    final List<Node> children = mainNode.children()
    final Set<CheckerFrameworkError> suppressedErrors = new HashSet<>()

    children.each { node ->
        final Node errorNode = node as Node
        suppressedErrors.add(getError(errorNode))
    }
    return suppressedErrors
}

/**
 * Construct the {@link CheckerFrameworkError} object from the {@code checkerFrameworkError}
 * XML node. The suppression file is parsed to get the {@code checkerFrameworkError} node.
 *
 * @param errorNode the {@code error} XML node
 * @return {@link CheckerFrameworkError} object represented by the {@code error} XML node
 */
private static CheckerFrameworkError getError(Node errorNode) {
    final List childNodes = errorNode.children()

    final List<String> details = [] as ArrayList
    String fileName = null
    String specifier = null
    String message = null
    String lineContent = null
    final int lineNumber = -1
    childNodes.each {
        final Node childNode = it as Node
        final String text = childNode.name()

        final String childNodeText = XmlUtil.escapeXml(childNode.text())
        switch (text) {
            case "fileName":
                fileName = childNodeText
                break
            case "specifier":
                specifier = childNodeText
                break
            case "message":
                message = childNodeText
                break
            case "lineContent":
                lineContent = childNodeText
                break
            case "details":
                final String[] detailsArray = childNodeText.split("\\n")
                detailsArray.each { detail ->
                    final String detailString = detail.trim()
                    if (!detailString.isEmpty()) {
                        details.add(detailString)
                    }
                }
        }
    }

    final String unstableAttributeValue = errorNode.attribute("unstable")
    final boolean isUnstable = Boolean.parseBoolean(unstableAttributeValue)

    return new CheckerFrameworkError(fileName, specifier, message, details, lineContent,
            lineNumber, isUnstable)
}

/**
 * Compare the actual and the suppressed errors. The comparison passes successfully
 * (i.e. returns 0) when:
 * <ol>
 *     <li>Surviving and suppressed errors are equal.</li>
 *     <li>There are extra suppressed errors but they are unstable
 *       i.e. {@code unstable="true"}.</li>
 * </ol>
 * The comparison fails when (i.e. returns 1) when:
 * <ol>
 *     <li>Surviving errors are not present in the suppressed list.</li>
 *     <li>There are errors in the suppression list that are not there is surviving list.</li>
 * </ol>
 *
 * @param actualErrors A set of actual errors reported by error prone
 * @param suppressedErrors A set of suppressed errors from suppression file
 * @param flag command line argument flag to determine output format
 * @return {@code 0} if comparison passes successfully
 */
private static int compareErrors(Set<CheckerFrameworkError> actualErrors,
                                 Set<CheckerFrameworkError> suppressedErrors,
                                 String flag) {
    final Set<CheckerFrameworkError> unsuppressedErrors =
            setDifference(actualErrors, suppressedErrors)
    final Set<CheckerFrameworkError> extraSuppressions =
            setDifference(suppressedErrors, actualErrors)

    final int exitCode
    if (actualErrors == suppressedErrors) {
        exitCode = 0
    }
    else if (unsuppressedErrors.isEmpty()
            && !hasOnlyStableErrors(extraSuppressions)) {
        exitCode = 0
    }
    else {
        if (!unsuppressedErrors.isEmpty()) {
            println "New surviving error(s) found:"
            unsuppressedErrors.each {
                printError(flag, it)
            }
        }
        if (!extraSuppressions.isEmpty()
                && extraSuppressions.any { !it.isUnstable() }) {
            println "\nUnnecessary suppressed error(s) found and should be removed:"
            extraSuppressions.each {
                if (!it.isUnstable()) {
                    printError(flag, it)
                }
            }
        }
        exitCode = 1
    }

    if (exitCode == 0) {
        println "Build successful with no errors."
    }

    return exitCode
}

/**
 * Whether a set has only stable errors.
 *
 * @param errors A set of errors
 * @return {@code true} if a set has only stable errors
 */
private static boolean hasOnlyStableErrors(Set<CheckerFrameworkError> errors) {
    return errors.every { !it.isUnstable() }
}

/**
 * Prints the error according to the nature of the flag.
 *
 * @param flag command line argument flag to determine output format
 * @param error error to print
 */
private static void printError(String flag, CheckerFrameworkError error) {
    if (flag != null) {
        println error.toXmlString()
    }
    else {
        println error
    }
}

/**
 * Determine the difference between 2 sets. The result is {@code setOne - setTwo}.
 *
 * @param setOne The first set in the difference
 * @param setTwo The second set in the difference
 * @return {@code setOne - setTwo}
 */
private static Set<CheckerFrameworkError> setDifference(final Set<CheckerFrameworkError> setOne,
                                                        final Set<CheckerFrameworkError> setTwo) {
    final Set<CheckerFrameworkError> result = new TreeSet<>(setOne)
    result.removeIf { error -> setTwo.contains(error) }
    return result
}

/**
 * A class to represent the XML {@code checkerFrameworkError} node.
 */
@Immutable
class CheckerFrameworkError implements Comparable<CheckerFrameworkError> {

    /**
     * Error nodes present in suppressions file do not have a {@code lineNumber}.
     * The {@code lineNumber} is set to {@code -1} for such errors.
     */
    private static final int LINE_NUMBER_NOT_PRESENT_VALUE = -1

    String fileName
    String specifier
    String message
    List<String> details
    String lineContent
    int lineNumber

    /**
     * Whether the error is unstable. Unstable errors in suppression list are not flagged as
     * unnecessary suppressions.
     *
     * <p>An error is considered to be unstable when error message changes with each run.
     * Some error messages contains strings like {@code temp-var-1234}, the numerical part changes
     * with each run so the error is considered unstable. In such cases numerical values in
     * {@code details} and {@code message} are replaced with empty string while comparing and
     * hashing errors.
     */
    boolean unstable

    @Override
    String toString() {
        String toString = """
            File Name: "${getFileName()}"
            Specifier: "${getSpecifier()}"
            Message: "${getMessage()}"
            Line Contents: "${getLineContent()}\"""".stripIndent()
        if (getLineNumber() != LINE_NUMBER_NOT_PRESENT_VALUE) {
            toString += '\nLine Number: ' + getLineNumber()
        }

        final List<String> details = getDetails()
        if (!details.isEmpty()) {
            toString += '\nDetails: ' + details.get(0)
            if (details.size() > 1) {
                for (int index = 1; index < details.size(); index++) {
                    toString += '\n' + ' ' * 9 + details.get(index)
                }
            }
        }
        return toString
    }

    @Override
    int compareTo(CheckerFrameworkError other) {
        int i = getFileName() <=> other.getFileName()
        if (i != 0) {
            return i
        }

        i = getSpecifier() <=> other.getSpecifier()
        if (i != 0) {
            return i
        }

        if (this.isUnstable() || other.isUnstable()) {
            final String messageWithoutLineNumber = getMessage().replaceAll('\\d+', '')
            final String thatMessageWithoutLineNumber = other.getMessage().replaceAll('\\d+', '')
            i = messageWithoutLineNumber <=> thatMessageWithoutLineNumber
            if (i != 0) {
                return i
            }

            final List<String> detailsWithoutLineNumber = this.getDetails()*.replaceAll('\\d+', '')
            final List<String> thatDetailsWithoutLineNumber =
                    other.getDetails()*.replaceAll('\\d+', '')

            i = detailsWithoutLineNumber.join('') <=> thatDetailsWithoutLineNumber.join('')
            if (i != 0) {
                return i
            }
        }
        else {
            i = getMessage() <=> other.getMessage()
            if (i != 0) {
                return i
            }

            i = getDetails().join('') <=> other.getDetails().join('')
            if (i != 0) {
                return i
            }
        }

        return getLineContent() <=> other.getLineContent()
    }

    @Override
    boolean equals(Object object) {
        if (this.is(object)) {
            return true
        }
        if (!(object instanceof CheckerFrameworkError)) {
            return false
        }

        CheckerFrameworkError that = (CheckerFrameworkError) object

        return (this <=> that) == 0
    }

    @Override
    int hashCode() {
        int result
        if (unstable) {
            result = (message != null ? message.replaceAll('\\d+', '').hashCode() : 0)
            result = 31 * result + (details != null ? details*.replaceAll(
                    '\\d+', '').hashCode() : 0)
        }
        else {
            result = (message != null ? message.hashCode() : 0)
            result = 31 * result + (details != null ? details.hashCode() : 0)
        }
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0)
        result = 31 * result + (specifier != null ? specifier.hashCode() : 0)
        result = 31 * result + (lineContent != null ? lineContent.hashCode() : 0)
        return result
    }

    /**
     * XML format of the checker framework error.
     *
     * @return XML format of the checker framework error
     */
    String toXmlString() {
        final List<String> details = getDetails()
        String toXmlString = """
            <checkerFrameworkError unstable="${isUnstable()}">
              <fileName>${getFileName()}</fileName>
              <specifier>${getSpecifier()}</specifier>
              <message>${getMessage()}</message>
              <lineContent>${getLineContent()}</lineContent>
            """.stripIndent(10)
        if (!details.isEmpty()) {
            toXmlString += "    <details>"
            getDetails().each {
                toXmlString += '\n' + ' ' * 6 + it
            }
            toXmlString += "\n    </details>\n"
        }
        toXmlString += "  </checkerFrameworkError>"
        return toXmlString
    }

}
