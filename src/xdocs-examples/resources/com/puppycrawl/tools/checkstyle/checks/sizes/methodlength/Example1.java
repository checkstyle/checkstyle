/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodLength"/>
  </module>
</module>
*/

// xdoc section -- start
public class Example1 {
  public Example1() {  // constructor (line 1)
      /* line 2
          ...
         line 4 */
  } // line 5, OK, as it is less than 150

  public void firstExample() { // line 1

      // line 3
      System.out.println("line 4");
      /* line 5
         line 6 */
  } // line 7, OK, as it is less than 150

  public void secondExample() { // line 1
      // line 2
      System.out.println("line 3");

      /* line 5
          ...
         line 7 */
  } // line 8, OK, as it is less than 150
}
// xdoc section -- end
