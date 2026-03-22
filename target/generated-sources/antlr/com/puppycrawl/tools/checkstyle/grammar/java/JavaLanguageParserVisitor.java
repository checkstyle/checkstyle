// Generated from com/puppycrawl/tools/checkstyle/grammar/java/JavaLanguageParser.g4 by ANTLR 4.13.2
package com.puppycrawl.tools.checkstyle.grammar.java;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link JavaLanguageParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface JavaLanguageParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#compilationUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompilationUnit(JavaLanguageParser.CompilationUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#packageDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPackageDeclaration(JavaLanguageParser.PackageDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code importDec}
	 * labeled alternative in {@link JavaLanguageParser#importDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportDec(JavaLanguageParser.ImportDecContext ctx);
	/**
	 * Visit a parse tree produced by the {@code singleSemiImport}
	 * labeled alternative in {@link JavaLanguageParser#importDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleSemiImport(JavaLanguageParser.SingleSemiImportContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#typeDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeDeclaration(JavaLanguageParser.TypeDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#types}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypes(JavaLanguageParser.TypesContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#modifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModifier(JavaLanguageParser.ModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#variableModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableModifier(JavaLanguageParser.VariableModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#classDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDeclaration(JavaLanguageParser.ClassDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#recordDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordDeclaration(JavaLanguageParser.RecordDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#recordComponentsList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordComponentsList(JavaLanguageParser.RecordComponentsListContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#recordComponents}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordComponents(JavaLanguageParser.RecordComponentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#recordComponent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordComponent(JavaLanguageParser.RecordComponentContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#lastRecordComponent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLastRecordComponent(JavaLanguageParser.LastRecordComponentContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#recordBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordBody(JavaLanguageParser.RecordBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#recordBodyDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordBodyDeclaration(JavaLanguageParser.RecordBodyDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#compactConstructorDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompactConstructorDeclaration(JavaLanguageParser.CompactConstructorDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#classExtends}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassExtends(JavaLanguageParser.ClassExtendsContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#implementsClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImplementsClause(JavaLanguageParser.ImplementsClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#typeParameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeParameters(JavaLanguageParser.TypeParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#typeParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeParameter(JavaLanguageParser.TypeParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#typeUpperBounds}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeUpperBounds(JavaLanguageParser.TypeUpperBoundsContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#typeBound}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeBound(JavaLanguageParser.TypeBoundContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#typeBoundType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeBoundType(JavaLanguageParser.TypeBoundTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#enumDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumDeclaration(JavaLanguageParser.EnumDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#enumBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumBody(JavaLanguageParser.EnumBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#enumConstants}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumConstants(JavaLanguageParser.EnumConstantsContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#enumConstant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumConstant(JavaLanguageParser.EnumConstantContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#enumBodyDeclarations}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumBodyDeclarations(JavaLanguageParser.EnumBodyDeclarationsContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#interfaceDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceDeclaration(JavaLanguageParser.InterfaceDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#interfaceExtends}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceExtends(JavaLanguageParser.InterfaceExtendsContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#classBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassBody(JavaLanguageParser.ClassBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#interfaceBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceBody(JavaLanguageParser.InterfaceBodyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code emptyClass}
	 * labeled alternative in {@link JavaLanguageParser#classBodyDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptyClass(JavaLanguageParser.EmptyClassContext ctx);
	/**
	 * Visit a parse tree produced by the {@code classBlock}
	 * labeled alternative in {@link JavaLanguageParser#classBodyDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassBlock(JavaLanguageParser.ClassBlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code classDef}
	 * labeled alternative in {@link JavaLanguageParser#classBodyDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDef(JavaLanguageParser.ClassDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#memberDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberDeclaration(JavaLanguageParser.MemberDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#methodDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodDeclaration(JavaLanguageParser.MethodDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#methodBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodBody(JavaLanguageParser.MethodBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#throwsList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThrowsList(JavaLanguageParser.ThrowsListContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#constructorDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructorDeclaration(JavaLanguageParser.ConstructorDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#fieldDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldDeclaration(JavaLanguageParser.FieldDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#interfaceBodyDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceBodyDeclaration(JavaLanguageParser.InterfaceBodyDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#interfaceMemberDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceMemberDeclaration(JavaLanguageParser.InterfaceMemberDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#interfaceMethodDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceMethodDeclaration(JavaLanguageParser.InterfaceMethodDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#variableDeclarators}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclarators(JavaLanguageParser.VariableDeclaratorsContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#variableDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclarator(JavaLanguageParser.VariableDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#variableDeclaratorId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaratorId(JavaLanguageParser.VariableDeclaratorIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#variableInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableInitializer(JavaLanguageParser.VariableInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#arrayInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayInitializer(JavaLanguageParser.ArrayInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#classOrInterfaceType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassOrInterfaceType(JavaLanguageParser.ClassOrInterfaceTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#classOrInterfaceTypeExtended}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassOrInterfaceTypeExtended(JavaLanguageParser.ClassOrInterfaceTypeExtendedContext ctx);
	/**
	 * Visit a parse tree produced by the {@code simpleTypeArgument}
	 * labeled alternative in {@link JavaLanguageParser#typeArgument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleTypeArgument(JavaLanguageParser.SimpleTypeArgumentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code wildCardTypeArgument}
	 * labeled alternative in {@link JavaLanguageParser#typeArgument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWildCardTypeArgument(JavaLanguageParser.WildCardTypeArgumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#qualifiedNameList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQualifiedNameList(JavaLanguageParser.QualifiedNameListContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#formalParameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormalParameters(JavaLanguageParser.FormalParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#formalParameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormalParameterList(JavaLanguageParser.FormalParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#formalParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormalParameter(JavaLanguageParser.FormalParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#lastFormalParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLastFormalParameter(JavaLanguageParser.LastFormalParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#qualifiedName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQualifiedName(JavaLanguageParser.QualifiedNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#qualifiedNameExtended}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQualifiedNameExtended(JavaLanguageParser.QualifiedNameExtendedContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(JavaLanguageParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#integerLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntegerLiteral(JavaLanguageParser.IntegerLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#floatLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloatLiteral(JavaLanguageParser.FloatLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#textBlockLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTextBlockLiteral(JavaLanguageParser.TextBlockLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#annotations}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotations(JavaLanguageParser.AnnotationsContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#annotation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotation(JavaLanguageParser.AnnotationContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#elementValuePairs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementValuePairs(JavaLanguageParser.ElementValuePairsContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#elementValuePair}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementValuePair(JavaLanguageParser.ElementValuePairContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#elementValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementValue(JavaLanguageParser.ElementValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#elementValueArrayInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementValueArrayInitializer(JavaLanguageParser.ElementValueArrayInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#annotationTypeDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotationTypeDeclaration(JavaLanguageParser.AnnotationTypeDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#annotationTypeBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotationTypeBody(JavaLanguageParser.AnnotationTypeBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#annotationTypeElementDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotationTypeElementDeclaration(JavaLanguageParser.AnnotationTypeElementDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code annotationField}
	 * labeled alternative in {@link JavaLanguageParser#annotationTypeElementRest}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotationField(JavaLanguageParser.AnnotationFieldContext ctx);
	/**
	 * Visit a parse tree produced by the {@code annotationType}
	 * labeled alternative in {@link JavaLanguageParser#annotationTypeElementRest}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotationType(JavaLanguageParser.AnnotationTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#annotationMethodRest}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotationMethodRest(JavaLanguageParser.AnnotationMethodRestContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#annotationConstantRest}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotationConstantRest(JavaLanguageParser.AnnotationConstantRestContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#defaultValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefaultValue(JavaLanguageParser.DefaultValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#constructorBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructorBlock(JavaLanguageParser.ConstructorBlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code explicitCtorCall}
	 * labeled alternative in {@link JavaLanguageParser#explicitConstructorInvocation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExplicitCtorCall(JavaLanguageParser.ExplicitCtorCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code primaryCtorCall}
	 * labeled alternative in {@link JavaLanguageParser#explicitConstructorInvocation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryCtorCall(JavaLanguageParser.PrimaryCtorCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(JavaLanguageParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code localVar}
	 * labeled alternative in {@link JavaLanguageParser#blockStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalVar(JavaLanguageParser.LocalVarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stat}
	 * labeled alternative in {@link JavaLanguageParser#blockStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat(JavaLanguageParser.StatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code localType}
	 * labeled alternative in {@link JavaLanguageParser#blockStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalType(JavaLanguageParser.LocalTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#localVariableDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalVariableDeclaration(JavaLanguageParser.LocalVariableDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#localTypeDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalTypeDeclaration(JavaLanguageParser.LocalTypeDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code blockStat}
	 * labeled alternative in {@link JavaLanguageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStat(JavaLanguageParser.BlockStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assertExp}
	 * labeled alternative in {@link JavaLanguageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssertExp(JavaLanguageParser.AssertExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ifStat}
	 * labeled alternative in {@link JavaLanguageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStat(JavaLanguageParser.IfStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code forStat}
	 * labeled alternative in {@link JavaLanguageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStat(JavaLanguageParser.ForStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code whileStat}
	 * labeled alternative in {@link JavaLanguageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStat(JavaLanguageParser.WhileStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code doStat}
	 * labeled alternative in {@link JavaLanguageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoStat(JavaLanguageParser.DoStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tryStat}
	 * labeled alternative in {@link JavaLanguageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTryStat(JavaLanguageParser.TryStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tryWithResourceStat}
	 * labeled alternative in {@link JavaLanguageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTryWithResourceStat(JavaLanguageParser.TryWithResourceStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code yieldStat}
	 * labeled alternative in {@link JavaLanguageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitYieldStat(JavaLanguageParser.YieldStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code switchStat}
	 * labeled alternative in {@link JavaLanguageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchStat(JavaLanguageParser.SwitchStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code syncStat}
	 * labeled alternative in {@link JavaLanguageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSyncStat(JavaLanguageParser.SyncStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code returnStat}
	 * labeled alternative in {@link JavaLanguageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStat(JavaLanguageParser.ReturnStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code throwStat}
	 * labeled alternative in {@link JavaLanguageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThrowStat(JavaLanguageParser.ThrowStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code breakStat}
	 * labeled alternative in {@link JavaLanguageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStat(JavaLanguageParser.BreakStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code continueStat}
	 * labeled alternative in {@link JavaLanguageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinueStat(JavaLanguageParser.ContinueStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code emptyStat}
	 * labeled alternative in {@link JavaLanguageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptyStat(JavaLanguageParser.EmptyStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expStat}
	 * labeled alternative in {@link JavaLanguageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpStat(JavaLanguageParser.ExpStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code labelStat}
	 * labeled alternative in {@link JavaLanguageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabelStat(JavaLanguageParser.LabelStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#switchExpressionOrStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchExpressionOrStatement(JavaLanguageParser.SwitchExpressionOrStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code switchRules}
	 * labeled alternative in {@link JavaLanguageParser#switchBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchRules(JavaLanguageParser.SwitchRulesContext ctx);
	/**
	 * Visit a parse tree produced by the {@code switchBlocks}
	 * labeled alternative in {@link JavaLanguageParser#switchBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchBlocks(JavaLanguageParser.SwitchBlocksContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#switchLabeledRule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchLabeledRule(JavaLanguageParser.SwitchLabeledRuleContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#switchLabeledExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchLabeledExpression(JavaLanguageParser.SwitchLabeledExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#switchLabeledBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchLabeledBlock(JavaLanguageParser.SwitchLabeledBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#switchLabeledThrow}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchLabeledThrow(JavaLanguageParser.SwitchLabeledThrowContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#elseStat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseStat(JavaLanguageParser.ElseStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#catchClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCatchClause(JavaLanguageParser.CatchClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#catchParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCatchParameter(JavaLanguageParser.CatchParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#catchType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCatchType(JavaLanguageParser.CatchTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#finallyBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFinallyBlock(JavaLanguageParser.FinallyBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#resourceSpecification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitResourceSpecification(JavaLanguageParser.ResourceSpecificationContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#resources}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitResources(JavaLanguageParser.ResourcesContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#resource}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitResource(JavaLanguageParser.ResourceContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#resourceDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitResourceDeclaration(JavaLanguageParser.ResourceDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#variableAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableAccess(JavaLanguageParser.VariableAccessContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#fieldAccessNoIdent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldAccessNoIdent(JavaLanguageParser.FieldAccessNoIdentContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#switchBlockStatementGroup}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchBlockStatementGroup(JavaLanguageParser.SwitchBlockStatementGroupContext ctx);
	/**
	 * Visit a parse tree produced by the {@code caseLabel}
	 * labeled alternative in {@link JavaLanguageParser#switchLabel}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCaseLabel(JavaLanguageParser.CaseLabelContext ctx);
	/**
	 * Visit a parse tree produced by the {@code defaultLabel}
	 * labeled alternative in {@link JavaLanguageParser#switchLabel}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefaultLabel(JavaLanguageParser.DefaultLabelContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#caseConstants}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCaseConstants(JavaLanguageParser.CaseConstantsContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#caseConstant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCaseConstant(JavaLanguageParser.CaseConstantContext ctx);
	/**
	 * Visit a parse tree produced by the {@code enhancedFor}
	 * labeled alternative in {@link JavaLanguageParser#forControl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnhancedFor(JavaLanguageParser.EnhancedForContext ctx);
	/**
	 * Visit a parse tree produced by the {@code forFor}
	 * labeled alternative in {@link JavaLanguageParser#forControl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForFor(JavaLanguageParser.ForForContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#forInit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForInit(JavaLanguageParser.ForInitContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#enhancedForControl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnhancedForControl(JavaLanguageParser.EnhancedForControlContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#enhancedForControlWithRecordPattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnhancedForControlWithRecordPattern(JavaLanguageParser.EnhancedForControlWithRecordPatternContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#parExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParExpression(JavaLanguageParser.ParExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#expressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionList(JavaLanguageParser.ExpressionListContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(JavaLanguageParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code refOp}
	 * labeled alternative in {@link JavaLanguageParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRefOp(JavaLanguageParser.RefOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code superExp}
	 * labeled alternative in {@link JavaLanguageParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuperExp(JavaLanguageParser.SuperExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code instanceOfExp}
	 * labeled alternative in {@link JavaLanguageParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceOfExp(JavaLanguageParser.InstanceOfExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code bitShift}
	 * labeled alternative in {@link JavaLanguageParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBitShift(JavaLanguageParser.BitShiftContext ctx);
	/**
	 * Visit a parse tree produced by the {@code newExp}
	 * labeled alternative in {@link JavaLanguageParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewExp(JavaLanguageParser.NewExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code prefix}
	 * labeled alternative in {@link JavaLanguageParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefix(JavaLanguageParser.PrefixContext ctx);
	/**
	 * Visit a parse tree produced by the {@code castExp}
	 * labeled alternative in {@link JavaLanguageParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCastExp(JavaLanguageParser.CastExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code indexOp}
	 * labeled alternative in {@link JavaLanguageParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexOp(JavaLanguageParser.IndexOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code invOp}
	 * labeled alternative in {@link JavaLanguageParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInvOp(JavaLanguageParser.InvOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code initExp}
	 * labeled alternative in {@link JavaLanguageParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInitExp(JavaLanguageParser.InitExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code simpleMethodCall}
	 * labeled alternative in {@link JavaLanguageParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleMethodCall(JavaLanguageParser.SimpleMethodCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code lambdaExp}
	 * labeled alternative in {@link JavaLanguageParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaExp(JavaLanguageParser.LambdaExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code thisExp}
	 * labeled alternative in {@link JavaLanguageParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThisExp(JavaLanguageParser.ThisExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code primaryExp}
	 * labeled alternative in {@link JavaLanguageParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryExp(JavaLanguageParser.PrimaryExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code postfix}
	 * labeled alternative in {@link JavaLanguageParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfix(JavaLanguageParser.PostfixContext ctx);
	/**
	 * Visit a parse tree produced by the {@code methodRef}
	 * labeled alternative in {@link JavaLanguageParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodRef(JavaLanguageParser.MethodRefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ternaryOp}
	 * labeled alternative in {@link JavaLanguageParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTernaryOp(JavaLanguageParser.TernaryOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binOp}
	 * labeled alternative in {@link JavaLanguageParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinOp(JavaLanguageParser.BinOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code methodCall}
	 * labeled alternative in {@link JavaLanguageParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodCall(JavaLanguageParser.MethodCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#typeCastParameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeCastParameters(JavaLanguageParser.TypeCastParametersContext ctx);
	/**
	 * Visit a parse tree produced by the {@code singleLambdaParam}
	 * labeled alternative in {@link JavaLanguageParser#lambdaParameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleLambdaParam(JavaLanguageParser.SingleLambdaParamContext ctx);
	/**
	 * Visit a parse tree produced by the {@code formalLambdaParam}
	 * labeled alternative in {@link JavaLanguageParser#lambdaParameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormalLambdaParam(JavaLanguageParser.FormalLambdaParamContext ctx);
	/**
	 * Visit a parse tree produced by the {@code multiLambdaParam}
	 * labeled alternative in {@link JavaLanguageParser#lambdaParameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiLambdaParam(JavaLanguageParser.MultiLambdaParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#multiLambdaParams}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiLambdaParams(JavaLanguageParser.MultiLambdaParamsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code switchPrimary}
	 * labeled alternative in {@link JavaLanguageParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchPrimary(JavaLanguageParser.SwitchPrimaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parenPrimary}
	 * labeled alternative in {@link JavaLanguageParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenPrimary(JavaLanguageParser.ParenPrimaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tokenPrimary}
	 * labeled alternative in {@link JavaLanguageParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTokenPrimary(JavaLanguageParser.TokenPrimaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code literalPrimary}
	 * labeled alternative in {@link JavaLanguageParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralPrimary(JavaLanguageParser.LiteralPrimaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code classRefPrimary}
	 * labeled alternative in {@link JavaLanguageParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassRefPrimary(JavaLanguageParser.ClassRefPrimaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code primitivePrimary}
	 * labeled alternative in {@link JavaLanguageParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitivePrimary(JavaLanguageParser.PrimitivePrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#classType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassType(JavaLanguageParser.ClassTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#creator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreator(JavaLanguageParser.CreatorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code createdNameObject}
	 * labeled alternative in {@link JavaLanguageParser#createdName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreatedNameObject(JavaLanguageParser.CreatedNameObjectContext ctx);
	/**
	 * Visit a parse tree produced by the {@code createdNamePrimitive}
	 * labeled alternative in {@link JavaLanguageParser#createdName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreatedNamePrimitive(JavaLanguageParser.CreatedNamePrimitiveContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#createdNameExtended}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreatedNameExtended(JavaLanguageParser.CreatedNameExtendedContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#innerCreator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInnerCreator(JavaLanguageParser.InnerCreatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#arrayCreatorRest}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayCreatorRest(JavaLanguageParser.ArrayCreatorRestContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#bracketsWithExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBracketsWithExp(JavaLanguageParser.BracketsWithExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#classCreatorRest}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassCreatorRest(JavaLanguageParser.ClassCreatorRestContext ctx);
	/**
	 * Visit a parse tree produced by the {@code diamond}
	 * labeled alternative in {@link JavaLanguageParser#typeArgumentsOrDiamond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDiamond(JavaLanguageParser.DiamondContext ctx);
	/**
	 * Visit a parse tree produced by the {@code typeArgs}
	 * labeled alternative in {@link JavaLanguageParser#typeArgumentsOrDiamond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeArgs(JavaLanguageParser.TypeArgsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nonWildcardDiamond}
	 * labeled alternative in {@link JavaLanguageParser#nonWildcardTypeArgumentsOrDiamond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonWildcardDiamond(JavaLanguageParser.NonWildcardDiamondContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nonWildcardTypeArgs}
	 * labeled alternative in {@link JavaLanguageParser#nonWildcardTypeArgumentsOrDiamond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonWildcardTypeArgs(JavaLanguageParser.NonWildcardTypeArgsContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#nonWildcardTypeArguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonWildcardTypeArguments(JavaLanguageParser.NonWildcardTypeArgumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#typeArgumentsTypeList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeArgumentsTypeList(JavaLanguageParser.TypeArgumentsTypeListContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#typeList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeList(JavaLanguageParser.TypeListContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#typeType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeType(JavaLanguageParser.TypeTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#classOrInterfaceOrPrimitiveType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassOrInterfaceOrPrimitiveType(JavaLanguageParser.ClassOrInterfaceOrPrimitiveTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#arrayDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayDeclarator(JavaLanguageParser.ArrayDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#primitiveType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitiveType(JavaLanguageParser.PrimitiveTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#typeArguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeArguments(JavaLanguageParser.TypeArgumentsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code superSuffixSimple}
	 * labeled alternative in {@link JavaLanguageParser#superSuffix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuperSuffixSimple(JavaLanguageParser.SuperSuffixSimpleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code superSuffixDot}
	 * labeled alternative in {@link JavaLanguageParser#superSuffix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuperSuffixDot(JavaLanguageParser.SuperSuffixDotContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#arguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArguments(JavaLanguageParser.ArgumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#pattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPattern(JavaLanguageParser.PatternContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#innerPattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInnerPattern(JavaLanguageParser.InnerPatternContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#guardedPattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGuardedPattern(JavaLanguageParser.GuardedPatternContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#guard}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGuard(JavaLanguageParser.GuardContext ctx);
	/**
	 * Visit a parse tree produced by the {@code patternVariableDef}
	 * labeled alternative in {@link JavaLanguageParser#primaryPattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPatternVariableDef(JavaLanguageParser.PatternVariableDefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code recordPatternDef}
	 * labeled alternative in {@link JavaLanguageParser#primaryPattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordPatternDef(JavaLanguageParser.RecordPatternDefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code typePatternDef}
	 * labeled alternative in {@link JavaLanguageParser#typePattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypePatternDef(JavaLanguageParser.TypePatternDefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unnamedPatternDef}
	 * labeled alternative in {@link JavaLanguageParser#typePattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnnamedPatternDef(JavaLanguageParser.UnnamedPatternDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#recordPattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordPattern(JavaLanguageParser.RecordPatternContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#recordComponentPatternList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordComponentPatternList(JavaLanguageParser.RecordComponentPatternListContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#permittedSubclassesAndInterfaces}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPermittedSubclassesAndInterfaces(JavaLanguageParser.PermittedSubclassesAndInterfacesContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaLanguageParser#id}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId(JavaLanguageParser.IdContext ctx);
}