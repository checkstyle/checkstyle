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
            int var = 12; // violation
            obj.test("abc");
            obj.test("abc");
            obj.test("abc");
            int k = var + 3;
            return this;
        }

        void test() {
            Test obj = test("abc");
            int var = 12; // ok
            int var2 = 12; // violation
            obj.test("abc");
            obj.test("abc");
            obj.test("abc");
            obj.test(var);
            new Test().test(var2);
        }

        void testNew() {
            int var = 12; // violation
            new Test().test("abc");
            new Test().test("abc");
            new Test().test("abc");
            new Test().test(var);
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
            int abc = 12; // violation
            Test test = new Test();
            test.test("abc");
            test("abc").test("abc");
            test("abc").test("abc");
            test("abc").test("abc");
            test.test("abc");
            test("abc").test(abc);
        }

        void testMixedBranches2() {
            int c = 12;
            Test test = new Test();
            test.test("abc");
            test.test(c);
        }
    }
}
