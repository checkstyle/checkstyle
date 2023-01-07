/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = (default)^$
period = 。


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

public class InputSummaryJavadocPeriodAtEndJapanese {

/** // violation 'First sentence of Javadoc is missing an ending period'
 * これは 1 バージョンです
 *
 *
 *
 * @inspection dd
 */
public class Test {
}

/** // violation 'First sentence of Javadoc is missing an ending period'
 * これは 1.0 バージョンです
 */
class Test2 {
}

/**
 * これは 1 バージョンです。
 */
class Test3 {
}

/** インスタンスの作成を停止します。 **/
    String twoSentences1() {return "Sentence one. Sentence two.";}

/** // violation
 * これは 1 つのバージョンです *
 */
class Test4 {
}

    /**
     * 私はジャバ**です。
     *
     * @param str string
     * @return false
     */
    public boolean method(String str) {
    return false;
    }

    /**
     * //////////私は元気です\\\\\\\\\\\\。
     */
    public void method2() {
        return;
    }

}
