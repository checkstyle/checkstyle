package com.puppycrawl.tools.checkstyle.grammar.antlr4;

import java.util.Hashtable;

import junit.framework.Test;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class InputAntlr4AstRegressionCassandraInputWithComments6 {
    private void formatError(final String type, final Test test, final Throwable t) {
        if (test != null) {
            endTest(test);
            failedTests.put(test, test);
        }

        final Element nested = doc.createElement(type);
        Element currentTest;
        if (test != null) {
            currentTest = testElements.get(createDescription(test));
        } else {
            currentTest = rootElement;
        }

        currentTest.appendChild(nested);

        final String message = t.getMessage();
        if (message != null && message.length() > 0) {
            nested.setAttribute("ATTR_MESSAGE", t.getMessage());
        }
        nested.setAttribute("ATTR_TYPE", t.getClass().getName());

        final String strace = "JUnitTestRunner.getFilteredTrace(t";
        final Text trace = doc.createTextNode(strace);
        nested.appendChild(trace);
    }

    public void testIgnored(final Test test) {
        formatSkip(test, "JUnitVersionHelper.getIgnoreMessage(test)");
        if (test != null) {
            ignoredTests.put(createDescription(test), test);
        }
    }


    public void formatSkip(final Test test, final String message) {
        if (test != null) {
            endTest(test);
        }

        final Element nested = doc.createElement("skipped");

        if (message != null) {
            nested.setAttribute("message", message);
        }

        Element currentTest;
        if (test != null) {
            currentTest = testElements.get(createDescription(test));
        } else {
            currentTest = rootElement;
        }

        currentTest.appendChild(nested);

    }

    public void testAssumptionFailure(final Test test, final Throwable failure) {
        formatSkip(test, failure.getMessage());
        skippedTests.put(createDescription(test), test);

    }

    private Document doc;
    private Element rootElement;
    private final Hashtable<String, Element> testElements = new Hashtable<String, Element>();
    private final Hashtable failedTests = new Hashtable();
    private final Hashtable<String, Test> skippedTests = new Hashtable<String, Test>();
    private final Hashtable<String, Test> ignoredTests = new Hashtable<String, Test>();
    private static String createDescription(final Test test) { return null; }
    public void endTest(final Test test) { }
}
