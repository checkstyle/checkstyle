/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RegexpSingleline">
      <property name="format" value="^[ ]*\* This file is copyrighted"/>
      <property name="minimum" value="1"/>
      <property name="message" value="File must contain copyright statement"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.sariflogger;
/* violation on first line 'File must contain copyright statement' */
public class InputSarifLoggerLineOnly {

}
