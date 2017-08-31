////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

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
// -@cs[AbbreviationAsWordInName] We can not change it as,
// check's name is part of API (used in configurations).
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

    /** The writer lock object. */
    private final Object writerLock = new Object();

    /** Holds all messages for the given file. */
    private final Map<String, FileMessages> fileMessages =
            new ConcurrentHashMap<>();

    /**
     * Helper writer that allows easy encoding and printing.
     */
    private final PrintWriter writer;

    /**
     * Creates a new {@code XMLLogger} instance.
     * Sets the output to a defined stream.
     * @param outputStream the stream to write logs to.
     * @param closeStream close oS in auditFinished
     * @deprecated in order to fullfil demands of BooleanParameter IDEA check.
     * @noinspection BooleanParameter
     */
    @Deprecated
    public XMLLogger(OutputStream outputStream, boolean closeStream) {
        writer = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
        this.closeStream = closeStream;
    }

    /**
     * Creates a new {@code XMLLogger} instance.
     * Sets the output to a defined stream.
     * @param outputStream the stream to write logs to.
     * @param outputStreamOptions if {@code CLOSE} stream should be closed in auditFinished()
     */
    public XMLLogger(OutputStream outputStream, OutputStreamOptions outputStreamOptions) {
        writer = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
        closeStream = outputStreamOptions == OutputStreamOptions.CLOSE;
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
        fileMessages.forEach(this::writeFileMessages);

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
        fileMessages.put(event.getFileName(), new FileMessages());
    }

    @Override
    public void fileFinished(AuditEvent event) {
        final String fileName = event.getFileName();
        final FileMessages messages = fileMessages.get(fileName);

        synchronized (writerLock) {
            writeFileMessages(fileName, messages);
        }

        fileMessages.remove(fileName);
    }

    /**
     * Prints the file section with all file errors and exceptions.
     * @param fileName The file name, as should be printed in the opening file tag.
     * @param messages The file messages.
     */
    private void writeFileMessages(String fileName, FileMessages messages) {
        writeFileOpeningTag(fileName);
        if (messages != null) {
            for (AuditEvent errorEvent : messages.getErrors()) {
                writeFileError(errorEvent);
            }
            for (Throwable exception : messages.getExceptions()) {
                writeException(exception);
            }
        }
        writeFileClosingTag();
    }

    /**
     * Prints the "file" opening tag with the given filename.
     * @param fileName The filename to output.
     */
    private void writeFileOpeningTag(String fileName) {
        writer.println("<file name=\"" + encode(fileName) + "\">");
    }

    /**
     * Prints the "file" closing tag.
     */
    private void writeFileClosingTag() {
        writer.println("</file>");
    }

    @Override
    public void addError(AuditEvent event) {
        if (event.getSeverityLevel() != SeverityLevel.IGNORE) {
            final String fileName = event.getFileName();
            if (fileName == null) {
                synchronized (writerLock) {
                    writeFileError(event);
                }
            }
            else {
                final FileMessages messages = fileMessages.computeIfAbsent(
                        fileName, name -> new FileMessages());
                messages.addError(event);
            }
        }
    }

    /**
     * Outputs the given envet to the writer.
     * @param event An event to print.
     */
    private void writeFileError(AuditEvent event) {
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
        writer.print(" source=\"");
        if (event.getModuleId() == null) {
            writer.print(encode(event.getSourceName()));
        }
        else {
            writer.print(encode(event.getModuleId()));
        }
        writer.println("\"/>");
    }

    @Override
    public void addException(AuditEvent event, Throwable throwable) {
        final String fileName = event.getFileName();
        if (fileName == null) {
            synchronized (writerLock) {
                writeException(throwable);
            }
        }
        else {
            final FileMessages messages = fileMessages.computeIfAbsent(
                    fileName, name -> new FileMessages());
            messages.addException(throwable);
        }
    }

    /**
     * Writes the exception event to the print writer.
     * @param throwable The
     */
    private void writeException(Throwable throwable) {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printer = new PrintWriter(stringWriter);
        printer.println("<exception>");
        printer.println("<![CDATA[");
        throwable.printStackTrace(printer);
        printer.println("]]>");
        printer.println("</exception>");
        writer.println(encode(stringWriter.toString()));
    }

    /**
     * Escape &lt;, &gt; &amp; &#39; and &quot; as their entities.
     * @param value the value to escape.
     * @return the escaped value if necessary.
     */
    public static String encode(String value) {
        final StringBuilder sb = new StringBuilder(256);
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
                case '\r':
                    break;
                case '\n':
                    sb.append("&#10;");
                    break;
                default:
                    sb.append(chr);
                    break;
            }
        }
        return sb.toString();
    }

    /**
     * Finds whether the given argument is character or entity reference.
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
        if (nextSemi == -1
            || !isReference(value.substring(ampPosition, nextSemi + 1))) {
            result = "&amp;";
        }
        else {
            result = "&";
        }
        return result;
    }

    /**
     * The registered file messages.
     */
    private static class FileMessages {
        /** The file error events. */
        private final List<AuditEvent> errors = Collections.synchronizedList(new ArrayList<>());

        /** The file exceptions. */
        private final List<Throwable> exceptions = Collections.synchronizedList(new ArrayList<>());

        /**
         * Returns the file error events.
         * @return the file error events.
         */
        public List<AuditEvent> getErrors() {
            return Collections.unmodifiableList(errors);
        }

        /**
         * Adds the given error event to the messages.
         * @param event the error event.
         */
        public void addError(AuditEvent event) {
            errors.add(event);
        }

        /**
         * Returns the file exceptions.
         * @return the file exceptions.
         */
        public List<Throwable> getExceptions() {
            return Collections.unmodifiableList(exceptions);
        }

        /**
         * Adds the given exception to the messages.
         * @param throwable the file exception
         */
        public void addException(Throwable throwable) {
            exceptions.add(throwable);
        }
    }
}
