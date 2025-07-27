/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionXpathFilter">
      <property name="file" value="${config.folder}/suppressions7.xml"/>
    </module>
    <module name="MagicNumber"/>
    <module name="MethodName"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathfilter;

// xdoc section -- start
public class Example7 {
  public void DoMATH() {} // filtered violation "Name 'DoMATH' must match pattern"
  public void DoEng() {} // violation, "Name 'DoEng' must match pattern"
}

class Main {
  int someField = 11; // violation, "'11' is a magic number."
  void FOO() {} // filtered violation "Name 'FOO' must match pattern"
}
// xdoc section -- end
