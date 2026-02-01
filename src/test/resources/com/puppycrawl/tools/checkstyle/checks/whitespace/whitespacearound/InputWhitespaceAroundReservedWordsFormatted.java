/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WhitespaceAround">
      <property name="tokens"
        value="LITERAL_IF, LITERAL_ELSE, LITERAL_FOR, LITERAL_WHILE"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

public class InputWhitespaceAroundReservedWordsFormatted {

    void testIf(int x) {
        if (x > 0) {
            x++;
        } else {
            x--;
        }
    }

    void testFor() {
        for (int i = 0; i < 1; i++) {
            System.out.println(i);
        }
    }

    void testWhile() {
        while (true) {
            break;
        }
    }
}
