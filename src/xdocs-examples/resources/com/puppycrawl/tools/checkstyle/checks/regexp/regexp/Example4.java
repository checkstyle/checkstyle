/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Regexp">
      <property name="format"
        value="// This code is copyrighted\n// \(c\) MyCompany"/>
      <property name="duplicateLimit" value="0"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.regexp.regexp;

// xdoc section -- start
// This code is copyrighted
// (c) MyCompany
public class Example4 {}

// violation below 'Found duplicate pattern'
// This code is copyrighted
// (c) MyCompany
class Example41 {}
// xdoc section -- end
