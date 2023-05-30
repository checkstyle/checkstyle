/*
AbstractClassName
format = (default)^Abstract.+$
ignoreModifier = (default)false
ignoreName = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abstractclassname;

abstract class AbstractFirstClass {} // OK
abstract class SecondClass {} // violation
class AbstractThirdClass {} // violation
class FourthClass {} // OK
