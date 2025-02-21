/*
NoWhitespaceAfter
allowLineBreaks = false
tokens = (default)ARRAY_INIT, AT, INC, DEC, UNARY_MINUS, UNARY_PLUS, BNOT, LNOT, \
         DOT, ARRAY_DECLARATOR, INDEX_OP


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter; // ^ 2 violations above

public class InputNoWhitespaceAfterTestAssignment {

    Object o;
    static boolean b = true;

    void some() {
        Object oo = new Object[4];
        Object[] oo2 = new Object[4];
        this.o = ((Object[]) oo)[1];
        this.o = ((java.lang.Object[]) oo)[1];
        this.o = oo2[1];
        QualifiedAssignment.o1 = ((Object[]) oo)[1];
        QualifiedAssignment.o1 = ((java.lang.Object[]) oo)[1];
        QualifiedAssignment.o1 = oo2[1];
        QualifiedAssignment qa1 = null;
        QualifiedAssignment[] qa2 = null;
        int idx = 0;
        (qa1 = (QualifiedAssignment)qa2[idx]).o1 = (new QualifiedAssignment[idx][idx][idx])[idx];
        (b ? (new QualifiedAssignment().q1 = new QualifiedAssignment()) :
            (QualifiedAssignment)(new QualifiedAssignment().q1 = new QualifiedAssignment())).q1 =
            (new QualifiedAssignment[new QualifiedAssignment().idx = (QualifiedAssignment.idx =
                QualifiedAssignment.idx)])[QualifiedAssignment.idx];
    }
}

class QualifiedAssignment {
    static Object o1;
    static QualifiedAssignment q1;
    static int idx = 1;
}
