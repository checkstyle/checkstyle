//Moved to noncompilable because UT requires imports from the same package
package java.util.concurrent;
import java.util.regex.Pattern;
import java.util.List; //warn, LEX, should be before "java.util.regex.Pattern"
import java.util.regex.Matcher; //warn, LEX, should be before "java.util.regex.Pattern"
import java.util.StringTokenizer; //warn, LEX, should be before "java.util.regex.Pattern"
import java.util.*;  //warn, LEX, should be before "java.util.regex.Pattern"
import java.util.concurrent.AbstractExecutorService; //warn, ORDER, should be on SAME_PACKAGE, now NOT_ASSIGNED
import java.util.concurrent.*; //warn, ORDER, should be on SAME_PACKAGE, now NOT_ASSIGNED

public class InputCustomImportOrderSamePackage2 {
}
/*
test: testOnlySamePackage()
configuration:
        checkConfig.addAttribute("customImportOrderRules", "SAME_PACKAGE(3)");
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "true");
*/
