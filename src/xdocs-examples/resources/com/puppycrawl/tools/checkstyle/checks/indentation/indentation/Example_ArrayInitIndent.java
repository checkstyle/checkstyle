/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Indentation">
      <property name="arrayInitIndent" value="4"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;

// xdoc section -- start
public class Example_ArrayInitIndent {

    // Example 1: Default arrayInitIndent (4) - correct
    int[] defaultArr = {
        1,
        2,
        3
    };

    // Example 2: Custom arrayInitIndent (e.g., 8)
    // If arrayInitIndent is set to 8, this would be correct:
    /*
    int[] customArr = {
            10,
            20
    };
    */

    // Example 3: Incorrect indentation - only 2 spaces instead of 4
    int[] wrongArr = {
      1, // violation, 'array initialization' child has incorrect indentation level 6, expected level should be 8
      2
    };

    void method() {
        int[] localArr = {
            10,
            20,
            30
        };
    }
}
// xdoc section -- end
