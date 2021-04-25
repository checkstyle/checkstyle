package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

import java.net.*;
import java.util.*;
import org.antlr.v4.runtime.*;
import com.puppycrawl.tools.checkstyle.*;

/*
 * Config:
 * illegalClassNames = { List }
 */
public class InputIllegalTypeTestStarImports
{
    List<Integer> l = new LinkedList<>(); // violation
}
