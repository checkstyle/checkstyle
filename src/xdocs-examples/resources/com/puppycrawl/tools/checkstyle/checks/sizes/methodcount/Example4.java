/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodCount">
      <property name="maxPrivate" value="1"/>
      <property name="maxPackage" value="1"/>
      <property name="maxProtected" value="1"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.sizes.methodcount;

// xdoc section -- start
public class Example4 {

    private void privateMethod1() { }

    // violation below: private method count exceeds maxPrivate (1)
    private void privateMethod2() { }

    void packageMethod1() { }

    // violation below: package-private method count exceeds maxPackage (1)
    void packageMethod2() { }

    protected void protectedMethod1() { }

    // violation below: protected method count exceeds maxProtected (1)
    protected void protectedMethod2() { }
}
// xdoc section -- end
