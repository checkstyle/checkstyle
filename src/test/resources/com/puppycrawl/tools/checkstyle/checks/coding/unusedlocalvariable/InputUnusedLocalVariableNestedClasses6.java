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

public class InputUnusedLocalVariableNestedClasses6 {
  void foo() {
    int a = 12; // violation, unused variable 'a'
    int ab = 12; // violation, unused variable 'ab'

    class Ghi {
      void foo() {
        int a = 12;
        int ab = 12; // violation, unused variable 'ab'
        System.out.println(a);
      }
    }

    class Def {
      void foo() {
        int a = 12; // violation, unused variable 'a'
        int ab = 12; // violation, unused variable 'ab'

        class InnerDef {
          void foo() {
            int a = 12; // violation, unused variable 'a'
            int ab = 12;
            System.out.println(ab);
          }
        }
      }
    }
  }
}
