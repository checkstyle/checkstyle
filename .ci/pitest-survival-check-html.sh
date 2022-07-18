#!/bin/bash
# Attention, there is no "-x" to avoid problems on CircleCI
set -e

###############################
function checkPitestReport() {
  local -n ignored=$1
  fail=0
  SEARCH_REGEXP="(span  class='survived'|class='uncovered'><pre>)"
  PIT_REPORTS_DIRECTORY="target/pit-reports"
  if [ -d "$PIT_REPORTS_DIRECTORY" ]; then
    grep -irE "$SEARCH_REGEXP" "$PIT_REPORTS_DIRECTORY" \
       | sed -E 's/.*\/([A-Za-z]+.java.html)/\1/' | LC_ALL=C sort > target/actual.txt
    printf "%s\n" "${ignored[@]}" | sed '/^$/d' | LC_ALL=C sort > target/ignored.txt
    if [ -n "$2" ] ; then
        local -n unstableMutations=$2
        printf "%s\n" "${unstableMutations[@]}" | sed '/^$/d' | LC_ALL=C sort > target/unstable.txt
        if grep -Fxf target/unstable.txt target/actual.txt > target/unstableMatching.txt ; then
            echo "Following unstable mutations were encountered:"
            cat target/unstableMatching.txt
            grep -xvFf target/unstableMatching.txt target/actual.txt > target/tempActual.txt
            cat target/tempActual.txt > target/actual.txt
        fi;
    fi;
    if [ "$(diff --unified -w target/ignored.txt target/actual.txt)" != "" ] ; then
        fail=1
        echo "Actual:" ;
        grep -irE "$SEARCH_REGEXP" "$PIT_REPORTS_DIRECTORY" \
           | sed -E 's/.*\/([A-Za-z]+.java.html)/\1/' | sort
        echo "Ignore:" ;
        printf '%s\n' "${ignored[@]}"
        echo "Diff:"
        diff --unified -w target/ignored.txt target/actual.txt | cat
    fi;
    if [ "$fail" -ne "0" ]; then
      echo "Difference between 'Actual' and 'Ignore' lists is detected, lists should be equal."
      echo "build will be failed."
    fi
  else
    fail=1
    echo "No pit-reports directory found"
  fi
  sleep 5s
  exit $fail
}
###############################

case $1 in

pitest-annotation|pitest-design \
|pitest-metrics|pitest-modifier|pitest-naming \
|pitest-sizes|pitest-whitespace \
|pitest-api|pitest-blocks \
|pitest-packagenamesloader \
|pitest-common-2|pitest-misc|pitest-xpath \
|pitest-filters \
|pitest-regexp \
|pitest-meta \
|pitest-tree-walker \
|pitest-utils \
|pitest-java-ast-visitor)
  declare -a ignoredItems=();
  checkPitestReport ignoredItems
  ;;

pitest-main)
  declare -a ignoredItems=(
  "Main.java.html:<td class='uncovered'><pre><span  class=''>        }</span></pre></td></tr>"
  );
  checkPitestReport ignoredItems
  ;;

pitest-header)
  declare -a ignoredItems=(
  "RegexpHeaderCheck.java.html:<td class='covered'><pre><span  class='survived'>                    isMatch = headerLineNo == headerSize</span></pre></td></tr>"
  "RegexpHeaderCheck.java.html:<td class='covered'><pre><span  class='survived'>                            || isMatch(line, headerLineNo);</span></pre></td></tr>"
  );
  checkPitestReport ignoredItems
  ;;

pitest-imports)
  declare -a ignoredItems=(
  "ImportControlLoader.java.html:<td class='covered'><pre><span  class='survived'>        else if (ALLOW_ELEMENT_NAME.equals(qName) || &#34;disallow&#34;.equals(qName)) {</span></pre></td></tr>"
  "PkgImportControl.java.html:<td class='covered'><pre><span  class='survived'>        if (regex || parent.regex) {</span></pre></td></tr>"
  "PkgImportControl.java.html:<td class='covered'><pre><span  class='survived'>        if (regex) {</span></pre></td></tr>"
  );
  checkPitestReport ignoredItems
  ;;

pitest-common)
  declare -a ignoredItems=(
  "DefaultLogger.java.html:<td class='covered'><pre><span  class='survived'>        closeError = errorStreamOptions == OutputStreamOptions.CLOSE;</span></pre></td></tr>"
  "DefaultLogger.java.html:<td class='covered'><pre><span  class='survived'>        closeInfo = infoStreamOptions == OutputStreamOptions.CLOSE;</span></pre></td></tr>"
  "DefaultLogger.java.html:<td class='covered'><pre><span  class='survived'>        if (closeError) {</span></pre></td></tr>"
  "DefaultLogger.java.html:<td class='covered'><pre><span  class='survived'>        if (closeInfo) {</span></pre></td></tr>"
  "PackageObjectFactory.java.html:<td class='covered'><pre><span  class='survived'>        if (instance == null</span></pre></td></tr>"
  "PackageObjectFactory.java.html:<td class='covered'><pre><span  class='survived'>        if (!name.contains(PACKAGE_SEPARATOR)) {</span></pre></td></tr>"
  "PackageObjectFactory.java.html:<td class='covered'><pre><span  class='survived'>                if (thirdPartyNameToFullModuleNames == null) {</span></pre></td></tr>"
  );
  checkPitestReport ignoredItems
  ;;


pitest-ant)
  declare -a ignoredItems=(
  "CheckstyleAntTask.java.html:<td class='covered'><pre><span  class='survived'>            log(&#34;To process the files took &#34; + (processingEndTime - processingStartTime)</span></pre></td></tr>"
  "CheckstyleAntTask.java.html:<td class='covered'><pre><span  class='survived'>        log(&#34;To locate the files took &#34; + (endTime - startTime) + TIME_SUFFIX,</span></pre></td></tr>"
  );
  checkPitestReport ignoredItems
  ;;

pitest-indentation)
  declare -a ignoredItems=(
  "AbstractExpressionHandler.java.html:<td class='covered'><pre><span  class='survived'>                    &#38;&#38; curNode.getColumnNo() &#60; realStart.getColumnNo()) {</span></pre></td></tr>"
  "AbstractExpressionHandler.java.html:<td class='covered'><pre><span  class='survived'>            if (colNum == null || thisLineColumn &#60; colNum) {</span></pre></td></tr>"
  "AbstractExpressionHandler.java.html:<td class='covered'><pre><span  class='survived'>            if (toTest.getColumnNo() &#60; first.getColumnNo()) {</span></pre></td></tr>"
  "BlockParentHandler.java.html:<td class='covered'><pre><span  class='survived'>        return getIndentCheck().getLineWrappingIndentation();</span></pre></td></tr>"
  "CommentsIndentationCheck.java.html:<td class='covered'><pre><span  class='survived'>            &#38;&#38; root.getFirstChild().getFirstChild().getFirstChild().getNextSibling() != null;</span></pre></td></tr>"
  "CommentsIndentationCheck.java.html:<td class='covered'><pre><span  class='survived'>                if (isUsingOfObjectReferenceToInvokeMethod(blockBody)) {</span></pre></td></tr>"
  "CommentsIndentationCheck.java.html:<td class='covered'><pre><span  class='survived'>        if (isUsingOfObjectReferenceToInvokeMethod(root)) {</span></pre></td></tr>"
  "CommentsIndentationCheck.java.html:<td class='covered'><pre><span  class='survived'>            if (root.getFirstChild().getType() == TokenTypes.LITERAL_NEW) {</span></pre></td></tr>"
  "CommentsIndentationCheck.java.html:<td class='covered'><pre><span  class='survived'>        if (root.getLineNo() &#62;= comment.getLineNo()) {</span></pre></td></tr>"
  "CommentsIndentationCheck.java.html:<td class='covered'><pre><span  class='survived'>        return root.getFirstChild().getFirstChild().getFirstChild() != null</span></pre></td></tr>"
  "HandlerFactory.java.html:<td class='covered'><pre><span  class='survived'>        createdHandlers.clear();</span></pre></td></tr>"
  "HandlerFactory.java.html:<td class='covered'><pre><span  class='survived'>        register(TokenTypes.INDEX_OP, IndexHandler.class);</span></pre></td></tr>"
  "IndentationCheck.java.html:<td class='covered'><pre><span  class='survived'>        handlerFactory.clearCreatedHandlers();</span></pre></td></tr>"
  "IndentationCheck.java.html:<td class='covered'><pre><span  class='survived'>        handlers.clear();</span></pre></td></tr>"
  "IndentationCheck.java.html:<td class='covered'><pre><span  class='survived'>        primordialHandler.checkIndentation();</span></pre></td></tr>"
  "MethodDefHandler.java.html:<td class='covered'><pre><span  class='survived'>            if (node.getLineNo() &#60; lineStart) {</span></pre></td></tr>"
  "NewHandler.java.html:<td class='covered'><pre><span  class='survived'>        return false;</span></pre></td></tr>"
  "SwitchHandler.java.html:<td class='covered'><pre><span  class='survived'>        checkExpressionSubtree(</span></pre></td></tr>"
  "SwitchHandler.java.html:<td class='covered'><pre><span  class='survived'>        checkSwitchExpr();</span></pre></td></tr>"
  "SynchronizedHandler.java.html:<td class='covered'><pre><span  class='survived'>        checkExpressionSubtree(syncAst, expected, false, false);</span></pre></td></tr>"
  "SynchronizedHandler.java.html:<td class='covered'><pre><span  class='survived'>            checkSynchronizedExpr();</span></pre></td></tr>"
  "SynchronizedHandler.java.html:<td class='covered'><pre><span  class='survived'>            checkWrappingIndentation(getMainAst(),</span></pre></td></tr>"
  "TryHandler.java.html:<td class='covered'><pre><span  class='survived'>            checkTryResParen(getTryResLparen(), &#34;lparen&#34;);</span></pre></td></tr>"
  );
  checkPitestReport ignoredItems
  ;;

pitest-coding-1)
  declare -a ignoredItems=(
  "UnnecessaryParenthesesCheck.java.html:<td class='covered'><pre><span  class='survived'>        else if (type != TokenTypes.ASSIGN</span></pre></td></tr>"
  "UnnecessaryParenthesesCheck.java.html:<td class='covered'><pre><span  class='survived'>        if (firstChild != null &#38;&#38; firstChild.getType() == TokenTypes.LPAREN) {</span></pre></td></tr>"
  "UnnecessaryParenthesesCheck.java.html:<td class='covered'><pre><span  class='survived'>        if (type == TokenTypes.LAMBDA &#38;&#38; isLambdaSingleParameterSurrounded(ast)) {</span></pre></td></tr>"
  "VariableDeclarationUsageDistanceCheck.java.html:<td class='covered'><pre><span  class='survived'>                &#38;&#38; currentAst.getType() != TokenTypes.RCURLY) {</span></pre></td></tr>"
  "VariableDeclarationUsageDistanceCheck.java.html:<td class='covered'><pre><span  class='survived'>                &#38;&#38; currentNode.getType() == TokenTypes.LITERAL_CATCH) {</span></pre></td></tr>"
  "VariableDeclarationUsageDistanceCheck.java.html:<td class='covered'><pre><span  class='survived'>                &#38;&#38; currentStatementAst.getType() != TokenTypes.RCURLY) {</span></pre></td></tr>"
  "VariableDeclarationUsageDistanceCheck.java.html:<td class='covered'><pre><span  class='survived'>                else if (isChild(currentNode, variable)) {</span></pre></td></tr>"
  "VariableDeclarationUsageDistanceCheck.java.html:<td class='covered'><pre><span  class='survived'>                if (currentNode.getType() == TokenTypes.LITERAL_IF) {</span></pre></td></tr>"
  "VariableDeclarationUsageDistanceCheck.java.html:<td class='covered'><pre><span  class='survived'>            if (curNode.getType() == ast.getType() &#38;&#38; curNode.getText().equals(ast.getText())) {</span></pre></td></tr>"
  "VariableDeclarationUsageDistanceCheck.java.html:<td class='covered'><pre><span  class='survived'>            if (currentNodeType == TokenTypes.SLIST) {</span></pre></td></tr>"
  "VariableDeclarationUsageDistanceCheck.java.html:<td class='covered'><pre><span  class='survived'>        if (!firstUsageFound) {</span></pre></td></tr>"
  "VariableDeclarationUsageDistanceCheck.java.html:<td class='covered'><pre><span  class='survived'>        if (!isVarInOperatorDeclaration &#38;&#38; operator.getType() == TokenTypes.LITERAL_IF) {</span></pre></td></tr>"
  "VariableDeclarationUsageDistanceCheck.java.html:<td class='covered'><pre><span  class='survived'>        while (result</span></pre></td></tr>"
  );
  checkPitestReport ignoredItems
  ;;

pitest-coding-2)
  declare -a ignoredItems=(
  "FinalLocalVariableCheck.java.html:<td class='covered'><pre><span  class='survived'>                            &#38;&#38; isSameVariables(storedVariable, variable)</span></pre></td></tr>"
  );
  checkPitestReport ignoredItems
  ;;

pitest-coding-require-this-check)
  declare -a ignoredItems=(
  "RequireThisCheck.java.html:<td class='covered'><pre><span  class='survived'>        if (variableDeclarationFrame.getType() == FrameType.CLASS_FRAME) {</span></pre></td></tr>"
  );
  checkPitestReport ignoredItems
  ;;

pitest-javadoc)
  declare -a ignoredItems=(
  "AbstractJavadocCheck.java.html:<td class='covered'><pre><span  class='survived'>                            JavadocUtil.getTokenName(javadocTokenId), getClass().getName());</span></pre></td></tr>"
  "AbstractJavadocCheck.java.html:<td class='covered'><pre><span  class='survived'>                            javadocToken, getClass().getName());</span></pre></td></tr>"
  "AbstractJavadocCheck.java.html:<td class='covered'><pre><span  class='survived'>                        waitsForProcessing = shouldBeProcessed(curNode);</span></pre></td></tr>"
  "AbstractJavadocCheck.java.html:<td class='covered'><pre><span  class='survived'>                    blockCommentNode.getColumnNo());</span></pre></td></tr>"
  "AbstractJavadocCheck.java.html:<td class='covered'><pre><span  class='survived'>                    if (curNode != null) {</span></pre></td></tr>"
  "AbstractJavadocCheck.java.html:<td class='covered'><pre><span  class='survived'>                if (toVisit == null) {</span></pre></td></tr>"
  "AbstractJavadocCheck.java.html:<td class='covered'><pre><span  class='survived'>        if (getRequiredJavadocTokens().length != 0) {</span></pre></td></tr>"
  "AtclauseOrderCheck.java.html:<td class='covered'><pre><span  class='survived'>            customOrder.add(order.trim());</span></pre></td></tr>"
  "HtmlTag.java.html:<td class='covered'><pre><span  class='survived'>        final int endOfText = Math.min(startOfText + MAX_TEXT_LEN, text.length());</span></pre></td></tr>"
  "JavadocContentLocationCheck.java.html:<td class='covered'><pre><span  class='survived'>        location = JavadocContentLocationOption.valueOf(value.trim().toUpperCase(Locale.ENGLISH));</span></pre></td></tr>"
  "JavadocContentLocationCheck.java.html:<td class='covered'><pre><span  class='survived'>    private JavadocContentLocationOption location = JavadocContentLocationOption.SECOND_LINE;</span></pre></td></tr>"
  "JavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>                    .getColumnNo());</span></pre></td></tr>"
  "JavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>                checkReturnTag(tags, ast.getLineNo(), true);</span></pre></td></tr>"
  "JavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>                final int col = calculateTagColumn(noargCurlyMatcher, i, startColumnNumber);</span></pre></td></tr>"
  "JavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>            Arrays.copyOf(accessModifiers, accessModifiers.length);</span></pre></td></tr>"
  "JavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>            columnNo = fullIdent.getColumnNo();</span></pre></td></tr>"
  "JavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>            currentClassName = currentClassName.substring(0, dotIdx);</span></pre></td></tr>"
  "JavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>            final Token token = new Token(tag.getFirstArg(), tag.getLineNo(), tag</span></pre></td></tr>"
  "JavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>            final int dotIdx = currentClassName.lastIndexOf(&#39;$&#39;);</span></pre></td></tr>"
  "JavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>            foundThrows.add(documentedClassInfo.getName().getText());</span></pre></td></tr>"
  "JavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>            if (CommonUtil.startsWithChar(arg1, &#39;&#60;&#39;) &#38;&#38; CommonUtil.endsWithChar(arg1, &#39;&#62;&#39;)) {</span></pre></td></tr>"
  "JavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>            if (ancestor.getType() == TokenTypes.LITERAL_TRY</span></pre></td></tr>"
  "JavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>            if (child.getType() == TokenTypes.PARAMETER_DEF) {</span></pre></td></tr>"
  "JavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>            if (class1.contains(separator) || class2.contains(separator)) {</span></pre></td></tr>"
  "JavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>            lineNo = fullIdent.getLineNo();</span></pre></td></tr>"
  "JavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>            || ast.getType() == TokenTypes.ENUM_DEF</span></pre></td></tr>"
  "JavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>            || ast.getType() == TokenTypes.INTERFACE_DEF</span></pre></td></tr>"
  "JavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>            || ast.getType() == TokenTypes.RECORD_DEF) {</span></pre></td></tr>"
  "JavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>        DetailAST ancestor = throwAst.getParent();</span></pre></td></tr>"
  "JavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>        String innerClass = ident.getText();</span></pre></td></tr>"
  "JavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>        currentClassName = &#34;&#34;;</span></pre></td></tr>"
  "JavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>        final DetailAST blockAst = methodAst.findFirstToken(TokenTypes.SLIST);</span></pre></td></tr>"
  "JavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>        final DetailAST ident = ast.findFirstToken(TokenTypes.IDENT);</span></pre></td></tr>"
  "JavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>        if (ast.getType() == TokenTypes.CLASS_DEF</span></pre></td></tr>"
  "JavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>        if (validateThrows &#38;&#38; reportExpectedTags) {</span></pre></td></tr>"
  "JavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>        return surroundingAccessModifier != null</span></pre></td></tr>"
  "JavadocMissingLeadingAsteriskCheck.java.html:<td class='covered'><pre><span  class='survived'>        if (detailNode.getType() == JavadocTokenTypes.TEXT</span></pre></td></tr>"
  "JavadocNodeImpl.java.html:<td class='covered'><pre><span  class='survived'>                + &#34;, children=&#34; + Objects.hashCode(children)</span></pre></td></tr>"
  "JavadocNodeImpl.java.html:<td class='covered'><pre><span  class='survived'>                .map(array -&#62; Arrays.copyOf(array, array.length))</span></pre></td></tr>"
  "JavadocNodeImpl.java.html:<td class='covered'><pre><span  class='survived'>        this.children = Arrays.copyOf(children, children.length);</span></pre></td></tr>"
  "JavadocParagraphCheck.java.html:<td class='covered'><pre><span  class='survived'>                    &#38;&#38; previousNode.getType() != JavadocTokenTypes.NEWLINE</span></pre></td></tr>"
  "JavadocParagraphCheck.java.html:<td class='covered'><pre><span  class='survived'>                    &#38;&#38; previousNode.getType() != JavadocTokenTypes.TEXT) {</span></pre></td></tr>"
  "JavadocParagraphCheck.java.html:<td class='covered'><pre><span  class='survived'>                || previousNode.getType() != JavadocTokenTypes.LEADING_ASTERISK</span></pre></td></tr>"
  "JavadocParagraphCheck.java.html:<td class='covered'><pre><span  class='survived'>            if (previousSibling.getType() == JavadocTokenTypes.TEXT</span></pre></td></tr>"
  "JavadocParagraphCheck.java.html:<td class='covered'><pre><span  class='survived'>        DetailNode newLine = JavadocUtil.getPreviousSibling(node);</span></pre></td></tr>"
  "JavadocParagraphCheck.java.html:<td class='covered'><pre><span  class='survived'>        DetailNode tag = JavadocUtil.getNextSibling(node);</span></pre></td></tr>"
  "JavadocStyleCheck.java.html:<td class='covered'><pre><span  class='survived'>                    &#38;&#38; builder.charAt(index - 1) == &#39;*&#39;) {</span></pre></td></tr>"
  "JavadocStyleCheck.java.html:<td class='covered'><pre><span  class='survived'>                    builder.deleteCharAt(index - 1);</span></pre></td></tr>"
  "JavadocStyleCheck.java.html:<td class='covered'><pre><span  class='survived'>                    index += 2;</span></pre></td></tr>"
  "JavadocStyleCheck.java.html:<td class='covered'><pre><span  class='survived'>                builder.deleteCharAt(index);</span></pre></td></tr>"
  "JavadocStyleCheck.java.html:<td class='covered'><pre><span  class='survived'>                while (builder.charAt(index - 1) == &#39;*&#39;) {</span></pre></td></tr>"
  "JavadocStyleCheck.java.html:<td class='covered'><pre><span  class='survived'>            else if (index &#62; 0 &#38;&#38; builder.charAt(index) == &#39;/&#39;</span></pre></td></tr>"
  "JavadocStyleCheck.java.html:<td class='covered'><pre><span  class='survived'>            if (Character.isWhitespace(builder.charAt(index))) {</span></pre></td></tr>"
  "JavadocStyleCheck.java.html:<td class='covered'><pre><span  class='survived'>            if (token.equalsIgnoreCase(tag.getId())) {</span></pre></td></tr>"
  "JavadocStyleCheck.java.html:<td class='covered'><pre><span  class='survived'>        if (ast.getType() == TokenTypes.PACKAGE_DEF) {</span></pre></td></tr>"
  "JavadocStyleCheck.java.html:<td class='covered'><pre><span  class='survived'>        return ALLOWED_TAGS.contains(tag.getId().toLowerCase(Locale.ENGLISH));</span></pre></td></tr>"
  "JavadocStyleCheck.java.html:<td class='covered'><pre><span  class='survived'>        return SINGLE_TAGS.contains(tag.getId().toLowerCase(Locale.ENGLISH));</span></pre></td></tr>"
  "JavadocStyleCheck.java.html:<td class='covered'><pre><span  class='survived'>        return builder.toString().trim();</span></pre></td></tr>"
  "JavadocTagContinuationIndentationCheck.java.html:<td class='covered'><pre><span  class='survived'>            if (CommonUtil.isBlank(text)) {</span></pre></td></tr>"
  "JavadocTagContinuationIndentationCheck.java.html:<td class='covered'><pre><span  class='survived'>            inlineTag = inlineTag.getParent();</span></pre></td></tr>"
  "JavadocTagContinuationIndentationCheck.java.html:<td class='covered'><pre><span  class='survived'>        DetailNode inlineTag = description.getParent();</span></pre></td></tr>"
  "JavadocTagContinuationIndentationCheck.java.html:<td class='covered'><pre><span  class='survived'>        while (JavadocUtil.getNextSibling(node) != null) {</span></pre></td></tr>"
  "JavadocTagInfo.java.html:<td class='covered'><pre><span  class='survived'>                &#38;&#38; varType.getFirstChild().getType() == TokenTypes.ARRAY_DECLARATOR</span></pre></td></tr>"
  "JavadocTagInfo.java.html:<td class='covered'><pre><span  class='survived'>            return astType == TokenTypes.METHOD_DEF</span></pre></td></tr>"
  "JavadocTagInfo.java.html:<td class='covered'><pre><span  class='survived'>            return astType == TokenTypes.METHOD_DEF</span></pre></td></tr>"
  "JavadocTagInfo.java.html:<td class='covered'><pre><span  class='survived'>            return astType == TokenTypes.VARIABLE_DEF</span></pre></td></tr>"
  "JavadocTypeCheck.java.html:<td class='covered'><pre><span  class='survived'>                &#38;&#38; !surroundingScope.isIn(excludeScope))</span></pre></td></tr>"
  "JavadocTypeCheck.java.html:<td class='covered'><pre><span  class='survived'>                if (ScopeUtil.isOuterMostType(ast)) {</span></pre></td></tr>"
  "JavadocTypeCheck.java.html:<td class='covered'><pre><span  class='survived'>                || !customScope.isIn(excludeScope)</span></pre></td></tr>"
  "JavadocTypeCheck.java.html:<td class='covered'><pre><span  class='survived'>            &#38;&#38; !AnnotationUtil.containsAnnotation(ast, allowedAnnotations);</span></pre></td></tr>"
  "JavadocTypeCheck.java.html:<td class='covered'><pre><span  class='survived'>            .filter(JavadocTag::isParamTag)</span></pre></td></tr>"
  "JavadocTypeCheck.java.html:<td class='covered'><pre><span  class='survived'>            typeParamName = matchInAngleBrackets.group(1).trim();</span></pre></td></tr>"
  "JavadocTypeCheck.java.html:<td class='covered'><pre><span  class='survived'>        allowedAnnotations = Set.of(userAnnotations);</span></pre></td></tr>"
  "MissingJavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>                || ast.getType() == TokenTypes.COMPACT_CTOR_DEF)</span></pre></td></tr>"
  "MissingJavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>                || ast.getType() == TokenTypes.CTOR_DEF</span></pre></td></tr>"
  "MissingJavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>        return (ast.getType() == TokenTypes.METHOD_DEF</span></pre></td></tr>"
  "MissingJavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>        return (excludeScope == null</span></pre></td></tr>"
  "MissingJavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>    private int minLineCount = DEFAULT_MIN_LINE_COUNT;</span></pre></td></tr>"
  "NonEmptyAtclauseDescriptionCheck.java.html:<td class='covered'><pre><span  class='survived'>                    || !CommonUtil.isBlank(child.getText())) {</span></pre></td></tr>"
  "NonEmptyAtclauseDescriptionCheck.java.html:<td class='covered'><pre><span  class='survived'>            if (child.getType() != JavadocTokenTypes.TEXT</span></pre></td></tr>"
  "NonEmptyAtclauseDescriptionCheck.java.html:<td class='covered'><pre><span  class='survived'>            log(ast.getLineNumber(), MSG_KEY, ast.getText());</span></pre></td></tr>"
  "RequireEmptyLineBeforeBlockTagGroupCheck.java.html:<td class='covered'><pre><span  class='survived'>        while (count &#60;= 1</span></pre></td></tr>"
  "SummaryJavadocCheck.java.html:<td class='covered'><pre><span  class='survived'>                    &#38;&#38; containsForbiddenFragment(firstSentence.substring(0, endOfSentence))) {</span></pre></td></tr>"
  "SummaryJavadocCheck.java.html:<td class='covered'><pre><span  class='survived'>                    isTextPresentInsideHtmlTag = !nestedChild.getText().isBlank();</span></pre></td></tr>"
  "SummaryJavadocCheck.java.html:<td class='covered'><pre><span  class='survived'>                .matcher(firstSentence).replaceAll(&#34; &#34;).trim();</span></pre></td></tr>"
  "SummaryJavadocCheck.java.html:<td class='covered'><pre><span  class='survived'>                || ast.getType() == JavadocTokenTypes.HTML_ELEMENT</span></pre></td></tr>"
  "SummaryJavadocCheck.java.html:<td class='covered'><pre><span  class='survived'>            if (child.getType() == JavadocTokenTypes.JAVADOC_TAG) {</span></pre></td></tr>"
  "SummaryJavadocCheck.java.html:<td class='covered'><pre><span  class='survived'>            if (text.contains(periodSuffix)) {</span></pre></td></tr>"
  "SummaryJavadocCheck.java.html:<td class='covered'><pre><span  class='survived'>        DetailNode previousSibling = JavadocUtil.getPreviousSibling(inlineSummaryTag);</span></pre></td></tr>"
  "SummaryJavadocCheck.java.html:<td class='covered'><pre><span  class='survived'>        else if (!period.isEmpty()) {</span></pre></td></tr>"
  "SummaryJavadocCheck.java.html:<td class='covered'><pre><span  class='survived'>        final String returnVisible = getVisibleContent(inlineReturn);</span></pre></td></tr>"
  "SummaryJavadocCheck.java.html:<td class='covered'><pre><span  class='survived'>        final String summarySentence = sentence.trim();</span></pre></td></tr>"
  "SummaryJavadocCheck.java.html:<td class='covered'><pre><span  class='survived'>        for (int i = 0; !found; i++) {</span></pre></td></tr>"
  "SummaryJavadocCheck.java.html:<td class='covered'><pre><span  class='survived'>        return child[1].getType() == JavadocTokenTypes.CUSTOM_NAME</span></pre></td></tr>"
  "TagParser.java.html:<td class='covered'><pre><span  class='survived'>                        || Character.isJavaIdentifierPart(text.charAt(position)))) {</span></pre></td></tr>"
  "TagParser.java.html:<td class='covered'><pre><span  class='survived'>                    &#38;&#38; (Character.isJavaIdentifierStart(text.charAt(position))</span></pre></td></tr>"
  "TagParser.java.html:<td class='covered'><pre><span  class='survived'>                .substring(0, toPoint.getColumnNo() + 1).endsWith(&#34;--&#62;&#34;)) {</span></pre></td></tr>"
  "TagParser.java.html:<td class='covered'><pre><span  class='survived'>            text = text.substring(column).trim();</span></pre></td></tr>"
  "TagParser.java.html:<td class='covered'><pre><span  class='survived'>            toPoint = findChar(text, &#39;&#62;&#39;, getNextPoint(text, toPoint));</span></pre></td></tr>"
  "TagParser.java.html:<td class='covered'><pre><span  class='survived'>        Point position = findChar(text, &#39;&#60;&#39;, new Point(0, 0));</span></pre></td></tr>"
  "TagParser.java.html:<td class='covered'><pre><span  class='survived'>        if (incompleteTag) {</span></pre></td></tr>"
  "TagParser.java.html:<td class='covered'><pre><span  class='survived'>        toPoint = findChar(text, &#39;&#62;&#39;, toPoint);</span></pre></td></tr>"
  "TagParser.java.html:<td class='covered'><pre><span  class='survived'>        while (toPoint.getLineNo() &#60; text.length &#38;&#38; !text[toPoint.getLineNo()]</span></pre></td></tr>"
  "WriteTagCheck.java.html:<td class='covered'><pre><span  class='survived'>        tagSeverity = severity;</span></pre></td></tr>"
  );
  checkPitestReport ignoredItems
  ;;

# pitesttyle-gui)
#   mvn -e -P$1 clean test-compile org.pitest:pitest-maven:mutationCoverage;
#   # post validation is skipped, we do not test gui throughly
#   ;;

--list)
  echo "Supported profiles:"
  pomXmlPath="$(dirname "${0}")/../pom.xml"
  sed -n -e 's/^.*<id>\(pitest-[^<]\+\)<\/id>.*$/\1/p' < "${pomXmlPath}" | sort
  ;;

*)
  if [[ -n "$1" ]]; then
    echo "Unexpected argument: $*"
  fi
  echo "Usage $0 <profile>"
  echo "To see the full list of supported profiles run '$0 --list'"
  sleep 5s
  false
  ;;

esac
