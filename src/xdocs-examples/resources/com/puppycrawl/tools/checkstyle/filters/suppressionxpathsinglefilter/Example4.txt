/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value="PackageName"/>
      <property name="query" value="/PACKAGE_DEF[@text='File']/IDENT"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
package File; // OK

public class FileOne {}
// xdoc section -- end
