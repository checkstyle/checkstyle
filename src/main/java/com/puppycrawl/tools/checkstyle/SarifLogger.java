////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;

/**
 * Simple SARIF logger.
 * SARIF stands for the static analysis results interchange format.
 * Reference: https://sarifweb.azurewebsites.net/
 */
public class SarifLogger extends AutomaticBean implements AuditListener {

    /** The JSON Schema URL. */
    private static final String SCHEMA = "https://raw.githubusercontent.com/"
            + "oasis-tcs/sarif-spec/master/Schemata/sarif-schema-2.1.0.json";

    /** The download url for checkstyle. */
    private static final String DOWNLOAD_URL =
            "https://github.com/checkstyle/checkstyle/releases/";

    /** The length of unicode placeholder. */
    private static final int UNICODE_LENGTH = 4;

    /** Unicode escaping upper limit. */
    private static final int UNICODE_ESCAPE_UPPER_LIMIT = 0x1F;

    /** Helper writer that allows easy encoding and printing. */
    private final PrintWriter writer;

    /** Close output stream in auditFinished. */
    private final boolean closeStream;

    /** Print first error or exception. */
    private boolean firstErrorOrException = true;

    /**
     * Creates a new {@code SarifLogger} instance.
     *
     * @param outputStream where to log audit events
     * @param outputStreamOptions if {@code CLOSE} that should be closed in auditFinished()
     * @throws IllegalArgumentException if outputStreamOptions is null
     */
    public SarifLogger(OutputStream outputStream, OutputStreamOptions outputStreamOptions) {
        writer = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
        if (outputStreamOptions == null) {
            throw new IllegalArgumentException("Parameter outputStreamOptions can not be null");
        }
        closeStream = outputStreamOptions == OutputStreamOptions.CLOSE;
    }

    @Override
    protected void finishLocalSetup() {
        // No code by default
    }

    @Override
    public void auditStarted(AuditEvent event) {
        final String version = Main.class.getPackage().getImplementationVersion();
        writer.println("{");
        writer.println("  \"$schema\": \"" + SCHEMA + "\",");
        writer.println("  \"version\": \"2.1.0\",");
        writer.println("  \"runs\": [");
        writer.println("    {");
        writer.println("      \"tool\": {");
        writer.println("        \"driver\": {");
        writer.println("          \"downloadUri\": \"" + DOWNLOAD_URL + "\",");
        writer.println("          \"fullName\": \"Checkstyle\",");
        writer.println("          \"guid\": \"1826873e-87b4-11eb-87b3-0242ac130003\",");
        writer.println("          \"informationUri\": \"https://checkstyle.org/\",");
        writer.println("          \"language\": \"en\",");
        writer.println("          \"name\": \"Checkstyle\",");
        writer.println("          \"organization\": \"Checkstyle\",");
        writer.println("          \"rules\": [");
        writer.println("          ],");
        writer.println("          \"semanticVersion\": \"" + version + "\",");
        writer.println("          \"version\": \"" + version + "\"");
        writer.println("        }");
        writer.println("      },");
        writer.println("      \"results\": [");
    }

    @Override
    public void auditFinished(AuditEvent event) {
        writer.println("      ]");
        writer.println("    }");
        writer.println("  ]");
        writer.println("}");
        if (closeStream) {
            writer.close();
        }
        else {
            writer.flush();
        }
    }

    @Override
    public void addError(AuditEvent event) {
        final String severityLevel = printSeverityLevel(event.getSeverityLevel());
        if (firstErrorOrException) {
            firstErrorOrException = false;
        }
        else {
            writer.println("        ,");
        }
        writer.println("        {");
        writer.println("          \"level\": \"" + severityLevel + "\",");
        writer.println("          \"locations\": [");
        writer.println("            {");
        writer.println("              \"physicalLocation\": {");
        writer.println("                \"artifactLocation\": {");
        writer.println("                  \"uri\": \"" + event.getFileName() + "\"");
        writer.println("                },");
        writer.println("                \"region\": {");
        writer.println("                  \"startColumn\": " + event.getColumn() + ",");
        writer.println("                  \"startLine\": " + event.getLine());
        writer.println("                }");
        writer.println("              }");
        writer.println("            }");
        writer.println("          ],");
        writer.println("          \"message\": {");
        writer.println("            \"text\": \"" + escape(event.getMessage()) + "\"");
        writer.println("          },");
        writer.println("          \"ruleId\": \"" + event.getLocalizedMessage().getKey() + "\"");
        writer.println("        }");

    }

    @Override
    public void addException(AuditEvent event, Throwable throwable) {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printer = new PrintWriter(stringWriter);
        throwable.printStackTrace(printer);
        if (firstErrorOrException) {
            firstErrorOrException = false;
        }
        else {
            writer.println("        ,");
        }
        writer.println("        {");
        writer.println("          \"level\": \"error\",");
        if (event.getFileName() != null) {
            writer.println("          \"locations\": [");
            writer.println("            {");
            writer.println("              \"physicalLocation\": {");
            writer.println("                \"artifactLocation\": {");
            writer.println("                  \"uri\": \"" + event.getFileName() + "\"");
            writer.println("                }");
            writer.println("              }");
            writer.println("            }");
            writer.println("          ],");
        }
        writer.println("          \"message\": {");
        writer.println("            \"text\": \"" + escape(stringWriter.toString()) + "\"");
        writer.println("          }");
        writer.println("        }");

    }

    @Override
    public void fileStarted(AuditEvent event) {
        // No need to implement this method in this class
    }

    @Override
    public void fileFinished(AuditEvent event) {
        writer.flush();
    }

    /**
     * Print the severity level into SARIF severity level.
     *
     * @param severityLevel the Severity level.
     * @return the rendered severity level in string.
     */
    private static String printSeverityLevel(SeverityLevel severityLevel) {
        final String renderedSeverityLevel;
        switch (severityLevel) {
            case IGNORE:
                renderedSeverityLevel = "none";
                break;
            case INFO:
                renderedSeverityLevel = "note";
                break;
            case WARNING:
                renderedSeverityLevel = "warning";
                break;
            case ERROR:
            default:
                renderedSeverityLevel = "error";
                break;
        }
        return renderedSeverityLevel;
    }

    /**
     * Escape \b, \f, \n, \r, \t, \", \\ and U+0000 through U+001F.
     * Reference: https://www.ietf.org/rfc/rfc4627.txt - 2.5. Strings
     *
     * @param value the value to escape.
     * @return the escaped value if necessary.
     */
    public static String escape(String value) {
        final StringBuilder sb = new StringBuilder(value.length());
        for (int i = 0; i < value.length(); i++) {
            final char chr = value.charAt(i);
            switch (chr) {
                case '"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                default:
                    if (chr <= UNICODE_ESCAPE_UPPER_LIMIT) {
                        sb.append(escapeUnicode1F(chr));
                    }
                    else {
                        sb.append(chr);
                    }
                    break;
            }
        }
        return sb.toString();
    }

    /**
     * Escape the character between 0x00 to 0x1F in JSON.
     *
     * @param chr the character to be escaped.
     * @return the escaped string.
     */
    private static String escapeUnicode1F(char chr) {
        final StringBuilder stringBuilder = new StringBuilder(UNICODE_LENGTH + 1);
        stringBuilder.append("\\u");
        final String hexString = Integer.toHexString(chr);
        for (int i = 0; i < UNICODE_LENGTH - hexString.length(); i++) {
            stringBuilder.append('0');
        }
        stringBuilder.append(hexString.toUpperCase(Locale.US));
        return stringBuilder.toString();
    }
}
