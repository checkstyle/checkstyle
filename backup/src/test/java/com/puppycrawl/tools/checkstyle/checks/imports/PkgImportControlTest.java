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

package com.puppycrawl.tools.checkstyle.checks.imports;

import static com.google.common.truth.Truth.assertWithMessage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PkgImportControlTest {

    private final PkgImportControl icRoot = new PkgImportControl(
            "com.kazgroup.courtlink", false, MismatchStrategy.DISALLOWED);
    private final PkgImportControl icCommon = new PkgImportControl(icRoot,
            "common", false, MismatchStrategy.DELEGATE_TO_PARENT);
    private final PkgImportControl icUncommon = new PkgImportControl(icRoot,
            "uncommon", true, MismatchStrategy.DELEGATE_TO_PARENT);

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
    public void testDotMetaCharacter() {
        assertWithMessage("Unexpected response")
            .that(icUncommon.locateFinest("com-kazgroup.courtlink.uncommon.regexp", "MyClass"))
            .isNull();
    }

    @Test
    public void testLocateFinest() {
        assertWithMessage("Unexpected response")
            .that(icRoot.locateFinest("com.kazgroup.courtlink.domain", "MyClass"))
            .isEqualTo(icRoot);
        assertWithMessage("Unexpected response")
            .that(icRoot.locateFinest("com.kazgroup.courtlink.common.api", "MyClass"))
            .isEqualTo(icCommon);
        assertWithMessage("Unexpected response")
            .that(icRoot.locateFinest("com", "MyClass"))
            .isNull();
    }

    @Test
    public void testEnsureTrailingDot() {
        assertWithMessage("Unexpected response")
            .that(icRoot.locateFinest("com.kazgroup.courtlinkkk", "MyClass"))
            .isNull();
        assertWithMessage("Unexpected response")
            .that(icRoot.locateFinest("com.kazgroup.courtlink/common.api", "MyClass"))
            .isNull();
    }

    @Test
    public void testCheckAccess() {
        assertWithMessage("Unexpected access result")
            .that(icCommon.checkAccess(
                "com.kazgroup.courtlink.common", "MyClass",
                "org.springframework.something"))
            .isEqualTo(AccessResult.DISALLOWED);
        assertWithMessage("Unexpected access result")
            .that(icCommon
                .checkAccess("com.kazgroup.courtlink.common", "MyClass",
                        "org.apache.commons.something"))
            .isEqualTo(AccessResult.ALLOWED);
        assertWithMessage("Unexpected access result")
            .that(icCommon.checkAccess(
                "com.kazgroup.courtlink.common", "MyClass",
                "org.apache.commons"))
            .isEqualTo(AccessResult.DISALLOWED);
        assertWithMessage("Unexpected access result")
            .that(icCommon.checkAccess(
                "com.kazgroup.courtlink.common", "MyClass",
                "org.hibernate.something"))
            .isEqualTo(AccessResult.ALLOWED);
        assertWithMessage("Unexpected access result")
            .that(icCommon.checkAccess(
                "com.kazgroup.courtlink.common", "MyClass",
                "com.badpackage.something"))
            .isEqualTo(AccessResult.DISALLOWED);
        assertWithMessage("Unexpected access result")
            .that(icRoot.checkAccess(
                "com.kazgroup.courtlink", "MyClass",
                "org.hibernate.something"))
            .isEqualTo(AccessResult.DISALLOWED);
    }

    @Test
    public void testUnknownPkg() {
        assertWithMessage("Unexpected response")
            .that(icRoot.locateFinest("net.another", "MyClass"))
            .isNull();
    }

    @Test
    public void testRegExpChildLocateFinest() {
        assertWithMessage("Unexpected response")
            .that(icRootRegexpChild.locateFinest("com.kazgroup.courtlink.domain", "MyClass"))
            .isEqualTo(icRootRegexpChild);
        assertWithMessage("Unexpected response")
            .that(icRootRegexpChild.locateFinest("com.kazgroup.courtlink.common.api", "MyClass"))
            .isEqualTo(icCommonRegexpChild);
        assertWithMessage("Unexpected response")
            .that(icRootRegexpChild.locateFinest("com", "MyClass"))
            .isNull();
    }

    @Test
    public void testRegExpChildCheckAccess() {
        assertWithMessage("Unexpected access result")
            .that(icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common", "MyClass",
                        "org.springframework.something"))
            .isEqualTo(AccessResult.DISALLOWED);
        assertWithMessage("Unexpected access result")
            .that(icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common", "MyClass",
                        "org.luiframework.something"))
            .isEqualTo(AccessResult.DISALLOWED);
        assertWithMessage("Unexpected access result")
            .that(icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common", "MyClass",
                        "de.springframework.something"))
            .isEqualTo(AccessResult.DISALLOWED);
        assertWithMessage("Unexpected access result")
            .that(icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common", "MyClass",
                "de.luiframework.something"))
            .isEqualTo(AccessResult.DISALLOWED);
        assertWithMessage("Unexpected access result")
            .that(icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common", "MyClass",
                        "org.apache.commons.something"))
            .isEqualTo(AccessResult.ALLOWED);
        assertWithMessage("Unexpected access result")
            .that(icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common", "MyClass",
                        "org.lui.commons.something"))
            .isEqualTo(AccessResult.ALLOWED);
        assertWithMessage("Unexpected access result")
            .that(icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common", "MyClass",
                        "org.apache.commons"))
            .isEqualTo(AccessResult.DISALLOWED);
        assertWithMessage("Unexpected access result")
            .that(icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common", "MyClass",
                        "org.lui.commons"))
            .isEqualTo(AccessResult.DISALLOWED);
        assertWithMessage("Unexpected access result")
            .that(icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common", "MyClass",
                        "org.hibernate.something"))
            .isEqualTo(AccessResult.ALLOWED);
        assertWithMessage("Unexpected access result")
            .that(icCommonRegexpChild.checkAccess("com.kazgroup.courtlink.common", "MyClass",
                        "com.badpackage.something"))
            .isEqualTo(AccessResult.DISALLOWED);
        assertWithMessage("Unexpected access result")
            .that(icRootRegexpChild.checkAccess("com.kazgroup.courtlink", "MyClass",
                        "org.hibernate.something"))
            .isEqualTo(AccessResult.DISALLOWED);
    }

    @Test
    public void testRegExpChildUnknownPkg() {
        assertWithMessage("Unexpected response")
            .that(icRootRegexpChild.locateFinest("net.another", "MyClass"))
            .isNull();
    }

    @Test
    public void testRegExpParentInRootIsConsidered() {
        assertWithMessage("Package should not be null")
            .that(icRootRegexpParent.locateFinest("com", "MyClass"))
            .isNull();
        assertWithMessage("Package should not be null")
            .that(icRootRegexpParent.locateFinest("com/hurz/courtlink", "MyClass"))
            .isNull();
        assertWithMessage("Package should not be null")
            .that(icRootRegexpParent.locateFinest("com.hurz.hurz.courtlink", "MyClass"))
            .isNull();
        assertWithMessage("Invalid package")
            .that(icRootRegexpParent.locateFinest("com.hurz.courtlink.domain", "MyClass"))
            .isEqualTo(icRootRegexpParent);
        assertWithMessage("Invalid package")
            .that(icRootRegexpParent.locateFinest("com.kazgroup.courtlink.domain", "MyClass"))
            .isEqualTo(icRootRegexpParent);
    }

    @Test
    public void testRegExpParentInSubpackageIsConsidered() {
        assertWithMessage("Invalid package")
            .that(icRootRegexpParent
                .locateFinest("com.kazgroup.courtlink.boot.api", "MyClass"))
            .isEqualTo(icBootRegexpParen);
        assertWithMessage("Invalid package")
            .that(icRootRegexpParent
                .locateFinest("com.kazgroup.courtlink.bot.api", "MyClass"))
            .isEqualTo(icBootRegexpParen);
    }

    @Test
    public void testRegExpParentEnsureTrailingDot() {
        assertWithMessage("Invalid package")
            .that(icRootRegexpParent.locateFinest("com.kazgroup.courtlinkkk", "MyClass"))
            .isNull();
        assertWithMessage("Invalid package")
            .that(icRootRegexpParent.locateFinest("com.kazgroup.courtlink/common.api", "MyClass"))
            .isNull();
    }

    @Test
    public void testRegExpParentAlternationInParentIsHandledCorrectly() {
        // the regular expression has to be adjusted to (com\.foo|com\.bar)
        final PkgImportControl root = new PkgImportControl("com\\.foo|com\\.bar", true,
                MismatchStrategy.DISALLOWED);
        final PkgImportControl common = new PkgImportControl(root, "common", false,
                MismatchStrategy.DELEGATE_TO_PARENT);
        root.addChild(common);
        assertWithMessage("Invalid package")
            .that(root.locateFinest("com.foo", "MyClass"))
            .isEqualTo(root);
        assertWithMessage("Invalid package")
            .that(root.locateFinest("com.foo.common", "MyClass"))
            .isEqualTo(common);
        assertWithMessage("Invalid package")
            .that(root.locateFinest("com.bar", "MyClass"))
            .isEqualTo(root);
        assertWithMessage("Invalid package")
            .that(root.locateFinest("com.bar.common", "MyClass"))
            .isEqualTo(common);
    }

    @Test
    public void testRegExpParentAlternationInParentIfUserCaresForIt() {
        // the regular expression has to be adjusted to (com\.foo|com\.bar)
        final PkgImportControl root = new PkgImportControl("(com\\.foo|com\\.bar)", true,
                MismatchStrategy.DISALLOWED);
        final PkgImportControl common = new PkgImportControl(root, "common", false,
                MismatchStrategy.DELEGATE_TO_PARENT);
        root.addChild(common);
        assertWithMessage("Invalid package")
            .that(root.locateFinest("com.foo", "MyClass"))
            .isEqualTo(root);
        assertWithMessage("Invalid package")
            .that(root.locateFinest("com.foo.common", "MyClass"))
            .isEqualTo(common);
        assertWithMessage("Invalid package")
            .that(root.locateFinest("com.bar", "MyClass"))
            .isEqualTo(root);
        assertWithMessage("Invalid package")
            .that(root.locateFinest("com.bar.common", "MyClass"))
            .isEqualTo(common);
    }

    @Test
    public void testRegExpParentAlternationInSubpackageIsHandledCorrectly() {
        final PkgImportControl root = new PkgImportControl("org.somewhere", false,
                MismatchStrategy.DISALLOWED);
        // the regular expression has to be adjusted to (foo|bar)
        final PkgImportControl subpackages = new PkgImportControl(root, "foo|bar", true,
                MismatchStrategy.DELEGATE_TO_PARENT);
        root.addChild(subpackages);
        assertWithMessage("Invalid package")
            .that(root.locateFinest("org.somewhere", "MyClass"))
            .isEqualTo(root);
        assertWithMessage("Invalid package")
            .that(root.locateFinest("org.somewhere.foo", "MyClass"))
            .isEqualTo(subpackages);
        assertWithMessage("Invalid package")
            .that(root.locateFinest("org.somewhere.bar", "MyClass"))
            .isEqualTo(subpackages);
    }

    @Test
    public void testRegExpParentUnknownPkg() {
        assertWithMessage("Package should not be null")
            .that(icRootRegexpParent.locateFinest("net.another", "MyClass"))
            .isNull();
    }

}
