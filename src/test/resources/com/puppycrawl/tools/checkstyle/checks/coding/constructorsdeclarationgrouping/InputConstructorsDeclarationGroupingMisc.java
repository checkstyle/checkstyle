/*
ConstructorsDeclarationGrouping
orderByIncreasingArity = true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.constructorsdeclarationgrouping;

public class InputConstructorsDeclarationGroupingMisc {

    public InputConstructorsDeclarationGroupingMisc() {
    }

    public InputConstructorsDeclarationGroupingMisc(int a, int b) {
    }

    int a;

    public InputConstructorsDeclarationGroupingMisc(int a) {
    } // violation above 'Constructors should be grouped together.*'
    // violation 2 lines above 'Constructors should be ordered by increasing arity.'

    public record MyRecord(int a, int b) {
        public MyRecord(int a) {
            this(a, a);
        }
        int d;

        public MyRecord(int a, int b, int c, int d) {
            this(a + b, c + d);
        } // violation 2 lines above 'Constructors should be grouped together.*'
        int c;

        public MyRecord(int a, int b, int c) {
            this(a + b, c); // violation above 'Constructors should be grouped together.*'
        } // violation 2 lines above 'Constructors should be ordered by increasing arity.'
    }
}
