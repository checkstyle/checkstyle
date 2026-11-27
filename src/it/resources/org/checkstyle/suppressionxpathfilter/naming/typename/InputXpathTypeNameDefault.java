/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TypeName"/>
  </module>
</module>
*/

package org.checkstyle.suppressionxpathfilter.naming.typename;

public class InputXpathTypeNameDefault {
    public interface FirstName {}
    protected class SecondName_ {} // warn
}
