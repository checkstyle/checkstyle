/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WhitespaceAround"/>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

// xdoc section -- start
class Example1 {
  public Example1(){} // 3 violations
  // no space after ')' and '{', no space before '}'
  public static void main(String[] args) {
    if (true) { }
    else{ // 2 violations
         // no space after 'else', no space before '}'
    }

    for (int i = 1; i > 1; i++) {} // 2 violations
    // no space after '{', no space before '}'

    Runnable noop = () ->{}; // 4 violations
    // no space after '->' and '{', no space before '{' and '}'
    try { }
    catch (Exception e){} // 3 violations
    // no space after ')' and '{', no space before '}'
    char[] vowels = {'a', 'e', 'i', 'o', 'u'};
    for (char item: vowels) { // ok, ignoreEnhancedForColon is true by default

    }

    int value = 0;
    // violation below: empty switch block is not allowed when
    // allowEmptySwitchBlockStatements is false (default behavior)
    switch (value) {}
  }
}
// xdoc section -- end
