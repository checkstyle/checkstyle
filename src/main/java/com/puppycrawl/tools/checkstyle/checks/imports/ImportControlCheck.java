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

package com.puppycrawl.tools.checkstyle.checks.imports;

import java.net.URI;
import java.util.Collections;
import java.util.Set;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.ExternalResourceHolder;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Controls what can be imported in each package and file. Useful for ensuring
 * that application layering rules are not violated, especially on large projects.
 * </p>
 * <p>
 * You can control imports based on the package name or based on the file name.
 * When controlling packages, all files and sub-packages in the declared package
 * will be controlled by this check. To specify differences between a main package
 * and a sub-package, you must define the sub-package inside the main package.
 * When controlling file, only the file name is considered and only files processed by
 * <a href="https://checkstyle.org/config.html#TreeWalker">TreeWalker</a>.
 * The file's extension is ignored.
 * </p>
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
 * <p>
 * The DTD for an import control XML document is at
 * <a href="https://checkstyle.org/dtds/import_control_1_4.dtd">
 * https://checkstyle.org/dtds/import_control_1_4.dtd</a>.
 * It contains documentation on each of the elements and attributes.
 * </p>
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
 * <p>
 * To configure the check using an import control file called "config/import-control.xml",
 * then have the following:
 * </p>
 * <pre>
 * &lt;module name="ImportControl"&gt;
 *   &lt;property name="file" value="config/import-control.xml"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * To configure the check to only check the "src/main" directory using an import
 * control file called "config/import-control.xml", then have the following:
 * </p>
 * <pre>
 * &lt;module name="ImportControl"&gt;
 *   &lt;property name="file" value="config/import-control.xml"/&gt;
 *   &lt;property name="path" value="^.*[\\/]src[\\/]main[\\/].*$"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * In the example below access to package {@code com.puppycrawl.tools.checkstyle.checks}
 * and its subpackages is allowed from anywhere in {@code com.puppycrawl.tools.checkstyle}
 * except from the {@code filters} subpackage where access to all {@code check}'s
 * subpackages is disallowed. Two {@code java.lang.ref} classes are allowed by virtue
 * of one regular expression instead of listing them in two separate allow rules
 * (as it is done with the {@code Files} and {@code ClassPath} classes).
 * </p>
 * <pre>
 * &lt;import-control pkg="com.puppycrawl.tools.checkstyle"&gt;
 *   &lt;disallow pkg="sun"/&gt;
 *   &lt;allow pkg="com.puppycrawl.tools.checkstyle.api"/&gt;
 *   &lt;allow pkg="com.puppycrawl.tools.checkstyle.checks"/&gt;
 *   &lt;allow class="com.google.common.io.Files"/&gt;
 *   &lt;allow class="com.google.common.reflect.ClassPath"/&gt;
 *   &lt;subpackage name="filters"&gt;
 *     &lt;allow class="java\.lang\.ref\.(Weak|Soft)Reference"
 *       regex="true"/&gt;
 *     &lt;disallow pkg="com\.puppycrawl\.tools\.checkstyle\.checks\.[^.]+"
 *       regex="true"/&gt;
 *     &lt;disallow pkg="com.puppycrawl.tools.checkstyle.ant"/&gt;
 *     &lt;disallow pkg="com.puppycrawl.tools.checkstyle.gui"/&gt;
 *   &lt;/subpackage&gt;
 *   &lt;subpackage name="dao"&gt;
 *     &lt;disallow pkg="javax.swing" exact-match="true"/&gt;
 *   &lt;/subpackage&gt;
 * &lt;/import-control&gt;
 * </pre>
 * <p>
 * In the next example regular expressions are used to enforce a layering rule:
 * In all {@code dao} packages it is not allowed to access UI layer code ({@code ui},
 * {@code awt}, and {@code swing}). On the other hand it is not allowed to directly
 * access {@code dao} and {@code service} layer from {@code ui} packages.
 * The root package is also a regular expression that is used to handle old and
 * new domain name with the same rules.
 * </p>
 * <pre>
 * &lt;import-control pkg="(de.olddomain|de.newdomain)\..*" regex="true"&gt;
 *   &lt;subpackage pkg="[^.]+\.dao" regex="true"&gt;
 *     &lt;disallow pkg=".*\.ui" regex="true"/&gt;
 *     &lt;disallow pkg=".*\.(awt|swing).\.*" regex="true"/&gt;
 *   &lt;/subpackage&gt;
 *   &lt;subpackage pkg="[^.]+\.ui" regex="true"&gt;
 *     &lt;disallow pkg=".*\.(dao|service)" regex="true"/&gt;
 *   &lt;/subpackage&gt;
 * &lt;/import-control&gt;
 * </pre>
 * <p>
 * In the next examples usage of {@code strategyOnMismatch} property is shown.
 * This property defines strategy in a case when no matching allow/disallow rule was found.
 * Property {@code strategyOnMismatch} is attribute of {@code import-control} and
 * {@code subpackage} tags. Property can have the following values for {@code import-control} tag:
 * </p>
 * <ul>
 * <li>
 * disallowed (default value) - if there is no matching allow/disallow rule in any of
 * the subpackages, including the root level (import-control), then the import is disallowed.
 * </li>
 * <li>
 * allowed - if there is no matching allow/disallow rule in any of the subpackages,
 * including the root level, then the import is allowed.
 * </li>
 * </ul>
 * <p>
 * And following values for {@code subpackage} tags:
 * </p>
 * <ul>
 * <li>
 * delegateToParent (default value) - if there is no matching allow/disallow rule
 * inside the current subpackage, then it continues checking in the parent subpackage.
 * </li>
 * <li>
 * allowed - if there is no matching allow/disallow rule inside the current subpackage,
 * then the import is allowed.
 * </li>
 * <li>
 * disallowed - if there is no matching allow/disallow rule inside the current subpackage,
 * then the import is disallowed.
 * </li>
 * </ul>
 * <p>
 * The following example demonstrates usage of {@code strategyOnMismatch}
 * property for {@code import-control} tag. Here all imports are allowed except
 * {@code java.awt.Image} and {@code java.io.File} classes.
 * </p>
 * <pre>
 * &lt;import-control pkg="com.puppycrawl.tools.checkstyle.checks"
 *   strategyOnMismatch="allowed"&gt;
 *   &lt;disallow class="java.awt.Image"/&gt;
 *   &lt;disallow class="java.io.File"/&gt;
 * &lt;/import-control&gt;
 * </pre>
 * <p>
 * In the example below, any import is disallowed inside
 * {@code com.puppycrawl.tools.checkstyle.checks.imports} package except imports
 * from package {@code javax.swing} and class {@code java.io.File}.
 * However, any import is allowed in the classes outside of
 * {@code com.puppycrawl.tools.checkstyle.checks.imports} package.
 * </p>
 * <pre>
 * &lt;import-control pkg="com.puppycrawl.tools.checkstyle.checks"
 *   strategyOnMismatch="allowed"&gt;
 *   &lt;subpackage name="imports" strategyOnMismatch="disallowed"&gt;
 *     &lt;allow pkg="javax.swing"/&gt;
 *     &lt;allow class="java.io.File"/&gt;
 *   &lt;/subpackage&gt;
 * &lt;/import-control&gt;
 * </pre>
 * <p>
 * When {@code strategyOnMismatch} has {@code allowed} or {@code disallowed}
 * value for {@code subpackage} tag, it makes {@code subpackage} isolated from
 * parent rules. In the next example, if no matching rule was found inside
 * {@code com.puppycrawl.tools.checkstyle.checks.filters} then it continues
 * checking in the parent subpackage, while for
 * {@code com.puppycrawl.tools.checkstyle.checks.imports} import will be allowed by default.
 * </p>
 * <pre>
 * &lt;import-control pkg="com.puppycrawl.tools.checkstyle.checks"&gt;
 *   &lt;allow class="java\.awt\.Image" regex="true"/&gt;
 *   &lt;allow class="java\..*\.File" local-only="true" regex="true"/&gt;
 *   &lt;subpackage name="imports" strategyOnMismatch="allowed"&gt;
 *     &lt;allow pkg="javax\.swing" regex="true"/&gt;
 *     &lt;allow pkg="java\.io" exact-match="true"
 *       local-only="true" regex="true"/&gt;
 *   &lt;/subpackage&gt;
 *   &lt;subpackage name="filters"&gt;
 *     &lt;allow class="javax.util.Date"/&gt;
 *   &lt;/subpackage&gt;
 * &lt;/import-control&gt;
 * </pre>
 * <p>
 * In the example below, only file names that end with "Panel", "View", or "Dialog"
 * in the package {@code gui} are disallowed to have imports from {@code com.mycompany.dao}
 * and any {@code jdbc} package. In addition, only the file name named "PresentationModel"
 * in the package {@code gui} are disallowed to have imports that match {@code javax.swing.J*}.
 * All other imports in the package are allowed.
 * </p>
 * <pre>
 * &lt;import-control pkg="com.mycompany.billing"&gt;
 *   &lt;subpackage name="gui" strategyOnMismatch="allowed"&gt;
 *     &lt;file name=".*(Panel|View|Dialog)" regex="true"&gt;
 *       &lt;disallow pkg="com.mycompany.dao"/&gt;
 *       &lt;disallow pkg=".*\.jdbc" regex="true"/&gt;
 *     &lt;/file&gt;
 *     &lt;file name="PresentationModel"&gt;
 *       &lt;disallow pkg="javax\.swing\.J.*" regex="true"/&gt;
 *     &lt;/file&gt;
 *   &lt;/subpackage&gt;
 * &lt;/import-control&gt;
 * </pre>
 * <p>
 * For a real-life import control file look at the file called
 * <a href="https://github.com/checkstyle/checkstyle/blob/master/config/import-control.xml">
 * import-control.xml</a> which is part of the Checkstyle distribution.
 * </p>
 * <p id="blacklist-example">Example of blacklist mode</p>
 * <p>
 * To have a <b>blacklist mode</b>, it is required to have disallows inside
 * subpackage and to have allow rule inside parent of the current subpackage
 * to catch classes and packages those are not in the blacklist.
 * </p>
 * <p>
 * In the example below any import from {@code java.util}({@code java.util.List},
 * {@code java.util.Date}) package is allowed except {@code java.util.Map}
 * inside subpackage {@code com.puppycrawl.tools.checkstyle.filters}.
 * </p>
 * <pre>
 * &lt;import-control pkg="com.puppycrawl.tools.checkstyle"&gt;
 *   &lt;allow pkg="java.util"/&gt;
 *   &lt;subpackage name="filters" &gt;
 *     &lt;disallow class="java.util.Map"/&gt;
 *   &lt;/subpackage&gt;
 * &lt;/import-control&gt;
 * </pre>
 * <p>
 * In the next example imports {@code java.util.stream.Stream} and
 * {@code java.util.stream.Collectors} are disallowed inside
 * {@code com.puppycrawl.tools.checkstyle.checks.imports} package, but because of
 * {@code &lt;allow pkg="java.util.stream"/&gt;} every import from
 * {@code java.util.stream} is allowed except described ones.
 * </p>
 * <pre>
 * &lt;import-control pkg="com.puppycrawl.tools.checkstyle.checks"&gt;
 *   &lt;allow pkg="java.util.stream"/&gt;
 *   &lt;subpackage name="imports"&gt;
 *     &lt;disallow class="java.util.stream.Stream"/&gt;
 *     &lt;disallow class="java.util.stream.Collectors"/&gt;
 *   &lt;/subpackage&gt;
 * &lt;/import-control&gt;
 * </pre>
 * <pre>
 * package com.puppycrawl.tools.checkstyle.checks.imports;
 *
 * import java.util.stream.Stream;     // violation here
 * import java.util.stream.Collectors; // violation here
 * import java.util.stream.IntStream;
 * </pre>
 * <p>
 * In the following example, all imports are allowed except the classes
 * {@code java.util.Date}, {@code java.util.List} and package {@code sun}.
 * </p>
 * <pre>
 * &lt;import-control pkg="com.puppycrawl.tools.checkstyle.checks"&gt;
 *   &lt;allow pkg=".*" regex="true"/&gt;
 *   &lt;subpackage name="imports"&gt;
 *     &lt;disallow class="java.util.Date"/&gt;
 *     &lt;disallow class="java.util.List"/&gt;
 *     &lt;disallow pkg="sun"/&gt;
 *   &lt;/subpackage&gt;
 * &lt;/import-control&gt;
 * </pre>
 * <p>
 * In the following example, all imports of the {@code java.util} package are
 * allowed except the {@code java.util.Date} class.
 * </p>
 * <pre>
 * &lt;import-control pkg="com.puppycrawl.tools.checkstyle.checks"&gt;
 *   &lt;disallow class="java.util.Date"/&gt;
 *
 *   &lt;allow pkg="java.util"/&gt;
 * &lt;/import-control&gt;
 * </pre>
 * <p id="regex-notes">Notes on regular expressions</p>
 * <p>
 * Regular expressions in import rules have to match either Java packages or classes.
 * The language rules for packages and class names can be described by the following
 * complicated regular expression that takes into account that Java names may contain
 * any unicode letter, numbers, underscores, and dollar signs (see section 3.8 in the
 * <a href="https://docs.oracle.com/javase/specs/">Java specs</a>):
 * </p>
 * <ul>
 * <li>
 * {@code [\p{Letter}_$][\p{Letter}\p{Number}_$]*} or short {@code [\p{L}_$][\p{L}\p{N}_$]*}
 * for a class name or package component.
 * </li>
 * <li>
 * {@code ([\p{L}_$][\p{L}\p{N}_$]*\.)*[\p{L}_$][\p{L}\p{N}_$]*} for a fully qualified name.
 * </li>
 * </ul>
 * <p>
 * But it is not necessary to use these complicated expressions since no validation is required.
 * Differentiating between package separator '.' and others is sufficient.
 * Unfortunately '.' has a special meaning in regular expressions so one has to write {@code \.}
 * to match an actual dot.
 * </p>
 * <ul>
 * <li>
 * Use {@code [^.]+}(one or more "not a dot" characters) for a class name or package component.
 * </li>
 * <li>
 * Use {@code com\.google\.common\.[^.]+} to match any subpackage of {@code com.google.common}.
 * </li>
 * <li>
 * When matching concrete packages like {@code com.google.common} omitting the backslash before
 * the dots may improve readability and may be just exact enough: {@code com.google.common\.[^.]+}
 * matches not only subpackages of {@code com.google.common} but e.g. also of
 * {@code com.googleecommon} but you may not care for that.
 * </li>
 * <li>
 * Do not use {@code .*} unless you really do not care for what is matched.
 * Often you want to match only a certain package level instead.
 * </li>
 * </ul><p id="static-import-notes">Notes on static imports</p>
 * <p>
 * Static members (including methods, constants and static inner classes)
 * have to be explicitly allowed when they are imported, they are not automatically
 * allowed along with their enclosing class.
 * </p>
 * <p>
 * For example, to allow importing both {@code java.util.Map} and {@code java.util.Map.Entry}
 * use the following configuration:
 * </p>
 * <pre>
 * &lt;import-control pkg="com.puppycrawl.tools.checkstyle"&gt;
 *   &lt;allow class="java.util.Map"/&gt;
 *   &lt;allow class="java.util.Map.Entry"/&gt;
 * &lt;/import-control&gt;
 * </pre>
 * <p>
 * It is also possible to use a regex with a wildcard:
 * </p>
 * <pre>
 * &lt;import-control pkg="com.puppycrawl.tools.checkstyle"&gt;
 *   &lt;allow class="java.util.Map"/&gt;
 *   &lt;allow class="java.util.Map.*" regex="true" /&gt;
 * &lt;/import-control&gt;
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
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
        return new int[] {TokenTypes.PACKAGE_DEF, TokenTypes.IMPORT, TokenTypes.STATIC_IMPORT, };
    }

    // suppress deprecation until https://github.com/checkstyle/checkstyle/issues/11166
    @SuppressWarnings("deprecation")
    @Override
    public void beginTree(DetailAST rootAST) {
        currentImportControl = null;
        processCurrentFile = path.matcher(getFilePath()).find();
        fileName = getFileContents().getText().getFile().getName();

        final int period = fileName.lastIndexOf('.');

        if (period != -1) {
            fileName = fileName.substring(0, period);
        }
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
        return Collections.singleton(file.toString());
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
     */
    public void setPath(Pattern pattern) {
        path = pattern;
    }

}
