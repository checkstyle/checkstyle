/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value="LocalFinalVariableName"/>
      <property name="query" value="//CLASS_DEF[@text='TestClass']/OBJBLOCK
            /METHOD_DEF[@text='testMethod']/SLIST
            /VARIABLE_DEF[@text='testVariable1']/IDENT"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
public class TestClass {
  public void testMethod() {
    final int testVariable1 = 10; // OK
    final int testVariable2 = 10; // violation of the LocalFinalVariableName check,
                                  // name 'testVariable2' must match pattern '^[A-Z][A-Z0-9]*$'
  }
}
// xdoc section -- end
