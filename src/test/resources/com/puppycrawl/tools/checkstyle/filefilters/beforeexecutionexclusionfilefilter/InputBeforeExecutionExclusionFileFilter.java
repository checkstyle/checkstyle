/*
BeforeExecutionExclusionFileFilter
fileNamePattern = FileFilter\\.java


com.puppycrawl.tools.checkstyle.checks.coding.FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
tokens = (default)VARIABLE_DEF


*/

package com.puppycrawl.tools.checkstyle.filefilters.beforeexecutionexclusionfilefilter;

public class InputBeforeExecutionExclusionFileFilter {
    void foo() {
        int i = 0; // filtered violation
    }
}
