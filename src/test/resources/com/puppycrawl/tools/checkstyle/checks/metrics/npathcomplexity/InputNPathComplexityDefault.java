package com.puppycrawl.tools.checkstyle.checks.metrics.npathcomplexity;

public class InputNPathComplexityDefault {
    // NP = 2
    public void foo() {
        //NP(while-statement) = (while-range=1) + (expr=0) + 1 = 2
        while (true) {
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
    }

    // NP = 10
    public void bar() {
        // NP = (if-range=3*3) + (expr=0) + 1 = 10
        if (System.currentTimeMillis() == 0) {
            //NP = (if-range=1) + 1 + (expr=1) = 3
            if (System.currentTimeMillis() == 0 && System.currentTimeMillis() == 0) {
            }
            //NP = (if-range=1) + 1 + (expr=1) = 3
            if (System.currentTimeMillis() == 0 || System.currentTimeMillis() == 0) {
            }
        }
    }

    // NP = 3
    public void simpleElseIf() {
        // NP = (if-range=1) + (else-range=2) + 0 = 3
        if (System.currentTimeMillis() == 0) {
        // NP(else-range) = (if-range=1) + (else-range=1) + (expr=0) = 2
        } else if (System.currentTimeMillis() == 0) {
        } else {
        }
    }

    // NP = 7
    public void stupidElseIf() {
        // NP = (if-range=1) + (else-range=3*2) + (expr=0) = 7
        if (System.currentTimeMillis() == 0) {
        } else {
            // NP = (if-range=1) + (else-range=2) + (expr=0) = 3
            if (System.currentTimeMillis() == 0) {
            } else {
                // NP = (if-range=1) + 1 + (expr=0) = 2
                if (System.currentTimeMillis() == 0) {
                }
            }
            // NP = (if-range=1) + 1 + (expr=0) = 2
            if (System.currentTimeMillis() == 0) {
            }
        }
    }

    // NP = 3
    public InputNPathComplexityDefault()
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
    public InputNPathComplexityDefault(int aParam)
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
        double x = (getSmth() || Math.random() == 5) ? null : (int) Math
                .cos(400 * (10 + 40)); // good
        double y = (0.2 == Math.random()) ? (0.3 == Math.random()) ? null : (int) Math
                .cos(400 * (10 + 40)) : 6; // bad (nested in first position)
        double z = (Integer) ((0.2 == Math.random()) ? (Integer) null + apply(null)
                : (0.3 == Math.random()) ? (Integer) null : (int) Math
                        .sin(300 * (12 + 30))); // bad (nested in second
                                                // position)
    }
    public boolean getSmth() { return true; }; 
    public int apply(Object o) { return 0; }
}
