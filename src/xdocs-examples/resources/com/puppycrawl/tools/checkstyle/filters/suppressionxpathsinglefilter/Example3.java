/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodName">
      <property name="id" value="MethodName1"/>
      <property name="format" value="^[a-z](_?[a-zA-Z0-9]+)*$"/>
    </module>
    <module name="SuppressionXpathSingleFilter">
      <property name="files" value="FileOne.java"/>
      <property name="id" value="MethodName1"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
public class FileOne {
  public void MyMethod() {} // OK
}
public class FileTwo {
  public void MyMethod() {} // violation,  name 'MyMethod' must
                            //match pattern '^[a-z](_?[a-zA-Z0-9]+)*$'
}
// xdoc section -- end
