/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Regexp">
      <property
        name="format"
        value="\A// Copyright \(C\) \d\d\d\d MyCompany\n// All rights reserved"/>
    </module>
  </module>
</module>
*/
// violation 11 lines above 'missing in file.'

package com.puppycrawl.tools.checkstyle.checks.regexp.regexp;

// xdoc section -- start
// Copyright (C) 2004 MyCompany
// All rights reserved
public class Example11 {}
// xdoc section -- end
