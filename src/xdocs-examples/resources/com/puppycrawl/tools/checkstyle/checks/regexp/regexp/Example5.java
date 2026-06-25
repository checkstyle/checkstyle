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

package com.puppycrawl.tools.checkstyle.checks.regexp.regexp;
// violation first line 'Required pattern 'Copyright' missing in file.'
// xdoc section -- start
/*
 * violation on first line 'Required pattern 'Copyright' missing in file.'
*/
public class Example5 {}
// xdoc section -- end
