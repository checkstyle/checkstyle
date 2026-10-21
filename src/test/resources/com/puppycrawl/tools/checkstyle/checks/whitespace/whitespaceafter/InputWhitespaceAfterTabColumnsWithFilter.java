/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WhitespaceAfter">
      <property name="tabWidth" value="4"/>
      <property name="tokens" value="COMMA"/>
    </module>
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value="WhitespaceAfter"/>
      <property name="query" value="//VARIABLE_DEF[./IDENT[@text='first']]//COMMA"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

class InputWhitespaceAfterTabColumnsWithFilter {
	int[] first = {1,2}; // filtered violation "',' is not followed by whitespace."
	int[] second = {1,2}; // violation "',' is not followed by whitespace."
}
