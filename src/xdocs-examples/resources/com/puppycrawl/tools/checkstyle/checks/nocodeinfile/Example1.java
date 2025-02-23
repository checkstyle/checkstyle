/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NoCodeInFile"/>
  </module>
</module>
*/

// violation 8 lines above 'The file does not contain any code'

// xdoc section -- start
/* the violation is on first line of file */
// public class Example1 {
// single-line comment is not code
// }
// xdoc section -- end
