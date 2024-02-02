/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TodoComment"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.todocomment;

// xdoc section -- start
public class Example1 {
    int i;
    public void test() { // violation below 'Comment matches .*'
        i++; // TODO: do differently in future
        i++; // todo: do differently in future
    }
}
// xdoc section -- end
