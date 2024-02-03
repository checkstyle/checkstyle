/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NoCodeInFile"/>
  </module>
</module>
*/
//non-compiled: no code for testing

// violation 9 lines above

// xdoc section -- start
/*
public class Example2 {
// Whole code is commented out , violation on first line of file
}
*/
// xdoc section -- end
