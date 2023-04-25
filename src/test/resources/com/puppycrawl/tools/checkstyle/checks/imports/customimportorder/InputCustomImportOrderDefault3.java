/*
CustomImportOrder
customImportOrderRules = STATIC###STANDARD_JAVA_PACKAGE###THIRD_PARTY_PACKAGE
standardPackageRegExp = (default)^(java|javax)\.
thirdPartyPackageRegExp = com.|org.
specialImportsRegExp = (default)^$
separateLineBetweenGroups = (default)true
sortImportsInGroupAlphabetically = true


*/

package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder;

import static java.io.File.createTempFile;
import static java.awt.Button.ABORT; // violation in ASCII order 'A' should go before 'c',
// as all uppercase come before lowercase letters
import static java.awt.print.Paper.*; // violation in ASCII order 'P' should go before 'c',
// as all uppercase come before lowercase letters
import static javax.swing.WindowConstants.*;

import java.awt.Button;
import java.awt.Frame;
import java.awt.Dialog; // violation in ASCII order 'D' should go before 'F'
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import javax.swing.JComponent;
import javax.swing.JTable;
import java.io.File; // violation in ASCII order 'i' should go before 's'
import java.io.IOException; // violation in ASCII order 'i' should go before 's'
import java.io.InputStream; // violation in ASCII order 'i' should go before 's'
import java.io.Reader; // violation in ASCII order 'i' should go before 's'

import com.puppycrawl.tools.checkstyle.*;

import com.google.common.collect.*; // 2 violations
import org.junit.*;

public class InputCustomImportOrderDefault3 {
}
