package com.puppycrawl.tools.checkstyle.checks.javadoc;

public class InputSummaryJavadocChinesePeriod
{
    /**
      * <a href="mailto:vlad@htmlbook.ru"></a>
      */
    public void foo2() {

    }

    /**
     * @return 1。
     */
    public int foo3() {
        return 1;
    }

    /**
     * A sentence。For Chinese Period Test
     */
    void foo4() throws Exception {}

    /** A sentence
     * 。For Chinese Period Test */
    void foo5() {}

    /**
     * A
     * sentence。For Chinese Period Test
     */
    void foo6() {}
}
