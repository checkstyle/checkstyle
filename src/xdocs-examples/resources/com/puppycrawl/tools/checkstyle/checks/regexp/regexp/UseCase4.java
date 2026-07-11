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

// xdoc section -- start
// violation first line 'Required pattern 'Copyright' missing in file.'
/*
 * violation on first line 'Required pattern 'Copyright' missing in file.'
*/
public class UseCase4 {}
// xdoc section -- end
