///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.indentation;

import java.util.function.IntFunction;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * Immutable view of everything an indentation handler needs from the surrounding
 * {@code IndentationCheck}, so handlers do not hold a direct reference back to
 * the check.
 */
public final class IndentationContext {

    /** Message key for an indentation error. */
    /* package */ static final String MSG_ERROR = "indentation.error";
    /** Message key for a multi-level indentation error. */
    /* package */ static final String MSG_ERROR_MULTI = "indentation.error.multi";
    /** Message key for a child indentation error. */
    /* package */ static final String MSG_CHILD_ERROR = "indentation.child.error";
    /** Message key for a multi-level child indentation error. */
    /* package */ static final String MSG_CHILD_ERROR_MULTI = "indentation.child.error.multi";

    /** Basic offset property value. */
    private final int basicOffset;
    /** Brace adjustment property value. */
    private final int braceAdjustment;
    /** Case indent property value. */
    private final int caseIndent;
    /** Throws indent property value. */
    private final int throwsIndent;
    /** Array initialization indent property value. */
    private final int arrayInitIndent;
    /** Line wrapping indentation property value. */
    private final int lineWrappingIndentation;
    /** Force-strict-condition property value. */
    private final boolean forceStrictCondition;
    /** Tab width used for expanded-tabs column calculations. */
    private final int indentationTabWidth;
    /** Provides a source line by zero-based index. */
    private final IntFunction<String> lineProvider;
    /** Factory that creates handlers for AST nodes. */
    private final HandlerFactory handlerFactory;
    /** Shared line-wrapping handler used by expression handlers. */
    private final LineWrappingHandler lineWrappingHandler;
    /** Sink for indentation violations. */
    private final IndentationLogger logger;

    /**
     * Construct a context with all values handlers need for one file.
     *
     * @param basicOffset             basic offset property
     * @param braceAdjustment         brace adjustment property
     * @param caseIndent              case indent property
     * @param throwsIndent            throws indent property
     * @param arrayInitIndent         array-init indent property
     * @param lineWrappingIndentation line-wrapping indent property
     * @param forceStrictCondition    force-strict-condition property
     * @param indentationTabWidth     tab width for expanded-tabs math
     * @param lineProvider            zero-based source line accessor
     * @param handlerFactory          factory for AST handlers
     * @param lineWrappingHandler     shared line-wrapping handler
     * @param logger                  sink for violations
     * @noinspection ParametersPerConstructor
     * @noinspectionreason ParametersPerConstructor - value-object ctor bundles all
     *     data handlers need per file
     */
    // -@cs[ParameterNumber] bundling ctor for a value-object context
    /* package */ IndentationContext(int basicOffset, int braceAdjustment, int caseIndent,
                       int throwsIndent,
                       int arrayInitIndent, int lineWrappingIndentation,
                       boolean forceStrictCondition, int indentationTabWidth,
                       IntFunction<String> lineProvider, HandlerFactory handlerFactory,
                       LineWrappingHandler lineWrappingHandler, IndentationLogger logger) {
        this.basicOffset = basicOffset;
        this.braceAdjustment = braceAdjustment;
        this.caseIndent = caseIndent;
        this.throwsIndent = throwsIndent;
        this.arrayInitIndent = arrayInitIndent;
        this.lineWrappingIndentation = lineWrappingIndentation;
        this.forceStrictCondition = forceStrictCondition;
        this.indentationTabWidth = indentationTabWidth;
        this.lineProvider = lineProvider;
        this.handlerFactory = handlerFactory;
        this.lineWrappingHandler = lineWrappingHandler;
        this.logger = logger;
    }

    /**
     * Get the basic offset property.
     *
     * @return basic offset
     */
    /* package */ int getBasicOffset() {
        return basicOffset;
    }

    /**
     * Get the brace adjustment property.
     *
     * @return brace adjustment
     */
    /* package */ int getBraceAdjustment() {
        return braceAdjustment;
    }

    /**
     * Get the case indent property.
     *
     * @return case indent
     */
    /* package */ int getCaseIndent() {
        return caseIndent;
    }

    /**
     * Get the throws indent property.
     *
     * @return throws indent
     */
    /* package */ int getThrowsIndent() {
        return throwsIndent;
    }

    /**
     * Get the array-init indent property.
     *
     * @return array-init indent
     */
    /* package */ int getArrayInitIndent() {
        return arrayInitIndent;
    }

    /**
     * Get the line-wrapping indentation property.
     *
     * @return line-wrapping indent
     */
    /* package */ int getLineWrappingIndentation() {
        return lineWrappingIndentation;
    }

    /**
     * Get the force-strict-condition property.
     *
     * @return true when strict line-wrap indent is enforced
     */
    /* package */ boolean isForceStrictCondition() {
        return forceStrictCondition;
    }

    /**
     * Get the tab width used for expanded-tabs column math.
     *
     * @return tab width
     */
    /* package */ int getIndentationTabWidth() {
        return indentationTabWidth;
    }

    /**
     * Get a source line by zero-based index.
     *
     * @param lineIndex zero-based line index
     * @return the source line
     */
    /* package */ String getLine(int lineIndex) {
        return lineProvider.apply(lineIndex);
    }

    /**
     * Get the shared handler factory.
     *
     * @return handler factory
     */
    /* package */ HandlerFactory getHandlerFactory() {
        return handlerFactory;
    }

    /**
     * Get the shared line-wrapping handler.
     *
     * @return line-wrapping handler
     */
    /* package */ LineWrappingHandler getLineWrappingHandler() {
        return lineWrappingHandler;
    }

    /**
     * Report an indentation violation for the given AST.
     *
     * @param ast the AST that caused the violation
     * @param messageKey the message key
     * @param args message arguments
     */
    /* package */ void indentationLog(DetailAST ast, String messageKey, Object... args) {
        logger.log(ast, messageKey, args);
    }

}
