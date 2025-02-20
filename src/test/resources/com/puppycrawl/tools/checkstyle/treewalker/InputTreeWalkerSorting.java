/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalCatchCheck">
      <property name="id" value="id1"/>
    </module>
    <module name="ConstantNameCheck">
      <property name="id" value="id2"/>
      <property name="format" value="^[A-Z][a-zA-Z0-9]*$"/>
    </module>
    <module name="WhitespaceAfterCheck">
      <property name="id" value="id3"/>
    </module>
    <module name="ParameterNameCheck">
      <property name="id" value="id4"/>
      <property name="format" value="^[a-z][a-zA-Z0-9]*$"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.treewalker;

public class InputTreeWalkerSorting {

    // ConstantNameCheck violation - should match all caps pattern
    private static final int someConstant = 42;

    // ParameterNameCheck violation - parameter should start with lowercase letter
    public void testCatch(int BADparam) {

        try {
            int x = 1 / 0;
        }

        // IllegalCatchCheck violation - catching generic Exception is disallowed
        // WhitespaceAfterCheck violation - 'catch' not followed by whitespace
        catch(Exception e) {
            System.out.println("Caught an exception");
        }
    }
}
