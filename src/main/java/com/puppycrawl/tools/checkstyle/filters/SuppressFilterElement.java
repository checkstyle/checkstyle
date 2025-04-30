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

package com.puppycrawl.tools.checkstyle.filters;

import java.util.Objects;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.Filter;

/**
 * This filter element is immutable and processes {@link AuditEvent}
 * objects based on the criteria of file, check, module id, line, and
 * column. It rejects an AuditEvent if the following match:
 * <ul>
 *   <li>the event's file name; and</li>
 *   <li>the check name or the module identifier; and</li>
 *   <li>(optionally) the event's line is in the filter's line CSV; and</li>
 *   <li>(optionally) the check's columns is in the filter's column CSV.</li>
 * </ul>
 *
 */
public class SuppressFilterElement
    implements Filter {

    /** The regexp to match file names against. */
    private final Pattern fileRegexp;

    /** The regexp to match check names against. */
    private final Pattern checkRegexp;

    /** The regexp to match message names against. */
    private final Pattern messageRegexp;

    /** Module id filter. */
    private final String moduleId;

    /** Line number filter. */
    private final CsvFilterElement lineFilter;

    /** CSV for line number filter. */
    private final String linesCsv;

    /** Column number filter. */
    private final CsvFilterElement columnFilter;

    /** CSV for column number filter. */
    private final String columnsCsv;

    /**
     * Creates a {@code SuppressFilterElement} instance.
     *
     * @param files regular expression for filtered file names
     * @param checks regular expression for filtered check classes
     * @param message regular expression for messages.
     * @param moduleId the module id
     * @param lines CSV for lines
     * @param columns CSV for columns
     */
    public SuppressFilterElement(Pattern files, Pattern checks, Pattern message, String moduleId,
            String lines, String columns) {
        fileRegexp = files;
        checkRegexp = checks;
        messageRegexp = message;
        this.moduleId = moduleId;
        if (lines == null) {
            linesCsv = null;
            lineFilter = null;
        }
        else {
            linesCsv = lines;
            lineFilter = new CsvFilterElement(lines);
        }
        if (columns == null) {
            columnsCsv = null;
            columnFilter = null;
        }
        else {
            columnsCsv = columns;
            columnFilter = new CsvFilterElement(columns);
        }
    }

    /**
     * Constructs a {@code SuppressFilterElement} using regular expressions
     * as {@code String}s. These are internally compiled into {@code Pattern}
     * objects and passed to the main constructor.
     *
     * @param files   regular expression for names of filtered files.
     * @param checks  regular expression for filtered check classes.
     * @param message regular expression for messages.
     * @param modId   the id
     * @param lines   lines CSV values and ranges for line number filtering.
     * @param columns columns CSV values and ranges for column number filtering.
     */
    public SuppressFilterElement(String files, String checks,
                                 String message, String modId, String lines, String columns) {
        this(toPattern(files), toPattern(checks), toPattern(message),
                modId, lines, columns);
    }

    /**
     * Converts a string into a compiled {@code Pattern}, or return {@code null}
     * if input is {@code null}.
     *
     * @param regex the regular expression as a string, may be {@code null}.
     * @return the compiled {@code Pattern}, or {@code null} if input is {@code null}.
     */
    private static Pattern toPattern(String regex) {
        final Pattern result;
        if (regex != null) {
            result = Pattern.compile(regex);
        }
        else {
            result = null;
        }
        return result;
    }

    @Override
    public boolean accept(AuditEvent event) {
        return !isFileNameAndModuleNameMatching(event)
                || !isMessageNameMatching(event)
                || !isLineAndColumnMatching(event);
    }

    /**
     * Is matching by file name, module id, and Check name.
     *
     * @param event event
     * @return true if it is matching
     */
    private boolean isFileNameAndModuleNameMatching(AuditEvent event) {
        return event.getFileName() != null
                && (fileRegexp == null || fileRegexp.matcher(event.getFileName()).find())
                && event.getViolation() != null
                && (moduleId == null || moduleId.equals(event.getModuleId()))
                && (checkRegexp == null || checkRegexp.matcher(event.getSourceName()).find());
    }

    /**
     * Is matching by message.
     *
     * @param event event
     * @return true if it is matching or not set.
     */
    private boolean isMessageNameMatching(AuditEvent event) {
        return messageRegexp == null || messageRegexp.matcher(event.getMessage()).find();
    }

    /**
     * Whether line and column match.
     *
     * @param event event to process.
     * @return true if line and column are matching or not set.
     */
    private boolean isLineAndColumnMatching(AuditEvent event) {
        return lineFilter == null && columnFilter == null
                || lineFilter != null && lineFilter.accept(event.getLine())
                || columnFilter != null && columnFilter.accept(event.getColumn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPatternSafely(fileRegexp), getPatternSafely(checkRegexp),
                getPatternSafely(messageRegexp), moduleId, linesCsv, columnsCsv);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final SuppressFilterElement suppressElement = (SuppressFilterElement) other;
        return Objects.equals(getPatternSafely(fileRegexp),
                    getPatternSafely(suppressElement.fileRegexp))
                && Objects.equals(getPatternSafely(checkRegexp),
                    getPatternSafely(suppressElement.checkRegexp))
                && Objects.equals(getPatternSafely(messageRegexp),
                    getPatternSafely(suppressElement.messageRegexp))
                && Objects.equals(moduleId, suppressElement.moduleId)
                && Objects.equals(linesCsv, suppressElement.linesCsv)
                && Objects.equals(columnsCsv, suppressElement.columnsCsv);
    }

    /**
     * Util method to get pattern String value from Pattern object safely, return null if
     * pattern object is null.
     *
     * @param pattern pattern object
     * @return value of pattern or null
     */
    private static String getPatternSafely(Pattern pattern) {
        String result = null;
        if (pattern != null) {
            result = pattern.pattern();
        }
        return result;
    }
}
