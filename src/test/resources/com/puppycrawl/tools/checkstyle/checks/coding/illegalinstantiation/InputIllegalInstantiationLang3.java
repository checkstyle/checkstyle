/*
IllegalInstantiation
classes = jaaa.lnng.Boolean,jaaa.lnng.String
tokens = (default)IMPORT,LITERAL_NEW,PACKAGE_DEF,CLASS_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalinstantiation;

class InputIllegalInstantiationLang3 {
    Boolean obj = new Boolean(true);
    Integer obj2 = new Integer(0);
}
