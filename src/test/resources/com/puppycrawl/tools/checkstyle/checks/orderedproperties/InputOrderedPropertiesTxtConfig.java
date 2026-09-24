/*
OrderedProperties
fileExtensions = (default).properties


*/

package com.puppycrawl.tools.checkstyle.checks.orderedproperties;

// Config-only sidecar for testShouldNotProcessFilesWithWrongFileExtension.
// The actual target lives in InputOrderedProperties.txt in the same
// directory and must stay a plain .txt file so the check's default
// fileExtensions = "properties" filter is what is being tested.
public class InputOrderedPropertiesTxtConfig {
}
