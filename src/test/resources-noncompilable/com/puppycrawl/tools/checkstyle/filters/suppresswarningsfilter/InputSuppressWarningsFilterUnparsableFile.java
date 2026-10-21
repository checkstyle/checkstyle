/*xml
<module name="Checker">
  <module name="SuppressWarningsFilter"/>
  <module name="RegexpSingleline">
    <property name="format" value="forbidde[n]"/>
  </module>
  <module name="TreeWalker">
    <property name="skipFileOnJavaParseException" value="true"/>
    <module name="SuppressWarningsHolder"/>
  </module>
</module>
*/

// violation first line 'occurred while parsing file'
// non-compiled syntax: bad file for testing
// lines below are within the range of @SuppressWarnings("all")
// of InputSuppressWarningsFilterSuppressAll.java,
// but that suppression must not be applied to this file
public clazz InputSuppressWarningsFilterUnparsableFile {
    int forbidden; // violation 'Line matches the illegal pattern'
}
