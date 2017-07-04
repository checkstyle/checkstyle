////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.design;

import static com.puppycrawl.tools.checkstyle.checks.design.DesignForExtensionCheck.MSG_KEY;
import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class DesignForExtensionCheckTest
    extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "design" + File.separator
                + "designforextension" + File.separator
                + filename);
    }

    @Test
    public void testGetRequiredTokens() {
        final DesignForExtensionCheck checkObj = new DesignForExtensionCheck();
        final int[] expected = {TokenTypes.METHOD_DEF};
        assertArrayEquals(expected, checkObj.getRequiredTokens());
    }

    @Test
    public void testIt() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DesignForExtensionCheck.class);
        final String[] expected = {
            "46:5: " + getCheckMessage(MSG_KEY, "InputDesignForExtension", "doh"),
            "100:9: " + getCheckMessage(MSG_KEY, "anotherNonFinalClass", "someMethod"),
        };
        verify(checkConfig, getPath("InputDesignForExtension.java"), expected);

    }

    @Test
    public void testGetAcceptableTokens() {
        final DesignForExtensionCheck obj = new DesignForExtensionCheck();
        final int[] expected = {TokenTypes.METHOD_DEF};
        assertArrayEquals(expected, obj.getAcceptableTokens());
    }

    @Test
    public void testOverridableMethods() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(DesignForExtensionCheck.class);
        final String[] expected = {
            "6:9: " + getCheckMessage(MSG_KEY, "A", "foo1"),
            "30:9: " + getCheckMessage(MSG_KEY, "A", "foo8"),
            "35:9: " + getCheckMessage(MSG_KEY, "A", "foo9"),
            "42:9: " + getCheckMessage(MSG_KEY, "A", "foo10"),
            "49:9: " + getCheckMessage(MSG_KEY, "A", "foo11"),
            "54:9: " + getCheckMessage(MSG_KEY, "A", "foo12"),
            "61:9: " + getCheckMessage(MSG_KEY, "A", "foo13"),
            "68:9: " + getCheckMessage(MSG_KEY, "A", "foo14"),
            "90:9: " + getCheckMessage(MSG_KEY, "A", "foo22"),
            "96:9: " + getCheckMessage(MSG_KEY, "A", "foo23"),
            "110:9: " + getCheckMessage(MSG_KEY, "A", "foo26"),
            "117:9: " + getCheckMessage(MSG_KEY, "A", "foo27"),
        };
        verify(checkConfig, getPath("InputDesignForExtensionOverridableMethods.java"), expected);
    }

    @Test
    public void testIgnoredAnnotationsOption() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(DesignForExtensionCheck.class);
        checkConfig.addAttribute("ignoredAnnotations", "Override, Deprecated, MyAnnotation");
        final String[] expected = {
            "31:5: "
                + getCheckMessage(MSG_KEY, "InputDesignForExtensionIgnoredAnnotations", "foo1"),
            "141:5: "
                + getCheckMessage(MSG_KEY, "InputDesignForExtensionIgnoredAnnotations", "foo21"),
            "146:5: "
                + getCheckMessage(MSG_KEY, "InputDesignForExtensionIgnoredAnnotations", "setAge"),
            "161:5: "
                + getCheckMessage(MSG_KEY, "InputDesignForExtensionIgnoredAnnotations", "foo24"),
        };
        verify(checkConfig, getPath("InputDesignForExtensionIgnoredAnnotations.java"), expected);
    }

    @Test
    public void testIgnoreAnnotationsOptionWithMultipleAnnotations() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(DesignForExtensionCheck.class);
        checkConfig.addAttribute("ignoredAnnotations",
            "Override, Deprecated, Before, After, BeforeClass, AfterClass");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputDesignForExtensionMultipleAnnotations.java"), expected);
    }

    @Test
    public void testNativeMethods() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(DesignForExtensionCheck.class);
        checkConfig.addAttribute("ignoredAnnotations", "Deprecated");
        final String[] expected = {
            "8:5: " + getCheckMessage(MSG_KEY, "InputDesignForExtensionNativeMethods", "foo1"),
        };
        verify(checkConfig, getPath("InputDesignForExtensionNativeMethods.java"), expected);
    }
}
