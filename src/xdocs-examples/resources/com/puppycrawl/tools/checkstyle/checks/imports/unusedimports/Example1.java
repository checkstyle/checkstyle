/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UnusedImports"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

// xdoc section -- start
// limitation as it match field name in code
import java.awt.Component;

// no ability to recognize what import is not used
import static java.util.Map.copyOf;
import static java.util.Arrays.copyOf;

import java.lang.String; // violation 'Unused import - java.lang.String.'

import java.util.Stack;
import java.util.Map;   // violation 'Unused import - java.util.Map.'

import java.util.List;
import java.util.function.Function;

import static java.lang.Integer.parseInt; // violation 'Unused import - java.lang.Integer.parseInt.'

/**
* {@link List}
*/
class Example1{
  Stack stack = new Stack();
  private Object Component;
  int[] arr = {0,0};
  int[] array = copyOf(arr , 1);
  Function <String, Integer> stringToInteger = Integer::parseInt;
}
// xdoc section -- end
