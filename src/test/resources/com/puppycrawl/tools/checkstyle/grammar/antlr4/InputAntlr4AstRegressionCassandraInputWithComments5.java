package com.puppycrawl.tools.checkstyle.grammar.antlr4;

import java.util.Hashtable;

import junit.framework.AssertionFailedError;
import junit.framework.Test;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class InputAntlr4AstRegressionCassandraInputWithComments5 {
    /**
     * Interface TestListener.
     *
     * <p>A Test is finished.
     * @param test the test.
     */
    public void endTest(final Test test) {
        final String testDescription = createDescription(test);

        // Fix for bug #5637 - if a junit.extensions.TestSetup is
        // used and throws an exception during setUp then startTest
        // would never have been called
        if (!testStarts.containsKey(testDescription)) {
            startTest(test);
        }
        Element currentTest;
        if (!failedTests.containsKey(test) && !skippedTests.containsKey(testDescription)
                && !ignoredTests.containsKey(testDescription)) {
            currentTest = doc.createElement("TESTCASE");
            String n = "";
            if (n != null && !tag.isEmpty())
                n = n + "-" + tag;
            currentTest.setAttribute(ATTR_NAME,
                                     n == null ? UNKNOWN : n);
            // a TestSuite can contain Tests from multiple classes,
            // even tests with the same name - disambiguate them.
            currentTest.setAttribute("ATTR_CLASSNAME",
                    "");
            rootElement.appendChild(currentTest);
            testElements.put(createDescription(test), currentTest);
        } else {
            currentTest = testElements.get(testDescription);
        }

        final Long l = testStarts.get(createDescription(test));
    }

    /**
     * Interface TestListener for JUnit &lt;= 3.4.
     *
     * <p>A Test failed.
     * @param test the test.
     * @param t the exception.
     */
    public void addFailure(final Test test, final Throwable t) {
        formatError("FAILURE", test, t);
    }

    /**
     * Interface TestListener for JUnit &gt; 3.4.
     *
     * <p>A Test failed.
     * @param test the test.
     * @param t the assertion.
     */
    public void addFailure(final Test test, final AssertionFailedError t) {
        addFailure(test, (Throwable) t);
    }

    /**
     * Interface TestListener.
     *
     * <p>An error occurred while running the test.
     * @param test the test.
     * @param t the error.
     */
    public void addError(final Test test, final Throwable t) {
        formatError("ERROR", test, t);
    }

    private static final String UNKNOWN = "unknown";
    private static final String tag = System.getProperty("cassandra.testtag", "");
    private Document doc;
    private Element rootElement;
    private final Hashtable<String, Element> testElements = new Hashtable<String, Element>();
    private final Hashtable failedTests = new Hashtable();
    private final Hashtable<String, Test> skippedTests = new Hashtable<String, Test>();
    private final Hashtable<String, Test> ignoredTests = new Hashtable<String, Test>();
    private final Hashtable<String, Long> testStarts = new Hashtable<String, Long>();
    private static final String ATTR_NAME = "";
    private static String createDescription(final Test test) { return null; }
    public void startTest(final Test t) { }
    private void formatError(final String type, final Test test, final Throwable t) { }
}
