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

class ParentClass2 {
  public void test(){}

}

// xdoc section -- start
class Example2 {
  /** {@inheritDoc} */
  // violation 2 lines below """Must include @java.lang.Override annotation when
  //  '@inheritDoc' Javadoc tag exists."""
  public boolean equals(Object o) {
    return o == this;
  }
}

interface B {

  /** {@inheritDoc} */
  // violation 2 lines below """Must include @java.lang.Override annotation when
  //  '@inheritDoc' Javadoc tag exists."""
  void test();
}

class C extends ParentClass2 {
  /** {@inheritDoc} */
  public void test() { // ok, is ignored because class extends other class

  }
}

class D implements B {
  /** {@inheritDoc} */
  public void test() { // ok, is ignored because class implements interface
  }
}

class E {
  Runnable r = new Runnable() {

    /** {@inheritDoc} */
    public void run() { // ok, is ignored because class is anonymous class
    }
  };
}
// xdoc section -- end
