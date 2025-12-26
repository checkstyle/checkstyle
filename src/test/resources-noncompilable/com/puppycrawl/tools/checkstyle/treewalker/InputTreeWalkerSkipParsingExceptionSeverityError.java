/*xml
<module name="Checker">
  <module name="TreeWalker">
    <property name="skipFileOnJavaParseException" value="true"/>
    <property name="javaParseExceptionSeverity" value="error"/>
    <module name="ConstantName"/>
  </module>
</module>
*/

// violation 10 lines above
// non-compiled syntax: bad file for testing
public clazz InputTreeWalkerSkipParsingExceptionSeverityError { }
