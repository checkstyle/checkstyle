/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ClassMemberImpliedModifier">
      <property name="violateImpliedStaticOnNestedEnum" value="false"/>
    </module>
  </module>
</module>
*/
// Java17
package com.puppycrawl.tools.checkstyle.checks.modifier.classmemberimpliedmodifier;

// xdoc section -- start
public final class Example3 {

  static interface Address1 {
  }

  interface Address2 {  // violation, 'Implied modifier 'static' should be explicit'
  }

  static enum Age1 {
    CHILD, ADULT
  }

  enum Age2 {  // ok, implied static is not enforced for nested enums
    CHILD, ADULT
  }

  public static record GoodRecord() {}

  // violation below, 'Implied modifier 'static' should be explicit'
  public record BadRecord() {}

  public static record OuterRecord() {
    static record InnerRecord1(){}
    // violation below, 'Implied modifier 'static' should be explicit'
    record InnerRecord2(){}
  }
}
// xdoc section -- end

