/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocMethod">
        <property name="allowInlineReturn" value="true"/>
        <property name="validateThrows" value="false" default="true"/>
        <property name="allowedAnnotations" value="Override" default="true"/>
        <property name="accessModifiers"
                value="public, protected, package, private"
                default="true"/>
        <property name="allowMissingParamTags" value="false" default="true"/>
        <property name="allowMissingReturnTag" value="false" default="true"/>
        <property name="tokens"
                value="METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF"
                default="true"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

// xdoc section -- start
public class Example8 {

  /**
   * {@return the foo}
   */
  public int getFoo() { return 0; }

  /**
   * Returns the bar
   * @return the bar
   */
  public int getBar() { return 0; }

}
// xdoc section -- end
