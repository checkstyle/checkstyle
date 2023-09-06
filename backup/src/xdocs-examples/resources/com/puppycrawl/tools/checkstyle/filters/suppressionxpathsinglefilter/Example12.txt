/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value="IllegalThrows"/>
      <property name="query" value="//LITERAL_THROWS/IDENT[
          ..[@text='RuntimeException'] and ./ancestor::METHOD_DEF[@text='throwsMethod']]"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
public class InputTest {
  public void throwsMethod() throws RuntimeException { // violation will be suppressed
  }

  public void sampleMethod() throws RuntimeException { // will throw violation here
  }
}
// xdoc section -- end
