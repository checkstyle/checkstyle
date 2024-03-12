/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value=".*"/>
      <property name="query"
                value="(//CLASS_DEF[@text='FileOne'])|
                (//CLASS_DEF[@text='FileOne']/OBJBLOCK/METHOD_DEF[@text='MyMethod']/IDENT)"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
abstract class FileOne { // OK
  public void MyMethod() {} // OK
}

abstract class FileTwo { // violation of the AbstractClassName check,
                         // it should match the pattern "^Abstract.+$"
  public void MyMethod() {} // violation, name 'MyMethod'
                            // must match pattern '^[a-z](_?[a-zA-Z0-9]+)*$'
}
// xdoc section -- end
