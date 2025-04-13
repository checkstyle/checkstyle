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

import java.util.Set;

import com.puppycrawl.tools.checkstyle.AbstractAutomaticBean;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.ExternalResourceHolder;
import com.puppycrawl.tools.checkstyle.api.Filter;
import com.puppycrawl.tools.checkstyle.api.FilterSet;
import com.puppycrawl.tools.checkstyle.utils.FilterUtil;
import com.puppycrawl.tools.checkstyle.utils.UnmodifiableCollectionUtil;

/**
 * <div>
 * Filter {@code SuppressionFilter} rejects audit events for Check violations according to a
 * <a href="https://checkstyle.org/dtds/suppressions_1_2.dtd">suppressions XML document</a>
 * in a file. If there is no configured suppressions file or the optional is set to true and
 * suppressions file was not found the Filter accepts all audit events.
 * </div>
 *
 * <p>
 * A <a href="https://checkstyle.org/dtds/suppressions_1_2.dtd">suppressions XML document</a>
 * contains a set of {@code suppress} elements, where each {@code suppress}
 * element can have the following attributes:
 * </p>
 * <ul>
 * <li>
 * {@code files} - a <a href="https://checkstyle.org/property_types.html#Pattern">
 * Pattern</a> matched against the file name associated with an audit event.
 * It is optional.
 * </li>
 * <li>
 * {@code checks} - a <a href="https://checkstyle.org/property_types.html#Pattern">
 * Pattern</a> matched against the name of the check associated with an audit event.
 * Optional as long as {@code id} or {@code message} is specified.
 * </li>
 * <li>
 * {@code message} - a <a href="https://checkstyle.org/property_types.html#Pattern">
 * Pattern</a> matched against the message of the check associated with an audit event.
 * Optional as long as {@code checks} or {@code id} is specified.
 * </li>
 * <li>
 * {@code id} - a <a href="https://checkstyle.org/property_types.html#String">String</a>
 * matched against the <a href="https://checkstyle.org/config.html#Id">check id</a>
 * associated with an audit event.
 * Optional as long as {@code checks} or {@code message} is specified.
 * </li>
 * <li>
 * {@code lines} - a comma-separated list of values, where each value is an
 * <a href="https://checkstyle.org/property_types.html#int">int</a>
 * or a range of integers denoted by integer-integer.
 * It is optional.
 * </li>
 * <li>
 * {@code columns} - a comma-separated list of values, where each value is an
 * <a href="https://checkstyle.org/property_types.html#int">int</a>
 * or a range of integers denoted by integer-integer.
 * It is optional.
 * </li>
 * </ul>
 *
 * <p>
 * Each audit event is checked against each {@code suppress} element.
 * It is suppressed if all specified attributes match against the audit event.
 * </p>
 *
 * <p>
 * ATTENTION: filtering by message is dependent on runtime locale.
 * If project is running in different languages it is better to avoid filtering by message.
 * </p>
 *
 * <p>
 * You can download template of empty suppression filter
 * <a href="https://checkstyle.org/files/suppressions_none.xml">here</a>.
 * </p>
 *
 * <p>
 * Location of the file defined in {@code file} property is checked in the following order:
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
 * SuppressionFilter can suppress Checks that have Treewalker or Checker as parent module.
 * </p>
 * <ul>
 * <li>
 * Property {@code file} - Specify the location of the <em>suppressions XML document</em> file.
 * Type is {@code java.lang.String}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code optional} - Control what to do when the file is not existing.
 * If {@code optional} is set to {@code false} the file must exist, or else it
 * ends with error. On the other hand if optional is {@code true} and file is
 * not found, the filter accept all audit events.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.Checker}
 * </p>
 *
 * @since 3.2
 */
public class SuppressionFilter
    extends AbstractAutomaticBean
    implements Filter, ExternalResourceHolder {

    /** Specify the location of the <em>suppressions XML document</em> file. */
    private String file;
    /**
     * Control what to do when the file is not existing. If {@code optional} is
     * set to {@code false} the file must exist, or else it ends with error.
     * On the other hand if optional is {@code true} and file is not found,
     * the filter accept all audit events.
     */
    private boolean optional;
    /** Set of individual suppresses. */
    private FilterSet filters = new FilterSet();

    /**
     * Setter to specify the location of the <em>suppressions XML document</em> file.
     *
     * @param fileName name of the suppressions file.
     * @since 3.2
     */
    public void setFile(String fileName) {
        file = fileName;
    }

    /**
     * Setter to control what to do when the file is not existing.
     * If {@code optional} is set to {@code false} the file must exist, or else
     * it ends with error. On the other hand if optional is {@code true}
     * and file is not found, the filter accept all audit events.
     *
     * @param optional tells if config file existence is optional.
     * @since 6.15
     */
    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    @Override
    public boolean accept(AuditEvent event) {
        return filters.accept(event);
    }

    @Override
    protected void finishLocalSetup() throws CheckstyleException {
        if (file != null) {
            if (optional) {
                if (FilterUtil.isFileExists(file)) {
                    filters = SuppressionsLoader.loadSuppressions(file);
                }
            }
            else {
                filters = SuppressionsLoader.loadSuppressions(file);
            }
        }
    }

    @Override
    public Set<String> getExternalResourceLocations() {
        return UnmodifiableCollectionUtil.singleton(file);
    }

}
