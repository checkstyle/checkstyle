/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionXpathSingleFilter">
      <property name="files" value="FileOne.java"/>
      <property name="checks" value="MyMethod"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
public class FileOne {
  public void MyMethod() {} // OK
}

public class FileTwo {
  public void MyMethod() {} // violation, name 'MyMethod'
                            // must match pattern '^[a-z](_?[a-zA-Z0-9]+)*$'
}
// xdoc section -- end
