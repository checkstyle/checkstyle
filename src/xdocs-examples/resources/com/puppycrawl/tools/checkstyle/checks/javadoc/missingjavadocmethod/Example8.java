/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingJavadocMethod">
      <property name="ignoreMethodsWithImplementation" value="true"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;
// xdoc section -- start
public class Example8 {
  public void concreteMethod() {} // ok, has implementation

  public abstract class AbstractInner {
    public abstract void abstractMethod(); // violation, 'Missing a Javadoc comment'
    public void concreteInAbstract() {} // ok, has implementation
  }

  public interface InnerInterface {
    void interfaceMethod(); // violation, 'Missing a Javadoc comment'
  }
}
// xdoc section -- end
