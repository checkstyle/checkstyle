/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ArrayTrailingComma">
        <property name="alwaysDemandTrailingComma" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.arraytrailingcomma;

// xdoc section -- start
public class Example2 {
  int[] numbers = {1, 2, 3}; // violation 'Array should contain trailing comma.'
  boolean[] bools = {
    true,
    true,
    false // violation 'Array should contain trailing comma.'
    };

  String[][] text = {{},{},};

  double[][] decimals = {
    {0.5, 2.3, 1.1,},
    {1.7, 1.9, 0.6}, // violation 'Array should contain trailing comma.'
    {0.8, 7.4, 6.5,} // violation 'Array should contain trailing comma.'
  };

  char[] chars = {'a', 'b', 'c'  // violation 'Array should contain trailing comma.'
  };

  String[] letters = {
    "a", "b", "c"}; // violation 'Array should contain trailing comma.'

  int[] a1 = new int[]{
    1,
    2
    ,
  };

  int[] a2 = new int[]{
    1,
    2
  ,};
}
// xdoc section -- end






