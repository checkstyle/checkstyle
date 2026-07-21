/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NoCodeInFile"/>
  </module>
</module>
*/

// xdoc section -- start
// violation first line 'The file does not contain any code'
/*
 public class Example2 {
 block comment is not code
 }
*/
// xdoc section -- end
