package com.puppycrawl.tools.checkstyle.grammars;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiDimensionalArraysInGenericsTestInput {

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