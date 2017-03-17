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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_CLASS_INFO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.checks.AbstractTypeAwareCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

@SuppressWarnings("deprecation")
public class AbstractTypeAwareCheckTest extends BaseCheckTestSupport {
    private DefaultConfiguration checkConfig;

    @Before
    public void setUp() {
        checkConfig = createCheckConfig(JavadocMethodCheck.class);
    }

    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "javadoc" + File.separator + filename);
    }

    @Test
    public void testIsSubclassWithNulls() throws Exception {
        final JavadocMethodCheck check = new JavadocMethodCheck();
        final Method isSubclass = check.getClass().getSuperclass()
                .getDeclaredMethod("isSubclass", Class.class, Class.class);
        isSubclass.setAccessible(true);
        assertFalse((boolean) isSubclass.invoke(check, null, null));
    }

    @Test
    public void testTokenToString() throws Exception {
        final Class<?> tokenType = Class
                .forName("com.puppycrawl.tools.checkstyle.checks.AbstractTypeAwareCheck$Token");
        final Constructor<?> tokenConstructor = tokenType.getDeclaredConstructor(String.class,
                int.class, int.class);
        final Object token = tokenConstructor.newInstance("blablabla", 1, 1);
        final Method toString = token.getClass().getDeclaredMethod("toString");
        final String result = (String) toString.invoke(token);
        assertEquals("Token[blablabla(1x1)]", result);
    }

    @Test
    public void testClassRegularClass() throws Exception {
        final Class<?> tokenType = Class
                .forName("com.puppycrawl.tools.checkstyle.checks.AbstractTypeAwareCheck$Token");

        final Class<?> regularClassType = Class
                .forName(
                    "com.puppycrawl.tools.checkstyle.checks.AbstractTypeAwareCheck$RegularClass");
        final Constructor<?> regularClassConstructor = regularClassType.getDeclaredConstructor(
                tokenType, String.class, AbstractTypeAwareCheck.class);
        regularClassConstructor.setAccessible(true);

        try {
            regularClassConstructor.newInstance(null, "", new JavadocMethodCheck());
        }
        catch (InvocationTargetException ex) {
            assertTrue(ex.getCause() instanceof IllegalArgumentException);
            assertEquals("ClassInfo's name should be non-null", ex.getCause().getMessage());
        }

        final Constructor<?> tokenConstructor = tokenType.getDeclaredConstructor(String.class,
                int.class, int.class);
        final Object token = tokenConstructor.newInstance("blablabla", 1, 1);

        final Object regularClass = regularClassConstructor.newInstance(token, "sur",
                new JavadocMethodCheck());

        final Method toString = regularClass.getClass().getDeclaredMethod("toString");
        toString.setAccessible(true);
        final String result = (String) toString.invoke(regularClass);
        assertEquals("RegularClass[name=Token[blablabla(1x1)], in class=sur, loadable=true,"
                + " class=null]", result);

        final Method setClazz = regularClass.getClass().getDeclaredMethod("setClazz", Class.class);
        setClazz.setAccessible(true);
        final Class<?> arg = null;
        setClazz.invoke(regularClass, arg);

        final Method getClazz = regularClass.getClass().getDeclaredMethod("getClazz");
        getClazz.setAccessible(true);
        assertNull(getClazz.invoke(regularClass));
    }

    @Test
    public void testClassAliasToString() throws Exception {
        final Class<?> tokenType = Class
                .forName("com.puppycrawl.tools.checkstyle.checks.AbstractTypeAwareCheck$Token");

        final Class<?> regularClassType = Class
                .forName(
                    "com.puppycrawl.tools.checkstyle.checks.AbstractTypeAwareCheck$RegularClass");
        final Constructor<?> regularClassConstructor = regularClassType.getDeclaredConstructor(
                tokenType, String.class, AbstractTypeAwareCheck.class);
        regularClassConstructor.setAccessible(true);

        final Constructor<?> tokenConstructor = tokenType.getDeclaredConstructor(String.class,
                int.class, int.class);
        final Object token = tokenConstructor.newInstance("blablabla", 1, 1);

        final Object regularClass = regularClassConstructor.newInstance(token, "sur",
                new JavadocMethodCheck());

        final Class<?> classAliasType = Class.forName(
                "com.puppycrawl.tools.checkstyle.checks.AbstractTypeAwareCheck$ClassAlias");
        final Class<?> abstractTypeInfoType = Class.forName(
                "com.puppycrawl.tools.checkstyle.checks.AbstractTypeAwareCheck$AbstractClassInfo");

        final Constructor<?> classAliasConstructor = classAliasType
                .getDeclaredConstructor(tokenType, abstractTypeInfoType);
        classAliasConstructor.setAccessible(true);

        final Object classAlias = classAliasConstructor.newInstance(token, regularClass);
        final Method toString = classAlias.getClass().getDeclaredMethod("toString");
        toString.setAccessible(true);
        final String result = (String) toString.invoke(classAlias);
        assertEquals("ClassAlias[alias Token[blablabla(1x1)] for Token[blablabla(1x1)]]", result);
    }

    @Test
    public void testWithoutLogErrors() throws Exception {
        final DefaultConfiguration config = createCheckConfig(JavadocMethodCheck.class);
        config.addAttribute("logLoadErrors", "false");
        config.addAttribute("allowUndeclaredRTE", "true");
        final String[] expected = {
            "7:8: " + getCheckMessage(MSG_CLASS_INFO, "@throws", "InvalidExceptionName"),
        };
        try {
            verify(config, getPath("InputLoadErrors.java"), expected);
        }
        catch (CheckstyleException ex) {
            final IllegalStateException cause = (IllegalStateException) ex.getCause();
            assertEquals(getCheckMessage(MSG_CLASS_INFO, "@throws", "InvalidExceptionName"),
                    cause.getMessage());
        }
    }

    @Test
    public void testWithSuppressLoadErrors() throws Exception {
        checkConfig.addAttribute("suppressLoadErrors", "true");
        checkConfig.addAttribute("allowUndeclaredRTE", "true");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputLoadErrors.java"), expected);
    }
}
