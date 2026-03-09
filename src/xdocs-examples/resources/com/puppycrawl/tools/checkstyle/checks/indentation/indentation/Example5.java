/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Indentation">
      <property name="basicOffset" value="2"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;

// xdoc section -- start
class Example5 {
    String field = "example";
    int[] values = {
        10,
        20,
        30
    };

    void processValues() throws Exception {
        handleValue("Test String", 42);
    }

    void handleValue(String aFooString,
                     int aFooInt) {

        boolean cond1, cond2;
        cond1 = cond2 = false;

        if (cond1
            || cond2) {
            field = field.toUpperCase();
        }
    }
}
// xdoc section -- end