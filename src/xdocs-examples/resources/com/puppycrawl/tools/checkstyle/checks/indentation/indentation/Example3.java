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
    @SuppressWarnings("che")
    String field = "example";   // basicOffset
    int[] values = {            // basicOffset
        10,                     // arrayInitIndent
        20,                     // arrayInitIndent
        30                      // arrayInitIndent
    };                          // arrayInitIndent

    void processValues() throws Exception {  // basicOffset
        handleValue("Test String", 42);       // basicOffset
    }

    void handleValue(String aFooString,
                     int aFooInt) {  // violation, "'int' has incorrect indentation level 21, expected level should be 8."
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
                || (cond3 && cond4) // violation,  "has incorrect indentation level 16, expected level should be 12."
                || !(cond5 && cond6)) { // violation, " has incorrect indentation level 16, expected level should be 12."
            field.toUpperCase()
                 .concat(" TASK") // violation, " has incorrect indentation level 17, expected level should be 16."
                 .chars().forEach(c -> { // violation, " has incorrect indentation level 17, expected level should be 16."
                     System.out.println((char) c);
                 });
        }
    }

    void demonstrateSwitch() throws Exception {  // basicOffset
        switch (field) {                         // basicOffset
            case "EXAMPLE": processValues();      // caseIndent
            case "COMPLETED": handleValue("Completed Case", 456);  // caseIndent
        }
    }
}

// xdoc section -- end
