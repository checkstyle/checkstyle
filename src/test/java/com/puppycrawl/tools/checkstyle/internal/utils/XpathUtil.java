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

package com.puppycrawl.tools.checkstyle.internal.utils;

import java.util.List;
import java.util.stream.Collectors;

import com.puppycrawl.tools.checkstyle.xpath.AbstractNode;
import net.sf.saxon.Configuration;
import net.sf.saxon.om.Item;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.sxpath.XPathDynamicContext;
import net.sf.saxon.sxpath.XPathEvaluator;
import net.sf.saxon.sxpath.XPathExpression;
import net.sf.saxon.trans.XPathException;

/**
 * XpathUtil.
 *
 * @noinspection ClassOnlyUsedInOnePackage
 */
public final class XpathUtil {

    private XpathUtil() {
    }

    /**
     * Returns list of nodes matching xpath expression given node context.
     *
     * @param xpath Xpath expression
     * @param rootNode {@code NodeInfo} node context
     * @return list of nodes matching xpath expression given node context
     */
    public static List<NodeInfo> getXpathItems(String xpath, AbstractNode rootNode)
            throws XPathException {
        final XPathEvaluator xpathEvaluator = new XPathEvaluator(Configuration.newConfiguration());
        final XPathExpression xpathExpression = xpathEvaluator.createExpression(xpath);
        final XPathDynamicContext xpathDynamicContext = xpathExpression
                .createDynamicContext(rootNode);
        final List<Item> items = xpathExpression.evaluate(xpathDynamicContext);
        return items.stream().map(item -> (NodeInfo) item).collect(Collectors.toList());
    }

}
