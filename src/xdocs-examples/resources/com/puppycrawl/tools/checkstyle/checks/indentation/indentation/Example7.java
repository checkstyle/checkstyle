/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Indentation">
      <property name="throwsIndent" value="8"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;

// xdoc section -- start
class Example7 {
    int a;
    boolean x, y;
    String field = "example";                  // basicOffset
    int[] values = {
        1, 2, 3
    };
    int[] values1 = {                          // basicOffset
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
                 int b) {
        if (x
                && y) {
            method3(a, b);
        }
    }

    void method4(int a,
                 int b) {
        if (x
                && y) {
            method4(a, b);
        }
    }

    void processValues()
        throws Exception { // violation, ''throws' has incorrect indentation'
        handleValue("Test String", 42);          // basicOffset
    }

    void handleValue(String aFooString,
                     int aFooInt) {             // indent:8 ; expected: > 4;

        boolean cond1,cond2,cond3,cond4,cond5,cond6;
        cond1=cond2=cond3=cond4=cond5=cond6=false;

        if (cond1
            || cond2) {
            field = field.toUpperCase()
                .concat(" TASK");
        }

        if ((cond1 && cond2)
                || (cond3 && cond4)          // ok, lineWrappingIndentation
                || !(cond5 && cond6)) {      // ok, lineWrappingIndentation
            field.toUpperCase()
                 .concat(" TASK")             // ok, lineWrappingIndentation
                 .chars().forEach(c -> {      // ok, lineWrappingIndentation
                     System.out.println((char) c);
                 });
        }
    }

    void demonstrateSwitch()
            throws Exception {               // ok, throwsIndent is 8
        switch (field) {
            case "EXAMPLE": processValues();                        // caseIndent
            case "COMPLETED": handleValue("Completed Case", 456);   // caseIndent
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
