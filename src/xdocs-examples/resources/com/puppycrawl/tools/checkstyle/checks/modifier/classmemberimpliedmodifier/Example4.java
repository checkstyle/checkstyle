/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ClassMemberImpliedModifier">
      <property name="violateImpliedStaticOnNestedRecord" value="false"/>
    </module>
  </module>
</module>
*/
// Java17
package com.puppycrawl.tools.checkstyle.checks.modifier.classmemberimpliedmodifier;

// xdoc section -- start
public final class Example4 {

  static interface Address1 {
  }

  interface Address2 {  // violation, 'Implied modifier 'static' should be explicit'
  }

  static enum Age1 {
    CHILD, ADULT
  }

  enum Age2 {  // violation, 'Implied modifier 'static' should be explicit'
    CHILD, ADULT
  }

  public static record GoodRecord() {}

  // ok, static modifier is not required due to configuration
  public record BadRecord() {}

  public static record OuterRecord() {
    static record InnerRecord1(){}

    // ok, static modifier is not required due to configuration
    record InnerRecord2(){}
  }
}
// xdoc section -- end

