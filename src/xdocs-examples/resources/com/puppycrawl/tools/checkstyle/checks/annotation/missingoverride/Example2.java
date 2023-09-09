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

class SuperClass2{

  public void test(){

  }
}

interface Test2 {

  /** {@inheritDoc} */
  void test(); // violation, 'include @java.lang.Override'
}

class Test3 extends SuperClass2 {
  /** {@inheritDoc} */
  public void test() { // OK, is ignored because class extends other class

  }
}

class Test4 implements Test2 {
  /** {@inheritDoc} */
  public void test() { // OK, is ignored because class implements interface
  }
}

class Test5 {
  Runnable r = new Runnable() {

    /** {@inheritDoc} */
    public void run() { // OK, is ignored because class is anonymous class
    }
  };
}
// xdoc section -- end
