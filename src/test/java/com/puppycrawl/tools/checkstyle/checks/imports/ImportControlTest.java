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

package com.puppycrawl.tools.checkstyle.checks.imports;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class ImportControlTest {

    private final ImportControl icRoot = new ImportControl("com.kazgroup.courtlink", false);
    private final ImportControl icCommon = new ImportControl(icRoot, "common", false);

    private final ImportControl icRootRegexpChild = new ImportControl("com.kazgroup.courtlink",
            false);
    private final ImportControl icCommonRegexpChild = new ImportControl(icRootRegexpChild,
            "common", false);

    private final ImportControl icRootRegexpParent = new ImportControl("com\\.[^.]+\\.courtlink",
            true);
    private final ImportControl icCommonRegexpParen = new ImportControl(icRootRegexpParent,
            "com+on", true);

    @Before
    public void setUp() {
        icRoot.addChild(icCommon);
        icRoot.addImportRule(
            new PkgImportRule(false, false, "org.springframework", false, false));
        icRoot.addImportRule(
            new PkgImportRule(false, false, "org.hibernate", false, false));
        icRoot.addImportRule(
            new PkgImportRule(true, false, "org.apache.commons", false, false));

        icRootRegexpChild.addChild(icCommonRegexpChild);
        icRootRegexpChild.addImportRule(
            new PkgImportRule(false, false, ".*\\.(spring|lui)framework", false, true));
        icRootRegexpChild.addImportRule(
            new PkgImportRule(false, false, "org\\.hibernate", false, true));
        icRootRegexpChild.addImportRule(
            new PkgImportRule(true, false, "org\\.(apache|lui)\\.commons", false, true));

        icCommon.addImportRule(
            new PkgImportRule(true, false, "org.hibernate", false, false));

        icCommonRegexpChild.addImportRule(
            new PkgImportRule(true, false, "org\\.h.*", false, true));

        icRootRegexpParent.addChild(icCommonRegexpParen);
    }

    @Test
    public void testLocateFinest() {
        assertEquals("Unexpected response", icRoot, icRoot
                .locateFinest("com.kazgroup.courtlink.domain"));
        assertEquals("Unexpected response", icCommon, icRoot
                .locateFinest("com.kazgroup.courtlink.common.api"));
        assertNull("Unexpected response", icRoot.locateFinest("com"));
    }

    @Test
    public void testEnsureTrailingDot() {
        assertNull("Unexpected response", icRoot.locateFinest("com.kazgroup.courtlinkkk"));
        assertNull("Unexpected response",
                icRoot.locateFinest("com.kazgroup.courtlink/common.api"));
    }

    @Test
    public void testCheckAccess() {
        assertEquals("Unexpected access result", AccessResult.DISALLOWED, icCommon.checkAccess(
                "com.kazgroup.courtlink.common",
                "org.springframework.something"));
        assertEquals("Unexpected access result", AccessResult.ALLOWED, icCommon
                .checkAccess("com.kazgroup.courtlink.common",
                        "org.apache.commons.something"));
        assertEquals("Unexpected access result", AccessResult.DISALLOWED, icCommon.checkAccess(
                "com.kazgroup.courtlink.common", "org.apache.commons"));
        assertEquals("Unexpected access result", AccessResult.ALLOWED, icCommon.checkAccess(
                "com.kazgroup.courtlink.common", "org.hibernate.something"));
        assertEquals("Unexpected access result", AccessResult.DISALLOWED, icCommon.checkAccess(
                "com.kazgroup.courtlink.common", "com.badpackage.something"));
        assertEquals("Unexpected access result", AccessResult.DISALLOWED, icRoot.checkAccess(
                "com.kazgroup.courtlink", "org.hibernate.something"));
    }

    @Test
    public void testUnknownPkg() {
        assertNull("Unexpected response", icRoot.locateFinest("net.another"));
    }

    @Test
    public void testRegExpChildLocateFinest() {
        assertEquals("Unexpected response", icRootRegexpChild, icRootRegexpChild
                .locateFinest("com.kazgroup.courtlink.domain"));
        assertEquals("Unexpected response", icCommonRegexpChild, icRootRegexpChild
                .locateFinest("com.kazgroup.courtlink.common.api"));
        assertNull("Unexpected response", icRootRegexpChild.locateFinest("com"));
    }

    @Test
    public void testRegExpChildCheckAccess() {
        assertEquals("Unexpected access result", AccessResult.DISALLOWED,
                icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common",
                        "org.springframework.something"));
        assertEquals("Unexpected access result", AccessResult.DISALLOWED,
                icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common",
                        "org.luiframework.something"));
        assertEquals("Unexpected access result", AccessResult.DISALLOWED,
                icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common",
                        "de.springframework.something"));
        assertEquals("Unexpected access result", AccessResult.DISALLOWED,
                icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common",
                "de.luiframework.something"));
        assertEquals("Unexpected access result", AccessResult.ALLOWED,
                icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common",
                        "org.apache.commons.something"));
        assertEquals("Unexpected access result", AccessResult.ALLOWED,
                icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common",
                        "org.lui.commons.something"));
        assertEquals("Unexpected access result", AccessResult.DISALLOWED,
                icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common",
                        "org.apache.commons"));
        assertEquals("Unexpected access result", AccessResult.DISALLOWED,
                icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common",
                        "org.lui.commons"));
        assertEquals("Unexpected access result", AccessResult.ALLOWED,
                icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common",
                        "org.hibernate.something"));
        assertEquals("Unexpected access result", AccessResult.DISALLOWED,
                icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common",
                        "com.badpackage.something"));
        assertEquals("Unexpected access result", AccessResult.DISALLOWED,
                icRootRegexpChild.checkAccess("com.kazgroup.courtlink",
                        "org.hibernate.something"));
    }

    @Test
    public void testRegExpChildUnknownPkg() {
        assertNull("Unexpected response", icRootRegexpChild.locateFinest("net.another"));
    }

    @Test
    public void testRegExpParentInRootIsConsidered() {
        assertNull("Package should not be null", icRootRegexpParent.locateFinest("com"));
        assertNull("Package should not be null",
                icRootRegexpParent.locateFinest("com/hurz/courtlink"));
        assertNull("Package should not be null",
                icRootRegexpParent.locateFinest("com.hurz.hurz.courtlink"));
        assertEquals("Invalid package", icRootRegexpParent,
                icRootRegexpParent.locateFinest("com.hurz.courtlink.domain"));
        assertEquals("Invalid package", icRootRegexpParent, icRootRegexpParent
                .locateFinest("com.kazgroup.courtlink.domain"));
    }

    @Test
    public void testRegExpParentInSubpackageIsConsidered() {
        assertEquals("Invalid package", icCommonRegexpParen, icRootRegexpParent
                .locateFinest("com.kazgroup.courtlink.common.api"));
        assertEquals("Invalid package", icCommonRegexpParen, icRootRegexpParent
                .locateFinest("com.kazgroup.courtlink.comon.api"));
    }

    @Test
    public void testRegExpParentEnsureTrailingDot() {
        assertNull("Invalid package", icRootRegexpParent.locateFinest("com.kazgroup.courtlinkkk"));
        assertNull("Invalid package",
                icRootRegexpParent.locateFinest("com.kazgroup.courtlink/common.api"));
    }

    @Test
    public void testRegExpParentAlternationInParentIsHandledCorrectly() {
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
    public void testRegExpParentAlternationInParentIfUserCaresForIt() {
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
    public void testRegExpParentAlternationInSubpackageIsHandledCorrectly() {
        final ImportControl root = new ImportControl("org.somewhere", false);
        // the regular expression has to be adjusted to (foo|bar)
        final ImportControl subpackages = new ImportControl(root, "foo|bar", true);
        root.addChild(subpackages);
        assertEquals("Invalid package", root, root.locateFinest("org.somewhere"));
        assertEquals("Invalid package", subpackages, root.locateFinest("org.somewhere.foo"));
        assertEquals("Invalid package", subpackages, root.locateFinest("org.somewhere.bar"));
    }

    @Test
    public void testRegExpParentUnknownPkg() {
        assertNull("Package should not be null", icRootRegexpParent.locateFinest("net.another"));
    }

}
