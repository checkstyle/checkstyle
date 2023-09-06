package com.puppycrawl.tools.checkstyle.grammar;

public class InputAstRegressionMethodDefArrayDeclarator {
    double[] computeDerivatives() {
        return null;
    }

    double computeIntegrations() {
        return 1.1;
    }

    double
            []
            []
            []
    computeOtherStuff() {
        return new double [2][2][8];
    }

    public double[] test = {0};
    double[] test2 = {0};
    double [][][] test3;
    @Deprecated double[] test4 = {0};

}
