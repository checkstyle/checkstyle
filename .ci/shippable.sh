#!/bin/bash
# Attention, there is no "-x" to avoid problems on Wercker
set -e

###############################
function checkPitestReport() {
  ignored=("$@")
  fail=0
  SEARCH_REGEXP="(span  class='survived'|class='uncovered'><pre>)"
  grep -irE "$SEARCH_REGEXP" target/pit-reports | sed -E 's/.*\/([A-Za-z]+.java.html)/\1/' | sort > target/actual.txt
  printf "%s\n" "${ignored[@]}" > target/ignored.txt
  if [ "$(diff --unified target/ignored.txt target/actual.txt)" != "" ] ; then
      fail=1
      echo "Actual:" ;
      grep -irE "$SEARCH_REGEXP" target/pit-reports | sed -E 's/.*\/([A-Za-z]+.java.html)/\1/' | sort
      echo "Ignore:" ;
      printf '%s\n' "${ignored[@]}"
      echo "Diff:"
      diff --unified target/ignored.txt target/actual.txt | cat
  fi;
  if [ "$fail" -ne "0" ]; then
    echo "Difference between 'Actual' and 'Ignore' lists is detected, lists should be equal, build will be failed."
  fi
  sleep 5s
  exit $fail
}
###############################

case $1 in

pitest-annotation|pitest-design|pitest-header|pitest-imports \
|pitest-metrics|pitest-misc|pitest-modifier|pitest-naming \
|pitest-regexp|pitest-sizes|pitest-whitespace|pitest-ant \
|pitest-api|pitest-common|pitest-filters|pitest-main \
|pitest-packagenamesloader|pitest-tree-walker|pitest-utils \
|pitest-xpath)
  mvn -e -P$1 clean test org.pitest:pitest-maven:mutationCoverage;
  declare -a ignoredItems=();
  checkPitestReport "${ignoredItems[@]}"
  ;;

pitest-blocks)
  mvn -e -P$1 clean test org.pitest:pitest-maven:mutationCoverage;
  declare -a ignoredItems=(
  "LeftCurlyCheck.java.html:<td class='covered'><pre><span  class='survived'>               &#38;&#38; annotation.getNextSibling().getType() == TokenTypes.ANNOTATION) {</span></pre></td></tr>"
  "LeftCurlyCheck.java.html:<td class='covered'><pre><span  class='survived'>                &#38;&#38; previousAnnotation.getPreviousSibling().getLineNo()</span></pre></td></tr>"
  "LeftCurlyCheck.java.html:<td class='covered'><pre><span  class='survived'>                if (tokenAfterLast.getLineNo() &#62; lastAnnotation.getLineNo()) {</span></pre></td></tr>"
  "LeftCurlyCheck.java.html:<td class='covered'><pre><span  class='survived'>        return previousAnnotation;</span></pre></td></tr>"
  "LeftCurlyCheck.java.html:<td class='covered'><pre><span  class='survived'>        while (previousAnnotation.getPreviousSibling() != null</span></pre></td></tr>"
  );
  checkPitestReport "${ignoredItems[@]}"
  ;;

pitest-coding)
  mvn -e -P$1 clean test org.pitest:pitest-maven:mutationCoverage;
  declare -a ignoredItems=(
  "EqualsAvoidNullCheck.java.html:<td class='covered'><pre><span  class='survived'>                    &#38;&#38; field.getColumnNo() + minimumSymbolsBetween &#60;= objCalledOn.getColumnNo()) {</span></pre></td></tr>"
  "HiddenFieldCheck.java.html:<td class='covered'><pre><span  class='survived'>            processVariable(ast);</span></pre></td></tr>"
  "IllegalInstantiationCheck.java.html:<td class='covered'><pre><span  class='survived'>        classNames.clear();</span></pre></td></tr>"
  "IllegalInstantiationCheck.java.html:<td class='covered'><pre><span  class='survived'>        imports.clear();</span></pre></td></tr>"
  "IllegalInstantiationCheck.java.html:<td class='covered'><pre><span  class='survived'>        instantiations.clear();</span></pre></td></tr>"
  "IllegalInstantiationCheck.java.html:<td class='covered'><pre><span  class='survived'>                processClassDef(ast);</span></pre></td></tr>"
  "IllegalTypeCheck.java.html:<td class='covered'><pre><span  class='survived'>        while (toVisit != null) {</span></pre></td></tr>"
  "MultipleVariableDeclarationsCheck.java.html:<td class='covered'><pre><span  class='survived'>                    &#38;&#38; newNode.getColumnNo() &#62; currentNode.getColumnNo()) {</span></pre></td></tr>"
  "MultipleVariableDeclarationsCheck.java.html:<td class='covered'><pre><span  class='survived'>            if (newNode.getLineNo() &#62; currentNode.getLineNo()</span></pre></td></tr>"
  "MultipleVariableDeclarationsCheck.java.html:<td class='covered'><pre><span  class='survived'>                || newNode.getLineNo() == currentNode.getLineNo()</span></pre></td></tr>"
  "RequireThisCheck.java.html:<td class='covered'><pre><span  class='survived'>                    &#38;&#38; ast1.getColumnNo() &#60; ast2.getColumnNo()) {</span></pre></td></tr>"
  "RequireThisCheck.java.html:<td class='covered'><pre><span  class='survived'>        current.clear();</span></pre></td></tr>"
  "RequireThisCheck.java.html:<td class='covered'><pre><span  class='survived'>        final boolean methodNameInMethodCall = parentType == TokenTypes.DOT</span></pre></td></tr>"
  "UnnecessaryParenthesesCheck.java.html:<td class='covered'><pre><span  class='survived'>        if (type != TokenTypes.ASSIGN</span></pre></td></tr>"
  "VariableDeclarationUsageDistanceCheck.java.html:<td class='covered'><pre><span  class='survived'>                &#38;&#38; currentNode.getType() == TokenTypes.LITERAL_CATCH) {</span></pre></td></tr>"
  "VariableDeclarationUsageDistanceCheck.java.html:<td class='covered'><pre><span  class='survived'>                if (currentNode.getType() == TokenTypes.LITERAL_IF) {</span></pre></td></tr>"
  "VariableDeclarationUsageDistanceCheck.java.html:<td class='covered'><pre><span  class='survived'>            if (elseBlock.getType() == TokenTypes.LITERAL_ELSE) {</span></pre></td></tr>"
  "VariableDeclarationUsageDistanceCheck.java.html:<td class='covered'><pre><span  class='survived'>        if (!isVarInOperatorDeclaration &#38;&#38; operator.getType() == TokenTypes.LITERAL_IF) {</span></pre></td></tr>"
  "VariableDeclarationUsageDistanceCheck.java.html:<td class='covered'><pre><span  class='survived'>        while (currentNode != null</span></pre></td></tr>"
  );
  checkPitestReport "${ignoredItems[@]}"
  ;;

pitest-indentation)
  mvn -e -P$1 clean test org.pitest:pitest-maven:mutationCoverage;
  declare -a ignoredItems=(
  "AbstractExpressionHandler.java.html:<td class='covered'><pre><span  class='survived'>            if (colNum == null || thisLineColumn &#60; colNum) {</span></pre></td></tr>"
  "AbstractExpressionHandler.java.html:<td class='covered'><pre><span  class='survived'>        if (currLine &#60; realStart) {</span></pre></td></tr>"
  "AbstractExpressionHandler.java.html:<td class='covered'><pre><span  class='survived'>            if (toTest.getColumnNo() &#60; first.getColumnNo()) {</span></pre></td></tr>"
  "ArrayInitHandler.java.html:<td class='covered'><pre><span  class='survived'>        if (firstChildPos &#62;= 0) {</span></pre></td></tr>"
  "BlockParentHandler.java.html:<td class='covered'><pre><span  class='survived'>                level.addAcceptedIndent(level.getFirstIndentLevel() + getLineWrappingIndent());</span></pre></td></tr>"
  "BlockParentHandler.java.html:<td class='covered'><pre><span  class='survived'>        return getIndentCheck().getLineWrappingIndentation();</span></pre></td></tr>"
  "BlockParentHandler.java.html:<td class='covered'><pre><span  class='survived'>        return true;</span></pre></td></tr>"
  "CommentsIndentationCheck.java.html:<td class='covered'><pre><span  class='survived'>            &#38;&#38; root.getFirstChild().getFirstChild().getFirstChild().getNextSibling() != null;</span></pre></td></tr>"
  "CommentsIndentationCheck.java.html:<td class='covered'><pre><span  class='survived'>                distanceAim += nextToken.getLastChild().getLineNo() - nextToken.getLineNo();</span></pre></td></tr>"
  "CommentsIndentationCheck.java.html:<td class='covered'><pre><span  class='survived'>                if (comment.getColumnNo() &#60; nextStmt.getColumnNo()) {</span></pre></td></tr>"
  "CommentsIndentationCheck.java.html:<td class='covered'><pre><span  class='survived'>        if (comment.getType() == TokenTypes.SINGLE_LINE_COMMENT) {</span></pre></td></tr>"
  "CommentsIndentationCheck.java.html:<td class='covered'><pre><span  class='survived'>                if (isUsingOfObjectReferenceToInvokeMethod(blockBody)) {</span></pre></td></tr>"
  "CommentsIndentationCheck.java.html:<td class='covered'><pre><span  class='survived'>        if (isUsingOfObjectReferenceToInvokeMethod(root)) {</span></pre></td></tr>"
  "CommentsIndentationCheck.java.html:<td class='covered'><pre><span  class='survived'>            if (nextToken.getType() == TokenTypes.BLOCK_COMMENT_BEGIN) {</span></pre></td></tr>"
  "CommentsIndentationCheck.java.html:<td class='covered'><pre><span  class='survived'>            if (root.getFirstChild().getType() == TokenTypes.LITERAL_NEW) {</span></pre></td></tr>"
  "CommentsIndentationCheck.java.html:<td class='covered'><pre><span  class='survived'>        if (root.getLineNo() &#62;= comment.getLineNo()) {</span></pre></td></tr>"
  "CommentsIndentationCheck.java.html:<td class='covered'><pre><span  class='survived'>            || previousSibling.getType() == TokenTypes.ANNOTATION_DEF;</span></pre></td></tr>"
  "CommentsIndentationCheck.java.html:<td class='covered'><pre><span  class='survived'>            || previousSibling.getType() == TokenTypes.CLASS_DEF</span></pre></td></tr>"
  "CommentsIndentationCheck.java.html:<td class='covered'><pre><span  class='survived'>            || previousSibling.getType() == TokenTypes.INTERFACE_DEF</span></pre></td></tr>"
  "CommentsIndentationCheck.java.html:<td class='covered'><pre><span  class='survived'>        return root.getFirstChild().getFirstChild().getFirstChild() != null</span></pre></td></tr>"
  "ElseHandler.java.html:<td class='covered'><pre><span  class='survived'>        return getMainAst().getFirstChild();</span></pre></td></tr>"
  "ElseHandler.java.html:<td class='covered'><pre><span  class='survived'>            super.checkTopLevelToken();</span></pre></td></tr>"
  "ForHandler.java.html:<td class='covered'><pre><span  class='survived'>            checkExpressionSubtree(forEach, expected, false, false);</span></pre></td></tr>"
  "ForHandler.java.html:<td class='covered'><pre><span  class='survived'>        checkWrappingIndentation(getMainAst(), getForLoopRightParen(getMainAst()));</span></pre></td></tr>"
  "HandlerFactory.java.html:<td class='covered'><pre><span  class='survived'>        createdHandlers.clear();</span></pre></td></tr>"
  "HandlerFactory.java.html:<td class='covered'><pre><span  class='survived'>        register(TokenTypes.INDEX_OP, IndexHandler.class);</span></pre></td></tr>"
  "IndentationCheck.java.html:<td class='covered'><pre><span  class='survived'>        handlerFactory.clearCreatedHandlers();</span></pre></td></tr>"
  "IndentationCheck.java.html:<td class='covered'><pre><span  class='survived'>        handlers.clear();</span></pre></td></tr>"
  "IndentationCheck.java.html:<td class='covered'><pre><span  class='survived'>        primordialHandler.checkIndentation();</span></pre></td></tr>"
  "IndentLevel.java.html:<td class='covered'><pre><span  class='survived'>            for (int i = levels.nextSetBit(0); i &#62;= 0;</span></pre></td></tr>"
  "MethodDefHandler.java.html:<td class='covered'><pre><span  class='survived'>            if (node.getLineNo() &#60; lineStart) {</span></pre></td></tr>"
  "MethodDefHandler.java.html:<td class='covered'><pre><span  class='survived'>            if (node.getType() == TokenTypes.ANNOTATION) {</span></pre></td></tr>"
  "NewHandler.java.html:<td class='covered'><pre><span  class='survived'>            checkExpressionSubtree(type, getIndent(), false, false);</span></pre></td></tr>"
  "NewHandler.java.html:<td class='covered'><pre><span  class='survived'>        checkLeftParen(lparen);</span></pre></td></tr>"
  "NewHandler.java.html:<td class='covered'><pre><span  class='survived'>        return false;</span></pre></td></tr>"
  "PackageDefHandler.java.html:<td class='covered'><pre><span  class='survived'>        checkWrappingIndentation(getMainAst(), semi);</span></pre></td></tr>"
  "StaticInitHandler.java.html:<td class='covered'><pre><span  class='survived'>        return false;</span></pre></td></tr>"
  "SwitchHandler.java.html:<td class='covered'><pre><span  class='survived'>        checkExpressionSubtree(</span></pre></td></tr>"
  "SwitchHandler.java.html:<td class='covered'><pre><span  class='survived'>        checkSwitchExpr();</span></pre></td></tr>"
  "SynchronizedHandler.java.html:<td class='covered'><pre><span  class='survived'>        checkExpressionSubtree(syncAst, expected, false, false);</span></pre></td></tr>"
  "SynchronizedHandler.java.html:<td class='covered'><pre><span  class='survived'>            checkSynchronizedExpr();</span></pre></td></tr>"
  "SynchronizedHandler.java.html:<td class='covered'><pre><span  class='survived'>            checkWrappingIndentation(getMainAst(),</span></pre></td></tr>"
  "SynchronizedHandler.java.html:<td class='covered'><pre><span  class='survived'>            super.checkIndentation();</span></pre></td></tr>"
  "TryHandler.java.html:<td class='covered'><pre><span  class='survived'>            checkTryResParen(getTryResLparen(), &#34;lparen&#34;);</span></pre></td></tr>"
  );
  checkPitestReport "${ignoredItems[@]}"
  ;;

pitest-javadoc)
  mvn -e -P$1 clean test org.pitest:pitest-maven:mutationCoverage;
  declare -a ignoredItems=(
  "AbstractJavadocCheck.java.html:<td class='covered'><pre><span  class='survived'>            Arrays.sort(acceptableJavadocTokens);</span></pre></td></tr>"
  "AbstractJavadocCheck.java.html:<td class='covered'><pre><span  class='survived'>            Arrays.sort(defaultJavadocTokens);</span></pre></td></tr>"
  "AbstractJavadocCheck.java.html:<td class='covered'><pre><span  class='survived'>        beginJavadocTree(root);</span></pre></td></tr>"
  "AbstractJavadocCheck.java.html:<td class='covered'><pre><span  class='survived'>        finishJavadocTree(root);</span></pre></td></tr>"
  "AbstractJavadocCheck.java.html:<td class='covered'><pre><span  class='survived'>        javadocTokens.clear();</span></pre></td></tr>"
  "AbstractJavadocCheck.java.html:<td class='covered'><pre><span  class='survived'>        TREE_CACHE.get().clear();</span></pre></td></tr>"
  "AbstractJavadocCheck.java.html:<td class='covered'><pre><span  class='survived'>        TREE_CACHE.get().clear();</span></pre></td></tr>"
  "AbstractTypeAwareCheck.java.html:<td class='covered'><pre><span  class='survived'>        if (!currentClassName.isEmpty()) {</span></pre></td></tr>"
  "AbstractTypeAwareCheck.java.html:<td class='covered'><pre><span  class='survived'>            if (dotIdx == -1) {</span></pre></td></tr>"
  "AbstractTypeAwareCheck.java.html:<td class='covered'><pre><span  class='survived'>        imports.clear();</span></pre></td></tr>"
  "AbstractTypeAwareCheck.java.html:<td class='covered'><pre><span  class='survived'>        typeParams.clear();</span></pre></td></tr>"
  "JavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>        final int col = noargMultilineStart.start(1) - 1;</span></pre></td></tr>"
  "JavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>        return (ast.getType() == TokenTypes.METHOD_DEF || ast.getType() == TokenTypes.CTOR_DEF)</span></pre></td></tr>"
  "JavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>        return true;</span></pre></td></tr>"
  "JavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>        while (remIndex &#60; lines.length) {</span></pre></td></tr>"
  "JavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>        while (remIndex &#60; lines.length) {</span></pre></td></tr>"
  "JavadocPackageCheck.java.html:<td class='covered'><pre><span  class='survived'>        directoriesChecked.clear();</span></pre></td></tr>"
  "JavadocPackageCheck.java.html:<td class='covered'><pre><span  class='survived'>        setFileExtensions(&#34;java&#34;);</span></pre></td></tr>"
  "JavadocPackageCheck.java.html:<td class='covered'><pre><span  class='survived'>        super.beginProcessing(charset);</span></pre></td></tr>"
  "JavadocParagraphCheck.java.html:<td class='covered'><pre><span  class='survived'>            if (nextNode.getType() == JavadocTokenTypes.TEXT</span></pre></td></tr>"
  "JavadocParagraphCheck.java.html:<td class='covered'><pre><span  class='survived'>                    || nextNode.getType() == JavadocTokenTypes.HTML_ELEMENT) {</span></pre></td></tr>"
  "JavadocStyleCheck.java.html:<td class='covered'><pre><span  class='survived'>                    &#38;&#38; builder.charAt(index - 1) == &#39;*&#39;) {</span></pre></td></tr>"
  "JavadocStyleCheck.java.html:<td class='covered'><pre><span  class='survived'>            else if (index &#62; 0 &#38;&#38; builder.charAt(index) == &#39;/&#39;</span></pre></td></tr>"
  "JavadocStyleCheck.java.html:<td class='covered'><pre><span  class='survived'>                trimTail(builder);</span></pre></td></tr>"
  "JavadocTagContinuationIndentationCheck.java.html:<td class='covered'><pre><span  class='survived'>                            &#38;&#38; (text.length() &#60;= offset</span></pre></td></tr>"
  "JavadocTagContinuationIndentationCheck.java.html:<td class='covered'><pre><span  class='survived'>        while (inlineTag != null) {</span></pre></td></tr>"
  "JavadocTagInfo.java.html:<td class='covered'><pre><span  class='survived'>            .collect(Collectors.toMap(JavadocTagInfo::getName, tagName -&#62; tagName)));</span></pre></td></tr>"
  "JavadocTagInfo.java.html:<td class='covered'><pre><span  class='survived'>            .collect(Collectors.toMap(JavadocTagInfo::getText, tagText -&#62; tagText)));</span></pre></td></tr>"
  "JavadocTag.java.html:<td class='uncovered'><pre><span  class='survived'>        return tagInfo == JavadocTagInfo.SEE</span></pre></td></tr>"
  "JavadocTypeCheck.java.html:<td class='covered'><pre><span  class='survived'>                    tagCount++;</span></pre></td></tr>"
  "SummaryJavadocCheck.java.html:<td class='covered'><pre><span  class='survived'>        for (int i = 0; !found &#38;&#38; i &#60; children.length - 1; i++) {</span></pre></td></tr>"
  "SummaryJavadocCheck.java.html:<td class='covered'><pre><span  class='survived'>            if (child.getType() != JavadocTokenTypes.JAVADOC_INLINE_TAG</span></pre></td></tr>"
  "TagParser.java.html:<td class='covered'><pre><span  class='survived'>                       &#38;&#38; (Character.isWhitespace(currentLine.charAt(column))</span></pre></td></tr>"
  "TagParser.java.html:<td class='covered'><pre><span  class='survived'>                           || currentLine.charAt(column) == &#39;*&#39;)) {</span></pre></td></tr>"
  "TagParser.java.html:<td class='covered'><pre><span  class='survived'>                while (column &#60; currentLine.length()</span></pre></td></tr>"
  "WriteTagCheck.java.html:<td class='covered'><pre><span  class='survived'>            log(lineNo, MSG_MISSING_TAG, tag);</span></pre></td></tr>"
  "WriteTagCheck.java.html:<td class='covered'><pre><span  class='survived'>                    tagCount += 1;</span></pre></td></tr>"
  );
  checkPitestReport "${ignoredItems[@]}"
  ;;

# pitesttyle-gui)
#   mvn -e -P$1 clean test org.pitest:pitest-maven:mutationCoverage;
#   # post validation is skipped, we do not test gui throughly
#   ;;

*)
  echo "Unexpected argument: $1"
  sleep 5s
  false
  ;;

esac



