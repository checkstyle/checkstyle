/*
CustomImportOrder
customImportOrderRules = STATIC###SAME_PACKAGE(3)###THIRD_PARTY_PACKAGE###STANDARD_JAVA_PACKAGE
standardPackageRegExp = (default)^(java|javax)\\.
thirdPartyPackageRegExp = com|org
specialImportsRegExp = (default)^$
separateLineBetweenGroups = (default)true
sortImportsInGroupAlphabetically = true


*/

package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder;

import static java.io.File.createTempFile;
import static java.awt.Button.ABORT; // violation - in ASCII order 'A' should go before 'c',
// as all uppercase come before lowercase letters
import static java.awt.print.Paper.*; // violation - in ASCII order 'P' should go before 'c',
// as all uppercase come before lowercase letters
import static javax.swing.WindowConstants.*;

import java.awt.Button; // violation should be at the end
import java.awt.Frame; // violation should be at the end
import java.awt.Dialog; // violation should be at the end
import java.awt.color.ColorSpace; // violation should be at the end
import java.awt.event.ActionEvent; // violation should be at the end
import javax.swing.JComponent; // violation should be at the end
import javax.swing.JTable; // violation should be at the end
import java.io.File; // violation should be at the end
import java.io.IOException; // violation should be at the end
import java.io.InputStream; // violation should be at the end
import java.io.Reader; // violation should be at the end

import com.puppycrawl.tools.checkstyle.*;

import com.google.common.collect.*;
import org.junit.*;

public class InputCustomImportOrderDefault {
}
