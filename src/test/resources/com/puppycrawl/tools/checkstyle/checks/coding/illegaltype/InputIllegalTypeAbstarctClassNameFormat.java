/*
IllegalType
validateAbstractClassNames = true
illegalAbstractClassNameFormat = Gitt


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

public class InputIllegalTypeAbstarctClassNameFormat {

}

class Test extends Gitter { // violation 'Usage of type 'Gitter' is not allowed'
}

class Test1 extends Github { // OK
}

class Gitter{}
class Github{}
