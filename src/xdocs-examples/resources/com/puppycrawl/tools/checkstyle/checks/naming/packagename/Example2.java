/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PackageName">
      <property name="format"
        value="^[a-z]+(\.[a-z][a-z0-9]*)*$"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start


package COM; // violation, name 'COM' must match pattern '^[a-z]+(\.[a-z][a-z0-9]*)*$'



public class Example2 {

}
// xdoc section -- end