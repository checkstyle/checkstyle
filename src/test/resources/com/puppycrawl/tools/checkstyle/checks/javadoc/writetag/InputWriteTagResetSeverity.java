/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WriteTag">
      <property name="tag" value="@author"/>
      <property name="tagFormat" value="Mohanad"/>
      <property name="tagSeverity" value="warning"/>
    </module>
  </module>
</module>

*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;
/**
 * Testing tag writing
 * @author Mohanad
 * @author Another Author
 * @incomplete This class needs more code...
 * @doubletag first text
 * @doubletag second text
 * @emptytag
 */
class InputWriteTagResetSeverity
{
    /**
     * @todo Add a constructor comment
     */
    public InputWriteTagResetSeverity()
    {
    }

    public void method()
    {
    }

    /**
     * @todo Add a comment
     */
    public void anotherMethod()
    {
    }
}

