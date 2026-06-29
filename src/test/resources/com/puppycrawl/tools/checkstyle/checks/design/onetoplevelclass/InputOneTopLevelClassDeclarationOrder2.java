/*
OneTopLevelClass


*/

package com.puppycrawl.tools.checkstyle.checks.design.onetoplevelclass;

public class InputOneTopLevelClassDeclarationOrder2
{
    private class InnerClass
    {
    }
}

// violation below 'Top-level class Input.* has to reside in its own source file.'
enum InputDeclarationOrderEnum2
{
    ENUM_VALUE_1;

    class AsyncProcess {
        private final int startLogErrorsCnt = 0;
        protected final int maxTotalConcurrentTasks = 0;
    }
}

// violation below 'Top-level class Input.* has to reside in its own source file.'
@interface InputDeclarationOrderAnnotation2
{

}
