/*
VariableDeclarationUsageDistance
allowedDistance = (default)3
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class InputVariableDeclarationUsageDistanceInitializationReturn {

    static class MyNode {
    }

    static class MatchesFunction {
        Object call(Context c, List<Object> list) {
            return null;
        }
    }

    static class Attribute {
        Attribute(MyNode node, String name, Method method) {
        }
    }

    static class Context {
        Context(Object ignored) {
        }

        void setNodeSet(List<?> list) {
        }
    }

    static class FunctionCallException extends Exception {
    }

    private Object tryRegexp(MyNode myNode, String exp)
        throws FunctionCallException, NoSuchMethodException {
         // violation below 'Distance .* is 5.'
        MatchesFunction function = new MatchesFunction();
        List<Object> list = new ArrayList<>();
        List<Attribute> attrs = new ArrayList<>();
        attrs.add(new Attribute(myNode, "matches",
            myNode.getClass().getMethod("getClassName", new Class[0])));
        list.add(attrs);
        list.add(exp);
        Context c = new Context(null);
        c.setNodeSet(new ArrayList<>());
        return function.call(c, list);
    }
}
