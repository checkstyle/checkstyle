/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UnusedImports">
      <property name="processJavadoc" value="false"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

// xdoc section -- start
// limitation as it match field name in code
import java.awt.Component; //OK

// no ability to recognize what import is not used
import static java.util.Map.copyOf; //OK
import static java.util.Arrays.copyOf; //OK

import java.lang.String; // violation

import java.util.Stack;
import java.util.Map;   // violation

import java.util.List; // violation

import java.util.function.Function;

import static java.lang.Integer.parseInt; // violation

/**
* @link List
*/
class Example2{
  Stack stack = new Stack();
  private Object Component;
  int[] arr = {0,0};
  int[] array = copyOf(arr , 1);
 Function <String, Integer> stringToInteger = Integer::parseInt;
}
// xdoc section -- end
