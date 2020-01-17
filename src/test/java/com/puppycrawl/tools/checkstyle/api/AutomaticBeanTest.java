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

package com.puppycrawl.tools.checkstyle.api;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.Arrays;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.junit.jupiter.api.Test;
import org.powermock.reflect.Whitebox;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DefaultContext;
import com.puppycrawl.tools.checkstyle.checks.naming.AccessModifier;

public class AutomaticBeanTest {

    @Test
    public void testConfigureNoSuchAttribute() {
        final TestBean testBean = new TestBean();
        final DefaultConfiguration conf = new DefaultConfiguration("testConf");
        conf.addAttribute("NonExistent", "doesn't matter");
        try {
            testBean.configure(conf);
            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            assertNull(ex.getCause(), "Exceptions cause should be null");
            assertEquals("Property 'NonExistent' does not exist, please check the documentation",
                    ex.getMessage(), "Invalid exception message");
        }
    }

    @Test
    public void testConfigureNoSuchAttribute2() {
        final TestBean testBean = new TestBean();
        final DefaultConfiguration conf = new DefaultConfiguration("testConf");
        conf.addAttribute("privateField", "doesn't matter");
        try {
            testBean.configure(conf);
            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            assertNull(ex.getCause(), "Exceptions cause should be null");
            assertEquals("Property 'privateField' does not exist, please check the documentation",
                    ex.getMessage(), "Invalid exception message");
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
            assertEquals(expectedMessage, ex.getMessage(), "Invalid exception message");
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
            assertEquals("childConf is not allowed as a "
                            + "child in parentConf. Please review 'Parent Module' section "
                            + "for this Check in web documentation if Check is standard.",
                    ex.getMessage(), "expected exception");
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
            assertTrue(ex.getCause() instanceof InvocationTargetException,
                    "Invalid exception cause, should be: InvocationTargetException");
            assertTrue(ex.getMessage().startsWith(expected),
                    "Invalid exception message, should start with: " + expected);
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
            assertTrue(ex.getCause() instanceof ConversionException,
                    "Invalid exception cause, should be: ConversionException");
            assertTrue(ex.getMessage().startsWith(expected),
                    "Invalid exception message, should start with: " + expected);
        }
    }

    @Test
    public void testTestBean() {
        final TestBean testBean = new TestBean();
        testBean.setVal(0);
        testBean.setWrong("wrongVal");
        testBean.assignPrivateFieldSecretly(null);
        try {
            testBean.setExceptionalMethod("someValue");
            fail("exception expected");
        }
        catch (IllegalStateException ex) {
            assertEquals("null,wrongVal,0,someValue", ex.getMessage(), "Invalid exception message");
        }
    }

    @Test
    public void testRegisterIntegralTypes() throws Exception {
        final ConvertUtilsBeanStub convertUtilsBean = new ConvertUtilsBeanStub();
        Whitebox.invokeMethod(AutomaticBean.class, "registerIntegralTypes", convertUtilsBean);
        assertEquals(81, convertUtilsBean.getRegisterCount(),
                "Number of converters registered differs from expected");
    }

    @Test
    public void testBeanConverters() throws Exception {
        final ConverterBean bean = new ConverterBean();

        // methods are not seen as used by reflection
        bean.setStrings("BAD");
        bean.setPattern(null);
        bean.setSeverityLevel(null);
        bean.setScope(null);
        bean.setUri(null);
        bean.setAccessModifiers(AccessModifier.PACKAGE);

        final DefaultConfiguration config = new DefaultConfiguration("bean");
        config.addAttribute("strings", "a, b, c");
        config.addAttribute("pattern", ".*");
        config.addAttribute("severityLevel", "error");
        config.addAttribute("scope", "public");
        config.addAttribute("uri", "http://github.com");
        config.addAttribute("accessModifiers", "public, private");
        bean.configure(config);

        assertArrayEquals(new String[] {"a", "b", "c"}, bean.strings, "invalid result");
        assertEquals(".*", bean.pattern.pattern(), "invalid result");
        assertEquals(SeverityLevel.ERROR, bean.severityLevel, "invalid result");
        assertEquals(Scope.PUBLIC, bean.scope, "invalid result");
        assertEquals(new URI("http://github.com"), bean.uri, "invalid result");
        assertArrayEquals(
                new AccessModifier[] {AccessModifier.PUBLIC, AccessModifier.PRIVATE},
                bean.accessModifiers, "invalid result");
    }

    @Test
    public void testBeanConvertersUri2() throws Exception {
        final ConverterBean bean = new ConverterBean();
        final DefaultConfiguration config = new DefaultConfiguration("bean");
        config.addAttribute("uri", "");
        bean.configure(config);

        assertNull(bean.uri, "invalid result");
    }

    @Test
    public void testBeanConvertersUri3() {
        final ConverterBean bean = new ConverterBean();
        final DefaultConfiguration config = new DefaultConfiguration("bean");
        config.addAttribute("uri", "BAD");

        try {
            bean.configure(config);
            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            assertEquals("illegal value 'BAD' for property 'uri'", ex.getMessage(),
                    "Error message is not expected");
        }
    }

    private static class ConvertUtilsBeanStub extends ConvertUtilsBean {

        private int registerCount;

        @Override
        public void register(Converter converter, Class<?> clazz) {
            super.register(converter, clazz);
            if (converter != null) {
                registerCount++;
            }
        }

        public int getRegisterCount() {
            return registerCount;
        }

    }

    public static final class TestBean extends AutomaticBean {

        private String privateField;

        private String wrong;

        private int val;

        public void setWrong(String wrong) {
            this.wrong = wrong;
        }

        public void setVal(int val) {
            this.val = val;
        }

        public void assignPrivateFieldSecretly(String input) {
            privateField = input;
        }

        public void setExceptionalMethod(String value) {
            throw new IllegalStateException(privateField + "," + wrong + "," + val + "," + value);
        }

        @Override
        protected void finishLocalSetup() {
            // No code by default
        }

    }

    /**
     * This class has to be public for reflection to access the methods.
     */
    public static class ConverterBean extends AutomaticBean {

        private String[] strings;
        private Pattern pattern;
        private SeverityLevel severityLevel;
        private Scope scope;
        private URI uri;
        private AccessModifier[] accessModifiers;

        /**
         * Setter for strings.
         * @param strings strings.
         */
        public void setStrings(String... strings) {
            this.strings = Arrays.copyOf(strings, strings.length);
        }

        /**
         * Setter for pattern.
         * @param pattern pattern.
         */
        public void setPattern(Pattern pattern) {
            this.pattern = pattern;
        }

        /**
         * Setter for severity level.
         * @param severityLevel severity level.
         */
        public void setSeverityLevel(SeverityLevel severityLevel) {
            this.severityLevel = severityLevel;
        }

        /**
         * Setter for scope.
         * @param scope scope.
         */
        public void setScope(Scope scope) {
            this.scope = scope;
        }

        /**
         * Setter for uri.
         * @param uri uri.
         */
        public void setUri(URI uri) {
            this.uri = uri;
        }

        /**
         * Setter for access modifiers.
         * @param accessModifiers access modifiers.
         */
        public void setAccessModifiers(AccessModifier... accessModifiers) {
            this.accessModifiers = Arrays.copyOf(accessModifiers, accessModifiers.length);
        }

        @Override
        protected void finishLocalSetup() {
            // no code
        }

    }

}
