/*xml
<module name="Checker">
  <module name="Translation">
    <property name="baseName" value="^ButtonLabels.*$"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.translation;

// xdoc section -- start
/*
messages.properties
messages_fr.properties
messages_es.properties
ButtonLabels.properties     // violation 'Key 'name' is missing.'
ButtonLabels_fr.properties  // violation 'Key 'cancel' is missing.'
messages_home.properties

messages_home.translations

*/
public class Example4 {}
// xdoc section -- end
