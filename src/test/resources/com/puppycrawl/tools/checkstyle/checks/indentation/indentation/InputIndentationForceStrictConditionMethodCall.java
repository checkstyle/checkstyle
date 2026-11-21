/*
Indentation
basicOffset = (default)4
braceAdjustment = (default)0
caseIndent = (default)4
throwsIndent = 8
arrayInitIndent = (default)4
lineWrappingIndentation = 8
forceStrictCondition = true


*/

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;

import java.lang.reflect.Method;

public class InputIndentationForceStrictConditionMethodCall {

    public void validNestedCalls(Method method, Helper resources) throws Exception {
        Object packageIdentifiers =
                (Object) method.invoke(
                        resources.getAssets());

        Object withClosingParenOnNewLine =
                (Object) method.invoke(
                        resources.getAssets()
                );

        Object twoLevelsNested =
                method.invoke(
                        resources.wrap(
                                resources.getAssets()));

        int i;
        int j;
        for (i = 0,
                j = 1;
                i < j;
                i++) {
            method.invoke(resources);
        }
    }

    public void invalidNestedCalls(Method method, Helper resources) throws Exception {
        Object underIndentedArg =
                (Object) method.invoke(
                resources.getAssets());
        // violation above ''resources' has incorrect indentation level 16'

        Object overIndentedArg =
                (Object) method.invoke(
                                resources.getAssets());
        // violation above ''resources' has incorrect indentation level 32'

        Object wrongClosingParen =
                (Object) method.invoke(
                        resources.getAssets()
                        );
        // violation above '')' has incorrect indentation level 24'
    }

    interface Helper {
        Object getAssets();

        Object wrap(Object value);
    }
}
