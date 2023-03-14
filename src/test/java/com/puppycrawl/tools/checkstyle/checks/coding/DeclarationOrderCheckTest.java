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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.DeclarationOrderCheck.MSG_ACCESS;
import static com.puppycrawl.tools.checkstyle.checks.coding.DeclarationOrderCheck.MSG_CONSTRUCTOR;
import static com.puppycrawl.tools.checkstyle.checks.coding.DeclarationOrderCheck.MSG_INSTANCE;
import static com.puppycrawl.tools.checkstyle.checks.coding.DeclarationOrderCheck.MSG_STATIC;

import java.util.SortedSet;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Violation;

public class DeclarationOrderCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/declarationorder";
    }

    @Test
    public void testDefault() throws Exception {

        final String[] expected = {
            "16:5: " + getCheckMessage(MSG_ACCESS),
            "21:5: " + getCheckMessage(MSG_ACCESS),
            "26:5: " + getCheckMessage(MSG_ACCESS),
            "29:5: " + getCheckMessage(MSG_ACCESS),
            "35:5: " + getCheckMessage(MSG_STATIC),
            "42:9: " + getCheckMessage(MSG_ACCESS),
            "60:9: " + getCheckMessage(MSG_STATIC),
            "69:5: " + getCheckMessage(MSG_CONSTRUCTOR),
            "95:5: " + getCheckMessage(MSG_INSTANCE),
            "107:9: " + getCheckMessage(MSG_ACCESS),
            "115:9: " + getCheckMessage(MSG_STATIC),
            "121:5: " + getCheckMessage(MSG_ACCESS),
            "126:5: " + getCheckMessage(MSG_ACCESS),
            "131:5: " + getCheckMessage(MSG_ACCESS),
            "134:5: " + getCheckMessage(MSG_ACCESS),
            "140:5: " + getCheckMessage(MSG_STATIC),
            "147:9: " + getCheckMessage(MSG_ACCESS),
            "158:9: " + getCheckMessage(MSG_STATIC),
            "167:5: " + getCheckMessage(MSG_CONSTRUCTOR),
            "193:5: " + getCheckMessage(MSG_INSTANCE),
            "198:9: " + getCheckMessage(MSG_ACCESS),
        };
        verifyWithInlineConfigParser(
                getPath("InputDeclarationOrder.java"), expected);
    }

    @Test
    public void testOnlyConstructors() throws Exception {

        final String[] expected = {
            "53:9: " + getCheckMessage(MSG_STATIC),
            "62:5: " + getCheckMessage(MSG_CONSTRUCTOR),
            "88:5: " + getCheckMessage(MSG_INSTANCE),
            "107:9: " + getCheckMessage(MSG_STATIC),
            "143:9: " + getCheckMessage(MSG_STATIC),
            "152:5: " + getCheckMessage(MSG_CONSTRUCTOR),
            "178:5: " + getCheckMessage(MSG_INSTANCE),
        };
        verifyWithInlineConfigParser(
                getPath("InputDeclarationOrderOnlyConstructors.java"), expected);
    }

    @Test
    public void testOnlyModifiers() throws Exception {

        final String[] expected = {
            "16:5: " + getCheckMessage(MSG_ACCESS),
            "21:5: " + getCheckMessage(MSG_ACCESS),
            "26:5: " + getCheckMessage(MSG_ACCESS),
            "29:5: " + getCheckMessage(MSG_ACCESS),
            "35:5: " + getCheckMessage(MSG_STATIC),
            "42:9: " + getCheckMessage(MSG_ACCESS),
            "60:9: " + getCheckMessage(MSG_STATIC),
            "94:5: " + getCheckMessage(MSG_INSTANCE),
            "106:9: " + getCheckMessage(MSG_ACCESS),
            "114:9: " + getCheckMessage(MSG_STATIC),
            "120:5: " + getCheckMessage(MSG_ACCESS),
            "125:5: " + getCheckMessage(MSG_ACCESS),
            "130:5: " + getCheckMessage(MSG_ACCESS),
            "133:5: " + getCheckMessage(MSG_ACCESS),
            "139:5: " + getCheckMessage(MSG_STATIC),
            "146:9: " + getCheckMessage(MSG_ACCESS),
            "157:9: " + getCheckMessage(MSG_STATIC),
            "191:5: " + getCheckMessage(MSG_INSTANCE),
            "196:9: " + getCheckMessage(MSG_ACCESS),
        };
        verifyWithInlineConfigParser(
                getPath("InputDeclarationOrderOnlyModifiers.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        final DeclarationOrderCheck check = new DeclarationOrderCheck();
        assertWithMessage("Acceptable tokens should not be null")
            .that(check.getAcceptableTokens())
            .isNotNull();
        assertWithMessage("Default tokens should not be null")
            .that(check.getDefaultTokens())
            .isNotNull();
        assertWithMessage("Required tokens should not be null")
            .that(check.getRequiredTokens())
            .isNotNull();
    }

    @Test
    public void testParents() {
        final DetailAstImpl parent = new DetailAstImpl();
        parent.setType(TokenTypes.STATIC_INIT);
        final DetailAstImpl method = new DetailAstImpl();
        method.setType(TokenTypes.METHOD_DEF);
        parent.setFirstChild(method);
        final DetailAstImpl ctor = new DetailAstImpl();
        ctor.setType(TokenTypes.CTOR_DEF);
        method.setNextSibling(ctor);

        final DeclarationOrderCheck check = new DeclarationOrderCheck();

        check.visitToken(method);
        final SortedSet<Violation> violations1 = check.getViolations();

        assertWithMessage("No exception violations expected")
            .that(violations1)
            .isEmpty();

        check.visitToken(ctor);
        final SortedSet<Violation> violations2 = check.getViolations();

        assertWithMessage("No exception violations expected")
            .that(violations2)
            .isEmpty();
    }

    @Test
    public void testImproperToken() {
        final DetailAstImpl parent = new DetailAstImpl();
        parent.setType(TokenTypes.STATIC_INIT);
        final DetailAstImpl array = new DetailAstImpl();
        array.setType(TokenTypes.ARRAY_INIT);
        parent.setFirstChild(array);

        final DeclarationOrderCheck check = new DeclarationOrderCheck();

        check.visitToken(array);
        final SortedSet<Violation> violations = check.getViolations();

        assertWithMessage("No exception violations expected")
            .that(violations)
            .isEmpty();
    }

    @Test
    public void testForwardReference() throws Exception {
        final String[] expected = {
            "20:5: " + getCheckMessage(MSG_ACCESS),
            "21:5: " + getCheckMessage(MSG_ACCESS),
            "22:5: " + getCheckMessage(MSG_ACCESS),
            "23:5: " + getCheckMessage(MSG_ACCESS),
            "24:5: " + getCheckMessage(MSG_ACCESS),
            "25:5: " + getCheckMessage(MSG_ACCESS),
            "31:5: " + getCheckMessage(MSG_ACCESS),
            "49:5: " + getCheckMessage(MSG_STATIC),
            "69:5: " + getCheckMessage(MSG_ACCESS),
        };
        verifyWithInlineConfigParser(
                getPath("InputDeclarationOrderForwardReference.java"), expected);
    }

    @Test
    public void testDeclarationOrderRecordsAndCompactCtors() throws Exception {
        final String[] expected = {
            "21:9: " + getCheckMessage(MSG_CONSTRUCTOR),
            "24:9: " + getCheckMessage(MSG_STATIC),
            "33:9: " + getCheckMessage(MSG_CONSTRUCTOR),
            "36:9: " + getCheckMessage(MSG_STATIC),
            "43:9: " + getCheckMessage(MSG_STATIC),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputDeclarationOrderRecordsAndCompactCtors.java"),
            expected);
    }

    @Test
    public void testDeclarationOrderInterfaceMemberScopeIsPublic() throws Exception {
        final String[] expected = {
            "21:3: " + getCheckMessage(MSG_STATIC),
        };
        verifyWithInlineConfigParser(
                getPath("InputDeclarationOrderInterfaceMemberScopeIsPublic.java"),
            expected);
    }

    @Test
    public void testVariableAccess() throws Exception {
        final String[] expected = {
            "23:5: " + getCheckMessage(MSG_ACCESS),
        };
        verifyWithInlineConfigParser(
                getPath("InputDeclarationOrderVariableAccess.java"), expected);
    }

    @Test
    public void testAvoidDuplicatesForStaticFinalFields() throws Exception {
        final String[] expected = {
            "14:5: " + getCheckMessage(MSG_STATIC),
        };
        verifyWithInlineConfigParser(
                getPath("InputDeclarationOrderAvoidDuplicatesInStaticFinalFields.java"),
                expected);
    }

}
