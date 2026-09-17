/*
JavadocVariable
accessModifiers = (default)public,protected,package,private
ignoreNamePattern = (default)null
tokens = (default)VARIABLE_DEF, ENUM_CONSTANT_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocvariable;


class InputJavadocVariableOddCases {

    // violation below 'Missing a Javadoc comment'
    @Deprecated
    /** javadoc tool does not consider this as javadoc comment, so checkstyle does the same */
    int field22;

    /** Javadoc */
    private static enum PathStatus {
        INVALID, CHECKED }; // 2 violations

    // the following is taken
    // from openjdk25/src/java.base/share/classes/jdk/internal/vm/Continuation.java

    public enum Pinned {
        /** Native frame on stack */ NATIVE,
        /** Monitor held */ MONITOR,
        /** In critical section */ CRITICAL_SECTION,
        /** Exception (OOME/SOE) */ EXCEPTION
    }

    /** Preemption attempt result */
    public enum PreemptStatus {
        /** Success */                                                      SUCCESS(null),
        /** Permanent failure */                                            PERM_FAIL_U(null),
        /** Permanent failure: continuation already yielding */             PERM_FAIL_Y(null),
        /** Permanent failure: continuation not mounted on the thread */    PERM_FAIL_N(null),
        /** Transient failure: due to a held CS */      CRITICAL_SECTION(Pinned.CRITICAL_SECTION),
        /** Transient failure: due to native frame */   NATIVE(Pinned.NATIVE),
        /** Transient failure: due to a held monitor */ MONITOR(Pinned.MONITOR);

        final Pinned pinned; // violation 'Missing a Javadoc comment'
        private PreemptStatus(Pinned reason) { this.pinned = reason; }
        /**
         * Whether or not the continuation is pinned.
         * @return whether or not the continuation is pinned
         **/
         public Pinned pinned() { return pinned; }
    }

}
