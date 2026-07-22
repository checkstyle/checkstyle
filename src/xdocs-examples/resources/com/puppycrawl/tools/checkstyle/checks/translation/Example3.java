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

// xdoc section -- start
/*
messages.properties
messages_fr.properties
messages_es.properties
ButtonLabels.properties
ButtonLabels_fr.properties
messages_home.properties
// violation above 'Properties file 'messages_home_fr.properties' missing.'
messages_home.translations
// violation above 'Properties file 'messages_home_fr.translations' missing.'
*/
public class Example3 {}
// xdoc section -- end
