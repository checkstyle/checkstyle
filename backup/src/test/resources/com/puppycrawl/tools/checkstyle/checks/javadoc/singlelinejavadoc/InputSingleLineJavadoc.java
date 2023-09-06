/*
SingleLineJavadoc
violateExecutionOnNonTightHtml = (default)false
ignoredTags = (default)
ignoreInlineTags = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.singlelinejavadoc;

class InputSingleLineJavadoc {

        /** As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)} */
    void foo() {}

    /**
     * As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)}
     */
    void foo1() {}

    /** @throws CheckstyleException if a problem occurs */ // violation
    void foo2() {}

    /**
     * @throws CheckstyleException if a problem occurs
     */
    void foo3() {}

    /** An especially short bit of Javadoc. */
    void foo4() {}

    /**
     * An especially short bit of Javadoc.
     */
    void foo5() {}

    /** @inheritDoc */ // violation
    void foo6() {}

    /** {@inheritDoc} */
    void foo7() {}

    /** {@inheritDoc}  {@code bar} */
    void foo8() {}

    /** {@inheritDoc}  {@link #bar} */
    void foo9() {}

    /** @customTag */ // violation
    void bar() {}

    /** @ignoredCustomTag */ // violation
    void bar1() {}

    /** <h1> Some header </h1> {@inheritDoc} {@code bar1} text*/
    void bar2() {}

    /** @customTag <a> href="https://github.com/checkstyle/checkstyle/"</a> text*/ // violation
    void bar3() {}
}
