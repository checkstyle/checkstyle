/*
CyclomaticComplexity
max = 0
switchBlockAsSingleDecisionPoint = (default)false
tokens = (default)CTOR_DEF, METHOD_DEF, INSTANCE_INIT, STATIC_INIT, LITERAL_WHILE, LITERAL_DO, \
         LITERAL_FOR, LITERAL_IF, LITERAL_SWITCH, LITERAL_CASE, LITERAL_CATCH, QUESTION, \
         LAND, LOR, COMPACT_CTOR_DEF, LITERAL_WHEN


*/

package org.checkstyle.suppressionxpathfilter.metrics.cyclomaticcomplexity;

public class InputXpathCyclomaticComplexityConditionals {
    public void test(int a, int b) { // violation 'Cyclomatic Complexity is 2 (max allowed is 0).'
        if (a > b) {

        } else {

        }
    }
}
