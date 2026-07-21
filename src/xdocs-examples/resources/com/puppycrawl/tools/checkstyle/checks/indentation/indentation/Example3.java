/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Indentation">
      <property name="forceStrictCondition" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;

// xdoc section -- start
class Example3 {
    int a;
    boolean x, y;
    String field = "example";
    int[] values = {
        1, 2, 3
    };
    int[] values2 = {
  10 // violation, 'level 2, expected level should be 8'
    };

    void method2()
        throws Exception {
        switch (a) {
            case 1:             // ok, caseIndent = 4
                break;
            case 2:
                break;
        }
    }
    void method3(int a,
                 int b) {       // violation, 'level 17, expected level should be 8'
        if (x
                && y) {         // violation, 'level 16, expected level should be 12'
            method3(a, b);
        }
    }
    // violation 2 lines below 'level 21, expected level should be 8'
    void handleValue(String aFooString,
                     int aFooInt) {

        boolean cond1,cond2,cond3,cond4,cond5,cond6;

        cond1=cond2=cond3=cond4=cond5=cond6=false;
        if (cond1
                || cond2) { // violation, 'level 16, expected level should be 12'
            field = field.toUpperCase()
                    .concat(" TASK");
        }
    }

    void methodBrace()
    {
    }
}
// xdoc section -- end
