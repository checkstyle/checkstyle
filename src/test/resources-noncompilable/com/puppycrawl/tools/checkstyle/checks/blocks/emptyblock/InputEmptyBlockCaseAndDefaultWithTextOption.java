/*
EmptyBlock
option = text
tokens = LITERAL_DEFAULT, LITERAL_CASE


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.blocks.emptyblock;

public class InputEmptyBlockCaseAndDefaultWithTextOption {

    void testWithEmptyBlocks(Object obj) {
        switch (obj) {
            case Integer i: {
                System.out.println("Integer");
            }
            // violation below, 'Empty case block'
            case String _: {

            }
            // violation below, 'Empty default block'
            default: {
            }
        }

        switch (obj) {
            case Integer i -> {
                System.out.println("Integer");
            }
            // violation below, 'Empty case block'
            case String s -> {

            }
            // violation below, 'Empty default block'
            default -> {

            }
        }

        switch (obj) {
            case Integer i: {
                System.out.println("Integer");
            }
            case String _: {
                System.out.println("String");
            }
            default: {
                System.out.println("defuault");
            }
        }

        switch (obj) {
            case Integer i -> {
                System.out.println("Integer");
            }
            case String s -> {
                System.out.println("String");
            }
            default -> {
                System.out.println("defuault");
            }
        }
    }


    void testWithTextInsideBlocks(Object obj) {
        switch (obj) {
            case Integer i: {
                System.out.println("Integer");
            }
            case String _: {  // ok, has text inside
                // no code by default
            }
            default: { // ok, has text inside
                // no code by default
            }
        }

        switch (obj) {
            case Integer i -> {
                System.out.println("Integer");
            }
            case String s -> { // ok, has text inside
                // no code by default
            }
            default -> { // ok, has text inside
                // no code by default
            }
        }
    }

}
