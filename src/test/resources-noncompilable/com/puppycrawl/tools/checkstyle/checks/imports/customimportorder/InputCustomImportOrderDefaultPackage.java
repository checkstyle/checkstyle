/*
CustomImportOrder
customImportOrderRules = STATIC###SAME_PACKAGE(3)###THIRD_PARTY_PACKAGE###STANDARD_JAVA_PACKAGE
standardPackageRegExp = (default)^(java|javax)\.
thirdPartyPackageRegExp = com|org
specialImportsRegExp = (default)^$
separateLineBetweenGroups = (default)true
sortImportsInGroupAlphabetically = true


*/

//non-compiled with javac: contains specially crafted set of imports for testing

//////////////////////////////////////////
//Some header
//////////////////////////////////////////
import static java.io.File.createTempFile;
import static java.awt.Button.ABORT; // violation
import static javax.swing.WindowConstants.*;

import java.awt.Button; // violation
import java.awt.Frame; // violation
import java.awt.Dialog; // violation
import java.awt.event.ActionEvent; // violation
import javax.swing.JComponent; // violation
import javax.swing.JTable; // violation
import java.io.File; // violation
import java.io.IOException; // violation
import java.io.InputStream; // violation
import java.io.Reader; // violation

import com.puppycrawl.tools.*;

import com.google.common.*; // 2 violations
import org.apache.*;

public class InputCustomImportOrderDefaultPackage {
}
