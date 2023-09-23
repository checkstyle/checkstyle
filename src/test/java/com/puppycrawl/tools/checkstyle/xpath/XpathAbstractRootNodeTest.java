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

package com.puppycrawl.tools.checkstyle.xpath;

import static com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenTextCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenTextCheck;

public class XpathAbstractRootNodeTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/xpath/xpathabstractrootnode";
    }

    @Test
    public void testAbstractRootNodeIteratorAxisDescendantOrSelf() throws Exception {
        final String pattern = "[^a-zA-z0-9]*";
        final String[] expected = {
            "17:14: " + getCheckMessage(IllegalTokenTextCheck.class, MSG_KEY, pattern),
        };

        final String[] suppressed = {
            "17:14: " + getCheckMessage(IllegalTokenTextCheck.class, MSG_KEY, pattern),
        };

        verifyFilterWithInlineConfigParser(getPath("InputxpathAbstractRootNode.java"),
                                           expected, removeSuppressed(expected, suppressed));
    }
}
