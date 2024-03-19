/*xml
<module name="Checker">
    <property name="charset" value="UTF-8" />
    <property name="severity" value="warning" />
    <property name="haltOnException" value="false" />
    <module name="TreeWalker">
        <module name="UnusedLocalVariable">
        </module>
    </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

public class InputUnusedLocalVariableNestedClasses5 {
  int a = 12;

  void foo() {
    int a = 12; // violation, unused variable 'a'
    int ab = 12; // violation, unused variable 'ab'

    class abc {
      Test a = new Test() {
        void abc() {
          System.out.println(a);
          int abc = 10; // violation, unused variable 'abc'

          class def {
            Test abc = new Test() {
              void def() {
                System.out.println(abc);
              }
            };
          }
        }
      };
    }
  }
}
