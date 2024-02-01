/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodLength"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.sizes.methodlength;

// xdoc section -- start
public class Example1 {

  public Example1() { // line1
      /* line 2
         line 3
         line 4
         line 5 */
  }// line 6, OK, as it is less than 150

  public void firstExample() { // line 1

    // line 3
    System.out.println("line 4");
    /* line 5
    line 6 */

    // line 8
    System.out.println("line 9");
  } // line 10, OK, as it is less than 150
}
// xdoc section -- end
