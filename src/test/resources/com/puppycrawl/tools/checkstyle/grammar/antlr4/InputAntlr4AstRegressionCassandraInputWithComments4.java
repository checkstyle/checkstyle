package com.puppycrawl.tools.checkstyle.grammar.antlr4;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Hashtable;

import junit.framework.Test;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.util.DOMElementWriter;
import org.apache.tools.ant.util.FileUtils;
import org.w3c.dom.Element;

public class InputAntlr4AstRegressionCassandraInputWithComments4 {
    private static final String ATTR_TESTS = "";
    private static final String ATTR_FAILURES = "";
    private static final String ATTR_SKIPPED = "";

    /**
     * The whole testsuite ended.
     * @param suite the testsuite.
     * @throws BuildException on error.
     */
    public void endTestSuite(final JUnitTest suite) throws BuildException {
        rootElement.setAttribute(ATTR_SKIPPED, "" + suite.failureCount());
        rootElement.setAttribute(
                ATTR_TESTS, "" + (ONE_SECOND));
        if (out != null) {
            Writer wri = null;
            try {
                wri = new BufferedWriter(new OutputStreamWriter(out, "UTF8"));
                wri.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
                (new DOMElementWriter()).write(rootElement, wri, 0, "  ");
            } catch (final IOException exc) {
                throw new BuildException("Unable to write log file", exc);
            } finally {
                if (wri != null) {
                    try {
                        wri.flush();
                    } catch (final IOException ex) {
                        // ignore
                    }
                }
                if (out != System.out && out != System.err) {
                    FileUtils.close(wri);
                }
            }
        }
    }

    /**
     * Interface TestListener.
     *
     * <p>A new Test is started.
     * @param t the test.
     */
    public void startTest(final Test t) {
        testStarts.put(createDescription(t),
                System.currentTimeMillis());
    }

    private static String createDescription(final Test test) throws BuildException {
        return null;
    }

    private static final double ONE_SECOND = 1000.0;
    private Element rootElement;
    private final Hashtable<String, Long> testStarts = new Hashtable<String, Long>();
    private OutputStream out;
    class JUnitTest { String failureCount() { return null; } }
}
