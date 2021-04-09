////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.puppycrawl.tools.checkstyle.checks.coding.DeclarationOrderCheck.MSG_ACCESS;
import static com.puppycrawl.tools.checkstyle.checks.coding.DeclarationOrderCheck.MSG_CONSTRUCTOR;
import static com.puppycrawl.tools.checkstyle.checks.coding.DeclarationOrderCheck.MSG_INSTANCE;
import static com.puppycrawl.tools.checkstyle.checks.coding.DeclarationOrderCheck.MSG_STATIC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.SortedSet;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
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
        final DefaultConfiguration checkConfig =
            createModuleConfig(DeclarationOrderCheck.class);

        final String[] expected = {
            "11:5: " + getCheckMessage(MSG_ACCESS),
            "16:5: " + getCheckMessage(MSG_ACCESS),
            "21:5: " + getCheckMessage(MSG_ACCESS),
            "24:5: " + getCheckMessage(MSG_ACCESS),
            "30:5: " + getCheckMessage(MSG_STATIC),
            "37:9: " + getCheckMessage(MSG_ACCESS),
            "55:9: " + getCheckMessage(MSG_STATIC),
            "64:5: " + getCheckMessage(MSG_CONSTRUCTOR),
            "90:5: " + getCheckMessage(MSG_INSTANCE),
            "102:9: " + getCheckMessage(MSG_ACCESS),
            "110:9: " + getCheckMessage(MSG_STATIC),
            "116:5: " + getCheckMessage(MSG_ACCESS),
            "121:5: " + getCheckMessage(MSG_ACCESS),
            "126:5: " + getCheckMessage(MSG_ACCESS),
            "129:5: " + getCheckMessage(MSG_ACCESS),
            "135:5: " + getCheckMessage(MSG_STATIC),
            "142:9: " + getCheckMessage(MSG_ACCESS),
            "153:9: " + getCheckMessage(MSG_STATIC),
            "162:5: " + getCheckMessage(MSG_CONSTRUCTOR),
            "188:5: " + getCheckMessage(MSG_INSTANCE),
            "193:9: " + getCheckMessage(MSG_ACCESS),
        };
        verify(checkConfig, getPath("InputDeclarationOrder.java"), expected);
    }

    @Test
    public void testOnlyConstructors() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(DeclarationOrderCheck.class);
        checkConfig.addAttribute("ignoreConstructors", "false");
        checkConfig.addAttribute("ignoreModifiers", "true");

        final String[] expected = {
            "50:9: " + getCheckMessage(MSG_STATIC),
            "59:5: " + getCheckMessage(MSG_CONSTRUCTOR),
            "85:5: " + getCheckMessage(MSG_INSTANCE),
            "104:9: " + getCheckMessage(MSG_STATIC),
            "140:9: " + getCheckMessage(MSG_STATIC),
            "149:5: " + getCheckMessage(MSG_CONSTRUCTOR),
            "175:5: " + getCheckMessage(MSG_INSTANCE),
        };
        verify(checkConfig, getPath("InputDeclarationOrderOnlyConstructors.java"), expected);
    }

    @Test
    public void testOnlyModifiers() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(DeclarationOrderCheck.class);
        checkConfig.addAttribute("ignoreConstructors", "true");
        checkConfig.addAttribute("ignoreModifiers", "false");

        final String[] expected = {
            "13:5: " + getCheckMessage(MSG_ACCESS),
            "18:5: " + getCheckMessage(MSG_ACCESS),
            "23:5: " + getCheckMessage(MSG_ACCESS),
            "26:5: " + getCheckMessage(MSG_ACCESS),
            "32:5: " + getCheckMessage(MSG_STATIC),
            "39:9: " + getCheckMessage(MSG_ACCESS),
            "57:9: " + getCheckMessage(MSG_STATIC),
            "91:5: " + getCheckMessage(MSG_INSTANCE),
            "103:9: " + getCheckMessage(MSG_ACCESS),
            "111:9: " + getCheckMessage(MSG_STATIC),
            "117:5: " + getCheckMessage(MSG_ACCESS),
            "122:5: " + getCheckMessage(MSG_ACCESS),
            "127:5: " + getCheckMessage(MSG_ACCESS),
            "130:5: " + getCheckMessage(MSG_ACCESS),
            "136:5: " + getCheckMessage(MSG_STATIC),
            "143:9: " + getCheckMessage(MSG_ACCESS),
            "154:9: " + getCheckMessage(MSG_STATIC),
            "188:5: " + getCheckMessage(MSG_INSTANCE),
            "193:9: " + getCheckMessage(MSG_ACCESS),
        };
        verify(checkConfig, getPath("InputDeclarationOrderOnlyModifiers.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        final DeclarationOrderCheck check = new DeclarationOrderCheck();
        assertNotNull(check.getAcceptableTokens(), "Acceptable tokens should not be null");
        assertNotNull(check.getDefaultTokens(), "Default tokens should not be null");
        assertNotNull(check.getRequiredTokens(), "Required tokens should not be null");
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

        assertEquals(0, violations1.size(), "No exception violations expected");

        check.visitToken(ctor);
        final SortedSet<Violation> violations2 = check.getViolations();

        assertEquals(0, violations2.size(), "No exception violations expected");
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

        assertEquals(0, violations.size(), "No exception violations expected");
    }

    @Test
    public void testForwardReference() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(DeclarationOrderCheck.class);
        final String[] expected = {
            "15:5: " + getCheckMessage(MSG_ACCESS),
            "16:5: " + getCheckMessage(MSG_ACCESS),
            "17:5: " + getCheckMessage(MSG_ACCESS),
            "18:5: " + getCheckMessage(MSG_ACCESS),
            "19:5: " + getCheckMessage(MSG_ACCESS),
            "20:5: " + getCheckMessage(MSG_ACCESS),
            "26:5: " + getCheckMessage(MSG_ACCESS),
            "44:5: " + getCheckMessage(MSG_STATIC),
            "65:5: " + getCheckMessage(MSG_ACCESS),
        };
        verify(checkConfig, getPath("InputDeclarationOrderForwardReference.java"), expected);
    }

    @Test
    public void testDeclarationOrderRecordsAndCompactCtors() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(DeclarationOrderCheck.class);
        final String[] expected = {
            "17:9: " + getCheckMessage(MSG_CONSTRUCTOR),
            "20:9: " + getCheckMessage(MSG_STATIC),
            "29:9: " + getCheckMessage(MSG_CONSTRUCTOR),
            "32:9: " + getCheckMessage(MSG_STATIC),
            "39:9: " + getCheckMessage(MSG_STATIC),
            };
        verify(checkConfig,
            getNonCompilablePath("InputDeclarationOrderRecordsAndCompactCtors.java"),
            expected);
    }

    @Test
    public void testVariableAccess() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(DeclarationOrderCheck.class);
        final String[] expected = {
            "19:5: " + getCheckMessage(MSG_ACCESS),
        };
        verify(checkConfig, getPath("InputDeclarationOrderVariableAccess.java"), expected);
    }

    @Test
    public void testAvoidDuplicatesForStaticFinalFields() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(DeclarationOrderCheck.class);
        final String[] expected = {
            "9:5: " + getCheckMessage(MSG_STATIC),
        };
        verify(checkConfig,
                getPath("InputDeclarationOrderAvoidDuplicatesInStaticFinalFields.java"),
                expected);
    }

}
