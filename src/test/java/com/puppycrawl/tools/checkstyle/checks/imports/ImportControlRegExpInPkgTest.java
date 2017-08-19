////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class ImportControlRegExpInPkgTest {
    private ImportControl icRoot;
    private ImportControl icCommon;

    @Before
    public void setUp() {
        icRoot = new ImportControl("com\\.[^.]+\\.courtlink", true);
        icCommon = new ImportControl(icRoot, "com+on", true);
        icRoot.addChild(icCommon);
    }

    @Test
    public void testRegExpInRootIsConsidered() {
        assertNull("Package should not be null", icRoot.locateFinest("com"));
        assertNull("Package should not be null", icRoot.locateFinest("com/hurz/courtlink"));
        assertNull("Package should not be null", icRoot.locateFinest("com.hurz.hurz.courtlink"));
        assertEquals("Invalid package", icRoot, icRoot.locateFinest("com.hurz.courtlink.domain"));
        assertEquals("Invalid package", icRoot, icRoot
                .locateFinest("com.kazgroup.courtlink.domain"));
    }

    @Test
    public void testRegExpInSubpackageIsConsidered() {
        assertEquals("Invalid package", icCommon, icRoot
                .locateFinest("com.kazgroup.courtlink.common.api"));
        assertEquals("Invalid package", icCommon, icRoot
                .locateFinest("com.kazgroup.courtlink.comon.api"));
    }

    @Test
    public void testEnsureTrailingDot() {
        assertNull("Invalid package", icRoot.locateFinest("com.kazgroup.courtlinkkk"));
        assertNull("Invalid package", icRoot.locateFinest("com.kazgroup.courtlink/common.api"));
    }

    @Test
    public void testAlternationInParentIsHandledCorrectly() {
        // the regular expression has to be adjusted to (com\.foo|com\.bar)
        final ImportControl root = new ImportControl("com\\.foo|com\\.bar", true);
        final ImportControl common = new ImportControl(root, "common", false);
        root.addChild(common);
        assertEquals("Invalid package", root, root.locateFinest("com.foo"));
        assertEquals("Invalid package", common, root.locateFinest("com.foo.common"));
        assertEquals("Invalid package", root, root.locateFinest("com.bar"));
        assertEquals("Invalid package", common, root.locateFinest("com.bar.common"));
    }

    @Test
    public void testAlternationInParentIfUserCaresForIt() {
        // the regular expression has to be adjusted to (com\.foo|com\.bar)
        final ImportControl root = new ImportControl("(com\\.foo|com\\.bar)", true);
        final ImportControl common = new ImportControl(root, "common", false);
        root.addChild(common);
        assertEquals("Invalid package", root, root.locateFinest("com.foo"));
        assertEquals("Invalid package", common, root.locateFinest("com.foo.common"));
        assertEquals("Invalid package", root, root.locateFinest("com.bar"));
        assertEquals("Invalid package", common, root.locateFinest("com.bar.common"));
    }

    @Test
    public void testAlternationInSubpackageIsHandledCorrectly() {
        final ImportControl root = new ImportControl("org.somewhere", false);
        // the regular expression has to be adjusted to (foo|bar)
        final ImportControl subpackages = new ImportControl(root, "foo|bar", true);
        root.addChild(subpackages);
        assertEquals("Invalid package", root, root.locateFinest("org.somewhere"));
        assertEquals("Invalid package", subpackages, root.locateFinest("org.somewhere.foo"));
        assertEquals("Invalid package", subpackages, root.locateFinest("org.somewhere.bar"));
    }

    @Test
    public void testUnknownPkg() {
        assertNull("Package should not be null", icRoot.locateFinest("net.another"));
    }
}
