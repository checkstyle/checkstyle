/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodName"/>
    <module name="SuppressionXpathSingleFilter">
      <property name="files" value="Example(1|2|3)\.java"/>
      <property name="checks" value="MethodName"/>
      <property name="query" value="(//CLASS_DEF[./IDENT[@text='Example1']]
                /OBJBLOCK/METHOD_DEF/IDENT[@text='MyMethod'])|
                (//CLASS_DEF[./IDENT[@text='Example3']]/OBJBLOCK
                /METHOD_DEF/IDENT[@text='MyMethodA'])"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter;

// xdoc section -- start
public class Example1 {
  public void MyMethod() {} // filtered violation 'Name 'MyMethod'
                            // must match pattern '\^\[a-z\]\[a-zA-Z0-9\]\*\$'.'
}
// xdoc section -- end
