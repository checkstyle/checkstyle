/*xml
<module name="Checker">
  <module name="NewlineAtEndOfFile">
    <property name="fileExtensions" value="java, xml, cpp"/>
  </module>
</module>
*/
// violation 7 lines above 'File does not end with a newline.'
// xdoc section -- start
int main() { // ⤶
    return 0;// ⤶
} // no ⤶ below it is violation
// xdoc section -- end
