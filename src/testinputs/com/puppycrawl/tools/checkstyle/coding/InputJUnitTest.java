package com.puppycrawl.tools.checkstyle.checks.coding;

import junit.framework.*;

public class InputJUnitTest extends TestCase
{
    public static Test suite() { return new TestSuite(""); } // correct
    protected void setUp() {} // correct
    public void tearDown() {} // correct
}

class BadTest1 extends TestCase
{
    private void setUp() {} // private!!!
    public static void tearDown() {} // static!!!
    static Test suite() { return new TestSuite(""); } // non-public
}

class BadTest2 extends TestCase
{
    public void SetUp() {} // wrong name
    public int tearDown() {} // wrong return type
    public static Test[] suite() {} // wrong return type
}

class BadTest3 extends TestCase
{
    public void setUp(int i) {} // too many args
    public void tear_down() {}  // correct (unchecked)
    public Test suite() { return new TestSuite(""); } // non-static
    public void tearDown(int i) {} // too many args
}
