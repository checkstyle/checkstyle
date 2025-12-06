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

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;

/**
 * Generates <b>suppressions.xml</b> file, based on violations occurred.
 * See issue <a href="https://github.com/checkstyle/checkstyle/issues/5983">#5983</a>
 */
public final class ChecksAndFilesSuppressionFileGeneratorAuditListener
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

    /**
     * Collects the check names corrosponds to file name.
     */
    private final Map<Path, Set<String>> filesAndChecksCollector = new HashMap<>();

    /**
     * Collects the module ids corrosponds to file name.
     */
    private final Map<Path, Set<String>> filesAndModuleIdCollector = new HashMap<>();

    /** Determines if xml header is printed. */
    private boolean isXmlHeaderPrinted;

    /**
     * Creates a new {@code ChecksAndFilesSuppressionFileGeneratorAuditListener} instance.
     * Sets the output to a defined stream.
     *
     * @param out the output stream
     * @param outputStreamOptions if {@code CLOSE} stream should be closed in auditFinished()
     * @throws IllegalArgumentException if outputStreamOptions is null.
     */
    public ChecksAndFilesSuppressionFileGeneratorAuditListener(OutputStream out,
                                           OutputStreamOptions outputStreamOptions) {
        if (outputStreamOptions == null) {
            throw new IllegalArgumentException("Parameter outputStreamOptions can not be null");
        }

        writer = new PrintWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
        closeStream = outputStreamOptions == OutputStreamOptions.CLOSE;
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
    public void addError(AuditEvent event) {
        printXmlHeader();

        final Path path = Path.of(event.getFileName());
        final Path fileName = path.getFileName();
        final String checkName =
                PackageObjectFactory.getShortFromFullModuleNames(event.getSourceName());
        final String moduleIdName = event.getModuleId();

        final boolean isAlreadyPresent;

        if (fileName != null) {
            if (moduleIdName == null) {
                isAlreadyPresent = isFileAndCheckNamePresent(fileName, checkName);
            }
            else {
                isAlreadyPresent = isFileAndModuleIdPresent(fileName, moduleIdName);
            }
        }
        else {
            isAlreadyPresent = true;
        }

        if (!isAlreadyPresent) {
            suppressXmlWriter(fileName, checkName, moduleIdName);
        }
    }

    /**
     * Checks whether the check name is already associated with the given file
     * in the {@code FilesAndChecksCollector} map.
     *
     * @param fileName The path of the file where the violation occurred.
     * @param checkName The name of the check that triggered the violation.
     * @return {@code true} if the collector already contains the check name for the file,
     *     {@code false} otherwise.
     */
    private boolean isFileAndCheckNamePresent(Path fileName, String checkName) {
        boolean isPresent = false;
        final Set<String> checks = filesAndChecksCollector.get(fileName);
        if (checks != null) {
            isPresent = checks.contains(checkName);
        }
        return isPresent;
    }

    /**
     * Checks the {@code FilesAndModuleIdCollector} map to see if the module ID has
     * already been recorded for the specified file.
     *
     * @param fileName The path of the file where the violation occurred.
     * @param moduleIdName The module ID associated with the check name which trigger violation.
     * @return {@code true} if the module ID is not yet recorded for the file,
     *     {@code false} otherwise.
     */
    private boolean isFileAndModuleIdPresent(Path fileName, String moduleIdName) {
        boolean isPresent = false;
        final Set<String> moduleIds = filesAndModuleIdCollector.get(fileName);
        if (moduleIds != null) {
            isPresent = moduleIds.contains(moduleIdName);
        }
        return isPresent;
    }

    @Override
    public void addException(AuditEvent event, Throwable throwable) {
        throw new UnsupportedOperationException("Operation is not supported");
    }

    /**
     * Prints XML suppression with check/id and file name.
     *
     * @param fileName The file path associated with the check or module ID.
     * @param checkName The check name to write if {@code moduleIdName} is {@code null}.
     * @param moduleIdName The module ID name to write if {@code null}, {@code checkName} is
     *     used instead.
     */
    private void suppressXmlWriter(Path fileName, String checkName, String moduleIdName) {
        writer.println("  <suppress");
        writer.print("      files=\"");
        writer.print(fileName);
        writer.println(QUOTE_CHAR);

        if (moduleIdName == null) {
            writer.print("      checks=\"");
            writer.print(checkName);
        }
        else {
            writer.print("      id=\"");
            writer.print(moduleIdName);
        }
        writer.println("\"/>");
        addCheckOrModuleId(fileName, checkName, moduleIdName);
    }

    /**
     * Adds either the check name or module ID to the corresponding collector map
     * for the specified file path.
     *
     * @param fileName The path of the file associated with the check or module ID.
     * @param checkName The name of the check to add if {@code moduleIdName} is {@code null}.
     * @param moduleIdName The name of the module ID to add if {@code null}, {@code checkName} is
     *     used instead.
     */
    private void addCheckOrModuleId(Path fileName, String checkName, String moduleIdName) {
        if (moduleIdName == null) {
            addToCollector(filesAndChecksCollector, fileName, checkName);
        }
        else {
            addToCollector(filesAndModuleIdCollector, fileName, moduleIdName);
        }
    }

    /**
     * Adds a value (either a check name or module ID) to the set associated with the given file
     * in the specified collector map.
     *
     * @param collector The map that collects values (check names or module IDs) for each file.
     * @param fileName The file path for which the value should be recorded.
     * @param value the check name or module ID to add to the set for the specified file.
     */
    private static void addToCollector(Map<Path, Set<String>> collector,
        Path fileName, String value) {
        final Set<String> values = collector.computeIfAbsent(fileName, key -> new HashSet<>());
        values.add(value);
    }

    /**
     * Prints XML header if only it was not printed before.
     */
    private void printXmlHeader() {
        if (!isXmlHeaderPrinted) {
            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.println("<!DOCTYPE suppressions PUBLIC");
            writer.println("    \"-//Checkstyle//DTD SuppressionFilter Configuration 1.2//EN\"");
            writer.println("    \"https://checkstyle.org/dtds/suppressions_1_2.dtd\">");
            writer.println("<suppressions>");
            isXmlHeaderPrinted = true;
        }
    }

    @Override
    protected void finishLocalSetup() {
        // No code by default
    }
}
