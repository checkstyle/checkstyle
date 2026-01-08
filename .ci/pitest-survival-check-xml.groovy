import groovy.io.FileType
import groovy.transform.EqualsAndHashCode
import groovy.transform.Field
import groovy.transform.Immutable
import groovy.xml.XmlParser
import groovy.xml.XmlSlurper
import groovy.xml.XmlUtil
import groovy.xml.slurpersupport.GPathResult

@Field static final String USAGE_STRING = "Usage groovy .${File.separator}.ci${File.separator}" +
        "pitest-survival-check-xml.groovy [profile]\n" +
        "To see the full list of supported profiles run\ngroovy .${File.separator}" +
        ".ci${File.separator} pitest-survival-check-xml.groovy --list\n"

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
private int parseArgumentAndExecute(String argument) {
    final Set<String> profiles = getPitestProfiles()
    final int exitCode
    if (profiles.contains(argument)) {
        exitCode = checkPitestReport(argument)
    }
    else if (argument == "--list") {
        println "Supported profiles:"
        profiles.each { println it }
        exitCode = 0
    }
    else {
        final String exceptionMessage = "\nUnexpected argument: '${argument}' " + USAGE_STRING
        throw new IllegalArgumentException(exceptionMessage)
    }
    return exitCode
}

/**
 * Parse the pom.xml file to get all the available pitest profiles.
 *
 * @return A set of all available pitest profiles
 */
private static Set<String> getPitestProfiles() {
    final GPathResult mainNode = new XmlSlurper().parse(".${File.separator}pom.xml")
    final GPathResult ids = mainNode.profiles.profile.id
    final Set<String> profiles = new HashSet<>()
    ids.each { node ->
        final GPathResult id = node
        final String idText = id.text()
        if (idText.startsWith("pitest-")) {
            profiles.add(idText)
        }
    }
    return profiles
}

/**
 * Check the generated pitest report. Parse the surviving and suppressed mutations and compare
 * them.
 *
 * @param profile the pitest profile to execute
 * @return {@code 0} if pitest report is as expected, {@code 1} otherwise
 */
private static int checkPitestReport(String profile) {
    final XmlParser xmlParser = new XmlParser()
    File mutationReportFile = null
    final String suppressedMutationFileUri = ".${File.separator}config${File.separator}" +
            "pitest-suppressions${File.separator}${profile}-suppressions.xml"

    final File pitReports =
            new File(".${File.separator}target${File.separator}pit-reports")

    if (!pitReports.exists()) {
        throw new IllegalStateException(
                "Pitest report directory does not exist, generate pitest report first")
    }

    pitReports.eachFileRecurse(FileType.FILES) {
        if (it.name == 'mutations.xml') {
            mutationReportFile = it
        }
    }
    final Node mutationReportNode = xmlParser.parse(mutationReportFile)
    final Set<Mutation> survivingMutations = getSurvivingMutations(mutationReportNode)

    final File suppressionFile = new File(suppressedMutationFileUri)
    Set<Mutation> suppressedMutations = new TreeSet<>()
    if (suppressionFile.exists()) {
        final Node suppressedMutationNode = xmlParser.parse(suppressedMutationFileUri)
        suppressedMutations = getSuppressedMutations(suppressedMutationNode)
    }

    if (survivingMutations.isEmpty()) {
        if (suppressionFile.exists()) {
            suppressionFile.delete()
        }
    }
    else {
        final StringBuilder suppressionFileContent = new StringBuilder(1024)
        suppressionFileContent.append(
                '<?xml version="1.0" encoding="UTF-8"?>\n<suppressedMutations>')

        survivingMutations.each {
            suppressionFileContent.append(it.toXmlString())
        }
        suppressionFileContent.append('</suppressedMutations>\n')

        if (!suppressionFile.exists()) {
            suppressionFile.createNewFile()
        }
        suppressionFile.write(suppressionFileContent.toString())
    }

    return printComparisonToConsole(survivingMutations, suppressedMutations)
}

/**
 * Get the surviving mutations. All child nodes of the main {@code mutations} node
 * are parsed.
 *
 * @param mainNode the main {@code mutations} node
 * @return A set of surviving mutations
 */
private static Set<Mutation> getSurvivingMutations(Node mainNode) {

    final List<Node> children = mainNode.children()
    final Set<Mutation> survivingMutations = new TreeSet<>()

    children.each { node ->
        final Node mutationNode = node

        final String mutationStatus = mutationNode.attribute("status")

        if (mutationStatus == "SURVIVED" || mutationStatus == "NO_COVERAGE") {
            survivingMutations.add(getMutation(mutationNode))
        }
    }
    return survivingMutations
}

/**
 * Get the suppressed mutations. All child nodes of the main {@code suppressedMutations} node
 * are parsed.
 *
 * @param mainNode the main {@code suppressedMutations} node
 * @return A set of suppressed mutations
 */
private static Set<Mutation> getSuppressedMutations(Node mainNode) {
    final List<Node> children = mainNode.children()
    final Set<Mutation> suppressedMutations = new TreeSet<>()

    children.each { node ->
        final Node mutationNode = node
        suppressedMutations.add(getMutation(mutationNode))
    }
    return suppressedMutations
}

/**
 * Construct the {@link Mutation} object from the {@code mutation} XML node.
 * The {@code mutations.xml} file is parsed to get the {@code mutationNode}.
 *
 * @param mutationNode the {@code mutation} XML node
 * @return {@link Mutation} object represented by the {@code mutation} XML node
 */
private static Mutation getMutation(Node mutationNode) {
    final List<Node> childNodes = mutationNode.children()

    String sourceFile = null
    String mutatedClass = null
    String mutatedMethod = null
    String mutator = null
    String lineContent = null
    String description = null
    String mutationClassPackage = null
    int lineNumber = 0
    childNodes.each {
        final Node childNode = it
        final String text = childNode.name()

        final String childNodeText = XmlUtil.escapeXml(childNode.text())
        switch (text) {
            case "sourceFile":
                sourceFile = childNodeText
                break
            case "mutatedClass":
                mutatedClass = childNodeText
                mutationClassPackage = mutatedClass.split("[A-Z]")[0]
                break
            case "mutatedMethod":
                mutatedMethod = childNodeText
                break
            case "mutator":
                mutator = childNodeText
                break
            case "description":
                description = childNodeText
                break
            case "lineNumber":
                lineNumber = Integer.parseInt(childNodeText)
                break
            case "lineContent":
                lineContent = childNodeText
                break
        }
    }
    if (lineContent == null) {
        final String mutationFileName = mutationClassPackage + sourceFile
        final String startingPath =
                ".${File.separator}src${File.separator}main${File.separator}java${File.separator}"
        final String javaExtension = ".java"
        final String mutationFilePath = startingPath + mutationFileName
                .substring(0, mutationFileName.length() - javaExtension.length())
                .replace(".", File.separator) + javaExtension

        final File file = new File(mutationFilePath)
        lineContent = XmlUtil.escapeXml(file.readLines().get(lineNumber - 1).trim())
    }
    if (lineNumber == 0) {
        lineNumber = -1
    }

    final String unstableAttributeValue = mutationNode.attribute("unstable")
    final boolean isUnstable = Boolean.parseBoolean(unstableAttributeValue)

    return new Mutation(sourceFile, mutatedClass, mutatedMethod, mutator, description,
            lineContent, lineNumber, isUnstable)
}

/**
 * Compare surviving and suppressed mutations. The comparison passes successfully (i.e. returns 0)
 * when:
 * <ol>
 *   <li>Surviving and suppressed mutations are equal.</li>
 *   <li>There are extra suppressed mutations but they are unstable
 *     i.e. {@code unstable="true"}.</li>
 * </ol>
 * The comparison fails (i.e. returns 1) when:
 * <ol>
 *     <li>Surviving mutations are not present in the suppressed list.</li>
 *     <li>There are mutations in the suppression list that are not there is surviving list.</li>
 * </ol>
 *
 * @param survivingMutations A set of surviving mutations
 * @param suppressedMutations A set of suppressed mutations
 * @return {@code 0} if comparison passes successfully
 */
private static int printComparisonToConsole(Set<Mutation> survivingMutations,
                                            Set<Mutation> suppressedMutations) {
    final Set<Mutation> survivingUnsuppressedMutations =
            setDifference(survivingMutations, suppressedMutations)
    final Set<Mutation> extraSuppressions =
            setDifference(suppressedMutations, survivingMutations)

    final int exitCode
    if (survivingMutations == suppressedMutations) {
        exitCode = 0
        println 'No new surviving mutation(s) found.'
    }
    else if (survivingUnsuppressedMutations.isEmpty()
            && hasOnlyUnstableMutations(extraSuppressions)) {
        exitCode = 0
        println 'No new surviving mutation(s) found.'
    }
    else {
        if (!survivingUnsuppressedMutations.isEmpty()) {
            println "New surviving mutation(s) found:"
            survivingUnsuppressedMutations.each {
                println it
            }
        }
        if (!extraSuppressions.isEmpty()
                && extraSuppressions.any { !it.isUnstable() }) {
            println "\nUnnecessary suppressed mutation(s) found and should be removed:"
            extraSuppressions.each {
                if (!it.isUnstable()) {
                    println it
                }
            }
        }
        exitCode = 1
    }
    return exitCode
}

/**
 * Whether a set has only unstable mutations.
 *
 * @param mutations A set of mutations
 * @return {@code true} if a set has only unstable mutations
 */
private static boolean hasOnlyUnstableMutations(Set<Mutation> mutations) {
    return mutations.every { it.isUnstable() }
}

/**
 * Determine the difference between 2 sets. The result is {@code setOne - setTwo}.
 *
 * @param setOne The first set in the difference
 * @param setTwo The second set in the difference
 * @return {@code setOne - setTwo}
 */
private static Set<Mutation> setDifference(final Set<Mutation> setOne,
                                           final Set<Mutation> setTwo) {
    final Set<Mutation> result = new TreeSet<>(setOne)
    result.removeIf { mutation -> setTwo.contains(mutation) }
    return result
}

/**
 * A class to represent the XML {@code mutation} node.
 */
@EqualsAndHashCode(excludes = ["lineNumber", "unstable"])
@Immutable
class Mutation implements Comparable<Mutation> {

    /**
     * Mutation nodes present in suppressions file do not have a {@code lineNumber}.
     * The {@code lineNumber} is set to {@code -1} for such mutations.
     */
    private static final int LINE_NUMBER_NOT_PRESENT_VALUE = -1

    String sourceFile
    String mutatedClass
    String mutatedMethod
    String mutator
    String description
    String lineContent
    int lineNumber
    boolean unstable

    @Override
    String toString() {
        String toString = """
            Source File: "${getSourceFile()}"
            Class: "${getMutatedClass()}"
            Method: "${getMutatedMethod()}"
            Line Contents: "${getLineContent()}"
            Mutator: "${getMutator()}"
            Description: "${getDescription()}"
            """.stripIndent()
        if (getLineNumber() != LINE_NUMBER_NOT_PRESENT_VALUE) {
            toString += 'Line Number: ' + getLineNumber()
        }
        return toString
    }

    @Override
    int compareTo(Mutation other) {
        int i = getSourceFile() <=> other.getSourceFile()
        if (i != 0) {
            return i
        }

        i = getMutatedClass() <=> other.getMutatedClass()
        if (i != 0) {
            return i
        }

        i = getMutatedMethod() <=> other.getMutatedMethod()
        if (i != 0) {
            return i
        }

        i = getLineContent() <=> other.getLineContent()
        if (i != 0) {
            return i
        }

        i = getMutator() <=> other.getMutator()
        if (i != 0) {
            return i
        }

        return getDescription() <=> other.getDescription()
    }

    /**
     * XML format of the mutation.
     *
     * @return XML format of the mutation
     */
    String toXmlString() {
        return """
            <mutation unstable="${isUnstable()}">
              <sourceFile>${getSourceFile()}</sourceFile>
              <mutatedClass>${getMutatedClass()}</mutatedClass>
              <mutatedMethod>${getMutatedMethod()}</mutatedMethod>
              <mutator>${getMutator()}</mutator>
              <description>${getDescription()}</description>
              <lineContent>${getLineContent()}</lineContent>
            </mutation>
            """.stripIndent(10)
    }

}
