/*
UnnecessaryNullCheckWithInstanceOf

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarynullcheckwithinstanceof;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Predicate;

public class InputUnnecessaryNullCheckWithInstanceOfLambda {
    private final List<Object> objects = new ArrayList<>();
    public void simpleLambdas() {
        objects.forEach(obj -> {
            if (obj != null && obj instanceof String) { // violation, 'Unnecessary nullity check'
                String str = (String) obj;
            }
        });

        // violation below, 'Unnecessary nullity check'
        Predicate<Object> isString = obj -> obj != null && obj instanceof String;

        // violation below, 'Unnecessary nullity check'
        objects.removeIf(obj -> obj != null && obj instanceof Integer);
    }
    public void validLambdas() {
        objects.forEach(obj -> {
            if (obj instanceof String) {
                String str = (String) obj;
            }
        });
        objects.stream()
               .filter(Objects::nonNull)
               .filter(obj -> obj instanceof String)
               .forEach(obj -> {
                   String str = (String) obj;
               });
    }
}
