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

public class InputVariableDeclarationUsageDistanceInitializationSequence2 {

    public void whileBlock() {
        List<Integer> list = new ArrayList<>();
        System.out.println(0);
        while (check()) {
            System.out.println(list.size());
        }

        List<Integer> list2 = new ArrayList<>();  // violation 'Distance .* is 2'
        nothing();
        while (check()) {
            System.out.println(list2.size());
        }
    }

    public void whileCondition() {
        List<Integer> list = new ArrayList<>();
        System.out.println(0);
        System.out.println(0);
        while (list.isEmpty()) {
            nothing();
        }

        List<Integer> list2 = new ArrayList<>();  // violation 'Distance .* is 2'
        nothing();
        System.out.println(0);
        while (list2.isEmpty()) {}
    }

    public void doWhileBlock() {
        List<Integer> list = new ArrayList<>();
        System.out.println(0);
        do {
            System.out.println(list.size());
        } while (check());

        List<Integer> list2 = new ArrayList<>();  // violation 'Distance .* is 2'
        nothing();
        do {
            System.out.println(list2.size());
        } while (check());
    }

    public void doWhileCondition() {
        List<Integer> list = new ArrayList<>();
        System.out.println(0);
        System.out.println(0);
        do {
            nothing();
        } while (list.isEmpty());

        List<Integer> list2 = new ArrayList<>();  // violation 'Distance .* is 2'
        nothing();
        System.out.println(0);
        do {
        } while (list2.isEmpty());
    }

    public void forBlock() {
        List<Integer> list = new ArrayList<>();
        System.out.println(0);
        for (; check(); ) {
            System.out.println(list.size());
        }

        List<Integer> list2 = new ArrayList<>();  // violation 'Distance .* is 2'
        nothing();
        for (; check(); ) {
            System.out.println(list2.size());
        }
    }

    public void forInitAndIterator() {
        List<Integer> list = new ArrayList<>();
        System.out.println(0);
        System.out.println(0);
        for (list.add(1); check(); list.add(1)) {}
        List<Integer> list2 = new ArrayList<>();  // violation 'Distance .* is 2'
        nothing();
        System.out.println(1);
        for (System.out.println(list2.size()); check(); System.out.println(list2.size())) {}
    }

    public void forEnhanced() {
        List<Integer> list = new ArrayList<>();
        System.out.println(0);
        System.out.println(0);
        for (Integer i : list) {}
        List<Integer> list2 = new ArrayList<>();  // violation 'Distance .* is 2'
        nothing();
        System.out.println(1);
        for (Integer i : list2.subList(0, 1)) {}
    }

    private void nothing() {
    }

    private boolean check() {
        return true;
    }
}
