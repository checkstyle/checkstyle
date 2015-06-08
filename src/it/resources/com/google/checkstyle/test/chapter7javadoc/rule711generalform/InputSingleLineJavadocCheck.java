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
}