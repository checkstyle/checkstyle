/*
RedundantThis
checkMethods=(default)false
allowAdjacentToRequiredThis=(default)true

*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

public class InputRedundantThisForLoop {
    private int count;
    private String item;

    public void example(String[] items) {
        for (String item : items) {
            System.out.println(item);
        }
        // violation below 'Redundant "this", field 'item' can be accessed directly.'
        this.item = "default";

        for (int count = 0; count < 10; count++) {
            System.out.println(count);
        }
        // violation below 'Redundant "this", field 'count' can be accessed directly.'
        this.count = 0;

        for (String item : items)
            System.out.println(item);
        // violation below 'Redundant "this", field 'item' can be accessed directly.'
        this.item = "default";

        for (String item : items) {
            this.item = item;
        }

        for (int i = 0, count = 0; i < 10; i++) {
            System.out.println(i + count);
        }
        // violation below 'Redundant "this", field 'count' can be accessed directly.'
        this.count = 0;

        for (int i = 0; i < 10; i++) {
            for (String item : items) {
                System.out.println(item);
            }
            // violation below 'Redundant "this", field 'item' can be accessed directly.'
            this.item = "inside outer";
        }
    }

    public void tryWithResourcesScoping() throws Exception {
        try (java.io.InputStream item = null) {
            item.read();
        }
        catch (Exception e) {
            // violation below 'Redundant "this", field 'item' can be accessed directly.'
            this.item = "catch";
        }
        finally {
            // violation below 'Redundant "this", field 'item' can be accessed directly.'
            this.item = "finally";
        }
    }

    public void lambdaSingleUntypedParam() {
        java.util.function.Consumer<Integer> c1 = count -> this.count = 0;
    }

    public void lambdaSingleTypedParam() {
        java.util.function.Consumer<Integer> c2 = (count) -> this.count = 0;
    }

    public void lambdaMultipleParams() {
        java.util.function.BiConsumer<Integer, String> d = (count, item) -> {
            this.count = 0;
            this.item = "test";
        };
    }

    public void lambdaUnshadowed() {
        java.util.function.Consumer<Integer> c3 = x -> {
            // violation below 'Redundant "this", field 'count' can be accessed directly.'
            this.count = 0;
        };
    }

    public void switchRule(int x) {
        switch (x) {
            case 1 -> {
                // violation below 'Redundant "this", field 'count' can be accessed directly.'
                this.count = 0;
            }
            default -> {
            }
        }
    }
}
