/*xml
<module name="Checker">
  <module name="RegexpHeader">
    <property
      name="header"
      value="^// Copyright \(C\) (\d\d\d\d -)? 2004 MyCompany$
      \n^// All rights reserved$"/>
  </module>
</module>
*/
// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.header.regexpheader;
/* violation on first line 'Line does not match expected header line' */
public class Example3 { }
// xdoc section -- end
// violation 15 lines above 'Line does not match expected header line'
