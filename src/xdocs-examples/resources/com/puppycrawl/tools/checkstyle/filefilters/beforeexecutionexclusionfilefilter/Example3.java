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
      value="^(?!.*(Remote\.java|Client\.java|[\\/]Remote\.java|[\\/]Client\.java)).*$"/>
  </module>

</module>
*/

package com.puppycrawl.tools.checkstyle.filefilters.beforeexecutionexclusionfilefilter;

public class Example3 {}

/*
// xdoc section -- start
.../Example1.java
.../Example3.java
.../Example4.java
// OK below as only the files ending with 'Remote.java' or 'Client.java' are audited.
.../test/generated_TestCase1.java
// violation below 'File name must start with an uppercase and match '^[A-Z][a-zA-Z0-9_]*\.java$.'
.../test/generated_StubBankingRemote.java
.../test/MockPaymentRemote.java
// xdoc section -- end
*/

