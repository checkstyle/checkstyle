/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Regexp">
      <property
        name="format"
        value="\A/\*\n \* (\w*)\.java\n \*\n \* Copyright \(c\)
        \d\d\d\d ACME\n \* 123 Some St\.\n \* Somewhere\.\n \*\n
        \* This software is the confidential and proprietary information
        of ACME\.\n \* \(&quot;Confidential Information&quot;\)\. You
        shall not disclose such\n \* Confidential Information and shall
        use it only in accordance with\n \* the terms of the license
        agreement you entered into with ACME\.\n \*\n
        \* \$Log: config_misc\.xml,v $
        \* Revision 1\.7  2007/01/16 12:16:35  oburn
        \* Removing all reference to mailing lists
        \* \
        \* Revision 1.6  2005/12/25 16:13:10  o_sukhodolsky
        \* Fix for rfe 1248106 \(TYPECAST is now accepted by NoWhitespaceAfter\)
        \* \
        \* Fix for rfe 953266 \(thanks to Paul Guyot \(pguyot\) for submitting
         patch\)
        \* IllegalType can be configured to accept some abstract classes which
        \* matches to regexp of illegal type names \(property
         legalAbstractClassNames\)
        \*
        \* TrailingComment now can be configured to accept some trailing comments
        \* \(such as NOI18N\) \(property legalComment, rfe 1385344\).
        \*
        \* Revision 1.5  2005/11/06 11:54:12  oburn
        \* Incorporate excellent patch \[ 1344344 \] Consolidation of regexp checks.
        \* \\n(.*\n)*([\w|\s]*( class | interface )\1)"/>
      <property name="message" value="Correct header not found"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.regexp.regexp;

public class Example13 {}

// xdoc section -- start
// xdoc section -- end
