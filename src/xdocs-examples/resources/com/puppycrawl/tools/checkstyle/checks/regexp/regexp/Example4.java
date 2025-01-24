/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Regexp">
      <property name="format"
        value="// This code is copyrighted\n// \(c\) MyCompany"/>
      <property name="message" value="Copyright"/>
    </module>
  </module>
</module>
*/
// violation 11 lines above 'Copyright'

package com.puppycrawl.tools.checkstyle.checks.regexp.regexp;

// xdoc section -- start
/* violation on first line 'Required pattern 'Copyright' missing in file.' */
public class Example4 {}

// xdoc section -- end
