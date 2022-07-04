import groovy.io.FileType
import groovy.transform.EqualsAndHashCode
import groovy.transform.Field
import groovy.transform.Immutable
import groovy.util.slurpersupport.GPathResult
import groovy.util.slurpersupport.NodeChildren
import groovy.xml.XmlUtil

@Field final static String SEPARATOR = System.getProperty("file.separator")

final int exitCode
if (args.length == 2) {
    exitCode = parseArgumentAndExecute(args[0], args[1])
}
else {
    exitCode = parseArgumentAndExecute(args[0], null)
}
System.exit(exitCode)

/**
 * Parse the command line arguments passed in and execute the branch based on the arguments.
 *
 * @param argument command line argument
 * @return {@code 0} if command executes successfully, {@code 1} otherwise
 */
private int parseArgumentAndExecute(String argument, String flag) {
    final Set<String> profiles = getPitestProfiles()
    final String usageString = """
        Usage groovy ./.ci/pitest-survival-check-xml.groovy [profile] [-g | --generate-suppression]
        To see the full list of supported profiles run
        'groovy ./.ci/pitest-survival-check-xml.groovy --list'
        """.stripIndent()

    final int exitCode
    if (profiles.contains(argument)) {
        if (flag != null && flag != "-g" && flag != "--generate-suppression") {
            final String exceptionMessage = "\nUnexpected flag: ${flag}" + usageString
            throw new IllegalArgumentException(exceptionMessage)
        }
        exitCode = checkPitestReport(argument, flag)

    }
    else if (argument == "--list") {
        println "Supported profiles:"
        profiles.each { println it }
        exitCode = 0
    }
    else {
        final String exceptionMessage = "\nUnexpected argument: ${argument}" + usageString
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
    final GPathResult mainNode = new XmlSlurper().parse(".${SEPARATOR}pom.xml")
    final NodeChildren ids = mainNode.profiles.profile.id as NodeChildren
    final Set<String> profiles = new HashSet<>()
    ids.each { node ->
        final GPathResult id = node as GPathResult
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
 * @param flag command line argument flag to determine output format
 * @return {@code 0} if pitest report is as expected, {@code 1} otherwise
 */
private static int checkPitestReport(String profile, String flag) {
    final XmlParser xmlParser = new XmlParser()
    File mutationReportFile = null
    final String suppressedMutationFileUri =
            ".${SEPARATOR}.ci${SEPARATOR}pitest-suppressions${SEPARATOR}${profile}-suppressions.xml"

    final File pitReports = new File(".${SEPARATOR}target${SEPARATOR}pit-reports")

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
    Set<Mutation> suppressedMutations = Collections.emptySet()
    if (suppressionFile.exists()) {
        final Node suppressedMutationNode = xmlParser.parse(suppressedMutationFileUri)
        suppressedMutations = getSuppressedMutations(suppressedMutationNode)
    }
    return compareMutations(survivingMutations, suppressedMutations, flag)
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
        final Node mutationNode = node as Node

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
        final Node mutationNode = node as Node
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
    final List childNodes = mutationNode.children()

    String sourceFile = null
    String mutatedClass = null
    String mutatedMethod = null
    String mutator = null
    String lineContent = null
    String description = null
    String mutationClassPackage = null
    int lineNumber = 0
    childNodes.each {
        final Node childNode = it as Node
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
        final String startingPath = ".${SEPARATOR}src${SEPARATOR}main${SEPARATOR}java${SEPARATOR}"
        final String javaExtension = ".java"
        final String mutationFilePath = startingPath + mutationFileName
                .substring(0, mutationFileName.length() - javaExtension.length())
                .replaceAll("\\.", SEPARATOR) + javaExtension

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
 * @param flag command line argument flag to determine output format
 * @return {@code 0} if comparison passes successfully
 */
private static int compareMutations(Set<Mutation> survivingMutations,
                                    Set<Mutation> suppressedMutations,
                                    String flag) {
    final Set<Mutation> survivingUnsuppressedMutations =
            setDifference(survivingMutations, suppressedMutations)
    final Set<Mutation> extraSuppressions =
            setDifference(suppressedMutations, survivingMutations)

    final int exitCode
    if (survivingMutations == suppressedMutations) {
        exitCode = 0
    }
    else if (survivingUnsuppressedMutations.isEmpty()
            && !hasOnlyStableMutations(extraSuppressions)) {
        exitCode = 0
    }
    else {
        if (!survivingUnsuppressedMutations.isEmpty()) {
            println "Surviving mutation(s) found:"
            survivingUnsuppressedMutations.each {
                printMutation(flag, it)
            }
        }
        if (!extraSuppressions.isEmpty()
                && extraSuppressions.any { !it.isUnstable() }) {
            println "\nUnnecessary suppressed mutation(s) found:"
            extraSuppressions.each {
                if (!it.isUnstable()) {
                    printMutation(flag, it)
                }
            }
        }
        exitCode = 1
    }
    return exitCode
}

/**
 * Whether a set has only stable mutations.
 *
 * @param mutations A set of mutations
 * @return {@code true} if a set has only stable mutations
 */
private static boolean hasOnlyStableMutations(Set<Mutation> mutations) {
    return mutations.every { !it.isUnstable() }
}

/**
 * Prints the mutation according to the nature of the flag.
 *
 * @param flag command line argument flag to determine output format
 * @param mutation mutation to print
 */
private static void printMutation(String flag, Mutation mutation) {
    if (flag != null) {
        println mutation.toXmlString()
    }
    else {
        println mutation
    }
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
    final Set<Mutation> result = new HashSet<>(setOne)
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
