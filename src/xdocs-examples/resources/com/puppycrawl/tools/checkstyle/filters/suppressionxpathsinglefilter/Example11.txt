/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value="RequireThis"/>
      <property name="query" value="//CLASS_DEF[@text='InputTest']
            //METHOD_DEF[@text='changeAge']//ASSIGN[@text='age']/IDENT"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
public class InputTest {
  private int age = 23;

  public void changeAge() {
    age = 24; // violation will be suppressed
  }
}
// xdoc section -- end
