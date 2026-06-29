/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavaLineLength"/>
      <property name="max" value="100"/>
      <property name="tokens" value="TEXT_BLOCK_LITERAL_BEGIN"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.sizes.javalinelength;

// xdoc section -- start
public class Example1 {

  String s1 = """
            This string is a really long word that exceeds default line limit but will not raise violation.
            """;

  String s2 = "This string is a really long word that exceeds default line limit but will raise violation";
  // violation above, 'Line is longer than 100 characters (found 107).'
}
// xdoc section -- end
