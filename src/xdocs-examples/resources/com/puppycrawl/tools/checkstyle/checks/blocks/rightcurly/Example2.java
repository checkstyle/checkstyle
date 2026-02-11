/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RightCurly">
      <property name="option" value="alone"/>
      <property name="tokens" value="LITERAL_ELSE, METHOD_DEF"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

// xdoc section -- start
public class Example2 {

    public void test() {

        boolean foo = false;
        if (foo) {
            bar();
        } // ok, because 'LITERAL_IF' is not in 'tokens'
        // as the next part of a multi-block statement
        else {
            bar();
        }

        if (foo) {
            bar();
        } else { // ok, because 'LITERAL_IF' is not in 'tokens'
            bar();
        }

        if (foo) { bar(); } int i = 0;
        // ok above, because 'LITERAL_IF' is not in 'tokens'

        if (foo) { bar(); }
        i = 0;

        try {
            bar();
        } // ok, because 'LITERAL_TRY' is not in 'tokens'
        // as the next part of a multi-block statement (one that directly
        // contains multiple blocks: if/else-if/else, do/while or try/catch/finally).
        catch (Exception e) {
            bar();
        }

        try {
            bar();
        } catch (Exception e) {
            bar();
        }

    }

    private void bar() {
    }

    public void testSingleLine() { bar(); } // violation, 'should be alone on a line'
}
// xdoc section -- end