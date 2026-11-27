/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TypeName">
      <property name="format" value="^I_[a-zA-Z0-9]*$"/>
      <property name="tokens" value="INTERFACE_DEF"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.suppressionxpathfilter.naming.typename;

public class InputXpathTypeNameInterfaceDef {

    public interface I_FirstName {}
    interface SecondName {} // warn

}
