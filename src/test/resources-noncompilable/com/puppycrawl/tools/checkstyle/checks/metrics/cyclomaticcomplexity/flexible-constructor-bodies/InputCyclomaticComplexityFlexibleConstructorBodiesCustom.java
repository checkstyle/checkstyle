/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="CyclomaticComplexity">
      <property name="max" value="5"/>
      <property name="switchBlockAsSingleDecisionPoint" value="true"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.metrics.cyclomaticcomplexity;

class InputCyclomaticComplexityFlexibleConstructorBodiesCustom {

    static class Before {
        // violation below 'Cyclomatic Complexity is 7 (max allowed is 5).'
        Before(int input, boolean first, boolean second, boolean third, boolean fourth,
                boolean fifth) {
            switch (input) {
                case 1:
                    System.out.println("one");
                    break;
                case 2:
                    System.out.println("two");
                    break;
                case 3:
                    System.out.println("three");
                    break;
                default:
                    System.out.println("other");
                    break;
            }
            if (first && second && third && fourth && fifth) {
                System.out.println("all true");
            }
            super();
        }
    }

    static class After {
        After(boolean first, boolean second, boolean third) {
            super();
            if (first && second && third) {
                System.out.println("all true");
            }
        }
    }
}
