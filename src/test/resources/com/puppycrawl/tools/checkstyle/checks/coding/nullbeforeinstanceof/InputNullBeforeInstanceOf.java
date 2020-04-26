package com.puppycrawl.tools.checkstyle.checks.coding.nullbeforeinstanceof;

public class InputNullBeforeInstanceOf {

    public void main(int[] obj) {
        int[] myObj = obj;
        Boolean a = myObj != null;

        if (myObj != null && myObj instanceof Object) { // violation

        }

        if (myObj != null) { // violation
            if (myObj instanceof Object) {

            }
        }

        if (myObj != null) { // violation
            if (myObj instanceof Object)
                System.out.println();
        }

        if (myObj instanceof Object) {
            if (myObj !=null) {
                // ...
            }
        }

        if (myObj instanceof Object) {
            if (myObj !=null)
                System.out.println();
        }

        if (a)
            System.out.println();

        if (a) {
            System.out.println();
        }

        if ((myObj != null) && (myObj instanceof Object)) {}  // violation

        if (myObj instanceof Object) {
            System.out.println();
            if (myObj !=null)
                System.out.println();
        }

        if (myObj instanceof Object) {

        }

        if (myObj != null && myObj == obj) {

        }

        if (myObj == obj && myObj instanceof Object) {

        }

        if (myObj != null) {
            if (myObj == obj)
                System.out.println();
        }

        if (myObj != obj) {
            if (myObj == obj)
                System.out.println();
        }

        if (myObj != null)
            if (myObj == obj)
                System.out.println();

        if (myObj != null) {
            if (a instanceof Object)
                System.out.println();
        }

        if (myObj != null) {
            if (a == true)
                System.out.println();
        }

        if (myObj != null) {
            System.out.println();
        }

        if (myObj != null)
            System.out.println();

        if ((myObj != null) && (a instanceof Object)) {

        }

    }
}
