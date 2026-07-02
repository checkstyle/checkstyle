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

enum InputDeclarationOrderEnum // violation 'Top-level class InputDeclarationOrderEnum has to reside in its own source file.'
{
    ENUM_VALUE_1;

    class AsyncProcess {
        private final int startLogErrorsCnt = 0;
        protected final int maxTotalConcurrentTasks = 0;
    }
}

@interface InputDeclarationOrderAnnotation // violation 'Top-level class InputDeclarationOrderAnnotation has to reside in its own source file.'
{

}
