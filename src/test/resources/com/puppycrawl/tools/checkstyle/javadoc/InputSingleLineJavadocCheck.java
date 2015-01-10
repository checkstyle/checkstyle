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
}