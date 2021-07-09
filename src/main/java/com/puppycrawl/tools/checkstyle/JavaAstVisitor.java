////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.grammar.java.JavaLexer;
import com.puppycrawl.tools.checkstyle.grammar.java.JavaParser;
import com.puppycrawl.tools.checkstyle.grammar.java.JavaParserBaseVisitor;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * Visitor class used to build Checkstyle's Java AST.
 */
public final class JavaAstVisitor extends JavaParserBaseVisitor<DetailAstImpl> {

    /** String representation of the left shift operator. */
    private static final String LEFT_SHIFT = "<<";

    /** String representation of the unsigned right shift operator. */
    private static final String UNSIGNED_RIGHT_SHIFT = ">>>";

    /** String representation of the right shift operator. */
    private static final String RIGHT_SHIFT = ">>";

    /** Token stream to check for hidden tokens. */
    private final BufferedTokenStream tokens;

    /**
     * Constructs a JavaAstVisitor with given token stream.
     *
     * @param tokenStream the token stream to check for hidden tokens
     */
    public JavaAstVisitor(CommonTokenStream tokenStream) {
        tokens = tokenStream;
    }

    @Override
    public DetailAstImpl visitCompilationUnit(JavaParser.CompilationUnitContext ctx) {
        final DetailAstImpl root = visit(ctx.children.get(0));
        for (int i = 1; i < ctx.children.size(); i++) {
            addLastSibling(root, visit(ctx.children.get(i)));
        }
        return root;
    }

    @Override
    public DetailAstImpl visitPackageDeclaration(JavaParser.PackageDeclarationContext ctx) {
        final DetailAstImpl packageDeclaration =
                create(TokenTypes.PACKAGE_DEF, (Token) ctx.LITERAL_package().getPayload());

        // We use the last index of the package declaration to determine the
        // position of the ANNOTATIONS subtree.
        final DetailAstImpl annotations = visit(ctx.annotations());
        packageDeclaration.addChild(annotations);

        final DetailAstImpl qualifiedName = visit(ctx.qualifiedName());
        packageDeclaration.addChild(qualifiedName);

        if (!annotations.hasChildren()) {
            annotations.setColumnNo(qualifiedName.getColumnNo());
            annotations.setLineNo(qualifiedName.getLineNo());
        }

        packageDeclaration.addChild(create(ctx.SEMI()));
        return packageDeclaration;
    }

    @Override
    public DetailAstImpl visitImportDec(JavaParser.ImportDecContext ctx) {
        final DetailAstImpl importRoot = create(ctx.start);

        // Static import
        if (ctx.LITERAL_static() != null) {
            importRoot.setType(TokenTypes.STATIC_IMPORT);
            importRoot.addChild(create(ctx.LITERAL_static()));
        }

        // Handle star imports
        final DetailAstImpl qualifiedName = visit(ctx.qualifiedName());
        final boolean isStarImport = ctx.STAR() != null;
        if (isStarImport) {
            final DetailAstImpl dot = create(ctx.DOT());
            dot.addChild(qualifiedName);
            dot.addChild(create(ctx.STAR()));
            importRoot.addChild(dot);
        }
        else {
            importRoot.addChild(visit(ctx.qualifiedName()));
        }

        importRoot.addChild(create(ctx.SEMI()));
        return importRoot;
    }

    @Override
    public DetailAstImpl visitSingleSemiImport(JavaParser.SingleSemiImportContext ctx) {
        return create(ctx.SEMI());
    }

    @Override
    public DetailAstImpl visitTypeDeclaration(JavaParser.TypeDeclarationContext ctx) {
        final DetailAstImpl typeDeclaration;
        if (ctx.type == null) {
            typeDeclaration = create(ctx.semi.get(0));
            ctx.semi.subList(1, ctx.semi.size())
                    .forEach(semi -> addLastSibling(typeDeclaration, create(semi)));
        }
        else {
            typeDeclaration = visit(ctx.type);
        }
        return typeDeclaration;
    }

    @Override
    public DetailAstImpl visitModifier(JavaParser.ModifierContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public DetailAstImpl visitVariableModifier(JavaParser.VariableModifierContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public DetailAstImpl visitClassDeclaration(JavaParser.ClassDeclarationContext ctx) {
        final DetailAstImpl classDef = createImaginary(TokenTypes.CLASS_DEF, ctx.start);
        classDef.addChild(createModifiers(ctx.mods, classDef));
        processChildren(classDef, ctx.children);

        // 'CLASS_DEF' imaginary node takes position of first child
        classDef.setColumnNo(classDef.getFirstChild().getColumnNo());
        classDef.setLineNo(classDef.getFirstChild().getLineNo());
        return classDef;
    }

    @Override
    public DetailAstImpl visitRecordDeclaration(JavaParser.RecordDeclarationContext ctx) {
        final DetailAstImpl recordDef = createImaginary(TokenTypes.RECORD_DEF, ctx.start);
        recordDef.addChild(createModifiers(ctx.mods, recordDef));
        processChildren(recordDef, ctx.children);

        // 'RECORD_DEF' imaginary node takes position of first child
        recordDef.setColumnNo(recordDef.getFirstChild().getColumnNo());
        recordDef.setLineNo(recordDef.getFirstChild().getLineNo());
        return recordDef;
    }

    @Override
    public DetailAstImpl visitRecordComponentsList(JavaParser.RecordComponentsListContext ctx) {
        final DetailAstImpl lparen = create(ctx.LPAREN());

        // We make a "RECORD_COMPONENTS" node whether parameters exist or not
        if (ctx.recordComponents() == null) {
            addLastSibling(lparen, createImaginary(TokenTypes.RECORD_COMPONENTS, ctx.RPAREN()));
        }
        else {
            addLastSibling(lparen, visit(ctx.recordComponents()));
        }
        addLastSibling(lparen, create(ctx.RPAREN()));
        return lparen;
    }

    @Override
    public DetailAstImpl visitRecordComponents(JavaParser.RecordComponentsContext ctx) {
        final DetailAstImpl firstRecordComponent = visit(ctx.children.get(0));
        final DetailAstImpl recordComponents =
                copy(TokenTypes.RECORD_COMPONENTS, firstRecordComponent);
        recordComponents.addChild(firstRecordComponent);
        processChildren(recordComponents, ctx.children.subList(1, ctx.children.size()));
        return recordComponents;
    }

    @Override
    public DetailAstImpl visitRecordComponent(JavaParser.RecordComponentContext ctx) {
        final DetailAstImpl recordComponent =
                createImaginary(TokenTypes.RECORD_COMPONENT_DEF, ctx.start);
        recordComponent.addChild(visit(ctx.annotations()));
        final DetailAstImpl type = visit(ctx.type);
        recordComponent.addChild(type);
        recordComponent.addChild(visit(ctx.id()));
        return recordComponent;
    }

    @Override
    public DetailAstImpl visitLastRecordComponent(JavaParser.LastRecordComponentContext ctx) {
        final DetailAstImpl recordComponent =
                createImaginary(TokenTypes.RECORD_COMPONENT_DEF, ctx.start);
        recordComponent.addChild(visit(ctx.annotations()));
        final DetailAstImpl type = visit(ctx.type);
        recordComponent.addChild(type);
        recordComponent.addChild(create(ctx.ELLIPSIS()));
        recordComponent.addChild(visit(ctx.id()));
        return recordComponent;
    }

    @Override
    public DetailAstImpl visitRecordBody(JavaParser.RecordBodyContext ctx) {
        final DetailAstImpl objBlock = createImaginary(TokenTypes.OBJBLOCK, ctx.start);
        processChildren(objBlock, ctx.children);
        return objBlock;
    }

    @Override
    public DetailAstImpl visitCompactConstructorDeclaration(
            JavaParser.CompactConstructorDeclarationContext ctx) {
        final DetailAstImpl compactConstructor =
                createImaginary(TokenTypes.COMPACT_CTOR_DEF, ctx.start);
        compactConstructor.addChild(createModifiers(ctx.mods, compactConstructor));
        compactConstructor.addChild(visit(ctx.id()));
        compactConstructor.addChild(visit(ctx.constructorBlock()));
        return compactConstructor;
    }

    @Override
    public DetailAstImpl visitClassExtends(JavaParser.ClassExtendsContext ctx) {
        final DetailAstImpl classExtends = create(ctx.EXTENDS_CLAUSE());
        classExtends.addChild(visit(ctx.type));
        return classExtends;
    }

    @Override
    public DetailAstImpl visitImplementsClause(JavaParser.ImplementsClauseContext ctx) {
        final DetailAstImpl classImplements = create(TokenTypes.IMPLEMENTS_CLAUSE,
                (Token) ctx.LITERAL_implements().getPayload());
        classImplements.addChild(visit(ctx.typeList()));
        return classImplements;
    }

    @Override
    public DetailAstImpl visitTypeParameters(JavaParser.TypeParametersContext ctx) {
        final DetailAstImpl typeParameters = createImaginary(TokenTypes.TYPE_PARAMETERS, ctx.start);
        typeParameters.addChild(create(TokenTypes.GENERIC_START, (Token) ctx.LT().getPayload()));
        // Exclude '<' and '>'
        processChildren(typeParameters, ctx.children.subList(1, ctx.children.size() - 1));
        typeParameters.addChild(create(TokenTypes.GENERIC_END, (Token) ctx.GT().getPayload()));
        return typeParameters;
    }

    @Override
    public DetailAstImpl visitTypeParameter(JavaParser.TypeParameterContext ctx) {
        final DetailAstImpl typeParameter = createImaginary(TokenTypes.TYPE_PARAMETER, ctx.start);
        processChildren(typeParameter, ctx.children);
        return typeParameter;
    }

    @Override
    public DetailAstImpl visitTypeUpperBounds(JavaParser.TypeUpperBoundsContext ctx) {
        // In this case, we call 'extends` TYPE_UPPER_BOUNDS
        final DetailAstImpl typeUpperBounds = create(TokenTypes.TYPE_UPPER_BOUNDS,
                (Token) ctx.EXTENDS_CLAUSE().getPayload());
        // 'extends' is child[0]
        processChildren(typeUpperBounds, ctx.children.subList(1, ctx.children.size()));
        return typeUpperBounds;
    }

    @Override
    public DetailAstImpl visitTypeBound(JavaParser.TypeBoundContext ctx) {
        final DetailAstImpl typeBoundType = visit(ctx.typeBoundType(0));
        final Iterator<JavaParser.TypeBoundTypeContext> typeBoundTypeIterator =
                ctx.typeBoundType().listIterator(1);
        ctx.BAND().forEach(band -> {
            addLastSibling(typeBoundType, create(TokenTypes.TYPE_EXTENSION_AND,
                                (Token) band.getPayload()));
            addLastSibling(typeBoundType, visit(typeBoundTypeIterator.next()));
        });
        return typeBoundType;
    }

    @Override
    public DetailAstImpl visitTypeBoundType(JavaParser.TypeBoundTypeContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public DetailAstImpl visitEnumDeclaration(JavaParser.EnumDeclarationContext ctx) {
        final DetailAstImpl enumDef = createImaginary(TokenTypes.ENUM_DEF, ctx.start);
        enumDef.addChild(createModifiers(ctx.mods, enumDef));
        processChildren(enumDef, ctx.children);

        // 'ENUM_DEF' imaginary node takes position of first child
        enumDef.setColumnNo(enumDef.getFirstChild().getColumnNo());
        enumDef.setLineNo(enumDef.getFirstChild().getLineNo());
        return enumDef;
    }

    @Override
    public DetailAstImpl visitEnumBody(JavaParser.EnumBodyContext ctx) {
        final DetailAstImpl objBlock = createImaginary(TokenTypes.OBJBLOCK, ctx.start);
        processChildren(objBlock, ctx.children);
        return objBlock;
    }

    @Override
    public DetailAstImpl visitEnumConstants(JavaParser.EnumConstantsContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public DetailAstImpl visitEnumConstant(JavaParser.EnumConstantContext ctx) {
        final DetailAstImpl enumConstant =
                createImaginary(TokenTypes.ENUM_CONSTANT_DEF, ctx.start);
        processChildren(enumConstant, ctx.children);
        return enumConstant;
    }

    @Override
    public DetailAstImpl visitEnumBodyDeclarations(JavaParser.EnumBodyDeclarationsContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public DetailAstImpl visitInterfaceDeclaration(JavaParser.InterfaceDeclarationContext ctx) {
        final DetailAstImpl interfaceDef = createImaginary(TokenTypes.INTERFACE_DEF, ctx.start);
        interfaceDef.addChild(createModifiers(ctx.mods, interfaceDef));
        processChildren(interfaceDef, ctx.children);

        // 'INTERFACE_DEF' imaginary node takes position of first child
        interfaceDef.setColumnNo(interfaceDef.getFirstChild().getColumnNo());
        interfaceDef.setLineNo(interfaceDef.getFirstChild().getLineNo());
        return interfaceDef;
    }

    @Override
    public DetailAstImpl visitInterfaceExtends(JavaParser.InterfaceExtendsContext ctx) {
        final DetailAstImpl interfaceExtends = create(ctx.EXTENDS_CLAUSE());
        interfaceExtends.addChild(visit(ctx.typeList()));
        return interfaceExtends;
    }

    @Override
    public DetailAstImpl visitClassBody(JavaParser.ClassBodyContext ctx) {
        final DetailAstImpl objBlock = createImaginary(TokenTypes.OBJBLOCK, ctx.start);
        processChildren(objBlock, ctx.children);
        return objBlock;
    }

    @Override
    public DetailAstImpl visitInterfaceBody(JavaParser.InterfaceBodyContext ctx) {
        final DetailAstImpl objBlock = createImaginary(TokenTypes.OBJBLOCK, ctx.start);
        processChildren(objBlock, ctx.children);
        return objBlock;
    }

    @Override
    public DetailAstImpl visitEmptyClass(JavaParser.EmptyClassContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public DetailAstImpl visitClassBlock(JavaParser.ClassBlockContext ctx) {
        DetailAstImpl classBlock = super.visitClassBlock(ctx);

        if (ctx.LITERAL_static() == null) {
            // We call it an INSTANCE_INIT
            final DetailAstImpl temp = classBlock;
            classBlock = createImaginary(TokenTypes.INSTANCE_INIT, ctx.start);
            classBlock.addChild(temp);
        }
        else {
            // Create imaginary STATIC_INIT node
            final DetailAstImpl temp = classBlock;
            classBlock = create(TokenTypes.STATIC_INIT, (Token) ctx.LITERAL_static().getPayload());
            classBlock.setText(TokenUtil.getTokenName(TokenTypes.STATIC_INIT));
            classBlock.addChild(temp);
        }

        return classBlock;
    }

    @Override
    public DetailAstImpl visitMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {
        // We could have type parameters or return type as first element
        final ParserRuleContext typeStart =
                Optional.ofNullable((ParserRuleContext) ctx.typeParameters())
                .orElse(ctx.type);

        // Or, modifiers could be first
        final DetailAstImpl startToken = getStartToken(ctx.mods, typeStart);
        final DetailAstImpl methodDef = copy(TokenTypes.METHOD_DEF, startToken);
        methodDef.addChild(createModifiers(ctx.mods, startToken));

        // Process all children except C style array declarators
        processChildren(methodDef, ctx.children.stream()
                .filter(child -> !(child instanceof JavaParser.ArrayDeclaratorContext))
                .collect(Collectors.toList()));

        // We add C style array declarator brackets to TYPE ast
        final DetailAstImpl typeAst = (DetailAstImpl) methodDef.findFirstToken(TokenTypes.TYPE);
        ctx.cStyleArrDec.forEach(child -> typeAst.addChild(visit(child)));

        return methodDef;
    }

    @Override
    public DetailAstImpl visitMethodBody(JavaParser.MethodBodyContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public DetailAstImpl visitThrowsList(JavaParser.ThrowsListContext ctx) {
        final DetailAstImpl throwsRoot = create(ctx.LITERAL_throws());
        throwsRoot.addChild(visit(ctx.qualifiedNameList()));
        return throwsRoot;
    }

    @Override
    public DetailAstImpl visitConstructorDeclaration(
            JavaParser.ConstructorDeclarationContext ctx) {
        // CTOR_DEF imaginary node takes position of first element
        final DetailAstImpl startToken = getStartToken(ctx.mods, ctx.start);
        final DetailAstImpl constructorDeclaration = copy(TokenTypes.CTOR_DEF, startToken);
        constructorDeclaration.addChild(createModifiers(ctx.mods, constructorDeclaration));
        processChildren(constructorDeclaration, ctx.children);
        return constructorDeclaration;
    }

    @Override
    public DetailAstImpl visitFieldDeclaration(JavaParser.FieldDeclarationContext ctx) {
        final DetailAstImpl dummyNode = new DetailAstImpl();
        // Since the TYPE AST is built by visitVariableDeclarator(), we skip it here (child [0])
        // We also append the SEMI token to the first child [size() - 1],
        // until https://github.com/checkstyle/checkstyle/issues/3151
        processChildren(dummyNode, ctx.children.subList(1, ctx.children.size() - 1));
        dummyNode.getFirstChild().addChild(create(ctx.SEMI()));
        return dummyNode.getFirstChild();
    }

    @Override
    public DetailAstImpl visitInterfaceBodyDeclaration(
            JavaParser.InterfaceBodyDeclarationContext ctx) {
        final DetailAstImpl returnTree;
        if (ctx.SEMI() == null) {
            returnTree = visit(ctx.interfaceMemberDeclaration());
        }
        else {
            returnTree = create(ctx.SEMI());
        }
        return returnTree;
    }

    @Override
    public DetailAstImpl visitInterfaceMethodDeclaration(
            JavaParser.InterfaceMethodDeclarationContext ctx) {
        // We use the modifiers AST to determine the starting position of the METHOD_DEF AST
        final ParserRuleContext typeStart =
                Optional.ofNullable((ParserRuleContext) ctx.typeParameters())
                .orElse(ctx.type);

        final DetailAstImpl startToken = getStartToken(ctx.mods, typeStart);
        final DetailAstImpl methodDef = copy(TokenTypes.METHOD_DEF, startToken);
        methodDef.addChild(createModifiers(ctx.mods, startToken));

        // Process all children except C style array declarators and modifiers
        final List<ParseTree> children = ctx.children
                .stream()
                .filter(child -> !(child instanceof JavaParser.ArrayDeclaratorContext))
                .collect(Collectors.toList());
        processChildren(methodDef, children);

        // We add C style array declarator brackets to TYPE ast
        final DetailAstImpl typeAst = (DetailAstImpl) methodDef.findFirstToken(TokenTypes.TYPE);
        ctx.cStyleArrDec.forEach(child -> typeAst.addChild(visit(child)));

        return methodDef;
    }

    @Override
    public DetailAstImpl visitVariableDeclarators(JavaParser.VariableDeclaratorsContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public DetailAstImpl visitVariableDeclarator(JavaParser.VariableDeclaratorContext ctx) {
        // VARIABLE_DEF imaginary node takes position of first element
        final DetailAstImpl type = visit(ctx.type);
        final DetailAstImpl root = new DetailAstImpl();
        root.addChild(createModifiers(ctx.mods, type));
        root.addChild(type);
        root.addChild(visit(ctx.id()));

        // Add C style array declarator brackets to TYPE ast
        ctx.arrayDeclarator().forEach(child -> type.addChild(visit(child)));

        // If this is an assignment statement, ASSIGN becomes the parent of EXPR
        if (ctx.ASSIGN() != null) {
            root.addChild(create(ctx.ASSIGN()));
            ((DetailAstImpl) root.getLastChild()).addChild(visit(ctx.variableInitializer()));
        }
        final DetailAstImpl variableDef = copy(TokenTypes.VARIABLE_DEF, root.getFirstChild());
        variableDef.addChild(root.getFirstChild());
        return variableDef;
    }

    @Override
    public DetailAstImpl visitVariableDeclaratorId(JavaParser.VariableDeclaratorIdContext ctx) {
        final DetailAstImpl type = visit(ctx.type);
        final DetailAstImpl root = new DetailAstImpl();
        root.addChild(createModifiers(ctx.mods, type));
        root.addChild(type);

        final DetailAstImpl declaratorId;
        if (ctx.DOT() == null && ctx.LITERAL_this() == null) {
            declaratorId = visit(ctx.id());
        }
        else if (ctx.DOT() == null) {
            declaratorId = create(ctx.LITERAL_this());
        }
        else {
            declaratorId = create(ctx.DOT());
            declaratorId.addChild(visit(ctx.id()));
            declaratorId.addChild(create(ctx.LITERAL_this()));
        }

        root.addChild(declaratorId);
        ctx.arrayDeclarator().forEach(child -> type.addChild(visit(child)));

        return root.getFirstChild();
    }

    @Override
    public DetailAstImpl visitArrayInitializer(JavaParser.ArrayInitializerContext ctx) {
        final DetailAstImpl arrayInitializer = create(TokenTypes.ARRAY_INIT, ctx.start);
        // ARRAY_INIT was child[0]
        processChildren(arrayInitializer, ctx.children.subList(1, ctx.children.size()));
        return arrayInitializer;
    }

    @Override
    public DetailAstImpl visitClassOrInterfaceType(JavaParser.ClassOrInterfaceTypeContext ctx) {
        final DetailAstPair currentAST = new DetailAstPair();
        DetailAstPair.addASTChild(currentAST, visit(ctx.annotations()));
        DetailAstPair.addASTChild(currentAST, visit(ctx.id()));
        DetailAstPair.addASTChild(currentAST, visit(ctx.typeArguments()));

        // This is how we build the annotations/ qualified name/ type parameters tree
        for (int i = 0; i < ctx.extended.size(); i++) {
            final ParserRuleContext extendedContext = ctx.extended.get(i);
            final DetailAstImpl dot = create(extendedContext.start);
            DetailAstPair.makeAstRoot(currentAST, dot);
            final List<ParseTree> childList = extendedContext
                    .children.subList(1, extendedContext.children.size());
            childList.forEach(child -> {
                DetailAstPair.addASTChild(currentAST, visit(child));
            });
        }

        // Create imaginary 'TYPE' parent if specified
        final DetailAstImpl returnTree;
        if (ctx.createImaginaryNode) {
            returnTree = copy(TokenTypes.TYPE, currentAST.root);
            returnTree.addChild(currentAST.root);
        }
        else {
            returnTree = currentAST.root;
        }
        return returnTree;
    }

    @Override
    public DetailAstImpl visitSimpleTypeArgument(JavaParser.SimpleTypeArgumentContext ctx) {
        final DetailAstImpl typeArgument =
                copy(TokenTypes.TYPE_ARGUMENT, visit(ctx.children.get(0)));
        typeArgument.addChild(visit(ctx.typeType()).getFirstChild());
        return typeArgument;
    }

    @Override
    public DetailAstImpl visitWildCardTypeArgument(JavaParser.WildCardTypeArgumentContext ctx) {
        final DetailAstImpl typeArgument = createImaginary(TokenTypes.TYPE_ARGUMENT, ctx.start);
        typeArgument.addChild(visit(ctx.annotations()));
        typeArgument.addChild(create(TokenTypes.WILDCARD_TYPE,
                (Token) ctx.QUESTION().getPayload()));

        if (Optional.ofNullable(ctx.upperBound).isPresent()) {
            final DetailAstImpl upperBound = create(TokenTypes.TYPE_UPPER_BOUNDS, ctx.upperBound);
            upperBound.addChild(visit(ctx.typeType()).getFirstChild());
            typeArgument.addChild(upperBound);
        }
        else if (Optional.ofNullable(ctx.lowerBound).isPresent()) {
            final DetailAstImpl lowerBound = create(TokenTypes.TYPE_LOWER_BOUNDS, ctx.lowerBound);
            lowerBound.addChild(visit(ctx.typeType()).getFirstChild());
            typeArgument.addChild(lowerBound);
        }

        return typeArgument;
    }

    @Override
    public DetailAstImpl visitQualifiedNameList(JavaParser.QualifiedNameListContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public DetailAstImpl visitFormalParameters(JavaParser.FormalParametersContext ctx) {
        final DetailAstImpl lparen = create(ctx.LPAREN());

        // We make a "PARAMETERS" node whether parameters exist or not
        if (ctx.formalParameterList() == null) {
            addLastSibling(lparen, createImaginary(TokenTypes.PARAMETERS, ctx.RPAREN()));
        }
        else {
            addLastSibling(lparen, visit(ctx.formalParameterList()));
        }
        addLastSibling(lparen, create(ctx.RPAREN()));
        return lparen;
    }

    @Override
    public DetailAstImpl visitFormalParameterList(JavaParser.FormalParameterListContext ctx) {
        final DetailAstImpl parameters = copy(TokenTypes.PARAMETERS, visit(ctx.children.get(0)));
        processChildren(parameters, ctx.children);
        return parameters;
    }

    @Override
    public DetailAstImpl visitFormalParameter(JavaParser.FormalParameterContext ctx) {
        final DetailAstImpl variableDeclaratorId =
                visitVariableDeclaratorId(ctx.variableDeclaratorId());
        final DetailAstImpl parameterDef = copy(TokenTypes.PARAMETER_DEF, variableDeclaratorId);
        parameterDef.addChild(variableDeclaratorId);
        return parameterDef;
    }

    @Override
    public DetailAstImpl visitLastFormalParameter(JavaParser.LastFormalParameterContext ctx) {
        final DetailAstImpl parameterDef =
                createImaginary(TokenTypes.PARAMETER_DEF, ctx.start);
        parameterDef.addChild(visit(ctx.variableDeclaratorId()));
        final DetailAstImpl ident = (DetailAstImpl) parameterDef.findFirstToken(TokenTypes.IDENT);
        ident.addPreviousSibling(create(ctx.ELLIPSIS()));
        // We attach annotations on ellipses in varargs to the 'TYPE' ast
        final DetailAstImpl type = (DetailAstImpl) parameterDef.findFirstToken(TokenTypes.TYPE);
        type.addChild(visit(ctx.annotations()));
        return parameterDef;
    }

    @Override
    public DetailAstImpl visitQualifiedName(JavaParser.QualifiedNameContext ctx) {
        final DetailAstImpl qualifiedName = create(ctx.start);
        return buildRootTree(qualifiedName, ctx);
    }

    @Override
    public DetailAstImpl visitLiteral(JavaParser.LiteralContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public DetailAstImpl visitIntegerLiteral(JavaParser.IntegerLiteralContext ctx) {
        final int[] longTypes = {
            JavaLexer.DECIMAL_LITERAL_LONG,
            JavaLexer.HEX_LITERAL_LONG,
            JavaLexer.OCT_LITERAL_LONG,
            JavaLexer.BINARY_LITERAL_LONG,
        };

        final int tokenType;
        if (TokenUtil.isOfType(ctx.start.getType(), longTypes)) {
            tokenType = TokenTypes.NUM_LONG;
        }
        else {
            tokenType = TokenTypes.NUM_INT;
        }

        return create(tokenType, ctx.start);
    }

    @Override
    public DetailAstImpl visitFloatLiteral(JavaParser.FloatLiteralContext ctx) {
        final DetailAstImpl floatLiteral;
        if (TokenUtil.isOfType(ctx.start.getType(),
                JavaLexer.DOUBLE_LITERAL, JavaLexer.HEX_DOUBLE_LITERAL)) {
            floatLiteral = create(TokenTypes.NUM_DOUBLE, ctx.start);
        }
        else {
            floatLiteral = create(TokenTypes.NUM_FLOAT, ctx.start);
        }
        return floatLiteral;
    }

    @Override
    public DetailAstImpl visitTextBlockLiteral(JavaParser.TextBlockLiteralContext ctx) {
        final DetailAstImpl textBlockLiteralBegin = create(ctx.TEXT_BLOCK_LITERAL_BEGIN());
        textBlockLiteralBegin.addChild(create(ctx.TEXT_BLOCK_CONTENT()));
        textBlockLiteralBegin.addChild(create(ctx.TEXT_BLOCK_LITERAL_END()));
        return textBlockLiteralBegin;
    }

    @Override
    public DetailAstImpl visitAnnotations(JavaParser.AnnotationsContext ctx) {
        final DetailAstImpl annotations;

        if (!ctx.createImaginaryNode && ctx.anno.isEmpty()) {
            // There are no annotations, and we don't want to create the empty node
            annotations = null;
        }
        else if (ctx.anno.isEmpty()) {
            // There are no annotations, but we want the empty node
            annotations = createImaginary(TokenTypes.ANNOTATIONS, ctx.start);
            annotations.setColumnNo(ctx.getParent().start.getCharPositionInLine());
            annotations.setLineNo(ctx.getParent().start.getLine());
        }
        else {
            // There are annotations
            annotations = createImaginary(TokenTypes.ANNOTATIONS, ctx.start);
            annotations.setColumnNo(ctx.anno.get(0).start.getCharPositionInLine());
            annotations.setLineNo(ctx.anno.get(0).start.getLine());
            processChildren(annotations, ctx.anno);
        }

        return annotations;
    }

    @Override
    public DetailAstImpl visitAnnotation(JavaParser.AnnotationContext ctx) {
        final DetailAstImpl annotation = createImaginary(TokenTypes.ANNOTATION, ctx.start);
        processChildren(annotation, ctx.children);
        return annotation;
    }

    @Override
    public DetailAstImpl visitElementValuePairs(JavaParser.ElementValuePairsContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public DetailAstImpl visitElementValuePair(JavaParser.ElementValuePairContext ctx) {
        final DetailAstImpl elementValuePair =
                createImaginary(TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR, ctx.start);
        processChildren(elementValuePair, ctx.children);
        return elementValuePair;
    }

    @Override
    public DetailAstImpl visitElementValue(JavaParser.ElementValueContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public DetailAstImpl visitElementValueArrayInitializer(
            JavaParser.ElementValueArrayInitializerContext ctx) {
        final DetailAstImpl arrayInit =
                create(TokenTypes.ANNOTATION_ARRAY_INIT, (Token) ctx.LCURLY().getPayload());
        processChildren(arrayInit, ctx.children.subList(1, ctx.children.size()));
        return arrayInit;
    }

    @Override
    public DetailAstImpl visitAnnotationTypeDeclaration(
            JavaParser.AnnotationTypeDeclarationContext ctx) {
        final DetailAstImpl annotationDef = createImaginary(TokenTypes.ANNOTATION_DEF, ctx.start);
        annotationDef.addChild(createModifiers(ctx.mods, annotationDef));
        processChildren(annotationDef, ctx.children);

        // 'CLASS_DEF' imaginary node takes position of first child
        annotationDef.setColumnNo(annotationDef.getFirstChild().getColumnNo());
        annotationDef.setLineNo(annotationDef.getFirstChild().getLineNo());
        return annotationDef;
    }

    @Override
    public DetailAstImpl visitAnnotationTypeBody(JavaParser.AnnotationTypeBodyContext ctx) {
        final DetailAstImpl objBlock = createImaginary(TokenTypes.OBJBLOCK, ctx.start);
        processChildren(objBlock, ctx.children);
        return objBlock;
    }

    @Override
    public DetailAstImpl visitAnnotationTypeElementDeclaration(
            JavaParser.AnnotationTypeElementDeclarationContext ctx) {
        final DetailAstImpl returnTree;
        if (ctx.SEMI() == null) {
            returnTree = visit(ctx.annotationTypeElementRest());
        }
        else {
            returnTree = create(ctx.SEMI());
        }
        return returnTree;
    }

    @Override
    public DetailAstImpl visitAnnotationField(JavaParser.AnnotationFieldContext ctx) {
        final DetailAstImpl dummyNode = new DetailAstImpl();
        // Since the TYPE AST is built by visitAnnotationMethodOrConstantRest(), we skip it
        // here (child [0])
        processChildren(dummyNode, Collections.singletonList(ctx.children.get(1)));
        // We also append the SEMI token to the first child (checkstyle bug) [size() - 1]
        dummyNode.getFirstChild().addChild(create(ctx.SEMI()));
        return dummyNode.getFirstChild();
    }

    @Override
    public DetailAstImpl visitAnnotationType(JavaParser.AnnotationTypeContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public DetailAstImpl visitAnnotationMethodRest(JavaParser.AnnotationMethodRestContext ctx) {
        final DetailAstImpl startToken = getStartToken(ctx.mods, ctx.type);
        final DetailAstImpl annotationFieldDef =
                copy(TokenTypes.ANNOTATION_FIELD_DEF, startToken);
        annotationFieldDef.addChild(createModifiers(ctx.mods, annotationFieldDef));
        annotationFieldDef.addChild(visit(ctx.type));
        processChildren(annotationFieldDef, ctx.children);
        return annotationFieldDef;
    }

    @Override
    public DetailAstImpl visitDefaultValue(JavaParser.DefaultValueContext ctx) {
        final DetailAstImpl defaultValue = create(ctx.LITERAL_default());
        defaultValue.addChild(visit(ctx.elementValue()));
        return defaultValue;
    }

    @Override
    public DetailAstImpl visitConstructorBlock(JavaParser.ConstructorBlockContext ctx) {
        // Set SLIST as parent
        final DetailAstImpl slist = create(TokenTypes.SLIST, ctx.start);
        // SLIST was child [0]
        processChildren(slist, ctx.children.subList(1, ctx.children.size()));
        return slist;
    }

    @Override
    public DetailAstImpl visitExplicitCtorCall(JavaParser.ExplicitCtorCallContext ctx) {
        final DetailAstImpl root;
        if (ctx.LITERAL_this() == null) {
            root = create(TokenTypes.SUPER_CTOR_CALL, (Token) ctx.LITERAL_super().getPayload());
        }
        else {
            root = create(TokenTypes.CTOR_CALL, (Token) ctx.LITERAL_this().getPayload());
        }
        root.addChild(visit(ctx.typeArguments()));
        root.addChild(visit(ctx.arguments()));
        root.addChild(create(ctx.SEMI()));
        return root;
    }

    @Override
    public DetailAstImpl visitPrimaryCtorCall(JavaParser.PrimaryCtorCallContext ctx) {
        final DetailAstImpl root = create(TokenTypes.SUPER_CTOR_CALL,
                (Token) ctx.LITERAL_super().getPayload());
        root.addChild(visit(ctx.expr()));
        root.addChild(create(ctx.DOT()));
        root.addChild(visit(ctx.typeArguments()));
        root.addChild(visit(ctx.arguments()));
        root.addChild(create(ctx.SEMI()));
        return root;
    }

    @Override
    public DetailAstImpl visitBlock(JavaParser.BlockContext ctx) {
        // Set SLIST as parent
        final DetailAstImpl slist = create(TokenTypes.SLIST, ctx.start);
        // SLIST was child [0]
        processChildren(slist, ctx.children.subList(1, ctx.children.size()));
        return slist;
    }

    @Override
    public DetailAstImpl visitLocalVar(JavaParser.LocalVarContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public DetailAstImpl visitBlockStat(JavaParser.BlockStatContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public DetailAstImpl visitAssertExp(JavaParser.AssertExpContext ctx) {
        final DetailAstImpl assertExp = create(ctx.ASSERT());
        // child[0] is 'ASSERT'
        processChildren(assertExp, ctx.children.subList(1, ctx.children.size()));
        return assertExp;
    }

    @Override
    public DetailAstImpl visitIfStat(JavaParser.IfStatContext ctx) {
        final DetailAstImpl ifStat = create(ctx.LITERAL_if());
        // child[0] is 'LITERAL_if'
        processChildren(ifStat, ctx.children.subList(1, ctx.children.size()));
        return ifStat;
    }

    @Override
    public DetailAstImpl visitForStat(JavaParser.ForStatContext ctx) {
        final DetailAstImpl forInit = create(ctx.start);
        // child[0] is LITERAL_FOR
        processChildren(forInit, ctx.children.subList(1, ctx.children.size()));
        return forInit;
    }

    @Override
    public DetailAstImpl visitWhileStat(JavaParser.WhileStatContext ctx) {
        final DetailAstImpl whileStatement = create(ctx.start);
        // 'LITERAL_WHILE' is child[0]
        processChildren(whileStatement, ctx.children.subList(1, ctx.children.size()));
        return whileStatement;
    }

    @Override
    public DetailAstImpl visitDoStat(JavaParser.DoStatContext ctx) {
        final DetailAstImpl doStatement = create(ctx.start);
        // 'LITERAL_DO' is child[0]
        doStatement.addChild(visit(ctx.statement()));
        // We make 'LITERAL_WHILE' into 'DO_WHILE' in this case
        doStatement.addChild(create(TokenTypes.DO_WHILE,
                (Token) ctx.LITERAL_while().getPayload()));
        doStatement.addChild(visit(ctx.parExpression()));
        doStatement.addChild(create(ctx.SEMI()));
        return doStatement;
    }

    @Override
    public DetailAstImpl visitTryStat(JavaParser.TryStatContext ctx) {
        final DetailAstImpl tryStat = create(ctx.start);
        // child[0] is 'LITERAL_TRY'
        processChildren(tryStat, ctx.children.subList(1, ctx.children.size()));
        return tryStat;
    }

    @Override
    public DetailAstImpl visitTryWithResourceStat(JavaParser.TryWithResourceStatContext ctx) {
        final DetailAstImpl tryWithResources = create(ctx.LITERAL_try());
        // child[0] is 'LITERAL_try'
        processChildren(tryWithResources, ctx.children.subList(1, ctx.children.size()));
        return tryWithResources;
    }

    @Override
    public DetailAstImpl visitSyncStat(JavaParser.SyncStatContext ctx) {
        final DetailAstImpl syncStatement = create(ctx.start);
        // child[0] is 'LITERAL_SYNCHRONIZED'
        processChildren(syncStatement, ctx.children.subList(1, ctx.children.size()));
        return syncStatement;
    }

    @Override
    public DetailAstImpl visitReturnStat(JavaParser.ReturnStatContext ctx) {
        final DetailAstImpl returnStat = create(ctx.LITERAL_return());
        // child[0] is 'LITERAL_return'
        processChildren(returnStat, ctx.children.subList(1, ctx.children.size()));
        return returnStat;
    }

    @Override
    public DetailAstImpl visitThrowStat(JavaParser.ThrowStatContext ctx) {
        final DetailAstImpl throwStat = create(ctx.LITERAL_throw());
        // child[0] is 'LITERAL_throw'
        processChildren(throwStat, ctx.children.subList(1, ctx.children.size()));
        return throwStat;
    }

    @Override
    public DetailAstImpl visitBreakStat(JavaParser.BreakStatContext ctx) {
        final DetailAstImpl literalBreak = create(ctx.LITERAL_break());
        // child[0] is 'LITERAL_break'
        processChildren(literalBreak, ctx.children.subList(1, ctx.children.size()));
        return literalBreak;
    }

    @Override
    public DetailAstImpl visitContinueStat(JavaParser.ContinueStatContext ctx) {
        final DetailAstImpl continueStat = create(ctx.LITERAL_continue());
        // child[0] is 'LITERAL_continue'
        processChildren(continueStat, ctx.children.subList(1, ctx.children.size()));
        return continueStat;
    }

    @Override
    public DetailAstImpl visitEmptyStat(JavaParser.EmptyStatContext ctx) {
        return create(TokenTypes.EMPTY_STAT, ctx.start);
    }

    @Override
    public DetailAstImpl visitExpStat(JavaParser.ExpStatContext ctx) {
        final DetailAstImpl expStatRoot = visit(ctx.statementExpression);
        expStatRoot.setNextSibling(create(ctx.SEMI()));
        return expStatRoot;
    }

    @Override
    public DetailAstImpl visitLabelStat(JavaParser.LabelStatContext ctx) {
        final DetailAstImpl labelStat = create(TokenTypes.LABELED_STAT,
                (Token) ctx.COLON().getPayload());
        labelStat.addChild(visit(ctx.id()));
        labelStat.addChild(visit(ctx.statement()));
        return labelStat;
    }

    @Override
    public DetailAstImpl visitYieldStat(JavaParser.YieldStatContext ctx) {
        final DetailAstImpl yieldParent = create(ctx.LITERAL_yield());
        // LITERAL_yield is child[0]
        processChildren(yieldParent, ctx.children.subList(1, ctx.children.size()));
        return yieldParent;
    }

    @Override
    public DetailAstImpl visitSwitchExpressionOrStatement(
            JavaParser.SwitchExpressionOrStatementContext ctx) {
        final DetailAstImpl switchStat = create(ctx.LITERAL_switch());
        switchStat.addChild(visit(ctx.parExpression()));
        switchStat.addChild(create(ctx.LCURLY()));
        switchStat.addChild(visit(ctx.switchBlock()));
        switchStat.addChild(create(ctx.RCURLY()));
        return switchStat;
    }

    @Override
    public DetailAstImpl visitSwitchRules(JavaParser.SwitchRulesContext ctx) {
        final DetailAstImpl dummyRoot = new DetailAstImpl();
        ctx.switchLabeledRule().forEach(switchLabeledRuleContext -> {
            final DetailAstImpl switchRule = visit(switchLabeledRuleContext);
            final DetailAstImpl switchRuleParent = copy(TokenTypes.SWITCH_RULE, switchRule);
            switchRuleParent.addChild(switchRule);
            dummyRoot.addChild(switchRuleParent);
        });
        return dummyRoot.getFirstChild();
    }

    @Override
    public DetailAstImpl visitSwitchBlocks(JavaParser.SwitchBlocksContext ctx) {
        final DetailAstImpl dummyRoot = new DetailAstImpl();
        ctx.groups.forEach(group -> dummyRoot.addChild(visit(group)));

        // Add any empty switch labels to end of statement in one 'CASE_GROUP'
        if (!ctx.emptyLabels.isEmpty()) {
            final DetailAstImpl emptyLabelParent =
                    copy(TokenTypes.CASE_GROUP, visit(ctx.emptyLabels.get(0)));
            ctx.emptyLabels.forEach(label -> emptyLabelParent.addChild(visit(label)));
            dummyRoot.addChild(emptyLabelParent);
        }
        return dummyRoot.getFirstChild();
    }

    @Override
    public DetailAstImpl visitSwitchLabeledExpression(
            JavaParser.SwitchLabeledExpressionContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public DetailAstImpl visitSwitchLabeledBlock(JavaParser.SwitchLabeledBlockContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public DetailAstImpl visitSwitchLabeledThrow(JavaParser.SwitchLabeledThrowContext ctx) {
        final DetailAstImpl switchLabel = visit(ctx.switchLabel());
        addLastSibling(switchLabel, create(ctx.LAMBDA()));
        final DetailAstImpl literalThrow = create(ctx.LITERAL_throw());
        literalThrow.addChild(visit(ctx.expression()));
        literalThrow.addChild(create(ctx.SEMI()));
        addLastSibling(switchLabel, literalThrow);
        return switchLabel;
    }

    @Override
    public DetailAstImpl visitElseStat(JavaParser.ElseStatContext ctx) {
        final DetailAstImpl elseStat = create(ctx.LITERAL_else());
        // child[0] is 'LITERAL_else'
        processChildren(elseStat, ctx.children.subList(1, ctx.children.size()));
        return elseStat;
    }

    @Override
    public DetailAstImpl visitCatchClause(JavaParser.CatchClauseContext ctx) {
        final DetailAstImpl catchClause = create(TokenTypes.LITERAL_CATCH,
                (Token) ctx.LITERAL_catch().getPayload());
        // 'LITERAL_catch' is child[0]
        processChildren(catchClause, ctx.children.subList(1, ctx.children.size()));
        return catchClause;
    }

    @Override
    public DetailAstImpl visitCatchParameter(JavaParser.CatchParameterContext ctx) {
        final DetailAstImpl catchParameterDef = copy(TokenTypes.PARAMETER_DEF,
                visit(ctx.children.get(0)));
        catchParameterDef.addChild(createModifiers(ctx.mods, catchParameterDef));
        // filter mods
        processChildren(catchParameterDef, ctx.children.stream()
                .filter(child -> !(child instanceof JavaParser.VariableModifierContext))
                .collect(Collectors.toList()));
        return catchParameterDef;
    }

    @Override
    public DetailAstImpl visitCatchType(JavaParser.CatchTypeContext ctx) {
        final DetailAstImpl type = copy(TokenTypes.TYPE, visit(ctx.children.get(0)));
        processChildren(type, ctx.children);
        return type;
    }

    @Override
    public DetailAstImpl visitFinallyBlock(JavaParser.FinallyBlockContext ctx) {
        final DetailAstImpl finallyBlock = create(ctx.LITERAL_finally());
        // child[0] is 'LITERAL_finally'
        processChildren(finallyBlock, ctx.children.subList(1, ctx.children.size()));
        return finallyBlock;
    }

    @Override
    public DetailAstImpl visitResourceSpecification(JavaParser.ResourceSpecificationContext ctx) {
        final DetailAstImpl resourceSpecification =
                createImaginary(TokenTypes.RESOURCE_SPECIFICATION, ctx.start);
        processChildren(resourceSpecification, ctx.children);
        return resourceSpecification;
    }

    @Override
    public DetailAstImpl visitResources(JavaParser.ResourcesContext ctx) {
        final DetailAstImpl firstResource = visit(ctx.resource(0));
        // Imaginary 'RESOURCES` node takes position of first resource
        final DetailAstImpl resources = copy(TokenTypes.RESOURCES, firstResource);
        resources.addChild(firstResource);
        processChildren(resources, ctx.children.subList(1, ctx.children.size()));
        return resources;
    }

    @Override
    public DetailAstImpl visitResourceDeclaration(JavaParser.ResourceDeclarationContext ctx) {
        final DetailAstImpl resource = createImaginary(TokenTypes.RESOURCE, ctx.start);
        resource.addChild(visit(ctx.variableDeclaratorId()));

        final DetailAstImpl assign = create(ctx.ASSIGN());
        resource.addChild(assign);
        assign.addChild(visit(ctx.expression()));
        return resource;
    }

    @Override
    public DetailAstImpl visitVariableAccess(JavaParser.VariableAccessContext ctx) {
        final DetailAstImpl resource;
        if (ctx.accessList.isEmpty()) {
            resource = createImaginary(TokenTypes.RESOURCE, ctx.start);
            resource.addChild(visit(ctx.id()));
        }
        else {
            final DetailAstPair currentAst = new DetailAstPair();
            ctx.accessList.forEach(fieldAccess -> {
                DetailAstPair.addASTChild(currentAst, visit(fieldAccess.expr()));
                DetailAstPair.makeAstRoot(currentAst, create(fieldAccess.DOT()));
            });
            resource = copy(TokenTypes.RESOURCE, currentAst.root);
            resource.addChild(currentAst.root);
            resource.getFirstChild().addChild(visit(ctx.id()));
        }
        return resource;
    }

    @Override
    public DetailAstImpl visitSwitchBlockStatementGroup(
            JavaParser.SwitchBlockStatementGroupContext ctx) {
        final DetailAstImpl caseGroup = copy(TokenTypes.CASE_GROUP, visit(ctx.switchLabel(0)));
        processChildren(caseGroup, ctx.switchLabel());
        final DetailAstImpl sList = copy(TokenTypes.SLIST, visit(ctx.slists.get(0)));
        processChildren(sList, ctx.slists);
        caseGroup.addChild(sList);
        return caseGroup;
    }

    @Override
    public DetailAstImpl visitCaseLabel(JavaParser.CaseLabelContext ctx) {
        final DetailAstImpl caseLabel = create(ctx.LITERAL_case());
        // child [0] is 'LITERAL_case'
        processChildren(caseLabel, ctx.children.subList(1, ctx.children.size()));
        return caseLabel;
    }

    @Override
    public DetailAstImpl visitDefaultLabel(JavaParser.DefaultLabelContext ctx) {
        final DetailAstImpl defaultLabel = create(ctx.LITERAL_default());
        if (ctx.COLON() != null) {
            defaultLabel.addChild(create(ctx.COLON()));
        }
        return defaultLabel;
    }

    @Override
    public DetailAstImpl visitCaseConstants(JavaParser.CaseConstantsContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public DetailAstImpl visitEnhancedFor(JavaParser.EnhancedForContext ctx) {
        final DetailAstImpl leftParen = create(ctx.LPAREN());
        final DetailAstImpl enhancedForControl =
                 visit(ctx.enhancedForControl());
        final DetailAstImpl forEachClause = copy(TokenTypes.FOR_EACH_CLAUSE,
                enhancedForControl);
        forEachClause.addChild(enhancedForControl);
        addLastSibling(leftParen, forEachClause);
        addLastSibling(leftParen, create(ctx.RPAREN()));
        return leftParen;
    }

    @Override
    public DetailAstImpl visitForFor(JavaParser.ForForContext ctx) {
        final DetailAstImpl dummyRoot = new DetailAstImpl();

        dummyRoot.addChild(create(ctx.LPAREN()));

        if (ctx.forInit() == null) {
            final DetailAstImpl imaginaryForInitParent =
                    createImaginary(TokenTypes.FOR_INIT, ctx.SEMI(0));
            dummyRoot.addChild(imaginaryForInitParent);
        }
        else {
            dummyRoot.addChild(visit(ctx.forInit()));
        }

        dummyRoot.addChild(create(ctx.SEMI(0)));

        if (ctx.forCond == null) {
            final DetailAstImpl imaginaryForCondParent =
                    createImaginary(TokenTypes.FOR_CONDITION, ctx.SEMI(1));
            dummyRoot.addChild(imaginaryForCondParent);
        }
        else {
            final DetailAstImpl child = visit(ctx.forCond);
            final DetailAstImpl forCondParent = copy(TokenTypes.FOR_CONDITION, child);
            forCondParent.addChild(child);
            dummyRoot.addChild(forCondParent);
        }

        dummyRoot.addChild(create(ctx.SEMI(1)));

        if (ctx.forUpdate == null) {
            final DetailAstImpl imaginaryForItParent =
                    createImaginary(TokenTypes.FOR_ITERATOR, ctx.RPAREN());
            dummyRoot.addChild(imaginaryForItParent);
        }
        else {
            final DetailAstImpl child = visit(ctx.forUpdate);
            final DetailAstImpl forItParent = copy(TokenTypes.FOR_ITERATOR, child);
            forItParent.addChild(child);
            dummyRoot.addChild(forItParent);
        }

        dummyRoot.addChild(create(ctx.RPAREN()));

        return dummyRoot.getFirstChild();
    }

    @Override
    public DetailAstImpl visitForInit(JavaParser.ForInitContext ctx) {
        final DetailAstImpl forInit = copy(TokenTypes.FOR_INIT, visit(ctx.children.get(0)));
        processChildren(forInit, ctx.children);
        return forInit;
    }

    @Override
    public DetailAstImpl visitEnhancedForControl(JavaParser.EnhancedForControlContext ctx) {
        final DetailAstImpl variableDeclaratorId =
                 visit(ctx.variableDeclaratorId());
        final DetailAstImpl variableDef = copy(TokenTypes.VARIABLE_DEF, variableDeclaratorId);
        variableDef.addChild(variableDeclaratorId);

        // Add colon
        addLastSibling(variableDef, create(ctx.COLON()));
        // Add expression
        addLastSibling(variableDef, visit(ctx.expression()));
        return variableDef;
    }

    @Override
    public DetailAstImpl visitParExpression(JavaParser.ParExpressionContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public DetailAstImpl visitExpressionList(JavaParser.ExpressionListContext ctx) {
        // ELIST node is just an imaginary node that is a copy of it's child
        final DetailAstImpl child = visit(ctx.startExp);
        final DetailAstImpl elist = copy(TokenTypes.ELIST, child);
        elist.addChild(child);
        // First 'EXPR' is child[0]
        processChildren(elist, ctx.children.subList(1, ctx.children.size()));
        return elist;
    }

    @Override
    public DetailAstImpl visitMethod(JavaParser.MethodContext ctx) {
        final DetailAstImpl methodCall = create(TokenTypes.METHOD_CALL,
                (Token) ctx.LPAREN().getPayload());
        // We always add an 'ELIST' node
        final DetailAstImpl expressionList = Optional.ofNullable(visit(ctx.expressionList()))
                .orElse(createImaginary(TokenTypes.ELIST, ctx.RPAREN()));

        methodCall.addChild(expressionList);
        methodCall.addChild(create((Token) ctx.RPAREN().getPayload()));
        return methodCall;
    }

    @Override
    public DetailAstImpl visitMethodExp(JavaParser.MethodExpContext ctx) {
        final DetailAstImpl root = visit(ctx.mCall);
        final DetailAstImpl dot = create(ctx.DOT());
        root.getFirstChild().addPreviousSibling(dot);
        dot.addChild(visit(ctx.expr()));
        // Add last IDENT from 'methodCall' production here
        dot.addChild(visit(ctx.mCall.children.get(0)));
        return root;
    }

    @Override
    public DetailAstImpl visitExpression(JavaParser.ExpressionContext ctx) {
        final DetailAstImpl expression = visit(ctx.expr());
        DetailAstImpl exprRoot = copy(TokenTypes.EXPR, expression);
        exprRoot.addChild(expression);

        final int[] expressionsWithNoExprRoot = {
            TokenTypes.CTOR_CALL,
            TokenTypes.SUPER_CTOR_CALL,
            TokenTypes.LAMBDA,
        };

        if (TokenUtil.isOfType(expression, expressionsWithNoExprRoot)) {
            exprRoot = exprRoot.getFirstChild();
        }

        return exprRoot;
    }

    @Override
    public DetailAstImpl visitPrimaryExp(JavaParser.PrimaryExpContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public DetailAstImpl visitParenPrimary(JavaParser.ParenPrimaryContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public DetailAstImpl visitTokenPrimary(JavaParser.TokenPrimaryContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public DetailAstImpl visitClassRefPrimary(JavaParser.ClassRefPrimaryContext ctx) {
        final DetailAstImpl dot = create(ctx.DOT());
        final DetailAstImpl primaryTypeNoArray = visit(ctx.type);
        dot.addChild(primaryTypeNoArray);
        if (primaryTypeNoArray.getType() == TokenTypes.DOT) {
            // We append '[]' to the qualified name 'TYPE' `ast
            ctx.arrayDeclarator().forEach(child -> {
                primaryTypeNoArray.addChild(visit(child));
            });
        }
        else {
            ctx.arrayDeclarator().forEach(child -> {
                addLastSibling(primaryTypeNoArray, visit(child));
            });
        }
        dot.addChild(create(ctx.LITERAL_class()));
        return dot;
    }

    @Override
    public DetailAstImpl visitPrimitivePrimary(JavaParser.PrimitivePrimaryContext ctx) {
        final DetailAstImpl dot = create(ctx.DOT());
        final DetailAstImpl primaryTypeNoArray = visit(ctx.type);
        dot.addChild(primaryTypeNoArray);
        ctx.arrayDeclarator().forEach(child -> dot.addChild(visit(child)));
        dot.addChild(create(ctx.LITERAL_class()));
        return dot;
    }

    @Override
    public DetailAstImpl visitPrimaryClassOrInterfaceTypeNoArray(
            JavaParser.PrimaryClassOrInterfaceTypeNoArrayContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public DetailAstImpl visitPrimaryPrimitiveTypeNoArray(
            JavaParser.PrimaryPrimitiveTypeNoArrayContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public DetailAstImpl visitRefOp(JavaParser.RefOpContext ctx) {
        final DetailAstImpl bop = create(ctx.bop);
        final DetailAstImpl leftChild = visit(ctx.expr());
        final DetailAstImpl rightChild = create(TokenTypes.IDENT, ctx.stop);
        bop.addChild(leftChild);
        bop.addChild(rightChild);
        return bop;
    }

    @Override
    public DetailAstImpl visitBinOp(JavaParser.BinOpContext ctx) {
        final DetailAstImpl bop = create(ctx.bop);

        // To improve performance, we iterate through binary operations
        // since they are frequently deeply nested.
        final List<JavaParser.BinOpContext> binOpList = new ArrayList<>();
        ParseTree firstExpression = ctx.expr(0);
        while (firstExpression instanceof JavaParser.BinOpContext) {
            // Get all nested binOps
            binOpList.add((JavaParser.BinOpContext) firstExpression);
            firstExpression = ((JavaParser.BinOpContext) firstExpression).expr(0);
        }

        if (binOpList.isEmpty()) {
            final DetailAstImpl leftChild = visit(ctx.children.get(0));
            bop.addChild(leftChild);
        }
        else {
            // Map all descendants to individual AST's since we can parallelize this
            // operation
            final Queue<DetailAstImpl> descendantList = binOpList.parallelStream()
                    .map(this::getInnerBopAst)
                    .collect(Collectors.toCollection(ConcurrentLinkedQueue::new));

            bop.addChild(descendantList.poll());
            DetailAstImpl pointer = bop.getFirstChild();
            // Build tree
            for (DetailAstImpl descendant : descendantList) {
                pointer.getFirstChild().addPreviousSibling(descendant);
                pointer = descendant;
            }
        }

        processChildren(bop, Collections.singletonList(ctx.children.get(2)));
        return bop;
    }

    /**
     * Builds the binary operation (binOp) AST.
     *
     * @param descendant the BinOpContext to build AST from
     * @return binOp AST
     */
    private DetailAstImpl getInnerBopAst(JavaParser.BinOpContext descendant) {
        final DetailAstImpl innerBop = create(descendant.bop);
        if (!(descendant.expr(0) instanceof JavaParser.BinOpContext)) {
            innerBop.addChild(visit(descendant.expr(0)));
        }
        innerBop.addChild(visit(descendant.expr(1)));
        return innerBop;
    }

    @Override
    public DetailAstImpl visitInitExp(JavaParser.InitExpContext ctx) {
        final DetailAstImpl dot = create(ctx.bop);
        dot.addChild(visit(ctx.expr()));
        final DetailAstImpl literalNew = create(ctx.LITERAL_new());
        literalNew.addChild(visit(ctx.innerCreator()));
        dot.addChild(literalNew);
        return dot;
    }

    @Override
    public DetailAstImpl visitSuperExp(JavaParser.SuperExpContext ctx) {
        final DetailAstImpl bop = create(ctx.bop);
        bop.addChild(visit(ctx.expr()));
        bop.addChild(create(ctx.LITERAL_super()));
        final DetailAstImpl superSuffixParent = visit(ctx.superSuffix());

        DetailAstImpl firstChild = superSuffixParent.getFirstChild();
        while (firstChild.getFirstChild() != null) {
            firstChild = firstChild.getFirstChild();
        }
        firstChild.addPreviousSibling(bop);

        return superSuffixParent;
    }

    @Override
    public DetailAstImpl visitInvOp(JavaParser.InvOpContext ctx) {
        final DetailAstPair currentAst = new DetailAstPair();

        final DetailAstImpl returnAst = visit(ctx.expr());
        DetailAstPair.addASTChild(currentAst, returnAst);
        DetailAstPair.makeAstRoot(currentAst, create(ctx.bop));

        DetailAstPair.addASTChild(currentAst,
                 visit(ctx.nonWildcardTypeArguments()));
        DetailAstPair.addASTChild(currentAst, visit(ctx.id()));
        final DetailAstImpl lparen = create(TokenTypes.METHOD_CALL,
                (Token) ctx.LPAREN().getPayload());
        DetailAstPair.makeAstRoot(currentAst, lparen);

        // We always add an 'ELIST' node
        final DetailAstImpl expressionList = Optional.ofNullable(visit(ctx.expressionList()))
                .orElse(createImaginary(TokenTypes.ELIST, ctx.RPAREN()));

        DetailAstPair.addASTChild(currentAst, expressionList);
        DetailAstPair.addASTChild(currentAst, create(ctx.RPAREN()));

        return currentAst.root;
    }

    @Override
    public DetailAstImpl visitIndexOp(JavaParser.IndexOpContext ctx) {
        // LBRACK -> INDEX_OP is root of this AST
        final DetailAstImpl indexOp = create(TokenTypes.INDEX_OP,
                (Token) ctx.LBRACK().getPayload());

        // add expression(IDENT) on LHS
        indexOp.addChild(visit(ctx.expr(0)));

        // create imaginary node for expression on RHS
        final DetailAstImpl expr = visit(ctx.expr(1));
        final DetailAstImpl imaginaryExpr = copy(TokenTypes.EXPR, expr);
        imaginaryExpr.addChild(expr);
        indexOp.addChild(imaginaryExpr);

        // complete AST by adding RBRACK
        indexOp.addChild(create(ctx.RBRACK()));

        return indexOp;
    }

    @Override
    public DetailAstImpl visitSimpleMethodExp(JavaParser.SimpleMethodExpContext ctx) {
        final DetailAstImpl root = visit(ctx.methodCall());
        root.getFirstChild().addPreviousSibling(create(ctx.mCall.start));
        return root;
    }

    @Override
    public DetailAstImpl visitNewExp(JavaParser.NewExpContext ctx) {
        final DetailAstImpl newExp = create(ctx.LITERAL_new());
        // child [0] is LITERAL_new
        processChildren(newExp, ctx.children.subList(1, ctx.children.size()));
        return newExp;
    }

    @Override
    public DetailAstImpl visitCastExp(JavaParser.CastExpContext ctx) {
        final DetailAstImpl cast = create(TokenTypes.TYPECAST, (Token) ctx.LPAREN().getPayload());
        // child [0] is LPAREN
        processChildren(cast, ctx.children.subList(1, ctx.children.size()));

        // Cast is always the child of an imaginary EXPR node
        final DetailAstImpl exprRoot = copy(TokenTypes.EXPR, cast);
        exprRoot.addChild(cast);
        return exprRoot.getFirstChild();
    }

    @Override
    public DetailAstImpl visitPostfix(JavaParser.PostfixContext ctx) {
        final DetailAstImpl postfix;
        if (ctx.postfix.getType() == JavaLexer.INC) {
            postfix = create(TokenTypes.POST_INC, ctx.postfix);
        }
        else {
            postfix = create(TokenTypes.POST_DEC, ctx.postfix);
        }
        postfix.addChild(visit(ctx.expr()));
        return postfix;
    }

    @Override
    public DetailAstImpl visitPrefix(JavaParser.PrefixContext ctx) {
        final int tokenType;
        switch (ctx.prefix.getType()) {
            case JavaLexer.PLUS:
                tokenType = TokenTypes.UNARY_PLUS;
                break;
            case JavaLexer.MINUS:
                tokenType = TokenTypes.UNARY_MINUS;
                break;
            default:
                tokenType = ctx.prefix.getType();
        }
        final DetailAstImpl prefix = create(tokenType, ctx.prefix);
        prefix.addChild(visit(ctx.expr()));
        return prefix;
    }

    @Override
    public DetailAstImpl visitBitOp(JavaParser.BitOpContext ctx) {
        final DetailAstImpl shiftOperation;

        // We determine the type of shift operation in the parser, instead of the
        // lexer as in older grammars. This makes it easier to parse type parameters
        // and less than/ greater than operators in general.
        if (ctx.LT().size() == LEFT_SHIFT.length()) {
            shiftOperation = create(TokenTypes.SL, (Token) ctx.LT(0).getPayload());
            shiftOperation.setText(LEFT_SHIFT);
        }
        else if (ctx.GT().size() == UNSIGNED_RIGHT_SHIFT.length()) {
            shiftOperation = create(TokenTypes.BSR, (Token) ctx.GT(0).getPayload());
            shiftOperation.setText(UNSIGNED_RIGHT_SHIFT);
        }
        else {
            shiftOperation = create(TokenTypes.SR, (Token) ctx.GT(0).getPayload());
            shiftOperation.setText(RIGHT_SHIFT);
        }
        // Add child on LHS
        shiftOperation.addChild(visit(ctx.expr(0)));
        // Add child on RHS
        shiftOperation.addChild(visit(ctx.expr(1)));

        return shiftOperation;
    }

    @Override
    public DetailAstImpl visitInstanceOfExp(JavaParser.InstanceOfExpContext ctx) {
        final DetailAstImpl literalInstanceOf = create(ctx.LITERAL_instanceof());
        literalInstanceOf.addChild(visit(ctx.expr()));
        literalInstanceOf.addChild(visit(ctx.children.get(2)));
        return literalInstanceOf;
    }

    @Override
    public DetailAstImpl visitTernaryOp(JavaParser.TernaryOpContext ctx) {
        final DetailAstImpl root = create(ctx.QUESTION());
        processChildren(root, ctx.children.stream()
                        .filter(child -> !child.equals(ctx.QUESTION()))
                        .collect(Collectors.toList()));
        return root;
    }

    @Override
    public DetailAstImpl visitLambdaExp(JavaParser.LambdaExpContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public DetailAstImpl visitMethodRef(JavaParser.MethodRefContext ctx) {
        final DetailAstImpl doubleColon = create(TokenTypes.METHOD_REF,
                (Token) ctx.DOUBLE_COLON().getPayload());
        final List<ParseTree> children = ctx.children.stream()
                .filter(child -> !child.equals(ctx.DOUBLE_COLON()))
                .collect(Collectors.toList());
        processChildren(doubleColon, children);
        return doubleColon;
    }

    @Override
    public DetailAstImpl visitTypeCastParameters(JavaParser.TypeCastParametersContext ctx) {
        final DetailAstImpl typeType = visit(ctx.typeType(0));
        for (int i = 0; i < ctx.BAND().size(); i++) {
            addLastSibling(typeType, create(TokenTypes.TYPE_EXTENSION_AND,
                                (Token) ctx.BAND(i).getPayload()));
            addLastSibling(typeType, visit(ctx.typeType(i + 1)));
        }
        return typeType;
    }

    @Override
    public DetailAstImpl visitLambdaExpression(JavaParser.LambdaExpressionContext ctx) {
        final DetailAstImpl lambda = create(ctx.LAMBDA());
        lambda.addChild(visit(ctx.lambdaParameters()));
        lambda.addChild(visit(ctx.lambdaBody()));
        return lambda;
    }

    @Override
    public DetailAstImpl visitSingleLambdaParam(JavaParser.SingleLambdaParamContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public DetailAstImpl visitFormalLambdaParam(JavaParser.FormalLambdaParamContext ctx) {
        final DetailAstImpl lparen = create(ctx.LPAREN());

        // We add an 'PARAMETERS' node here whether it exists or not
        final DetailAstImpl parameters = Optional.ofNullable(visit(ctx.formalParameterList()))
                .orElse(createImaginary(TokenTypes.PARAMETERS, ctx.RPAREN()));
        addLastSibling(lparen, parameters);
        addLastSibling(lparen, create(ctx.RPAREN()));
        return lparen;
    }

    @Override
    public DetailAstImpl visitMultiLambdaParam(JavaParser.MultiLambdaParamContext ctx) {
        final DetailAstImpl lparen = create(ctx.LPAREN());
        addLastSibling(lparen, visit(ctx.multiLambdaParams()));
        addLastSibling(lparen, create(ctx.RPAREN()));
        return lparen;
    }

    @Override
    public DetailAstImpl visitMultiLambdaParams(JavaParser.MultiLambdaParamsContext ctx) {
        final DetailAstImpl parameters = createImaginary(TokenTypes.PARAMETERS, ctx.start);
        parameters.addChild(createLambdaParameter(ctx.id(0)));

        for (int i = 0; i < ctx.COMMA().size(); i++) {
            parameters.addChild(create(ctx.COMMA(i)));
            parameters.addChild(createLambdaParameter(ctx.id(i + 1)));
        }
        return parameters;
    }

    /**
     * Creates a 'PARAMETER_DEF' node for a lambda expression, with
     * imaginary modifier and type nodes.
     *
     * @param ctx the IdContext to create imaginary nodes for
     * @return DetailAstImpl of lambda parameter
     */
    private DetailAstImpl createLambdaParameter(JavaParser.IdContext ctx) {
        final DetailAstImpl parameter = visit(ctx);
        parameter.setType(TokenTypes.PARAMETER_DEF);
        parameter.setText(TokenUtil.getTokenName(TokenTypes.PARAMETER_DEF));
        final DetailAstImpl modifiers = copy(TokenTypes.MODIFIERS, parameter);
        final DetailAstImpl type = copy(TokenTypes.TYPE, parameter);
        parameter.addChild(modifiers);
        parameter.addChild(type);
        parameter.addChild(visit(ctx));
        return parameter;
    }

    @Override
    public DetailAstImpl visitCreator(JavaParser.CreatorContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public DetailAstImpl visitCreatedNameObject(JavaParser.CreatedNameObjectContext ctx) {
        final DetailAstPair currentAST = new DetailAstPair();
        DetailAstPair.addASTChild(currentAST, visit(ctx.id()));

        if (ctx.typeArgumentsOrDiamond() != null) {
            DetailAstPair.addASTChild(currentAST, visit(ctx.typeArgumentsOrDiamond()));
        }

        // This is how we build the type arguments/ qualified name tree
        for (int i = 0; i < ctx.extended.size(); i++) {
            final ParserRuleContext extendedContext = ctx.extended.get(i);
            final DetailAstImpl dot = create(extendedContext.start);
            DetailAstPair.makeAstRoot(currentAST, dot);
            final List<ParseTree> childList = extendedContext
                    .children.subList(1, extendedContext.children.size());
            processChildren(dot, childList);
        }

        return currentAST.root;
    }

    @Override
    public DetailAstImpl visitCreatedNamePrimitive(JavaParser.CreatedNamePrimitiveContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public DetailAstImpl visitInnerCreator(JavaParser.InnerCreatorContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public DetailAstImpl visitArrayCreatorRest(JavaParser.ArrayCreatorRestContext ctx) {
        final DetailAstImpl arrayDeclarator = create(TokenTypes.ARRAY_DECLARATOR,
                (Token) ctx.LBRACK().getPayload());
        // child[0] is LBRACK
        for (int i = 1; i < ctx.children.size(); i++) {
            if (ctx.children.get(i) == ctx.RBRACK()) {
                arrayDeclarator.addChild(create(ctx.RBRACK()));
            }
            else if (ctx.children.get(i) == ctx.expression()) {
                // Handle '[8]', etc.
                arrayDeclarator.addChild(visit(ctx.expression()));
            }
            else {
                addLastSibling(arrayDeclarator, visit(ctx.children.get(i)));
            }
        }
        return arrayDeclarator;
    }

    @Override
    public DetailAstImpl visitBracketsWithExp(JavaParser.BracketsWithExpContext ctx) {
        final DetailAstImpl dummyRoot = new DetailAstImpl();
        dummyRoot.addChild(visit(ctx.annotations()));
        final DetailAstImpl arrayDeclarator =
                create(TokenTypes.ARRAY_DECLARATOR, (Token) ctx.LBRACK().getPayload());
        arrayDeclarator.addChild(visit(ctx.expression()));
        arrayDeclarator.addChild(create(ctx.stop));
        dummyRoot.addChild(arrayDeclarator);
        return dummyRoot.getFirstChild();
    }

    @Override
    public DetailAstImpl visitClassCreatorRest(JavaParser.ClassCreatorRestContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public DetailAstImpl visitDiamond(JavaParser.DiamondContext ctx) {
        final DetailAstImpl typeArguments =
                createImaginary(TokenTypes.TYPE_ARGUMENTS, ctx.LT());
        typeArguments.addChild(create(TokenTypes.GENERIC_START,
                (Token) ctx.LT().getPayload()));
        typeArguments.addChild(create(TokenTypes.GENERIC_END,
                (Token) ctx.GT().getPayload()));
        return typeArguments;
    }

    @Override
    public DetailAstImpl visitTypeArgs(JavaParser.TypeArgsContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public DetailAstImpl visitNonWildcardTypeArguments(
            JavaParser.NonWildcardTypeArgumentsContext ctx) {
        final DetailAstImpl typeArguments = createImaginary(TokenTypes.TYPE_ARGUMENTS, ctx.start);
        typeArguments.addChild(create(TokenTypes.GENERIC_START, (Token) ctx.LT().getPayload()));
        typeArguments.addChild(visit(ctx.typeArgumentsTypeList()));
        typeArguments.addChild(create(TokenTypes.GENERIC_END, (Token) ctx.GT().getPayload()));
        return typeArguments;
    }

    @Override
    public DetailAstImpl visitTypeArgumentsTypeList(JavaParser.TypeArgumentsTypeListContext ctx) {
        final DetailAstImpl firstIdent = visit(ctx.typeType(0));
        final DetailAstImpl firstTypeArgument = copy(TokenTypes.TYPE_ARGUMENT, firstIdent);
        firstTypeArgument.addChild(firstIdent);

        for (int i = 0; i < ctx.COMMA().size(); i++) {
            addLastSibling(firstTypeArgument, create(ctx.COMMA(i)));
            final DetailAstImpl ident = visit(ctx.typeType(i + 1));
            final DetailAstImpl typeArgument = copy(TokenTypes.TYPE_ARGUMENT, ident);
            typeArgument.addChild(ident);
            addLastSibling(firstTypeArgument, typeArgument);
        }
        return firstTypeArgument;
    }

    @Override
    public DetailAstImpl visitTypeList(JavaParser.TypeListContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public DetailAstImpl visitArrayDeclarator(JavaParser.ArrayDeclaratorContext ctx) {
        final DetailAstImpl arrayDeclarator = create(TokenTypes.ARRAY_DECLARATOR,
                (Token) ctx.LBRACK().getPayload());
        arrayDeclarator.addChild(create(ctx.RBRACK()));

        final DetailAstImpl returnTree;
        final DetailAstImpl annotations = visit(ctx.anno);
        if (annotations == null) {
            returnTree = arrayDeclarator;
        }
        else {
            returnTree = annotations;
            addLastSibling(returnTree, arrayDeclarator);
        }
        return returnTree;
    }

    @Override
    public DetailAstImpl visitPrimitiveType(JavaParser.PrimitiveTypeContext ctx) {
        return create(ctx.start);
    }

    @Override
    public DetailAstImpl visitTypeType(JavaParser.TypeTypeContext ctx) {
        final DetailAstImpl type = createImaginary(TokenTypes.TYPE, ctx.start);
        processChildren(type, ctx.children);

        // We set the imaginary 'TYPE' node to the position of the first child
        type.setLineNo(type.getFirstChild().getLineNo());
        type.setColumnNo(type.getFirstChild().getColumnNo());

        final DetailAstImpl returnTree;
        if (ctx.createImaginaryNode) {
            returnTree = type;
        }
        else {
            returnTree = type.getFirstChild();
        }
        return returnTree;
    }

    @Override
    public DetailAstImpl visitTypeArguments(JavaParser.TypeArgumentsContext ctx) {
        final DetailAstImpl typeArguments = createImaginary(TokenTypes.TYPE_ARGUMENTS, ctx.start);
        typeArguments.addChild(create(TokenTypes.GENERIC_START, (Token) ctx.LT().getPayload()));
        // Exclude '<' and '>'
        processChildren(typeArguments, ctx.children.subList(1, ctx.children.size() - 1));
        typeArguments.addChild(create(TokenTypes.GENERIC_END, (Token) ctx.GT().getPayload()));
        return typeArguments;
    }

    @Override
    public DetailAstImpl visitSuperSuffixDot(JavaParser.SuperSuffixDotContext ctx) {
        final DetailAstImpl root;
        if (ctx.LPAREN() == null) {
            root = create(ctx.DOT());
            root.addChild(visit(ctx.id()));
        }
        else {
            root = create(TokenTypes.METHOD_CALL, (Token) ctx.LPAREN().getPayload());

            final DetailAstImpl dot = create(ctx.DOT());
            dot.addChild(visit(ctx.id()));
            root.addChild(dot);

            final DetailAstImpl expressionList = Optional.ofNullable(visit(ctx.expressionList()))
                    .orElse(createImaginary(TokenTypes.ELIST, ctx.RPAREN()));
            root.addChild(expressionList);

            root.addChild(create(ctx.RPAREN()));
        }

        return root;
    }

    @Override
    public DetailAstImpl visitArguments(JavaParser.ArgumentsContext ctx) {
        final DetailAstImpl lparen = create(ctx.LPAREN());

        // We always add an 'ELIST' node
        final DetailAstImpl expressionList = Optional.ofNullable(visit(ctx.expressionList()))
                .orElse(createImaginary(TokenTypes.ELIST, ctx.RPAREN()));
        addLastSibling(lparen, expressionList);
        addLastSibling(lparen, create(ctx.RPAREN()));
        return lparen;
    }

    @Override
    public DetailAstImpl visitPatternVariableDefinition(
            JavaParser.PatternVariableDefinitionContext ctx) {

        final DetailAstImpl type = visit(ctx.type);
        final DetailAstImpl patternVariableDef;
        if (ctx.mods.isEmpty()) {
            patternVariableDef = copy(TokenTypes.PATTERN_VARIABLE_DEF, type);
        }
        else {
            patternVariableDef = createImaginary(TokenTypes.PATTERN_VARIABLE_DEF, ctx.start);
        }
        patternVariableDef.addChild(createModifiers(ctx.mods, patternVariableDef));
        patternVariableDef.addChild(type);
        patternVariableDef.addChild(visit(ctx.id()));
        return patternVariableDef;
    }

    @Override
    public DetailAstImpl visitPermittedSubclassesAndInterfaces(
            JavaParser.PermittedSubclassesAndInterfacesContext ctx) {
        final DetailAstImpl literalPermits =
                create(TokenTypes.PERMITS_CLAUSE, (Token) ctx.LITERAL_permits().getPayload());
        // 'LITERAL_permits' is child[0]
        processChildren(literalPermits, ctx.children.subList(1, ctx.children.size()));
        return literalPermits;
    }

    @Override
    public DetailAstImpl visitId(JavaParser.IdContext ctx) {
        return create(TokenTypes.IDENT, ctx.start);
    }

    /**
     * Builds the AST for a particular node, then returns a "flattened" tree
     * of siblings. This method should be used in rule contexts such as
     * {@code variableDeclarators}, where we have both terminals and non-terminals.
     *
     * @param ctx the ParserRuleContext to base tree on
     * @return flattened DetailAstImpl
     */
    private DetailAstImpl flattenedTree(ParserRuleContext ctx) {
        final DetailAstImpl dummyNode = new DetailAstImpl();
        processChildren(dummyNode, ctx.children);
        return dummyNode.getFirstChild();
    }

    /**
     * Adds all the children from the given ParseTree or JavaParserContext
     * list to the parent DetailAstImpl.
     *
     * @param parent the DetailAstImpl to add children to
     * @param children the list of children to add
     */
    private void processChildren(DetailAstImpl parent, List<? extends ParseTree> children) {
        children.forEach(child -> {
            if (child instanceof TerminalNode) {
                // Child is a token, create a new DetailAstImpl and add it to parent
                parent.addChild(create((TerminalNode) child));
            }
            else {
                // Child is another rule context; visit it, create token, and add to parent
                parent.addChild(visit(child));
            }
        });
    }

    /**
     * Create a DetailAstImpl from a given token and token type. This method
     * should be used for imaginary nodes only, i.e. 'OBJBLOCK -> OBJBLOCK',
     * where the text on the RHS matches the text on the LHS.
     *
     * @param tokenType  the token type of this DetailAstImpl
     * @param token the first token to appear in our RuleContext
     * @return new DetailAstImpl of given type
     */
    private static DetailAstImpl createImaginary(int tokenType, Token token) {
        final DetailAstImpl detailAst = new DetailAstImpl();
        detailAst.initialize(token);
        // We have used the given start token to initialize the DetailAstImpl,
        // but now we need to set the type of this imaginary node.
        detailAst.setType(tokenType);
        detailAst.setText(TokenUtil.getTokenName(tokenType));
        return detailAst;
    }

    /**
     * Create a DetailAstImpl from a given token and token type. This method
     * should be used for imaginary nodes only, i.e. 'OBJBLOCK -> OBJBLOCK',
     * where the text on the RHS matches the text on the LHS.
     *
     * @param tokenType  the token type of this DetailAstImpl
     * @param terminalNode the first token to appear in our RuleContext
     * @return new DetailAstImpl of given type
     */
    private static DetailAstImpl createImaginary(int tokenType, TerminalNode terminalNode) {
        return createImaginary(tokenType, (Token) terminalNode.getPayload());
    }

    /**
     * Create a DetailAstImpl from a given token and token type. This method
     * should be used for literal nodes only, i.e. 'PACKAGE_DEF -> package'.
     *
     * @param tokenType the token type of this DetailAstImpl
     * @param startToken the first token that appears in this DetailAstImpl.
     * @return new DetailAstImpl of given type
     */
    private DetailAstImpl create(int tokenType, Token startToken) {
        final DetailAstImpl ast = create(startToken);
        ast.setType(tokenType);
        return ast;
    }

    /**
     * Create a DetailAstImpl from a given token. This method should be
     * used for terminal nodes, i.e. {@code LCURLY}, when we are building
     * an AST for a specific token, regardless of position.
     *
     * @param token the token to build the DetailAstImpl from
     * @return new DetailAstImpl of given type
     */
    private DetailAstImpl create(Token token) {
        final int tokenIndex = token.getTokenIndex();
        final List<Token> tokensToLeft =
                tokens.getHiddenTokensToLeft(tokenIndex, JavaLexer.COMMENTS);
        final List<Token> tokensToRight =
                tokens.getHiddenTokensToRight(tokenIndex, JavaLexer.COMMENTS);

        final DetailAstImpl detailAst = new DetailAstImpl();
        detailAst.initialize(token);
        if (tokensToLeft != null) {
            detailAst.setHiddenBefore(tokensToLeft);
        }
        if (tokensToRight != null) {
            detailAst.setHiddenAfter(tokensToRight);
        }
        return detailAst;
    }

    /**
     * Create a DetailAstImpl from a given TerminalNode. This method should be
     * used for terminal nodes, i.e. {@code @}.
     *
     * @param node the TerminalNode to build the DetailAstImpl from
     * @return new DetailAstImpl of given type
     */
    private DetailAstImpl create(TerminalNode node) {
        return create((Token) node.getPayload());
    }

    /**
     * Builds a "root tree", where we iteratively set the root of the ast
     * to the next operator.
     *
     * <p>For example:</p>
     *
     * <pre>
     * DOT -&gt; .
     *  |--DOT -&gt; .
     *  |   |--DOT -&gt; .
     *  |   |   |--DOT -&gt; .
     *  |   |   |   |--DOT -&gt; .
     *  |   |   |   |   |--IDENT -&gt; com
     *  |   |   |   |   `--IDENT -&gt; puppycrawl
     *  |   |   |   `--IDENT -&gt; tools
     *  |   |   `--IDENT -&gt; checkstyle
     *  |   `--IDENT -&gt; grammar
     *  `--IDENT -&gt; antlr4
     * </pre>
     *
     * @param innerChild the first node, or resulting innermost child, of this DetailAstImpl
     * @param ctx        the parser rule context that we are in
     * @return root tree of given parser rule context
     */
    private DetailAstImpl buildRootTree(DetailAstImpl innerChild, ParserRuleContext ctx) {
        DetailAstImpl ast = innerChild;
        final DetailAstPair currentAst = new DetailAstPair();
        DetailAstPair.addASTChild(currentAst, ast);

        for (int i = 1; i < ctx.children.size() - 1; i += 2) {

            // Token to become root
            final DetailAstImpl temp = create((Token) ctx.children.get(i).getPayload());
            DetailAstPair.makeAstRoot(currentAst, temp);

            // Token to become child or next sibling
            final Object child = ctx.children.get(i + 1).getPayload();
            ast = visit((ParseTree) child);

            DetailAstPair.addASTChild(currentAst, ast);
        }
        return currentAst.getRoot();
    }

    /**
     * Creates a shallow copy of a single node. Relationships to other nodes
     * are not maintained.
     *
     * @param tokenType the desired TokenType of the copy
     * @param ast the DetailAstImpl to copy
     * @return shallow copy of ast node
     */
    private static DetailAstImpl copy(int tokenType, DetailAstImpl ast) {
        final DetailAstImpl copy = new DetailAstImpl();
        copy.setLineNo(ast.getLineNo());
        copy.setColumnNo(ast.getColumnNo());
        copy.setType(tokenType);
        copy.setText(TokenUtil.getTokenName(tokenType));
        return copy;
    }

    /**
     * Builds the modifiers AST.
     *
     * @param modifierList the list of modifier contexts
     * @param parent use in case where we have no modifiers
     * @return "MODIFIERS" ast
     */
    private DetailAstImpl createModifiers(
            List<? extends ParseTree> modifierList, DetailAstImpl parent) {
        final DetailAstImpl mods;
        if (modifierList.isEmpty()) {
            // Create imaginary MODIFIERS
            mods = copy(TokenTypes.MODIFIERS, parent);
        }
        else {
            mods = copy(TokenTypes.MODIFIERS,
                     visit(modifierList.get(0)));
            processChildren(mods, modifierList);
        }
        return mods;
    }

    /**
     * Gets the first token of this AST. We check to see if there are modifiers; if so,
     * syntactically these will appear before the type, so we return the first modifier
     * token. If not, we return the type token.
     *
     * @param modifierList the list of modifiers
     * @param typeStart the RuleContext of the type
     * @return DetailAstImpl of the first token in AST
     */
    private DetailAstImpl getStartToken(List<? extends ParseTree> modifierList,
                                                   ParserRuleContext typeStart) {
        final DetailAstImpl startToken;
        if (modifierList.isEmpty()) {
            final DetailAstImpl type = visit(typeStart);
            startToken = copy(TokenTypes.TYPE, type);
        }
        else {
            startToken = create(((ParserRuleContext) modifierList.get(0)).start);
        }
        return startToken;
    }

    /**
     * Gets the first token of this AST. We check to see if there are modifiers; if so,
     * syntactically these will appear before the type, so we return the first modifier
     * token. If not, we return the AST of the given token.
     *
     * @param modifierList the list of modifiers
     * @param start the startToken in the ParserRuleContext
     * @return DetailAstImpl of first token in AST
     */
    private DetailAstImpl getStartToken(List<JavaParser.ModifierContext> modifierList,
                                        Token start) {
        final DetailAstImpl startToken;
        if (modifierList.isEmpty()) {
            startToken = create(start);
        }
        else {
            startToken = create(modifierList.get(0).start);
        }
        return startToken;
    }

    /**
     * Add new sibling to the end of existing siblings.
     *
     * @param self DetailAstImpl to add last sibling to
     * @param sibling DetailAstImpl sibling to add
     */
    private static void addLastSibling(DetailAstImpl self, DetailAstImpl sibling) {
        DetailAstImpl nextSibling = self;
        while (nextSibling.getNextSibling() != null) {
            nextSibling = nextSibling.getNextSibling();
        }
        nextSibling.setNextSibling(sibling);
    }

    @Override
    public DetailAstImpl visit(ParseTree tree) {
        DetailAstImpl ast = null;
        if (tree != null) {
            ast = tree.accept(this);
        }
        return ast;
    }

    /**
     * Used to swap and organize DetailAstImpl subtrees.
     */
    private static final class DetailAstPair {

        /** The root DetailAstImpl of this pair. */
        private DetailAstImpl root;

        /** The child (potentially with siblings) of this pair. */
        private DetailAstImpl child;

        /**
         * Moves child reference to the last child.
         */
        private void advanceChildToEnd() {
            while (child.getNextSibling() != null) {
                child = child.getNextSibling();
            }
        }

        private DetailAstImpl getRoot() {
            return root;
        }

        /**
         * This method is used to replace the {@code ^} (set as root node) ANTLR2
         * operator.
         *
         * @param pair the DetailAstPair to use for swapping nodes
         * @param ast the new root
         */
        private static void makeAstRoot(DetailAstPair pair, DetailAstImpl ast) {
            ast.addChild(pair.root);
            pair.child = pair.root;
            pair.advanceChildToEnd();
            pair.root = ast;
        }

        /**
         * Adds a child (or new root) to the given DetailAstPair.
         *
         * @param pair the DetailAstPair to add child to
         * @param ast the child to add
         */
        private static void addASTChild(DetailAstPair pair, DetailAstImpl ast) {
            if (ast != null) {
                if (pair.root == null) {
                    pair.root = ast;
                }
                else {
                    pair.child.setNextSibling(ast);
                }
                pair.child = ast;
                pair.advanceChildToEnd();
            }
        }
    }
}
