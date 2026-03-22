/*
UnnecessarySemicolonInEnumeration


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarysemicoloninenumeration;

public class InputUnnecessarySemicolonInEnumeration{

    enum Nothing {
        A,B
    }
    enum Comma {
        A,B,
    }
    enum Paren {
        A,B()
    }
    enum Block {
        A,B{}
    }
    enum ParenAndBlock {
        A,B(){ public String toString() { return "";}}
    }
    enum ParenAndBlockAndComma {
        A,B(){ public String toString() { return "";}},
    }
    enum Semicolon {
        A,B; // violation 'Unnecessary semicolon'
    }
    enum CommaAndSemicolon {
        A,B,; // violation 'Unnecessary semicolon'
    }
    enum BlockAndSemicolon {
        A,B{}; // violation 'Unnecessary semicolon'
    }
    enum ParensAndSemicolon {
        A,B(); // violation 'Unnecessary semicolon'
    }
    enum BlockAndCommaAndSemicolon {
        A,B{ public String toString() { return "";}},; // violation 'Unnecessary semicolon'
    }
    enum ParensAndCommaAndSemicolon {
        A,B(),; // violation 'Unnecessary semicolon'
    }
    enum All {
        A,B(){ public String toString() { return "";}},; // violation 'Unnecessary semicolon'
    }
    enum SemicolonNextLine {
        A,B
        ; // violation 'Unnecessary semicolon'
    }
    enum NestedEnum {
        A, B, C;
        enum Nested {
            First, Second, Third; // violation 'Unnecessary semicolon'
        }
    }
    enum NoEnums {
        ; // violation 'Unnecessary semicolon'
    }
    enum NoEnums2 {
        ,; // violation 'Unnecessary semicolon'
    }
    enum NoEnums3 {
        ;
        {}
    }
    enum EmptyEnum {
    }
    enum EmptyEnum2 {
        ,
    }
    enum Normal {
        A,B;
        void m(){}
    }
    enum CommaNormal {
        A,B,;
        {}
    }
    enum ParenNormal {
        A,B();
        static {}
    }
    enum SemiNextLine {
        A,
        B
        ;
        SemiNextLine(){}
    }
    enum BlockNormal {
        A,B{ public String toString() { return "";}};
        BlockNormal(){}
    }
    enum ParenAndBlockNormal {
        A,B(){ public String toString() { return "";}};
        int a = 10;
    }
    enum ParenAndBlockAndCommaNormal {
        A,B(){ public String toString() { return "";}},;
        interface a {}
    }
}
