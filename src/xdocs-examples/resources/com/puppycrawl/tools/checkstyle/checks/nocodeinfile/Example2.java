/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NoCodeInFile"/>
  </module>
</module>
*/

// violation 8 lines above

// xdoc section -- start
// the violation is on first line of file
/*
 public class Example2 {
 block comment is not code
 }
*/
// xdoc section -- end
