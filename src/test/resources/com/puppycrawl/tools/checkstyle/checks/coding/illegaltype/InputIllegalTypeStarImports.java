package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

import java.net.*;
import java.util.*;
import org.antlr.v4.runtime.*;
import com.puppycrawl.tools.checkstyle.*;
//configuration "illegalClassNames": List
public class InputIllegalTypeStarImports
{
    List<Integer> l = new LinkedList<>(); //WARNING
}
