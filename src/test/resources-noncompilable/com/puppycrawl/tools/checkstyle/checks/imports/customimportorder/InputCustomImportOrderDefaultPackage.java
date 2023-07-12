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
import static java.awt.Button.ABORT; // violation 'Wrong lexicographical order for 'java.awt.Button.ABORT' import. Should be before 'java.io.File.createTempFile''
import static javax.swing.WindowConstants.*;

import java.awt.Button; // violation 'Import statement for 'java.awt.Button' is in the wrong order. Should be in the 'STANDARD_JAVA_PACKAGE' group, expecting group 'THIRD_PARTY_PACKAGE' on this line'
import java.awt.Frame; // violation 'Import statement for 'java.awt.Frame' is in the wrong order. Should be in the 'STANDARD_JAVA_PACKAGE' group, expecting group 'THIRD_PARTY_PACKAGE' on this line.'
import java.awt.Dialog; // violation 'Import statement for 'java.awt.Dialog' is in the wrong order. Should be in the 'STANDARD_JAVA_PACKAGE' group, expecting group 'THIRD_PARTY_PACKAGE' on this line.'
import java.awt.event.ActionEvent; // violation 'Import statement for 'java.awt.event.ActionEvent' is in the wrong order. Should be in the 'STANDARD_JAVA_PACKAGE' group, expecting group 'THIRD_PARTY_PACKAGE' on this line.'
import javax.swing.JComponent; // violation 'Import statement for 'javax.swing.JComponent' is in the wrong order. Should be in the 'STANDARD_JAVA_PACKAGE' group, expecting group 'THIRD_PARTY_PACKAGE' on this line.'
import javax.swing.JTable; // violation 'Import statement for 'javax.swing.JTable' is in the wrong order. Should be in the 'STANDARD_JAVA_PACKAGE' group, expecting group 'THIRD_PARTY_PACKAGE' on this line.'
import java.io.File; // violation 'Import statement for 'java.io.File' is in the wrong order. Should be in the 'STANDARD_JAVA_PACKAGE' group, expecting group 'THIRD_PARTY_PACKAGE' on this line.'
import java.io.IOException; // violation 'Import statement for 'java.io.IOException' is in the wrong order. Should be in the 'STANDARD_JAVA_PACKAGE' group, expecting group 'THIRD_PARTY_PACKAGE' on this line.'
import java.io.InputStream; // violation 'Import statement for 'java.io.InputStream' is in the wrong order. Should be in the 'STANDARD_JAVA_PACKAGE' group, expecting group 'THIRD_PARTY_PACKAGE' on this line.'
import java.io.Reader; // violation 'Import statement for 'java.io.Reader' is in the wrong order. Should be in the 'STANDARD_JAVA_PACKAGE' group, expecting group 'THIRD_PARTY_PACKAGE' on this line.'

import com.puppycrawl.tools.*;

import com.google.common.*; // 2 violations
import org.apache.*;

public class InputCustomImportOrderDefaultPackage {
}
