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

public class InputVariableDeclarationUsageDistanceInitializationSequence5 {
    public void innerClassMethod() {
        List<Integer> list = new ArrayList<>();
        System.out.println(0);
        class AClass extends Parent {
            @Override
            void method() {
                System.out.println(list.size());
            }
        }

        List<Integer> list2 = new ArrayList<>(); // violation 'Distance .* is 2'
        nothing();
        class BClass extends Parent {
            @Override
            void method() {
                System.out.println(list2.size());
            }
        }
    }

    public void innerClassField() {
        List<Integer> list = new ArrayList<>();
        Integer.valueOf(0);
        class AClass extends Parent {
            private int i = Integer.valueOf(list.size());
        }

        List<Integer> list2 = new ArrayList<>(); // violation 'Distance .* is 2'
        nothing();
        class BClass extends Parent {
            private int i = list2.size();
        }
    }

    public void innerClassInitializer() {
        List<Integer> list = new ArrayList<>();
        System.out.println(0);
        class AClass extends Parent {
            {
                System.out.println(list.size());
            }
        }

        List<Integer> list2 = new ArrayList<>(); // violation 'Distance .* is 3'
        nothing();
        System.out.println(1);
        class BClass extends Parent {
            {
                System.out.println(list2.size());
            }
        }
    }

    public void anonymousClassMethod() {
        List<Integer> list = new ArrayList<>();
        System.out.println(0);
        Parent aClass = new Parent() {
            @Override
            void method() {
                System.out.println(list.size());
            }
        };

        List<Integer> list2 = new ArrayList<>();  // violation 'Distance .* is 2'
        nothing();
        Parent bClass = new Parent() {
            @Override
            void method() {
               System.out.println(list2.size());
            }
        };
    }

    private class Parent {
        void method() {
        }
    }

    private void nothing() {
    }
}
