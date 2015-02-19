package com.puppycrawl.tools.checkstyle.coding;
	
public class InputFinalLocalVariableEnhancedForLoopVariable {
    public void method1()
    {
        final java.util.List<Object> list = new java.util.ArrayList<>();

        for(Object a : list){
        }
    }
    
    public void method2()
    {
        final int[] squares = {0, 1, 4, 9, 16, 25};
        int x;
        for (final int i : squares) {
        }
        
    }
    
}
