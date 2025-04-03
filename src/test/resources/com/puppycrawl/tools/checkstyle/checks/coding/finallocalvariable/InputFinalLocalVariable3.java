/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
tokens = (default)VARIABLE_DEF
validateUnnamedVariables = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

import java.net.CacheRequest;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class InputFinalLocalVariable3 {

    private List<ProblemDescriptor> checkCodeBlock(final PsiCodeBlock body) {
        if (body == null) return null;
        final ControlFlow flow = new ControlFlow();
        try {
            int start = flow.getStartOffset(body);
            // violation above 'Variable 'start' should be declared final'
            int end = flow.getEndOffset(body);
            // violation above 'Variable 'end' should be declared final'

            final HashSet<Object> result = new HashSet<>();
            body.accept(new JavaRecursiveElementWalkingVisitor() {

                public void visitPatternVariable(Object variable) {
                    super.visitPatternVariable(variable);
                    if (true) return;
                    if (true) {
                        return;
                    }
                    int from;
                    int end;
                    if (true) {
                        from = flow.getEndOffset(body);
                        end = flow.getEndOffset(body);
                    } else if (false) {
                        PsiCodeBlock list; // violation 'Variable 'list' should be declared final'
                        end = flow.getEndOffset(body);
                    } else if (true) {
                        CacheRequest forEach;
                        // violation above 'Variable 'forEach' should be declared final'
                        PsiStatement body = null;
                        // violation above 'Variable 'body' should be declared final'
                        if (body == null) return;
                        from = flow.getStartOffset(body);
                        end = flow.getEndOffset(body);
                    } else {
                        return;
                    }
                    from = MathUtil.clamp(from, 0, flow.getInstructions().size());
                    end = MathUtil.clamp(end, from, flow.getInstructions().size());
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}

class PsiStatement {
}

class MathUtil {

    public static int clamp(int from, int i, int size) {
        return 0;
    }
}

class PsiExpression {
}

class ProblemDescriptor {
}

class PsiCodeBlock {
    public void accept(JavaRecursiveElementWalkingVisitor javaRecursiveElementWalkingVisitor) {
    }
}

abstract class JavaRecursiveElementWalkingVisitor {

    protected void visitPatternVariable(Object variable) {

    }
}

class ControlFlow {
    public int getStartOffset(PsiCodeBlock body) {
        return 0;
    }

    public int getEndOffset(PsiCodeBlock body) {
        return 0;
    }

    public Collection<Object> getInstructions() {
        return null;
    }

    public int getStartOffset(PsiStatement body) {
        return 0;
    }

    public int getEndOffset(PsiStatement body) {
        return 0;
    }

    public int getStartOffset(PsiExpression guardingExpression) {
        return 0;
    }
}
