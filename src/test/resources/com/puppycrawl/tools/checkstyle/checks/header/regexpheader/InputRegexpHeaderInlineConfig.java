/*
RegexpHeader
headerFile = (default)null
charset = (default)null
fileExtensions = (default)""
header = ^/*$\n// .*\n// Created: 2002\n^//.*\n^//.*
multiLines = (default)


*/

package com.puppycrawl.tools.checkstyle.checks.header.regexpheader;

// Config-only sidecar for testInlineRegexpHeader. The actual target
// lives in InputRegexpHeaderInline.java in the same directory and
// stays untouched; embedding this config block in the target itself
// would be part of what RegexpHeaderCheck compares against the
// configured header patterns.
public class InputRegexpHeaderInlineConfig {
}
