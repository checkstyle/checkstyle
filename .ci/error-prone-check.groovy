import groovy.transform.EqualsAndHashCode
import groovy.transform.Field
import groovy.transform.Immutable
import groovy.xml.XmlParser
import groovy.xml.XmlUtil

import java.util.regex.Matcher
import java.util.regex.Pattern

@Field static final String USAGE_STRING = "Usage groovy " +
        ".${File.separator}.ci${File.separator}error-prone-check.groovy" +
        " (compile | test-compile) [-g | --generate-suppression]\n"

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
 * compile - compile the source code of the project.
 * test-compile - compile the test source code into the test destination directory.
 *
 * @param argument command line argument
 * @return {@code 0} if command executes successfully, {@code 1} otherwise
 */
private int parseArgumentAndExecute(String argument, String flag) {
    final int exitCode
    if (argument == "compile" || argument == "test-compile") {
        if (flag != null && flag != "-g" && flag != "--generate-suppression") {
            final String exceptionMessage = "Unexpected flag: '${flag}'\n" + USAGE_STRING
            throw new IllegalArgumentException(exceptionMessage)
        }
        exitCode = checkErrorProneReport(argument, flag)
    }
    else {
        final String exceptionMessage = "Unexpected argument: '${argument}'\n" + USAGE_STRING
        throw new IllegalArgumentException(exceptionMessage)
    }
    return exitCode
}

/**
 * Check the generated error prone report. Parse the errors and compare them with the suppressed
 * errors.
 *
 * @param profile the error prone profile to execute
 * @param flag command line argument flag to determine output format
 * @return {@code 0} if error prone report is as expected, {@code 1} otherwise
 */
private static int checkErrorProneReport(String profile, String flag) {
    final XmlParser xmlParser = new XmlParser()
    final String suppressedErrorsFileUri =
            ".${File.separator}config${File.separator}" +
                    "error-prone-suppressions${File.separator}${profile}-phase-suppressions.xml"
    final List<String> errorProneErrors = getErrorProneErrors(profile)
    Set<ErrorProneError> errors = Collections.emptySet()
    if (!errorProneErrors.isEmpty()) {
        errors = getErrorFromText(errorProneErrors)
    }
    final File suppressionFile = new File(suppressedErrorsFileUri)
    Set<ErrorProneError> suppressedErrors = Collections.emptySet()
    if (suppressionFile.exists()) {
        final groovy.util.Node suppressedErrorsNode = xmlParser.parse(suppressedErrorsFileUri)
        suppressedErrors = getSuppressedErrors(suppressedErrorsNode)
    }
    return compareErrors(errors, suppressedErrors, flag)
}

/**
 * Generates the error prone report and filters out the errors.
 *
 * @param profile the error prone profile to execute
 * @return A set of errors
 */
private static List<String> getErrorProneErrors(String profile) {
    final List<String> errorProneErrors = [] as ArrayList
    final String command = "mvn -e --no-transfer-progress clean" +
            " ${profile} -Perror-prone-${profile}"
    println "Execution of error-prone by command:"
    println command
    final Process process = getOsSpecificCmd(command).execute()
    process.in.eachLine { line ->
        if (line.startsWith("[ERROR]")) {
            errorProneErrors.add(line)
        }
        println(line)
    }
    process.waitFor()
    return errorProneErrors
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
 * Get a set of {@link ErrorProneError} from text.
 *
 * @param errorsText errors in text format
 * @return A set of errors
 */
private static Set<ErrorProneError> getErrorFromText(List<String> errorsText) {
    final Set<ErrorProneError> errors = new HashSet<>()
    final Pattern errorExtractingPattern = Pattern
            .compile(".*[\\\\/](.*\\.java):\\[(\\d+).*\\[(\\w+)](.*)")
    final Pattern filePathExtractingPattern = Pattern.compile("\\[ERROR] (.*\\.java)")
    final int sourceFileGroup = 1
    final int lineNumberGroup = 2
    final int bugPatternGroup = 3
    final int descriptionGroup = 4
    errorsText.each { error ->
        final Matcher matcher = errorExtractingPattern.matcher(error)
        String sourceFile = null
        String bugPattern = null
        String description = null
        String lineContent = null
        int lineNumber = 0
        if (matcher.matches()) {
            sourceFile = matcher.group(sourceFileGroup)
            lineNumber = Integer.parseInt(matcher.group(lineNumberGroup))
            bugPattern = matcher.group(bugPatternGroup).trim()
            description = XmlUtil.escapeXml(matcher.group(descriptionGroup).trim())

            final Matcher filePathMatcher = filePathExtractingPattern.matcher(error)
            if (filePathMatcher.find()) {
                final String absoluteFilePath = filePathMatcher.group(1)
                final File file = new File(absoluteFilePath)
                lineContent = XmlUtil.escapeXml(file.readLines().get(lineNumber - 1).trim())
            }

            final ErrorProneError errorProneError = new ErrorProneError(
                    sourceFile, bugPattern, description, lineContent, lineNumber)
            errors.add(errorProneError)
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
private static Set<ErrorProneError> getSuppressedErrors(Node mainNode) {
    final List<Node> children = mainNode.children()
    final Set<ErrorProneError> suppressedErrors = new HashSet<>()

    children.each { node ->
        final Node errorNode = node
        suppressedErrors.add(getError(errorNode))
    }
    return suppressedErrors
}

/**
 * Construct the {@link ErrorProneError} object from the {@code error} XML node.
 * The suppression file is parsed to get the {@code errorNode}.
 *
 * @param errorNode the {@code error} XML node
 * @return {@link ErrorProneError} object represented by the {@code error} XML node
 */
private static ErrorProneError getError(Node errorNode) {
    final List<Node> childNodes = errorNode.children()

    String sourceFile = null
    String bugPattern = null
    String description = null
    String lineContent = null
    final int lineNumber = -1
    childNodes.each {
        final Node childNode = it
        final String text = childNode.name()

        final String childNodeText = XmlUtil.escapeXml(childNode.text())
        switch (text) {
            case "sourceFile":
                sourceFile = childNodeText
                break
            case "bugPattern":
                bugPattern = childNodeText
                break
            case "description":
                description = childNodeText
                break
            case "lineContent":
                lineContent = childNodeText
                break
        }
    }

    return new ErrorProneError(sourceFile, bugPattern, description, lineContent, lineNumber)
}

/**
 * Compare the actual and the suppressed errors. The comparison passes successfully
 * (i.e. returns 0) when:
 * <ol>
 *     <li>Surviving and suppressed errors are equal.</li>
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
private static int compareErrors(Set<ErrorProneError> actualErrors,
                                    Set<ErrorProneError> suppressedErrors,
                                    String flag) {
    final Set<Error> unsuppressedErrors =
            setDifference(actualErrors, suppressedErrors)
    final Set<Error> extraSuppressions =
            setDifference(suppressedErrors, actualErrors)

    final int exitCode
    if (actualErrors == suppressedErrors) {
        exitCode = 0
    }
    else {
        if (!unsuppressedErrors.isEmpty()) {
            println "New surviving error(s) found:"
            unsuppressedErrors.each {
                printError(flag, it)
            }
        }
        if (!extraSuppressions.isEmpty()) {
            println "\nUnnecessary suppressed error(s) found and should be removed:"
            extraSuppressions.each {
                printError(flag, it)
            }
        }
        exitCode = 1
    }
    return exitCode
}

/**
 * Prints the error according to the nature of the flag.
 *
 * @param flag command line argument flag to determine output format
 * @param error error to print
 */
private static void printError(String flag, ErrorProneError error) {
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
private static Set<Error> setDifference(final Set<ErrorProneError> setOne,
                                           final Set<ErrorProneError> setTwo) {
    final Set<Error> result = new TreeSet<>(setOne)
    result.removeIf { error -> setTwo.contains(error) }
    return result
}

/**
 * A class to represent the XML {@code error} node.
 */
@EqualsAndHashCode(excludes = "lineNumber")
@Immutable
class ErrorProneError implements Comparable<ErrorProneError> {

    /**
     * Error nodes present in suppressions file do not have a {@code lineNumber}.
     * The {@code lineNumber} is set to {@code -1} for such errors.
     */
    private static final int LINE_NUMBER_NOT_PRESENT_VALUE = -1

    String sourceFile
    String bugPattern
    String description
    String lineContent
    int lineNumber

    @Override
    String toString() {
        String toString = """
            Source File: "${getSourceFile()}"
            Bug Pattern: "${getBugPattern()}"
            Description: "${getDescription()}"
            Line Contents: "${getLineContent()}"
            """.stripIndent()
        if (getLineNumber() != LINE_NUMBER_NOT_PRESENT_VALUE) {
            toString += 'Line Number: ' + getLineNumber()
        }
        return toString
    }

    @Override
    int compareTo(ErrorProneError other) {
        int i = getSourceFile() <=> other.getSourceFile()
        if (i != 0) {
            return i
        }

        i = getBugPattern() <=> other.getBugPattern()
        if (i != 0) {
            return i
        }

        i = getLineContent() <=> other.getLineContent()
        if (i != 0) {
            return i
        }

        return getDescription() <=> other.getDescription()
    }

    /**
     * XML format of the error.
     *
     * @return XML format of the error
     */
    String toXmlString() {
        return """
            <error>
              <sourceFile>${getSourceFile()}</sourceFile>
              <bugPattern>${getBugPattern()}</bugPattern>
              <description>${getDescription()}</description>
              <lineContent>${getLineContent()}</lineContent>
            </error>
            """.stripIndent(10)
    }

}

