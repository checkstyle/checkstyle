/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ImportOrder"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

// xdoc section -- start
import java.io.IOException;
import java.net.URL;

import java.io.IOException;
// 2 violations above:
//  'Extra separation in import group before 'java.io.IOException''
//  'Wrong order for 'java.io.IOException' import.'
import javax.net.ssl.TrustManager; // violation, extra separation due to above comment
import javax.swing.JComponent;
import org.w3c.dom.Document;
import java.util.Set; // violation, wrong order, 'java' should not come after 'org' imports
import java.util.Map; // violation, wrong order, 'java' should not come after 'org' imports
import com.sun.security.auth.UserPrincipal; // violation, wrong order
import com.sun.source.tree.Tree;
// xdoc section -- end

public class Example1 { }
