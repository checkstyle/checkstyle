/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value=".*"/>
      <property name="query" value="//METHOD_DEF[@text='TestMethod1']
            /descendant-or-self::node()"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
public class TestClass {
  public void TestMethod1() { // OK
    final int num = 10; // OK
  }

  public void TestMethod2() { // violation of the MethodName check,
                              // name 'TestMethod2' must match pattern '^[a-z](_?[a-zA-Z0-9]+)*$'
    final int num = 10; // violation of the LocalFinalVariableName check,
                        // name 'num' must match pattern '^[A-Z][A-Z0-9]*$'
  }
}
// xdoc section -- end
