/*
Indentation
arrayInitIndent = (default)4
basicOffset = (default)4
braceAdjustment = (default)0
caseIndent = (default)4
forceStrictCondition = true
lineWrappingIndentation = (default)4
throwsIndent = (default)4

*/

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;

public class InputIndentationNestedCallArgumentsReport {
    // Reduced from Apache Ant UnknownElement.java:351-353 in the regression report.
    void nestedCondition(String parentUri, String childNamespace, String childTag) {
        if (supportsNestedElement(
            parentUri, componentName(
                childNamespace, childTag))) {
        }
        if (supportsNestedElement(
            parentUri, componentName(
                    // violation below 'incorrect .* level 20, expected level should be 16.'
                    childNamespace, childTag))) {
        }
    }

    // Reduced from Apache Ant MailLogger.java:139-144 in the regression report.
    void fluentChain(String properties) {
        if (properties != null) {
            StringBuilder values = new StringBuilder()
                .append(Integer.parseInt(
                    getValue(
                        properties, "port",
                        String.valueOf(25))));
            StringBuilder invalidValues = new StringBuilder()
                .append(Integer.parseInt(
                    // violation below 'incorrect .* level 26, expected level should be 20.'
                          getValue(
                    // violation below 'incorrect .* level 30, expected level should be 24.'
                              properties, "port",
                    // violation below 'incorrect .* level 30, expected level should be 24.'
                              String.valueOf(25))));
        }
    }

    private boolean supportsNestedElement(String parentUri, String componentName) {
        return false;
    }

    private String componentName(String namespace, String tag) {
        return tag;
    }

    private String getValue(String properties, String name, String defaultValue) {
        return defaultValue;
    }
}
