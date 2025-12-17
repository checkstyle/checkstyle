/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingOverride"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverride;

class ParentClass3 {
    public void test1() {}
    public void test2() {}
    public void test3() {}
    public void test4() {}
}

// xdoc section -- start
class Example3 extends ParentClass3 {

    // No javadoc, no @Override needed
    public void test1() {
    }

    // Javadoc present but no {@inheritDoc}, no @Override needed
    /**
     * This is a sample javadoc.
     */
    public void test2() {
    }

    /** {@inheritDoc} */
    @Override
    public void test3() {
    }

    /** {@inheritDoc} */
    public void test4() { // violation, '@inheritDoc' tag requires @Override annotation
    }
}
// xdoc section -- end