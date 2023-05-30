/*
AbstractClassName
format = ^Generator.+$
ignoreModifier = (default)false
ignoreName = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abstractclassname;

abstract class GeneratorThirteenthClass {} // OK
abstract class FourteenthClass {} // violation
class GeneratorFifteenthClass {} // violation
class SixteenthClass {} // OK
