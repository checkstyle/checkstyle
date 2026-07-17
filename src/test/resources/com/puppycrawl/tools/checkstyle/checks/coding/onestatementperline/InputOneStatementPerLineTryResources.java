/*
OneStatementPerLine
treatTryResourcesAsStatement = true

*/
package com.puppycrawl.tools.checkstyle.checks.coding.onestatementperline;

public class InputOneStatementPerLineTryResources {

    static class Res implements AutoCloseable {
        String name;
        Res(String name) {
            this.name = name;
            System.out.println("Open " + name);
        }
        @Override public void close() {
            System.out.println("Close " + name);
        }
    }

    public static void main(String[] args) {
        try (Res r1 = new Res("r1"); Res r2 = new Res("r2")) {
        }
        // violation 2 lines above 'Only one statement per line allowed.'

        try (Res r1 = new Res("r1"); Res r2 = new Res("r2"); Res r3 = new Res("r3")) {
        // 2 violations above
        // 'Only one statement per line allowed.'
        // 'Only one statement per line allowed.'
        }
    }
}
