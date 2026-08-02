/*xml
<module name="Checker">
  <module name=
      "com.puppycrawl.tools.checkstyle.filters.SuppressionSingleFilter">
    <property name="checks" value="WriteTag"/>
    <property name="message"
              value="^(Type|Method) Javadoc comment is missing @author tag\.$"/>
  </module>

  <module name="TreeWalker">
    <module name="WriteTag">
      <property name="tokens"
                value="INTERFACE_DEF, CLASS_DEF, ENUM_DEF,
                ANNOTATION_DEF, RECORD_DEF, METHOD_DEF, CTOR_DEF,
                ENUM_CONSTANT_DEF, ANNOTATION_FIELD_DEF,
                COMPACT_CTOR_DEF" />
      <property name="tag" value="@author"/>
      <property name="tagSeverity" value="error"/>
      <message key="javadoc.writeTag" value="No {0} tags should be used."/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

// xdoc section - start

// violation 4 lines below 'No @author tags should be used.'
/**
 * Some class.
 *
 * @author John Doe
 */
public class UseCase1 {

  /**
   * Helper without author tag.
   */
  void testMethod() {}
}
// xdoc section - end
