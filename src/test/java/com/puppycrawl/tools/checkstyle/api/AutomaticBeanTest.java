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
import com.puppycrawl.tools.checkstyle.DefaultContext;

public class AutomaticBeanTest {

    public class TestBean extends AutomaticBean {

        private String wrong;

        private int val;

        public void setWrong(String wrong) {
            this.wrong = wrong;
        }

        public void setIntVal(int val) {
            this.val = val;
        }

        public void setName(String name) {
        }

        /**
         * just fore code coverage
         * @param childConf a child of this component's Configuration
         * @throws CheckstyleException
         */
        @Override
        protected void setupChild(Configuration childConf) throws CheckstyleException {
            super.setupChild(childConf);
        }
    }

    private final DefaultConfiguration conf = new DefaultConfiguration(
            "testConf");

    @Test(expected = CheckstyleException.class)
    public void testNoSuchAttribute() throws CheckstyleException {
        final TestBean testBean = new TestBean();
        conf.addAttribute("NonExisting", "doesn't matter");
        testBean.configure(conf);
    }

    @Test
    public void testNoWrongSetterImplementation() throws CheckstyleException {
        final TestBean testBean = new TestBean();
        conf.addAttribute("wrong", "123");
        testBean.configure(conf);
    }

    @Test
    public void testSetupChild() throws CheckstyleException {
        final TestBean testBean = new TestBean();
        testBean.setupChild(null);
    }

    @Test
    public void testContextualize1() throws CheckstyleException {
        final TestBean testBean = new TestBean();
        DefaultContext context = new DefaultContext();
        context.add("val", 123f);
        testBean.contextualize(context);
    }
}
