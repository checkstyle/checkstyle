/*
ConstructorsDeclarationGrouping
orderByIncreasingArity = true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.constructorsdeclarationgrouping;

public class InputConstructorsDeclarationGroupingRecord {
    public record MyRecord(int a, int b) {

        public MyRecord(int a) {
            this(a, a);
        }

        public MyRecord(int a, int b, int c, int d) {
            this(a+b, c+d);
        }

        public MyRecord(int a, int b, int c) {
            this(a + b, c); // violation above 'Constructors should be ordered by increasing arity.'
        }
    }

    static class Inner {
        public Inner(int a, int b) {
        }

        public Inner(int a) {
            this(a, a); // violation above 'Constructors should be ordered by increasing arity.'
        }

        public Inner(int a, int b, int c, int d) {
            this(a+b, c+d); // violation above 'Constructors should be ordered by increasing arity.'
        }

        public Inner(int a, int b, int c) {
            this(a + b, c); // violation above 'Constructors should be ordered by increasing arity.'
        }
    }
}
