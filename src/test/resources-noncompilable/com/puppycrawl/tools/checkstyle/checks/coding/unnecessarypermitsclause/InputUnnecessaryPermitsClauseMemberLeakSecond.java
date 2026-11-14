/*
UnnecessaryPermitsClause

*/

// non-compiled with javac: reference to non existent type, declared in another file
package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarypermitsclause;

public class InputUnnecessaryPermitsClauseMemberLeakSecond {

    static class Holder {
        static sealed class SealedInHolder permits LeakedMemberSub {
        }
    }
}
