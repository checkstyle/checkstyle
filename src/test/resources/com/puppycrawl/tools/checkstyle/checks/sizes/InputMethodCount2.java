package com.puppycrawl.tools.checkstyle.checks.sizes;

public enum InputMethodCount2 {

    RED {
        @Override void something() {};
    },

    BLUE {
        @Override void something() {};
        protected void other1() {};
        private void other2() {};
    };

    @Override public String toString() { return ""; };

    abstract void something();
}
