/*
SingleLineJavadoc
violateExecutionOnNonTightHtml = (default)false
ignoredTags = @inheritDoc, @throws, @ignoredCustomTag
ignoreInlineTags = false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.singlelinejavadoc;

class InputSingleLineJavadocIgnoredTags {

        /** As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)} */
    void foo() {
         // violation 2 lines above 'Single-line Javadoc comment should be multi-line.'
    }

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

    /** {@inheritDoc}  {@code bar} */
    void foo8() {
         // violation 2 lines above 'Single-line Javadoc comment should be multi-line.'
    }

    /** {@inheritDoc}  {@link #bar} */
    void foo9() {
         // violation 2 lines above 'Single-line Javadoc comment should be multi-line.'
    }

    /** @customTag */ // violation 'Single-line Javadoc comment should be multi-line.'
    void bar() {}

    /** @ignoredCustomTag */
    void bar1() {}

    /** <h1> Some header </h1> {@inheritDoc} {@code bar1} text*/
    void bar2() {
        // violation 2 lines above 'Single-line Javadoc comment should be multi-line.'
    }

    /** @customTag <a> href="https://github.com/checkstyle/checkstyle/"</a> text*/
    void bar3() {
         // violation 2 lines above 'Single-line Javadoc comment should be multi-line.'
    }
}
