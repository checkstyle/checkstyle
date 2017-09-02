////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

package com.puppycrawl.tools.checkstyle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.xpath.XpathQueryGenerator;

/**
 * Catches {@code TreeWalkerAuditEvent} and generates corresponding xpath query.
 * Stores event and query inside static variables for {@code XpathFileGeneratorAuditListener}.
 * See issue #102 https://github.com/checkstyle/checkstyle/issues/102
 */
public class XpathFileGeneratorAstFilter extends AutomaticBean implements TreeWalkerFilter {

    /** The delimiter between xpath queries. */
    private static final String DELIMITER = " | \n";

    /** List of the {@code TreeWalkerAuditEvent} objects, which supports xpath suppressions. */
    private static final List<TreeWalkerAuditEvent> EVENTS = new ArrayList<>();

    /** List of xpath queries for corresponding {@code TreeWalkerAuditEvent} objects. */
    private static final List<String> QUERIES = new ArrayList<>();

    /** The distance between tab stop position. */
    private int tabWidth;

    /**
     * Sets tab width.
     * @param tabWidth the distance between tab stops
     */
    public void setTabWidth(int tabWidth) {
        this.tabWidth = tabWidth;
    }

    /**
     * Returns xpath query corresponding to {@code TreeWalkerAuditEvent} object which points to
     * the same AST element as specified {@code AuditEvent} object.
     * @param event the {@code AuditEvent} object.
     * @return returns corresponding xpath query
     */
    public static String findCorrespondingXpathQuery(AuditEvent event) {
        String result = null;
        for (int i = 0; i < EVENTS.size(); i++) {
            final TreeWalkerAuditEvent treeWalkerAuditEvent =
                    EVENTS.get(i);
            if (event.getSourceName().equals(treeWalkerAuditEvent.getSourceName())
                    && event.getLine() == treeWalkerAuditEvent.getLine()
                    && event.getColumn() == treeWalkerAuditEvent.getColumn()
                    && event.getFileName().endsWith(treeWalkerAuditEvent.getFileName())) {
                result = QUERIES.get(i);
                break;
            }
        }
        return result;
    }

    @Override
    protected void finishLocalSetup() {
        cleanup();
    }

    /**
     * Makes {@code EVENTS} and {@code QUERIES} lists empty.
     */
    private static void cleanup() {
        QUERIES.clear();
        EVENTS.clear();
    }

    @Override
    public boolean accept(TreeWalkerAuditEvent event) {
        if (event.getTokenType() != 0) {
            final XpathQueryGenerator xpathQueryGenerator =
                    new XpathQueryGenerator(event, tabWidth);
            final List<String> xpathQueries = xpathQueryGenerator.generate();
            if (!xpathQueries.isEmpty()) {
                final String query = xpathQueries.stream().collect(Collectors.joining(DELIMITER));
                EVENTS.add(event);
                QUERIES.add(query);
            }
        }
        return true;
    }
}
