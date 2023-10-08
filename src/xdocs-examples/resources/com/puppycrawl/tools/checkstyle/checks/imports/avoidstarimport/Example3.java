/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AvoidStarImport">
      <property name="allowClassImports" value="true"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
import java.util.Scanner;         // OK
import java.io.*;                 // OK
import static java.lang.Math.*;   // violation
import java.util.*;               // OK
import java.net.*;                // OK
// xdoc section -- end
