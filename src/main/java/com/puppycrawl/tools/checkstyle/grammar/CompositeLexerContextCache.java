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

/**
 * This class is used to keep track of the lexer context to help us determine
 * when to switch lexer modes.
 */
public final class CompositeLexerContextCache {

    /** Stack for counting curly brackets across nested string templates. */
    private final Deque<Integer> curlyBraceCounterStack;

    /** The lexer to use. */
    private final Lexer lexer;

    /** Depth of the string template context. */
    private int stringTemplateDepth;

    /**
     * Creates a new CompositeLexerContextCache instance.
     *
     * @param lexer the lexer to use
     */
    public CompositeLexerContextCache(
        Lexer lexer
    ) {
        curlyBraceCounterStack = new ArrayDeque<>();
        stringTemplateDepth = 0;
        this.lexer = lexer;
    }

    /**
     * Update the left curly brace context if we are in a string template.
     */
    public void updateLeftCurlyBraceContext() {
        if (isInStringTemplateContext()) {
            incrementBracketCounter();
        }
    }

    /**
     * Update the right curly brace context if we are in a string template.
     *
     * @param mode the mode to push to the mode stack
     */
    public void updateRightCurlyBraceContext(int mode) {
        if (isInStringTemplateContext()) {
            if (curlyBraceCounterStack.isEmpty()) {
                // this is the start delimiter of the outermost STRING_TEMPLATE_END
                pushToModeStack(mode);
            }
            else {
                final int bracketCounter = curlyBraceCounterStack.pop();
                if (bracketCounter == 0) {
                    // this is the start delimiter of a nested STRING_TEMPLATE_END
                    pushToModeStack(mode);
                }
                else {
                    curlyBraceCounterStack.push(bracketCounter - 1);
                }
            }
        }
    }

    /**
     * Enter a string template context.
     */
    public void enterStringTemplateContext() {
        stringTemplateDepth++;
        curlyBraceCounterStack.push(0);
    }

    /**
     * Exit a string template context.
     */
    public void exitStringTemplateContext() {
        stringTemplateDepth--;
    }

    /**
     * Push a mode to the mode stack.
     *
     * @param mode the mode to push to the mode stack
     */
    private void pushToModeStack(int mode) {
        lexer.more();
        lexer.pushMode(mode);
    }

    /**
     * Increment the curly bracket counter.
     */
    private void incrementBracketCounter() {
        final int bracketCounter = curlyBraceCounterStack.pop();
        curlyBraceCounterStack.push(bracketCounter + 1);
    }

    /**
     * Check if we are in a string template context.
     *
     * @return true if we are in a string template context
     */
    private boolean isInStringTemplateContext() {
        return stringTemplateDepth > 0;
    }

}
