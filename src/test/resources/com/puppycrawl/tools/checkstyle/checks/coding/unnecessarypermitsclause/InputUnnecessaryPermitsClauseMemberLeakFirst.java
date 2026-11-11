/*
UnnecessaryPermitsClause

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarypermitsclause;

public class InputUnnecessaryPermitsClauseMemberLeakFirst {

    static class Holder {
        // violation below 'Unnecessary 'permits' clause for classes in the same compilation unit.'
        static sealed class SealedInHolder permits LeakedMemberSub {
        }

        static final class LeakedMemberSub extends SealedInHolder {
        }
    }
}
