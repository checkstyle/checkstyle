/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Indentation"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;

// xdoc section -- start
class Example1 {
    int a;                      // ok, basicOffset = 4
    boolean x, y;
    String field = "example";
    int[] values = {
        1, 2, 3                 // ok, arrayInitIndent = 4
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
        int b = 0;              // ok, basicOffset = 4
        int c = 1;
    }

    void method2()
            throws Exception {  // ok, throwsIndent = 4
        switch (a) {
            case 1:             // ok, caseIndent = 4
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
            throws Exception {
        handleValue("Test String", 42);
    }

    void handleValue(String aFooString,
                     int aFooInt) {
        boolean cond1,cond2,cond3,cond4,cond5,cond6;
        cond1=cond2=cond3=cond4=cond5=cond6=false;
        if (cond1
                || cond2) {
            field = field.toUpperCase()
                    .concat(" TASK");
        }
        if ((cond1 && cond2)
                || (cond3 && cond4)
                || !(cond5 && cond6)) {
            field.toUpperCase()
                    .concat(" TASK")
                    .chars().forEach(c -> {
                        System.out.println((char) c);
                    });
        }
    }

    void demonstrateSwitch()
            throws Exception {
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
