/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Indentation"/>
  </module>
</module>

*/package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;

// xdoc section -- start
class Example4 {
    String field = "example";   // basicOffset
    int[] values = {            // basicOffset
        10,                     // arrayInitIndent
        20,                     // arrayInitIndent
        30                      // arrayInitIndent
    };                          // arrayInitIndent

    void processValues() throws Exception // basicOffset
    {                                     // braceAdjustment
        handleValue("Test String", 42);                    // basicOffset
    }                                     // braceAdjustment

    void handleValue(String aFooString,
                     int aFooInt) { // indent:8 ; expected: > 4; ok, because 8 > 4
        boolean cond1 = true; // defining conditions
        boolean cond2 = false;
        boolean cond3 = true;
        boolean cond4 = false;
        boolean cond5 = true;
        boolean cond6 = false;

        if (cond1
            || cond2) {
            field = field.toUpperCase()
                .concat(" TASK");
        }

        if ((cond1 && cond2)
              || (cond3 && cond4)
              || !(cond5 && cond6)) {
            field = field.toUpperCase()
                 .concat(" TASK")
                 .replaceAll("TASK", "COMPLETED")
                 .repeat(1);
        }
    }

    void demonstrateSwitch()               // basicOffset
        throws Exception {                 // throwsIndent
        switch (field) {                   // basicOffset
            case "EXAMPLE": processValues(); // caseIndent
            case "COMPLETED": handleValue("Completed Case", 456); // caseIndent
        }
    }

}
// xdoc section -- end
