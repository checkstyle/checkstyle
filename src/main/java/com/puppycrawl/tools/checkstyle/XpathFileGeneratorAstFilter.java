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

package com.puppycrawl.tools.checkstyle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.Violation;
import com.puppycrawl.tools.checkstyle.xpath.XpathQueryGenerator;

/**
 * Catches {@code TreeWalkerAuditEvent} and generates corresponding xpath query.
 * Stores violations and xpath queries map inside static variable
 * for {@code XpathFileGeneratorAuditListener}.
 * See issue <a href="https://github.com/checkstyle/checkstyle/issues/102">#102</a>
 */
public class XpathFileGeneratorAstFilter extends AbstractAutomaticBean implements TreeWalkerFilter {

    /** The delimiter between xpath queries. */
    private static final String DELIMITER = " | \n";

    /** Map from {@code Violation} objects to xpath queries. */
    private static final Map<Violation, String> MESSAGE_QUERY_MAP = new HashMap<>();

    /** The distance between tab stop position. */
    private int tabWidth;

    /**
     * Sets tab width.
     *
     * @param tabWidth the distance between tab stops
     */
    public void setTabWidth(int tabWidth) {
        this.tabWidth = tabWidth;
    }

    /**
     * Returns xpath query corresponding to violation of the
     * {@code TreeWalkerAuditEvent} object which points to the same AST element as specified
     * {@code AuditEvent} object.
     *
     * @param event the {@code AuditEvent} object.
     * @return returns corresponding xpath query
     */
    public static String findCorrespondingXpathQuery(AuditEvent event) {
        return MESSAGE_QUERY_MAP.get(event.getViolation());
    }

    @Override
    protected void finishLocalSetup() {
        MESSAGE_QUERY_MAP.clear();
    }

    @Override
    public boolean accept(TreeWalkerAuditEvent event) {
        if (event.getTokenType() != 0) {
            final XpathQueryGenerator xpathQueryGenerator =
                new XpathQueryGenerator(event, tabWidth);
            final List<String> xpathQueries = xpathQueryGenerator.generate();
            if (!xpathQueries.isEmpty()) {
                final String query = String.join(DELIMITER, xpathQueries);
                MESSAGE_QUERY_MAP.put(event.getViolation(), query);
            }
        }
        return true;
    }
}
