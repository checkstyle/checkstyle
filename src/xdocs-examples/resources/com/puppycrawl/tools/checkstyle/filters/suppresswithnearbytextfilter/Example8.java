/*xml
<module name="Checker">
  <module name="SuppressWithNearbyTextFilter">
    <property name="nearbyTextPattern"
      value="@cs-: (\w+) for ([+-]\d+) lines"/>
    <property name="checkPattern" value="$1"/>
    <property name="lineRange" value="$2"/>
  </module>
  <module name="TreeWalker">
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
package com.puppycrawl.tools.checkstyle.filters.suppresswithnearbytextfilter;
// xdoc section -- start
public class Example8 {
  // filtered violation below ''42' is a magic number'
  int a = 42; // @cs-: MagicNumber for +3 lines
  int b = 43; // filtered violation ''43' is a magic number'
  int c = 44; // filtered violation ''44' is a magic number'
  int d = 45; // filtered violation ''45' is a magic number'
  int e = 46; // violation "'46' is a magic number."
}
// xdoc section -- end
