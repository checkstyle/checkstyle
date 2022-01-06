/*
IllegalInstantiation
classes = java.util.Map, java.util.HashMap
tokens = (default)CLASS_DEF


*/

// this is not compilable because the import is missing and is used to test the code
package com.puppycrawl.tools.checkstyle.checks.coding.illegalinstantiation;

class InputIllegalInstantiationMap
{
    HashMap<String> map = new HashMap<>(); // ok
}
