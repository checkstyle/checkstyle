////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @BeforeEach
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
        assertEquals(icRoot, icRoot.locateFinest("com.kazgroup.courtlink.domain", "MyClass"),
                "Unexpected response");
        assertEquals(icCommon, icRoot.locateFinest("com.kazgroup.courtlink.common.api", "MyClass"),
                "Unexpected response");
        assertNull(icRoot.locateFinest("com", "MyClass"), "Unexpected response");
    }

    @Test
    public void testEnsureTrailingDot() {
        assertNull(icRoot.locateFinest("com.kazgroup.courtlinkkk", "MyClass"),
                "Unexpected response");
        assertNull(icRoot.locateFinest("com.kazgroup.courtlink/common.api", "MyClass"),
                "Unexpected response");
    }

    @Test
    public void testCheckAccess() {
        assertEquals(AccessResult.DISALLOWED, icCommon.checkAccess(
                "com.kazgroup.courtlink.common", "MyClass",
                "org.springframework.something"), "Unexpected access result");
        assertEquals(AccessResult.ALLOWED, icCommon
                .checkAccess("com.kazgroup.courtlink.common", "MyClass",
                        "org.apache.commons.something"), "Unexpected access result");
        assertEquals(AccessResult.DISALLOWED, icCommon.checkAccess(
                "com.kazgroup.courtlink.common", "MyClass",
                "org.apache.commons"), "Unexpected access result");
        assertEquals(AccessResult.ALLOWED, icCommon.checkAccess(
                "com.kazgroup.courtlink.common", "MyClass",
                "org.hibernate.something"), "Unexpected access result");
        assertEquals(AccessResult.DISALLOWED, icCommon.checkAccess(
                "com.kazgroup.courtlink.common", "MyClass",
                "com.badpackage.something"), "Unexpected access result");
        assertEquals(AccessResult.DISALLOWED, icRoot.checkAccess(
                "com.kazgroup.courtlink", "MyClass",
                "org.hibernate.something"), "Unexpected access result");
    }

    @Test
    public void testUnknownPkg() {
        assertNull(icRoot.locateFinest("net.another", "MyClass"), "Unexpected response");
    }

    @Test
    public void testRegExpChildLocateFinest() {
        assertEquals(icRootRegexpChild,
                icRootRegexpChild.locateFinest("com.kazgroup.courtlink.domain", "MyClass"),
                "Unexpected response");
        assertEquals(icCommonRegexpChild,
                icRootRegexpChild.locateFinest("com.kazgroup.courtlink.common.api", "MyClass"),
                "Unexpected response");
        assertNull(icRootRegexpChild.locateFinest("com", "MyClass"), "Unexpected response");
    }

    @Test
    public void testRegExpChildCheckAccess() {
        assertEquals(AccessResult.DISALLOWED,
                icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common", "MyClass",
                        "org.springframework.something"), "Unexpected access result");
        assertEquals(AccessResult.DISALLOWED,
                icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common", "MyClass",
                        "org.luiframework.something"), "Unexpected access result");
        assertEquals(AccessResult.DISALLOWED,
                icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common", "MyClass",
                        "de.springframework.something"), "Unexpected access result");
        assertEquals(AccessResult.DISALLOWED,
                icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common", "MyClass",
                "de.luiframework.something"), "Unexpected access result");
        assertEquals(AccessResult.ALLOWED,
                icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common", "MyClass",
                        "org.apache.commons.something"), "Unexpected access result");
        assertEquals(AccessResult.ALLOWED,
                icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common", "MyClass",
                        "org.lui.commons.something"), "Unexpected access result");
        assertEquals(AccessResult.DISALLOWED,
                icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common", "MyClass",
                        "org.apache.commons"), "Unexpected access result");
        assertEquals(AccessResult.DISALLOWED,
                icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common", "MyClass",
                        "org.lui.commons"), "Unexpected access result");
        assertEquals(AccessResult.ALLOWED,
                icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common", "MyClass",
                        "org.hibernate.something"), "Unexpected access result");
        assertEquals(AccessResult.DISALLOWED,
                icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common", "MyClass",
                        "com.badpackage.something"), "Unexpected access result");
        assertEquals(AccessResult.DISALLOWED,
                icRootRegexpChild.checkAccess("com.kazgroup.courtlink", "MyClass",
                        "org.hibernate.something"), "Unexpected access result");
    }

    @Test
    public void testRegExpChildUnknownPkg() {
        assertNull(icRootRegexpChild.locateFinest("net.another", "MyClass"), "Unexpected response");
    }

    @Test
    public void testRegExpParentInRootIsConsidered() {
        assertNull(icRootRegexpParent.locateFinest("com", "MyClass"), "Package should not be null");
        assertNull(icRootRegexpParent.locateFinest("com/hurz/courtlink", "MyClass"),
                "Package should not be null");
        assertNull(icRootRegexpParent.locateFinest("com.hurz.hurz.courtlink", "MyClass"),
                "Package should not be null");
        assertEquals(icRootRegexpParent,
                icRootRegexpParent.locateFinest("com.hurz.courtlink.domain", "MyClass"),
                "Invalid package");
        assertEquals(icRootRegexpParent,
                icRootRegexpParent.locateFinest("com.kazgroup.courtlink.domain", "MyClass"),
                "Invalid package");
    }

    @Test
    public void testRegExpParentInSubpackageIsConsidered() {
        assertEquals(icBootRegexpParen, icRootRegexpParent
                .locateFinest("com.kazgroup.courtlink.boot.api", "MyClass"), "Invalid package");
        assertEquals(icBootRegexpParen, icRootRegexpParent
                .locateFinest("com.kazgroup.courtlink.bot.api", "MyClass"), "Invalid package");
    }

    @Test
    public void testRegExpParentEnsureTrailingDot() {
        assertNull(icRootRegexpParent.locateFinest("com.kazgroup.courtlinkkk", "MyClass"),
                "Invalid package");
        assertNull(icRootRegexpParent.locateFinest("com.kazgroup.courtlink/common.api", "MyClass"),
                "Invalid package");
    }

    @Test
    public void testRegExpParentAlternationInParentIsHandledCorrectly() {
        // the regular expression has to be adjusted to (com\.foo|com\.bar)
        final PkgImportControl root = new PkgImportControl("com\\.foo|com\\.bar", true,
                MismatchStrategy.DISALLOWED);
        final PkgImportControl common = new PkgImportControl(root, "common", false,
                MismatchStrategy.DELEGATE_TO_PARENT);
        root.addChild(common);
        assertEquals(root, root.locateFinest("com.foo", "MyClass"), "Invalid package");
        assertEquals(common, root.locateFinest("com.foo.common", "MyClass"), "Invalid package");
        assertEquals(root, root.locateFinest("com.bar", "MyClass"), "Invalid package");
        assertEquals(common, root.locateFinest("com.bar.common", "MyClass"), "Invalid package");
    }

    @Test
    public void testRegExpParentAlternationInParentIfUserCaresForIt() {
        // the regular expression has to be adjusted to (com\.foo|com\.bar)
        final PkgImportControl root = new PkgImportControl("(com\\.foo|com\\.bar)", true,
                MismatchStrategy.DISALLOWED);
        final PkgImportControl common = new PkgImportControl(root, "common", false,
                MismatchStrategy.DELEGATE_TO_PARENT);
        root.addChild(common);
        assertEquals(root, root.locateFinest("com.foo", "MyClass"), "Invalid package");
        assertEquals(common, root.locateFinest("com.foo.common", "MyClass"), "Invalid package");
        assertEquals(root, root.locateFinest("com.bar", "MyClass"), "Invalid package");
        assertEquals(common, root.locateFinest("com.bar.common", "MyClass"), "Invalid package");
    }

    @Test
    public void testRegExpParentAlternationInSubpackageIsHandledCorrectly() {
        final PkgImportControl root = new PkgImportControl("org.somewhere", false,
                MismatchStrategy.DISALLOWED);
        // the regular expression has to be adjusted to (foo|bar)
        final PkgImportControl subpackages = new PkgImportControl(root, "foo|bar", true,
                MismatchStrategy.DELEGATE_TO_PARENT);
        root.addChild(subpackages);
        assertEquals(root, root.locateFinest("org.somewhere", "MyClass"), "Invalid package");
        assertEquals(subpackages,
                root.locateFinest("org.somewhere.foo", "MyClass"), "Invalid package");
        assertEquals(subpackages,
                root.locateFinest("org.somewhere.bar", "MyClass"), "Invalid package");
    }

    @Test
    public void testRegExpParentUnknownPkg() {
        assertNull(icRootRegexpParent.locateFinest("net.another", "MyClass"),
                "Package should not be null");
    }

}
