/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AvoidStarImport"/>
  </module>
</module>
*/

// xdoc section -- start
import java.util.Scanner;         // OK
import java.io.*;                 // violation
import static java.lang.Math.*;   // violation
import java.util.*;               // violation
import java.net.*;                // violation
// xdoc section -- end
