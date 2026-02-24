/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavaLineLength">
      <property name="max" value="80"/>
      <property name="tokens" value="TEXT_BLOCK_LITERAL_BEGIN, METHOD_DEF"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.sizes.javalinelength;

// xdoc section -- start
public class Example2 {

    String s1 = """
            This string is greater than 80 characters but no violations will be raised.
            """;

    String s2 = "This string is greater than 80 characters but violations will be raised";
    // violation above, 'Line is longer than 80 characters'

    public void methodWhoseCharactersExceed80Characters (String s1, String s2, String s3) {

        String s4 = """
                This string is greater than 80 characters but no violations will be raised.
                """;
    }
}
// xdoc section -- end
