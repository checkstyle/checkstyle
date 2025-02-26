/*xml
<module name="Checker">

  <module name="RegexpOnFilename">
    <property name="fileNamePattern" value="^[A-Z].*"/>
    <property name="match" value="false"/>
    <message key="regexp.filename.mismatch"
      value="File name must start with an uppercase."/>
  </module>

  <module name="BeforeExecutionExclusionFileFilter">
    <property name="fileNamePattern"
      value="(.*[\\/]test[\\/].*$)|(module\-info\.java$)"/>
  </module>

</module>
*/

package com.puppycrawl.tools.checkstyle.filefilters.beforeexecutionexclusionfilefilter;

public class Example4 {}

/*
// xdoc section -- start
.../Example1.java
.../Example2.java
.../Example3.java
.../Example4.java
.../test/generated_TestCase1.java // OK, 'test' folder is not audited
.../test/generated_StubBankRemote.java // OK, 'test' folder is not audited
.../test/MockPaymentRemote.java
.../module-info.java // OK, the file is not audited
// xdoc section -- end
*/
