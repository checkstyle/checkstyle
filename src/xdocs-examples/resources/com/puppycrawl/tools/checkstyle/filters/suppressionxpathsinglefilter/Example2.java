/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value="MethodName"/>
      <property name="message" value="MyMethod[0-9]"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
public class FileOne {
  public void MyMethod1() {} // OK
  public void MyMethod2() {} // OK
  public void MyMethodA() {} // violation, name 'MyMethodA' must
                             // match pattern '^[a-z](_?[a-zA-Z0-9]+)*$'
}
// xdoc section -- end
