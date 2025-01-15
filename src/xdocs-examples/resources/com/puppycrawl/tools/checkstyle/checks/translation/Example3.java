/*xml
<module name="Checker">
  <module name="Translation">
    <property name="requiredTranslations" value="ja, fr"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.translation;

// xdoc section -- start
public class Example3 {
    // English translations
    public String hello = "Hello";
    public String cancel = "Cancel";

    // French translations
    public String greeting_fr = "Bonjour";
    public String name_fr = "Nom";

    // Japanese translations
    public String greeting_ja = "こんにちは";
    public String age_ja = "年齢";
}
// xdoc section -- end
