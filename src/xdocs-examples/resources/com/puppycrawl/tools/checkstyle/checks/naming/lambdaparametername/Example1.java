/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="LambdaParameterName"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.lambdaparametername;

import java.util.function.Function;

// xdoc section -- start
class Example1 {
  Function<String, String> function1 = s -> s.toLowerCase();
  Function<String, String> function2 =
          S -> S.toLowerCase(); // violation, name 'S'
}
// xdoc section -- end
