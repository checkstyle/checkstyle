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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_CLASS_INFO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.AbstractTypeAwareCheck;

public class AbstractTypeAwareCheckTest extends BaseCheckTestSupport {
    private DefaultConfiguration checkConfig;

    @Before
    public void setUp() {
        checkConfig = createCheckConfig(JavadocMethodCheck.class);
    }

    @Test
    public void testIsSubclassWithNulls() throws Exception {
        JavadocMethodCheck check = new JavadocMethodCheck();
        Method isSublclass = check.getClass().getSuperclass()
                .getDeclaredMethod("isSubclass", Class.class, Class.class);
        isSublclass.setAccessible(true);
        assertFalse((boolean) isSublclass.invoke(check, null, null));
    }

    @Test
    public void testTokenToString() throws Exception {
        Class<?> tokenType = Class
                .forName("com.puppycrawl.tools.checkstyle.checks.AbstractTypeAwareCheck$Token");
        Constructor<?> tokenConstructor = tokenType.getDeclaredConstructor(String.class, int.class,
                int.class);
        Object token = tokenConstructor.newInstance("blablabla", 1, 1);
        Method toString = token.getClass().getDeclaredMethod("toString");
        String result = (String) toString.invoke(token);
        assertEquals("Token[blablabla(1x1)]", result);
    }

    @Test
    public void testClassRegularClass() throws Exception {
        Class<?> tokenType = Class
                .forName("com.puppycrawl.tools.checkstyle.checks.AbstractTypeAwareCheck$Token");

        Class<?> regularClassType = Class
                .forName(
                        "com.puppycrawl.tools.checkstyle.checks.AbstractTypeAwareCheck$RegularClass");
        Constructor<?> regularClassConstructor = regularClassType.getDeclaredConstructor(tokenType,
                String.class, AbstractTypeAwareCheck.class);
        regularClassConstructor.setAccessible(true);

        try {
            regularClassConstructor.newInstance(null, "", new JavadocMethodCheck());
        }
        catch (InvocationTargetException ex) {
            assertTrue(ex.getCause() instanceof IllegalArgumentException);
            assertEquals(ex.getCause().getMessage(), "ClassInfo's name should be non-null");
        }

        Constructor<?> tokenConstructor = tokenType.getDeclaredConstructor(String.class, int.class,
                int.class);
        Object token = tokenConstructor.newInstance("blablabla", 1, 1);

        Object regularClass = regularClassConstructor.newInstance(token, "sur",
                new JavadocMethodCheck());

        Method toString = regularClass.getClass().getDeclaredMethod("toString");
        toString.setAccessible(true);
        String result = (String) toString.invoke(regularClass);
        assertEquals("RegularClass[name=Token[blablabla(1x1)], in class=sur, loadable=true,"
                + " class=null]", result);

        Method setClazz = regularClass.getClass().getDeclaredMethod("setClazz", Class.class);
        setClazz.setAccessible(true);
        Class<?> arg = null;
        setClazz.invoke(regularClass, arg);

        Method getClazz = regularClass.getClass().getDeclaredMethod("getClazz");
        getClazz.setAccessible(true);
        assertTrue(getClazz.invoke(regularClass) == null);
    }

    @Test
    public void testClassAliasToString() throws Exception {
        Class<?> tokenType = Class
                .forName("com.puppycrawl.tools.checkstyle.checks.AbstractTypeAwareCheck$Token");

        Class<?> regularClassType = Class
                .forName(
                        "com.puppycrawl.tools.checkstyle.checks.AbstractTypeAwareCheck$RegularClass");
        Constructor<?> regularClassConstructor = regularClassType.getDeclaredConstructor(tokenType,
                String.class, AbstractTypeAwareCheck.class);
        regularClassConstructor.setAccessible(true);

        Constructor<?> tokenConstructor = tokenType.getDeclaredConstructor(String.class, int.class,
                int.class);
        Object token = tokenConstructor.newInstance("blablabla", 1, 1);

        Object regularClass = regularClassConstructor.newInstance(token, "sur",
                new JavadocMethodCheck());

        Class<?> classAliasType = Class.forName(
                "com.puppycrawl.tools.checkstyle.checks.AbstractTypeAwareCheck$ClassAlias");
        Class<?> abstractTypeInfoType = Class.forName(
                "com.puppycrawl.tools.checkstyle.checks.AbstractTypeAwareCheck$AbstractClassInfo");

        Constructor<?> classAliasConstructor = classAliasType
                .getDeclaredConstructor(tokenType, abstractTypeInfoType);
        classAliasConstructor.setAccessible(true);

        Object classAlias = classAliasConstructor.newInstance(token, regularClass);
        Method toString = classAlias.getClass().getDeclaredMethod("toString");
        toString.setAccessible(true);
        String result = (String) toString.invoke(classAlias);
        assertEquals("ClassAlias[alias Token[blablabla(1x1)] for Token[blablabla(1x1)]]", result);
    }

    @Test
    public void testWithoutLogErrors() throws Exception {
        DefaultConfiguration config = createCheckConfig(JavadocMethodCheck.class);
        config.addAttribute("logLoadErrors", "false");
        config.addAttribute("allowUndeclaredRTE", "true");
        final String[] expected = {
            "7:8: " + getCheckMessage(MSG_CLASS_INFO, "@throws", "InvalidExceptionName"),
        };
        try {
            verify(config, getPath("javadoc/InputLoadErrors.java"), expected);
        }
        catch (IllegalStateException ex) {
            assertEquals("Unable to get"
                    + " class information for @throws tag 'InvalidExceptionName'.",
                    ex.getMessage());
        }
    }

    @Test
    public void testWithSuppressLoadErrors() throws Exception {
        checkConfig.addAttribute("suppressLoadErrors", "true");
        checkConfig.addAttribute("allowUndeclaredRTE", "true");
        final String[] expected = {
        };
        verify(checkConfig, getPath("javadoc/InputLoadErrors.java"), expected);
    }
}
