/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalCatch">
      <property name="id" value="id2"/>
    </module>
    <module name="ConstantName">
      <property name="id" value="id1"/>
      <property name="format" value="^[A-Z][a-zA-Z0-9]*$"/>
    </module>
    <module name="WhitespaceAfter">
      <property name="id" value="id4"/>
    </module>
    <module name="ParameterName">
      <property name="id" value="id3"/>
      <property name="format" value="^[a-z][a-zA-Z0-9]*$"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.treewalker;

public class InputTreeWalkerSorting {

    // violation below, 'Name 'someConstant' must match pattern'
    private static final int someConstant = 42;

    // violation below, 'Name 'BADparam' must match pattern'
    public void testCatch(int BADparam) {

        try {
            int x = 1 / 0;
        }

        // violation below, 'Catching 'Exception' is not allowed'
        catch(Exception e) {// violation, ''catch' is not followed by whitespace'
            System.out.println("Caught an exception");
        }
    }
}
