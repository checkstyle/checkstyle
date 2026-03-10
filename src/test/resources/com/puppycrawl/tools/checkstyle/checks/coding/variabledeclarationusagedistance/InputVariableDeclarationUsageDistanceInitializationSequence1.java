/*
VariableDeclarationUsageDistance
allowedDistance = 1
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

import java.util.ArrayList;
import java.util.List;

public class InputVariableDeclarationUsageDistanceInitializationSequence1 {

    public void ifBlock() {
        List<Integer> list = new ArrayList<>();
        System.out.println(0);
        if (check()) {
            System.out.println(list.size());
        }

        List<Integer> list2 = new ArrayList<>();  // violation 'Distance .* is 2'
        nothing();
        if (check()) {
            System.out.println(list2.size());
        }
    }

    public void elseBlock() {
        List<Integer> list = new ArrayList<>();
        System.out.println(0);
        if (check()) {
        } else {
            System.out.println(list.size());
        }

        List<Integer> list2 = new ArrayList<>();  // violation 'Distance .* is 2'
        nothing();
        if (check()) {
        } else {
            System.out.println(list2.size());
        }
    }

    public void ifCondition() {
        List<Integer> list = new ArrayList<>();
        System.out.println(0);
        System.out.println(0);
        if (System.out.equals(list)) {
        }

        List<Integer> list2 = new ArrayList<>();  // violation 'Distance .* is 2'
        nothing();
        System.out.println(0);
        if (System.out.equals(list2)) {
        }
    }

    public void switchCase() {
        List<Integer> list = new ArrayList<>();
        System.out.println(0);
        switch (1 + 1) {
        case 2: nothing(); break;
        case 3: System.out.println(list.size()); break;
        }
        List<Integer> list2 = new ArrayList<>();  // violation 'Distance .* is 2'
        nothing();
        switch (1 + 1) {
        case 2: System.out.println(list2.size()); break;
        }
    }

    public void switchDefault() {
        List<Integer> list = new ArrayList<>();
        System.out.println(0);
        switch (1 + 1) {
        case 2: nothing(); break;
        default: System.out.println(list.size()); break;
        }
        List<Integer> list2 = new ArrayList<>();  // violation 'Distance .* is 2'
        nothing();
        switch (1 + 1) {
        default: System.out.println(list2.size()); break;
        }
    }

    public void switchCondition() {
        List<Integer> list = new ArrayList<>();
        System.out.println(0);
        System.out.println(0);
        switch (list.size()) {
        default: break;
        }
        List<Integer> list2 = new ArrayList<>();  // violation 'Distance .* is 2'
        nothing();
        System.out.println(0);
        switch (list2.size()) {
        default: break;
        }
    }

    private void nothing() {
    }

    private boolean check() {
        return true;
    }
}
