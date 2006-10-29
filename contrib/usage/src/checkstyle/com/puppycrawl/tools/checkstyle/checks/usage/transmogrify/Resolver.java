
// Transmogrify License
// 
// Copyright (c) 2001, ThoughtWorks, Inc.
// All rights reserved.
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions
// are met:
// - Redistributions of source code must retain the above copyright notice,
//   this list of conditions and the following disclaimer.
// - Redistributions in binary form must reproduce the above copyright
// notice, this list of conditions and the following disclaimer in the
// documentation and/or other materials provided with the distribution.
// Neither the name of the ThoughtWorks, Inc. nor the names of its
// contributors may be used to endorse or promote products derived from this
// software without specific prior written permission.
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
// "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
// TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
// PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
// CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
// EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
// PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
// OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
// WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
// OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
// ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

package com.puppycrawl.tools.checkstyle.checks.usage.transmogrify;



import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogConfigurationException;
import org.apache.commons.logging.LogFactory;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * The resolver is responsible for traversing all the various
 * definitions in a symbol table and resolving references in them.
 *
 * @see SymbolTable
 */

public class Resolver extends DefinitionTraverser {

    /** true if the log factory has been initialized */
    private boolean mInitialized = false;

    /** Factory for creating org.apache.commons.logging.Log instances */
    private LogFactory mLogFactory;

    /**
     * constructor with <code>SymbolTable</code> to be resolved
     */
    public Resolver(SymbolTable symbolTable) {
        super(symbolTable);

        try {
            mLogFactory = LogFactory.getFactory();
        }
        catch (LogConfigurationException e) {
            System.out.println("log configuration exception" + e);
        }
        mInitialized = true;

    }

    /**
     * resolves the symbol table
     * @return <code>void</code>
     * @see #traverse()
     */
    public void resolve() {
        traverse();
    }

    protected void handleSList(SymTabAST node, Scope scope) {
        SymTabASTIterator iterator = node.getChildren();
        while (iterator.hasNext()) {
            SymTabAST current = iterator.nextChild();
            resolveExpression(current, scope, null, true);
        }
    }

    protected void handleAnonymousInnerClass(AnonymousInnerClass innerClass) {
        SymTabAST objblock = innerClass.getTreeNode();
        SymTabAST expression = (SymTabAST) objblock.getFirstChild();
        while (expression != null) {
            resolveExpression(expression, innerClass, null, true);
            expression = (SymTabAST) expression.getNextSibling();
        }
    }

    /**
     * processes a <code>ClassDef</code> and resolves references in it
     *
     * @param classDef the <code>ClassDef</code> to process
     */
    protected void handleClass(ClassDef classDef) {
        SymTabAST node = classDef.getTreeNode();

        if (node != null) {
            SymTabAST nameNode = node.findFirstToken(TokenTypes.IDENT);
            nameNode.setDefinition(classDef, classDef, true);

            SymTabAST extendsClause =
                node.findFirstToken(TokenTypes.EXTENDS_CLAUSE);

            if(extendsClause != null) {
                SymTabAST extendedClassNode =
                    (SymTabAST) extendsClause.getFirstChild();

                while (extendedClassNode != null) {
                    IClass superClass =
                        resolveClass(extendedClassNode, classDef, null, true);
                    extendedClassNode.setDefinition(superClass, classDef, true);
                    extendedClassNode =
                        (SymTabAST) extendedClassNode.getNextSibling();
                }
            }

            SymTabAST implementsNode =
                node.findFirstToken(TokenTypes.IMPLEMENTS_CLAUSE);

            if (implementsNode != null) {
                SymTabAST interfaceNode =
                    (SymTabAST) (implementsNode.getFirstChild());
                while (interfaceNode != null) {
                    resolveClass(interfaceNode, classDef, null, true);
                    interfaceNode =
                        (SymTabAST) (interfaceNode.getNextSibling());
                }
            }
        }
    }

    /**
     * processes a <code>MethodDef</code> and resolves references in it
     *
     * @param method the <code>MethodDef</code> to process
     */
    protected void handleMethod(MethodDef method) {
        SymTabAST node = method.getTreeNode();

        SymTabAST nameNode = node.findFirstToken(TokenTypes.IDENT);
        nameNode.setDefinition(method, method, true);

        // references to classes in return type
        SymTabAST returnTypeNode = node.findFirstToken(TokenTypes.TYPE);

        if (returnTypeNode != null) {
            // this is not a constructor
            resolveExpression(returnTypeNode, method, null, true);
        }

        SymTabAST throwsNode =
            node.findFirstToken(TokenTypes.LITERAL_THROWS);
        if (throwsNode != null) {
            SymTabAST exception = (SymTabAST) throwsNode.getFirstChild();
            while (exception != null) {
                // handle Checkstyle grammar
                if (exception.getType() != TokenTypes.COMMA) {
                    resolveClass(exception, method, null, true);
                }
                exception = (SymTabAST) exception.getNextSibling();
            }
        }

        // references to classes in parameters

        // the body -- this would be better its own function
        SymTabAST slist = node.findFirstToken(TokenTypes.SLIST);

        if (slist != null) {
            handleSList(slist, method);
        }
    }

    /**
     * processes a <code>BlockDef</code> and resolves references in it
     *
     * @param block the <code>BlockDef</code> to process
     */
    protected void handleBlock(BlockDef block) {
        SymTabAST node = block.getTreeNode();

        switch (node.getType()) {

            case TokenTypes.LITERAL_FOR :
                handleFor(block);
                break;

            case TokenTypes.LITERAL_IF :
                handleIf(block);
                break;

            case TokenTypes.LITERAL_WHILE :
                handleWhileAndSynchronized(block);
                break;

            case TokenTypes.LITERAL_DO :
                handleDoWhile(block);
                break;

            case TokenTypes.LITERAL_TRY :
            case TokenTypes.LITERAL_FINALLY :
                SymTabAST slist = node.findFirstToken(TokenTypes.SLIST);

                handleSList(slist, block);
                break;

            case TokenTypes.LITERAL_CATCH :
                handleCatch(block);
                break;

            case TokenTypes.LITERAL_SWITCH :
                handleSwitch(block);
                break;

            case TokenTypes.SLIST :
                handleSList(node, block);
                break;

            case TokenTypes.EXPR :
                resolveExpression(node, block, null, true);
                break;

            case TokenTypes.INSTANCE_INIT :
            case TokenTypes.STATIC_INIT :
                handleSList((SymTabAST) node.getFirstChild(), block);
                break;

            case TokenTypes.LITERAL_SYNCHRONIZED :
                handleWhileAndSynchronized(block);
                break;

            case TokenTypes.LITERAL_ASSERT :
                handleAssert(block);
                break;

            default :
                if (mInitialized) {
                    final Log log = mLogFactory.getInstance(this.getClass());
                    log.error(
                        "Unhandled block "
                            + block
                            + " of type "
                            + node.getType());
                }
        }
    }

    /**
     * @param block
     */
    private void handleAssert(BlockDef block) {
        SymTabAST node = block.getTreeNode();

        SymTabAST conditional =
            (node.findFirstToken(TokenTypes.EXPR));
        resolveExpression(conditional, block, null, true);

        SymTabAST message = (SymTabAST) conditional.getNextSibling();
        while ((message != null) && (message.getType() != TokenTypes.EXPR)) {
            message = (SymTabAST) message.getNextSibling();
        }
        if (message != null) {
            resolveExpression(message, block, null, true);
        }
    }

    /**
     * processes a switch statement and resolves references in it
     *
     * @param block the <code>BlockDef</code> to process
     */
    private void handleSwitch(BlockDef block) {
        SymTabAST node = block.getTreeNode();

        SymTabAST expr = node.findFirstToken(TokenTypes.EXPR);
        resolveExpression(expr, block, null, true);

        SymTabAST caseGroup = (SymTabAST) (expr.getNextSibling());
        while (caseGroup != null
            && (caseGroup.getType() != TokenTypes.CASE_GROUP)) {
            caseGroup = (SymTabAST) caseGroup.getNextSibling();
        }
        if (caseGroup != null) {
            while (caseGroup.getType() == TokenTypes.CASE_GROUP) {
                SymTabAST caseNode =
                    caseGroup.findFirstToken(TokenTypes.LITERAL_CASE);
                while (caseNode != null
                    && caseNode.getType() == TokenTypes.LITERAL_CASE) {
                    resolveExpression(
                        (SymTabAST) caseNode.getFirstChild(),
                        block,
                        null,
                        true);
                    caseNode = (SymTabAST) caseNode.getNextSibling();
                }

                SymTabAST caseSlist =
                    caseGroup.findFirstToken(TokenTypes.SLIST);
                handleSList(caseSlist, block);

                caseGroup = (SymTabAST) (caseGroup.getNextSibling());
            }
        }
    }

    /**
     * processes a catch block and resolves references in it
     *
     * @param block the <code>BlockDef</code> to process
     */
    private void handleCatch(BlockDef block) {
        SymTabAST node = block.getTreeNode();

        SymTabAST slist = node.findFirstToken(TokenTypes.SLIST);
        handleSList(slist, block);
    }

    /**
     * processes a for loop and resolves references in it
     *
     * @param block the <code>BlockDef</code> to process
     */
    private void handleFor(BlockDef block) {
        SymTabAST node = block.getTreeNode();

        SymTabAST body;
        SymTabAST forEach = node.findFirstToken(TokenTypes.FOR_EACH_CLAUSE);
        if (forEach == null) {
            SymTabAST init = node.findFirstToken(TokenTypes.FOR_INIT);
            // only need to handle the elist case.  if the init node is a variable
            // definition, the variable def will be handled later on in the resolution
            if (init.getFirstChild() != null) {
                if (init.getFirstChild().getType() == TokenTypes.ELIST) {
                    resolveExpression(
                        (SymTabAST) (init.getFirstChild()),
                        block,
                        null,
                        true);
                }
            }

            SymTabAST cond = node.findFirstToken(TokenTypes.FOR_CONDITION);
            if (cond.getFirstChild() != null) {
                resolveExpression(
                    (SymTabAST) (cond.getFirstChild()),
                    block,
                    null,
                    true);
            }

            SymTabAST iterator = node.findFirstToken(TokenTypes.FOR_ITERATOR);
            if (iterator.getFirstChild() != null) {
                resolveExpression(
                    (SymTabAST) (iterator.getFirstChild()),
                    block,
                    null,
                    true);
            }
            body = (SymTabAST) (iterator.getNextSibling());
        }
        else {
            resolveExpression(
                (forEach.findFirstToken(TokenTypes.EXPR)),
                block,
                null,
                true);
            body = (SymTabAST) (forEach.getNextSibling());
        }
        //could be an SLIST, EXPR or an EMPTY_STAT
        if (body.getType() == TokenTypes.RPAREN) {
            body = (SymTabAST) body.getNextSibling();
        }
        if (body.getType() == TokenTypes.SLIST) {
            handleSList(body, block);
        }
        else {
            resolveExpression(body, block, null, true);
        }

    }

    /**
     * processes an if statement and resolves references in it
     *
     * @param block the <code>BlockDef</code> to process
     */
    private void handleIf(BlockDef block) {
        SymTabAST node = block.getTreeNode();

        SymTabAST conditional =
            (node.findFirstToken(TokenTypes.EXPR));
        resolveExpression(conditional, block, null, true);

        SymTabAST body = (SymTabAST) conditional.getNextSibling();
        // Handle Checkstyle grammar
        if (body.getType() == TokenTypes.RPAREN) {
            body = (SymTabAST) body.getNextSibling();
        }
        if (body != null) {
        	if (body.getType() == TokenTypes.SLIST) {
            	handleSList(body, block);
        	}
        	else {
            	resolveExpression(body, block, null, true);
        	}

	        SymTabAST elseBody = (SymTabAST) body.getNextSibling();
	        //handle Checkstyle grammar
	        while ((elseBody != null)
	            && (elseBody.getType() != TokenTypes.LITERAL_ELSE)) {
	            elseBody = (SymTabAST) elseBody.getNextSibling();
	        }
	        /*
	         if (elseBody != null && elseBody.getType() == TokenTypes.SLIST) {
	             handleSList(elseBody, block);
	         }else{
	             resolveExpression(elseBody, block, null, true);
	         }
	         */
	        if (elseBody != null) {
	            elseBody = (SymTabAST) elseBody.getFirstChild();
	        }
	        if (elseBody != null) {
	            resolveExpression(elseBody, block.getParentScope(), null, true);
	        }
	    }
    }

    /**
     * processes a while loop and resolves references in it
     *
     * @param block the <code>BlockDef</code> to process
     */
    private void handleWhileAndSynchronized(BlockDef block) {
        SymTabAST node = block.getTreeNode();

        SymTabAST condition =
            (node.findFirstToken(TokenTypes.EXPR));
        SymTabAST slist = (SymTabAST) (condition.getNextSibling());
        // handle Checkstyle grammar
        if (slist.getType() == TokenTypes.RPAREN) {
            slist = (SymTabAST) slist.getNextSibling();
        }

        resolveExpression(condition, block, null, true);
        handleSList(slist, block);
    }

    private void handleDoWhile(BlockDef block) {
        SymTabAST node = block.getTreeNode();

        SymTabAST slist = (SymTabAST) node.getFirstChild();
        SymTabAST condition =
            node.findFirstToken(TokenTypes.EXPR);

        handleSList(slist, block);
        resolveExpression(condition, block, null, true);
    }

    /**
     * processes a variable definition and resolves references in it
     *
     * @param variable the <code>VariableDef</code> to process
     */
    protected void handleVariable(VariableDef variable) {
        SymTabAST node = variable.getTreeNode();
        Scope location = variable.getParentScope();

        SymTabAST nameNode = node.findFirstToken(TokenTypes.IDENT);
        nameNode.setDefinition(variable, location, true);

        SymTabAST typeNode = node.findFirstToken(TokenTypes.TYPE);
        resolveType(typeNode, location, null, true);

        SymTabAST assignmentNode = node.findFirstToken(TokenTypes.ASSIGN);
        if (assignmentNode != null) {
            resolveExpression(
                (SymTabAST) (assignmentNode.getFirstChild()),
                variable.getParentScope(),
                null,
                true);
        }
    }

    /**
     * processes a label and resolves references in it
     *
     * @param label the <code>LabelDef</code> to process
     */
    protected void handleLabel(LabelDef label) {
        SymTabAST node = label.getTreeNode();
        ((SymTabAST) node.getFirstChild()).setDefinition(
            label,
            label.getParentScope(),
            true);
    }

    /**
     * Resolves Java expressions, returning the type to which the expression
     * evalutes.  If this is the reference creation phase, any references found during resolution are created and
     * resolved.
     *
     * @param expression the <code>SymTabAST</code> representing the expression
     * @param location the <code>Scope</code> in which the expression occours.
     * @param context the <code>Scope</code> in which the search for the
     *                definition will start
     * @param referencePhase whether or not this is the reference phase of
     *                       table construction
     *
     * @return the <code>ClassDef</code> representing the type to which the
     *         expression evalutes.
     */
    public IClass resolveExpression(
        SymTabAST expression,
        Scope location,
        IClass context,
        boolean referencePhase) {
        IClass result = null;

        try {

            switch (expression.getType()) {

                case TokenTypes.TYPECAST :
                    result =
                        resolveTypecast(
                            expression,
                            location,
                            context,
                            referencePhase);
                    break;
                case TokenTypes.EXPR :
                case TokenTypes.LITERAL_RETURN :
                    if (expression.getFirstChild() != null) {
                        result =
                            resolveExpression(
                                (SymTabAST) expression.getFirstChild(),
                                location,
                                context,
                                referencePhase);
                    }
                    else {
                        // YOU WRITE BAD CODE!
                    }
                    break;

                case TokenTypes.ELIST :

                    SymTabAST child = (SymTabAST) (expression.getFirstChild());
                    while (child != null) {
                        if (child.getType() != TokenTypes.COMMA) {
                            resolveExpression(
                                child,
                                location,
                                context,
                                referencePhase);
                        }
                        child = (SymTabAST) (child.getNextSibling());
                    }
                    break;

                case TokenTypes.IDENT :
                    result =
                        resolveIdent(
                            expression,
                            location,
                            context,
                            referencePhase);
                    break;

                case TokenTypes.TYPE :
                    result =
                        resolveType(
                            expression,
                            location,
                            context,
                            referencePhase);
                    break;

                case TokenTypes.METHOD_CALL :
                //case TokenTypes.SUPER_CTOR_CALL :
                    result =
                        resolveMethod(
                            expression,
                            location,
                            context,
                            referencePhase);
                    break;

                case TokenTypes.LITERAL_THIS :
                    result = resolveLiteralThis(expression, location, context);
                    break;

                case TokenTypes.LITERAL_SUPER :
                    result = resolveLiteralSuper(expression, location, context);
                    break;

                case TokenTypes.DOT :
                    result =
                        resolveDottedName(
                            expression,
                            location,
                            context,
                            referencePhase);
                    break;

                case TokenTypes.LITERAL_NEW :
                case TokenTypes.CTOR_CALL :
                case TokenTypes.SUPER_CTOR_CALL :
                    result =
                        resolveNew(
                            expression,
                            location,
                            context,
                            referencePhase);
                    break;

                case TokenTypes.LITERAL_BOOLEAN :
                case TokenTypes.LITERAL_DOUBLE :
                case TokenTypes.LITERAL_FLOAT :
                case TokenTypes.LITERAL_LONG :
                case TokenTypes.LITERAL_INT :
                case TokenTypes.LITERAL_SHORT :
                case TokenTypes.LITERAL_BYTE :
                case TokenTypes.LITERAL_CHAR :
                    result =
                        resolvePrimitiveType(
                            expression,
                            location,
                            context,
                            referencePhase);
                    break;

                case TokenTypes.NUM_INT :
                case TokenTypes.NUM_LONG :
                    result = resolveNumInt(expression, location, context);
                    break;

                case TokenTypes.NUM_FLOAT :
                case TokenTypes.NUM_DOUBLE :
                    result = resolveNumFloat(expression, location, context);
                    break;

                case TokenTypes.STRING_LITERAL :
                    result =
                        resolveStringLiteral(expression, location, context);
                    break;

                case TokenTypes.CHAR_LITERAL :
                    result = resolveCharLiteral(expression, location, context);
                    break;

                case TokenTypes.ASSIGN :
                case TokenTypes.PLUS_ASSIGN :
                case TokenTypes.MINUS_ASSIGN :
                case TokenTypes.STAR_ASSIGN :
                case TokenTypes.DIV_ASSIGN :
                case TokenTypes.MOD_ASSIGN :
                case TokenTypes.SR_ASSIGN :
                case TokenTypes.BSR_ASSIGN :
                case TokenTypes.SL_ASSIGN :
                case TokenTypes.BAND_ASSIGN :
                case TokenTypes.BXOR_ASSIGN :
                case TokenTypes.BOR_ASSIGN :
                    resolveAssignment(
                        expression,
                        location,
                        context,
                        referencePhase);
                    break;

                case TokenTypes.LOR :
                case TokenTypes.LAND :
                case TokenTypes.NOT_EQUAL :
                case TokenTypes.EQUAL :
                case TokenTypes.LT :
                case TokenTypes.GT :
                case TokenTypes.LE :
                case TokenTypes.GE :
                    result =
                        resolveBooleanExpression(
                            expression,
                            location,
                            context,
                            referencePhase);
                    break;

                case TokenTypes.LITERAL_INSTANCEOF :
                    result =
                        resolveInstanceOf(
                            expression,
                            location,
                            context,
                            referencePhase);
                    break;

                case TokenTypes.LITERAL_TRUE :
                case TokenTypes.LITERAL_FALSE :
                    result =
                        resolveBooleanLiteral(expression, location, context);
                    break;

                case TokenTypes.LNOT :
                    result =
                        resolveBooleanUnary(
                            expression,
                            location,
                            context,
                            referencePhase);
                    break;

                case TokenTypes.INC :
                case TokenTypes.POST_INC :
                case TokenTypes.DEC :
                case TokenTypes.POST_DEC :
                case TokenTypes.UNARY_PLUS :
                case TokenTypes.UNARY_MINUS :
                    result =
                        resolveUnaryExpression(
                            expression,
                            location,
                            context,
                            referencePhase);
                    break;

                case TokenTypes.PLUS :
                case TokenTypes.MINUS :
                case TokenTypes.DIV :
                case TokenTypes.STAR :
                case TokenTypes.BAND :
                case TokenTypes.BOR :
                case TokenTypes.BXOR :
                case TokenTypes.MOD :
                    result =
                        resolveArithmeticExpression(
                            expression,
                            location,
                            context,
                            referencePhase);
                    break;

                case TokenTypes.LITERAL_BREAK :
                case TokenTypes.LITERAL_CONTINUE :
                    resolveGoto(expression, location, context, referencePhase);
                    break;

                case TokenTypes.LPAREN :
                    result = resolveExpression(
                        //TODO: child || sibling?
     (SymTabAST) (expression.getNextSibling()),
                        //(SymTabAST) (expression.getFirstChild()),
    location, context, referencePhase);
                    break;

                case TokenTypes.INDEX_OP :
                    result =
                        resolveArrayAccess(
                            expression,
                            location,
                            context,
                            referencePhase);
                    break;

                case TokenTypes.LITERAL_NULL :
                    result = new NullClass();
                    break;

                case TokenTypes.QUESTION :
                    result =
                        resolveQuestion(
                            expression,
                            location,
                            context,
                            referencePhase);
                    break;

                case TokenTypes.LITERAL_CLASS :
                    result = resolveLiteralClass();
                    break;

                case TokenTypes.ARRAY_INIT :
                    resolveArrayInitializer(
                        expression,
                        location,
                        context,
                        referencePhase);
                    break;

                case TokenTypes.LITERAL_THROW :
                    resolveThrowExpression(
                        expression,
                        location,
                        context,
                        referencePhase);
                    break;

                case TokenTypes.SL :
                case TokenTypes.SR :
                case TokenTypes.BSR :
                    result =
                        resolveShiftOperator(
                            expression,
                            location,
                            context,
                            referencePhase);
                    break;

                case TokenTypes.BNOT :
                    resolveBitwiseNot(
                        expression,
                        location,
                        context,
                        referencePhase);
                    break;

                case TokenTypes.LITERAL_ASSERT :
//                                        resolveAssert(
//                                            expression,
//                                            location,
//                                            context,
//                                            referencePhase);
                    break;

                case TokenTypes.RPAREN :
                case TokenTypes.EMPTY_STAT :
                    //    case TokenTypes.ML_COMMENT:
                    //    case TokenTypes.SL_COMMENT:
                case TokenTypes.VARIABLE_DEF :
                case TokenTypes.METHOD_DEF :
                case TokenTypes.CLASS_DEF :
                case TokenTypes.LITERAL_FOR :
                case TokenTypes.LITERAL_WHILE :
                case TokenTypes.LITERAL_IF :
                case TokenTypes.LITERAL_VOID :
                    //    case TokenTypes.LITERAL_INTERFACE:
                case TokenTypes.LITERAL_DO :
                case TokenTypes.LITERAL_SWITCH :
                case TokenTypes.LITERAL_STATIC :
                case TokenTypes.LITERAL_TRANSIENT :
                case TokenTypes.LITERAL_NATIVE :
                    //    case TokenTypes.LITERAL_threadsafe:
                case TokenTypes.LITERAL_SYNCHRONIZED :
                case TokenTypes.LITERAL_VOLATILE :
                case TokenTypes.LITERAL_TRY :
                case TokenTypes.LITERAL_CATCH :
                case TokenTypes.LITERAL_FINALLY :
                case TokenTypes.LABELED_STAT :
                case TokenTypes.LCURLY :
                case TokenTypes.RCURLY :
                case TokenTypes.SLIST :
                case TokenTypes.SEMI :
                case TokenTypes.COMMA :
                case TokenTypes.ARRAY_DECLARATOR :
                    break;

                default :
                //TODO: throw exception
                    if (mInitialized) {
                        final Log log =
                            mLogFactory.getInstance(this.getClass());
                        log.error(
                            "Unhandled expression type: "
                                + expression.getType());
                    }
                    break;
            }
        }
        catch (Exception e) {
            
            result = new UnknownClass(expression.getText(), expression);
//          TODO: This really should be logged
//            if (mInitialized) {
//                final Log log = mLogFactory.getInstance(this.getClass());
//                log.error("Error resolving near " + expression);
//            }
        }

        return result;
    }

    private IClass resolveTypecast(
        SymTabAST node,
        Scope location,
        IClass context,
        boolean referencePhase) {
        SymTabAST typeNode = (SymTabAST) node.getFirstChild();
        SymTabAST exprNode = (SymTabAST) typeNode.getNextSibling();
        //handle Checkstyle grammar
        if (exprNode.getType() == TokenTypes.RPAREN) {
            exprNode = (SymTabAST) exprNode.getNextSibling();
        }

        IClass type = null;

        final SymTabAST child = (SymTabAST) typeNode.getFirstChild();
        // TODO: Checkstyle change.
        // Do not create references from typecast.
        // Original transmogrify code is equivalent to
        // final boolean createReference = referencePhase;
        // which creates non-existant references for variables.
        final boolean createReference = false;
        if (child.getType()
            == TokenTypes.ARRAY_DECLARATOR) {
            type =
                new ArrayDef(
                    resolveType(
                        (SymTabAST) typeNode.getFirstChild(),
                        location,
                        context,
                        createReference));
        }
        else {
            type = resolveType(typeNode, location, context, createReference);
        }

        resolveExpression(exprNode, location, context, referencePhase);
        //TODO: Checkstyle change. Can this be ignored?
        if (type != null) {
            ((SymTabAST) typeNode.getFirstChild()).setDefinition(
                type,
                location,
                referencePhase);
        }

        return type;
    }

    private IClass resolveArrayAccess(
        SymTabAST node,
        Scope location,
        IClass context,
        boolean referencePhase) {

        SymTabAST arrayNode = (SymTabAST) (node.getFirstChild());
        SymTabAST exprNode = (SymTabAST) (arrayNode.getNextSibling());

        //resolve index expressions
        while (arrayNode.getType() == TokenTypes.INDEX_OP) {
            resolveExpression(exprNode, location, context, referencePhase);
            arrayNode = (SymTabAST) (arrayNode.getFirstChild());
            exprNode = (SymTabAST) (arrayNode.getNextSibling()); 
        }
        
        ArrayDef array =
            (ArrayDef) resolveExpression(arrayNode,
                location,
                context,
                referencePhase);

        resolveExpression(exprNode, location, context, referencePhase);

        return array.getType();
    }

    private IClass resolveLiteralClass() {
        return new ExternalClass(Class.class);
    }

    /**
     * Resolves any dotted reference, returning the <code>Scope</code>
     * identified by the reference.
     *
     * @param tree the root node of the dotted reference
     * @param location the <code>Scope</code> in which the expression occours.
     * @param context the <code>Scope</code> in which the search for the
     *                definition will start
     * @return the <code>Scope</code> indentified by the reference
     */
    private IClass resolveDottedName(
        SymTabAST tree,
        Scope location,
        IClass context,
        boolean referencePhase) {
        IClass result = null;

        IClass localContext = context;
        String name = null;

        DotIterator it = new DotIterator(tree);
        while (it.hasNext()) {
            SymTabAST node = it.nextNode();
            if (node.getType() != TokenTypes.COMMA) {
                localContext =
                    resolveExpression(
                        node,
                        location,
                        localContext,
                        referencePhase);
                if (localContext == null) {
                    node.setMeaningfulness(false);
                    name = node.getText();
                    while (localContext == null && it.hasNext()) {
                        SymTabAST next = it.nextNode();
                        name = name + "." + next.getText();
                        localContext = location.getClassDefinition(name);
                        if (localContext != null && referencePhase) {
                            next.setDefinition(
                                localContext,
                                location,
                                referencePhase);
                        }
                        else {
                            next.setMeaningfulness(false);
                        }
                    }
                }
            }
        }

        if (localContext != null) {
            result = localContext;
        }
        else {
            result = new UnknownClass(name, tree);
        }

        return result;
    }

    /**
     * Resolves a method call.
     *
     * @param methodNode the <code>SymTabAST</code> for the METHOD_CALL node
     * @param location the <code>Scope</code> where the expression occurs
     * @param context the <code>Scope</code> in which the expression occurs
     *                (where the search for a defintion begins)
     * @param referencePhase whether or not this is the reference phase of
     *                       table construction
     *
     * @return the <code>ClassDef</code> for the type returned by the method
     */
    private IClass resolveMethod(
        SymTabAST methodNode,
        Scope location,
        IClass context,
        boolean referencePhase) {
        IClass result = new UnknownClass(methodNode.getText(), methodNode);
        IClass newContext = null;

        if (context == null) {
            newContext = location.getEnclosingClass();
        }
        else {
            newContext = context;
        }

        String name = null;
        boolean createReference = true;

        SymTabAST nameNode = (SymTabAST) (methodNode.getFirstChild());
        SymTabAST parametersNode = (SymTabAST) (nameNode.getNextSibling());

        ISignature signature =
            resolveParameters(
                parametersNode,
                location,
                context,
                referencePhase);

        if (nameNode.getType() == TokenTypes.IDENT) {
            name = nameNode.getText();
        }
        else if (
            nameNode.getType() == TokenTypes.LITERAL_SUPER
                || (nameNode.getType() == TokenTypes.SUPER_CTOR_CALL)) {
            IClass superclass = location.getEnclosingClass().getSuperclass();
            newContext = superclass;
            name = superclass.getName();
            createReference = false;
        }
        else if (nameNode.getType() == TokenTypes.LITERAL_THIS) {
            newContext = location.getEnclosingClass();
            name = newContext.getName();
            createReference = false;
        }
        else {
            // REDTAG -- doing dotted name resolution on its own
            SymTabAST contextNode = (SymTabAST) (nameNode.getFirstChild());
            //TODO: handle Checkstyle grammar
            nameNode = (SymTabAST) contextNode.getNextSibling();
            //skip to IDENT
            while (nameNode.getType() != TokenTypes.IDENT) {
                nameNode = (SymTabAST) nameNode.getNextSibling();
            }
            
            name = nameNode.getText();
            newContext =
                resolveExpression(
                    contextNode,
                    location,
                    context,
                    referencePhase);
        }

        if (newContext != null) {
            IMethod method = newContext.getMethodDefinition(name, signature);

            if (method != null) {
                if (createReference && referencePhase) {
                    nameNode.setDefinition(method, location, referencePhase);
                }
                result = method.getType();
            }
        }

        if (result == null) {
            result = new UnknownClass(methodNode.getText(), methodNode);
        }

        return result;
    }

    /**
     * resolves a literal "this"
     *
     * @param expression the <code>SymTabAST</code> of the expression
     * @param location the <code>Scope</code> where the expression occurs
     * @param context the <code>Scope</code> in which the expression occurs
     *                (where the search for a defintion begins)
     *
     * @return the resulting scope of the expression (the type to which it evaluates)
     */
    private IClass resolveLiteralThis(
        SymTabAST thisNode,
        Scope location,
        IClass context) {
        return location.getEnclosingClass();
    }

    /**
     * resolves a literal "super"
     *
     * @param expression the <code>SymTabAST</code> of the expression
     * @param location the <code>Scope</code> where the expression occurs
     * @param context the <code>Scope</code> in which the expression occurs
     *                (where the search for a defintion begins)
     *
     * @return the resulting scope of the expression (the type to which it evaluates)
     */
    private IClass resolveLiteralSuper(
        SymTabAST superNode,
        Scope location,
        IClass context) {
        return location.getEnclosingClass().getSuperclass();
    }

    private boolean newIsConstructor(SymTabAST newNode) {
        boolean result = false;

        SymTabAST typeNode =
            (SymTabAST) (newNode.getFirstChild().getNextSibling());
        //handle Checkstyle grammar
        if (typeNode.getType() == TokenTypes.LPAREN) {
            typeNode = (SymTabAST) typeNode.getNextSibling();
        }
        if (typeNode.getType() == TokenTypes.ELIST) {
            result = true;
        }
        return result;

    }

    /**
     * resolves and expression of type TokenTypes.TYPE
     *
     * @param expression the <code>SymTabAST</code> of the expression
     * @param location the <code>Scope</code> where the expression occurs
     * @param context the <code>Scope</code> in which the expression occurs
     *                (where the search for a defintion begins)
     * @param referencePhase whether or not this is the reference phase of
     *                       table construction
     * @return the resulting scope of the expression (the type to which it evaluates)
     * @see #resolveDottedName(SymTabAST, Scope, IClass, boolean)
     * @see #resolveClassIdent(SymTabAST, Scope, IClass, boolean)
     */
    public IClass resolveType(
        SymTabAST expr,
        Scope location,
        IClass context,
        boolean referencePhase) {
        IClass result = null;
        SymTabAST nameNode = (SymTabAST) expr.getFirstChild();

        // TODO: Checkstyle change.
        // Do not create references from typecast.
        // Original transmogrify code is equivalent to
        // final boolean createReference = referencePhase;
        // which creates non-existant references for variables.
        final boolean createReference = false;
        if (nameNode.getType() == TokenTypes.DOT) {
            result =
                resolveDottedName(nameNode, location, context, createReference);
        }
        else {
            result =
                resolveClassIdent(nameNode, location, context, createReference);
        }

        return result;
    }

    /**
     * resolves Class type expression
     * @param expr node to be resolved
     * @param location scope of the <code>expr</code>
     * @param context context of the <code>expr</code> if exists
     * @param referencePhase <code>true</code> if this method is used to during
     *                                         finding reference phase
     *                       <code>false</code> otherwise
     * @return <code>IClass</code> representing the type to which the
     *         expression evalutes.
     * @see #resolveDottedName(SymTabAST, Scope, IClass, boolean)
     */
    public IClass resolveClass(
        SymTabAST expr,
        Scope location,
        IClass context,
        boolean referencePhase) {

        IClass result =
            resolveDottedName(expr, location, context, referencePhase);
        if (result != null && referencePhase) {
            expr.setDefinition(result, location, referencePhase);
        }

        return result;
    }

    /**
     * resolves expression with <code>JavaTokenTypes<code> other than <code>DOT</code>
     * @param expr expression to be resolved
     * @param location scope of the expression
     * @param context context of the expression if any
     * @param referencePhase <code>true</code> if this method is used to during
     *                                         finding reference phase
     *                       <code>false</code> otherwise
     * @return <code>IClass</code> representing the type to which the
     *         expression evalutes.
     */
    public IClass resolveClassIdent(
        SymTabAST expr,
        Scope location,
        IClass context,
        boolean referencePhase) {

        IClass result = location.getClassDefinition(expr.getText());
        if (result != null) {
            expr.setDefinition(result, location, referencePhase);
        }

        return result;
    }

    private IClass resolveNew(
        SymTabAST newNode,
        Scope location,
        IClass context,
        boolean referencePhase) {

        IClass result;

        if (newIsConstructor(newNode)) {
            result =
                resolveConstructor(newNode, location, context, referencePhase);
        }
        else {
            result =
                resolveNewArray(newNode, location, context, referencePhase);
        }

        return result;
    }

    private IClass resolveNewArray(
        SymTabAST newNode,
        Scope location,
        IClass context,
        boolean referencePhase) {
        IClass arrayType;

        SymTabAST typeNode = (SymTabAST) (newNode.getFirstChild());
        SymTabAST declaratorNode = (SymTabAST) (typeNode.getNextSibling());
        SymTabAST initializerNode =
            (SymTabAST) (declaratorNode.getNextSibling());

        arrayType = resolveClass(typeNode, location, context, referencePhase);

        if (declaratorNode.getFirstChild() != null) {
            resolveExpression(
                ((SymTabAST) declaratorNode.getFirstChild()),
                location,
                context,
                referencePhase);
        }

        if (initializerNode != null) {
            resolveArrayInitializer(
                initializerNode,
                location,
                context,
                referencePhase);
        }

        return new ArrayDef(arrayType);
    }

    private IClass resolveQuestion(
        SymTabAST question,
        Scope location,
        IClass context,
        boolean referencePhase) {
        SymTabAST test = (SymTabAST) question.getFirstChild();
        while (test.getType() == TokenTypes.LPAREN) {
            test = (SymTabAST) test.getNextSibling();
        }
        SymTabAST leftBranch = (SymTabAST) test.getNextSibling();
        while (leftBranch.getType() == TokenTypes.RPAREN) {
            leftBranch = (SymTabAST) leftBranch.getNextSibling();
        }
        SymTabAST rightBranch = (SymTabAST) leftBranch.getNextSibling();
        while (rightBranch.getType() != TokenTypes.COLON) {
            rightBranch = (SymTabAST) rightBranch.getNextSibling();
        }
        rightBranch = (SymTabAST) rightBranch.getNextSibling();

        resolveExpression(test, location, context, referencePhase);
        IClass leftClass =
            resolveExpression(leftBranch, location, context, referencePhase);
        IClass rightClass =
            resolveExpression(rightBranch, location, context, referencePhase);

        return moreGeneral(leftClass, rightClass);
    }

    private IClass moreGeneral(IClass a, IClass b) {
        return (a.isCompatibleWith(b)) ? b : a;
    }

    /**
     * Resolves a constructor call.
     *
     * @param tree the root node of the constructor call
     * @return the <code>ClassDef</code> for the class instantiated by the
     *         constructor
     */
    private IClass resolveConstructor(
        SymTabAST constructor,
        Scope location,
        IClass context,
        boolean referencePhase) {

        IClass classConstructed = null;

        SymTabAST nameNode = (SymTabAST) (constructor.getFirstChild());
        //SymTabAST parametersNode = (SymTabAST) (nameNode.getNextSibling());
        SymTabAST parametersNode =
            constructor.findFirstToken(TokenTypes.ELIST);
        SymTabAST nameIdent = null;
        if (nameNode.getType() == TokenTypes.IDENT) {
            nameIdent = nameNode;
        }
        else {
            nameIdent = (SymTabAST) nameNode.getFirstChild().getNextSibling();
        }

        classConstructed = resolveClass(nameNode, location, context, false);
        if (classConstructed != null) {
            MethodSignature signature =
                resolveParameters(
                    parametersNode,
                    location,
                    context,
                    referencePhase);

            IMethod constructorDef =
                classConstructed.getMethodDefinition(
                    nameIdent.getText(),
                    signature);

            if (constructorDef != null && referencePhase) {
                nameIdent.setDefinition(
                    constructorDef,
                    location,
                    referencePhase);
            }
        }

        return classConstructed;
    }

    /**
     * Resolves the types found in a method call. Any references found
     * in the process are created.  Returns a <code>MethodSignature</code> for
     * the types of the parameters.
     *
     * @param elist The <code>SymTabAST</code> for the list of parameters
     * @return the signature of the parameters
     */
    private MethodSignature resolveParameters(
        SymTabAST elist,
        Scope location,
        IClass context,
        boolean referencePhase) {
        Vector parameters = new Vector();

        SymTabAST expr = (SymTabAST) (elist.getFirstChild());
        while (expr != null) {
            if (expr.getType() != TokenTypes.COMMA) {
                IClass parameter =
                    resolveExpression((SymTabAST) (expr
				    .getFirstChild()),
				    location,
				    context,
				    referencePhase);
                parameters.add(parameter);
            }

            expr = (SymTabAST) (expr.getNextSibling());
        }

        return new MethodSignature(parameters);
    }

    /**
     * Resolves an IDENT node of an AST, creating the appropriate reference and
     * returning the scope of the identifer.
     *
     * @param ident the IDENT node
     * @param location the <code>Scope</code> in which the IDENT is found
     * @return the <code>Scope</code> the identifier identifies
     */
    private IClass resolveIdent(
        SymTabAST ident,
        Scope location,
        IClass context,
        boolean referencePhase) {

        IClass result = null;
        IDefinition def = null;
        String name = ident.getText();

        // look for var
        if (context != null) {
            def = context.getVariableDefinition(name);
        }
        else {
            def = location.getVariableDefinition(name);
        }

        if (def != null) {
            result = ((IVariable) def).getType();
        }
        else {
            // look for class
            if (context != null) {
                result = context.getClassDefinition(name);
            }
            else {
                result = location.getClassDefinition(name);
            }
            def = result;
        }

        if (def != null) {
            ident.setDefinition(def, location, referencePhase);
        }

        return result;
    }

    /**
     * Resolves a (binary) boolean expression.  The left and right sides of the
     * expression
     * are resolved in the process.
     *
     * @param expression the <code>SymTabAST</code> representing the boolean
     *                   expression.
     * @return the <code>Scope</code> for the boolean primitive type.
     */
    private IClass resolveBooleanExpression(
        SymTabAST expression,
        Scope location,
        IClass context,
        boolean referencePhase) {
        IClass result = null;

        SymTabAST leftChild = findLeftChild(expression);
        resolveExpression(leftChild, location, context, referencePhase);
        SymTabAST rightChild = findRightSibling(leftChild);

        resolveExpression(rightChild, location, context, referencePhase);

        result = LiteralResolver.getDefinition(TokenTypes.LITERAL_BOOLEAN);

        return result;
    }

    /**
     * resolves references in an assignment expression
     *
     * @param expression the <code>SymTabAST</code> of the expression
     * @param location the <code>Scope</code> where the expression occurs
     * @param context the <code>Scope</code> in which the expression occurs
     *                (where the search for a defintion begins)
     *
     * @return the resulting scope of the expression (the type to which it evaluates)
     */
    private IClass resolveAssignment(
        SymTabAST expression,
        Scope location,
        IClass context,
        boolean referencePhase) {
        IClass result = null;

        SymTabAST leftNode = (SymTabAST) (expression.getFirstChild());
        SymTabAST rightNode = (SymTabAST) (leftNode.getNextSibling());

        result = resolveExpression(leftNode, location, context, referencePhase);
        resolveExpression(rightNode, location, context, referencePhase);

        return result;
    }

    /**
     * Resolves a unary expression.  Returns the type of the expression,
     * creating any references found along the way.  Unary expressions are
     * increment (x++), decrement (x--), unary plus (+x), and unary minus (-x)
     *
     * @param expression the <code>SymTabAST</code> of the unary expression.
     * @return the <code>Scope</code> for the type to which the expression
     * evalutes.
     */
    private IClass resolveUnaryExpression(
        SymTabAST expression,
        Scope location,
        IClass context,
        boolean referencePhase) {
        SymTabAST operatee = (SymTabAST) (expression.getFirstChild());
        return resolveExpression(operatee, location, context, referencePhase);
    }

    /**
     * Resolves an arithmetic expression.  Returns the <code>Scope</code> for
     * the type to which the expression resolves.  Any references found during
     * resolution are created and resolved.
     *
     * @param expression the <code>SymTabAST</code> representing the arithmetic
     *                   expression.
     *
     * @return the <code>Scope</code> for the type to which the expression
     *         evaluates.
     */
    private IClass resolveArithmeticExpression(
        SymTabAST expression,
        Scope location,
        IClass context,
        boolean referencePhase) {
        IClass result = null;

        SymTabAST leftChild = findLeftChild(expression);
        
        IClass leftType =
            (resolveExpression(leftChild,
		    location,
		    context,
		    referencePhase));
                
        SymTabAST rightChild = findRightSibling(leftChild);

        IClass rightType =
                    (resolveExpression(rightChild,
		    location,
		    context,
		    referencePhase));

        result = binaryResultType(leftType, rightType);

        return result;
    }

    /**
     * Finds the left child of a binary operator, skipping parentheses.
     * @param aExpression the node for the binary operator.
     * @return the node for the left child.
     */  
    private SymTabAST findLeftChild(SymTabAST aExpression) {
        SymTabAST leftChild = (SymTabAST) (aExpression.getFirstChild());
        // handle Checkstyle grammar
        while (leftChild.getType() == TokenTypes.LPAREN) {
            leftChild = (SymTabAST) leftChild.getNextSibling();
        }
        return leftChild;
    }

    /**
     * Finds the right sibling of the left child of a binary operator,
     * skipping parentheses.
     * @param aLeftChild the left child of a binary operator.
     * @return the node of the right sibling.
     */   
    private SymTabAST findRightSibling(SymTabAST aLeftChild) {
        SymTabAST rightChild = (SymTabAST) (aLeftChild.getNextSibling());
        // handle Checkstyle grammar
        while ((rightChild != null)
            && (rightChild.getType() == TokenTypes.RPAREN))
        {
            rightChild = (SymTabAST) rightChild.getNextSibling();
        }
        return rightChild;
    }

    /**
     * Returns the <code>ClassDef</code> for the type to which arithmetic
     * expressions evaluate.
     *
     * @param a the <code>ClassDef</code> of the first operand.
     * @param b the <code>ClassDef</code> of the second operand.
     *
     * @return the <code>ClassDef</code> to which the expression evaluates.
     */
    private IClass binaryResultType(IClass a, IClass b) {

        IClass result = null;

        // These may or may not be in line with the rules set forth in the java
        // language specification.  Not being in line would be a BadThing(r).

        IClass string = new ExternalClass(java.lang.String.class);

        if (a.equals(string) || b.equals(string)) {
            result = string;
        }
        else if (a.equals(PrimitiveClasses.BOOLEAN)) {
            result = PrimitiveClasses.BOOLEAN;
        }
        else {
            result =
                PrimitiveClasses.binaryPromote(
                    a,
                    b);
        }

        return result;
    }

    /**
     * resolves references in an instanceof expression
     *
     * @param expression the <code>SymTabAST</code> of the expression
     * @param location the <code>Scope</code> where the expression occurs
     * @param context the <code>Scope</code> in which the expression occurs
     *                (where the search for a defintion begins)
     *
     * @return the resulting scope of the expression (the type to which it evaluates)
     */
    private IClass resolveInstanceOf(
        SymTabAST expression,
        Scope location,
        IClass context,
        boolean referencePhase) {
        SymTabAST leftNode = (SymTabAST) (expression.getFirstChild());
        SymTabAST rightNode = (SymTabAST) (leftNode.getNextSibling());

        resolveExpression(leftNode, location, context, referencePhase);

        SymTabAST classNameNode = (SymTabAST) (rightNode.getFirstChild());
        resolveClass(classNameNode, location, context, referencePhase);

        return LiteralResolver.getDefinition(TokenTypes.LITERAL_BOOLEAN);
    }

    /**
     * resolves references in a a break statement
     *
     * @param expression the <code>SymTabAST</code> for the expression
     * @param location the <code>Scope</code> where the expression occurs
     * @param context the <code>Scope</code> in which the expression occurs
     *                (where the search for a defintion begins)
     *
     * @return the <code>Scope</code> for the int primitive type
     */
    private IClass resolveGoto(
        SymTabAST expression,
        Scope location,
        IClass context,
        boolean referencePhase) {
        SymTabAST label = (SymTabAST) (expression.getFirstChild());
        // handle Checkstyle grammar
        if (label != null && (label.getType() != TokenTypes.SEMI)) {
            LabelDef def = location.getLabelDefinition(label.getText());
            if (def != null) {
                label.setDefinition(def, location, referencePhase);
            }
        }

        return null;
    }

    private IClass resolvePrimitiveType(
        SymTabAST primitive,
        Scope location,
        IClass context,
        boolean referencePhase) {
        IClass result =
            LiteralResolver.getDefinition(primitive.getType());

        primitive.setDefinition(result, location, referencePhase);
        return result;
    }

    /**
     * Returns the <code>ClassDef</code> of the int primitive type.  This may
     * need to be amended, based on the Java Language spec, to return a long
     * if the literal is larger than an int can hold.
     *
     * @param expression the <code>SymTabAST</code> for the integer literal
     * @param location the <code>Scope</code> where the expression occurs
     * @param context the <code>Scope</code> in which the expression occurs
     *                (where the search for a defintion begins)
     *
     * @return the <code>Scope</code> for the int primitive type
     */
    private IClass resolveNumInt(
        SymTabAST expression,
        Scope location,
        IClass context) {
        return PrimitiveClasses.INT;
    }

    /**
     * Returns the <code>ClassDef</code> type of the float primitive type.
     * This may need to be amended, based on the Java Language spec, to return
     * a double if the literal is larger than a float can hold.
     *
     * @param expression the <code>SymTabAST</code> for the floating point
      literal
     * @param location the <code>Scope</code> where the expression occurs
     * @param context the <code>Scope</code> in which the expression occurs
     *                (where the search for a defintion begins)
     *
     * @return the <code>Scope</code> for the float primitive type
     */
    private IClass resolveNumFloat(
        SymTabAST expression,
        Scope location,
        IClass context) {
        return PrimitiveClasses.DOUBLE;
    }

    /**
     * Returns the <code>ClassDef</code> type of a string literal
     *
     * @param expression the <code>SymTabAST</code> for a string literal
     * @param location the <code>Scope</code> where the expression occurs
     * @param context the <code>Scope</code> in which the expression occurs
     *                (where the search for a defintion begins)
     *
     * @return the <code>Scope</code> type of a string literal
     */
    private IClass resolveStringLiteral(
        SymTabAST expression,
        Scope location,
        IClass context) {
        return LiteralResolver.getDefinition(
            TokenTypes.STRING_LITERAL);
    }

    /**
     * Returns the <code>ClassDef</code> type of a character literal
     *
     * @param expression the <code>SymTabAST</code> for a string literal
     * @param location the <code>Scope</code> where the expression occurs
     * @param context the <code>Scope</code> in which the expression occurs
     *                (where the search for a defintion begins)
     *
     * @return the <code>Scope</code> type of a character literal
     */
    private IClass resolveCharLiteral(
        SymTabAST expression,
        Scope location,
        IClass context) {
        return LiteralResolver.getDefinition(
            TokenTypes.LITERAL_CHAR);
    }

    /**
     * Describe <code>resolveBooleanLiteral</code> method here.
     *
     * @param expression the <code>SymTabAST</code> of the expression
     * @param location the <code>Scope</code> where the expression occurs
     * @param context the <code>Scope</code> in which the expression occurs
     *                (where the search for a defintion begins)
     *
     * @return the <code>Scope</code> for the boolean primitive.
     */
    private IClass resolveBooleanLiteral(
        SymTabAST expression,
        Scope location,
        IClass context) {
        return LiteralResolver.getDefinition(TokenTypes.LITERAL_BOOLEAN);
    }

    private IClass resolveBooleanUnary(
        SymTabAST expression,
        Scope location,
        IClass context,
        boolean referencePhase) {
        SymTabAST child = (SymTabAST) expression.getFirstChild();
        resolveExpression(child, location, context, referencePhase);

        return LiteralResolver.getDefinition(TokenTypes.LITERAL_BOOLEAN);
    }

    /**
     * Resolves a constructor call.
     *
     * @param tree the root node of the constructor call
     * @return the <code>ClassDef</code> for the class instantiated by the
     *         constructor
     */
    private void resolveArrayInitializer(
        SymTabAST initializerNode,
        Scope location,
        IClass context,
        boolean referencePhase) {
        SymTabAST child = (SymTabAST) (initializerNode.getFirstChild());
        while (child != null) {
            if (child.getType() != TokenTypes.COMMA) {
                resolveExpression(child, location, context, referencePhase);
            }
            child = (SymTabAST) (child.getNextSibling());
        }
    }

    /**
     * Resolves a constructor call.
     *
     * @param tree the root node of the constructor call
     * @return the <code>ClassDef</code> for the class instantiated by the
     *         constructor
     */
    private void resolveThrowExpression(
        SymTabAST throwNode,
        Scope location,
        IClass context,
        boolean referencePhase) {

        SymTabAST nameNode = (SymTabAST) (throwNode.getFirstChild());
        resolveExpression(nameNode, location, context, referencePhase);
    }

    private IClass resolveShiftOperator(
        SymTabAST expression,
        Scope location,
        IClass context,
        boolean referencePhase) {
        IClass result = null;

        SymTabAST leftChild = findLeftChild(expression);
        SymTabAST rightChild = findRightSibling(leftChild);

        result =
            resolveExpression(leftChild, location, context, referencePhase);
        resolveExpression(rightChild, location, context, referencePhase);

        result = PrimitiveClasses.unaryPromote(result);

        return result;
    }

    private IClass resolveBitwiseNot(
        SymTabAST expression,
        Scope location,
        IClass context,
        boolean referencePhase) {
        IClass result = null;
        SymTabAST child = (SymTabAST) expression.getFirstChild();
        result = resolveExpression(child, location, context, referencePhase);

        result = PrimitiveClasses.unaryPromote(result);

        return result;
    }
}
