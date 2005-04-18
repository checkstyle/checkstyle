package com.puppycrawl.tools.checkstyle;

import java.util.Collection;
import java.util.Map;

public class InputGenerics<A, B extends Collection<?>, C extends D&E>
{
}

//No whitespace after commas
class BadCommas < A,B,C extends Map < A,String > >
{
}

// we need these interfaces for generics
interface D {
}
interface E {
}
