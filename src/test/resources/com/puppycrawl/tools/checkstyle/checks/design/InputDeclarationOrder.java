package com.puppycrawl.tools.checkstyle.checks.design;

public class InputDeclarationOrder
{
    private class InnerClass
    {
    }
}

enum InputDeclarationOrderEnum
{
    ENUM_VALUE_1;

    class AsyncProcess {
        private final int startLogErrorsCnt = 0;
        protected final int maxTotalConcurrentTasks = 0;
    }
}
