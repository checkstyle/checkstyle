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

package com.puppycrawl.tools.checkstyle.filters;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.AbstractAutomaticBean;
import com.puppycrawl.tools.checkstyle.TreeWalkerAuditEvent;
import com.puppycrawl.tools.checkstyle.TreeWalkerFilter;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.ExternalResourceHolder;
import com.puppycrawl.tools.checkstyle.utils.FilterUtil;

/**
 * <div>
 * Filter {@code SuppressionXpathFilter} works as
 * <a href="https://checkstyle.org/filters/suppressionfilter.html#SuppressionFilter">
 * SuppressionFilter</a>.
 * Additionally, filter processes {@code suppress-xpath} elements,
 * which contains xpath-expressions. Xpath-expressions are queries for
 * suppressed nodes inside the AST tree.
 * </div>
 *
 * <p>
 * Currently, filter does not support the following checks:
 * </p>
 * <ul id="IncompatibleChecks">
 * <li>
 * NoCodeInFile (reason is that AST is not generated for a file not containing code)
 * </li>
 * <li>
 * Regexp (reason is at
 * <a href="https://github.com/checkstyle/checkstyle/issues/7759#issuecomment-605525287"> #7759</a>)
 * </li>
 * <li>
 * RegexpSinglelineJava (reason is at
 * <a href="https://github.com/checkstyle/checkstyle/issues/7759#issuecomment-605525287"> #7759</a>)
 * </li>
 * </ul>
 *
 * <p>
 * Also, the filter does not support suppressions inside javadoc reported by Javadoc checks:
 * </p>
 * <ul id="JavadocChecks">
 * <li>
 * AtclauseOrder
 * </li>
 * <li>
 * JavadocBlockTagLocation
 * </li>
 * <li>
 * JavadocMethod
 * </li>
 * <li>
 * JavadocMissingLeadingAsterisk
 * </li>
 * <li>
 * JavadocMissingWhitespaceAfterAsterisk
 * </li>
 * <li>
 * JavadocParagraph
 * </li>
 * <li>
 * JavadocStyle
 * </li>
 * <li>
 * JavadocTagContinuationIndentation
 * </li>
 * <li>
 * JavadocType
 * </li>
 * <li>
 * MissingDeprecated
 * </li>
 * <li>
 * NonEmptyAtclauseDescription
 * </li>
 * <li>
 * RequireEmptyLineBeforeBlockTagGroup
 * </li>
 * <li>
 * SingleLineJavadoc
 * </li>
 * <li>
 * SummaryJavadoc
 * </li>
 * <li>
 * WriteTag
 * </li>
 * </ul>
 *
 * <p>
 * Note, that support for these Checks will be available after resolving issue
 * <a href="https://github.com/checkstyle/checkstyle/issues/5770">#5770</a>.
 * </p>
 *
 * <p>
 * Currently, filter supports the following xpath axes:
 * </p>
 * <ul>
 * <li>
 * ancestor
 * </li>
 * <li>
 * ancestor-or-self
 * </li>
 * <li>
 * attribute
 * </li>
 * <li>
 * child
 * </li>
 * <li>
 * descendant
 * </li>
 * <li>
 * descendant-or-self
 * </li>
 * <li>
 * following
 * </li>
 * <li>
 * following-sibling
 * </li>
 * <li>
 * parent
 * </li>
 * <li>
 * preceding
 * </li>
 * <li>
 * preceding-sibling
 * </li>
 * <li>
 * self
 * </li>
 * </ul>
 *
 * <p>
 * You can use the command line helper tool to generate xpath suppressions based on your
 * configuration file and input files. See <a href="https://checkstyle.org/cmdline.html">here</a>
 * for more details.
 * </p>
 *
 * <p>
 * The suppression file location is checked in following order:
 * </p>
 * <ol>
 * <li>
 * as a filesystem location
 * </li>
 * <li>
 * if no file found, and the location starts with either {@code http://} or {@code https://},
 * then it is interpreted as a URL
 * </li>
 * <li>
 * if no file found, then passed to the {@code ClassLoader.getResource()} method.
 * </li>
 * </ol>
 *
 * <p>
 * SuppressionXpathFilter can suppress Checks that have Treewalker as parent module.
 * </p>
 * <ul>
 * <li>
 * Property {@code file} - Specify the location of the <em>suppressions XML document</em> file.
 * Type is {@code java.lang.String}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code optional} - Control what to do when the file is not existing.
 * If optional is set to false the file must exist, or else it ends with error.
 * On the other hand if optional is true and file is not found, the filter accepts all audit events.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * @since 8.6
 */
public class SuppressionXpathFilter extends AbstractAutomaticBean implements
    TreeWalkerFilter, ExternalResourceHolder {

    /** Set of individual xpath suppresses. */
    private final Set<TreeWalkerFilter> filters = new HashSet<>();

    /** Specify the location of the <em>suppressions XML document</em> file. */
    private String file;
    /**
     * Control what to do when the file is not existing.
     * If optional is set to false the file must exist, or else it ends with error.
     * On the other hand if optional is true and file is not found,
     * the filter accepts all audit events.
     */
    private boolean optional;

    /**
     * Setter to specify the location of the <em>suppressions XML document</em> file.
     *
     * @param fileName name of the suppressions file.
     * @since 8.6
     */
    public void setFile(String fileName) {
        file = fileName;
    }

    /**
     * Setter to control what to do when the file is not existing.
     * If optional is set to false the file must exist, or else it ends with error.
     * On the other hand if optional is true and file is not found,
     * the filter accepts all audit events.
     *
     * @param optional tells if config file existence is optional.
     * @since 8.6
     */
    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final SuppressionXpathFilter suppressionXpathFilter = (SuppressionXpathFilter) obj;
        return Objects.equals(filters, suppressionXpathFilter.filters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filters);
    }

    @Override
    public boolean accept(TreeWalkerAuditEvent treeWalkerAuditEvent) {
        boolean result = true;
        for (TreeWalkerFilter filter : filters) {
            if (!filter.accept(treeWalkerAuditEvent)) {
                result = false;
                break;
            }
        }
        return result;
    }

    @Override
    public Set<String> getExternalResourceLocations() {
        return Collections.singleton(file);
    }

    @Override
    protected void finishLocalSetup() throws CheckstyleException {
        if (file != null) {
            if (optional) {
                if (FilterUtil.isFileExists(file)) {
                    filters.addAll(SuppressionsLoader.loadXpathSuppressions(file));
                }
            }
            else {
                filters.addAll(SuppressionsLoader.loadXpathSuppressions(file));
            }
        }
    }

}
