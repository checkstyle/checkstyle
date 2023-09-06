/*
SingleLineJavadoc
violateExecutionOnNonTightHtml = (default)false
ignoredTags = @inheritDoc, @throws, @ignoredCustomTag
ignoreInlineTags = false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.singlelinejavadoc;

class InputSingleLineJavadocIgnoredTags {

        /** As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)} */ // violation
    void foo() {}

    /**
     * As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)}
     */
    void foo1() {}

    /** @throws CheckstyleException if a problem occurs */
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

    /** @inheritDoc */
    void foo6() {}

    /** {@inheritDoc} */
    void foo7() {}

    /** {@inheritDoc}  {@code bar} */ // violation
    void foo8() {}

    /** {@inheritDoc}  {@link #bar} */ // violation
    void foo9() {}

    /** @customTag */ // violation
    void bar() {}

    /** @ignoredCustomTag */
    void bar1() {}

    /** <h1> Some header </h1> {@inheritDoc} {@code bar1} text*/ // violation
    void bar2() {}

    /** @customTag <a> href="https://github.com/checkstyle/checkstyle/"</a> text*/ // violation
    void bar3() {}
}
