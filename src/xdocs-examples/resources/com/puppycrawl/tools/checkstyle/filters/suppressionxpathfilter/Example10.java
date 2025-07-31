/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionXpathFilter">
      <property name="file" value="${config.folder}/suppressions10.xml"/>
    </module>
    <module name="ModifierOrder"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathfilter;

// xdoc section -- start
public class Example10 {

  final public void legacyMethod() { // filtered violation 'modifier out of order'
    strictfp abstract class legacyClass {}
    // filtered violation above 'modifier out of order'
  }

  public void otherMethod() {
    strictfp abstract class strangeClass {} // violation, 'modifier out of order'
  }

}
// xdoc section -- end
