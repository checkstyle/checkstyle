/*
com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

package com.puppycrawl.tools.checkstyle.grammar;

import java.util.*;

public class InputJava7Diamond { // ok
    HashMap<String, Integer> map = new HashMap<String, Integer>();
    HashMap<String, Integer> map2 = new HashMap<>();
    HashMap<String, HashMap<Integer, Integer>> map3 = new HashMap<>();
    ArrayList<String> list = new ArrayList<>();
}
