/*
UniqueProperties
fileExtensions = (default).properties


*/

package com.puppycrawl.tools.checkstyle.checks.uniqueproperties;

// Config-only sidecar for testShouldNotProcessFilesWithWrongFileExtension.
// The actual target lives in InputUniqueProperties.txt in the same directory
// and must stay a plain .txt file so the check's default
// fileExtensions = ".properties" filter is what is being tested.
public class InputUniquePropertiesTxtConfig {
}
