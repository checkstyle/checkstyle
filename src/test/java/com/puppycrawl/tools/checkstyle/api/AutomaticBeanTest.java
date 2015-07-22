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

package com.puppycrawl.tools.checkstyle.api;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class AutomaticBeanTest {
    private static class TestBean extends AutomaticBean {
        public void setName(String name) {
        }
    }

    private final DefaultConfiguration conf = new DefaultConfiguration(
            "testConf");

    private final TestBean testBean = new TestBean();

    @Test(expected = CheckstyleException.class)
    public void testNoSuchAttribute() throws CheckstyleException {
        conf.addAttribute("NonExisting", "doesn't matter");
        testBean.configure(conf);
    }
}
