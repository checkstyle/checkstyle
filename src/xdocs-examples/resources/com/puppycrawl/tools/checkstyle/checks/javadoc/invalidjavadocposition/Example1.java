/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="InvalidJavadocPosition"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.invalidjavadocposition;

// xdoc section -- start
@SuppressWarnings("serial")
// violation below, 'Javadoc comment is placed in the wrong location'
/**
 * This comment looks like Javadoc but it is at an invalid location.
 * Therefore, the text will not get into TestClass.html.
 * And, the check will produce a violation.
 */
public class Example1 {
}
// xdoc section -- end
