package com.puppycrawl.tools.checkstyle.naming;

import org.junit.Before;

abstract class NonAAAAbstractClassName1 {
	public int serialNUMBER = 6;
	public final int s1erialNUMBER = 6;
	private static int s2erialNUMBER = 6;
	private static final int s3erialNUMBER = 6;
	
	@Override
	@SuppressWarnings
	@Before
	private void oveRRRRRrriddenMethod(){
	    int a = 0;
	    // blah-blah
	}
}

class Class1 {
    
    private void oveRRRRRrriddenMethod(){
        int a = 0;
        // blah-blah
    }
    
}

class Class2 extends Class1 {
    
    @Override
    @SuppressWarnings
    @Before
    private void oveRRRRRrriddenMethod(){
        int a = 0;
        // blah-blah
    }
    
}
