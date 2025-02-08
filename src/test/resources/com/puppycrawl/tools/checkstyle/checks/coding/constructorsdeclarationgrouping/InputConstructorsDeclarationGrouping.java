/*
ConstructorsDeclarationGrouping


*/

package com.puppycrawl.tools.checkstyle.checks.coding.constructorsdeclarationgrouping;

public class InputConstructorsDeclarationGrouping {

    int a;

    int b;

    void foo() {}

    InputConstructorsDeclarationGrouping() {}

    InputConstructorsDeclarationGrouping(String a) {}

    void foo2() {}

    InputConstructorsDeclarationGrouping(int a) {}
    // violation above 'Constructors should be grouped together.*'

    int abc;

    InputConstructorsDeclarationGrouping(double x) {}
    // violation above 'Constructors should be grouped together.*'

    private enum InnerEnum1 {

        one;

        int x;

        InnerEnum1() {}

        InnerEnum1(String f) {}

        String str;

        String str2;

        InnerEnum1(int x) {}
        // violation above 'Constructors should be grouped together.*'

        private abstract class Inner {
            Inner() {}

            int x;

            String neko;

            Inner(String g) {}
            // violation above 'Constructors should be grouped together.*'
        }

        InnerEnum1(double d) {}
        // violation above 'Constructors should be grouped together.*'
    }

    InputConstructorsDeclarationGrouping(float x) {}
    // violation above 'Constructors should be grouped together.*'

    InputConstructorsDeclarationGrouping(long l) {}
    // violation above 'Constructors should be grouped together.*'

}
