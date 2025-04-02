///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle;

import static com.google.common.truth.Truth.assertWithMessage;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.checks.naming.AccessModifierOption;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

public class AbstractAutomaticBeanTest {
    @Test
    public void testConfigureNoSuchAttribute() {
        final TestBean testBean = new TestBean();
        final DefaultConfiguration conf = new DefaultConfiguration("testConf");
        conf.addProperty("NonExistent", "doesn't matter");
        try {
            testBean.configure(conf);
            assertWithMessage("Exception is expected")
                    .fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Exceptions cause should be null")
                    .that(ex)
                    .hasCauseThat()
                    .isNull();
            assertWithMessage("Invalid exception message")
                    .that(ex)
                    .hasMessageThat()
                    .isEqualTo("Property 'NonExistent' does not exist,"
                            + " please check the documentation");
        }
    }

    @Test
    public void testConfigureNoSuchAttribute2() {
        final TestBean testBean = new TestBean();
        final DefaultConfiguration conf = new DefaultConfiguration("testConf");
        conf.addProperty("privateField", "doesn't matter");
        try {
            testBean.configure(conf);
            assertWithMessage("Exception is expected")
                    .fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Exceptions cause should be null")
                    .that(ex)
                    .hasCauseThat()
                    .isNull();
            assertWithMessage("Invalid exception message")
                    .that(ex)
                    .hasMessageThat()
                    .isEqualTo("Property 'privateField' does not exist,"
                            + " please check the documentation");
        }
    }

    @Test
    public void testSetupChildFromBaseClass() throws CheckstyleException {
        final TestBean testBean = new TestBean();
        testBean.configure(new DefaultConfiguration("bean config"));
        testBean.setupChild(null);
        try {
            testBean.setupChild(new DefaultConfiguration("dummy"));
            assertWithMessage("Exception is expected")
                    .fail();
        }
        catch (CheckstyleException ex) {
            final String expectedMessage = "dummy is not allowed as a child in bean config. "
                    + "Please review 'Parent Module' section for this Check"
                    + " in web documentation if Check is standard.";
            assertWithMessage("Invalid exception message")
                    .that(ex)
                    .hasMessageThat()
                    .isEqualTo(expectedMessage);
        }
    }

    @Test
    public void testNullConfiguration() throws CheckstyleException {
        final TestBean testBean = new TestBean();
        testBean.setupChild(null);

        try {
            testBean.getConfiguration();
            assertWithMessage("Exception is expected")
                    .fail();
        } catch (IllegalStateException ex) {
            assertWithMessage("Configuration is null")
                    .that(ex.getMessage())
                    .isNotNull();
        }
    }

    @Test
    public void testSetupInvalidChildFromBaseClass() {
        final TestBean testBean = new TestBean();
        final DefaultConfiguration parentConf = new DefaultConfiguration("parentConf");
        final DefaultConfiguration childConf = new DefaultConfiguration("childConf");
        TestUtil.setInternalState(testBean, "configuration", parentConf);

        try {
            testBean.setupChild(childConf);
            assertWithMessage("expecting checkstyle exception")
                    .fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid exception message")
                    .that(ex)
                    .hasMessageThat()
                    .isEqualTo("childConf is not allowed as a "
                            + "child in parentConf. Please review 'Parent Module' section "
                            + "for this Check in web documentation if Check is standard.");
        }
    }

    @Test
    public void testContextualizeInvocationTargetException() {
        final TestBean testBean = new TestBean();
        final DefaultContext context = new DefaultContext();
        context.add("exceptionalMethod", 123.0f);
        try {
            testBean.contextualize(context);
            assertWithMessage("InvocationTargetException is expected")
                    .fail();
        }
        catch (CheckstyleException ex) {
            final String expected = "Cannot set property 'exceptionalMethod' to '123.0'";
            assertWithMessage("Invalid exception cause, should be: ReflectiveOperationException")
                    .that(ex)
                    .hasCauseThat()
                    .isInstanceOf(ReflectiveOperationException.class);
            assertWithMessage("Invalid exception message, should start with: " + expected)
                    .that(ex)
                    .hasMessageThat()
                    .isEqualTo(expected);
        }
    }

    @Test
    public void testContextualizeConversionException() {
        final TestBean testBean = new TestBean();
        final DefaultContext context = new DefaultContext();
        context.add("val", "some string");
        try {
            testBean.contextualize(context);
            assertWithMessage("InvocationTargetException is expected")
                    .fail();
        }
        catch (CheckstyleException ex) {
            final String expected = "illegal value ";
            assertWithMessage("Invalid exception cause, should be: ConversionException")
                    .that(ex)
                    .hasCauseThat()
                    .isInstanceOf(ConversionException.class);
            assertWithMessage("Invalid exception message, should start with: " + expected)
                    .that(ex)
                    .hasMessageThat()
                    .startsWith(expected);
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
            assertWithMessage("exception expected")
                    .fail();
        }
        catch (IllegalStateException ex) {
            assertWithMessage("Invalid exception message")
                    .that(ex)
                    .hasMessageThat()
                    .isEqualTo("null,wrongVal,0,someValue");
        }
    }

    @Test
    public void testRegisterIntegralTypes() throws Exception {
        final ConvertUtilsBeanStub convertUtilsBean = new ConvertUtilsBeanStub();
        TestUtil.invokeStaticMethod(AbstractAutomaticBean.class,
                "registerIntegralTypes", convertUtilsBean);
        assertWithMessage("Number of converters registered differs from expected")
                .that(convertUtilsBean.getRegisterCount())
                .isEqualTo(81);
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
        bean.setAccessModifiers(AccessModifierOption.PACKAGE);

        final DefaultConfiguration config = new DefaultConfiguration("bean");
        config.addProperty("strings", "a, b, c");
        config.addProperty("pattern", ".*");
        config.addProperty("severityLevel", "error");
        config.addProperty("scope", "public");
        config.addProperty("uri", "http://github.com");
        config.addProperty("accessModifiers", "public, private");
        bean.configure(config);

        final String message = "invalid result";
        assertWithMessage(message)
                .that(bean.strings)
                .asList()
                .containsExactly("a", "b", "c")
                .inOrder();
        assertWithMessage(message)
                .that(bean.pattern.pattern())
                .isEqualTo(".*");
        assertWithMessage(message)
                .that(bean.severityLevel)
                .isEqualTo(SeverityLevel.ERROR);
        assertWithMessage(message)
                .that(bean.scope)
                .isEqualTo(Scope.PUBLIC);
        assertWithMessage(message)
                .that(bean.uri)
                .isEqualTo(new URI("http://github.com"));
        assertWithMessage(message)
                .that(bean.accessModifiers)
                .asList()
                .containsExactly(AccessModifierOption.PUBLIC, AccessModifierOption.PRIVATE)
                .inOrder();
    }

    @Test
    public void testBeanConvertersUri2() throws Exception {
        final ConverterBean bean = new ConverterBean();
        final DefaultConfiguration config = new DefaultConfiguration("bean");
        config.addProperty("uri", "");
        bean.configure(config);

        assertWithMessage("invalid result")
                .that(bean.uri)
                .isNull();
    }

    @Test
    public void testBeanConvertersUri3() {
        final ConverterBean bean = new ConverterBean();
        final DefaultConfiguration config = new DefaultConfiguration("bean");
        config.addProperty("uri", "BAD");

        try {
            bean.configure(config);
            assertWithMessage("Exception is expected")
                    .fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Error message is not expected")
                    .that(ex)
                    .hasMessageThat()
                    .isEqualTo("illegal value 'BAD' for property 'uri'");
        }
    }

    @Test
    public void testBeanConverterPatternArray() throws Exception {
        final ConverterBean bean = new ConverterBean();
        final DefaultConfiguration config = new DefaultConfiguration("bean");
        final String patternString = "^a*$  , ^b*$ , ^c*$ ";
        final List<String> expectedPatternStrings = Arrays.asList("^a*$", "^b*$", "^c*$");
        config.addProperty("patterns", patternString);
        bean.configure(config);

        final List<String> actualPatternStrings = Arrays.stream(bean.patterns)
                .map(Pattern::pattern)
                .collect(Collectors.toUnmodifiableList());

        assertWithMessage("invalid size of result")
                .that(bean.patterns)
                .hasLength(3);
        assertWithMessage("invalid result")
                .that(actualPatternStrings)
                .containsExactlyElementsIn(expectedPatternStrings);
    }

    @Test
    public void testBeanConverterPatternArraySingleElement() throws Exception {
        final ConverterBean bean = new ConverterBean();
        final DefaultConfiguration config = new DefaultConfiguration("bean");
        final String patternString = "^a*$";
        final List<String> expectedPatternStrings = List.of("^a*$");
        config.addProperty("patterns", patternString);
        bean.configure(config);

        final List<String> actualPatternStrings = Arrays.stream(bean.patterns)
                .map(Pattern::pattern)
                .collect(Collectors.toUnmodifiableList());

        assertWithMessage("invalid size of result")
                .that(bean.patterns)
                .hasLength(1);
        assertWithMessage("invalid result")
                .that(actualPatternStrings)
                .containsExactlyElementsIn(expectedPatternStrings);
    }

    @Test
    public void testBeanConverterPatternArrayEmptyString() throws Exception {
        final ConverterBean bean = new ConverterBean();
        final DefaultConfiguration config = new DefaultConfiguration("bean");
        config.addProperty("patterns", "");
        bean.configure(config);

        assertWithMessage("invalid size of result")
                .that(bean.patterns)
                .hasLength(0);
    }

    private static final class ConvertUtilsBeanStub extends ConvertUtilsBean {

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

    public static final class TestBean extends AbstractAutomaticBean {

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
    public static class ConverterBean extends AbstractAutomaticBean {

        private String[] strings;
        private Pattern pattern;
        private SeverityLevel severityLevel;
        private Scope scope;
        private URI uri;
        private AccessModifierOption[] accessModifiers;
        private Pattern[] patterns;

        /**
         * Setter for strings.
         *
         * @param strings strings.
         */
        public void setStrings(String... strings) {
            this.strings = Arrays.copyOf(strings, strings.length);
        }

        /**
         * Setter for pattern.
         *
         * @param pattern pattern.
         */
        public void setPattern(Pattern pattern) {
            this.pattern = pattern;
        }

        /**
         * Setter for severity level.
         *
         * @param severityLevel severity level.
         */
        public void setSeverityLevel(SeverityLevel severityLevel) {
            this.severityLevel = severityLevel;
        }

        /**
         * Setter for scope.
         *
         * @param scope scope.
         */
        public void setScope(Scope scope) {
            this.scope = scope;
        }

        /**
         * Setter for uri.
         *
         * @param uri uri.
         */
        public void setUri(URI uri) {
            this.uri = uri;
        }

        /**
         * Setter for access modifiers.
         *
         * @param accessModifiers access modifiers.
         */
        public void setAccessModifiers(AccessModifierOption... accessModifiers) {
            this.accessModifiers = Arrays.copyOf(accessModifiers,
                    accessModifiers.length);
        }

        /**
         * Setter for patterns.
         *
         * @param patterns patterns
         */
        public void setPatterns(Pattern... patterns) {
            this.patterns = Arrays.copyOf(patterns, patterns.length);
        }

        @Override
        protected void finishLocalSetup() {
            // no code
        }

    }

}
