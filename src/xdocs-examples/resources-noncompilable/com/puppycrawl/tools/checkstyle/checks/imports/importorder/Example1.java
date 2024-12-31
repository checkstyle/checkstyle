/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ImportOrder"/>
  </module>
</module>
*/
//non-compiled with javac: Compilable with Java7
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

// xdoc section -- start
import java.io.IOException;
import java.net.URL;

import java.io.IOException; // 2 violations
// extra separation before import and wrong order, comes before 'java.net.URL'.
import javax.net.ssl.TrustManager; // violation, extra separation due to above comment
import javax.swing.JComponent;
import org.apache.http.conn.ClientConnectionManager; // OK
import java.util.Set; // violation, wrong order, 'java' should not come after 'org' imports
import com.neurologic.http.HttpClient; // violation, wrong order, 'com' imports comes at top
import com.neurologic.http.impl.ApacheHttpClient; // OK

public class Example1 { }
// xdoc section -- end
