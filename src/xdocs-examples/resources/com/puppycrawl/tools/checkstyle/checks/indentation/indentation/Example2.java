/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Indentation">
      <property name="caseIndent" value="0"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;

// xdoc section -- start
class Example2 {
    String field = "example";   // basicOffset
    int[] values = {            // basicOffset
        10,                     // arrayInitIndent
        20,                     // arrayInitIndent
        30                      // arrayInitIndent
    };                          // arrayInitIndent

    void processValues() throws Exception // basicOffset
    {                                     // braceAdjustment
        handleValues();                   // basicOffset
    }                                     // braceAdjustment

    void handleValues() {                 // basicOffset
        boolean condition1 = true;        // defining conditions
        boolean condition2 = false;
        boolean condition3 = true;
        boolean condition4 = false;
        boolean condition5 = true;
        boolean condition6 = false;

        if (condition1 && condition2    // basicOffset
                  || condition3 && condition4
                  ||!(condition5 && condition6)) {
            field = field.toUpperCase()           // basicOffset
                .concat(" TASK")
                .replaceAll("TASK", "COMPLETED");
        }
    }

    void demonstrateSwitch() throws Exception {    // basicOffset
        switch (field) {// basicOffset
        case "EXAMPLE": processValues(); // caseIndent
        }
    }

}
// xdoc section -- end



