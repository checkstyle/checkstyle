package com.puppycrawl.tools.checkstyle.checks.indentation;

/**
 *
 * @author IljaDubinin
 */
public class InputNewHandler 
{
    
    public static void test() {
        method(ArrayList::new);
    }
    
}
