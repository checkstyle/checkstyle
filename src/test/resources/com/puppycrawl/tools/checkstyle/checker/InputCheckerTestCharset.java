/*xml
<module name="Checker">
  <module name="com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck">
    <property name="max" value="75"/>
  </module>
  <property name="charset" value="IBM1098"/>
</module>
*/
// violation 6 lines above
package com.puppycrawl.tools.checkstyle.checker;

public class InputCheckerTestCharset {
    // violation below
    String a = "ᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀ";
}
