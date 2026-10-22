/*
AvoidInlineConditionals


*/

package org.checkstyle.suppressionxpathfilter.coding.avoidinlineconditionals;

public class InputXpathAvoidInlineConditionalsVariableDef {
    String substring(String a) {
        String b = (a == null || a.length() < 1) ? null : a.substring(1);
        // violation above 'Avoid inline conditionals.'
        return b;
    }
}
