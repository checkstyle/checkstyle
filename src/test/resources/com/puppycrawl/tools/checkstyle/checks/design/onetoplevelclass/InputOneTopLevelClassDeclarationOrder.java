/*
OneTopLevelClass


*/

package com.puppycrawl.tools.checkstyle.checks.design.onetoplevelclass;

public class InputOneTopLevelClassDeclarationOrder
{
    private class InnerClass
    {
    }
}

// violation below 'Top-level class InputDeclarationOrderEnum has to reside in its own source file.'
enum InputDeclarationOrderEnum
{
    ENUM_VALUE_1;

    class AsyncProcess {
        private final int startLogErrorsCnt = 0;
        protected final int maxTotalConcurrentTasks = 0;
    }
}

// violation below 'Top-level class Input.* has to reside in its own source file.'
@interface InputDeclarationOrderAnnotation
{

}
