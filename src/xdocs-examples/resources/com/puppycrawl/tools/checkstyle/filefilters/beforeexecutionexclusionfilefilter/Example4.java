/*xml
<module name="Checker">

  <module name="RegexpOnFilename">
    <property name="fileExtensions" value="java"/>
    <property name="fileNamePattern" value="^[A-Z][a-zA-Z0-9_]*\.java$"/>
    <property name="match" value="false"/>
    <message key="regexp.filename.mismatch"
      value="File name must start with an uppercase and match ''{1}''."/>
  </module>

  <module name="BeforeExecutionExclusionFileFilter">
    <property name="fileNamePattern"
      value=".*[\\/]test[\\/].*$"/>
  </module>

</module>
*/

package com.puppycrawl.tools.checkstyle.filefilters.beforeexecutionexclusionfilefilter;

public class Example4 {}

/*
// xdoc section -- start
.../Example1.java
.../Example3.java
.../Example4.java
// OK in the test folder as the whole 'test' folder is excluded from the audit.
.../test/generated_TestCase1.java
.../test/generated_StubBankingRemote.java
.../test/MockPaymentRemote.java
// xdoc section -- end
*/
