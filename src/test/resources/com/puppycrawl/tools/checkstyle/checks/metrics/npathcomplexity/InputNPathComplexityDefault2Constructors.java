/*
NPathComplexity
max = (default)200


*/

package com.puppycrawl.tools.checkstyle.checks.metrics.npathcomplexity;

public class InputNPathComplexityDefault2Constructors {

    // NP = 3
    public InputNPathComplexityDefault2Constructors()
    {
        int i = 1;
        // NP = (if-range=1) + (else-range=2) + 0 = 3
        if (System.currentTimeMillis() == 0) {
        // NP(else-range) = (if-range=1) + (else-range=1) + (expr=0) = 2
        } else if (System.currentTimeMillis() == 0) {
        } else {
        }
    }

    // STATIC_INIT
    // NP = 3
    static {
        int i = 1;
        // NP = (if-range=1) + (else-range=2) + 0 = 3
        if (System.currentTimeMillis() == 0) {
        // NP(else-range) = (if-range=1) + (else-range=1) + (expr=0) = 2
        } else if (System.currentTimeMillis() == 0) {
        } else {
        }
    }

    // INSTANCE_INIT
    // NP = 3
    {
        int i = 1;
        // NP = (if-range=1) + (else-range=2) + 0 = 3
        if (System.currentTimeMillis() == 0) {
        // NP(else-range) = (if-range=1) + (else-range=1) + (expr=0) = 2
        } else if (System.currentTimeMillis() == 0) {
        } else {
        }
    }

    /** Inner */
    // NP = 0
    public InputNPathComplexityDefault2Constructors(int aParam)
    {
        Runnable runnable = new Runnable() {
            // NP = 2
            public void run() {
                // NP(while-statement) = (while-range=1) + (expr=0) + 1 = 2
                while (true) {
                }
            }
        };
        new Thread(runnable).start();
    }

    public void InputNestedTernaryCheck() {
        double x = (getSomething() || Math.random() == 5) ? null : (int) Math
                .cos(400 * (10 + 40)); // good
        double y = (0.2 == Math.random()) ? (0.3 == Math.random()) ? null : (int) Math
                .cos(400 * (10 + 40)) : 6; // bad (nested in first position)
        double z = (Integer) ((0.2 == Math.random()) ? (Integer) null + apply(null)
                : (0.3 == Math.random()) ? (Integer) null : (int) Math
                        .sin(300 * (12 + 30))); // bad (nested in second
                                                // position)
    }
    public boolean getSomething() { return true; };
    public int apply(Object o) { return 0; }

    public void inClass(int type, Short s, int color) {
        switch (type) {
        case 3:
            new Object() {
                public void anonymousMethod() {
                    {
                        switch (s) {
                        case 5:
                            switch (type) {
                            default:
                            }
                        }
                    }
                }
            };
        default:
            new Object() {
                class SwitchClass {
                    {
                        switch (color) {
                        case 5:
                            switch (type) {
                            default:
                            }
                        }
                    }
                }
            };
        }
    }
}
