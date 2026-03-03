/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Indentation">
      <property name="forceStrictCondition" value="true"/>
      <property name="braceAdjustment" value="0"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;

// xdoc section -- start
class Example3 {
    String field = "example";                  // basicOffset
    int[] values = {                           // basicOffset
        10,
        20,
        30
    };

    void processValues() throws Exception {
        handleValue("Test String", 42);
    }

    void handleValue(String aFooString,
                     int aFooInt) {      // violation, "incorrect indentation"

        boolean cond1,cond2,cond3,cond4,cond5,cond6;
        cond1=cond2=cond3=cond4=cond5=cond6=false;

        if (cond1
            || cond2) {
            field = field.toUpperCase()
                .concat(" TASK");
        }

        if ((cond1 && cond2)
                || (cond3 && cond4)        // violation, "incorrect indentation"
                || !(cond5 && cond6)) {    // violation, "incorrect indentation"
            field.toUpperCase()
                 .concat(" TASK")           // violation, "incorrect indentation"
                 .chars().forEach(c -> {    // violation, "incorrect indentation"
                     System.out.println((char) c);
                 });
        }
    }

    void demonstrateSwitch() throws Exception {
        switch (field) {
            case "EXAMPLE": processValues();                            // caseIndent
            case "COMPLETED": handleValue("Completed Case", 456);       // caseIndent
        }
    }
}


// xdoc section -- end
