/*xml
<module name="Checker">

  <module name="RegexpOnFilename">
    <property name="fileNamePattern" value="^[A-Z].*"/>
    <property name="match" value="false"/>
    <message key="regexp.filename.mismatch"
      value="File name must start with an uppercase."/>
  </module>

  <module name="BeforeExecutionExclusionFileFilter"/>

</module>
*/

package com.puppycrawl.tools.checkstyle.filefilters.beforeexecutionexclusionfilefilter;

public class Example1 {}

/*
// xdoc section -- start
.../Example1.java
.../Example2.java
.../Example3.java
.../Example4.java
.../test/generated_TestCase1.java // violation, name must start with an uppercase
.../test/generated_StubBankRemote.java // violation, must start with an uppercase
.../test/MockPaymentRemote.java
.../module-info.java // violation, name must start with an uppercase
// xdoc section -- end
*/
