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
import java.util.*; // violation 'Wrong lexicographical order for.*. Should be before .*'
import java.util.concurrent.*; // violation '.* wrong order. Should be in the .*group, expecting not assigned imports.*'
import static java.awt.Button.ABORT; // violation '.* wrong order. Should be in the .*group, expecting not assigned imports.*'
import static javax.swing.WindowConstants.*; // violation '.* wrong order. Should be in the .*group, expecting not assigned imports.*'
import com.puppycrawl.tools.*; // violation 'Wrong lexicographical order for.*. Should be before .*'
import java.util.concurrent.AbstractExecutorService; // violation '.* wrong order. Should be in the .*group, expecting not assigned imports.*'
import static java.io.File.createTempFile; // violation '.* wrong order. Should be in the .* group, expecting not assigned imports.*'
import com.*; // violation 'Wrong lexicographical order for .*. Should be before .*'
import org.apache.*;

public class InputCustomImportOrderSamePackage2 {
}
