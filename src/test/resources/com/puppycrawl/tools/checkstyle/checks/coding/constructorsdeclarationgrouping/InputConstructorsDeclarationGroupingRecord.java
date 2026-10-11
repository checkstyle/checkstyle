/*
ConstructorsDeclarationGrouping
orderByIncreasingParameterCount = true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.constructorsdeclarationgrouping;

public class InputConstructorsDeclarationGroupingRecord {
    public record MyRecord(int a, int b) {

        public MyRecord {
        }

        public MyRecord(int a) {
            this(a, a);
        }

        public MyRecord(int a, int b, int c, int d) {
            this(a+b, c+d);
        }

        public MyRecord(int a, int b, int c) {
            this(a + b, c);
        }// violation 2 lines above 'Constructors should be ordered by increasing parameter count.'
    }

    static class Inner {
        public Inner(int a, int b) {
        }

        public Inner(int a) {
            this(a, a);
        }// violation 2 lines above 'Constructors should be ordered by increasing parameter count.'

        public Inner(int a, int b, int c, int d) {
            this(a+b, c+d);
        }// violation 2 lines above 'Constructors should be ordered by increasing parameter count.'

        public Inner(int a, int b, int c) {
            this(a + b, c);
        }// violation 2 lines above 'Constructors should be ordered by increasing parameter count.'
    }
}
