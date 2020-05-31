package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder;

import static java.io.File.createTempFile;
import static java.awt.Button.ABORT; //warn, LEXIC, should be before java.io.File.createTempFile
import static javax.swing.WindowConstants.*;

import java.util.List; //warn, SEPARATED_IN_GROUP, LEXIC, should be before javax.swing.WindowConstants.*
import java.util.StringTokenizer; //warn, LEXIC, should be before javax.swing.WindowConstants.*
import java.util.*; //warn, LEXIC, should be before javax.swing.WindowConstants.*
import java.util.concurrent.AbstractExecutorService; //warn, LEXIC, should be before javax.swing.WindowConstants.*
import java.util.concurrent.*; //warn, LEXIC, should be before javax.swing.WindowConstants.*

import com.puppycrawl.tools.checkstyle.checks.*;
import com.puppycrawl.tools.checkstyle.*; //warn, LEXIC, should be before com.puppycrawl.tools.checkstyle.checks.*

import com.google.common.base.*; //warn, SEPARATED_IN_GROUP, LEXIC, should be before com.puppycrawl.tools.*
import org.junit.*;

public class InputCustomImportOrderDefault2 {
}
/*
 * test: testOrderRuleWithOneGroup()
 *
 * Config = default
 * customImportOrderRules = STANDARD_JAVA_PACKAGE
 * standardPackageRegExp = "^(java|javax)\."
 * thirdPartyPackageRegExp = "org."
 * specialImportsRegExp = "^$"
 * sortImportsInGroupAlphabetically = true
 * separateLineBetweenGroups = true
 *
 */
