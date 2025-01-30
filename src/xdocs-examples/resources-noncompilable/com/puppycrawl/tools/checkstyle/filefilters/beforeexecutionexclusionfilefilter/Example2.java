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
    <property name="fileNamePattern" value="module\-info\.java$"/>
  </module>

</module>
*/

// File is part of a non-compilable example fileset

package com.puppycrawl.tools.checkstyle.filefilters.beforeexecutionexclusionfilefilter;

public class Example2 {}

/*
// xdoc section -- start
.../Example2.java
.../module-info.java // OK, as the file is excluded from the audit.
// xdoc section -- end
*/
