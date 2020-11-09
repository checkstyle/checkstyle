package com.puppycrawl.tools.checkstyle.checks.coding.nulltestaroundinstanceof;

/* Config: default
*
*/
public class InputNullTestAroundInstanceOf {

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

        if (myObj != null) // violation
            if (myObj instanceof Object)
                System.out.println();

        if (myObj instanceof Object) { // violation
            if (myObj !=null) {
                // ...
            }
        }

        if (myObj instanceof Object) { // violation
            if (myObj !=null)
                System.out.println();
        }

        if (a)
            System.out.println();

        if (a) {
            System.out.println();
        }

        if ((myObj != null) && (myObj instanceof Object)) {}  // violation

        if (myObj instanceof Object) { // violation
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

        if (myObj != obj && myObj instanceof Object) {

        }

        if (myObj instanceof Object && myObj != null) { // violation

        }

        if (myObj instanceof Object) // violation
            if (myObj != null)
                System.out.println();

        if (myObj instanceof Object) {
            if (myObj == obj)
                System.out.println();
        }

        if (myObj instanceof Object)
            if (myObj == obj)
                System.out.println();

        if (a instanceof Object) {
            if (myObj != null)
                System.out.println();
        }

        if (myObj instanceof Object) {
            if (a == true)
                System.out.println();
        }

    }

    class Node {
        int value = 0;
        Node next = null;
        Node prev = null;
    }

    public void foo3() {
        Node node = new Node();
        if (node.next != null && node.next instanceof Object) { // violation

        }

        if (node.next instanceof Object && node.next != null) { // violation

        }

        if (node.next instanceof Object && node.prev != null) {

        }

        if (node instanceof Object && node.prev != null) {

        }

        if (node.next instanceof Object && node != null) {

        }

        if (node.next.next != null && node.next.prev instanceof Object) {

        }

        if (node.next.next != null && node.next.next instanceof Object) { // violation

        }

        if (node.next != null) { // violation
            if (node.next instanceof Object) {

            }
        }

        if (node.next != null)  // violation
            if (node.next instanceof Object) {

            }


        if (node.prev != null) {
            if (node.next instanceof Object) {

            }
        }

        if (node.prev == null) {
            if (node.next instanceof Object) {

            }
        }

        if (node.prev != null) {
            if (node.next == node) {

            }
        }

        if (node.next instanceof Object) { // violation
            if (node.next != null) {

            }
        }

        if (node.next instanceof Object)  // violation
            if (node.next != null) {

            }

        if (node.prev instanceof Object) {
            if (node.next != null) {

            }
        }

        if (node.next instanceof Object) {
            if (node.next == null) {

            }
        }

        if (node.next instanceof Object) {
            if (node.next != node.prev) {

            }
        }

        if (node.next == node) {
            if (node.next != null) {

            }
        }

        if (node.next.next != null) { // violation
            if (node.next.next instanceof Object) {

            }
        }

        if (node.next.next instanceof Object) { // violation
            if (node.next.next != null) {

            }
        }

    }

}
