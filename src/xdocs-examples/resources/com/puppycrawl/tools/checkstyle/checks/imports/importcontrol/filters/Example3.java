/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ImportControl">
      <property name="file" value="${config.folder}/import-control3.xml"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.imports.importcontrol.filters;

import com.google.common.io.Files;
import com.puppycrawl.tools.checkstyle.checks.blocks.LeftCurlyCheck;
// violation above, 'Disallowed import'

import java.lang.ref.ReferenceQueue;
// violation above, 'Disallowed import - java.lang.ref.ReferenceQueue'
import java.lang.ref.SoftReference; // ok, specifically allowed by regex expression

public class Example3 {}
// xdoc section -- end
