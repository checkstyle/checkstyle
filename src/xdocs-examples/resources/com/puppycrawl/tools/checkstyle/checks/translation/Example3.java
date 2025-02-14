/*xml
<module name="Checker">
  <module name="Translation">
    <property name="fileExtensions" value="properties"/>
    <property name="fileExtensions" value="translations"/>
    <property name="requiredTranslations" value="fr"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.translation;


/*
// xdoc section -- start
messages_home.properties   // violation,
                          'Properties file 'messages_home_fr.properties' missing.'
messages_home.translations // violation,
                          'Properties file 'messages_home_fr.translations' missing.'
// xdoc section -- end
*/
public class Example3 {}
