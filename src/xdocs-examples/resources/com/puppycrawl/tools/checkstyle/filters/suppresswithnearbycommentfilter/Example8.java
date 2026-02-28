/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressWithNearbyCommentFilter">
      <property name="commentFormat" value="@cs-\: ([\w\|]+) influence (\d+)"/>
      <property name="checkFormat" value="$1"/>
      <property name="influenceFormat" value="$2"/>
    </module>
    <module name="ClassDataAbstractionCoupling">
      <property name="max" value="1" />
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

package com.puppycrawl.tools.checkstyle.filters.suppresswithnearbycommentfilter;
// xdoc section -- start
// @cs-: ClassDataAbstractionCoupling influence 2
// @cs-: MagicNumber influence 4
public class Example8 { // filtered violation 'Class Data Abstraction Coupling is 2'
  private Example1 foo = new Example1();
  private Example2 bar = new Example2();
  private int value = 10022; // filtered violation ''10022' is a magic number.'
}
// xdoc section -- end
