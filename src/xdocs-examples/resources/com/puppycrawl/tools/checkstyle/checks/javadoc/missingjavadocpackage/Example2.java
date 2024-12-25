/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingJavadocPackage"/>
  </module>
</module>
*/
// xdoc section -- start
/*
 * Block comment is not a javadoc
 */
package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocpackage;

public class Example2 {
  String str="some content";
}
// xdoc section -- end
