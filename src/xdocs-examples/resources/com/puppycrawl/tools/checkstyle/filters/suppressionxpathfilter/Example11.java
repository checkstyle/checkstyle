/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionXpathFilter">
      <property name="file" value="${config.folder}/suppressions11.xml"/>
    </module>
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
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathfilter;

// xdoc section -- start
public class Example11 {
  private int wordCount = 11; // filtered violation "'11' is a magic number."
}
// xdoc section -- end
