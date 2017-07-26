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

package com.puppycrawl.tools.checkstyle.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DefaultContext;

public class AutomaticBeanTest {

    @Test
    public void testConfigureNoSuchAttribute() {
        final TestBean testBean = new TestBean();
        final DefaultConfiguration conf = new DefaultConfiguration("testConf");
        conf.addAttribute("NonExisting", "doesn't matter");
        try {
            testBean.configure(conf);
        }
        catch (CheckstyleException ex) {
            final String expected = "Property 'NonExisting' in module ";
            assertNull("Exceptions cause should be null", ex.getCause());
            assertTrue("Invalid exception message, should start with: " + expected,
                    ex.getMessage().startsWith(expected));
        }
    }

    @Test
    public void testConfigureNoSuchAttribute2() {
        final TestBean testBean = new TestBean();
        final DefaultConfiguration conf = new DefaultConfiguration("testConf");
        conf.addAttribute("privateField", "doesn't matter");
        try {
            testBean.configure(conf);
        }
        catch (CheckstyleException ex) {
            final String expected = "Property 'privateField' in module ";
            assertNull("Exceptions cause should be null", ex.getCause());
            assertTrue("Invalid exception message, should start with: " + expected,
                    ex.getMessage().startsWith(expected));
        }
    }

    @Test
    public void testSetupChildFromBaseClass() throws CheckstyleException {
        final TestBean testBean = new TestBean();
        testBean.configure(new DefaultConfiguration("bean config"));
        testBean.setupChild(null);
        try {
            testBean.setupChild(new DefaultConfiguration("dummy"));
            fail("Exception expected");
        }
        catch (CheckstyleException ex) {
            final String expectedMessage = "dummy is not allowed as a child in bean config. "
                    + "Please review 'Parent Module' section for this Check"
                    + " in web documentation if Check is standard.";
            assertEquals("Invalid exception message", expectedMessage, ex.getMessage());
        }
    }

    @Test
    public void testSetupInvalidChildFromBaseClass() throws Exception {
        final TestBean testBean = new TestBean();
        final DefaultConfiguration parentConf = new DefaultConfiguration("parentConf");
        final DefaultConfiguration childConf = new DefaultConfiguration("childConf");
        final Field field = AutomaticBean.class.getDeclaredField("configuration");
        field.setAccessible(true);
        field.set(testBean, parentConf);

        try {
            testBean.setupChild(childConf);
            fail("expecting checkstyle exception");
        }
        catch (CheckstyleException ex) {
            assertEquals("expected exception", "childConf is not allowed as a "
                            + "child in parentConf. Please review 'Parent Module' section "
                            + "for this Check in web documentation if Check is standard.",
                    ex.getMessage());
        }
    }

    @Test
    public void testContextualizeInvocationTargetException() {
        final TestBean testBean = new TestBean();
        final DefaultContext context = new DefaultContext();
        context.add("exceptionalMethod", 123.0f);
        try {
            testBean.contextualize(context);
            fail("InvocationTargetException is expected");
        }
        catch (CheckstyleException ex) {
            final String expected = "Cannot set property ";
            assertTrue("Invalid exception cause, should be: InvocationTargetException",
                    ex.getCause() instanceof InvocationTargetException);
            assertTrue("Invalid exception message, should start with: " + expected,
                    ex.getMessage().startsWith(expected));
        }
    }

    @Test
    public void testContextualizeConversionException() {
        final TestBean testBean = new TestBean();
        final DefaultContext context = new DefaultContext();
        context.add("val", "some string");
        try {
            testBean.contextualize(context);
            fail("InvocationTargetException is expected");
        }
        catch (CheckstyleException ex) {
            final String expected = "illegal value ";
            assertTrue("Invalid exception cause, should be: ConversionException",
                    ex.getCause() instanceof ConversionException);
            assertTrue("Invalid exception message, should start with: " + expected,
                    ex.getMessage().startsWith(expected));
        }
    }

    @Test
    public void testTestBean() {
        final TestBean testBean = new TestBean();
        testBean.setVal(0);
        testBean.setWrong("wrongVal");
        try {
            testBean.setExceptionalMethod("someValue");
            fail("exception expected");
        }
        catch (IllegalStateException ex) {
            assertEquals("Invalid exception message",
                    "null,wrongVal,0,someValue", ex.getMessage());
        }
    }

    @Test
    public void testRegisterIntegralTypes() throws Exception {
        final ConvertUtilsBeanStub convertUtilsBean = new ConvertUtilsBeanStub();
        Whitebox.invokeMethod(AutomaticBean.class, "registerIntegralTypes", convertUtilsBean);
        assertEquals("Number of converters registered differs from expected",
                81, convertUtilsBean.getRegisterCount());
    }

    private static class ConvertUtilsBeanStub extends ConvertUtilsBean {

        private int registerCount;

        @Override
        public void register(Converter converter, Class<?> clazz) {
            super.register(converter, clazz);
            registerCount++;
        }

        public int getRegisterCount() {
            return registerCount;
        }
    }

    private static class TestBean extends AutomaticBean {

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
            throw new IllegalStateException(privateField + "," + wrong + "," + val + "," + value);
        }

        public void doSmth() {
            privateField = "some value, just for fun";
        }

    }
}
