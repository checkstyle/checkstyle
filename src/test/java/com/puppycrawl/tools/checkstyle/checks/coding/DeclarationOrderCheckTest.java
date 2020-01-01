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
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

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
            "8:5: " + getCheckMessage(MSG_ACCESS),
            "13:5: " + getCheckMessage(MSG_ACCESS),
            "18:5: " + getCheckMessage(MSG_ACCESS),
            "21:5: " + getCheckMessage(MSG_ACCESS),
            "27:5: " + getCheckMessage(MSG_STATIC),
            "34:9: " + getCheckMessage(MSG_ACCESS),
            "52:9: " + getCheckMessage(MSG_STATIC),
            "61:5: " + getCheckMessage(MSG_CONSTRUCTOR),
            "87:5: " + getCheckMessage(MSG_INSTANCE),
            "99:9: " + getCheckMessage(MSG_ACCESS),
            "107:9: " + getCheckMessage(MSG_STATIC),
            "113:5: " + getCheckMessage(MSG_ACCESS),
            "118:5: " + getCheckMessage(MSG_ACCESS),
            "123:5: " + getCheckMessage(MSG_ACCESS),
            "126:5: " + getCheckMessage(MSG_ACCESS),
            "132:5: " + getCheckMessage(MSG_STATIC),
            "139:9: " + getCheckMessage(MSG_ACCESS),
            "150:9: " + getCheckMessage(MSG_STATIC),
            "159:5: " + getCheckMessage(MSG_CONSTRUCTOR),
            "185:5: " + getCheckMessage(MSG_INSTANCE),
            "189:9: " + getCheckMessage(MSG_ACCESS),
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
            "52:9: " + getCheckMessage(MSG_STATIC),
            "61:5: " + getCheckMessage(MSG_CONSTRUCTOR),
            "87:5: " + getCheckMessage(MSG_INSTANCE),
            "107:9: " + getCheckMessage(MSG_STATIC),
            "150:9: " + getCheckMessage(MSG_STATIC),
            "159:5: " + getCheckMessage(MSG_CONSTRUCTOR),
            "185:5: " + getCheckMessage(MSG_INSTANCE),
        };
        verify(checkConfig, getPath("InputDeclarationOrder.java"), expected);
    }

    @Test
    public void testOnlyModifiers() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(DeclarationOrderCheck.class);
        checkConfig.addAttribute("ignoreConstructors", "true");
        checkConfig.addAttribute("ignoreModifiers", "false");

        final String[] expected = {
            "8:5: " + getCheckMessage(MSG_ACCESS),
            "13:5: " + getCheckMessage(MSG_ACCESS),
            "18:5: " + getCheckMessage(MSG_ACCESS),
            "21:5: " + getCheckMessage(MSG_ACCESS),
            "27:5: " + getCheckMessage(MSG_STATIC),
            "34:9: " + getCheckMessage(MSG_ACCESS),
            "52:9: " + getCheckMessage(MSG_STATIC),
            "87:5: " + getCheckMessage(MSG_INSTANCE),
            "99:9: " + getCheckMessage(MSG_ACCESS),
            "107:9: " + getCheckMessage(MSG_STATIC),
            "113:5: " + getCheckMessage(MSG_ACCESS),
            "118:5: " + getCheckMessage(MSG_ACCESS),
            "123:5: " + getCheckMessage(MSG_ACCESS),
            "126:5: " + getCheckMessage(MSG_ACCESS),
            "132:5: " + getCheckMessage(MSG_STATIC),
            "139:9: " + getCheckMessage(MSG_ACCESS),
            "150:9: " + getCheckMessage(MSG_STATIC),
            "185:5: " + getCheckMessage(MSG_INSTANCE),
            "189:9: " + getCheckMessage(MSG_ACCESS),
        };
        verify(checkConfig, getPath("InputDeclarationOrder.java"), expected);
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
        final SortedSet<LocalizedMessage> messages1 = check.getMessages();

        assertEquals(0, messages1.size(), "No exception messages expected");

        check.visitToken(ctor);
        final SortedSet<LocalizedMessage> messages2 = check.getMessages();

        assertEquals(0, messages2.size(), "No exception messages expected");
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
        final SortedSet<LocalizedMessage> messages = check.getMessages();

        assertEquals(0, messages.size(), "No exception messages expected");
    }

    @Test
    public void testForwardReference() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(DeclarationOrderCheck.class);
        final String[] expected = {
            "12:5: " + getCheckMessage(MSG_ACCESS),
            "13:5: " + getCheckMessage(MSG_ACCESS),
            "14:5: " + getCheckMessage(MSG_ACCESS),
            "15:5: " + getCheckMessage(MSG_ACCESS),
            "16:5: " + getCheckMessage(MSG_ACCESS),
            "17:5: " + getCheckMessage(MSG_ACCESS),
            "23:5: " + getCheckMessage(MSG_ACCESS),
            "41:5: " + getCheckMessage(MSG_STATIC),
            "61:5: " + getCheckMessage(MSG_ACCESS),
        };
        verify(checkConfig, getPath("InputDeclarationOrderForwardReference.java"), expected);
    }

    @Test
    public void testVariableAccess() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(DeclarationOrderCheck.class);
        final String[] expected = {
            "15:5: " + getCheckMessage(MSG_ACCESS),
        };
        verify(checkConfig, getPath("InputDeclarationOrderVariableAccess.java"), expected);
    }

    @Test
    public void testAvoidDuplicatesForStaticFinalFields() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(DeclarationOrderCheck.class);
        final String[] expected = {
            "6:5: " + getCheckMessage(MSG_STATIC),
        };
        verify(checkConfig,
                getPath("InputDeclarationOrderAvoidDuplicatesInStaticFinalFields.java"),
                expected);
    }

}
