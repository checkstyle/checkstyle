/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WhitespaceAfter">
      <property name="tabWidth" value="8"/>
      <property name="tokens" value="COMMA, SEMI"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

class InputWhitespaceAfterTabWidthEight {
	int[] first = {1,2}; // violation "',' is not followed by whitespace."
 	int[] second = {1,2}; // violation "',' is not followed by whitespace."
		int[] third = {1,2}; // violation "',' is not followed by whitespace."
    	int[] fourth = {1,2}; // violation "',' is not followed by whitespace."
	int fifth;int sixth; // violation "';' is not followed by whitespace."
    int seventh;int eighth; // violation "';' is not followed by whitespace."
	int[] ninth = {1,2};	 // violation "',' is not followed by whitespace."
}
