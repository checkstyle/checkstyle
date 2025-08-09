/*
VariableDeclarationUsageDistance
allowedDistance = 1
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

public class InputVariableDeclarationUsageDistanceLocalType {

    public void testWithLocalClass() {
        int a = 1; // no violation expected

        class LocalHelper {
            void help() {
                System.out.println("working");
            }
        }

        System.out.println(a); // usage is after local class
    }

    public void testWithAnonymousBlock() {
        int b = 2; // violation 'Distance .* is 2.'

        {
            System.out.println("inside block");
        }

        System.out.println(b); // usage after anonymous block (should count for distance)
    }

    public void testWithMultipleLocalTypes() {
        int c = 3; // no violation expected

        class First {}
        class Second {}

        System.out.println(c); // usage after multiple local classes
    }
}

