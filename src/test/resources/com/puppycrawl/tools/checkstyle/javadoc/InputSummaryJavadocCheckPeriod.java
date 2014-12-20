package com.puppycrawl.tools.checkstyle.javadoc;

public class InputSummaryJavadocCheckPeriod
{
    /**
     * As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)}
     */
    void foo3() {}
    
    /**
     * Blabla
     */
    void foo4() throws Exception {}
    
    /** An especially short bit of Javadoc_ */
    void foo5() {}

    /**
     * An especially short bit of Javadoc_
     */
    void foo6() {}
}
