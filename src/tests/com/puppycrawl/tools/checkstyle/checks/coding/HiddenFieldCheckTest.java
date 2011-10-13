////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class HiddenFieldCheckTest
    extends BaseCheckTestSupport
{
    @Test
    public void testNoParameters()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HiddenFieldCheck.class);
        checkConfig.addAttribute("tokens", "VARIABLE_DEF");
        final String[] expected = {
            "18:13: 'hidden' hides a field.",
            "27:13: 'hidden' hides a field.",
            "32:18: 'hidden' hides a field.",
            "46:17: 'innerHidden' hides a field.",
            "55:17: 'innerHidden' hides a field.",
            "56:17: 'hidden' hides a field.",
            "61:22: 'innerHidden' hides a field.",
            "64:22: 'hidden' hides a field.",
            "76:17: 'innerHidden' hides a field.",
            "77:17: 'hidden' hides a field.",
            "82:13: 'hidden' hides a field.",
            "138:13: 'hidden' hides a field.",
            "143:13: 'hidden' hides a field.",
            "148:13: 'hidden' hides a field.",
            "152:13: 'hidden' hides a field.",
            "200:17: 'hidden' hides a field.",
            "217:13: 'hidden' hides a field.",
            "223:13: 'hiddenStatic' hides a field.",
        };
        verify(checkConfig, getPath("InputHiddenField.java"), expected);
    }

    @Test
    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HiddenFieldCheck.class);
        final String[] expected = {
            "18:13: 'hidden' hides a field.",
            "21:33: 'hidden' hides a field.",
            "27:13: 'hidden' hides a field.",
            "32:18: 'hidden' hides a field.",
            "36:33: 'hidden' hides a field.",
            "46:17: 'innerHidden' hides a field.",
            "49:26: 'innerHidden' hides a field.",
            "55:17: 'innerHidden' hides a field.",
            "56:17: 'hidden' hides a field.",
            "61:22: 'innerHidden' hides a field.",
            "64:22: 'hidden' hides a field.",
            "69:17: 'innerHidden' hides a field.",
            "70:17: 'hidden' hides a field.",
            "76:17: 'innerHidden' hides a field.",
            "77:17: 'hidden' hides a field.",
            "82:13: 'hidden' hides a field.",
            "100:29: 'prop' hides a field.",
            "106:29: 'prop' hides a field.",
            "112:29: 'prop' hides a field.",
            "124:28: 'prop' hides a field.",
            "138:13: 'hidden' hides a field.",
            "143:13: 'hidden' hides a field.",
            "148:13: 'hidden' hides a field.",
            "152:13: 'hidden' hides a field.",
            "179:23: 'y' hides a field.",
            "200:17: 'hidden' hides a field.",
            "210:20: 'hidden' hides a field.",
            "217:13: 'hidden' hides a field.",
            "223:13: 'hiddenStatic' hides a field.",
            "230:41: 'x' hides a field.",
            "236:30: 'xAxis' hides a field.",
        };
        verify(checkConfig, getPath("InputHiddenField.java"), expected);
    }

    /** tests ignoreFormat property */
    @Test
    public void testIgnoreFormat()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HiddenFieldCheck.class);
        checkConfig.addAttribute("ignoreFormat", "^i.*$");
        final String[] expected = {
            "18:13: 'hidden' hides a field.",
            "21:33: 'hidden' hides a field.",
            "27:13: 'hidden' hides a field.",
            "32:18: 'hidden' hides a field.",
            "36:33: 'hidden' hides a field.",
            "56:17: 'hidden' hides a field.",
            "64:22: 'hidden' hides a field.",
            "70:17: 'hidden' hides a field.",
            "77:17: 'hidden' hides a field.",
            "82:13: 'hidden' hides a field.",
            "100:29: 'prop' hides a field.",
            "106:29: 'prop' hides a field.",
            "112:29: 'prop' hides a field.",
            "124:28: 'prop' hides a field.",
            "138:13: 'hidden' hides a field.",
            "143:13: 'hidden' hides a field.",
            "148:13: 'hidden' hides a field.",
            "152:13: 'hidden' hides a field.",
            "179:23: 'y' hides a field.",
            "200:17: 'hidden' hides a field.",
            "210:20: 'hidden' hides a field.",
            "217:13: 'hidden' hides a field.",
            "223:13: 'hiddenStatic' hides a field.",
            "230:41: 'x' hides a field.",
            "236:30: 'xAxis' hides a field.",
        };
        verify(checkConfig, getPath("InputHiddenField.java"), expected);
    }

    /** tests ignoreSetter property */
    @Test
    public void testIgnoreSetter()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HiddenFieldCheck.class);
        checkConfig.addAttribute("ignoreSetter", "true");
        final String[] expected = {
            "18:13: 'hidden' hides a field.",
            "21:33: 'hidden' hides a field.",
            "27:13: 'hidden' hides a field.",
            "32:18: 'hidden' hides a field.",
            "36:33: 'hidden' hides a field.",
            "46:17: 'innerHidden' hides a field.",
            "49:26: 'innerHidden' hides a field.",
            "55:17: 'innerHidden' hides a field.",
            "56:17: 'hidden' hides a field.",
            "61:22: 'innerHidden' hides a field.",
            "64:22: 'hidden' hides a field.",
            "69:17: 'innerHidden' hides a field.",
            "70:17: 'hidden' hides a field.",
            "76:17: 'innerHidden' hides a field.",
            "77:17: 'hidden' hides a field.",
            "82:13: 'hidden' hides a field.",
            "106:29: 'prop' hides a field.",
            "112:29: 'prop' hides a field.",
            "124:28: 'prop' hides a field.",
            "138:13: 'hidden' hides a field.",
            "143:13: 'hidden' hides a field.",
            "148:13: 'hidden' hides a field.",
            "152:13: 'hidden' hides a field.",
            "179:23: 'y' hides a field.",
            "200:17: 'hidden' hides a field.",
            "210:20: 'hidden' hides a field.",
            "217:13: 'hidden' hides a field.",
            "223:13: 'hiddenStatic' hides a field.",
            "230:41: 'x' hides a field.",
        };
        verify(checkConfig, getPath("InputHiddenField.java"), expected);
    }

    /** tests ignoreConstructorParameter property */
    @Test
    public void testIgnoreConstructorParameter()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HiddenFieldCheck.class);
        checkConfig.addAttribute("ignoreConstructorParameter", "true");
        final String[] expected = {
            "18:13: 'hidden' hides a field.",
            "27:13: 'hidden' hides a field.",
            "32:18: 'hidden' hides a field.",
            "36:33: 'hidden' hides a field.",
            "46:17: 'innerHidden' hides a field.",
            "55:17: 'innerHidden' hides a field.",
            "56:17: 'hidden' hides a field.",
            "61:22: 'innerHidden' hides a field.",
            "64:22: 'hidden' hides a field.",
            "69:17: 'innerHidden' hides a field.",
            "70:17: 'hidden' hides a field.",
            "76:17: 'innerHidden' hides a field.",
            "77:17: 'hidden' hides a field.",
            "82:13: 'hidden' hides a field.",
            "100:29: 'prop' hides a field.",
            "106:29: 'prop' hides a field.",
            "112:29: 'prop' hides a field.",
            "124:28: 'prop' hides a field.",
            "138:13: 'hidden' hides a field.",
            "143:13: 'hidden' hides a field.",
            "148:13: 'hidden' hides a field.",
            "152:13: 'hidden' hides a field.",
            "179:23: 'y' hides a field.",
            "200:17: 'hidden' hides a field.",
            "217:13: 'hidden' hides a field.",
            "223:13: 'hiddenStatic' hides a field.",
            "230:41: 'x' hides a field.",
            "236:30: 'xAxis' hides a field.",
        };
        verify(checkConfig, getPath("InputHiddenField.java"), expected);
    }

    /** Test against a class with field declarations in different order */
    @Test
    public void testReordered()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HiddenFieldCheck.class);
        final String[] expected = {
            "18:13: 'hidden' hides a field.",
            "21:40: 'hidden' hides a field.",
            "27:13: 'hidden' hides a field.",
            "32:18: 'hidden' hides a field.",
            "36:33: 'hidden' hides a field.",
            "46:17: 'innerHidden' hides a field.",
            "49:26: 'innerHidden' hides a field.",
            "55:17: 'innerHidden' hides a field.",
            "56:17: 'hidden' hides a field.",
            "61:22: 'innerHidden' hides a field.",
            "64:22: 'hidden' hides a field.",
            "69:17: 'innerHidden' hides a field.",
            "70:17: 'hidden' hides a field.",
            "76:17: 'innerHidden' hides a field.",
            "77:17: 'hidden' hides a field.",
            "83:13: 'hidden' hides a field.",
            "105:17: 'hidden' hides a field.",
            "118:21: 'hidden' hides a field.",
            "125:13: 'hidden' hides a field.",
            "131:13: 'hiddenStatic' hides a field.",
        };
        verify(checkConfig, getPath("InputHiddenFieldReorder.java"), expected);
    }

    @Test
    public void testIgnoreAbstractMethods() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HiddenFieldCheck.class);
        checkConfig.addAttribute("ignoreAbstractMethods", "true");

        final String[] expected = {
            "18:13: 'hidden' hides a field.",
            "21:33: 'hidden' hides a field.",
            "27:13: 'hidden' hides a field.",
            "32:18: 'hidden' hides a field.",
            "36:33: 'hidden' hides a field.",
            "46:17: 'innerHidden' hides a field.",
            "49:26: 'innerHidden' hides a field.",
            "55:17: 'innerHidden' hides a field.",
            "56:17: 'hidden' hides a field.",
            "61:22: 'innerHidden' hides a field.",
            "64:22: 'hidden' hides a field.",
            "69:17: 'innerHidden' hides a field.",
            "70:17: 'hidden' hides a field.",
            "76:17: 'innerHidden' hides a field.",
            "77:17: 'hidden' hides a field.",
            "82:13: 'hidden' hides a field.",
            "100:29: 'prop' hides a field.",
            "106:29: 'prop' hides a field.",
            "112:29: 'prop' hides a field.",
            "124:28: 'prop' hides a field.",
            "138:13: 'hidden' hides a field.",
            "143:13: 'hidden' hides a field.",
            "148:13: 'hidden' hides a field.",
            "152:13: 'hidden' hides a field.",
            "179:23: 'y' hides a field.",
            "200:17: 'hidden' hides a field.",
            "210:20: 'hidden' hides a field.",
            "217:13: 'hidden' hides a field.",
            "223:13: 'hiddenStatic' hides a field.",
            "236:30: 'xAxis' hides a field.",
        };
        verify(checkConfig, getPath("InputHiddenField.java"), expected);
    }
}
