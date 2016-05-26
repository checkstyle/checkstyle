////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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
import java.util.ResourceBundle;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * Simple XML logger.
 * It outputs everything in UTF-8 (default XML encoding is UTF-8) in case
 * we want to localize error messages or simply that file names are
 * localized and takes care about escaping as well.

 * @author <a href="mailto:stephane.bailliez@wanadoo.fr">Stephane Bailliez</a>
 */
public class XMLLogger
    extends AutomaticBean
    implements AuditListener {
    /** Decimal radix. */
    private static final int BASE_10 = 10;

    /** Hex radix. */
    private static final int BASE_16 = 16;

    /** Some known entities to detect. */
    private static final String[] ENTITIES = {"gt", "amp", "lt", "apos",
                                              "quot", };

    /** Close output stream in auditFinished. */
    private final boolean closeStream;

    /** Helper writer that allows easy encoding and printing. */
    private PrintWriter writer;

    /**
     * Creates a new {@code XMLLogger} instance.
     * Sets the output to a defined stream.
     * @param outputStream the stream to write logs to.
     * @param closeStream close oS in auditFinished
     */
    public XMLLogger(OutputStream outputStream, boolean closeStream) {
        setOutputStream(outputStream);
        this.closeStream = closeStream;
    }

    /**
     * Sets the OutputStream.
     * @param outputStream the OutputStream to use
     **/
    private void setOutputStream(OutputStream outputStream) {
        final OutputStreamWriter osw = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
        writer = new PrintWriter(osw);
    }

    @Override
    public void auditStarted(AuditEvent event) {
        writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

        final ResourceBundle compilationProperties =
            ResourceBundle.getBundle("checkstylecompilation", Locale.ROOT);
        final String version =
            compilationProperties.getString("checkstyle.compile.version");

        writer.println("<checkstyle version=\"" + version + "\">");
    }

    @Override
    public void auditFinished(AuditEvent event) {
        writer.println("</checkstyle>");
        if (closeStream) {
            writer.close();
        }
        else {
            writer.flush();
        }
    }

    @Override
    public void fileStarted(AuditEvent event) {
        writer.println("<file name=\"" + encode(event.getFileName()) + "\">");
    }

    @Override
    public void fileFinished(AuditEvent event) {
        writer.println("</file>");
    }

    @Override
    public void addError(AuditEvent event) {
        if (event.getSeverityLevel() != SeverityLevel.IGNORE) {
            writer.print("<error" + " line=\"" + event.getLine() + "\"");
            if (event.getColumn() > 0) {
                writer.print(" column=\"" + event.getColumn() + "\"");
            }
            writer.print(" severity=\""
                + event.getSeverityLevel().getName()
                + "\"");
            writer.print(" message=\""
                + encode(event.getMessage())
                + "\"");
            writer.println(" source=\""
                + encode(event.getSourceName())
                + "\"/>");
        }
    }

    @Override
    public void addException(AuditEvent event, Throwable throwable) {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printer = new PrintWriter(stringWriter);
        printer.println("<exception>");
        printer.println("<![CDATA[");
        throwable.printStackTrace(printer);
        printer.println("]]>");
        printer.println("</exception>");
        printer.flush();
        writer.println(encode(stringWriter.toString()));
    }

    /**
     * Escape &lt;, &gt; &amp; &#39; and &quot; as their entities.
     * @param value the value to escape.
     * @return the escaped value if necessary.
     */
    public static String encode(String value) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            final char chr = value.charAt(i);
            switch (chr) {
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                case '\'':
                    sb.append("&apos;");
                    break;
                case '\"':
                    sb.append("&quot;");
                    break;
                case '&':
                    sb.append(encodeAmpersand(value, i));
                    break;
                default:
                    sb.append(chr);
                    break;
            }
        }
        return sb.toString();
    }

    /**
     * @param ent the possible entity to look for.
     * @return whether the given argument a character or entity reference
     */
    public static boolean isReference(String ent) {
        boolean reference = false;

        if (ent.charAt(0) != '&' || !CommonUtils.endsWithChar(ent, ';')) {
            reference = false;
        }
        else if (ent.charAt(1) == '#') {
            // prefix is "&#"
            int prefixLength = 2;

            int radix = BASE_10;
            if (ent.charAt(2) == 'x') {
                prefixLength++;
                radix = BASE_16;
            }
            try {
                Integer.parseInt(
                    ent.substring(prefixLength, ent.length() - 1), radix);
                reference = true;
            }
            catch (final NumberFormatException ignored) {
                reference = false;
            }
        }
        else {
            final String name = ent.substring(1, ent.length() - 1);
            for (String element : ENTITIES) {
                if (name.equals(element)) {
                    reference = true;
                    break;
                }
            }
        }
        return reference;
    }

    /**
     * Encodes ampersand in value at required position.
     * @param value string value, which contains ampersand
     * @param ampPosition position of ampersand in value
     * @return encoded ampersand which should be used in xml
     */
    private static String encodeAmpersand(String value, int ampPosition) {
        final int nextSemi = value.indexOf(';', ampPosition);
        final String result;
        if (nextSemi < 0
            || !isReference(value.substring(ampPosition, nextSemi + 1))) {
            result = "&amp;";
        }
        else {
            result = "&";
        }
        return result;
    }
}
