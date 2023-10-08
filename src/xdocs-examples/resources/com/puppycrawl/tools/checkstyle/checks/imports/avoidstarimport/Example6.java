/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AvoidStarImport">
      <property name="allowStaticMemberImports" value="true"/>
      <property name="excludes" value="java.io,java.net"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
import java.util.Scanner;         // OK
import java.io.*;                 // OK
import static java.lang.Math.*;   // OK
import java.util.*;               // violation
import java.net.*;                // OK
// xdoc section -- end
