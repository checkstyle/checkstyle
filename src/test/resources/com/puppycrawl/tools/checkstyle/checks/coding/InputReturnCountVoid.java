package com.puppycrawl.tools.checkstyle.checks.coding;

class InputReturnCountVoid {
    public InputReturnCountVoid() {
        return;
    }

    public void method() {
        if (true) {
            return;
        }
    }

    public void method2() {
        if (true) {
            return;
        }

        return;
    }

    public int method3() {
        if (true) {
            return 0;
        }

        return 0;
    }

    public int method4() {
        if (true) {
            return 0;
        }
        if (false) {
            return 0;
        }

        return 0;
    }
}