package com.google.checkstyle.test.chapter7javadoc.rule711generalform;

class Foo{

	/** As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)} */ //warn
    void foo1() {}

    /**
     * As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)}
     */
    void foo2() {}

    /** @throws CheckstyleException if an error occurs */ //warn
    void foo3() {}

    /**
     * @throws CheckstyleException if an error occurs
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

    /** {@inheritDoc} */ //warn
    void foo8() {}

    /** @customTag */ //warn
    void bar() {}

    /** <h1> Some header </h1> {@inheritDoc} {@code bar1} text*/ //warn
    void bar2() {}

    /** @customTag <a> href="https://github.com/checkstyle/chestyle/"</a> text*/ //warn
    void bar3() {}
}