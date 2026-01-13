/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Indentation">
      <property name="caseIndent" value="0"/>
      <property name="throwsIndent" value="4"/>
      <property name="arrayInitIndent" value="4"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;

// xdoc section -- start
class Example2 {
    String field = "example";                  // basicOffset
    int[] values = {                           // basicOffset
        10,
        20,
        30
    };

    void processValues() throws Exception {
        handleValue("Test String", 42);          // basicOffset
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
                || (cond3 && cond4)           // ok, lineWrappingIndentation
                || !(cond5 && cond6)) {       // ok, lineWrappingIndentation
            field.toUpperCase()
                 .concat(" TASK")             // ok, lineWrappingIndentation
                 .chars().forEach(c -> {      // ok, lineWrappingIndentation
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



