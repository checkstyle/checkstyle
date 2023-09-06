/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value="LeftCurly"/>
      <property name="query" value="//CLASS_DEF[@text='TestClass']/OBJBLOCK
            /METHOD_DEF[@text='testMethod1']/SLIST"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
public class TestClass {
  public void testMethod1()
  { // OK
  }

  public void testMethod2()
  { // violation, '{' should be on the previous line
  }
}
// xdoc section -- end
