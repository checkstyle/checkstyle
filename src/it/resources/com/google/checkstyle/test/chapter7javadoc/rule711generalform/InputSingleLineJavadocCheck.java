package com.google.checkstyle.test.chapter7javadoc.rule711generalform;

class InputSingleLineJavadocCheck{

    /** As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)} */
    void foo1() {}

    /**
     * As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)}
     */
    void foo2() {}

    /** @throws CheckstyleException if a problem occurs */ //warn
    void foo3() {}

    /**
     * @throws CheckstyleException if a problem occurs
     */
    void foo4() {}

    /** An especially short bit of Javadoc. */
    void foo5() {}

    /**
     * An especially short bit of Javadoc.
     */
    void foo6() {}

    /** @inheritDoc */ //warn
    void foo7() {}

    /** {@inheritDoc} */
    void foo8() {}

    /** @customTag */ //warn
    void bar() {}

    /** <h1> Some header </h1> {@inheritDoc} {@code bar1} text*/
    void bar2() {}

    /** @customTag <a> href="https://github.com/checkstyle/checkstyle/"</a> text*/ //warn
    void bar3() {}

    /** Single line Javadoc that references {@link String}. */
    void bar4() {}
}
