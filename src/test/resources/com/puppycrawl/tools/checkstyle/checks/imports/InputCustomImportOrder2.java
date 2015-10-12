package com.puppycrawl.tools.checkstyle.checks.imports;

import static java.io.File.createTempFile;
import static java.awt.Button.ABORT; //warn, LEXIC, should be before java.io.File.createTempFile
import static javax.swing.WindowConstants.*;

import java.util.List; //warn, LEXIC, should be before javax.swing.WindowConstants.*
import java.util.StringTokenizer; //warn, LEXIC, should be before javax.swing.WindowConstants.*
import java.util.*; //warn, LEXIC, should be before javax.swing.WindowConstants.*
import java.util.concurrent.AbstractExecutorService; //warn, LEXIC, should be before javax.swing.WindowConstants.*
import java.util.concurrent.*; //warn, LEXIC, should be before javax.swing.WindowConstants.*

import com.puppycrawl.tools.*;
import com.*; //warn, LEXIC, should be before com.puppycrawl.tools.*

import com.google.common.base.*; //warn, LEXIC, should be before com.puppycrawl.tools.*
import org.junit.*;

public class InputCustomImportOrder2 {
}
/*
test: testOrderRuleWithOneGroup()
configuration:
        checkConfig.addAttribute("thirdPartyPackageRegExp", "org.");
        checkConfig.addAttribute("customImportOrderRules",
                "STANDARD_JAVA_PACKAGE");
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "true");
*/
