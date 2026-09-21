/*xml
<module name="Checker">
  <property name="charset" value="IBM1098"/>
  <module name="LineLength">
    <property name="max" value="75"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.sizes.linelength;

public class InputLineLengthUnmappableCharacters {
    String a = "ᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀ"; // violation 'Line is longer than 75 characters (found 287).'
}
