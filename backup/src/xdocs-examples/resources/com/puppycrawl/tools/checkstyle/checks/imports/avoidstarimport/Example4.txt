/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AvoidStarImport">
      <property name="allowStaticMemberImports" value="true"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
import java.util.Scanner;         // OK
import java.io.*;                 // violation
import static java.lang.Math.*;   // OK
import java.util.*;               // violation
import java.net.*;                // violation
// xdoc section -- end
