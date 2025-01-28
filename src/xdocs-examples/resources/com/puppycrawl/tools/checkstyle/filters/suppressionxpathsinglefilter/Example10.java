/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="LeftCurly"/>
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value="LeftCurly"/>
      <property name="query" value="//CLASS_DEF[./IDENT[@text='Example10']]/OBJBLOCK
            /METHOD_DEF[./IDENT[@text='testMethod1']]/SLIST"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter;

// xdoc section -- start
public class Example10 {
  public void testMethod1()
  { // filtered violation ''{' at column 3 should be on the previous line.'
  }

  public void testMethod2()
  { // violation, ''{' at column 3 should be on the previous line.'
  }
}
// xdoc section -- end
