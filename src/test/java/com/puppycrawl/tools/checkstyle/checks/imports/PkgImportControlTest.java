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

public class PkgImportControlTest {

    private final PkgImportControl icRoot = new PkgImportControl(
            "com.kazgroup.courtlink", false, MismatchStrategy.DISALLOWED);
    private final PkgImportControl icCommon = new PkgImportControl(icRoot,
            "common", false, MismatchStrategy.DELEGATE_TO_PARENT);

    private final PkgImportControl icRootRegexpChild = new PkgImportControl(
            "com.kazgroup.courtlink", false, MismatchStrategy.DELEGATE_TO_PARENT);
    private final PkgImportControl icCommonRegexpChild = new PkgImportControl(icRootRegexpChild,
            "common", false, MismatchStrategy.DELEGATE_TO_PARENT);

    private final PkgImportControl icRootRegexpParent = new PkgImportControl(
            "com\\.[^.]+\\.courtlink", true, MismatchStrategy.DELEGATE_TO_PARENT);
    private final PkgImportControl icBootRegexpParen = new PkgImportControl(icRootRegexpParent,
            "bo+t", true, MismatchStrategy.DELEGATE_TO_PARENT);

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

        icRootRegexpParent.addChild(icBootRegexpParen);
    }

    @Test
    public void testLocateFinest() {
        assertEquals("Unexpected response", icRoot, icRoot
                .locateFinest("com.kazgroup.courtlink.domain", "MyClass"));
        assertEquals("Unexpected response", icCommon, icRoot
                .locateFinest("com.kazgroup.courtlink.common.api", "MyClass"));
        assertNull("Unexpected response", icRoot.locateFinest("com", "MyClass"));
    }

    @Test
    public void testEnsureTrailingDot() {
        assertNull("Unexpected response",
                icRoot.locateFinest("com.kazgroup.courtlinkkk", "MyClass"));
        assertNull("Unexpected response",
                icRoot.locateFinest("com.kazgroup.courtlink/common.api", "MyClass"));
    }

    @Test
    public void testCheckAccess() {
        assertEquals("Unexpected access result", AccessResult.DISALLOWED, icCommon.checkAccess(
                "com.kazgroup.courtlink.common", "MyClass",
                "org.springframework.something"));
        assertEquals("Unexpected access result", AccessResult.ALLOWED, icCommon
                .checkAccess("com.kazgroup.courtlink.common", "MyClass",
                        "org.apache.commons.something"));
        assertEquals("Unexpected access result", AccessResult.DISALLOWED, icCommon.checkAccess(
                "com.kazgroup.courtlink.common", "MyClass", "org.apache.commons"));
        assertEquals("Unexpected access result", AccessResult.ALLOWED, icCommon.checkAccess(
                "com.kazgroup.courtlink.common", "MyClass", "org.hibernate.something"));
        assertEquals("Unexpected access result", AccessResult.DISALLOWED, icCommon.checkAccess(
                "com.kazgroup.courtlink.common", "MyClass", "com.badpackage.something"));
        assertEquals("Unexpected access result", AccessResult.DISALLOWED, icRoot.checkAccess(
                "com.kazgroup.courtlink", "MyClass", "org.hibernate.something"));
    }

    @Test
    public void testUnknownPkg() {
        assertNull("Unexpected response", icRoot.locateFinest("net.another", "MyClass"));
    }

    @Test
    public void testRegExpChildLocateFinest() {
        assertEquals("Unexpected response", icRootRegexpChild, icRootRegexpChild
                .locateFinest("com.kazgroup.courtlink.domain", "MyClass"));
        assertEquals("Unexpected response", icCommonRegexpChild, icRootRegexpChild
                .locateFinest("com.kazgroup.courtlink.common.api", "MyClass"));
        assertNull("Unexpected response", icRootRegexpChild.locateFinest("com", "MyClass"));
    }

    @Test
    public void testRegExpChildCheckAccess() {
        assertEquals("Unexpected access result", AccessResult.DISALLOWED,
                icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common", "MyClass",
                        "org.springframework.something"));
        assertEquals("Unexpected access result", AccessResult.DISALLOWED,
                icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common", "MyClass",
                        "org.luiframework.something"));
        assertEquals("Unexpected access result", AccessResult.DISALLOWED,
                icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common", "MyClass",
                        "de.springframework.something"));
        assertEquals("Unexpected access result", AccessResult.DISALLOWED,
                icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common", "MyClass",
                "de.luiframework.something"));
        assertEquals("Unexpected access result", AccessResult.ALLOWED,
                icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common", "MyClass",
                        "org.apache.commons.something"));
        assertEquals("Unexpected access result", AccessResult.ALLOWED,
                icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common", "MyClass",
                        "org.lui.commons.something"));
        assertEquals("Unexpected access result", AccessResult.DISALLOWED,
                icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common", "MyClass",
                        "org.apache.commons"));
        assertEquals("Unexpected access result", AccessResult.DISALLOWED,
                icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common", "MyClass",
                        "org.lui.commons"));
        assertEquals("Unexpected access result", AccessResult.ALLOWED,
                icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common", "MyClass",
                        "org.hibernate.something"));
        assertEquals("Unexpected access result", AccessResult.DISALLOWED,
                icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common", "MyClass",
                        "com.badpackage.something"));
        assertEquals("Unexpected access result", AccessResult.DISALLOWED,
                icRootRegexpChild.checkAccess("com.kazgroup.courtlink", "MyClass",
                        "org.hibernate.something"));
    }

    @Test
    public void testRegExpChildUnknownPkg() {
        assertNull("Unexpected response", icRootRegexpChild.locateFinest("net.another", "MyClass"));
    }

    @Test
    public void testRegExpParentInRootIsConsidered() {
        assertNull("Package should not be null", icRootRegexpParent.locateFinest("com", "MyClass"));
        assertNull("Package should not be null",
                icRootRegexpParent.locateFinest("com/hurz/courtlink", "MyClass"));
        assertNull("Package should not be null",
                icRootRegexpParent.locateFinest("com.hurz.hurz.courtlink", "MyClass"));
        assertEquals("Invalid package", icRootRegexpParent,
                icRootRegexpParent.locateFinest("com.hurz.courtlink.domain", "MyClass"));
        assertEquals("Invalid package", icRootRegexpParent, icRootRegexpParent
                .locateFinest("com.kazgroup.courtlink.domain", "MyClass"));
    }

    @Test
    public void testRegExpParentInSubpackageIsConsidered() {
        assertEquals("Invalid package", icBootRegexpParen, icRootRegexpParent
                .locateFinest("com.kazgroup.courtlink.boot.api", "MyClass"));
        assertEquals("Invalid package", icBootRegexpParen, icRootRegexpParent
                .locateFinest("com.kazgroup.courtlink.bot.api", "MyClass"));
    }

    @Test
    public void testRegExpParentEnsureTrailingDot() {
        assertNull("Invalid package",
                icRootRegexpParent.locateFinest("com.kazgroup.courtlinkkk", "MyClass"));
        assertNull("Invalid package",
                icRootRegexpParent.locateFinest("com.kazgroup.courtlink/common.api", "MyClass"));
    }

    @Test
    public void testRegExpParentAlternationInParentIsHandledCorrectly() {
        // the regular expression has to be adjusted to (com\.foo|com\.bar)
        final PkgImportControl root = new PkgImportControl("com\\.foo|com\\.bar", true,
                MismatchStrategy.DISALLOWED);
        final PkgImportControl common = new PkgImportControl(root, "common", false,
                MismatchStrategy.DELEGATE_TO_PARENT);
        root.addChild(common);
        assertEquals("Invalid package", root, root.locateFinest("com.foo", "MyClass"));
        assertEquals("Invalid package", common, root.locateFinest("com.foo.common", "MyClass"));
        assertEquals("Invalid package", root, root.locateFinest("com.bar", "MyClass"));
        assertEquals("Invalid package", common, root.locateFinest("com.bar.common", "MyClass"));
    }

    @Test
    public void testRegExpParentAlternationInParentIfUserCaresForIt() {
        // the regular expression has to be adjusted to (com\.foo|com\.bar)
        final PkgImportControl root = new PkgImportControl("(com\\.foo|com\\.bar)", true,
                MismatchStrategy.DISALLOWED);
        final PkgImportControl common = new PkgImportControl(root, "common", false,
                MismatchStrategy.DELEGATE_TO_PARENT);
        root.addChild(common);
        assertEquals("Invalid package", root, root.locateFinest("com.foo", "MyClass"));
        assertEquals("Invalid package", common, root.locateFinest("com.foo.common", "MyClass"));
        assertEquals("Invalid package", root, root.locateFinest("com.bar", "MyClass"));
        assertEquals("Invalid package", common, root.locateFinest("com.bar.common", "MyClass"));
    }

    @Test
    public void testRegExpParentAlternationInSubpackageIsHandledCorrectly() {
        final PkgImportControl root = new PkgImportControl("org.somewhere", false,
                MismatchStrategy.DISALLOWED);
        // the regular expression has to be adjusted to (foo|bar)
        final PkgImportControl subpackages = new PkgImportControl(root, "foo|bar", true,
                MismatchStrategy.DELEGATE_TO_PARENT);
        root.addChild(subpackages);
        assertEquals("Invalid package", root, root.locateFinest("org.somewhere", "MyClass"));
        assertEquals("Invalid package", subpackages,
                root.locateFinest("org.somewhere.foo", "MyClass"));
        assertEquals("Invalid package", subpackages,
                root.locateFinest("org.somewhere.bar", "MyClass"));
    }

    @Test
    public void testRegExpParentUnknownPkg() {
        assertNull("Package should not be null",
                icRootRegexpParent.locateFinest("net.another", "MyClass"));
    }

}
