/*xml
<module name="Checker">
  <module name="RegexpSingleline">
    <property name="format" value="^[ ]*\* This file is copyrighted"/>
    <property name="minimum" value="1"/>
    <!--  Need to specify a maximum, so 10 times is more than enough. -->
    <property name="maximum" value="10"/>
    <property name="message"
              value="File must contain copyright statement"/>
  </module>
</module>
*/

// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.regexp.regexpsingleline;
/* violation on first line 'File must contain copyright statement' */
/**
 * MyClass as a configuration example.
 */
public class Example5 {}
// xdoc section -- end
// violation 21 lines above 'File must contain copyright statement'
