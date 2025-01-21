/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Regexp">
      <property name="format" value="// This code is copyrighted\."/>
    </module>
  </module>
</module>
*/
// violation 9 lines above 'missing in file.'

package com.puppycrawl.tools.checkstyle.checks.regexp.regexp;

// xdoc section -- start
/* violation on first line 'Required pattern missing in a file.' */
/*
 * Some Copyright
 */
public class Example2 {}
// xdoc section -- end
