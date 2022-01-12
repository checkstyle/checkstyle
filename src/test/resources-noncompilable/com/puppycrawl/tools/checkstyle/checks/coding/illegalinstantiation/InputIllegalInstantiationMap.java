/*
IllegalInstantiation
classes = java.util.Map, java.util.HashMap
tokens = (default)CLASS_DEF


*/

//non-compiled syntax: the import is missing and is used to test the code
package com.puppycrawl.tools.checkstyle.checks.coding.illegalinstantiation;

class InputIllegalInstantiationMap
{
    HashMap<String> map = new HashMap<>(); // ok
}
