////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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
import org.junit.Before;
import org.junit.Test;

public class RedundantThrowsCheckTest extends BaseCheckTestSupport
{
    private DefaultConfiguration mCheckConfig;

    @Before
    public void setUp()
    {
        mCheckConfig = createCheckConfig(RedundantThrowsCheck.class);
    }

    @Test
    public void testDefaults() throws Exception
    {
        final String[] expected = {
            "7:37: Redundant throws: 'java.io.FileNotFoundException' is subclass of 'java.io.IOException'.",
            "13:16: Redundant throws: 'RuntimeException' is unchecked exception.",
            "19:29: Redundant throws: 'java.io.IOException' listed more then one time.",
            "39:27: Redundant throws: 'NullPointerException' is subclass of 'RuntimeException'.",
            "39:27: Redundant throws: 'NullPointerException' is unchecked exception.",
            "39:49: Redundant throws: 'RuntimeException' is unchecked exception.",
        };
        verify(mCheckConfig, getPath("InputRedundantThrows.java"), expected);
    }

    @Test
    public void testAllowUnchecked() throws Exception
    {
        mCheckConfig.addAttribute("allowUnchecked", "true");
        final String[] expected = {
            "7:37: Redundant throws: 'java.io.FileNotFoundException' is subclass of 'java.io.IOException'.",
            "19:29: Redundant throws: 'java.io.IOException' listed more then one time.",
//             "35:27: Unable to get class information for WrongException.",
            "39:27: Redundant throws: 'NullPointerException' is subclass of 'RuntimeException'.",
        };
        verify(mCheckConfig, getPath("InputRedundantThrows.java"), expected);
    }

    @Test
    public void testAllowSubclasses() throws Exception
    {
        mCheckConfig.addAttribute("allowSubclasses", "true");
        final String[] expected = {
            "13:16: Redundant throws: 'RuntimeException' is unchecked exception.",
            "19:29: Redundant throws: 'java.io.IOException' listed more then one time.",
            "39:27: Redundant throws: 'NullPointerException' is unchecked exception.",
            "39:49: Redundant throws: 'RuntimeException' is unchecked exception.",
        };
        verify(mCheckConfig, getPath("InputRedundantThrows.java"), expected);
    }

    @Test
    public void testRejectDuplicatesOnly() throws Exception
    {
        mCheckConfig.addAttribute("allowSubclasses", "true");
        mCheckConfig.addAttribute("allowUnchecked", "true");
        final String[] expected = {
            "19:29: Redundant throws: 'java.io.IOException' listed more then one time.",
        };
        verify(mCheckConfig, getPath("InputRedundantThrows.java"), expected);
    }

    @Test
    public void test_1168408_1() throws Exception
    {
        final String[] expected = {};
        verify(mCheckConfig, getSrcPath("checks/javadoc/Input_01.java"), expected);
    }

    @Test
    public void test_1168408_2() throws Exception
    {
        final String[] expected = {};
        verify(mCheckConfig, getSrcPath("checks/javadoc/Input_02.java"), expected);
    }

    @Test
    public void test_1168408_3() throws Exception
    {
        final String[] expected = {};
        verify(mCheckConfig, getSrcPath("checks/javadoc/Input_03.java"), expected);
    }

    @Test
    public void test_1220726() throws Exception
    {
        final String[] expected = {};
        verify(mCheckConfig, getSrcPath("checks/javadoc/BadCls.java"), expected);
    }

    @Test
    public void test_generics_params() throws Exception
    {
        final String[] expected = {
            "15:34: Redundant throws: 'RE' is unchecked exception.",
            "23:37: Redundant throws: 'RE' is subclass of 'E'.",
            "23:37: Redundant throws: 'RE' is unchecked exception.",
            "31:69: Redundant throws: 'NPE' is subclass of 'RE'.",
            "31:69: Redundant throws: 'NPE' is unchecked exception.",
            "31:74: Redundant throws: 'RE' is unchecked exception.",
            "41:38: Redundant throws: 'RuntimeException' is subclass of 'RE'.",
            "41:38: Redundant throws: 'RuntimeException' is unchecked exception.",
            "41:56: Redundant throws: 'RE' is unchecked exception.",
            "42:13: Redundant throws: 'java.lang.RuntimeException' is unchecked exception.",
            "42:13: Redundant throws: 'java.lang.RuntimeException' listed more then one time.",
        };
        verify(mCheckConfig, getPath("javadoc/TestGenerics.java"), expected);
    }

    @Test
    public void test_1379666() throws Exception
    {
        final String[] expected = {};
        verify(mCheckConfig, getSrcPath("checks/javadoc/Input_1379666.java"), expected);
    }
}
