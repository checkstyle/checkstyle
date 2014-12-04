package com.puppycrawl.tools.checkstyle.coding;

import junit.framework.*;

public class InputJUnitTest extends TestCase
{
    public static Test suite() { return new Test(); } // correct
    protected void setUp() {} // correct
    public void tearDown() {} // correct
}

class BadTest1 extends TestCase
{
    public void setUp() {} 
    public void tearDown() {} 
    static Test suite() { return new Test(); } // non-public
}

class BadTest2 extends TestCase
{
    public void SetUp() {} // wrong name
    public void tearDown() {}
    public static Test[] suite() {
		return null;} // wrong return type
}

class BadTest3 extends TestCase
{
    public void setUp(int i) {} // too many args
    public void tear_down() {}  // correct (unchecked)
    public Test suite() { return new Test(); } // non-static
    public void tearDown(int i) {} // too many args
}
