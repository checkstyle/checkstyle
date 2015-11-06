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

package com.puppycrawl.tools.checkstyle.checks.regexp;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import com.google.common.io.Files;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * <p>
 * Implementation of a check that looks for a file name and/or path match (or
 * mis-match) against specified patterns. It can also be used to verify files
 * match specific naming patterns not covered by other checks (Ex: properties,
 * xml, etc.).
 * </p>
 *
 * <p>
 * When customizing the check, the properties are applied in a specific order.
 * The fileExtensions property first picks only files that match any of the
 * specific extensions supplied. Once files are matched against the
 * fileExtensions, the match property is then used in conjuction with the
 * patterns to determine if the check is looking for a match or mis-match on
 * those files. If the fileNamePattern is supplied, the matching is only applied
 * to the fileNamePattern and not the folderPattern. If no fileNamePattern is
 * supplied, then matching is applied to the folderPattern only and will result
 * in all files in a folder to be reported on violations. If no folderPattern is
 * supplied, then all folders that checkstyle finds are examined for violations.
 * The ignoreFileNameExtensions property drops the file extension and applies
 * the fileNamePattern only to the rest of file name. For example, if the file
 * is named 'test.java' and this property is turned on, the pattern is only
 * applied to 'test'.
 * </p>
 *
 * <p>
 * If this check is configured with no properties, then the default behavior of
 * this check is to report file names with spaces in them. When at least one
 * pattern property is supplied, the entire check is under the user's control to
 * allow them to fully customize the behavior.
 * </p>
 *
 * <p>
 * It is recommended that if you create your own pattern, to also specify a
 * custom error message. This allows the error message printed to be clear what
 * the violation is, especially if multiple RegexpOnFilename checks are used.
 * Argument 0 for the message populates the check's folderPattern. Argument 1
 * for the message populates the check's fileNamePattern. The file name is not
 * passed as an argument since it is part of CheckStyle's default error
 * messages.
 * </p>
 *
 * <p>
 * Check have following options:
 * </p>
 * <ul>
 * <li>
 * folderPattern - Regular expression to match the folder path against. Default
 * value is null.</li>
 *
 * <li>
 * fileNamePattern - Regular expression to match the file name against. Default
 * value is null.</li>
 *
 * <li>
 * match - Whether to look for a match or mis-match on the file name, if the
 * fileNamePattern is supplied, otherwise it is applied on the folderPattern.
 * Default value is true.</li>
 *
 * <li>
 * ignoreFileNameExtensions - Whether to ignore the file extension for the file
 * name match. Default value is false.</li>
 *
 * <li>
 * fileExtensions - File type extension of files to process. If this is
 * specified, then only files that match these types are examined with the other
 * patterns. Default value is {}.</li>
 * </ul>
 * <br>
 *
 * <p>
 * To configure the check to report file names that contain a space:
 * </p>
 *
 * <pre>
 * &lt;module name=&quot;RegexpOnFilename&quot;/&gt;
 * </pre>
 * <p>
 * To configure the check to force picture files to not be 'gif':
 * </p>
 *
 * <pre>
 * &lt;module name=&quot;RegexpOnFilename&quot;&gt;
 *   &lt;property name=&quot;fileNamePattern&quot; value=&quot;\\.gif$&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * OR:
 * </p>
 *
 * <pre>
 * &lt;module name=&quot;RegexpOnFilename&quot;&gt;
 *   &lt;property name=&quot;fileNamePattern&quot; value=&quot;.&quot;/&gt;
 *   &lt;property name=&quot;fileExtensions&quot; value=&quot;gif&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * <p>
 * To configure the check to only allow property and xml files to be located in
 * the resource folder:
 * </p>
 *
 * <pre>
 * &lt;module name=&quot;RegexpOnFilename&quot;&gt;
 *   &lt;property name=&quot;folderPattern&quot;
 *     value=&quot;[\\/]src[\\/]\\w+[\\/]resources[\\/]&quot;/&gt;
 *   &lt;property name=&quot;match&quot; value=&quot;false&quot;/&gt;
 *   &lt;property name=&quot;fileExtensions&quot; value=&quot;properties, xml&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * <p>
 * To configure the check to only allow Java and XML files in your folders use
 * the below.
 * </p>
 *
 * <pre>
 * &lt;module name=&quot;RegexpOnFilename&quot;&gt;
 *   &lt;property name=&quot;fileNamePattern&quot; value=&quot;\\.(java|xml)$&quot;/&gt;
 *   &lt;property name=&quot;match&quot; value=&quot;false&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * To configure the check to only allow Java and XML files only in your source
 * folder and ignore any other folders:
 * </p>
 *
 * <p>
 * <b>Note:</b> 'folderPattern' must be specified if checkstyle is analyzing
 * more than the normal source folder, like the 'bin' folder where class files
 * can be located.
 * </p>
 *
 * <pre>
 * &lt;module name=&quot;RegexpOnFilename&quot;&gt;
 *   &lt;property name=&quot;folderPattern&quot; value=&quot;[\\/]src[\\/]&quot;/&gt;
 *   &lt;property name=&quot;fileNamePattern&quot; value=&quot;\\.(java|xml)$&quot;/&gt;
 *   &lt;property name=&quot;match&quot; value=&quot;false&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * To configure the check to only allow file names to be camel case:
 * </p>
 *
 * <pre>
 * &lt;module name=&quot;RegexpOnFilename&quot;&gt;
 *   &lt;property name=&quot;fileNamePattern&quot;
 *     value=&quot;^([A-Z][a-z0-9]+\.?)+$&quot;/&gt;
 *   &lt;property name=&quot;match&quot; value=&quot;false&quot;/&gt;
 *   &lt;property name=&quot;ignoreFileNameExtensions&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @author Richard Veach
 */
public class RegexpOnFilenameCheck extends AbstractFileSetCheck {
    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_MATCH = "regexp.filename.match";
    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_MISMATCH = "regexp.filename.mismatch";

    /** Compiled regexp to match a folder. */
    private Pattern folderPattern;
    /** Compiled regexp to match a file. */
    private Pattern fileNamePattern;
    /** Whether to look for a file name match or mismatch. */
    private boolean match = true;
    /** Whether to ignore the file's extension when looking for matches. */
    private boolean ignoreFileNameExtensions;

    /**
     * Setter for folder format.
     *
     * @param folderPattern format of folder.
     * @throws org.apache.commons.beanutils.ConversionException if unable to
     *         create Pattern object.
     */
    public void setFolderPattern(String folderPattern) {
        this.folderPattern = CommonUtils.createPattern(folderPattern);
    }

    /**
     * Setter for file name format.
     *
     * @param fileNamePattern format of file.
     * @throws org.apache.commons.beanutils.ConversionException if unable to
     *         create Pattern object.
     */
    public void setFileNamePattern(String fileNamePattern) {
        this.fileNamePattern = CommonUtils.createPattern(fileNamePattern);
    }

    /**
     * Sets whether the check should look for a file name match or mismatch.
     *
     * @param match check's option for matching file names.
     */
    public void setMatch(boolean match) {
        this.match = match;
    }

    /**
     * Sets whether file name matching should drop the file extension or not.
     *
     * @param ignoreFileNameExtensions check's option for ignoring file extension.
     */
    public void setIgnoreFileNameExtensions(boolean ignoreFileNameExtensions) {
        this.ignoreFileNameExtensions = ignoreFileNameExtensions;
    }

    @Override
    public void init() {
        if (fileNamePattern == null && folderPattern == null) {
            fileNamePattern = CommonUtils.createPattern("\\s");
        }
    }

    @Override
    protected void processFiltered(File file, List<String> lines) throws CheckstyleException {
        final String fileName = getFileName(file);
        final String folderPath = getFolderPath(file);

        if (isMatchFolder(folderPath) && isMatchFile(fileName)) {
            log();
        }
    }

    /**
     * Retrieves the file name from the given {@code file}.
     *
     * @param file Input file to examine.
     * @return The file name.
     */
    private String getFileName(File file) {
        String fileName = file.getName();

        if (ignoreFileNameExtensions) {
            fileName = Files.getNameWithoutExtension(fileName);
        }

        return fileName;
    }

    /**
     * Retrieves the folder path from the given {@code file}.
     *
     * @param file Input file to examine.
     * @return The folder path.
     * @throws CheckstyleException if there is an error getting the canonical
     *         path of the {@code file}.
     */
    private static String getFolderPath(File file) throws CheckstyleException {
        try {
            return file.getParentFile().getCanonicalPath();
        }
        catch (IOException ex) {
            throw new CheckstyleException("unable to create canonical path names for "
                    + file.getAbsolutePath(), ex);
        }
    }

    /**
     * Checks if the given {@code folderPath} matches the specified
     * {@link #folderPattern}.
     *
     * @param folderPath Input folder path to examine.
     * @return true if they do match.
     */
    private boolean isMatchFolder(String folderPath) {
        final boolean result;

        // null pattern always matches, regardless of value of 'match'
        if (folderPattern == null) {
            result = true;
        }
        else {
            final boolean useMatch;

            // null pattern means 'match' applies to the folderPattern matching
            if (fileNamePattern == null) {
                useMatch = match;
            }
            else {
                useMatch = true;
            }

            result = folderPattern.matcher(folderPath).find() == useMatch;
        }

        return result;
    }

    /**
     * Checks if the given {@code fileName} matches the specified
     * {@link #fileNamePattern}.
     *
     * @param fileName Input file name to examine.
     * @return true if they do match.
     */
    private boolean isMatchFile(String fileName) {
        final boolean result;

        // null pattern always matches, regardless of value of 'match'
        if (fileNamePattern == null) {
            result = true;
        }
        else {
            result = fileNamePattern.matcher(fileName).find() == match;
        }

        return result;
    }

    /** Logs the errors for the check. */
    private void log() {
        final String folder = getStringOrDefault(folderPattern, "");
        final String fileName = getStringOrDefault(fileNamePattern, "");

        if (match) {
            log(0, MSG_MATCH, folder, fileName);
        }
        else {
            log(0, MSG_MISMATCH, folder, fileName);
        }
    }

    /**
     * Retrieves the String form of the {@code pattern} or {@code defaultString}
     * if null.
     *
     * @param pattern The pattern to convert.
     * @param defaultString The result to use if {@code pattern} is null.
     * @return The String form of the {@code pattern}.
     */
    private static String getStringOrDefault(Pattern pattern, String defaultString) {
        final String result;

        if (pattern == null) {
            result = defaultString;
        }
        else {
            result = pattern.toString();
        }

        return result;
    }
}
