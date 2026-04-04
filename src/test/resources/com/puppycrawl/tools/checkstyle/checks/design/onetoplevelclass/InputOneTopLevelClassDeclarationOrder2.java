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

enum InputDeclarationOrderEnum2 // violation 'Top-level class InputDeclarationOrderEnum2 has to reside in its own source file.'
{
    ENUM_VALUE_1;

    class AsyncProcess {
        private final int startLogErrorsCnt = 0;
        protected final int maxTotalConcurrentTasks = 0;
    }
}

@interface InputDeclarationOrderAnnotation2 // violation 'Top-level class InputDeclarationOrderAnnotation2 has to reside in its own source file.'
{

}
