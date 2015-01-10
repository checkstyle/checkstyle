package com.puppycrawl.tools.checkstyle.javadoc;

public class InputJavadocMethodIgnoreNameRegex
{
    private void foo() {
        
    }
    
    private void foo88() {
        
    }
    
    private void foo2() {
        int x = 0;
        int k = x >> 2;
        String s = String.valueOf(k);
        boolean b = false;
    }
}
