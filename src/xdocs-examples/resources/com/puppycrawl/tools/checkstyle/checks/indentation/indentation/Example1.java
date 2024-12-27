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
    String field = "example"; // basicOffset

    int[] values = {           // basicOffset
        10,                     // arrayInitIndent
        20,                     // arrayInitIndent
        30                      // arrayInitIndent
    };                          // arrayInitIndent

    void processValues() throws Exception { // basicOffset
        handleValues();                  // basicOffset
    }                                     // braceAdjustment

    void handleValues() {                 // basicOffset
        boolean condition1 = true;        // defining conditions
        boolean condition2 = false;
        boolean condition3 = true;
        boolean condition4 = false;
        boolean condition5 = true;
        boolean condition6 = false;

        if (condition1 && condition2   // basicOffset
            || condition3 && condition4  // lineWrappingIndentation, forceStrictCondition
            || !(condition5 && condition6)) { // lineWrappingIndentation, forceStrictCondition
            field = field.toUpperCase()           // basicOffset
                .concat(" TASK")                  // lineWrappingIndentation, forceStrictCondition
                .replaceAll("TASK", "COMPLETED");  // lineWrappingIndentation, forceStrictCondition
        }
    }

    void demonstrateSwitch() throws Exception { // basicOffset
        switch (field) {                       // basicOffset
            case "EXAMPLE":
                processValues();               // caseIndent
                break;                          // caseIndent
            case "COMPLETED":
                handleValues();                // caseIndent
                break;                          // caseIndent
        }
    }
}
// xdoc section -- end
