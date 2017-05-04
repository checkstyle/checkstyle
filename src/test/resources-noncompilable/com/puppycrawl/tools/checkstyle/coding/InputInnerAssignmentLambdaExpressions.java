//Compilable with Java8
package com.puppycrawl.tools.checkstyle.coding;

public class InputInnerAssignmentLambdaExpressions {
    
    private void setAction() {
        button.setOnAction(e -> pressed = true);  //No violation here
        button.setOnAction(e -> { pressed = true; });  //No violation here
    }
}
