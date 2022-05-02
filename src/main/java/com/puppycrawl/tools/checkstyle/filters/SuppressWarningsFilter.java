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

package com.puppycrawl.tools.checkstyle.filters;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.Filter;
import com.puppycrawl.tools.checkstyle.checks.SuppressWarningsHolder;

/**
 * <p>
 * Filter {@code SuppressWarningsFilter} uses annotation
 * {@code SuppressWarnings} to suppress audit events.
 * </p>
 * <p>
 * Rationale: Same as for {@code SuppressionCommentFilter}. In the contrary to it here,
 * comments are not used comments but the builtin syntax of {@code @SuppressWarnings}.
 * This can be perceived as a more elegant solution than using comments.
 * Also, this approach maybe supported by various IDE.
 * </p>
 * <p>
 * Usage: This filter only works in conjunction with a
 * <a href="https://checkstyle.org/config_annotation.html#SuppressWarningsHolder">
 * SuppressWarningsHolder</a>,
 * since that check finds the annotations in the Java files and makes them available for the filter.
 * Because of that, a configuration that includes this filter must also include
 * {@code SuppressWarningsHolder} as a child module of the {@code TreeWalker}.
 * Name of check in annotation is case-insensitive and should be written with
 * any dotted prefix or "Check" suffix removed.
 * </p>
 * <p>
 * SuppressWarningsFilter can suppress Checks that have Treewalker or
 * Checker as parent module.
 * </p>
 * <p>
 * To configure the check that makes tha annotations available to the filter.
 * </p>
 * <pre>
 * &lt;module name=&quot;TreeWalker&quot;&gt;
 *               ...
 * &lt;module name=&quot;SuppressWarningsHolder&quot; /&gt;
 *               ...
 * &lt;/module&gt;
 * </pre>
 * <p>
 * To configure filter to suppress audit events for annotations add:
 * </p>
 * <pre>
 * &lt;module name=&quot;SuppressWarningsFilter&quot; /&gt;
 * </pre>
 * <pre>
 * &#64;SuppressWarnings({"memberName"})
 * private int J; // should NOT fail MemberNameCheck
 *
 * &#64;SuppressWarnings({"MemberName"})
 * &#64;SuppressWarnings({"NoWhitespaceAfter"})
 * private int [] ARRAY; // should NOT fail MemberNameCheck and NoWhitespaceAfterCheck
 * </pre>
 * <p>
 * It is possible to specify an ID of checks, so that it can be leveraged by
 * the SuppressWarningsFilter to skip validations. The following examples show how to
 * skip validations near code that has {@code @SuppressWarnings("checkstyle:&lt;ID&gt;")}
 * or just {@code @SuppressWarnings("&lt;ID&gt;")} annotation, where ID is the ID
 * of checks you want to suppress.
 * </p>
 * <p>
 * Example of Checkstyle check configuration:
 * </p>
 * <pre>
 * &lt;module name=&quot;RegexpSinglelineJava&quot;&gt;
 *   &lt;property name=&quot;id&quot; value=&quot;systemout&quot;/&gt;
 *   &lt;property name=&quot;format&quot; value=&quot;^.*System\.(out|err).*$&quot;/&gt;
 *   &lt;property name=&quot;message&quot;
 *     value=&quot;Don't use System.out/err, use SLF4J instead.&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * To make the annotations available to the filter.
 * </p>
 * <pre>
 * &lt;module name=&quot;TreeWalker&quot;&gt;
 *   ...
 *   &lt;module name=&quot;SuppressWarningsHolder&quot; /&gt;
 *   ...
 * &lt;/module&gt;
 * </pre>
 * <p>
 * To configure filter to suppress audit events for annotations add:
 * </p>
 * <pre>
 * &lt;module name=&quot;SuppressWarningsFilter&quot; /&gt;
 * </pre>
 * <pre>
 * &#64;SuppressWarnings("checkstyle:systemout")
 * public static void foo() {
 *   System.out.println("Debug info."); // should NOT fail RegexpSinglelineJava
 * }
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.Checker}
 * </p>
 *
 * @since 5.7
 */
public class SuppressWarningsFilter
    extends AutomaticBean
    implements Filter {

    @Override
    protected void finishLocalSetup() {
        // No code by default
    }

    @Override
    public boolean accept(AuditEvent event) {
        return !SuppressWarningsHolder.isSuppressed(event);
    }

}
