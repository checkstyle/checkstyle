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

import com.puppycrawl.tools.checkstyle.AbstractAutomaticBean;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.Filter;
import com.puppycrawl.tools.checkstyle.checks.SuppressWarningsHolder;

/**
 * <div>
 * Filter {@code SuppressWarningsFilter} uses annotation
 * {@code @SuppressWarnings} to suppress audit events.
 * </div>
 *
 * <p>
 * Rationale: Same as for {@code SuppressionCommentFilter}. In the contrary to it here,
 * comments are not used comments but the builtin syntax of {@code @SuppressWarnings}.
 * This can be perceived as a more elegant solution than using comments.
 * Also, this approach maybe supported by various IDE.
 * </p>
 *
 * <p>
 * Usage: This filter only works in conjunction with a
 * <a href="https://checkstyle.org/checks/annotation/suppresswarningsholder.html#SuppressWarningsHolder">
 * SuppressWarningsHolder</a>,
 * since that check finds the annotations in the Java files and makes them available for the filter.
 * Because of that, a configuration that includes this filter must also include
 * {@code SuppressWarningsHolder} as a child module of the {@code TreeWalker}.
 * Name of check in annotation is case-insensitive and should be written with
 * any dotted prefix or "Check" suffix removed.
 * </p>
 *
 * <p>
 * SuppressWarningsFilter can suppress Checks that have Treewalker or
 * Checker as parent module.
 * </p>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.Checker}
 * </p>
 *
 * @since 5.7
 */
public class SuppressWarningsFilter
    extends AbstractAutomaticBean
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
