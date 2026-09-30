/*
UniqueProperties
fileExtensions = (default).properties


*/

package com.puppycrawl.tools.checkstyle.checks.uniqueproperties;

// Config-only sidecar for testDefault. The actual target lives in
// InputUniqueProperties.properties in the same directory and must stay
// a .properties file so the Java Properties parser handles it. Inline
// // violation markers are appended to duplicated-key lines on the
// value side, which does not change the key the check counts.
public class InputUniquePropertiesConfig {
}
