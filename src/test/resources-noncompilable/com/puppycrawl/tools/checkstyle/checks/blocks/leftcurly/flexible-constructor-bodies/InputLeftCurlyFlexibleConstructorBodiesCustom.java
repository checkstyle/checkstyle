/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="LeftCurly">
      <property name="option" value="nl"/>
      <property name="ignoreEnums" value="true"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

class InputLeftCurlyFlexibleConstructorBodiesCustom
{

    static class Before
    {
        Before(boolean stop) { // violation ''{' at column 30 should be on a new line.'
            int local = 1;
            super();
            if (stop)
            {
                return;
            }
        }
    }

    static class After
    {
        After(boolean stop)
        {
            super();
            int local = 1;
            if (stop)
            {
                return;
            }
        }
    }
}
