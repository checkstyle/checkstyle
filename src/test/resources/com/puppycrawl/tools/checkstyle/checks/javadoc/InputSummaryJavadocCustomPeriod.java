package com.puppycrawl.tools.checkstyle.checks.javadoc;

public class InputSummaryJavadocCustomPeriod {
    /**
     * As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)}!
     */
    void foo1() {}

    /**
     * Blabla!
     */
    void foo2() throws Exception {}

    /** An especially short bit of Javadoc! */
    void foo3() {}

    /**
     * As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)}.
     */
    void foo4() {}

    /**
     * @throws Exception if an error occurs
     */
    void foo5() throws Exception {}

    /** <a href="mailto:vlad@htmlbook.ru"/> */
    void foo6() {}


    /** An especially short bit of Javadoc! */
    void foo7() {}
}
