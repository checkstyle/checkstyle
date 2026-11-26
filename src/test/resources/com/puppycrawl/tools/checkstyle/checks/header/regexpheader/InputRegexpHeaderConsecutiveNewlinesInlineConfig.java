/*
RegexpHeader
headerFile = (default)null
charset = (default)null
fileExtensions = (default)""
header = ^/*$\n// .*\n\n// Created: 2017\n^//.*
multiLines = (default)


*/

package com.puppycrawl.tools.checkstyle.checks.header.regexpheader;

// Config-only sidecar for testInlineRegexpHeaderConsecutiveNewlines.
// The actual target lives in InputRegexpHeaderConsecutiveNewlinesInline.java
// in the same directory and stays untouched; embedding this config
// block in the target itself would be part of what RegexpHeaderCheck
// compares against the configured header patterns.
public class InputRegexpHeaderConsecutiveNewlinesInlineConfig {
}
