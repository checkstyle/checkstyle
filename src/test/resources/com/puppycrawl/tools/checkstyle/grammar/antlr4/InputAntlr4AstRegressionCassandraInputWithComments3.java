package com.puppycrawl.tools.checkstyle.grammar.antlr4;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;

import org.apache.tools.ant.util.DateUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class InputAntlr4AstRegressionCassandraInputWithComments3 {
    /**
     * The whole testsuite started.
     * @param suite the testsuite.
     */
    public void startTestSuite(final JUnitTest suite) {
        doc = getDocumentBuilder().newDocument();
        rootElement = doc.createElement(TESTSUITE);
        String n = suite.getName();
        if (n != null && !tag.isEmpty())
            n = n + "-" + tag;
        rootElement.setAttribute(ATTR_NAME, n == null ? UNKNOWN : n);

        //add the timestamp
        final String timestamp = DateUtils.format(new Date(),
                DateUtils.ISO8601_DATETIME_PATTERN);
        rootElement.setAttribute(TIMESTAMP, timestamp);
        //and the hostname.
        rootElement.setAttribute(HOSTNAME, getHostname());

        // Output properties
        final Element propsElement = doc.createElement(PROPERTIES);
        rootElement.appendChild(propsElement);
        final Properties props = suite.getProperties();
        if (props != null) {
            final Enumeration e = props.propertyNames();
            while (e.hasMoreElements()) {
                final String name = (String) e.nextElement();
                final Element propElement = doc.createElement(PROPERTY);
                propElement.setAttribute(ATTR_NAME, name);
                propElement.setAttribute(ATTR_VALUE, props.getProperty(name));
                propsElement.appendChild(propElement);
            }
        }
    }

    private static final String PROPERTIES = "";
    private static final String HOSTNAME = "";
    private static final String TIMESTAMP = "";
    private static final String ATTR_NAME = "";
    private static final String PROPERTY = "";
    private static final String ATTR_VALUE = "";

    class JUnitTest {
        public String getName() {
            return null;
        }

        public Properties getProperties() {
            return null;
        }

        public String failureCount() {
            return null;
        }

        public String getRunTime() {
            return null;
        }
    }

    /**
     * get the local hostname
     * @return the name of the local host, or "localhost" if we cannot work it out
     */
    private String getHostname()  {
        String hostname = "localhost";
        try {
            final InetAddress localHost = InetAddress.getLocalHost();
            if (localHost != null) {
                hostname = localHost.getHostName();
            }
        } catch (final UnknownHostException e) {
            // fall back to default 'localhost'
        }
        return hostname;
    }

    private static final String UNKNOWN = "unknown";
    private static final String TESTSUITE = "";
    private static final String tag = System.getProperty("cassandra.testtag", "");
    private Document doc;
    private Element rootElement;
    private static DocumentBuilder getDocumentBuilder() { return null; }
}
