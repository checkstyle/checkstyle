///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.grammar;

import java.util.ArrayDeque;
import java.util.Deque;

import org.antlr.v4.runtime.Lexer;

import com.puppycrawl.tools.checkstyle.grammar.java.JavaLanguageLexer;

/**
 * This class is used to keep track of the lexer context to help us determine
 * when to switch lexer modes.
 */
public final class CompositeLexerContextCache {

    /** Stack for tracking string template contexts. */
    private final Deque<TemplateContext> templateContextStack;

    /** The lexer to use. */
    private final Lexer lexer;

    /**
     * Creates a new CompositeLexerContextCache instance.
     *
     * @param lexer the lexer to use
     */
    public CompositeLexerContextCache(Lexer lexer) {
        templateContextStack = new ArrayDeque<>();
        this.lexer = lexer;
    }

    /**
     * Update the left curly brace context if we are in a string template.
     */
    public void updateLeftCurlyBraceContext() {
        if (isInTemplateContext()) {
            final TemplateContext currentContext = templateContextStack.pop();
            final TemplateContext newContext = new TemplateContext(currentContext.getMode(),
                    currentContext.getCurlyBraceDepth() + 1);
            templateContextStack.push(newContext);
        }
    }

    /**
     * Update the right curly brace context if we are in a string template.
     */
    public void updateRightCurlyBraceContext() {
        if (isInTemplateContext()) {
            final TemplateContext currentContext = templateContextStack.peek();
            if (currentContext.getCurlyBraceDepth() == 0) {
                // This right curly brace is the start delimiter
                // of a template middle or end. We consume
                // the right curly brace to be used as the first token
                // in the appropriate lexer mode rule, enter
                // the corresponding lexer mode, and keep consuming
                // the rest of the template middle or end.
                lexer.setType(JavaLanguageLexer.EMBEDDED_EXPRESSION_END);
                lexer.pushMode(currentContext.getMode());
            }
            else {
                // We've consumed a right curly brace within an embedded expression.
                final TemplateContext newContext = new TemplateContext(currentContext.getMode(),
                        currentContext.getCurlyBraceDepth() - 1);
                templateContextStack.push(newContext);
            }
        }
    }

    /**
     * Enter a string template context.
     *
     * @param mode the lexer mode to enter
     */
    public void enterTemplateContext(int mode) {
        final TemplateContext newContext = new TemplateContext(mode, 0);
        templateContextStack.push(newContext);
        lexer.pushMode(mode);
    }

    /**
     * Exit a string template context.
     */
    public void exitTemplateContext() {
        templateContextStack.pop();
        lexer.popMode();
    }

    /**
     * Check if we are in a string template context.
     *
     * @return true if we are in a string template context
     */
    private boolean isInTemplateContext() {
        return !templateContextStack.isEmpty();
    }

    /**
     * A class to represent the context of a string template.
     */
    private static final class TemplateContext {

        /** The lexer mode of this context. */
        private final int mode;

        /** The depth of this context. */
        private final int curlyBraceDepth;

        /**
         * Creates a new TemplateContext instance.
         *
         * @param mode the lexer mode of this context
         * @param curlyBraceDepth the depth of this context
         */
        private TemplateContext(int mode, int curlyBraceDepth) {
            this.mode = mode;
            this.curlyBraceDepth = curlyBraceDepth;
        }

        /**
         * Get the lexer mode of this context.
         *
         * @return current lexer mode
         */
        private int getMode() {
            return mode;
        }

        /**
         * Current depth of this context.
         *
         * @return current depth
         */
        private int getCurlyBraceDepth() {
            return curlyBraceDepth;
        }
    }

}
