/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value="MethodName"/>
      <property name="query" value="//CLASS_DEF[@text='FileOne']/OBJBLOCK/
                METHOD_DEF[@text='MyMethod1' or @text='MyMethod2']/IDENT"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
public class FileOne {
  public void MyMethod1() {} // OK
  public void MyMethod2() {} // OK
  public void MyMethod3() {} // violation, name 'MyMethod3' must
                             // match pattern '^[a-z](_?[a-zA-Z0-9]+)*$'
}
// xdoc section -- end
