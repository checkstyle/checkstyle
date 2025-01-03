/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="LambdaParameterName">
      <property name="format" value="^[a-z]([a-zA-Z]+)*$"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.lambdaparametername;

import java.util.function.Function;

import java.util.stream.Stream;

// xdoc section -- start
class Example2 {
  Function<String, String> function1 = str -> str.toUpperCase().trim();
  Function<String, String> function2 = _s -> _s.trim();
  // violation above 'Name '_s' must match pattern'

  public boolean myMethod(String sentence) {
    return Stream.of(sentence.split(" "))
            .map(word -> word.trim())
            .anyMatch(Word -> "in".equals(Word));
    // violation above 'Name 'Word' must match pattern'
  }
}
// xdoc section -- end
