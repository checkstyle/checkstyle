package com.puppycrawl.tools.checkstyle.javadoc;
class Foo{

	/** As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)} */
    void foo() {}

    /**
     * As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)}
     */
    void foo1() {}

    /** @throws CheckstyleException if an error occurs */
    void foo2() {}

    /**
     * @throws CheckstyleException if an error occurs
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
    void foo8() {}
    
    /** {@inheritDoc}  {@link #bar} */
    void foo9() {}

    /** @customTag */
    void bar() {}

    /** @ignoredCustomTag */
    void bar1() {}

    /** <h1> Some header </h1> {@inheritDoc} {@code bar1} text*/
    void bar2() {}

    /** @customTag <a> href="https://github.com/checkstyle/chestyle/"</a> text*/
    void bar3() {}
}