package org.checkstyle.suppressionxpathfilter.metrics.cyclomaticcomplexity;

public class InputXpathCyclomaticComplexityInnerClassLoops {
    class Inner {
        public void loops(int n) { //warn
            for (int i = 0; i < n; i++) {
                if (i % 2 == 0) {
                    // do something
                }
            }
        }
    }
}
