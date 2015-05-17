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

package com.puppycrawl.tools.checkstyle.checks.metrics;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.File;
import org.junit.Test;

import static com.puppycrawl.tools.checkstyle.checks.metrics.JavaNCSSCheck.MSG_CLASS;
import static com.puppycrawl.tools.checkstyle.checks.metrics.JavaNCSSCheck.MSG_FILE;
import static com.puppycrawl.tools.checkstyle.checks.metrics.JavaNCSSCheck.MSG_METHOD;

/**
 * Testcase for the JavaNCSS-Check.
 *
 * @author Lars Ködderitzsch
 */
public class JavaNCSSCheckTest extends BaseCheckTestSupport {

    @Test
    public void test() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(JavaNCSSCheck.class);

        checkConfig.addAttribute("methodMaximum", "0");
        checkConfig.addAttribute("classMaximum", "1");
        checkConfig.addAttribute("fileMaximum", "2");

        String[] expected = {
            "2:1: " + getCheckMessage(MSG_FILE, 35, 2),
            "9:1: " + getCheckMessage(MSG_CLASS, 22, 1),
            "14:5: " + getCheckMessage(MSG_METHOD, 2, 0),
            "21:5: " + getCheckMessage(MSG_METHOD, 4, 0),
            "30:5: " + getCheckMessage(MSG_METHOD, 12, 0),
            "42:13: " + getCheckMessage(MSG_METHOD, 2, 0),
            "49:5: " + getCheckMessage(MSG_CLASS, 2, 1),
            "56:1: " + getCheckMessage(MSG_CLASS, 10, 1),
            "61:5: " + getCheckMessage(MSG_METHOD, 8, 0),
        };

        verify(checkConfig, getPath("metrics" + File.separator
                + "JavaNCSSCheckTestInput.java"), expected);
    }
}
