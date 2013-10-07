package com.puppycrawl.tools.checkstyle.checks.metrics;

public class BooleanExpressionComplexityCheckTestInput {
    private boolean _a = false;
    private boolean _b = false;
    private boolean _c = false;
    private boolean _d = false;

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
        return (((_a && (_b & _c)) || (_c ^ _d) || (_a && _d)));
    }
    
    public boolean bitwise()
    {
        return (((_a & (_b & _c)) | (_c ^ _d) | (_a & _d)));
    }
}
