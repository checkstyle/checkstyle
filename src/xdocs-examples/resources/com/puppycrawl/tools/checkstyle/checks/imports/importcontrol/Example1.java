/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ImportControl">
      <property name="file" value="${config.folder}/import-control1.xml"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.imports.importcontrol;
// xdoc section -- start
import java.awt.Image;     // violation 'Disallowed import - java.awt.Image'
import java.lang.ref.SoftReference;
// violation above 'Disallowed import - java.lang.ref.SoftReference'
import java.io.File;       // violation 'Disallowed import - java.io.File'
import java.io.FileReader;
import java.util.Date;     // violation 'Disallowed import - java.util.Date'
import java.util.List;     // violation 'Disallowed import - java.util.List'

public class Example1 {}
// xdoc section -- end
