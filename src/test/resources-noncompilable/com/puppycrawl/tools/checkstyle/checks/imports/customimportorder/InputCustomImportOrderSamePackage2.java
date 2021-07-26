/*
CustomImportOrder
customImportOrderRules = STATIC###SAME_PACKAGE(3)
standardPackageRegExp = (default)^(java|javax)\.
thirdPartyPackageRegExp = org.
specialImportsRegExp = (default)^$
separateLineBetweenGroups = false
sortImportsInGroupAlphabetically = true


*/

//non-compiled with javac: special package and requires imports from the same package
package java.util.concurrent;
import com.google.common.*;
import java.util.StringTokenizer;
import java.util.*; // violation
import java.util.concurrent.*; // violation
import static java.awt.Button.ABORT; // violation
import static javax.swing.WindowConstants.*; // violation
import com.puppycrawl.tools.*; // violation
import java.util.concurrent.AbstractExecutorService; // violation
import static java.io.File.createTempFile; // violation
import com.*; // violation
import org.apache.*;

public class InputCustomImportOrderSamePackage2 {
}
