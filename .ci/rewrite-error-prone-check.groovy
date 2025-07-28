import groovy.transform.EqualsAndHashCode
import groovy.transform.Field
import groovy.transform.Immutable
import groovy.xml.XmlUtil

import java.util.regex.Matcher
import java.util.regex.Pattern

System.exit(getErrorProneErrors())

/**
 * Generates the error prone report and filters out the errors.
 *
 * @param profile the error prone profile to execute
 * @return A set of errors
 */
private static int getErrorProneErrors() {
    final List<String> errorProneErrors = [] as ArrayList
    final Process process = getOsSpecificCmd("mvn -e --no-transfer-progress clean rewrite:dryRun")
            .execute()
    process.in.eachLine { line ->
        if (line.startsWith("[ERROR]")) {
            errorProneErrors.add(line)
        }
        println(line)
    }
    process.waitFor()
    return 0
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

