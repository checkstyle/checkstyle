/*
AbstractClassName
format = ^Generator.+$
ignoreModifier = (default)false
ignoreName = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abstractclassname;

abstract class GeneratorThirteenthClass {} // OK
abstract class FourteenthClass {} // violation '.* must match pattern '\^Generator\.\+\$''
class GeneratorFifteenthClass {} // violation '.* must be declared as 'abstract''
class SixteenthClass {} // OK
