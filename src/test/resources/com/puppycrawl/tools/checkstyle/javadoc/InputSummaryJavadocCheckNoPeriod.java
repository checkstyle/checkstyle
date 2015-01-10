package com.puppycrawl.tools.checkstyle.javadoc;

public class InputSummaryJavadocCheckNoPeriod
{
    /**
     * As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)}
     */
    void foo3() {}
    
    /**
     * Blabla
     */
    void foo4() throws Exception {}
    
    /** An especially short bit of Javadoc */
    void foo5() {}
}
