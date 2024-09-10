/*xml
<module name="Checker">
  <module name="NewlineAtEndOfFile">
    <property name="fileExtensions" value="java, xml, cpp"/>
  </module>
</module>
*/
// violation 7 lines above 'File does not end with a newline.'
// xdoc section -- start
void main() { // ⤶
    std::cout << "Hello World!";// ⤶
} // no ⤶ below it is violation
// xdoc section -- end