/*xml
<module name="Checker">
  <module name="NewlineAtEndOfFile">
    <property name="fileExtensions" value="java, xml, py"/>
  </module>
</module>
*/
// violation 7 lines above 'File does not end with a newline.'
// xdoc section -- start
print("Hello world!")
# no ⤶ below it is violation
// xdoc section -- end