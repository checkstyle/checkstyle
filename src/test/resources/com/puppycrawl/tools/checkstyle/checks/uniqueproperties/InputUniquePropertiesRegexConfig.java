/*
UniqueProperties
fileExtensions = (default).properties


*/

package com.puppycrawl.tools.checkstyle.checks.uniqueproperties;

// Config-only sidecar for testRegexMetaCharacters. The actual target lives
// in InputUniquePropertiesRegex.properties in the same directory and its
// key contains regex meta-characters ([, ], -) so that the duplicate-key
// path is not affected by regex interpretation.
public class InputUniquePropertiesRegexConfig {
}
