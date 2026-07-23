/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ImportControl">
      <property name="file" value="${config.folder}/import-control9.xml"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.imports.importcontrol.filters;
// violation below 'Disallowed import - com.google.common.io.Files'
import com.google.common.io.Files;
import com.puppycrawl.tools.checkstyle.checks.blocks.LeftCurlyCheck;
// violation above 'Disallowed import'

import java.lang.ref.ReferenceQueue;
// violation above 'Disallowed import - java.lang.ref.ReferenceQueue'
import java.lang.ref.SoftReference;
// violation above 'Disallowed import - java.lang.ref.SoftReference'
import java.util.Date;
import java.util.List;
import java.util.Map;
// violation above 'Disallowed import - java.util.Map'

public class UseCase6 {}
// xdoc section -- end
