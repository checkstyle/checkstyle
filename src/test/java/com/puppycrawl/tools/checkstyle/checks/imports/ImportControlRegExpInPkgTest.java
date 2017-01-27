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

import org.junit.Test;

public class ImportControlRegExpInPkgTest {
    private final ImportControl icRoot = new ImportControl("com\\.[^.]+\\.courtlink", true);
    private final ImportControl icCommon = new ImportControl(icRoot, "com+on", true);

    @Test
    public void testRegExpInRootIsConsidered() {
        assertNull(icRoot.locateFinest("com"));
        assertNull(icRoot.locateFinest("com/hurz/courtlink"));
        assertNull(icRoot.locateFinest("com.hurz.hurz.courtlink"));
        assertEquals(icRoot, icRoot
                .locateFinest("com.hurz.courtlink.domain"));
        assertEquals(icRoot, icRoot
                .locateFinest("com.kazgroup.courtlink.domain"));
    }

    @Test
    public void testRegExpInSubpackageIsConsidered() {
        assertEquals(icCommon, icRoot
                .locateFinest("com.kazgroup.courtlink.common.api"));
        assertEquals(icCommon, icRoot
                .locateFinest("com.kazgroup.courtlink.comon.api"));
    }

    @Test
    public void testEnsureTrailingDot() {
        assertNull(icRoot.locateFinest("com.kazgroup.courtlinkkk"));
        assertNull(icRoot.locateFinest("com.kazgroup.courtlink/common.api"));
    }

    @Test
    public void testAlternationInParentIsHandledCorrectly() {
        // the regular expression has to be adjusted to (com\.foo|com\.bar)
        final ImportControl root = new ImportControl("com\\.foo|com\\.bar", true);
        final ImportControl common = new ImportControl(root, "common", false);
        assertEquals(root, root.locateFinest("com.foo"));
        assertEquals(common, root.locateFinest("com.foo.common"));
        assertEquals(root, root.locateFinest("com.bar"));
        assertEquals(common, root.locateFinest("com.bar.common"));
    }

    @Test
    public void testAlternationInParentIfUserCaresForIt() {
        // the regular expression has to be adjusted to (com\.foo|com\.bar)
        final ImportControl root = new ImportControl("(com\\.foo|com\\.bar)", true);
        final ImportControl common = new ImportControl(root, "common", false);
        assertEquals(root, root.locateFinest("com.foo"));
        assertEquals(common, root.locateFinest("com.foo.common"));
        assertEquals(root, root.locateFinest("com.bar"));
        assertEquals(common, root.locateFinest("com.bar.common"));
    }

    @Test
    public void testAlternationInSubpackageIsHandledCorrectly() {
        final ImportControl root = new ImportControl("org.somewhere", false);
        // the regular expression has to be adjusted to (foo|bar)
        final ImportControl subpackages = new ImportControl(root, "foo|bar", true);
        assertEquals(root, root.locateFinest("org.somewhere"));
        assertEquals(subpackages, root.locateFinest("org.somewhere.foo"));
        assertEquals(subpackages, root.locateFinest("org.somewhere.bar"));
    }

    @Test
    public void testUnknownPkg() {
        assertNull(icRoot.locateFinest("net.another"));
    }
}
