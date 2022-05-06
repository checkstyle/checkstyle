#!/bin/bash
# Attention, there is no "-x" to avoid problems on Wercker
set -e

###############################
function checkPitestReport() {
  local -n ignored=$1
  fail=0
  SEARCH_REGEXP="(span  class='survived'|class='uncovered'><pre>)"
  grep -irE "$SEARCH_REGEXP" target/pit-reports \
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
      grep -irE "$SEARCH_REGEXP" target/pit-reports \
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
  mvn --no-transfer-progress -e -P$1 clean test-compile org.pitest:pitest-maven:mutationCoverage;
  declare -a ignoredItems=();
  checkPitestReport ignoredItems
  ;;

# till #9351
pitest-main)
  mvn --no-transfer-progress -e -P$1 clean test-compile org.pitest:pitest-maven:mutationCoverage;
  declare -a ignoredItems=(
  "Main.java.html:<td class='uncovered'><pre><span  class=''>        }</span></pre></td></tr>"
  );
  checkPitestReport ignoredItems
  ;;

pitest-header)
  mvn --no-transfer-progress -e -P$1 clean test-compile org.pitest:pitest-maven:mutationCoverage;
  declare -a ignoredItems=(
  "RegexpHeaderCheck.java.html:<td class='covered'><pre><span  class='survived'>                    isMatch = headerLineNo == headerSize</span></pre></td></tr>"
  "RegexpHeaderCheck.java.html:<td class='covered'><pre><span  class='survived'>                            || isMatch(line, headerLineNo);</span></pre></td></tr>"
  );
  checkPitestReport ignoredItems
  ;;

pitest-imports)
  mvn --no-transfer-progress -e -P$1 clean test-compile org.pitest:pitest-maven:mutationCoverage;
  declare -a ignoredItems=(
  "ImportControlLoader.java.html:<td class='covered'><pre><span  class='survived'>        else if (ALLOW_ELEMENT_NAME.equals(qName) || &#34;disallow&#34;.equals(qName)) {</span></pre></td></tr>"
  "PkgImportControl.java.html:<td class='covered'><pre><span  class='survived'>        if (regex || parent.regex) {</span></pre></td></tr>"
  "PkgImportControl.java.html:<td class='covered'><pre><span  class='survived'>        if (regex) {</span></pre></td></tr>"
  );
  checkPitestReport ignoredItems
  ;;

pitest-common)
  mvn --no-transfer-progress -e -P$1 clean test-compile org.pitest:pitest-maven:mutationCoverage;
  declare -a ignoredItems=(
  "Checker.java.html:<td class='covered'><pre><span  class='survived'>                if (cacheFile != null &#38;&#38; cacheFile.isInCache(fileName, timestamp)</span></pre></td></tr>"
  "DefaultLogger.java.html:<td class='covered'><pre><span  class='survived'>        closeError = errorStreamOptions == OutputStreamOptions.CLOSE;</span></pre></td></tr>"
  "DefaultLogger.java.html:<td class='covered'><pre><span  class='survived'>        closeInfo = infoStreamOptions == OutputStreamOptions.CLOSE;</span></pre></td></tr>"
  "DefaultLogger.java.html:<td class='covered'><pre><span  class='survived'>        if (closeError) {</span></pre></td></tr>"
  "DefaultLogger.java.html:<td class='covered'><pre><span  class='survived'>        if (closeInfo) {</span></pre></td></tr>"
  "DefaultLogger.java.html:<td class='covered'><pre><span  class='survived'>        if (severityLevel != SeverityLevel.IGNORE) {</span></pre></td></tr>"
  "PackageObjectFactory.java.html:<td class='covered'><pre><span  class='survived'>        if (instance == null</span></pre></td></tr>"
  "PackageObjectFactory.java.html:<td class='covered'><pre><span  class='survived'>        if (!name.contains(PACKAGE_SEPARATOR)) {</span></pre></td></tr>"
  "PackageObjectFactory.java.html:<td class='covered'><pre><span  class='survived'>                if (thirdPartyNameToFullModuleNames == null) {</span></pre></td></tr>"
  );
  checkPitestReport ignoredItems
  ;;


pitest-ant)
  mvn --no-transfer-progress -e -P$1 clean test-compile org.pitest:pitest-maven:mutationCoverage;
  declare -a ignoredItems=(
  "CheckstyleAntTask.java.html:<td class='covered'><pre><span  class='survived'>            if (toFile == null || !useFile) {</span></pre></td></tr>"
  "CheckstyleAntTask.java.html:<td class='covered'><pre><span  class='survived'>            if (toFile == null || !useFile) {</span></pre></td></tr>"
  "CheckstyleAntTask.java.html:<td class='covered'><pre><span  class='survived'>            log(&#34;To process the files took &#34; + (processingEndTime - processingStartTime)</span></pre></td></tr>"
  "CheckstyleAntTask.java.html:<td class='covered'><pre><span  class='survived'>            log(&#34;Total execution took &#34; + (endTime - startTime) + TIME_SUFFIX,</span></pre></td></tr>"
  "CheckstyleAntTask.java.html:<td class='covered'><pre><span  class='survived'>        log(&#34;To locate the files took &#34; + (endTime - startTime) + TIME_SUFFIX,</span></pre></td></tr>"
  );
  checkPitestReport ignoredItems
  ;;

pitest-indentation)
  mvn --no-transfer-progress -e -P$1 clean test-compile org.pitest:pitest-maven:mutationCoverage;
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

pitest-javadoc)
  mvn --no-transfer-progress -e -P$1 clean test-compile org.pitest:pitest-maven:mutationCoverage;
  declare -a ignoredItems=(
  "AbstractJavadocCheck.java.html:<td class='covered'><pre><span  class='survived'>        beginJavadocTree(root);</span></pre></td></tr>"
  "AbstractJavadocCheck.java.html:<td class='covered'><pre><span  class='survived'>        finishJavadocTree(root);</span></pre></td></tr>"
  "TagParser.java.html:<td class='covered'><pre><span  class='survived'>                while (column &#60; currentLineLength</span></pre></td></tr>"
  );
  checkPitestReport ignoredItems
  ;;

pitest-coding)
  mvn --no-transfer-progress -e -P$1 clean test-compile org.pitest:pitest-maven:mutationCoverage;
  declare -a ignoredItems=(
  "FinalLocalVariableCheck.java.html:<td class='covered'><pre><span  class='survived'>                            &#38;&#38; isSameVariables(storedVariable, variable)</span></pre></td></tr>"
  "FinalLocalVariableCheck.java.html:<td class='covered'><pre><span  class='survived'>                        == ast.getParent()) {</span></pre></td></tr>"
  "FinalLocalVariableCheck.java.html:<td class='covered'><pre><span  class='survived'>                if (ast.getParent().getType() == TokenTypes.SWITCH_RULE</span></pre></td></tr>"
  "FinalLocalVariableCheck.java.html:<td class='covered'><pre><span  class='survived'>            if (astIterator.getType() == childType</span></pre></td></tr>"
  "FinalLocalVariableCheck.java.html:<td class='covered'><pre><span  class='survived'>            if (storedVariable != null &#38;&#38; isSameVariables(storedVariable, ast)) {</span></pre></td></tr>"
  "FinalLocalVariableCheck.java.html:<td class='covered'><pre><span  class='survived'>            result = findLastChildWhichContainsSpecifiedToken(</span></pre></td></tr>"
  "FinalLocalVariableCheck.java.html:<td class='covered'><pre><span  class='survived'>        return ast.getType() == TokenTypes.LITERAL_IF</span></pre></td></tr>"
  "FinalLocalVariableCheck.java.html:<td class='covered'><pre><span  class='survived'>        return loop1 != null &#38;&#38; loop1 == loop2;</span></pre></td></tr>"
  "HiddenFieldCheck.java.html:<td class='covered'><pre><span  class='survived'>                &#38;&#38; ast.getType() == TokenTypes.PARAMETER_DEF) {</span></pre></td></tr>"
  "HiddenFieldCheck.java.html:<td class='covered'><pre><span  class='survived'>                &#38;&#38; firstChild.getType() == TokenTypes.IDENT) {</span></pre></td></tr>"
  "HiddenFieldCheck.java.html:<td class='covered'><pre><span  class='survived'>                &#38;&#38; methodAST.getType() == TokenTypes.METHOD_DEF</span></pre></td></tr>"
  "HiddenFieldCheck.java.html:<td class='covered'><pre><span  class='survived'>            if (method.getType() == TokenTypes.METHOD_DEF) {</span></pre></td></tr>"
  "HiddenFieldCheck.java.html:<td class='covered'><pre><span  class='survived'>            return instanceFields.contains(field)</span></pre></td></tr>"
  "HiddenFieldCheck.java.html:<td class='covered'><pre><span  class='survived'>        if (ignoreSetter &#38;&#38; ast.getType() == TokenTypes.PARAMETER_DEF) {</span></pre></td></tr>"
  "HiddenFieldCheck.java.html:<td class='covered'><pre><span  class='survived'>        if (type == TokenTypes.CLASS_DEF</span></pre></td></tr>"
  "IllegalInstantiationCheck.java.html:<td class='covered'><pre><span  class='survived'>            &#38;&#38; illegal.startsWith(JAVA_LANG)) {</span></pre></td></tr>"
  "OneStatementPerLineCheck.java.html:<td class='covered'><pre><span  class='survived'>                        &#38;&#38; currentStatement.getPreviousSibling().getType() == TokenTypes.RESOURCES;</span></pre></td></tr>"
  "OneStatementPerLineCheck.java.html:<td class='covered'><pre><span  class='survived'>                currentStatement.getPreviousSibling() != null</span></pre></td></tr>"
  "OneStatementPerLineCheck.java.html:<td class='covered'><pre><span  class='survived'>                if (countOfSemiInLambda.isEmpty()) {</span></pre></td></tr>"
  "OneStatementPerLineCheck.java.html:<td class='covered'><pre><span  class='survived'>            multiline = !TokenUtil.areOnSameLine(prevSibling, ast)</span></pre></td></tr>"
  "OneStatementPerLineCheck.java.html:<td class='covered'><pre><span  class='survived'>        if (!hasResourcesPrevSibling &#38;&#38; isMultilineStatement(currentStatement)) {</span></pre></td></tr>"
  "RequireThisCheck.java.html:<td class='covered'><pre><span  class='survived'>                        &#38;&#38; ast.getParent().getType() != TokenTypes.LITERAL_CATCH) {</span></pre></td></tr>"
  "RequireThisCheck.java.html:<td class='covered'><pre><span  class='survived'>                if (isAnonymousClassDef(ast)) {</span></pre></td></tr>"
  "RequireThisCheck.java.html:<td class='covered'><pre><span  class='survived'>                if (toVisit == null) {</span></pre></td></tr>"
  "RequireThisCheck.java.html:<td class='covered'><pre><span  class='survived'>               &#38;&#38; parent.getType() != TokenTypes.CTOR_DEF</span></pre></td></tr>"
  "RequireThisCheck.java.html:<td class='covered'><pre><span  class='survived'>            &#38;&#38; lastChild.getType() == TokenTypes.OBJBLOCK;</span></pre></td></tr>"
  "RequireThisCheck.java.html:<td class='covered'><pre><span  class='survived'>            if (definitionToken.getType() == TokenTypes.STATIC_INIT) {</span></pre></td></tr>"
  "RequireThisCheck.java.html:<td class='covered'><pre><span  class='survived'>            if (elistToken != null &#38;&#38; ident.getText().equals(ast.getText())) {</span></pre></td></tr>"
  "RequireThisCheck.java.html:<td class='covered'><pre><span  class='survived'>            if (lookForMethod &#38;&#38; containsMethod(nameToFind)</span></pre></td></tr>"
  "RequireThisCheck.java.html:<td class='covered'><pre><span  class='survived'>            if (variableDeclarationFrame.getType() == FrameType.CLASS_FRAME) {</span></pre></td></tr>"
  "RequireThisCheck.java.html:<td class='covered'><pre><span  class='survived'>        if (fieldUsageFrame.getType() == FrameType.BLOCK_FRAME) {</span></pre></td></tr>"
  "RequireThisCheck.java.html:<td class='covered'><pre><span  class='survived'>        if (sibling != null &#38;&#38; isAssignToken(parent.getType())) {</span></pre></td></tr>"
  "RequireThisCheck.java.html:<td class='covered'><pre><span  class='survived'>        return left.getType() == right.getType() &#38;&#38; left.getText().equals(right.getText());</span></pre></td></tr>"
  "UnnecessaryParenthesesCheck.java.html:<td class='covered'><pre><span  class='survived'>            || parent.getType() != TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR) {</span></pre></td></tr>"
  "UnnecessaryParenthesesCheck.java.html:<td class='covered'><pre><span  class='survived'>        else if (type != TokenTypes.ASSIGN</span></pre></td></tr>"
  "UnnecessaryParenthesesCheck.java.html:<td class='covered'><pre><span  class='survived'>        if (firstChild != null &#38;&#38; firstChild.getType() == TokenTypes.LPAREN) {</span></pre></td></tr>"
  "UnnecessaryParenthesesCheck.java.html:<td class='covered'><pre><span  class='survived'>        if (type != TokenTypes.ASSIGN</span></pre></td></tr>"
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
  # Until https://github.com/checkstyle/checkstyle/issues/11427
  declare -a unstableItems=(
  "RequireThisCheck.java.html:<td class='covered'><pre><span  class='survived'>            if (returnedVariable) {</span></pre></td></tr>"
  );
  checkPitestReport ignoredItems unstableItems
  ;;

# pitesttyle-gui)
#   mvn -e -P$1 clean test-compile org.pitest:pitest-maven:mutationCoverage;
#   # post validation is skipped, we do not test gui throughly
#   ;;

--list)
  echo "Supported profiles:"
  pomXmlPath="$(dirname "${0}")/../pom.xml"
  sed -n -e 's/<id>\(pitest-\w\+\)<\/id>/\1/p' < "${pomXmlPath}" | sort
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


