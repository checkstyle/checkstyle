//Moved to noncompilable because UT requires imports from the same package
package java.util.concurrent;
import com.google.common.*;
import java.util.StringTokenizer;
import java.util.*; //warn, LEX, should be before "java.util.StringTokenizer"
import java.util.concurrent.*; //warn, ORDER, should be on SAME_PACKAGE, now NOT_ASSIGNED
import static java.awt.Button.ABORT; //warn, ORDER, should be on STATIC, now NOT_ASSIGNED
import static javax.swing.WindowConstants.*; //warn, ORDER, should be on STATIC, now NOT_ASSIGNED
import com.puppycrawl.tools.*; //warn, LEX, should be before "java.util.StringTokenizer"
import java.util.concurrent.AbstractExecutorService; //warn, ORDER, should be on SAME_PACKAGE, now NOT_ASSIGNED
import static java.io.File.createTempFile; //warn, ORDER, should be on STATIC, now NOT_ASSIGNED
import com.*; //warn, LEX, should be before "java.util.StringTokenizer"
import org.apache.*;

public class InputCustomImportOrderSamePackage {
}
/*
test: testStaticSamePackage()
configuration:
        checkConfig.addAttribute("thirdPartyPackageRegExp", "org.");
        checkConfig.addAttribute("customImportOrderRules",
                "STATIC###SAME_PACKAGE(3)");
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "true");
*/
