/*
CustomImportOrder
customImportOrderRules = SAME_PACKAGE(2)
standardPackageRegExp = (default)^(java|javax)\.
thirdPartyPackageRegExp = (default).*
specialImportsRegExp = (default)^$
separateLineBetweenGroups = false
sortImportsInGroupAlphabetically = (default)false


*/

//non-compiled with javac: special package and requires imports from the same package
package java.util.concurrent.locks;
// SAME_PACKAGE(2) should include #1-8
// SAME_PACKAGE(3) should include #4-6
// SAME_PACKAGE(4) should include only #6
// SAME_PACKAGE(5) should include no imports because actual package has only 4 domains
import java.io.File;
import java.util.*; // violation '.* wrong order. Should be in the .*group, expecting not assigned imports.*'
import java.util.List; // violation '.* wrong order. Should be in the .*group, expecting not assigned imports.*'
import java.util.StringTokenizer; // violation '.* wrong order. Should be in the .*group, expecting not assigned imports .*'
import java.util.concurrent.*; // violation '.* wrong order. Should be in the .*group, expecting not assigned imports.*'
import java.util.concurrent.AbstractExecutorService; // violation '.* wrong order. Should be in the .*group, expecting not assigned imports.*'
import java.util.concurrent.locks.LockSupport; // violation '.* wrong order. Should be in the .*group, expecting not assigned imports.*'
import java.util.regex.Pattern; // violation '.* wrong order. Should be in the .*group, expecting not assigned imports.*'
import java.util.regex.Matcher; // violation '.* wrong order. Should be in the .*group, expecting not assigned imports.*'

public class InputCustomImportOrderSamePackageDepth25 {
}
