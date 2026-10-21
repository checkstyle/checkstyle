/*xml
<module name="Checker">
  <module name="FileTabCharacter">
    <property name="eachLine" value="true"/>
    <property name="fileExtensions" value=""/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.filetabcharacter;

class InputFileTabCharacterExtensionsEmpty {
	int value; // violation 'Line contains a tab character.'
}
