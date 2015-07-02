package java.util.concurrent.locks;
// SAME_PACKAGE(2) should include #1-8
// SAME_PACKAGE(3) should include #4-6
// SAME_PACKAGE(4) should include only #6
// SAME_PACKAGE(5) should include no imports because actual package has only 4 domains 
import java.util.*; //#1
import java.util.List; //#2
import java.util.StringTokenizer; //#3
import java.util.concurrent.*; //#4
import java.util.concurrent.AbstractExecutorService; //#5
import java.util.concurrent.locks.LockSupport; //#6
import java.util.regex.Pattern; //#7
import java.util.regex.Matcher; //#8

public class InputCustomImportOrderSamePackage2 {
}

/*
test: testSamePackageDepth2()
configuration:
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "false");
        checkConfig.addAttribute("separateLineBetweenGroups", "false");
        checkConfig.addAttribute("customImportOrderRules",
                "SAME_PACKAGE(2)");
*/

/*
test: testSamePackageDepth3()
configuration:
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "false");
        checkConfig.addAttribute("separateLineBetweenGroups", "false");
        checkConfig.addAttribute("customImportOrderRules",
                "SAME_PACKAGE(3)");
*/

/*
test: testSamePackageDepth4()
configuration:
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "false");
        checkConfig.addAttribute("separateLineBetweenGroups", "false");
        checkConfig.addAttribute("customImportOrderRules",
                "SAME_PACKAGE(3)");
*/

/*
test: testSamePackageDepthLongerThenActualPackage()
configuration:
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "false");
        checkConfig.addAttribute("separateLineBetweenGroups", "false");
        checkConfig.addAttribute("customImportOrderRules",
                "SAME_PACKAGE(3)");
*/
