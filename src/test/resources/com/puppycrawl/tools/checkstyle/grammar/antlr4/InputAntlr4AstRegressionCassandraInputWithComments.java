/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.puppycrawl.tools.checkstyle.grammar.antlr4;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.AssertionFailedError;
import junit.framework.Test;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.util.DOMElementWriter;
import org.apache.tools.ant.util.DateUtils;
import org.apache.tools.ant.util.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

interface IgnoredTestListener{}
interface JUnitResultFormatter{}
interface XMLConstants{}
/**
 * Prints XML output of the test to a specified Writer.
 *
 * @see FormatterElement
 */
public class InputAntlr4AstRegressionCassandraInputWithComments
        implements JUnitResultFormatter, XMLConstants, IgnoredTestListener {

    private static final double ONE_SECOND = 1000.0;

    /** constant for unnnamed testsuites/cases */
    private static final String UNKNOWN = "unknown";
    private static final String ATTR_ERRORS = "";
    private static final String TESTSUITE = "";
    private static final String SYSTEM_OUT = "";
    private static final String SYSTEM_ERR = "";

    private static DocumentBuilder getDocumentBuilder() {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (final Exception exc) {
            throw new ExceptionInInitializerError(exc);
        }
    }

    private static final String tag = System.getProperty("cassandra.testtag", "");

    /*
     * Set the property for the test suite name so that log configuration can pick it up
     * and log to a file specific to this test suite
     */
    static
    {
        String command = System.getProperty("sun.java.command");
        String args[] = command.split(" ");
        System.setProperty("suitename", args[1]);
    }

    /**
     * The XML document.
     */
    private Document doc;

    /**
     * The wrapper for the whole testsuite.
     */
    private Element rootElement;

    /**
     * Element for the current test.
     *
     * The keying of this map is a bit of a hack: tests are keyed by caseName(className) since
     * the Test we get for Test-start isn't the same as the Test we get during test-assumption-fail,
     * so we can't easily match Test objects without manually iterating over all keys and checking
     * individual fields.
     */
    private final Hashtable<String, Element> testElements = new Hashtable<String, Element>();

    /**
     * tests that failed.
     */
    private final Hashtable failedTests = new Hashtable();

    /** No arg constructor. */
    public InputAntlr4AstRegressionCassandraInputWithComments() {
    }
} // XMLJUnitResultFormatter, no newline on purpose