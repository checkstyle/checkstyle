// Generated from com/puppycrawl/tools/checkstyle/grammar/javadoc/JavadocCommentsParser.g4 by ANTLR 4.13.2
package com.puppycrawl.tools.checkstyle.grammar.javadoc;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link JavadocCommentsParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface JavadocCommentsParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#javadoc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJavadoc(JavadocCommentsParser.JavadocContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#mainDescription}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMainDescription(JavadocCommentsParser.MainDescriptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#blockTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockTag(JavadocCommentsParser.BlockTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#authorTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAuthorTag(JavadocCommentsParser.AuthorTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#deprecatedTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeprecatedTag(JavadocCommentsParser.DeprecatedTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#returnTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnTag(JavadocCommentsParser.ReturnTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#parameterTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterTag(JavadocCommentsParser.ParameterTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#throwsTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThrowsTag(JavadocCommentsParser.ThrowsTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#exceptionTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExceptionTag(JavadocCommentsParser.ExceptionTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#sinceTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSinceTag(JavadocCommentsParser.SinceTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#versionTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVersionTag(JavadocCommentsParser.VersionTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#seeTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSeeTag(JavadocCommentsParser.SeeTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#hiddenTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHiddenTag(JavadocCommentsParser.HiddenTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#usesTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUsesTag(JavadocCommentsParser.UsesTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#providesTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProvidesTag(JavadocCommentsParser.ProvidesTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#serialTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSerialTag(JavadocCommentsParser.SerialTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#serialDataTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSerialDataTag(JavadocCommentsParser.SerialDataTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#serialFieldTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSerialFieldTag(JavadocCommentsParser.SerialFieldTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#customBlockTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCustomBlockTag(JavadocCommentsParser.CustomBlockTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#inlineTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInlineTag(JavadocCommentsParser.InlineTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#inlineTagContent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInlineTagContent(JavadocCommentsParser.InlineTagContentContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#codeInlineTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCodeInlineTag(JavadocCommentsParser.CodeInlineTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#linkPlainInlineTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLinkPlainInlineTag(JavadocCommentsParser.LinkPlainInlineTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#linkInlineTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLinkInlineTag(JavadocCommentsParser.LinkInlineTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#valueInlineTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValueInlineTag(JavadocCommentsParser.ValueInlineTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#inheritDocInlineTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInheritDocInlineTag(JavadocCommentsParser.InheritDocInlineTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#summaryInlineTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSummaryInlineTag(JavadocCommentsParser.SummaryInlineTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#systemPropertyInlineTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSystemPropertyInlineTag(JavadocCommentsParser.SystemPropertyInlineTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#indexInlineTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexInlineTag(JavadocCommentsParser.IndexInlineTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#returnInlineTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnInlineTag(JavadocCommentsParser.ReturnInlineTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#literalInlineTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralInlineTag(JavadocCommentsParser.LiteralInlineTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#snippetInlineTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSnippetInlineTag(JavadocCommentsParser.SnippetInlineTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#customInlineTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCustomInlineTag(JavadocCommentsParser.CustomInlineTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#reference}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReference(JavadocCommentsParser.ReferenceContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#typeName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeName(JavadocCommentsParser.TypeNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#qualifiedName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQualifiedName(JavadocCommentsParser.QualifiedNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#typeArguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeArguments(JavadocCommentsParser.TypeArgumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#typeArgument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeArgument(JavadocCommentsParser.TypeArgumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#memberReference}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberReference(JavadocCommentsParser.MemberReferenceContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#parameterTypeList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterTypeList(JavadocCommentsParser.ParameterTypeListContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#snippetAttribute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSnippetAttribute(JavadocCommentsParser.SnippetAttributeContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#snippetBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSnippetBody(JavadocCommentsParser.SnippetBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#description}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDescription(JavadocCommentsParser.DescriptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#htmlElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHtmlElement(JavadocCommentsParser.HtmlElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#voidElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVoidElement(JavadocCommentsParser.VoidElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#tightElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTightElement(JavadocCommentsParser.TightElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#nonTightElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonTightElement(JavadocCommentsParser.NonTightElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#selfClosingElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelfClosingElement(JavadocCommentsParser.SelfClosingElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#htmlTagStart}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHtmlTagStart(JavadocCommentsParser.HtmlTagStartContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#htmlTagEnd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHtmlTagEnd(JavadocCommentsParser.HtmlTagEndContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#htmlAttribute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHtmlAttribute(JavadocCommentsParser.HtmlAttributeContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#htmlContent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHtmlContent(JavadocCommentsParser.HtmlContentContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#nonTightHtmlContent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonTightHtmlContent(JavadocCommentsParser.NonTightHtmlContentContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#htmlComment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHtmlComment(JavadocCommentsParser.HtmlCommentContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavadocCommentsParser#htmlCommentContent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHtmlCommentContent(JavadocCommentsParser.HtmlCommentContentContext ctx);
}