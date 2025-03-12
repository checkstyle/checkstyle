/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuperClone"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.superclone;

// xdoc section -- start
class Example1 {
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}

class SuperCloneB {
  private int b;


  public SuperCloneB clone() { // violation "Overriding clone() method must invoke super.clone() to ensure proper finalization."
    SuperCloneB other = new SuperCloneB();
    other.b = this.b;
    return other;
  }
}

class SuperCloneC {

  public SuperCloneC clone() throws CloneNotSupportedException {
    return (SuperCloneC) super.clone();
  }
}
// xdoc section -- end
