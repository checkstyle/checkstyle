/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TypeName">
      <property name="format" value="^[a-z](_?[a-zA-Z0-9]+)*$"/>
      <property name="applyToPublic" value="false"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.suppressionxpathfilter.naming.typename;

public class InputXpathTypeNameAccessModifiers {

    public interface FirstName {}
    public class SecondName {
        protected class ThirdName {} // warn
    }
}
