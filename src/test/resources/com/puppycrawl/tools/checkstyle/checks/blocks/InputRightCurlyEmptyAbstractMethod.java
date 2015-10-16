package com.puppycrawl.tools.checkstyle.checks.blocks;
abstract class CharSequenceReader{
    
    abstract void moveTo(double deltaX, double deltaY);
    
    void foo() {
        while (true);        
    }
}