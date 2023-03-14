///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.regexp;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <p>
 * Checks that a specified pattern matches based on file and/or folder path.
 * It can also be used to verify files
 * match specific naming patterns not covered by other checks (Ex: properties,
 * xml, etc.).
 * </p>
 *
 * <p>
 * When customizing the check, the properties are applied in a specific order.
 * The fileExtensions property first picks only files that match any of the
 * specific extensions supplied. Once files are matched against the
 * fileExtensions, the match property is then used in conjunction with the
 * patterns to determine if the check is looking for a match or mismatch on
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
 * custom violation message. This allows the violation message printed to be clear what
 * the violation is, especially if multiple RegexpOnFilename checks are used.
 * Argument 0 for the message populates the check's folderPattern. Argument 1
 * for the message populates the check's fileNamePattern. The file name is not
 * passed as an argument since it is part of CheckStyle's default violation
 * messages.
 * </p>
 * <ul>
 * <li>
 * Property {@code folderPattern} - Specify the regular expression to match the folder path against.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code null}.</li>
 *
 * <li>
 * Property {@code fileNamePattern} - Specify the regular expression to match the file name against.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code null}.</li>
 *
 * <li>
 * Property {@code match} - Control whether to look for a match or mismatch on the file name, if
 * the fileNamePattern is supplied, otherwise it is applied on the folderPattern.
 * Type is {@code boolean}.
 * Default value is {@code true}.</li>
 *
 * <li>
 * Property {@code ignoreFileNameExtensions} - Control whether to ignore the file extension for
 * the file name match.
 * Type is {@code boolean}.
 * Default value is {@code false}.</li>
 *
 * <li>
 * Property {@code fileExtensions} - Specify the file type extension of files to process. If this is
 * specified, then only files that match these types are examined with the other
 * patterns.
 * Type is {@code java.lang.String[]}.
 * Default value is {@code ""}.</li>
 * </ul>
 * <p>
 * To configure the check to report file names that contain a space:
 * </p>
 *
 * <pre>
 * &lt;module name=&quot;RegexpOnFilename&quot;/&gt;
 * </pre>
 *
 * <p>Example:</p>
 * <pre>
 * src/xdocs/config_regexp.xml  //OK, contains no whitespace
 * src/xdocs/&quot;config regexp&quot;.xml  //violation, contains whitespace
 * </pre>
 *
 * <p>
 * To configure the check to forbid 'gif' files in folders:
 * </p>
 *
 * <pre>
 * &lt;module name=&quot;RegexpOnFilename&quot;&gt;
 *   &lt;property name=&quot;fileNamePattern&quot; value=&quot;\.gif$&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * <p>Example:</p>
 * <pre>
 * src/site/resources/images/favicon.png  //OK
 * src/site/resources/images/logo.jpg  //OK
 * src/site/resources/images/groups.gif  //violation, .gif images not allowed
 * </pre>
 *
 * <p>
 * To configure the check to forbid 'md' files except 'README.md file' in folders,
 * with custom message:
 * </p>
 *
 * <pre>
 * &lt;module name=&quot;RegexpOnFilename&quot;&gt;
 *   &lt;property name=&quot;fileNamePattern&quot; value=&quot;README&quot;/&gt;
 *   &lt;property name=&quot;fileExtensions&quot; value=&quot;md&quot;/&gt;
 *   &lt;property name=&quot;match&quot; value=&quot;false&quot;/&gt;
 *   &lt;message key=&quot;regexp.filename.mismatch&quot;
 *     value=&quot;No '*.md' files other then 'README.md'&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * <p>Example:</p>
 * <pre>
 * src/site/resources/README.md  //OK
 * src/site/resources/Logo.png  //OK
 * src/site/resources/Text.md  //violation, .md files other than 'README.md' are not allowed
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
 *     value=&quot;[\\/]src[\\/]\w+[\\/]resources[\\/]&quot;/&gt;
 *   &lt;property name=&quot;match&quot; value=&quot;false&quot;/&gt;
 *   &lt;property name=&quot;fileExtensions&quot; value=&quot;properties, xml&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * <p>Example:</p>
 * <pre>
 * src/main/resources/sun_checks.xml  //OK
 * src/main/resources/check_properties.properties  //OK
 * src/main/resources/JavaClass.java  //violation, xml|property files are allowed in resource folder
 * </pre>
 *
 * <p>
 * To configure the check to only allow Java and XML files in your folders use
 * the below.
 * </p>
 *
 * <pre>
 * &lt;module name=&quot;RegexpOnFilename&quot;&gt;
 *   &lt;property name=&quot;fileNamePattern&quot; value=&quot;\.(java|xml)$&quot;/&gt;
 *   &lt;property name=&quot;match&quot; value=&quot;false&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * <p>Example:</p>
 * <pre>
 * src/main/java/JavaClass.java  //OK
 * src/main/MainClass.java  //OK
 * src/main/java/java_xml.xml  //OK
 * src/main/main_xml.xml  //OK
 * src/main/java/image.png  //violation, folders should only contain java or xml files
 * src/main/check_properties.properties  //violation, folders should only contain java or xml files
 * </pre>
 *
 * <p>
 * To configure the check to only allow Java and XML files only in your source
 * folder and ignore any other folders:
 * </p>
 *
 * <pre>
 * &lt;module name=&quot;RegexpOnFilename&quot;&gt;
 *   &lt;property name=&quot;folderPattern&quot; value=&quot;[\\/]src[\\/]&quot;/&gt;
 *   &lt;property name=&quot;fileNamePattern&quot; value=&quot;\.(java|xml)$&quot;/&gt;
 *   &lt;property name=&quot;match&quot; value=&quot;false&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * <p>Example:</p>
 * <pre>
 * src/SourceClass.java  //OK
 * src/source_xml.xml  //OK
 * src/image.png  //violation, only java and xml files are allowed in src folder
 * src/main/main_properties.properties  //OK, this check only applies to src folder
 * </pre>
 *
 * <p>
 * <b>Note:</b> 'folderPattern' must be specified if checkstyle is analyzing
 * more than the normal source folder, like the 'bin' folder where class files
 * can be located.
 * </p>
 *
 * <p>
 * To configure the check to only allow file names to be camel case:
 * </p>
 *
 * <pre>
 * &lt;module name=&quot;RegexpOnFilename&quot;&gt;
 *   &lt;property name=&quot;fileNamePattern&quot; value=&quot;^([A-Z][a-z0-9]+\.?)+$&quot;/&gt;
 *   &lt;property name=&quot;match&quot; value=&quot;false&quot;/&gt;
 *   &lt;property name=&quot;ignoreFileNameExtensions&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * <p>Example:</p>
 * <pre>
 * src/main/java/JavaClass.java  //OK
 * src/main/MainClass.java  //OK
 * src/main/java/java_class.java  //violation, file names should be in Camel Case
 * src/main/main_class.java  //violation, file names should be in Camel Case
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.Checker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code regexp.filename.match}
 * </li>
 * <li>
 * {@code regexp.filename.mismatch}
 * </li>
 * </ul>
 *
 * @since 6.15
 */
@StatelessCheck
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

    /** Specify the regular expression to match the folder path against. */
    private Pattern folderPattern;
    /** Specify the regular expression to match the file name against. */
    private Pattern fileNamePattern;
    /**
     * Control whether to look for a match or mismatch on the file name,
     * if the fileNamePattern is supplied, otherwise it is applied on the folderPattern.
     */
    private boolean match = true;
    /** Control whether to ignore the file extension for the file name match. */
    private boolean ignoreFileNameExtensions;

    /**
     * Setter to specify the regular expression to match the folder path against.
     *
     * @param folderPattern format of folder.
     */
    public void setFolderPattern(Pattern folderPattern) {
        this.folderPattern = folderPattern;
    }

    /**
     * Setter to specify the regular expression to match the file name against.
     *
     * @param fileNamePattern format of file.
     */
    public void setFileNamePattern(Pattern fileNamePattern) {
        this.fileNamePattern = fileNamePattern;
    }

    /**
     * Setter to control whether to look for a match or mismatch on the file name,
     * if the fileNamePattern is supplied, otherwise it is applied on the folderPattern.
     *
     * @param match check's option for matching file names.
     */
    public void setMatch(boolean match) {
        this.match = match;
    }

    /**
     * Setter to control whether to ignore the file extension for the file name match.
     *
     * @param ignoreFileNameExtensions check's option for ignoring file extension.
     */
    public void setIgnoreFileNameExtensions(boolean ignoreFileNameExtensions) {
        this.ignoreFileNameExtensions = ignoreFileNameExtensions;
    }

    @Override
    public void init() {
        if (fileNamePattern == null && folderPattern == null) {
            fileNamePattern = CommonUtil.createPattern("\\s");
        }
    }

    @Override
    protected void processFiltered(File file, FileText fileText) throws CheckstyleException {
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
            fileName = CommonUtil.getFileNameWithoutExtension(fileName);
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
            return file.getCanonicalFile().getParent();
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
            // null pattern means 'match' applies to the folderPattern matching
            final boolean useMatch = fileNamePattern != null || match;
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
        // null pattern always matches, regardless of value of 'match'
        return fileNamePattern == null || fileNamePattern.matcher(fileName).find() == match;
    }

    /** Logs the violations for the check. */
    private void log() {
        final String folder = getStringOrDefault(folderPattern, "");
        final String fileName = getStringOrDefault(fileNamePattern, "");

        if (match) {
            log(1, MSG_MATCH, folder, fileName);
        }
        else {
            log(1, MSG_MISMATCH, folder, fileName);
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
