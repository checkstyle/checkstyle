/*
OpenjdkMethodParameterAlignment
tokens = (default)METHOD_DEF, CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.openjdkmethodparameteralignment;

import java.util.List;
import java.util.Map;

public class InputOpenjdkMethodParameterAlignmentParameterTypes {

    void annotatedParameters(@Deprecated int a,
                             @Deprecated int b, int c) {
        // violation above 'Only one parameter is allowed per line in a vertical list.'
    }

    void annotationOnItsOwnLine(@Deprecated
                                int a,
                                int b, int c) {
        // violation above 'Only one parameter is allowed per line in a vertical list.'
    }

    void annotationWithArrayArgument(@Names({"a", "b"}) int a,
                                     int b, int c) {
        // violation above 'Only one parameter is allowed per line in a vertical list.'
    }

    @Deprecated
    void annotationOnMethod(int a,
                            int b, int c) {
        // violation above 'Only one parameter is allowed per line in a vertical list.'
    }

    void finalAndGenericParameters(final Map<String, List<Integer>> a,
                                   final List<int[]> b, final int c) {
        // violation above 'Only one parameter is allowed per line in a vertical list.'
    }

    void varargs(int a,
                 int b, int... rest) {
        // violation above 'Only one parameter is allowed per line in a vertical list.'
    }

    void arrayDeclarators(int a[],
                          int b[], int c) {
        // violation above 'Only one parameter is allowed per line in a vertical list.'
    }

    <T> void typeParameters(T a,
                            T b, T c) {
        // violation above 'Only one parameter is allowed per line in a vertical list.'
    }

    void commentBetweenParameters(int a,
                                  // an explanation of b
                                  int b,
                                  int c) {
    }

    void parameterTypeSpanningLines(Map<String,
                                        String> a, int b) {
    }

    void qualifiedTypeName(java.util.List<Integer> a,
                           java.util.Map<String, String> b, int c) {
        // violation above 'Only one parameter is allowed per line in a vertical list.'
    }

    void qualifiedTypeOnFirstParameter(java.util.List<Integer> a,
                                       int b, java.util.Map<String, String> c) {
        // violation above 'Only one parameter is allowed per line in a vertical list.'
    }

    void lambdaParametersAreIgnored() {
        final Merger merger = (a,
                               b, c) -> a;
    }

    interface WithoutBody {

        void abstractMethod(int a,
                            int b, int c);
        // violation above 'Only one parameter is allowed per line in a vertical list.'
    }

    interface Merger {

        int merge(int a, int b, int c);
    }

    @interface Names {

        String[] value();
    }
}
