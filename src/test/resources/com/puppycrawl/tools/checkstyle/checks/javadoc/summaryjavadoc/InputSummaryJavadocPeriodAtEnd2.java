/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = (default)^$
period = (default).


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

public class InputSummaryJavadocPeriodAtEnd2 {

/** // violation 'First sentence of Javadoc is missing an ending period'
 * This is 1 version
 *
 */
public class Test {
}

/** // violation 'First sentence of Javadoc is missing an ending period'
 * This is 1.0 version
 */
class Test2 {
}

/**
 * This is 1 version.
 */
class Test3 {
}

/** Stop instances being created. **/
    String twoSentences1() {return "Sentence one. Sentence two.";}

/** // violation
 * This is 1 version *
 */
class Test4 {
}

    /**
     * I am java **.
     *
     * @param str string
     * @return false
     */
    public boolean method(String str) {
    return false;
    }

    /**
     * //////////I am fine\\\\\\\\\\\\.
     */
    public void method2() {
        return;
    }
}

/** // violation
 * Replaces {@code ${xxx}} style constructions in the given value
 * with the string value of the corresponding data types. This method must remain
 * outside inner class for easier testing since inner class requires an instance.
 *
 * <p>Code copied from
 * <a href="https://github.com/apache/ant/blob/master/src/main/org/apache/tools/ant/ProjectHelper.java">
 * ant.
 * </a>
 */
class extra {
}

/**
 * Replaces {@code ${xxx}} style constructions in the given value
 * with the string value of the corresponding data types. This method must remain
 * outside inner class for easier testing since inner class requires an instance.
 *
 * <p>Code copied from:
 * <a href="https://github.com/apache/ant/blob/master/src/main/org/apache/tools/ant/ProjectHelper.java">
 * ant.
 * </a>
 */
class extra1 {
}
