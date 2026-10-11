/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck">
      <property name="format" value="^[a-z][a-zA-Z0-9]*$"/>
      <message key="name.invalidPattern"
               value="Test XML encoding: &lt;tag&gt; &amp; &quot;quotes&quot; &amp; 'apos'; Existing entities: &amp; &lt;"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.xmllogger;

public class InputXMLLoggerEncodedMessage {
    private int XML_Chars;
}
