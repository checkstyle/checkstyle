/*
CustomImportOrder
customImportOrderRules = STATIC###SAME_PACKAGE(3)
standardPackageRegExp = (default)^(java|javax)\.
thirdPartyPackageRegExp = org.
specialImportsRegExp = (default)^$
separateLineBetweenGroups = (default)true
sortImportsInGroupAlphabetically = true


*/

//non-compiled with javac: special package and requires imports from the same package
package java.util.concurrent;
import com.google.common.*;
import java.util.StringTokenizer;
import java.util.*; // violation 'Wrong lexicographical order for\s.*. Should be before 'java.util.StringTokenizer''
import java.util.concurrent.*; // violation 'Import statement for 'java.util.concurrent.*' is in the wrong order. Should be in the 'SAME_PACKAGE' group, expecting not assigned imports on this line'
import static java.awt.Button.ABORT; // violation 'Import statement for 'java.awt.Button.ABORT' is in the wrong order. Should be in the 'STATIC' group, expecting not assigned imports on this line'
import static javax.swing.WindowConstants.*; // violation 'Import statement for 'javax.swing.WindowConstants.*' is in the wrong order. Should be in the 'STATIC' group, expecting not assigned imports on this line'
import com.puppycrawl.tools.*; // violation 'Wrong lexicographical order for\s.*. Should be before 'java.util.StringTokenizer''
import java.util.concurrent.AbstractExecutorService; // violation 'Import statement for 'java.util.concurrent.AbstractExecutorService' is in the wrong order. Should be in the 'SAME_PACKAGE' group, expecting not assigned imports on this line'
import static java.io.File.createTempFile; // violation 'Import statement for 'java.io.File.createTempFile' is in the wrong order. Should be in the 'STATIC' group, expecting not assigned imports on this line'
import com.*; // violation 'Wrong lexicographical order for\s.*. Should be before 'java.util.StringTokenizer''
import org.apache.*;

public class InputCustomImportOrderSamePackage {
}
