package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;
abstract class CharSequenceReader{
    
    abstract void moveTo(double deltaX, double deltaY);
    
    void foo() {
        while (true);        
    }
}
