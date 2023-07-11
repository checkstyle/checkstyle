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
import java.util.*; // violation 'Import statement for 'java.util.*' is in the wrong order. Should be in the 'SAME_PACKAGE' group, expecting not assigned imports on this line.'
import java.util.List; // violation 'Import statement for 'java.util.List' is in the wrong order. Should be in the 'SAME_PACKAGE' group, expecting not assigned imports on this line.'
import java.util.StringTokenizer; // violation 'Import statement for 'java.util.StringTokenizer' is in the wrong order. Should be in the 'SAME_PACKAGE' group, expecting not assigned imports on this line.'
import java.util.concurrent.*; // violation 'Import statement for 'java.util.concurrent.*' is in the wrong order. Should be in the 'SAME_PACKAGE' group, expecting not assigned imports on this line.'
import java.util.concurrent.AbstractExecutorService; // violation 'Import statement for 'java.util.concurrent.AbstractExecutorService' is in the wrong order. Should be in the 'SAME_PACKAGE' group, expecting not assigned imports on this line.'
import java.util.concurrent.locks.LockSupport; // violation 'Import statement for 'java.util.concurrent.locks.LockSupport' is in the wrong order. Should be in the 'SAME_PACKAGE' group, expecting not assigned imports on this line.'
import java.util.regex.Pattern; // violation 'Import statement for 'java.util.regex.Pattern' is in the wrong order. Should be in the 'SAME_PACKAGE' group, expecting not assigned imports on this line.'
import java.util.regex.Matcher; // violation 'Import statement for 'java.util.regex.Matcher' is in the wrong order. Should be in the 'SAME_PACKAGE' group, expecting not assigned imports on this line.'

public class InputCustomImportOrderSamePackageDepth25 {
}
