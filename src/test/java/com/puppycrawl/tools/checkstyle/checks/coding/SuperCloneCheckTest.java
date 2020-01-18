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

import static com.puppycrawl.tools.checkstyle.checks.coding.AbstractSuperCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

public class SuperCloneCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/superclone";
    }

    @Test
    public void testIt() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(SuperCloneCheck.class);
        final String[] expected = {
            "27:19: " + getCheckMessage(MSG_KEY, "clone", "super.clone"),
            "35:19: " + getCheckMessage(MSG_KEY, "clone", "super.clone"),
            "60:48: " + getCheckMessage(MSG_KEY, "clone", "super.clone"),
        };
        verify(checkConfig, getPath("InputSuperCloneInnerAndWithArguments.java"), expected);
    }

    @Test
    public void testAnotherInputFile() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(SuperCloneCheck.class);
        final String[] expected = {
            "43:17: " + getCheckMessage(MSG_KEY, "clone", "super.clone"),
            "9:17: " + getCheckMessage(MSG_KEY, "clone", "super.clone"),
        };
        verify(checkConfig, getPath("InputSuperClonePlainAndSubclasses.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        final SuperCloneCheck check = new SuperCloneCheck();
        assertNotNull(check.getAcceptableTokens(), "Acceptable tokens should not be null");
        assertNotNull(check.getDefaultTokens(), "Default tokens should not be null");
        assertNotNull(check.getRequiredTokens(), "Required tokens should not be null");
    }

    /**
     * We cannot reproduce situation when visitToken is called and leaveToken is not.
     * So, we have to use reflection to be sure that even in such situation
     * state of the field will be cleared.
     *
     * @throws Exception when code tested throws exception
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testClearState() throws Exception {
        final AbstractSuperCheck check = new SuperCloneCheck();
        final Optional<DetailAST> methodDef = TestUtil.findTokenInAstByPredicate(
            JavaParser.parseFile(new File(getPath("InputSuperCloneWithoutWarnings.java")),
                JavaParser.Options.WITHOUT_COMMENTS),
            ast -> ast.getType() == TokenTypes.METHOD_DEF);

        assertTrue(methodDef.isPresent(), "Ast should contain METHOD_DEF");
        assertTrue(
            TestUtil.isStatefulFieldClearedDuringBeginTree(check, methodDef.get(), "methodStack",
                methodStack -> ((Collection<Set<String>>) methodStack).isEmpty()),
                "State is not cleared on beginTree");
    }

}
