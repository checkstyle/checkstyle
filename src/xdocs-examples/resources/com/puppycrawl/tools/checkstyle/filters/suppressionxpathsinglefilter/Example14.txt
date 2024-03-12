/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionXpathSingleFilter">
      <property name="files" value="[\\/]src[\\/]test[\\/]java[\\/]"/>
      <property name="checks" value="Javadoc*"/>
    </module>
    <module name="SuppressionXpathSingleFilter">
      <property name="files" value=".*Tests\.java"/>
      <property name="checks" value="Javadoc*"/>
    </module>
    <module name="SuppressionXpathSingleFilter">
      <property name="files" value="generated-sources"/>
      <property name="checks" value="[a-zA-Z0-9]*"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
// xdoc section -- end
