/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Regexp">
      <property
        name="format"
        value="(/\*\*\n)( \*.*\n)*( \* &lt;P&gt;\n \*   &lt;I&gt;
        This software is the confidential and proprietary information of\n
        \*   ACME \(&lt;B&gt;&quot;Confidential Information&quot;&lt;/B&gt;
        \)\. You shall not\n \*   disclose such Confidential Information
        and shall use it only in\n \*   accordance with the terms of the
        license agreement you entered into\n \*   with ACME\.&lt;/I&gt;\n
        \* &lt;/P&gt;\n \*\n \* &#169; copyright \d\d\d\d ACME\n
        \*\n \* @author .*)(\n\s\*.*)*
        /\n[\w|\s]*( class | interface )"/>
        <property name="message"
        value="Copyright in class/interface Javadoc"/>
      <property name="duplicateLimit" value="0"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.regexp.regexp;

public class Example14 {}

// xdoc section -- start
// xdoc section -- end


