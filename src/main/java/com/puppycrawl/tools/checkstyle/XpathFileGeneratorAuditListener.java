///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle;

import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;

/**
 * Generates <b>suppressions.xml</b> file, based on violations occurred.
 * See issue <a href="https://github.com/checkstyle/checkstyle/issues/102">#102</a>
 */
public class XpathFileGeneratorAuditListener
        extends AbstractAutomaticBean
        implements AuditListener {

    /** The " quote character. */
    private static final String QUOTE_CHAR = "\"";

    /**
     * Helper writer that allows easy encoding and printing.
     */
    private final PrintWriter writer;

    /** Close output stream in auditFinished. */
    private final boolean closeStream;

    /** Determines if xml header is printed. */
    private boolean isXmlHeaderPrinted;

    /**
     * Creates a new {@code SuppressionFileGenerator} instance.
     * Sets the output to a defined stream.
     *
     * @param out the output stream
     * @param outputStreamOptions if {@code CLOSE} stream should be closed in auditFinished()
     * @throws IllegalArgumentException if input is null
     */
    @SuppressWarnings("CT_CONSTRUCTOR_THROW")
    public XpathFileGeneratorAuditListener(OutputStream out,
                                           OutputStreamOptions outputStreamOptions) {
        if (outputStreamOptions == null) {
            throw new IllegalArgumentException("outputStreamOptions parameter should not be null");
        }
        writer = new PrintWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
        closeStream = outputStreamOptions == OutputStreamOptions.CLOSE;
    }

    @Override
    public void auditStarted(AuditEvent event) {
        // No code by default
    }

    @Override
    public void auditFinished(AuditEvent event) {
        if (isXmlHeaderPrinted) {
            writer.println("</suppressions>");
        }

        writer.flush();
        if (closeStream) {
            writer.close();
        }
    }

    @Override
    public void fileStarted(AuditEvent event) {
        // No code by default
    }

    @Override
    public void fileFinished(AuditEvent event) {
        // No code by default
    }

    @Override
    public void addError(AuditEvent event) {
        final String xpathQuery = XpathFileGeneratorAstFilter.findCorrespondingXpathQuery(event);
        if (xpathQuery != null) {
            printXmlHeader();

            final File file = new File(event.getFileName());

            writer.println("<suppress-xpath");
            writer.print("       files=\"");
            writer.print(file.getName());
            writer.println(QUOTE_CHAR);

            if (event.getModuleId() == null) {
                final String checkName =
                        PackageObjectFactory.getShortFromFullModuleNames(event.getSourceName());
                writer.print("       checks=\"");
                writer.print(checkName);
            }
            else {
                writer.print("       id=\"");
                writer.print(event.getModuleId());
            }
            writer.println(QUOTE_CHAR);

            writer.print("       query=\"");
            writer.print(xpathQuery);

            writer.println("\"/>");
        }
    }

    @Override
    public void addException(AuditEvent event, Throwable throwable) {
        throw new UnsupportedOperationException("Operation is not supported");
    }

    /**
     * Prints XML header if only it was not printed before.
     */
    private void printXmlHeader() {
        if (!isXmlHeaderPrinted) {
            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.println("<!DOCTYPE suppressions PUBLIC");
            writer.println("    \"-//Checkstyle//DTD SuppressionXpathFilter Experimental "
                    + "Configuration 1.2//EN\"");
            writer.println("    \"https://checkstyle.org/dtds/"
                    + "suppressions_1_2_xpath_experimental.dtd\">");
            writer.println("<suppressions>");
            isXmlHeaderPrinted = true;
        }
    }

    @Override
    protected void finishLocalSetup() {
        // No code by default
    }
}
