/*xml
<module name="Checker">
  <module name="RegexpOnFilename">
    <property name="fileNamePattern" value="^([A-Z][a-z0-9]+\.?)+$"/>
    <property name="match" value="false"/>
    <property name="ignoreFileNameExtensions" value="true"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.regexp.regexponfilename;

// xdoc section -- start
/*checks/regexp/regexponfilename/Example5.java // OK, camelcase
/*checks/regexp/regexponfilename/testexample.xml // Not OK, not camlecase */
public class Example5 { }
// xdoc section -- end
