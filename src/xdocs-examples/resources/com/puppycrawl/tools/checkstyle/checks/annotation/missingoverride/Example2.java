/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingOverride">
      <property name="javaFiveCompatibility"
                value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverride;

// xdoc section -- start
class Example2 {
  /** {@inheritDoc} */
  public boolean equals(Object o) { // violation, 'include @java.lang.Override'
    return o == this;
  }
}

class SuperClass{

  public void test(){

  }
}

interface B {

  /** {@inheritDoc} */
  void test(); // violation, 'include @java.lang.Override'
}

class C extends SuperClass {
  /** {@inheritDoc} */
  public void test() { // OK, is ignored because class extends other class

  }
}

class D implements B {
  /** {@inheritDoc} */
  public void test() { // OK, is ignored because class implements interface
  }
}

class E {
  Runnable r = new Runnable() {

    /** {@inheritDoc} */
    public void run() { // OK, is ignored because class is anonymous class
    }
  };
}
// xdoc section -- end
