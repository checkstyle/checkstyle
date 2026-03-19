import groovy.transform.EqualsAndHashCode
import groovy.transform.Field
import groovy.transform.Immutable
import groovy.xml.XmlParser
import groovy.xml.XmlSlurper
import groovy.xml.XmlUtil
import groovy.xml.slurpersupport.GPathResult

import java.util.regex.Matcher
import java.util.regex.Pattern

@Field static final String USAGE_STRING = 'Usage groovy ' +
        ".${File.separator}.ci${File.separator}checker-framework.groovy" +
        ' [profile] [--list]\n'

int exitCode = 1
if (args.length == 1) {
    exitCode = parseArgumentAndExecute(args[0])
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
private int parseArgumentAndExecute(final String argument) {
    final int exitCode
    final Set<String> profiles = getCheckerFrameworkProfiles()
    if (profiles.contains(argument)) {
        exitCode = checkCheckerFrameworkReport(argument)
    }
    else if (argument == '--list') {
        println 'Supported profiles:'
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
    final GPathResult ids = mainNode.profiles.profile.id
    final Set<String> profiles = new HashSet<>()
    ids.each { final node ->
        final GPathResult id = node
        final String idText = id.text()
        if (idText.startsWith('checker-')) {
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
 * @return {@code 0} if checker framework report is as expected, {@code 1} otherwise
 */
private static int checkCheckerFrameworkReport(final String profile) {
    final XmlParser xmlParser = new XmlParser()
    final String suppressedErrorsFileUri =
            ".${File.separator}config${File.separator}" +
                    "checker-framework-suppressions${File.separator}${profile}-suppressions.xml"
    final List<List<String>> checkerFrameworkErrors = getCheckerFrameworkErrors(profile)
    List<CheckerFrameworkError> errors = Collections.emptyList()
    if (!checkerFrameworkErrors.isEmpty()) {
        errors = getErrorFromText(checkerFrameworkErrors)
    }
    final File suppressionFile = new File(suppressedErrorsFileUri)
    List<CheckerFrameworkError> suppressedErrors = Collections.emptyList()
    if (suppressionFile.exists()) {
        final groovy.util.Node suppressedErrorsNode = xmlParser.parse(suppressedErrorsFileUri)
        suppressedErrors = getSuppressedErrors(suppressedErrorsNode)
    }

    String newSuppression = '<?xml version="1.0" encoding="UTF-8"?>\n<suppressedErrors>'
    errors.each {
        newSuppression += it.toXmlString() + '\n'
    }
    if (errors.isEmpty()) {
        newSuppression += '\n'
    }
    newSuppression += '</suppressedErrors>\n'
    final FileWriter writer = new FileWriter(suppressionFile)
    writer.write(newSuppression)
    writer.flush()
    writer.close()

    return compareErrors(errors, suppressedErrors)
}

/**
 * Generates the checker framework report and filters out the errors.
 *
 * @param profile the checker framework profile to execute
 * @return A set of errors
 */
private static List<List<String>> getCheckerFrameworkErrors(final String profile) {
    final List<String> checkerFrameworkLines = new ArrayList<>()
    final String command = "mvn -e --no-transfer-progress clean compile" +
        " -P${profile},no-validations"
    println("Execution of Checker by command:")
    println(command)
    final ProcessBuilder processBuilder = new ProcessBuilder(getOsSpecificCmd(command).split(' '))
    processBuilder.redirectErrorStream(true)
    final Process process = processBuilder.start()

    BufferedReader reader = null
    try {
        reader = new BufferedReader(new InputStreamReader(process.inputStream))
        String lineFromReader = reader.readLine()
        while (lineFromReader != null) {
            println(lineFromReader)
            checkerFrameworkLines.add(lineFromReader)
            lineFromReader = reader.readLine()
        }
        int exitCode = process.waitFor()
        if (exitCode != 0) {
            throw new IllegalStateException("Maven process exited with error code: " + exitCode)
        }
    } finally {
        reader.close()
    }

    final List<List<String>> checkerFrameworkErrors = new ArrayList<>()
    for (int index = 0; index < checkerFrameworkLines.size(); index++) {
        final String line = checkerFrameworkLines.get(index)
        if (line.startsWith('[WARNING]')) {
            final List<String> error = new ArrayList<>()
            error.add(line)
            int currentErrorIndex = index + 1
            while (currentErrorIndex < checkerFrameworkLines.size()) {
                final String currentLine = checkerFrameworkLines.get(currentErrorIndex)
                if (currentLine.startsWith('[ERROR]')
                        || currentLine.startsWith('[INFO]')
                        || currentLine.startsWith('[WARNING]')) {
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
private static String getOsSpecificCmd(final String cmd) {
    final String osSpecificCmd
    if (System.getProperty('os.name').toLowerCase().contains('windows')) {
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
private static List<CheckerFrameworkError> getErrorFromText(final List<List<String>> errorsList) {
    final List<CheckerFrameworkError> errors = new ArrayList<>()
    final Pattern errorExtractingPattern = Pattern
        .compile('.*[\\\\/](src[\\\\/].*\\.java):\\[(\\d+)[^]]*][^\\[]*\\[([^]]*)](.*)')
    final Pattern filePathExtractingPattern = Pattern.compile('\\[WARNING] (.*\\.java)')
    final int fileNameGroup = 1
    final int lineNumberGroup = 2
    final int specifierGroup = 3
    final int messageGroup = 4
    errorsList.each { final errorList ->
        final String error = errorList.get(0)
        final Matcher matcher = errorExtractingPattern.matcher(error)
        final List<String> details = new ArrayList<>()
        String fileName = null
        String specifier = null
        String message = null
        String lineContent = null
        int lineNumber = 0
        if (matcher.matches()) {
            fileName = matcher.group(fileNameGroup).replace('\\', '/')
            lineNumber = Integer.parseInt(matcher.group(lineNumberGroup))
            specifier = XmlUtil.escapeXml(matcher.group(specifierGroup).trim())
            message = XmlUtil.escapeXml(matcher.group(messageGroup).trim())
                    .replaceAll('temp-var-\\d+', 'temp-var')

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
                        details.add(errorDetail.replaceAll('capture#\\d+', 'capture'))
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
    return errors.sort()
}

/**
 * Get the suppressed error. All child nodes of the main {@code suppressedErrors} node
 * are parsed.
 *
 * @param mainNode the main {@code suppressedErrors} node
 * @return A set of suppressed errors
 */
private static List<CheckerFrameworkError> getSuppressedErrors(groovy.util.Node mainNode) {
    final List<Node> children = mainNode.children()
    final List<CheckerFrameworkError> suppressedErrors = new ArrayList<>(children.size())

    children.each { final node ->
        final groovy.util.Node errorNode = node
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
private static CheckerFrameworkError getError(groovy.util.Node errorNode) {
    final List<Node> childNodes = errorNode.children()

    final List<String> details = new ArrayList<>()
    String fileName = null
    String specifier = null
    String message = null
    String lineContent = null
    final int lineNumber = -1
    childNodes.each {
        final groovy.util.Node childNode = it
        final String text = childNode.name()

        final String childNodeText = XmlUtil.escapeXml(childNode.text())
        switch (text) {
            case 'fileName':
                fileName = childNodeText
                break
            case 'specifier':
                specifier = childNodeText
                break
            case 'message':
                message = childNodeText
                break
            case 'lineContent':
                lineContent = childNodeText
                break
            case 'details':
                final String[] detailsArray = childNodeText.split('\\n')
                detailsArray.each { final detail ->
                    final String detailString = detail.trim()
                    if (!detailString.isEmpty()) {
                        details.add(detailString)
                    }
                }
        }
    }

    final String unstableAttributeValue = errorNode.attribute('unstable')
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
 * @return {@code 0} if comparison passes successfully
 */
private static int compareErrors(final List<CheckerFrameworkError> actualErrors,
                                 final List<CheckerFrameworkError> suppressedErrors) {
    final List<CheckerFrameworkError> unsuppressedErrors =
            setDifference(actualErrors, suppressedErrors)
    final List<CheckerFrameworkError> extraSuppressions =
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
            println 'New surviving error(s) found:'
            unsuppressedErrors.each {
                printError(it)
            }
        }
        if (!extraSuppressions.isEmpty()
                && extraSuppressions.any { !it.isUnstable() }) {
            println '\nUnnecessary suppressed error(s) found and should be removed:'
            extraSuppressions.each {
                if (!it.isUnstable()) {
                    printError(it)
                }
            }
        }
        exitCode = 1
    }

    if (exitCode == 0) {
        println 'Build successful with no errors.'
    }

    return exitCode
}

/**
 * Whether a set has only stable errors.
 *
 * @param errors A set of errors
 * @return {@code true} if a set has only stable errors
 */
private static boolean hasOnlyStableErrors(final List<CheckerFrameworkError> errors) {
    return errors.every { !it.isUnstable() }
}

/**
 * Prints the error.
 *
 * @param error error to print
 */
private static void printError(final CheckerFrameworkError error) {
    println error.toXmlString()
}

/**
 * Determine the difference between 2 sets. The result is {@code setOne - setTwo}.
 *
 * @param setOne The first set in the difference
 * @param setTwo The second set in the difference
 * @return {@code setOne - setTwo}
 */
private static List<CheckerFrameworkError> setDifference(final List<CheckerFrameworkError> setOne,
                                                         final List<CheckerFrameworkError> setTwo) {
    final List<CheckerFrameworkError> result = new ArrayList<>(setOne)
    result.removeIf { final error -> setTwo.contains(error) }
    return result
}

/**
 * A class to represent the XML {@code checkerFrameworkError} node.
 */
@EqualsAndHashCode(excludes = ['lineNumber', 'unstable'])
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
     */
    boolean unstable

    @Override
    String toString() {
        String toString = """
            File Name: "${fileName}"
            Specifier: "${specifier}"
            Message: "${message}"
            Line Contents: "${lineContent}\"""".stripIndent()
        if (lineNumber != LINE_NUMBER_NOT_PRESENT_VALUE) {
            toString += '\nLine Number: ' + lineNumber
        }

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
        int i = fileName <=> other.fileName
        if (i != 0) {
            return i
        }

        i = specifier <=> other.specifier
        if (i != 0) {
            return i
        }

        i = message <=> other.message
        if (i != 0) {
            return i
        }

        i = details.join('') <=> other.details.join('')
        if (i != 0) {
            return i

        }

        return lineContent <=> other.lineContent
    }

    /**
     * XML format of the checker framework error.
     *
     * @return XML format of the checker framework error
     */
    String toXmlString() {
        String toXmlString = """
            <checkerFrameworkError unstable="${unstable}">
              <fileName>${fileName}</fileName>
              <specifier>${specifier}</specifier>
              <message>${message}</message>
              <lineContent>${lineContent}</lineContent>
            """.stripIndent(10)
        if (!details.isEmpty()) {
            toXmlString += '    <details>'
            details.each {
                toXmlString += '\n' + ' ' * 6 + it
            }
            toXmlString += '\n    </details>\n'
        }
        toXmlString += '  </checkerFrameworkError>'
        return toXmlString
    }

}
