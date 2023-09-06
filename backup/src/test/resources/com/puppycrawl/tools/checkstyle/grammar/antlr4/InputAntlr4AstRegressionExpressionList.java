package com.puppycrawl.tools.checkstyle.grammar.antlr4;

public class InputAntlr4AstRegressionExpressionList {
        protected InputAntlr4AstRegressionExpressionList ( int i )
    {
        this (); //whitespace
        toString ();
    }
    protected InputAntlr4AstRegressionExpressionList ()
    {
        super ();
    }

    public void enhancedFor ()
    {
        int[] i = new int[2];
        for ( int j: i ) {
            System.identityHashCode ( j );
        }
    }
}

@interface CronExpression {
    Class<?>[] groups() default {};
}

@interface CronExpression1 {
    Class<?>[] groups() default { }; // extra space
}
