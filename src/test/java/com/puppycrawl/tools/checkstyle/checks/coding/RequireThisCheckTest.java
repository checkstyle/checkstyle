////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck.MSG_METHOD;
import static com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck.MSG_VARIABLE;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import antlr.CommonHiddenStreamToken;
import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class RequireThisCheckTest extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "coding" + File.separator + filename);
    }

    @Test
    public void testIt() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RequireThisCheck.class);
        checkConfig.addAttribute("validateOnlyOverlapping", "false");
        final String[] expected = {
            "11:9: " + getCheckMessage(MSG_VARIABLE, "i", ""),
            "17:9: " + getCheckMessage(MSG_METHOD, "method1", ""),
            "31:9: " + getCheckMessage(MSG_VARIABLE, "i", ""),
            "49:13: " + getCheckMessage(MSG_VARIABLE, "z", ""),
            "56:9: " + getCheckMessage(MSG_VARIABLE, "z", ""),
            "113:9: " + getCheckMessage(MSG_VARIABLE, "i", ""),
            "114:9: " + getCheckMessage(MSG_VARIABLE, "i", ""),
            "115:9: " + getCheckMessage(MSG_METHOD, "instanceMethod", ""),
            "121:13: " + getCheckMessage(MSG_METHOD, "instanceMethod", "Issue2240."),
            "122:13: " + getCheckMessage(MSG_VARIABLE, "i", "Issue2240."),
            "134:9: " + getCheckMessage(MSG_METHOD, "foo", ""),
        };
        verify(checkConfig,
               getPath("InputRequireThis.java"),
               expected);
    }

    @Test
    public void testMethodsOnly() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RequireThisCheck.class);
        checkConfig.addAttribute("checkFields", "false");
        checkConfig.addAttribute("validateOnlyOverlapping", "false");
        final String[] expected = {
            "17:9: " + getCheckMessage(MSG_METHOD, "method1", ""),
            "115:9: " + getCheckMessage(MSG_METHOD, "instanceMethod", ""),
            "121:13: " + getCheckMessage(MSG_METHOD, "instanceMethod", "Issue2240."),
            "134:9: " + getCheckMessage(MSG_METHOD, "foo", ""),
        };
        verify(checkConfig,
               getPath("InputRequireThis.java"),
               expected);
    }

    @Test
    public void testFieldsOnly() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RequireThisCheck.class);
        checkConfig.addAttribute("checkMethods", "false");
        checkConfig.addAttribute("validateOnlyOverlapping", "false");
        final String[] expected = {
            "11:9: " + getCheckMessage(MSG_VARIABLE, "i", ""),
            "31:9: " + getCheckMessage(MSG_VARIABLE, "i", ""),
            "49:13: " + getCheckMessage(MSG_VARIABLE, "z", ""),
            "56:9: " + getCheckMessage(MSG_VARIABLE, "z", ""),
            "113:9: " + getCheckMessage(MSG_VARIABLE, "i", ""),
            "114:9: " + getCheckMessage(MSG_VARIABLE, "i", ""),
            "122:13: " + getCheckMessage(MSG_VARIABLE, "i", "Issue2240."),
        };
        verify(checkConfig,
               getPath("InputRequireThis.java"),
               expected);
    }

    @Test
    public void testGenerics() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RequireThisCheck.class);
        checkConfig.addAttribute("validateOnlyOverlapping", "false");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("Input15Extensions.java"), expected);
    }

    @Test
    public void testGithubIssue41() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(RequireThisCheck.class);
        checkConfig.addAttribute("validateOnlyOverlapping", "false");
        final String[] expected = {
            "7:19: " + getCheckMessage(MSG_VARIABLE, "number", ""),
            "8:16: " + getCheckMessage(MSG_METHOD, "other", ""),
        };
        verify(checkConfig,
                getPath("InputRequireThis2.java"),
                expected);
    }

    @Test
    public void testTokensNotNull() {
        final RequireThisCheck check = new RequireThisCheck();
        Assert.assertNotNull(check.getAcceptableTokens());
        Assert.assertNotNull(check.getDefaultTokens());
        Assert.assertNotNull(check.getRequiredTokens());
    }

    @Test
    public void testWithAnonymousClass() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(RequireThisCheck.class);
        checkConfig.addAttribute("validateOnlyOverlapping", "false");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig,
                getPath("InputRequireThis3.java"),
                expected);
    }

    @Test
    public void testDefaultSwitch() {
        final RequireThisCheck check = new RequireThisCheck();
        final DetailAST ast = new DetailAST();
        ast.initialize(new CommonHiddenStreamToken(TokenTypes.ENUM, "ENUM"));
        check.visitToken(ast);
    }

    @Test
    public void testValidateOnlyOverlappingFalse() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(RequireThisCheck.class);
        checkConfig.addAttribute("validateOnlyOverlapping", "false");
        final String[] expected = {
            "20:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "21:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal1", ""),
            "22:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal2", ""),
            "23:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal3", ""),
            "27:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal1", ""),
            "28:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal2", ""),
            "29:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal3", ""),
            "33:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal1", ""),
            "37:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal3", ""),
            "41:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal1", ""),
            "43:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "45:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal3", ""),
            "49:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal2", ""),
            "50:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal3", ""),
            "60:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal1", ""),
            "61:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal2", ""),
            "80:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "119:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "128:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "132:9: " + getCheckMessage(MSG_METHOD, "method1", ""),
            "168:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal1", ""),
            "169:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal2", ""),
            "170:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal3", ""),
            "172:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "176:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal1", ""),
            "177:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal2", ""),
            "178:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal3", ""),
            "180:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "185:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "189:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "210:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "215:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "225:21: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "228:21: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "238:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "253:9: " + getCheckMessage(MSG_VARIABLE, "booleanField", ""),
            "262:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "270:18: " + getCheckMessage(MSG_METHOD, "addSuffixToField", ""),
            "275:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "275:18: " + getCheckMessage(MSG_METHOD, "addSuffixToField", ""),
            "301:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "340:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "374:40: " + getCheckMessage(MSG_METHOD, "getServletRelativeAction", ""),
            "376:20: " + getCheckMessage(MSG_METHOD, "processAction", ""),
            "383:9: " + getCheckMessage(MSG_VARIABLE, "servletRelativeAction", ""),
            "384:16: " + getCheckMessage(MSG_METHOD, "processAction", ""),
        };
        verify(checkConfig, getPath("InputValidateOnlyOverlappingFalse.java"), expected);
    }

    @Test
    public void testValidateOnlyOverlappingTrue() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(RequireThisCheck.class);
        final String[] expected = {
            "20:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "43:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "80:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "119:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "172:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "180:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "238:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "253:9: " + getCheckMessage(MSG_VARIABLE, "booleanField", ""),
            "262:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "275:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "301:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "339:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
        };
        verify(checkConfig, getPath("InputValidateOnlyOverlappingTrue.java"), expected);
    }

    @Test
    public void testReceiverParameter() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(RequireThisCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRequireThisReceiver.java"), expected);
    }

    @Test
    public void testBraceAlone() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(RequireThisCheck.class);
        checkConfig.addAttribute("validateOnlyOverlapping", "false");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRequireThisBraceAlone.java"), expected);
    }

    @Test
    public void testStatic() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(RequireThisCheck.class);
        checkConfig.addAttribute("validateOnlyOverlapping", "false");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRequireThisStatic.java"), expected);
    }
}
