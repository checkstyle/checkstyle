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
  public void concreteMethod() {}

  public abstract class AbstractInner {
    public abstract void abstractMethod(); // violation
    public void concreteInAbstract() {}
  }

  public interface InnerInterface {
    void interfaceMethod(); // violation
  }
}
// xdoc section -- end
