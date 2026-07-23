/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ImportControl">
      <property name="file" value="${config.folder}/import-control3.xml"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.imports.importcontrol.filters;
// xdoc section -- start
import java.awt.Image;     // violation 'Disallowed import - java.awt.Image'
import java.lang.ref.SoftReference; // ok, specifically allowed by regex expression

import java.io.File;       // violation 'Disallowed import - java.io.File'
import java.io.FileReader; // violation 'Disallowed import - java.io.FileReader'
import java.util.Date;     // violation 'Disallowed import - java.util.Date'
import java.util.List;     // violation 'Disallowed import - java.util.List'

public class Example3 {}
// xdoc section -- end
