package org.checkstyle.suppressionxpathfilter.avoidinlineconditionals;

public class InputXpathAvoidInlineConditionalsVariableDef {
    String substring(String a) {
        String b = (a == null || a.length() < 1) ? null : a.substring(1); // warn
        return b;
    }
}
