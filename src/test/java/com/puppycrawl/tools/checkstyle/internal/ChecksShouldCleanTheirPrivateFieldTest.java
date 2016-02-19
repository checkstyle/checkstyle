package com.puppycrawl.tools.checkstyle.internal;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;

import com.puppycrawl.tools.checkstyle.UserDefinedOption;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

import org.junit.Test;

public class ChecksShouldCleanTheirPrivateFieldTest {
    public static final String CHECKSTYLE_PACKAGE = "com.puppycrawl.tools.checkstyle";
    private static final String CHECK_FILE_NAME = ".+Check$";

    private static final List<String> CHECKS_ON_PAGE_IGNORE_LIST = Arrays.asList(
            "AbstractAccessControlNameCheck",
            "AbstractCheck",
            "AbstractClassCouplingCheck",
            "AbstractComplexityCheck",
            "AbstractFileSetCheck",
            "AbstractFormatCheck",
            "AbstractHeaderCheck",
            "AbstractIllegalCheck",
            "AbstractIllegalMethodCheck",
            "AbstractJavadocCheck",
            "AbstractNameCheck",
            "AbstractNestedDepthCheck",
            "AbstractOptionCheck",
            "AbstractParenPadCheck",
            "AbstractSuperCheck",
            "AbstractTypeAwareCheck",
            "AbstractTypeParameterNameCheck",
            "FileSetCheck"
    );

    @Test
    public void testAllChecksCleanTheyPrivateFields() throws Exception {
        ImmutableSet<ClassPath.ClassInfo> topLevelClassesRecursive =
                ClassPath.from(getClass().getClassLoader())
                        .getTopLevelClassesRecursive(CHECKSTYLE_PACKAGE);

        for (ClassPath.ClassInfo classInfo : topLevelClassesRecursive) {
            final Class cl = classInfo.load();
            final String className = classInfo.getSimpleName();
            if (className.matches(CHECK_FILE_NAME)
                    && classExtendsAbstractCheck(cl)
                    && !CHECKS_ON_PAGE_IGNORE_LIST.contains(className)) {
                List<String> classInstancePrivateFieldsName =
                        getClassInstanceFieldNames(cl);
                checkIfBeginTreeClearsFields(cl, classInstancePrivateFieldsName);
            }
        }
    }

    private boolean classExtendsAbstractCheck(Class cl) {
        boolean extendsAbstractCheck = false;
        if (cl != AbstractCheck.class && AbstractCheck.class.isAssignableFrom(cl)) {
            extendsAbstractCheck = true;
        }
        return extendsAbstractCheck;
    }

    private static List<String> getClassInstanceFieldNames(Class cl) {
        List<String> privateFieldNames = new LinkedList<>();
        for (Field field : cl.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())
                    && !field.isAnnotationPresent(UserDefinedOption.class)) {
                privateFieldNames.add(field.getName());
            }
        }
        return privateFieldNames;
    }

    private static void checkIfBeginTreeClearsFields(Class cl, List<String> fieldNames)
            throws IllegalAccessException, InvocationTargetException, InstantiationException,
            NoSuchFieldException, NoSuchMethodException {
        Object classInstance = cl.getConstructors()[0].newInstance();
        Map<String, Object> beforeBeginTreeFieldValues =
                getObjectFieldValues(classInstance, fieldNames);
        invokeBeginTreeOnObject(classInstance);
        Map<String, Object> afterBeginTreeFieldValues =
                getObjectFieldValues(classInstance, fieldNames);
        compareFieldValues(cl.getName(), beforeBeginTreeFieldValues, afterBeginTreeFieldValues);
    }

    private static Map<String, Object> getObjectFieldValues(Object object, List<String> fieldNames)
            throws NoSuchFieldException, IllegalAccessException {
        Map<String, Object> fieldValues = new HashMap<>();
        for (String fieldName : fieldNames) {
            Object fieldValue = getFieldValueFromObject(object, fieldName);
            fieldValues.put(fieldName, fieldValue);
        }
        return fieldValues;
    }

    private static void invokeBeginTreeOnObject(Object object)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method beginTree = object.getClass().getMethod("beginTree", DetailAST.class);
        DetailAST emptyDetailAst = new DetailAST();
        beginTree.invoke(object, emptyDetailAst);
    }

    private static void compareFieldValues(String className,
                                           Map<String, Object> beforeFieldValues,
                                           Map<String, Object> afterFieldValues) {
        for (String fieldName : beforeFieldValues.keySet()) {
            Object beforeValue = beforeFieldValues.get(fieldName);
            Object afterValue = afterFieldValues.get(fieldName);
            if ((beforeValue == null && afterValue == null)
                    || (beforeValue != null && beforeValue.equals(afterValue))) {
                System.err.println(className + " check doesnt clear " + fieldName + " on beginTree() method");
            }
        }
    }

    private static Object getFieldValueFromObject(Object classInstance, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = classInstance.getClass().getDeclaredField(fieldName);
        declaredField.setAccessible(true);
        return declaredField.get(classInstance);
    }
}
