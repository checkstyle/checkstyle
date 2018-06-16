package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;
abstract class InputRightCurlyEmptyAbstractMethod{
    
    abstract void moveTo(double deltaX, double deltaY);
    
    void foo() {
        while (true);        
    }
}
