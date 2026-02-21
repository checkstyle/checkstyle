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
// violation below 'Extra separation in import group before 'java.io.IOException''
import java.io.IOException; // violation 'Wrong lexicographical order for 'java.io.IOException' import. Should be before 'java.net.URL'.'
import javax.net.ssl.TrustManager;
import javax.swing.JComponent;
import org.w3c.dom.Document;
import java.util.Set; // violation 'Wrong lexicographical order for 'java.util.Set' import. Should be before 'org.w3c.dom.Document'.'
import java.util.Map; // violation 'Wrong lexicographical order for 'java.util.Map' import. Should be before 'java.util.Set'.'
import com.sun.security.auth.UserPrincipal; // violation 'Wrong lexicographical order for 'com.sun.security.auth.UserPrincipal' import. Should be before 'java.util.Map'.'
import com.sun.source.tree.Tree;
// xdoc section -- end

public class Example1 { }
