/*xml
<module name="Checker">
  <module name="Translation">
    <property name="baseName" value="^ButtonLabels.*$"/>
    <property name="requiredTranslations" value="fr"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.translation;

// xdoc section -- start
public class Example2 {
    public String hello = "Hello";
    public String cancel = "Cancel";

    public String hello_fr = "Hello";
    public String cancel_fr = "Cancel";
}
// xdoc section -- end
