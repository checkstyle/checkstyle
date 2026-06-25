/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Regexp">
      <property name="format" value="// This code is copyrighted\."/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.regexp.regexp;
// violation first line 'missing in file.'
// xdoc section -- start
/* violation on first line 'Required pattern missing in a file.' */
/*
 * Some Copyright
 */
public class Example2 {}
// xdoc section -- end
