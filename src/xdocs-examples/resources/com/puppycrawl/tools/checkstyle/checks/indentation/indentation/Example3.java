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
    int[] values1 = {
        10,
        20,
        30
    };
    int[] values2 = {
        10
    };

    void method() {
        int b = 0;
        int c = 1;
    }

    void method2()
        throws Exception {
        switch (a) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }

    void method3(int a,
        int b) {            // ok, lineWrappingIndentation=4 from method
        if (x
            && y) {         // ok, lineWrappingIndentation=4 from 'if'
            method3(a, b);
        }
    }

    void method4(int a,
                 int b) {   // violation, 'level 17, expected level should be 8'
        if (x
                && y) {     // violation, 'level 16, expected level should be 12'
            method4(a, b);
        }
    }

    void processValues()
        throws Exception {
        handleValue("Test String", 42);
    }

    void handleValue(String aFooString,
                     int aFooInt) { // violation, 'level 21, expected level should be 8'
        boolean cond1,cond2,cond3,cond4,cond5,cond6;
        cond1=cond2=cond3=cond4=cond5=cond6=false;
        if (cond1
                || cond2) { // violation, 'level 16, expected level should be 12'
            field = field.toUpperCase()
                    .concat(" TASK");
        }
        if ((cond1 && cond2)
                || (cond3 && cond4) // violation, 'level 16, expected level should be 12'
                || !(cond5 && cond6)) { // violation, 'level 16, expected level should be 12'
            field.toUpperCase()
                    .concat(" TASK") // violation, 'level 20, expected level should be 16'
                    .chars().forEach(c -> { // violation, 'level 20, expected level should be 16'
                        System.out.println((char) c);
                    });
        }
    }

    void demonstrateSwitch()
            throws Exception { // violation, 'level 12, expected level should be 8'
        switch (field) {
            case "EXAMPLE": processValues();
            case "COMPLETED": handleValue("Completed Case", 456);
        }
    }

    void methodBrace()
    {
    }

    void methodBrace2()
    {
    }
}
// xdoc section -- end
