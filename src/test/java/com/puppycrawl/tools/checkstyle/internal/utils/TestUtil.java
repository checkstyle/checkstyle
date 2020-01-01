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

package com.puppycrawl.tools.checkstyle.internal.utils;

import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import com.puppycrawl.tools.checkstyle.PackageNamesLoader;
import com.puppycrawl.tools.checkstyle.PackageObjectFactory;
import com.puppycrawl.tools.checkstyle.TreeWalkerAuditEvent;
import com.puppycrawl.tools.checkstyle.TreeWalkerFilter;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

public final class TestUtil {

    private TestUtil() {
    }

    /**
     * Verifies that utils class has private constructor and invokes it to satisfy code coverage.
     * @param utilClass class to test for c-tor
     * @param checkConstructorIsPrivate flag to skip check for private visibility, it is useful
     *                                  for Classes that are mocked by PowerMockRunner that make
     *                                  private c-tors as public
     * @return true if constructor is expected.
     * @noinspection BooleanParameter
     */
    public static boolean isUtilsClassHasPrivateConstructor(final Class<?> utilClass,
                                                             boolean checkConstructorIsPrivate)
            throws ReflectiveOperationException {
        final Constructor<?> constructor = utilClass.getDeclaredConstructor();
        final boolean result;
        if (checkConstructorIsPrivate && !Modifier.isPrivate(constructor.getModifiers())) {
            result = false;
        }
        else {
            constructor.setAccessible(true);
            constructor.newInstance();
            result = true;
        }
        return result;
    }

    /**
     * Retrieves the specified field by it's name in the class or it's direct super.
     *
     * @param clss The class to retrieve the field for.
     * @param fieldName The name of the field to retrieve.
     * @return The class' field.
     * @throws NoSuchFieldException if the requested field cannot be found in the class.
     */
    public static Field getClassDeclaredField(Class<?> clss, String fieldName)
            throws NoSuchFieldException {
        final Optional<Field> classField = Arrays.stream(clss.getDeclaredFields())
                .filter(field -> fieldName.equals(field.getName())).findFirst();
        final Field resultField;
        if (classField.isPresent()) {
            resultField = classField.get();
        }
        else {
            resultField = clss.getSuperclass().getDeclaredField(fieldName);
        }
        resultField.setAccessible(true);
        return resultField;
    }

    /**
     * Retrieves the specified method by it's name in the class or it's direct super.
     *
     * @param clss The class to retrieve the field for.
     * @param methodName The name of the method to retrieve.
     * @return The class' field.
     * @throws NoSuchMethodException if the requested method cannot be found in the class.
     */
    public static Method getClassDeclaredMethod(Class<?> clss, String methodName)
            throws NoSuchMethodException {
        final Optional<Method> classMethod = Arrays.stream(clss.getDeclaredMethods())
                .filter(method -> methodName.equals(method.getName())).findFirst();
        final Method resultMethod;
        if (classMethod.isPresent()) {
            resultMethod = classMethod.get();
        }
        else {
            resultMethod = clss.getSuperclass().getDeclaredMethod(methodName);
        }
        resultMethod.setAccessible(true);
        return resultMethod;
    }

    /**
     * Checks if stateful field is cleared during {@link AbstractCheck#beginTree} in check.
     *
     * @param check      check object which field is to be verified
     * @param astToVisit ast to pass into check methods
     * @param fieldName  name of the field to be checked
     * @param isClear    function for checking field state
     * @return {@code true} if state of the field is cleared
     * @throws NoSuchFieldException   if there is no field with the
     *                                {@code fieldName} in the {@code check}
     * @throws IllegalAccessException if the field is inaccessible
     */
    public static boolean isStatefulFieldClearedDuringBeginTree(AbstractCheck check,
                                                                DetailAST astToVisit,
                                                                String fieldName,
                                                                Predicate<Object> isClear)
            throws NoSuchFieldException, IllegalAccessException {
        check.beginTree(astToVisit);
        check.visitToken(astToVisit);
        check.beginTree(null);
        final Field resultField = getClassDeclaredField(check.getClass(), fieldName);
        return isClear.test(resultField.get(check));
    }

    /**
     * Checks if stateful field is cleared during {@link AutomaticBean}'s finishLocalSetup.
     *
     * @param filter filter object which field is to be verified
     * @param event event to pass into filter methods
     * @param fieldName name of the field to be checked
     * @param isClear function for checking field state
     * @return {@code true} if state of the field is cleared
     * @throws Exception if there was an error.
     */
    public static boolean isStatefulFieldClearedDuringLocalSetup(
            TreeWalkerFilter filter, TreeWalkerAuditEvent event,
            String fieldName, Predicate<Object> isClear) throws Exception {
        filter.accept(event);
        getClassDeclaredMethod(filter.getClass(), "finishLocalSetup").invoke(filter);
        final Field resultField = getClassDeclaredField(filter.getClass(), fieldName);
        return isClear.test(resultField.get(filter));
    }

    /**
     * Returns the default PackageObjectFactory with the default package names.
     * @return the default PackageObjectFactory.
     */
    public static PackageObjectFactory getPackageObjectFactory() throws CheckstyleException {
        final ClassLoader cl = TestUtil.class.getClassLoader();
        final Set<String> packageNames = PackageNamesLoader.getPackageNames(cl);
        return new PackageObjectFactory(packageNames, cl);
    }

    /**
     * Finds node of specified type among root children, siblings, siblings children
     * on any deep level.
     * @param root      DetailAST
     * @param predicate predicate
     * @return {@link Optional} of {@link DetailAST} node which matches the predicate.
     */
    public static Optional<DetailAST> findTokenInAstByPredicate(DetailAST root,
                                                                Predicate<DetailAST> predicate) {
        DetailAST curNode = root;
        while (!predicate.test(curNode)) {
            DetailAST toVisit = curNode.getFirstChild();
            while (curNode != null && toVisit == null) {
                toVisit = curNode.getNextSibling();
                if (toVisit == null) {
                    curNode = curNode.getParent();
                }
            }

            if (curNode == toVisit || curNode == root.getParent()) {
                curNode = null;
                break;
            }

            curNode = toVisit;
        }
        return Optional.ofNullable(curNode);
    }

    /**
     * <p>
     * Returns the JDK version as a number that is easy to compare.
     * </p>
     * <p>
     * For JDK "1.8" it will be 8; for JDK "11" it will be 11.
     * </p>
     *
     * @return JDK version as integer
     */
    public static int getJdkVersion() {
        String version = System.getProperty("java.specification.version");
        if (version.startsWith("1.")) {
            version = version.substring(2);
        }
        return Integer.parseInt(version);
    }

    /**
     * <p>
     * Adjusts the expected number of flushes for tests that call {@link OutputStream#close} method.
     * </p>
     * <p>
     * After <a href="https://bugs.openjdk.java.net/browse/JDK-8220477">JDK-8220477</a>
     * there is one additional flush from {@code sun.nio.cs.StreamEncoder#implClose}.
     * </p>
     *
     * @param flushCount flush count to adjust
     * @return adjusted flush count
     */
    public static int adjustFlushCountForOutputStreamClose(int flushCount) {
        int result = flushCount;
        if (getJdkVersion() >= 13) {
            ++result;
        }
        return result;
    }

}
