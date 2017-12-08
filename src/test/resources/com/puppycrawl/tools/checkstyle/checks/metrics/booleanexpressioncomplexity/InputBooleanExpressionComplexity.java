package com.puppycrawl.tools.checkstyle.checks.metrics.booleanexpressioncomplexity;

public class InputBooleanExpressionComplexity {
    private boolean _a = false; //boolean field
    private boolean _b = false;
    private boolean _c = false;
    private boolean _d = false;
    /*public method*/
    public void foo() {
        if (_a && _b || _c ^ _d) {
        }

        if (((_a && (_b & _c)) || (_c ^ _d))) {
        }

        if (_a && _b && _c) {
        }

        if (_a & _b) {
        }

        if (_a) {
        }
    }

    public boolean equals(Object object) {
        new NestedClass() {
            public void method() {
                new Settings(Settings.FALSE || Settings.FALSE || Settings.FALSE || Settings.FALSE || Settings.FALSE);
            }
            public void method2() {
            }
        };
        return (((_a && (_b & _c)) || (_c ^ _d) || (_a && _d)));
    }
    
    public boolean bitwise()
    {
        return (((_a & (_b & _c)) | (_c ^ _d) | (_a & _d)));
    }

    public void notIgnoredMethodParameters()
    {
        new Settings(Settings.FALSE && Settings.FALSE && Settings.FALSE
                && Settings.TRUE && Settings.TRUE);
        new Settings(Settings.FALSE || Settings.FALSE || Settings.FALSE
                || Settings.TRUE || Settings.TRUE);
    }

    public void ignoredMethodParameters()
    {
        new Settings(Settings.RESIZABLE | Settings.SCROLLBARS | Settings.LOCATION_BAR
                | Settings.MENU_BAR | Settings.TOOL_BAR);
        new Settings(Settings.RESIZABLE & Settings.SCROLLBARS & Settings.LOCATION_BAR
                & Settings.MENU_BAR & Settings.TOOL_BAR);
        new Settings(Settings.RESIZABLE ^ Settings.SCROLLBARS ^ Settings.LOCATION_BAR
                ^ Settings.MENU_BAR ^ Settings.TOOL_BAR);
    }

    private class Settings {
        public final static int RESIZABLE = 1;
        public final static int SCROLLBARS = 2;
        public final static int LOCATION_BAR = 3;
        public final static int MENU_BAR = 4;
        public final static int TOOL_BAR = 5;

        public final static boolean TRUE = true;
        public final static boolean FALSE = false;

        public Settings(int flag)
        {
        }

        public Settings(boolean flag)
        {
        }
    }

    abstract class NestedClass {
        public abstract void method();
        public abstract void method2();
    }
}
