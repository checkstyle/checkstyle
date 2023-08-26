/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value="RedundantModifier"/>
      <property name="query" value="//INTERFACE_DEF//*"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
public interface TestClass {
  public static final int CONSTANT1 = 1;  // OK
}
// xdoc section -- end
