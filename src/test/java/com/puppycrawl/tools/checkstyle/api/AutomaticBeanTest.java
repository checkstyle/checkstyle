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

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.ConversionException;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DefaultContext;

public class AutomaticBeanTest {

    @Test
    public void testConfigure_NoSuchAttribute() {
        final TestBean testBean = new TestBean();
        final DefaultConfiguration conf = new DefaultConfiguration("testConf");
        conf.addAttribute("NonExisting", "doesn't matter");
        try {
            testBean.configure(conf);
        }
        catch (CheckstyleException ex) {
            assertNull(ex.getCause());
            assertTrue(ex.getMessage().startsWith("Property '" + "NonExisting" + "' in module "));
        }
    }

    @Test
    public void testConfigure_NoSuchAttribute2() {
        final TestBean testBean = new TestBean();
        final DefaultConfiguration conf = new DefaultConfiguration("testConf");
        conf.addAttribute("privateField", "doesn't matter");
        try {
            testBean.configure(conf);
        }
        catch (CheckstyleException ex) {
            assertNull(ex.getCause());
            assertTrue(ex.getMessage().startsWith("Property '" + "privateField" + "' in module "));
        }
    }

    @Test
    public void testSetupChildFromBaseClass() throws CheckstyleException {
        final TestBean testBean = new TestBean();
        testBean.setupChild(null);
    }

    @Test
    public void testContextualize_InvocationTargetException() {
        final TestBean testBean = new TestBean();
        DefaultContext context = new DefaultContext();
        context.add("exceptionalMethod", 123.0f);
        try {
            testBean.contextualize(context);
            fail();
        }
        catch (CheckstyleException ex) {
            assertTrue(ex.getCause() instanceof InvocationTargetException);
            assertTrue(ex.getMessage().startsWith("Cannot set property "));
        }
    }

    @Test
    public void testContextualize_ConversionException() {
        final TestBean testBean = new TestBean();
        DefaultContext context = new DefaultContext();
        context.add("val", "some string");
        try {
            testBean.contextualize(context);
            fail();
        }
        catch (CheckstyleException ex) {
            assertTrue(ex.getCause() instanceof ConversionException);
            assertTrue(ex.getMessage().startsWith("illegal value "));
        }
    }

    @SuppressWarnings("unused")
    public static class TestBean extends AutomaticBean {

        private String privateField;

        private String wrong;

        private int val;

        public void setWrong(String wrong) {
            this.wrong = wrong;
        }

        public void setVal(int val) {
            this.val = val;
        }

        public void setExceptionalMethod(String value) {
            throw new IllegalStateException("for UT");
        }

        public void setName(String name) {
        }

        /**
         * just for code coverage
         * @param childConf a child of this component's Configuration
         */
        @Override
        protected void setupChild(Configuration childConf) throws CheckstyleException {
            super.setupChild(childConf);
        }
    }
}
