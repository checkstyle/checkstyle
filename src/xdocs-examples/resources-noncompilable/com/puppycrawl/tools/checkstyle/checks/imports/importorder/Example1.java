/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ImportOrder"/>
  </module>
</module>
*/
// non-compiled with javac: Compilable with java17
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
import org.apache.http.conn.ClientConnectionManager;
import java.util.Set; // violation, wrong order, 'java' should not come after 'org' imports
import com.neurologic.http.HttpClient; // violation, wrong order, 'com' imports comes at top
import com.neurologic.http.impl.ApacheHttpClient;
// xdoc section -- end

public class Example1 { }
