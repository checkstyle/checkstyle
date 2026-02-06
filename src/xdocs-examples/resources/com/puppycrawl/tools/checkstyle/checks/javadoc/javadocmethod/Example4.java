/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocMethod">
        <property name="allowMissingReturnTag" value="true"/>
        <property name="validateThrows" value="false" default="true"/>
        <property name="allowedAnnotations" value="Override" default="true"/>
        <property name="accessModifiers"
                value="public, protected, package, private"
                default="true"/>
        <property name="allowMissingParamTags" value="false" default="true"/>
        <property name="allowInlineReturn" value="false" default="true"/>
        <property name="tokens"
                value="METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF"
                default="true"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

// xdoc section -- start
public class Example4 {

  /** */
  Example4(int x) {}
  // violation above, 'Expected @param tag for 'x''
  /** */
  public int m1(int p1) { return p1; }
  // 1 violations above:
  //    'Expected @param tag for 'p1''
  // ok, No missing @return tag violation

  /**
   * @param p1 The first number
   */
  @Deprecated
  private int m2(int p1) { return p1; }
  // ok, No missing @return tag violation

  /** */
  void m3(int p1) {}
  // violation above, 'Expected @param tag for 'p1''
}
// xdoc section -- end
