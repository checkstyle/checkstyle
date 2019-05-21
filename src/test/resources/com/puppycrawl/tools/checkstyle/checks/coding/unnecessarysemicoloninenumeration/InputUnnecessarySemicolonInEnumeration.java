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
        A,B; // violation
    }
    enum CommaAndSemicolon {
        A,B,; // violation
    }
    enum BlockAndSemicolon {
        A,B{}; // violation
    }
    enum ParensAndSemicolon {
        A,B(); // violation
    }
    enum BlockAndCommaAndSemicolon {
        A,B{ public String toString() { return "";}},; // violation
    }
    enum ParensAndCommaAndSemicolon {
        A,B(),; // violation
    }
    enum All {
        A,B(){ public String toString() { return "";}},; // violation
    }
    enum SemicolonNextLine {
        A,B
        ; // violation
    }
    enum NestedEnum {
        A, B, C;
        enum Nested {
            First, Second, Third; // violation
        }
    }
    enum NoEnums {
        ; // violation
    }
    enum NoEnums2 {
        ;
        {}
    }
    enum EmptyEnum {
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
