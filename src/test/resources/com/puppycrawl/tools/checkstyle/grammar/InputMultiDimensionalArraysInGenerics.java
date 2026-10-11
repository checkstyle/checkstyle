/*
com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

package com.puppycrawl.tools.checkstyle.grammar;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.WildcardType;
import java.util.List;

public class InputMultiDimensionalArraysInGenerics { // ok

    @SuppressWarnings("unused")
    void withUpperBound(List<? extends int[][]> list) {}

    @SuppressWarnings("unused")
    void withLowerBound(List<? super String[][]> list) {}

    @SuppressWarnings("unused")
    void withLowerBound2(List<? super String[][][]> list) {}

    static WildcardType getWildcardType(String methodName) throws Exception {
      ParameterizedType parameterType = (ParameterizedType)
          WildcardType.class
              .getDeclaredMethod(methodName, List.class)
              .getGenericParameterTypes()[0];
      return (WildcardType) parameterType.getActualTypeArguments()[0];
    }

}
