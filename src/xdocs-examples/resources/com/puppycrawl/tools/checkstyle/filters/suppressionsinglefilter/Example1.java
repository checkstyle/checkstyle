/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocStyle"/>
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.MagicNumberCheck">
      <property name="ignoreNumbers" value="-1, 0, 1, 2"/>
      <property name="ignoreHashCodeMethod" value="false"/>
      <property name="ignoreAnnotation" value="false"/>
      <property name="ignoreFieldDeclaration" value="false"/>
      <property name="ignoreAnnotationElementDefaults" value="true"/>
      <property name="constantWaiverParentToken"
                value="TYPECAST, METHOD_CALL, EXPR, ARRAY_INIT, UNARY_MINUS,
                       UNARY_PLUS, ELIST, STAR, ASSIGN, PLUS_ASSIGN, MINUS_ASSIGN,
                       STAR_ASSIGN, DIV_ASSIGN, MOD_ASSIGN, SR_ASSIGN, BSR_ASSIGN,
                       SL_ASSIGN, BAND_ASSIGN, BXOR_ASSIGN, BOR_ASSIGN, LOR, LAND,
                       BOR, BXOR, BAND, NOT_EQUAL, EQUAL, LT, GT, LE, GE, SL, SR,
                       BSR, PLUS, MINUS, DIV, MOD, INC, DEC, POST_INC, POST_DEC,
                       LITERAL_RETURN, ARRAY_DECLARATOR"/>
      <property name="tokens" value="NUM_DOUBLE, NUM_FLOAT, NUM_INT, NUM_LONG"/>
    </module>
  </module>
  <module name="SuppressionSingleFilter">
    <property name="checks" value="JavadocStyle|MagicNumber"/>
    <property name="files" value="Example1.java"/>
    <property name="lines" value="1,5-100"/>
  </module>
  <module name="SuppressionSingleFilter">
    <property name="message" value="Missing a Javadoc comment"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example1 {
  public void exampleMethod() {
    int value = 100; // filtered violation ''100' is a magic number'
  }
}
// xdoc section -- end
