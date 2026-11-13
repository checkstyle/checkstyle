package com.puppycrawl.tools.checkstyle.grammar.antlr4;

import java.io.OutputStream;
import java.util.Hashtable;

import junit.framework.Test;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class InputAntlr4AstRegressionCassandraInputWithComments2 {
    /**
     * Tests that were skipped.
     */
    private final Hashtable<String, Test> skippedTests = new Hashtable<String, Test>();
    /**
     * Tests that were ignored. See the note above about the key being a bit of a hack.
     */
    private final Hashtable<String, Test> ignoredTests = new Hashtable<String, Test>();
    /**
     * Timing helper.
     */
    private final Hashtable<String, Long> testStarts = new Hashtable<String, Long>();
    /**
     * Where to write the log to.
     */
    private OutputStream out;

    /** {@inheritDoc}. */
    public void setOutput(final OutputStream out) {
        this.out = out;
    }

    /** {@inheritDoc}. */
    public void setSystemOutput(final String out) {
        formatOutput(SYSTEM_OUT, out);
    }

    /** {@inheritDoc}. */
    public void setSystemError(final String out) {
        formatOutput(SYSTEM_ERR, out);
    }

    private void formatOutput(final String type, final String output) {
        final Element nested = doc.createElement(type);
        rootElement.appendChild(nested);
        nested.appendChild(doc.createCDATASection(output));
    }

    private static final String SYSTEM_OUT = "";
    private static final String SYSTEM_ERR = "";
    private Document doc;
    private Element rootElement;
}
