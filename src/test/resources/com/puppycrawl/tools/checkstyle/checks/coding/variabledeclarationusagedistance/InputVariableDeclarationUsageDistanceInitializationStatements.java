/*
VariableDeclarationUsageDistance
allowedDistance = (default)3
ignoreVariablePattern = (default)
validateBetweenScopes = (default)false
ignoreFinal = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

public class InputVariableDeclarationUsageDistanceInitializationStatements {
    class Test {
        public Test test(Object a) {
            Test obj = test("abc");
            int var = 12; // violation 'Dist.* between variable 'var'.* and its first usage is 4'
            obj.test("abc");
            obj.test("abc");
            obj.test("abc");
            int k = var + 3;
            return this;
        }

        void test() {
            Test obj = test("abc");
            int var = 12; // ok
            int ar2 = 12; // violation 'Dist.* between variable 'ar2' .* and its first usage is 5'
            obj.test("abc");
            obj.test("abc");
            obj.test("abc");
            obj.test(var);
            new Test().test(ar2);
        }

        void testChained() {
            int pc = 0; // ok
            test("abc").test("abc");
            test("abc").test("abc");
            test("abc").test("abc");
            test("abc").test("abc");
            test("abc").test("abc");
            test("abc").test("abc");
            test(pc).test("abc");
        }

        void testMixedBranches() {
            int abc = 12; // violation 'Dist.* between variable 'abc'.* and its first usage is 6'
            Test test = new Test();
            test.test("abc");
            test("abc").test("abc");
            test("abc").test("abc");
            test("abc").test("abc");
            test.test("abc");
            test("abc").test(abc);
        }

        void testMixedBranches2() {
            int c = 12; // ok
            Test test = new Test();
            test.test("abc");
            test.test(c);
        }

        void testTypecast() {
            int c = 12; // ok
            Test test = new Test();
            test.test("abc");
            ((subclass)test).test("abc");
            test.test("abc");
            ((subclass)test).test("abc");
            test.test(c);
        }

        void testPriorToDeclaration() {
            Test test2 = new Test();
            test2.test("abc");
            test2.test("abc");
            Test test = new Test();
            int c = 12;
            test.test("abc");
            test.test("abc");
            test.test("abc");
            test.test(c);
        }

        void testAlternating() {
            Test test = new Test();
            int c = 13; // violation 'Dist.* between variable 'c'.* and its first usage is 4'
            test.test("abc");
            test("abc").test("abc");
            test.test("abc");
            test.test(c);
        }

        void testAnotherVariableDeclarationInBetween() {
            Test test = new Test();
            int a = 12;
            test.test("abc");
            test.test("abc");
            int b = 13;
            test.test(b);
            test.test(b);
            int c = 12;
            test.test(a + c);
        }

        class subclass extends Test {
        }
    }
}
