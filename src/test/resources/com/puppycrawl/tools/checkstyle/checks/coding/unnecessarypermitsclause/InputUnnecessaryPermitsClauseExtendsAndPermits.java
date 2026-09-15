/*
UnnecessaryPermitsClause

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarypermitsclause;

public class InputUnnecessaryPermitsClauseExtendsAndPermits {

    // violation below 'Unnecessary 'permits' clause for classes in the same compilation unit.'
    sealed class Parent permits Base {
    }

    // violation below 'Unnecessary 'permits' clause for classes in the same compilation unit.'
    sealed class Base extends Parent permits ChildA, ChildB {
    }

    final class ChildA extends Base {
    }

    final class ChildB extends Base {
    }
}
