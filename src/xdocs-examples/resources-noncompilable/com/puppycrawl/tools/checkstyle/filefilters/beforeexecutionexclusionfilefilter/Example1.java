/*xml
<module name="Checker">
  <module name="BeforeExecutionExclusionFileFilter">
    <property name="fileNamePattern" value="module\-info\.java$"/>
  </module>
</module>
*/

// File is part of a non-compilable example fileset

package com.puppycrawl.tools.checkstyle.filefilters.beforeexecutionexclusionfilefilter;

public class Example1 {}

/*
// xdoc section -- start
.../Example1.java
.../module-info.java // file is excluded from the audit
// xdoc section -- end
*/
