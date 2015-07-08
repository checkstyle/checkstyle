////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.coding.HiddenFieldCheck.MSG_KEY;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class HiddenFieldCheckTest
    extends BaseCheckTestSupport {
    @Test
    public void testNoParameters()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HiddenFieldCheck.class);
        checkConfig.addAttribute("tokens", "VARIABLE_DEF");
        final String[] expected = {
            "18:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "27:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "32:18: " + getCheckMessage(MSG_KEY, "hidden"),
            "46:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "55:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "56:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "61:22: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "64:22: " + getCheckMessage(MSG_KEY, "hidden"),
            "76:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "77:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "82:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "138:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "143:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "148:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "152:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "200:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "217:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "223:13: " + getCheckMessage(MSG_KEY, "hiddenStatic"),
        };
        verify(checkConfig, getPath("InputHiddenField.java"), expected);
    }

    @Test
    public void testDefault()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HiddenFieldCheck.class);
        final String[] expected = {
            "18:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "21:33: " + getCheckMessage(MSG_KEY, "hidden"),
            "27:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "32:18: " + getCheckMessage(MSG_KEY, "hidden"),
            "36:33: " + getCheckMessage(MSG_KEY, "hidden"),
            "46:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "49:26: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "55:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "56:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "61:22: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "64:22: " + getCheckMessage(MSG_KEY, "hidden"),
            "69:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "70:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "76:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "77:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "82:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "100:29: " + getCheckMessage(MSG_KEY, "prop"),
            "106:29: " + getCheckMessage(MSG_KEY, "prop"),
            "112:29: " + getCheckMessage(MSG_KEY, "prop"),
            "124:28: " + getCheckMessage(MSG_KEY, "prop"),
            "138:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "143:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "148:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "152:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "179:23: " + getCheckMessage(MSG_KEY, "y"),
            "200:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "210:20: " + getCheckMessage(MSG_KEY, "hidden"),
            "217:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "223:13: " + getCheckMessage(MSG_KEY, "hiddenStatic"),
            "230:41: " + getCheckMessage(MSG_KEY, "x"),
            "236:30: " + getCheckMessage(MSG_KEY, "xAxis"),
            "253:40: " + getCheckMessage(MSG_KEY, "prop"),
            "267:29: " + getCheckMessage(MSG_KEY, "prop"),
            "278:41: " + getCheckMessage(MSG_KEY, "prop2"),
            "290:19: " + getCheckMessage(MSG_KEY, "i"),
        };
        verify(checkConfig, getPath("InputHiddenField.java"), expected);
    }

    /** tests ignoreFormat property */
    @Test
    public void testIgnoreFormat()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HiddenFieldCheck.class);
        checkConfig.addAttribute("ignoreFormat", "^i.*$");
        Assert.assertNotNull(checkConfig.getAttribute("ignoreFormat"));
        final String[] expected = {
            "18:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "21:33: " + getCheckMessage(MSG_KEY, "hidden"),
            "27:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "32:18: " + getCheckMessage(MSG_KEY, "hidden"),
            "36:33: " + getCheckMessage(MSG_KEY, "hidden"),
            "56:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "64:22: " + getCheckMessage(MSG_KEY, "hidden"),
            "70:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "77:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "82:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "100:29: " + getCheckMessage(MSG_KEY, "prop"),
            "106:29: " + getCheckMessage(MSG_KEY, "prop"),
            "112:29: " + getCheckMessage(MSG_KEY, "prop"),
            "124:28: " + getCheckMessage(MSG_KEY, "prop"),
            "138:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "143:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "148:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "152:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "179:23: " + getCheckMessage(MSG_KEY, "y"),
            "200:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "210:20: " + getCheckMessage(MSG_KEY, "hidden"),
            "217:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "223:13: " + getCheckMessage(MSG_KEY, "hiddenStatic"),
            "230:41: " + getCheckMessage(MSG_KEY, "x"),
            "236:30: " + getCheckMessage(MSG_KEY, "xAxis"),
            "253:40: " + getCheckMessage(MSG_KEY, "prop"),
            "267:29: " + getCheckMessage(MSG_KEY, "prop"),
            "278:41: " + getCheckMessage(MSG_KEY, "prop2"),
        };
        verify(checkConfig, getPath("InputHiddenField.java"), expected);
    }

    /** tests ignoreSetter property */
    @Test
    public void testIgnoreSetter()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HiddenFieldCheck.class);
        checkConfig.addAttribute("ignoreSetter", "true");
        final String[] expected = {
            "18:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "21:33: " + getCheckMessage(MSG_KEY, "hidden"),
            "27:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "32:18: " + getCheckMessage(MSG_KEY, "hidden"),
            "36:33: " + getCheckMessage(MSG_KEY, "hidden"),
            "46:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "49:26: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "55:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "56:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "61:22: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "64:22: " + getCheckMessage(MSG_KEY, "hidden"),
            "69:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "70:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "76:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "77:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "82:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "106:29: " + getCheckMessage(MSG_KEY, "prop"),
            "112:29: " + getCheckMessage(MSG_KEY, "prop"),
            "124:28: " + getCheckMessage(MSG_KEY, "prop"),
            "138:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "143:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "148:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "152:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "179:23: " + getCheckMessage(MSG_KEY, "y"),
            "200:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "210:20: " + getCheckMessage(MSG_KEY, "hidden"),
            "217:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "223:13: " + getCheckMessage(MSG_KEY, "hiddenStatic"),
            "230:41: " + getCheckMessage(MSG_KEY, "x"),
            "253:40: " + getCheckMessage(MSG_KEY, "prop"),
            "278:41: " + getCheckMessage(MSG_KEY, "prop2"),
        };
        verify(checkConfig, getPath("InputHiddenField.java"), expected);
    }

    /** tests ignoreSetter and setterCanReturnItsClass properties */
    @Test
    public void testIgnoreChainSetter()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HiddenFieldCheck.class);
        checkConfig.addAttribute("ignoreSetter", "true");
        checkConfig.addAttribute("setterCanReturnItsClass", "true");
        final String[] expected = {
            "18:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "21:33: " + getCheckMessage(MSG_KEY, "hidden"),
            "27:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "32:18: " + getCheckMessage(MSG_KEY, "hidden"),
            "36:33: " + getCheckMessage(MSG_KEY, "hidden"),
            "46:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "49:26: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "55:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "56:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "61:22: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "64:22: " + getCheckMessage(MSG_KEY, "hidden"),
            "69:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "70:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "76:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "77:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "82:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "106:29: " + getCheckMessage(MSG_KEY, "prop"),
            "112:29: " + getCheckMessage(MSG_KEY, "prop"),
            "124:28: " + getCheckMessage(MSG_KEY, "prop"),
            "138:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "143:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "148:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "152:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "179:23: " + getCheckMessage(MSG_KEY, "y"),
            "200:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "210:20: " + getCheckMessage(MSG_KEY, "hidden"),
            "217:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "223:13: " + getCheckMessage(MSG_KEY, "hiddenStatic"),
            "230:41: " + getCheckMessage(MSG_KEY, "x"),
        };
        verify(checkConfig, getPath("InputHiddenField.java"), expected);
    }

    /** tests ignoreConstructorParameter property */
    @Test
    public void testIgnoreConstructorParameter()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HiddenFieldCheck.class);
        checkConfig.addAttribute("ignoreConstructorParameter", "true");
        final String[] expected = {
            "18:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "27:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "32:18: " + getCheckMessage(MSG_KEY, "hidden"),
            "36:33: " + getCheckMessage(MSG_KEY, "hidden"),
            "46:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "55:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "56:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "61:22: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "64:22: " + getCheckMessage(MSG_KEY, "hidden"),
            "69:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "70:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "76:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "77:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "82:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "100:29: " + getCheckMessage(MSG_KEY, "prop"),
            "106:29: " + getCheckMessage(MSG_KEY, "prop"),
            "112:29: " + getCheckMessage(MSG_KEY, "prop"),
            "124:28: " + getCheckMessage(MSG_KEY, "prop"),
            "138:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "143:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "148:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "152:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "179:23: " + getCheckMessage(MSG_KEY, "y"),
            "200:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "217:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "223:13: " + getCheckMessage(MSG_KEY, "hiddenStatic"),
            "230:41: " + getCheckMessage(MSG_KEY, "x"),
            "236:30: " + getCheckMessage(MSG_KEY, "xAxis"),
            "253:40: " + getCheckMessage(MSG_KEY, "prop"),
            "267:29: " + getCheckMessage(MSG_KEY, "prop"),
            "278:41: " + getCheckMessage(MSG_KEY, "prop2"),
            "290:19: " + getCheckMessage(MSG_KEY, "i"),
        };
        verify(checkConfig, getPath("InputHiddenField.java"), expected);
    }

    /** Test against a class with field declarations in different order */
    @Test
    public void testReordered()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HiddenFieldCheck.class);
        final String[] expected = {
            "18:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "21:40: " + getCheckMessage(MSG_KEY, "hidden"),
            "27:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "32:18: " + getCheckMessage(MSG_KEY, "hidden"),
            "36:33: " + getCheckMessage(MSG_KEY, "hidden"),
            "46:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "49:26: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "55:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "56:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "61:22: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "64:22: " + getCheckMessage(MSG_KEY, "hidden"),
            "69:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "70:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "76:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "77:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "83:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "105:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "118:21: " + getCheckMessage(MSG_KEY, "hidden"),
            "125:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "131:13: " + getCheckMessage(MSG_KEY, "hiddenStatic"),
        };
        verify(checkConfig, getPath("InputHiddenFieldReorder.java"), expected);
    }

    @Test
    public void testIgnoreAbstractMethods() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HiddenFieldCheck.class);
        checkConfig.addAttribute("ignoreAbstractMethods", "true");

        final String[] expected = {
            "18:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "21:33: " + getCheckMessage(MSG_KEY, "hidden"),
            "27:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "32:18: " + getCheckMessage(MSG_KEY, "hidden"),
            "36:33: " + getCheckMessage(MSG_KEY, "hidden"),
            "46:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "49:26: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "55:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "56:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "61:22: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "64:22: " + getCheckMessage(MSG_KEY, "hidden"),
            "69:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "70:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "76:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "77:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "82:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "100:29: " + getCheckMessage(MSG_KEY, "prop"),
            "106:29: " + getCheckMessage(MSG_KEY, "prop"),
            "112:29: " + getCheckMessage(MSG_KEY, "prop"),
            "124:28: " + getCheckMessage(MSG_KEY, "prop"),
            "138:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "143:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "148:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "152:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "179:23: " + getCheckMessage(MSG_KEY, "y"),
            "200:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "210:20: " + getCheckMessage(MSG_KEY, "hidden"),
            "217:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "223:13: " + getCheckMessage(MSG_KEY, "hiddenStatic"),
            "236:30: " + getCheckMessage(MSG_KEY, "xAxis"),
            "253:40: " + getCheckMessage(MSG_KEY, "prop"),
            "267:29: " + getCheckMessage(MSG_KEY, "prop"),
            "278:41: " + getCheckMessage(MSG_KEY, "prop2"),
            "290:19: " + getCheckMessage(MSG_KEY, "i"),
        };
        verify(checkConfig, getPath("InputHiddenField.java"), expected);
    }

}
