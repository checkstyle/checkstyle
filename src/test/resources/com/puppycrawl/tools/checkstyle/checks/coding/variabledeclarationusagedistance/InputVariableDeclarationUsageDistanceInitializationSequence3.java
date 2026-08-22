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

public class InputVariableDeclarationUsageDistanceInitializationSequence3 {
    public void codeBlock() {
        List<Integer> list = new ArrayList<>();
        System.out.println(0);
        {
            System.out.println(list.size());
        }

        List<Integer> list2 = new ArrayList<>();  // violation 'Distance .* is 2'
        nothing();
        {
            System.out.println(list2.size());
        }
    }

    public void synchronizedBlock() {
        List<Integer> list = new ArrayList<>();
        System.out.println(0);
        synchronized (this) {
            System.out.println(list.size());
        }

        List<Integer> list2 = new ArrayList<>();  // violation 'Distance .* is 3'
        nothing();
        synchronized (this) {
            System.out.println(list2.size());
        }
    }

    public void synchronizedObject() {
        List<Integer> list = new ArrayList<>();
        int i = 0;
        synchronized (list.subList(i, 1)) {
        }

        List<Integer> list2 = new ArrayList<>();  // violation 'Distance .* is 2'
        nothing();
        synchronized (list2.subList(0, 1)) {
        }
    }

    public void mixedNested1() {
        List<Integer> list = new ArrayList<>();
        class AClass extends Parent {
            @Override
            void method() {
                if (true) {
                    list.add(0);
                }
            }
        }
    }

    private void mixedNested2() {
        List<Integer> list = new ArrayList<>(); // violation 'Distance .* is 2'

        if (true) {
            int a = 3;
            while (check()) {
                int b = 2;
                nothing();
                {
                    list.add(2);
                }
            }
        }
    }

    private class Parent {
        void method() {
        }
    }

    private void nothing() {
    }

    private class Close implements AutoCloseable {
        public Close(int i) {
        }

        @Override
        public void close() throws Exception {
        }
    }

    private boolean check() {
        return true;
    }

    private AutoCloseable getAutoCloseable(int i) {
        return new Close(i);
    }
}
