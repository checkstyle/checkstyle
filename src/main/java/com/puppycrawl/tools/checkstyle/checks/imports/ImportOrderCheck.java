////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.imports;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <p>
 * Checks the ordering/grouping of imports. Features are:
 * </p>
 * <ul>
 * <li>
 * groups type/static imports: ensures that groups of imports come in a specific order
 * (e.g., java. comes first, javax. comes second, then everything else)
 * </li>
 * <li>
 * adds a separation between type import groups : ensures that a blank line sit between each group
 * </li>
 * <li>
 * type/static import groups aren't separated internally: ensures that each group aren't separated
 * internally by blank line or comment
 * </li>
 * <li>
 * sorts type/static imports inside each group: ensures that imports within each group are in
 * lexicographic order
 * </li>
 * <li>
 * sorts according to case: ensures that the comparison between imports is case-sensitive, in
 * <a href="https://en.wikipedia.org/wiki/ASCII#Order">ASCII sort order</a>
 * </li>
 * <li>
 * arrange static imports: ensures the relative order between type imports and static imports
 * (see
 * <a href="https://checkstyle.org/property_types.html#ImportOrderOption">ImportOrderOption</a>)
 * </li>
 * </ul>
 * <ul>
 * <li>
 * Property {@code option} - specify policy on the relative order between type imports and static
 * imports.
 * Type is {@code com.puppycrawl.tools.checkstyle.checks.imports.ImportOrderOption}.
 * Default value is {@code under}.
 * </li>
 * <li>
 * Property {@code groups} - specify list of <b>type import</b> groups. Every group identified
 * either by a common prefix string, or by a regular expression enclosed in forward slashes
 * (e.g. {@code /regexp/}). All type imports, which does not match any group, falls into an
 * additional group, located at the end.
 * Thus, the empty list of type groups (the default value) means one group for all type imports.
 * Type is {@code java.util.regex.Pattern[]}.
 * Default value is {@code ""}.
 * </li>
 * <li>
 * Property {@code ordered} - control whether type imports within each group should be
 * sorted.
 * It doesn't affect sorting for static imports.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code separated} - control whether type import groups should be separated
 * by, at least, one blank line or comment and aren't separated internally.
 * It doesn't affect separations for static imports.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code separatedStaticGroups} - control whether static import groups should
 * be separated by, at least, one blank line or comment and aren't separated internally.
 * This property has effect only when the property {@code option} is set to {@code top}
 * or {@code bottom} and when property {@code staticGroups} is enabled.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code caseSensitive} - control whether string comparison should be
 * case-sensitive or not. Case-sensitive sorting is in
 * <a href="https://en.wikipedia.org/wiki/ASCII#Order">ASCII sort order</a>.
 * It affects both type imports and static imports.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code staticGroups} - specify list of <b>static</b> import groups. Every group
 * identified either by a common prefix string, or by a regular expression enclosed in forward
 * slashes (e.g. {@code /regexp/}). All static imports, which does not match any group, fall into
 * an additional group, located at the end. Thus, the empty list of static groups (the default
 * value) means one group for all static imports. This property has effect only when the property
 * {@code option} is set to {@code top} or {@code bottom}.
 * Type is {@code java.util.regex.Pattern[]}.
 * Default value is {@code ""}.
 * </li>
 * <li>
 * Property {@code sortStaticImportsAlphabetically} - control whether
 * <b>static imports</b> located at <b>top</b> or <b>bottom</b> are sorted within the group.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code useContainerOrderingForStatic} - control whether to use container
 * ordering (Eclipse IDE term) for static imports or not.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#STATIC_IMPORT">
 * STATIC_IMPORT</a>.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name="ImportOrder"/&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * import java.io.IOException;
 * import java.net.URL;
 *
 * import java.io.IOException; // violation, extra separation before import
 *                             // and wrong order, comes before 'java.net.URL'.
 * import javax.net.ssl.TrustManager; // violation, extra separation due to above comment
 * import javax.swing.JComponent;
 * import org.apache.http.conn.ClientConnectionManager; // OK
 * import java.util.Set; // violation, wrong order, 'java' should not come after 'org' imports
 * import com.neurologic.http.HttpClient; // violation, wrong order, 'com' imports comes at top
 * import com.neurologic.http.impl.ApacheHttpClient; // OK
 *
 * public class SomeClass { }
 * </pre>
 * <p>
 * To configure the check so that it matches default Eclipse formatter configuration
 * (tested on Kepler and Luna releases):
 * </p>
 * <ul>
 * <li>
 * group of static imports is on the top
 * </li>
 * <li>
 * groups of type imports: "java" and "javax" packages first, then "org" and then all other imports
 * </li>
 * <li>
 * imports will be sorted in the groups
 * </li>
 * <li>
 * groups are separated by, at least, one blank line and aren't separated internally
 * </li>
 * </ul>
 * <p>
 * Notes:
 * </p>
 * <ul>
 * <li>
 * "com" package is not mentioned on configuration, because it is ignored by Eclipse Kepler and Luna
 * (looks like Eclipse defect)
 * </li>
 * <li>
 * configuration below doesn't work in all 100% cases due to inconsistent behavior prior to
 * Mars release, but covers most scenarios
 * </li>
 * </ul>
 * <pre>
 * &lt;module name=&quot;ImportOrder&quot;&gt;
 *   &lt;property name=&quot;groups&quot; value=&quot;/^java\./,javax,org&quot;/&gt;
 *   &lt;property name=&quot;ordered&quot; value=&quot;true&quot;/&gt;
 *   &lt;property name=&quot;separated&quot; value=&quot;true&quot;/&gt;
 *   &lt;property name=&quot;option&quot; value=&quot;above&quot;/&gt;
 *   &lt;property name=&quot;sortStaticImportsAlphabetically&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * import static java.lang.System.out;
 * import static java.lang.Math; // violation, alphabetical case-sensitive ASCII order, 'M' &lt; 'S'
 * import java.io.IOException;
 *
 * import java.net.URL; // violation, extra separation before import
 * import java.security.KeyManagementException;
 *
 * import javax.net.ssl.TrustManager;
 *
 * import javax.net.ssl.X509TrustManager; // violation, groups should not be separated internally
 *
 * import org.apache.http.conn.ClientConnectionManager;
 *
 * public class SomeClass { }
 * </pre>
 * <p>
 * To configure the check so that it matches default Eclipse formatter configuration
 * (tested on Mars release):
 * </p>
 * <ul>
 * <li>
 * group of static imports is on the top
 * </li>
 * <li>
 * groups of type imports: "java" and "javax" packages first, then "org" and "com",
 * then all other imports as one group
 * </li>
 * <li>
 * imports will be sorted in the groups
 * </li>
 * <li>
 * groups are separated by, at least, one blank line and aren't separated internally
 * </li>
 * </ul>
 * <pre>
 * &lt;module name=&quot;ImportOrder&quot;&gt;
 *   &lt;property name=&quot;groups&quot; value=&quot;/^java\./,javax,org,com&quot;/&gt;
 *   &lt;property name=&quot;ordered&quot; value=&quot;true&quot;/&gt;
 *   &lt;property name=&quot;separated&quot; value=&quot;true&quot;/&gt;
 *   &lt;property name=&quot;option&quot; value=&quot;above&quot;/&gt;
 *   &lt;property name=&quot;sortStaticImportsAlphabetically&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * import static java.io.File.createTempFile;
 * import static java.lang.Math.abs; // OK, alphabetical case-sensitive ASCII order, 'i' &lt; 'l'
 * import java.lang.Math.sqrt; // OK, follows property 'Option' value 'above'
 * import java.io.File; // violation, alphabetical case-sensitive ASCII order, 'i' &lt; 'l'
 *
 * import java.io.IOException; // violation, extra separation in 'java' import group
 *
 * import org.albedo.*;
 *
 * import static javax.WindowConstants.*; // violation, wrong order, 'javax' comes before 'org'
 * import javax.swing.JComponent;
 * import org.apache.http.ClientConnectionManager; // violation, must separate from previous import
 * import org.linux.apache.server.SoapServer; // OK
 *
 * import com.neurologic.http.HttpClient; // OK
 * import com.neurologic.http.impl.ApacheHttpClient; // OK
 *
 * public class SomeClass { }
 * </pre>
 * <p>
 * To configure the check so that it matches default IntelliJ IDEA formatter
 * configuration (tested on v2018.2):
 * </p>
 * <ul>
 * <li>
 * group of static imports is on the bottom
 * </li>
 * <li>
 * groups of type imports: all imports except of "javax" and "java", then "javax" and "java"
 * </li>
 * <li>
 * imports will be sorted in the groups
 * </li>
 * <li>
 * groups are separated by, at least, one blank line and aren't separated internally
 * </li>
 * </ul>
 * <p>
 * Note: a <a href="https://checkstyle.org/config_filters.html#SuppressionXpathSingleFilter">
 * suppression xpath single filter</a> is needed because
 * IDEA has no blank line between "javax" and "java".
 * ImportOrder has a limitation by design to enforce an empty line between groups ("java", "javax").
 * There is no flexibility to enforce empty lines between some groups and no empty lines between
 * other groups.
 * </p>
 * <p>
 * Note: "separated" option is disabled because IDEA default has blank line between "java" and
 * static imports, and no blank line between "javax" and "java".
 * </p>
 * <pre>
 * &lt;module name=&quot;ImportOrder&quot;&gt;
 *   &lt;property name=&quot;groups&quot; value=&quot;*,javax,java&quot;/&gt;
 *   &lt;property name=&quot;ordered&quot; value=&quot;true&quot;/&gt;
 *   &lt;property name=&quot;separated&quot; value=&quot;false&quot;/&gt;
 *   &lt;property name=&quot;option&quot; value=&quot;bottom&quot;/&gt;
 *   &lt;property name=&quot;sortStaticImportsAlphabetically&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * &lt;module name="SuppressionXpathSingleFilter"&gt;
 *   &lt;property name="checks" value="ImportOrder"/&gt;
 *   &lt;property name="message" value="^'java\..*'.*"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * import com.neurologic.http.impl.ApacheHttpClient; // OK
 * import static java.awt.Button.A;
 * import javax.swing.JComponent; // violation, wrong order, caused by above static import
 *                                // all static imports comes at bottom
 * import java.net.URL; // violation, extra separation in import group
 * import java.security.KeyManagementException;
 * import javax.swing.JComponent; // violation, wrong order, 'javax' should be above 'java' imports
 * import com.neurologic.http.HttpClient; // violation, wrong order, 'com' imports should be at top
 *
 * public class TestClass { }
 * </pre>
 * <p>
 * To configure the check so that it matches default NetBeans formatter configuration
 * (tested on v8):
 * </p>
 * <ul>
 * <li>
 * groups of type imports are not defined, all imports will be sorted as a one group
 * </li>
 * <li>
 * static imports are not separated, they will be sorted along with other imports
 * </li>
 * </ul>
 * <pre>
 * &lt;module name=&quot;ImportOrder&quot;&gt;
 *   &lt;property name=&quot;option&quot; value=&quot;inflow&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * import static java.io.File.createTempFile;
 * import java.lang.Math.sqrt;
 *
 * import javax.swing.JComponent; // violation, extra separation in import group
 * import static javax.windowConstants.*; // OK
 *                                     // all static imports are processed like non static imports.
 * public class SomeClass { }
 * </pre>
 * <p>
 * Group descriptions enclosed in slashes are interpreted as regular expressions.
 * If multiple groups match, the one matching a longer substring of the imported name
 * will take precedence, with ties broken first in favor of earlier matches and finally
 * in favor of the first matching group.
 * </p>
 * <p>
 * There is always a wildcard group to which everything not in a named group belongs.
 * If an import does not match a named group, the group belongs to this wildcard group.
 * The wildcard group position can be specified using the {@code *} character.
 * </p>
 * <p>
 * Check also has on option making it more flexible: <b>sortStaticImportsAlphabetically</b>
 * - sets whether static imports grouped by <b>top</b> or <b>bottom</b> option should be sorted
 * alphabetically or not, default value is <b>false</b>. It is applied to static imports grouped
 * with <b>top</b> or <b>bottom</b> options. This option is helping in reconciling of this
 * Check and other tools like Eclipse's Organize Imports feature.
 * </p>
 * <p>
 * To configure the Check allows static imports grouped to the <b>top</b> being sorted
 * alphabetically:
 * </p>
 * <pre>
 * &lt;module name=&quot;ImportOrder&quot;&gt;
 *   &lt;property name=&quot;sortStaticImportsAlphabetically&quot; value=&quot;true&quot;/&gt;
 *   &lt;property name=&quot;option&quot; value=&quot;top&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <pre>
 * import static java.lang.Math.PI;
 * import static java.lang.Math.abs; // OK, alphabetical case-sensitive ASCII order, 'P' &lt; 'a'
 * import static org.abego.treelayout.Configuration.AlignmentInLevel; // OK, alphabetical order
 *
 * import java.util.Set; // violation, extra separation in import group
 * import static java.lang.Math.abs; // violation, wrong order, all static imports comes at 'top'
 * import org.abego.*;
 *
 * public class SomeClass { }
 * </pre>
 * <p>
 * To configure the Check with groups of static imports:
 * </p>
 * <pre>
 * &lt;module name=&quot;ImportOrder&quot;&gt;
 *   &lt;property name=&quot;staticGroups&quot; value=&quot;org,java&quot;/&gt;
 *   &lt;property name=&quot;sortStaticImportsAlphabetically&quot; value=&quot;true&quot;/&gt;
 *   &lt;property name=&quot;option&quot; value=&quot;top&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <pre>
 * import static org.abego.treelayout.Configuration.AlignmentInLevel; // Group 1
 * import static java.lang.Math.abs; // Group 2
 * import static java.lang.String.format; // Group 2
 * import static com.google.common.primitives.Doubles.BYTES; // Group "everything else"
 *
 * public class SomeClass { }
 * </pre>
 * <p>
 * The following example shows the idea of 'useContainerOrderingForStatic' option that is
 * useful for Eclipse IDE users to match ordering validation.
 * This is how the import comparison works for static imports: we first compare
 * the container of the static import, container is the type enclosing the static element
 * being imported. When the result of the comparison is 0 (containers are equal),
 * we compare the fully qualified import names.
 * For e.g. this is what is considered to be container names for the given example:
 *
 * import static HttpConstants.COLON     =&gt; HttpConstants
 * import static HttpHeaders.addHeader   =&gt; HttpHeaders
 * import static HttpHeaders.setHeader   =&gt; HttpHeaders
 * import static HttpHeaders.Names.DATE  =&gt; HttpHeaders.Names
 *
 * According to this logic, HttpHeaders.Names should come after HttpHeaders.
 * </p>
 * <p>
 * Example for {@code useContainerOrderingForStatic=true}
 * </p>
 * <pre>
 * &lt;module name=&quot;ImportOrder&quot;&gt;
 *   &lt;property name=&quot;useContainerOrderingForStatic&quot; value=&quot;true&quot;/&gt;
 *   &lt;property name=&quot;ordered&quot; value=&quot;true&quot;/&gt;
 *   &lt;property name=&quot;option&quot; value=&quot;top&quot;/&gt;
 *   &lt;property name=&quot;caseSensitive&quot; value=&quot;false&quot;/&gt;
 *   &lt;property name=&quot;sortStaticImportsAlphabetically&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <pre>
 * import static io.netty.handler.codec.http.HttpConstants.COLON;
 * import static io.netty.handler.codec.http.HttpHeaders.addHeader;
 * import static io.netty.handler.codec.http.HttpHeaders.setHeader;
 * import static io.netty.handler.codec.http.HttpHeaders.Names.DATE;
 *
 * public class InputEclipseStaticImportsOrder { }
 * </pre>
 * <p>
 * Example for {@code useContainerOrderingForStatic=false}
 * </p>
 * <pre>
 * &lt;module name=&quot;ImportOrder&quot;&gt;
 *   &lt;property name=&quot;useContainerOrderingForStatic&quot; value=&quot;false&quot;/&gt;
 *   &lt;property name=&quot;ordered&quot; value=&quot;true&quot;/&gt;
 *   &lt;property name=&quot;option&quot; value=&quot;top&quot;/&gt;
 *   &lt;property name=&quot;caseSensitive&quot; value=&quot;false&quot;/&gt;
 *   &lt;property name=&quot;sortStaticImportsAlphabetically&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <pre>
 * import static io.netty.handler.codec.http.HttpConstants.COLON;
 * import static io.netty.handler.codec.http.HttpHeaders.addHeader;
 * import static io.netty.handler.codec.http.HttpHeaders.setHeader;
 * import static io.netty.handler.codec.http.HttpHeaders.Names.DATE; // violation
 *
 * public class InputEclipseStaticImportsOrder { }
 * </pre>
 * <p>
 * To configure the check to enforce static import group separation
 * </p>
 * <p>
 * Example for {@code separatedStaticGroups=true}
 * </p>
 * <pre>
 * &lt;module name=&quot;ImportOrder&quot;&gt;
 *   &lt;property name=&quot;staticGroups&quot; value=&quot;*,java,javax,org&quot;/&gt;
 *   &lt;property name=&quot;option&quot; value=&quot;top&quot;/&gt;
 *   &lt;property name=&quot;separatedStaticGroups&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <pre>
 * import static java.lang.Math.PI;
 * import static java.io.File.createTempFile;
 * import static javax.swing.JComponent; // violation, should be separated from previous imports
 * import static javax.WindowConstants.*; // OK
 *
 * import java.net.URL;
 *
 * public class SomeClass { }
 * </pre>
 * <p>
 * To configure the Check with groups of static imports when staticGroups=&quot;&quot;
 * represents all imports as {@code everything else} group:
 * </p>
 * <pre>
 * &lt;module name=&quot;ImportOrder&quot;&gt;
 *   &lt;property name=&quot;staticGroups&quot; value=&quot;&quot;/&gt;
 *   &lt;property name=&quot;option&quot; value=&quot;top&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <pre>
 * import static java.io.File.listRoots;   // OK
 * import static javax.swing.WindowConstants.*; // OK
 * import static java.io.File.createTempFile; // OK
 * import static com.puppycrawl.tools.checkstyle;  // OK
 *
 * public class SomeClass { }
 * </pre>
 * <p>
 * To configure the Check with groups of static imports when
 * staticGroups=&quot;java, javax&quot; represents three groups i.e java*, javax*
 * and * (everything else). In below example the static imports {@code com...}
 * should be in last group:
 * </p>
 * <pre>
 * &lt;module name=&quot;ImportOrder&quot;&gt;
 *   &lt;property name=&quot;staticGroups&quot; value=&quot;java, javax&quot;/&gt;
 *   &lt;property name=&quot;option&quot; value=&quot;top&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <pre>
 * import static java.io.File.listRoots;   // OK
 * import static javax.swing.WindowConstants.*; // OK
 * import static java.io.File.createTempFile; // violation should be before javax
 * import static com.puppycrawl.tools.checkstyle;  // OK
 *
 * public class SomeClass { }
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code import.groups.separated.internally}
 * </li>
 * <li>
 * {@code import.ordering}
 * </li>
 * <li>
 * {@code import.separation}
 * </li>
 * </ul>
 *
 * @since 3.2
 */
@FileStatefulCheck
public class ImportOrderCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_SEPARATION = "import.separation";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_ORDERING = "import.ordering";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_SEPARATED_IN_GROUP = "import.groups.separated.internally";

    /** The special wildcard that catches all remaining groups. */
    private static final String WILDCARD_GROUP_NAME = "*";

    /** Empty array of pattern type needed to initialize check. */
    private static final Pattern[] EMPTY_PATTERN_ARRAY = new Pattern[0];

    /**
     * Specify list of <b>type import</b> groups. Every group identified either by a common prefix
     * string, or by a regular expression enclosed in forward slashes (e.g. {@code /regexp/}).
     * All type imports, which does not match any group, falls into an additional group,
     * located at the end. Thus, the empty list of type groups (the default value) means one group
     * for all type imports.
     */
    private Pattern[] groups = EMPTY_PATTERN_ARRAY;

    /**
     * Specify list of <b>static</b> import groups. Every group identified either by a common prefix
     * string, or by a regular expression enclosed in forward slashes (e.g. {@code /regexp/}).
     * All static imports, which does not match any group, fall into an additional group, located
     * at the end. Thus, the empty list of static groups (the default value) means one group for all
     * static imports. This property has effect only when the property {@code option} is set to
     * {@code top} or {@code bottom}.
     */
    private Pattern[] staticGroups = EMPTY_PATTERN_ARRAY;

    /**
     * Control whether type import groups should be separated by, at least, one blank
     * line or comment and aren't separated internally. It doesn't affect separations for static
     * imports.
     */
    private boolean separated;

    /**
     * Control whether static import groups should be separated by, at least, one blank
     * line or comment and aren't separated internally. This property has effect only when the
     * property {@code option} is set to {@code top} or {@code bottom} and when property
     * {@code staticGroups} is enabled.
     */
    private boolean separatedStaticGroups;

    /**
     * Control whether type imports within each group should be sorted.
     * It doesn't affect sorting for static imports.
     */
    private boolean ordered = true;

    /**
     * Control whether string comparison should be case-sensitive or not. Case-sensitive
     * sorting is in <a href="https://en.wikipedia.org/wiki/ASCII#Order">ASCII sort order</a>.
     * It affects both type imports and static imports.
     */
    private boolean caseSensitive = true;

    /** Last imported group. */
    private int lastGroup;
    /** Line number of last import. */
    private int lastImportLine;
    /** Name of last import. */
    private String lastImport;
    /** If last import was static. */
    private boolean lastImportStatic;
    /** Whether there were any imports. */
    private boolean beforeFirstImport;
    /**
     * Whether static and type import groups should be split apart.
     * When the {@code option} property is set to {@code INFLOW}, {@code ABOVE} or {@code UNDER},
     * both the type and static imports use the properties {@code groups} and {@code separated}.
     * When the {@code option} property is set to {@code TOP} or {@code BOTTOM}, static imports
     * uses the properties {@code staticGroups} and {@code separatedStaticGroups}.
     **/
    private boolean staticImportsApart;

    /**
     * Control whether <b>static imports</b> located at <b>top</b> or <b>bottom</b> are
     * sorted within the group.
     */
    private boolean sortStaticImportsAlphabetically;

    /**
     * Control whether to use container ordering (Eclipse IDE term) for static imports
     * or not.
     */
    private boolean useContainerOrderingForStatic;

    /**
     * Specify policy on the relative order between type imports and static imports.
     */
    private ImportOrderOption option = ImportOrderOption.UNDER;

    /**
     * Setter to specify policy on the relative order between type imports and static imports.
     *
     * @param optionStr string to decode option from
     * @throws IllegalArgumentException if unable to decode
     */
    public void setOption(String optionStr) {
        option = ImportOrderOption.valueOf(optionStr.trim().toUpperCase(Locale.ENGLISH));
    }

    /**
     * Setter to specify list of <b>type import</b> groups. Every group identified either by a
     * common prefix string, or by a regular expression enclosed in forward slashes
     * (e.g. {@code /regexp/}). All type imports, which does not match any group, falls into an
     * additional group, located at the end. Thus, the empty list of type groups (the default value)
     * means one group for all type imports.
     *
     * @param packageGroups a comma-separated list of package names/prefixes.
     */
    public void setGroups(String... packageGroups) {
        groups = compilePatterns(packageGroups);
    }

    /**
     * Setter to specify list of <b>static</b> import groups. Every group identified either by a
     * common prefix string, or by a regular expression enclosed in forward slashes
     * (e.g. {@code /regexp/}). All static imports, which does not match any group, fall into an
     * additional group, located at the end. Thus, the empty list of static groups (the default
     * value) means one group for all static imports. This property has effect only when
     * the property {@code option} is set to {@code top} or {@code bottom}.
     *
     * @param packageGroups a comma-separated list of package names/prefixes.
     */
    public void setStaticGroups(String... packageGroups) {
        staticGroups = compilePatterns(packageGroups);
    }

    /**
     * Setter to control whether type imports within each group should be sorted.
     * It doesn't affect sorting for static imports.
     *
     * @param ordered
     *            whether lexicographic ordering of imports within a group
     *            required or not.
     */
    public void setOrdered(boolean ordered) {
        this.ordered = ordered;
    }

    /**
     * Setter to control whether type import groups should be separated by, at least,
     * one blank line or comment and aren't separated internally.
     * It doesn't affect separations for static imports.
     *
     * @param separated
     *            whether groups should be separated by one blank line or comment.
     */
    public void setSeparated(boolean separated) {
        this.separated = separated;
    }

    /**
     * Setter to control whether static import groups should be separated by, at least,
     * one blank line or comment and aren't separated internally.
     * This property has effect only when the property
     * {@code option} is set to {@code top} or {@code bottom} and when property {@code staticGroups}
     * is enabled.
     *
     * @param separatedStaticGroups
     *            whether groups should be separated by one blank line or comment.
     */
    public void setSeparatedStaticGroups(boolean separatedStaticGroups) {
        this.separatedStaticGroups = separatedStaticGroups;
    }

    /**
     * Setter to control whether string comparison should be case-sensitive or not.
     * Case-sensitive sorting is in
     * <a href="https://en.wikipedia.org/wiki/ASCII#Order">ASCII sort order</a>.
     * It affects both type imports and static imports.
     *
     * @param caseSensitive
     *            whether string comparison should be case-sensitive.
     */
    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    /**
     * Setter to control whether <b>static imports</b> located at <b>top</b> or
     * <b>bottom</b> are sorted within the group.
     *
     * @param sortAlphabetically true or false.
     */
    public void setSortStaticImportsAlphabetically(boolean sortAlphabetically) {
        sortStaticImportsAlphabetically = sortAlphabetically;
    }

    /**
     * Setter to control whether to use container ordering (Eclipse IDE term) for static
     * imports or not.
     *
     * @param useContainerOrdering whether to use container ordering for static imports or not.
     */
    public void setUseContainerOrderingForStatic(boolean useContainerOrdering) {
        useContainerOrderingForStatic = useContainerOrdering;
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.IMPORT, TokenTypes.STATIC_IMPORT};
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {TokenTypes.IMPORT};
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        lastGroup = Integer.MIN_VALUE;
        lastImportLine = Integer.MIN_VALUE;
        lastImport = "";
        lastImportStatic = false;
        beforeFirstImport = true;
        staticImportsApart =
            option == ImportOrderOption.TOP || option == ImportOrderOption.BOTTOM;
    }

    // -@cs[CyclomaticComplexity] SWITCH was transformed into IF-ELSE.
    @Override
    public void visitToken(DetailAST ast) {
        final FullIdent ident;
        final boolean isStatic;

        if (ast.getType() == TokenTypes.IMPORT) {
            ident = FullIdent.createFullIdentBelow(ast);
            isStatic = false;
        }
        else {
            ident = FullIdent.createFullIdent(ast.getFirstChild()
                    .getNextSibling());
            isStatic = true;
        }

        // using set of IF instead of SWITCH to analyze Enum options to satisfy coverage.
        // https://github.com/checkstyle/checkstyle/issues/1387
        if (option == ImportOrderOption.TOP || option == ImportOrderOption.ABOVE) {
            final boolean isStaticAndNotLastImport = isStatic && !lastImportStatic;
            doVisitToken(ident, isStatic, isStaticAndNotLastImport, ast);
        }
        else if (option == ImportOrderOption.BOTTOM || option == ImportOrderOption.UNDER) {
            final boolean isLastImportAndNonStatic = lastImportStatic && !isStatic;
            doVisitToken(ident, isStatic, isLastImportAndNonStatic, ast);
        }
        else if (option == ImportOrderOption.INFLOW) {
            // "previous" argument is useless here
            doVisitToken(ident, isStatic, true, ast);
        }
        else {
            throw new IllegalStateException(
                    "Unexpected option for static imports: " + option);
        }

        lastImportLine = ast.findFirstToken(TokenTypes.SEMI).getLineNo();
        lastImportStatic = isStatic;
        beforeFirstImport = false;
    }

    /**
     * Shares processing...
     *
     * @param ident the import to process.
     * @param isStatic whether the token is static or not.
     * @param previous previous non-static but current is static (above), or
     *                  previous static but current is non-static (under).
     * @param ast node of the AST.
     */
    private void doVisitToken(FullIdent ident, boolean isStatic, boolean previous, DetailAST ast) {
        final String name = ident.getText();
        final int groupIdx = getGroupNumber(isStatic && staticImportsApart, name);

        if (groupIdx > lastGroup) {
            if (!beforeFirstImport
                && ast.getLineNo() - lastImportLine < 2
                && needSeparator(isStatic)) {
                log(ast, MSG_SEPARATION, name);
            }
        }
        else if (groupIdx == lastGroup) {
            doVisitTokenInSameGroup(isStatic, previous, name, ast);
        }
        else {
            log(ast, MSG_ORDERING, name);
        }
        if (isSeparatorInGroup(groupIdx, isStatic, ast.getLineNo())) {
            log(ast, MSG_SEPARATED_IN_GROUP, name);
        }

        lastGroup = groupIdx;
        lastImport = name;
    }

    /**
     * Checks whether import groups should be separated.
     *
     * @param isStatic whether the token is static or not.
     * @return true if imports groups should be separated.
     */
    private boolean needSeparator(boolean isStatic) {
        final boolean typeImportSeparator = !isStatic && separated;
        final boolean staticImportSeparator;
        if (staticImportsApart) {
            staticImportSeparator = isStatic && separatedStaticGroups;
        }
        else {
            staticImportSeparator = separated;
        }
        final boolean separatorBetween = isStatic != lastImportStatic
            && (separated || separatedStaticGroups);

        return typeImportSeparator || staticImportSeparator || separatorBetween;
    }

    /**
     * Checks whether imports group separated internally.
     *
     * @param groupIdx group number.
     * @param isStatic whether the token is static or not.
     * @param line the line of the current import.
     * @return true if imports group are separated internally.
     */
    private boolean isSeparatorInGroup(int groupIdx, boolean isStatic, int line) {
        final boolean inSameGroup = groupIdx == lastGroup;
        return (inSameGroup || !needSeparator(isStatic)) && isSeparatorBeforeImport(line);
    }

    /**
     * Checks whether there is any separator before current import.
     *
     * @param line the line of the current import.
     * @return true if there is separator before current import which isn't the first import.
     */
    private boolean isSeparatorBeforeImport(int line) {
        return line - lastImportLine > 1;
    }

    /**
     * Shares processing...
     *
     * @param isStatic whether the token is static or not.
     * @param previous previous non-static but current is static (above), or
     *     previous static but current is non-static (under).
     * @param name the name of the current import.
     * @param ast node of the AST.
     */
    private void doVisitTokenInSameGroup(boolean isStatic,
            boolean previous, String name, DetailAST ast) {
        if (ordered) {
            if (option == ImportOrderOption.INFLOW) {
                if (isWrongOrder(name, isStatic)) {
                    log(ast, MSG_ORDERING, name);
                }
            }
            else {
                final boolean shouldFireError =
                    // previous non-static but current is static (above)
                    // or
                    // previous static but current is non-static (under)
                    previous
                        ||
                        // current and previous static or current and
                        // previous non-static
                        lastImportStatic == isStatic
                    && isWrongOrder(name, isStatic);

                if (shouldFireError) {
                    log(ast, MSG_ORDERING, name);
                }
            }
        }
    }

    /**
     * Checks whether import name is in wrong order.
     *
     * @param name import name.
     * @param isStatic whether it is a static import name.
     * @return true if import name is in wrong order.
     */
    private boolean isWrongOrder(String name, boolean isStatic) {
        final boolean result;
        if (isStatic) {
            if (useContainerOrderingForStatic) {
                result = compareContainerOrder(lastImport, name, caseSensitive) > 0;
            }
            else if (staticImportsApart) {
                result = sortStaticImportsAlphabetically
                    && compare(lastImport, name, caseSensitive) > 0;
            }
            else {
                result = compare(lastImport, name, caseSensitive) > 0;
            }
        }
        else {
            // out of lexicographic order
            result = compare(lastImport, name, caseSensitive) > 0;
        }
        return result;
    }

    /**
     * Compares two import strings.
     * We first compare the container of the static import, container being the type enclosing
     * the static element being imported. When this returns 0, we compare the qualified
     * import name. For e.g. this is what is considered to be container names:
     * <pre>
     * import static HttpConstants.COLON     =&gt; HttpConstants
     * import static HttpHeaders.addHeader   =&gt; HttpHeaders
     * import static HttpHeaders.setHeader   =&gt; HttpHeaders
     * import static HttpHeaders.Names.DATE  =&gt; HttpHeaders.Names
     * </pre>
     * <p>
     * According to this logic, HttpHeaders.Names would come after HttpHeaders.
     *
     * For more details, see <a href="https://bugs.eclipse.org/bugs/show_bug.cgi?id=473629#c3">
     * static imports comparison method</a> in Eclipse.
     * </p>
     *
     * @param importName1 first import name
     * @param importName2 second import name
     * @param caseSensitive whether the comparison of fully qualified import names is
     *                      case-sensitive
     * @return the value {@code 0} if str1 is equal to str2; a value
     *         less than {@code 0} if str is less than the str2 (container order
     *         or lexicographical); and a value greater than {@code 0} if str1 is greater than str2
     *         (container order or lexicographically)
     */
    private static int compareContainerOrder(String importName1, String importName2,
                                             boolean caseSensitive) {
        final String container1 = getImportContainer(importName1);
        final String container2 = getImportContainer(importName2);
        final int compareContainersOrderResult;
        if (caseSensitive) {
            compareContainersOrderResult = container1.compareTo(container2);
        }
        else {
            compareContainersOrderResult = container1.compareToIgnoreCase(container2);
        }
        final int result;
        if (compareContainersOrderResult == 0) {
            result = compare(importName1, importName2, caseSensitive);
        }
        else {
            result = compareContainersOrderResult;
        }
        return result;
    }

    /**
     * Extracts import container name from fully qualified import name.
     * An import container name is the type which encloses the static element being imported.
     * For example, HttpConstants, HttpHeaders, HttpHeaders.Names are import container names:
     * <pre>
     * import static HttpConstants.COLON     =&gt; HttpConstants
     * import static HttpHeaders.addHeader   =&gt; HttpHeaders
     * import static HttpHeaders.setHeader   =&gt; HttpHeaders
     * import static HttpHeaders.Names.DATE  =&gt; HttpHeaders.Names
     * </pre>
     *
     * @param qualifiedImportName fully qualified import name.
     * @return import container name.
     */
    private static String getImportContainer(String qualifiedImportName) {
        final int lastDotIndex = qualifiedImportName.lastIndexOf('.');
        return qualifiedImportName.substring(0, lastDotIndex);
    }

    /**
     * Finds out what group the specified import belongs to.
     *
     * @param isStatic whether the token is static or not.
     * @param name the import name to find.
     * @return group number for given import name.
     */
    private int getGroupNumber(boolean isStatic, String name) {
        final Pattern[] patterns;
        if (isStatic) {
            patterns = staticGroups;
        }
        else {
            patterns = groups;
        }

        int number = getGroupNumber(patterns, name);

        if (isStatic && option == ImportOrderOption.BOTTOM) {
            number += groups.length + 1;
        }
        else if (!isStatic && option == ImportOrderOption.TOP) {
            number += staticGroups.length + 1;
        }
        return number;
    }

    /**
     * Finds out what group the specified import belongs to.
     *
     * @param patterns groups to check.
     * @param name the import name to find.
     * @return group number for given import name.
     */
    private static int getGroupNumber(Pattern[] patterns, String name) {
        int bestIndex = patterns.length;
        int bestEnd = -1;
        int bestPos = Integer.MAX_VALUE;

        // find out what group this belongs in
        // loop over patterns and get index
        for (int i = 0; i < patterns.length; i++) {
            final Matcher matcher = patterns[i].matcher(name);
            if (matcher.find()) {
                if (matcher.start() < bestPos) {
                    bestIndex = i;
                    bestEnd = matcher.end();
                    bestPos = matcher.start();
                }
                else if (matcher.start() == bestPos && matcher.end() > bestEnd) {
                    bestIndex = i;
                    bestEnd = matcher.end();
                }
            }
        }
        return bestIndex;
    }

    /**
     * Compares two strings.
     *
     * @param string1
     *            the first string
     * @param string2
     *            the second string
     * @param caseSensitive
     *            whether the comparison is case-sensitive
     * @return the value {@code 0} if string1 is equal to string2; a value
     *         less than {@code 0} if string1 is lexicographically less
     *         than the string2; and a value greater than {@code 0} if
     *         string1 is lexicographically greater than string2
     */
    private static int compare(String string1, String string2,
            boolean caseSensitive) {
        final int result;
        if (caseSensitive) {
            result = string1.compareTo(string2);
        }
        else {
            result = string1.compareToIgnoreCase(string2);
        }

        return result;
    }

    /**
     * Compiles the list of package groups and the order they should occur in the file.
     *
     * @param packageGroups a comma-separated list of package names/prefixes.
     * @return array of compiled patterns.
     * @throws IllegalArgumentException if any of the package groups are not valid.
     */
    private static Pattern[] compilePatterns(String... packageGroups) {
        final Pattern[] patterns = new Pattern[packageGroups.length];

        for (int i = 0; i < packageGroups.length; i++) {
            String pkg = packageGroups[i];
            final Pattern grp;

            // if the pkg name is the wildcard, make it match zero chars
            // from any name, so it will always be used as last resort.
            if (WILDCARD_GROUP_NAME.equals(pkg)) {
                // matches any package
                grp = Pattern.compile("");
            }
            else if (CommonUtil.startsWithChar(pkg, '/')) {
                if (!CommonUtil.endsWithChar(pkg, '/')) {
                    throw new IllegalArgumentException("Invalid group: " + pkg);
                }
                pkg = pkg.substring(1, pkg.length() - 1);
                grp = Pattern.compile(pkg);
            }
            else {
                final StringBuilder pkgBuilder = new StringBuilder(pkg);
                if (!CommonUtil.endsWithChar(pkg, '.')) {
                    pkgBuilder.append('.');
                }
                grp = Pattern.compile("^" + Pattern.quote(pkgBuilder.toString()));
            }

            patterns[i] = grp;
        }
        return patterns;
    }

}
