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

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import org.junit.jupiter.api.Test;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class GenericXpathCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/genericxpath";
    }

    @Test
    public void testCheckWithMatchingMethodNames()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(GenericXpathCheck.class);
        final String xpath = "//METHOD_DEF[./IDENT[@text='test' or @text='foo']]";
        final String msgContent = "No methods all allowed";
        checkConfig.addAttribute("query", xpath);
        checkConfig.addMessage("xpath.finding", msgContent);
        final String[] expected = {
            "4:5: " + msgContent,
            "8:5: " + msgContent,
        };
        verify(checkConfig, getPath("InputGenericXpath.java"), expected);
    }

    @Test
    public void testCheckWithNoMatchingMethodName()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(GenericXpathCheck.class);
        final String xpath = "//METHOD_DEF[./IDENT[@text='wrongName' or @text='nonExistingMethod']]";
        final String msgContent = "No methods all allowed";
        checkConfig.addAttribute("query", xpath);
        checkConfig.addMessage("xpath.finding", msgContent);
        final String[] expected = {};
        verify(checkConfig, getPath("InputGenericXpath.java"), expected);
    }

    @Test
    public void testCheckWithDoubleBraceInitialization()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(GenericXpathCheck.class);
        final String msgContent = "Do not use double-brace initialization";
        final String xpath = "//INSTANCE_INIT[not(../*[not(\n"
                + "                    self::LCURLY or\n"
                + "                    self::INSTANCE_INIT or\n"
                + "                    self::RCURLY or\n"
                + "                    self::SINGLE_LINE_COMMENT or\n"
                + "                    self::BLOCK_COMMENT_BEGIN\n"
                + "                )])]";
        checkConfig.addAttribute("query", xpath);
        checkConfig.addMessage("xpath.finding", msgContent);
        final String[] expected = {
                "7:35: " + msgContent,
        };
        verify(checkConfig, getPath("InputGenericXpathDoubleBrace.java"), expected);
    }

    @Test
    public void testNonValidXpathQuery() {
        final DefaultConfiguration checkConfig =
                createModuleConfig(GenericXpathCheck.class);
        final String xpath = ")(WRONG_XPATH/+/";
        final String msgContent = "No methods all allowed";
        checkConfig.addAttribute("query", xpath);
        checkConfig.addMessage("xpath.finding", msgContent);
        try {
            final String[] expected = {};
            verify(checkConfig, getPath("InputGenericXpath.java"), expected);
            fail("Exception expected");
        } catch (Exception ex) {
            assertThat("Result nodes differ from expected", ex,
                    instanceOf(CheckstyleException.class));
        }
    }

    @Test
    public void testImitateIllegalThrowsCheck()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(GenericXpathCheck.class);
        final String msgContent = "Illegal throws statement";
        final String xpath = "//LITERAL_THROWS[./IDENT["
                + "@text='Throwable' or @text='RuntimeException' or ends-with(@text, 'Error')]]";
        checkConfig.addAttribute("query", xpath);
        checkConfig.addMessage("xpath.finding", msgContent);
        final String[] expected = {
                "4:25: " + msgContent,
                "6:25: " + msgContent,
                "7:25: " + msgContent,
        };
        verify(checkConfig, getPath("InputGenericXpathIllegalThrows.java"), expected);
    }
}
