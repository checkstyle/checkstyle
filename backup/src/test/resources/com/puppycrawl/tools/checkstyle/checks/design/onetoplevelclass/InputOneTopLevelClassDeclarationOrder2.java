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

enum InputDeclarationOrderEnum2 // violation
{
    ENUM_VALUE_1;

    class AsyncProcess {
        private final int startLogErrorsCnt = 0;
        protected final int maxTotalConcurrentTasks = 0;
    }
}

@interface InputDeclarationOrderAnnotation2 // violation
{

}
