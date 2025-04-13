////
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
///

package com.puppycrawl.tools.checkstyle.checks.imports;

import java.net.URI;
import java.util.Set;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.ExternalResourceHolder;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <div>
 * Controls what can be imported in each package and file. Useful for ensuring
 * that application layering rules are not violated, especially on large projects.
 * </div>
 *
 * <p>
 * You can control imports based on the package name or based on the file name.
 * When controlling packages, all files and sub-packages in the declared package
 * will be controlled by this check. To specify differences between a main package
 * and a sub-package, you must define the sub-package inside the main package.
 * When controlling file, only the file name is considered and only files processed by
 * <a href="https://checkstyle.org/config.html#TreeWalker">TreeWalker</a>.
 * The file's extension is ignored.
 * </p>
 *
 * <p>
 * Short description of the behaviour:
 * </p>
 * <ul>
 * <li>
 * Check starts checking from the longest matching subpackage (later 'current subpackage') or
 * the first file name match described inside import control file to package defined in class file.
 * <ul>
 * <li>
 * The longest matching subpackage is found by starting with the root package and
 * examining if any of the sub-packages or file definitions match the current
 * class' package or file name.
 * </li>
 * <li>
 * If a file name is matched first, that is considered the longest match and becomes
 * the current file/subpackage.
 * </li>
 * <li>
 * If another subpackage is matched, then it's subpackages and file names are examined
 * for the next longest match and the process repeats recursively.
 * </li>
 * <li>
 * If no subpackages or file names are matched, the current subpackage is then used.
 * </li>
 * </ul>
 * </li>
 * <li>
 * Order of rules in the same subpackage/root are defined by the order of declaration
 * in the XML file, which is from top (first) to bottom (last).
 * </li>
 * <li>
 * If there is matching allow/disallow rule inside the current file/subpackage
 * then the Check returns the first "allowed" or "disallowed" message.
 * </li>
 * <li>
 * If there is no matching allow/disallow rule inside the current file/subpackage
 * then it continues checking in the parent subpackage.
 * </li>
 * <li>
 * If there is no matching allow/disallow rule in any of the files/subpackages,
 * including the root level (import-control), then the import is disallowed by default.
 * </li>
 * </ul>
 *
 * <p>
 * The DTD for an import control XML document is at
 * <a href="https://checkstyle.org/dtds/import_control_1_4.dtd">
 * https://checkstyle.org/dtds/import_control_1_4.dtd</a>.
 * It contains documentation on each of the elements and attributes.
 * </p>
 *
 * <p>
 * The check validates a XML document when it loads the document. To validate against
 * the above DTD, include the following document type declaration in your XML document:
 * </p>
 * <pre>
 * &lt;!DOCTYPE import-control PUBLIC
 *     "-//Checkstyle//DTD ImportControl Configuration 1.4//EN"
 *     "https://checkstyle.org/dtds/import_control_1_4.dtd"&gt;
 * </pre>
 * <ul>
 * <li>
 * Property {@code file} - Specify the location of the file containing the
 * import control configuration. It can be a regular file, URL or resource path.
 * It will try loading the path as a URL first, then as a file, and finally as a resource.
 * Type is {@code java.net.URI}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code path} - Specify the regular expression of file paths to which
 * this check should apply. Files that don't match the pattern will not be checked.
 * The pattern will be matched against the full absolute file path.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code ".*"}.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code import.control.disallowed}
 * </li>
 * <li>
 * {@code import.control.missing.file}
 * </li>
 * <li>
 * {@code import.control.unknown.pkg}
 * </li>
 * </ul>
 *
 * @since 4.0
 */
@FileStatefulCheck
public class ImportControlCheck extends AbstractCheck implements ExternalResourceHolder {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_MISSING_FILE = "import.control.missing.file";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_UNKNOWN_PKG = "import.control.unknown.pkg";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_DISALLOWED = "import.control.disallowed";

    /**
     * A part of message for exception.
     */
    private static final String UNABLE_TO_LOAD = "Unable to load ";

    /**
     * Specify the location of the file containing the import control configuration.
     * It can be a regular file, URL or resource path. It will try loading the path
     * as a URL first, then as a file, and finally as a resource.
     */
    private URI file;

    /**
     * Specify the regular expression of file paths to which this check should apply.
     * Files that don't match the pattern will not be checked. The pattern will
     * be matched against the full absolute file path.
     */
    private Pattern path = Pattern.compile(".*");
    /** Whether to process the current file. */
    private boolean processCurrentFile;

    /** The root package controller. */
    private PkgImportControl root;
    /** The package doing the import. */
    private String packageName;
    /** The file name doing the import. */
    private String fileName;

    /**
     * The package controller for the current file. Used for performance
     * optimisation.
     */
    private AbstractImportControl currentImportControl;

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {TokenTypes.PACKAGE_DEF, TokenTypes.IMPORT, TokenTypes.STATIC_IMPORT,};
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        currentImportControl = null;
        final String fullFileName = getFilePath();
        processCurrentFile = path.matcher(fullFileName).find();
        fileName = CommonUtil.getFileNameWithoutExtension(fullFileName);
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (processCurrentFile) {
            if (ast.getType() == TokenTypes.PACKAGE_DEF) {
                if (root == null) {
                    log(ast, MSG_MISSING_FILE);
                }
                else {
                    packageName = getPackageText(ast);
                    currentImportControl = root.locateFinest(packageName, fileName);
                    if (currentImportControl == null) {
                        log(ast, MSG_UNKNOWN_PKG);
                    }
                }
            }
            else if (currentImportControl != null) {
                final String importText = getImportText(ast);
                final AccessResult access = currentImportControl.checkAccess(packageName, fileName,
                    importText);
                if (access != AccessResult.ALLOWED) {
                    log(ast, MSG_DISALLOWED, importText);
                }
            }
        }
    }

    @Override
    public Set<String> getExternalResourceLocations() {
        return Set.of(file.toASCIIString());
    }

    /**
     * Returns package text.
     *
     * @param ast PACKAGE_DEF ast node
     * @return String that represents full package name
     */
    private static String getPackageText(DetailAST ast) {
        final DetailAST nameAST = ast.getLastChild().getPreviousSibling();
        return FullIdent.createFullIdent(nameAST).getText();
    }

    /**
     * Returns import text.
     *
     * @param ast ast node that represents import
     * @return String that represents importing class
     */
    private static String getImportText(DetailAST ast) {
        final FullIdent imp;
        if (ast.getType() == TokenTypes.IMPORT) {
            imp = FullIdent.createFullIdentBelow(ast);
        }
        else {
            // know it is a static import
            imp = FullIdent.createFullIdent(ast
                .getFirstChild().getNextSibling());
        }
        return imp.getText();
    }

    /**
     * Setter to specify the location of the file containing the import control configuration.
     * It can be a regular file, URL or resource path. It will try loading the path
     * as a URL first, then as a file, and finally as a resource.
     *
     * @param uri the uri of the file to load.
     * @throws IllegalArgumentException on error loading the file.
     * @since 4.0
     */
    public void setFile(URI uri) {
        // Handle empty param
        if (uri != null) {
            try {
                root = ImportControlLoader.load(uri);
                file = uri;
            }
            catch (CheckstyleException ex) {
                throw new IllegalArgumentException(UNABLE_TO_LOAD + uri, ex);
            }
        }
    }

    /**
     * Setter to specify the regular expression of file paths to which this check should apply.
     * Files that don't match the pattern will not be checked. The pattern will be matched
     * against the full absolute file path.
     *
     * @param pattern the file path regex this check should apply to.
     * @since 7.5
     */
    public void setPath(Pattern pattern) {
        path = pattern;
    }
}
