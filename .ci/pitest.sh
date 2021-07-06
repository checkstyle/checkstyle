#!/bin/bash
# Attention, there is no "-x" to avoid problems on Wercker
set -e

###############################
function checkPitestReport() {
  ignored=("$@")
  fail=0
  SEARCH_REGEXP="(span  class='survived'|class='uncovered'><pre>)"
  grep -irE "$SEARCH_REGEXP" target/pit-reports \
     | sed -E 's/.*\/([A-Za-z]+.java.html)/\1/' | LC_ALL=C sort > target/actual.txt
  printf "%s\n" "${ignored[@]}" | sed '/^$/d' | LC_ALL=C sort > target/ignored.txt
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
|pitest-main \
|pitest-coding \
|pitest-regexp \
|pitest-meta)
  mvn -e -P$1 clean test org.pitest:pitest-maven:mutationCoverage;
  declare -a ignoredItems=();
  checkPitestReport "${ignoredItems[@]}"
  ;;

pitest-header)
  mvn -e -P$1 clean test org.pitest:pitest-maven:mutationCoverage;
  declare -a ignoredItems=(
  "AbstractHeaderCheck.java.html:<td class='uncovered'><pre><span  class=''>            catch (final IOException ex) {</span></pre></td></tr>"
  "AbstractHeaderCheck.java.html:<td class='uncovered'><pre><span  class='survived'>                throw new IllegalArgumentException(&#34;unable to load header&#34;, ex);</span></pre></td></tr>"
  "RegexpHeaderCheck.java.html:<td class='covered'><pre><span  class='survived'>                    isMatch = headerLineNo == headerSize</span></pre></td></tr>"
  "RegexpHeaderCheck.java.html:<td class='covered'><pre><span  class='survived'>                            || isMatch(line, headerLineNo);</span></pre></td></tr>"
  );
  checkPitestReport "${ignoredItems[@]}"
  ;;

pitest-imports)
  mvn -e -P$1 clean test org.pitest:pitest-maven:mutationCoverage;
  declare -a ignoredItems=(
  "ImportControlLoader.java.html:<td class='covered'><pre><span  class='survived'>        else if (ALLOW_ELEMENT_NAME.equals(qName) || &#34;disallow&#34;.equals(qName)) {</span></pre></td></tr>"
  "PkgImportControl.java.html:<td class='covered'><pre><span  class='survived'>        if (regex || parent.regex) {</span></pre></td></tr>"
  "PkgImportControl.java.html:<td class='covered'><pre><span  class='survived'>        if (regex) {</span></pre></td></tr>"
  );
  checkPitestReport "${ignoredItems[@]}"
  ;;

pitest-common)
  mvn -e -P$1 clean test org.pitest:pitest-maven:mutationCoverage;
  declare -a ignoredItems=(
  "AuditEventDefaultFormatter.java.html:<td class='covered'><pre><span  class='survived'>        if (lastDotIndex == -1) {</span></pre></td></tr>"
  "AuditEventDefaultFormatter.java.html:<td class='uncovered'><pre><span  class=''>                checkShortName = checkFullName.substring(0, checkFullName.lastIndexOf(SUFFIX));</span></pre></td></tr>"
  "AuditEventDefaultFormatter.java.html:<td class='uncovered'><pre><span  class=''>                checkShortName = checkFullName;</span></pre></td></tr>"
  "AuditEventDefaultFormatter.java.html:<td class='uncovered'><pre><span  class='survived'>            if (checkFullName.endsWith(SUFFIX)) {</span></pre></td></tr>"
  "Checker.java.html:<td class='covered'><pre><span  class='survived'>                if (cacheFile != null &#38;&#38; cacheFile.isInCache(fileName, timestamp)</span></pre></td></tr>"
  "DefaultLogger.java.html:<td class='covered'><pre><span  class='survived'>        closeError = errorStreamOptions == OutputStreamOptions.CLOSE;</span></pre></td></tr>"
  "DefaultLogger.java.html:<td class='covered'><pre><span  class='survived'>        closeInfo = infoStreamOptions == OutputStreamOptions.CLOSE;</span></pre></td></tr>"
  "DefaultLogger.java.html:<td class='covered'><pre><span  class='survived'>        if (closeError) {</span></pre></td></tr>"
  "DefaultLogger.java.html:<td class='covered'><pre><span  class='survived'>        if (closeInfo) {</span></pre></td></tr>"
  "DefaultLogger.java.html:<td class='covered'><pre><span  class='survived'>        if (severityLevel != SeverityLevel.IGNORE) {</span></pre></td></tr>"
  "ConfigurationLoader.java.html:<td class='uncovered'><pre><span  class=''>                    catch (final CheckstyleException ex) {</span></pre></td></tr>"
  "ConfigurationLoader.java.html:<td class='uncovered'><pre><span  class='survived'>                                        + recentModule.getName(), ex);</span></pre></td></tr>"
  "ConfigurationLoader.java.html:<td class='uncovered'><pre><span  class='survived'>                        throw new SAXException(</span></pre></td></tr>"
  "PackageObjectFactory.java.html:<td class='covered'><pre><span  class='survived'>        if (instance == null</span></pre></td></tr>"
  "PackageObjectFactory.java.html:<td class='covered'><pre><span  class='survived'>        if (!name.contains(PACKAGE_SEPARATOR)) {</span></pre></td></tr>"
  "PackageObjectFactory.java.html:<td class='covered'><pre><span  class='survived'>                if (thirdPartyNameToFullModuleNames == null) {</span></pre></td></tr>"
  "PackageObjectFactory.java.html:<td class='uncovered'><pre><span  class=''>            returnValue = Collections.emptyMap();</span></pre></td></tr>"
  "PackageObjectFactory.java.html:<td class='uncovered'><pre><span  class=''>        catch (IOException ignore) {</span></pre></td></tr>"
  "PropertyCacheFile.java.html:<td class='covered'><pre><span  class='survived'>                if (!cachedHashSum.equals(contentHashSum)) {</span></pre></td></tr>"
  "PropertyCacheFile.java.html:<td class='uncovered'><pre><span  class=''>                    changed = true;</span></pre></td></tr>"
  "PropertyCacheFile.java.html:<td class='uncovered'><pre><span  class=''>        catch (final IOException | NoSuchAlgorithmException ex) {</span></pre></td></tr>"
  "PropertyCacheFile.java.html:<td class='uncovered'><pre><span  class='survived'>            throw new IllegalStateException(&#34;Unable to calculate hashcode.&#34;, ex);</span></pre></td></tr>"
  );
  checkPitestReport "${ignoredItems[@]}"
  ;;


pitest-ant)
  mvn -e -P$1 clean test org.pitest:pitest-maven:mutationCoverage;
  declare -a ignoredItems=(
  "CheckstyleAntTask.java.html:<td class='covered'><pre><span  class='survived'>            if (toFile == null || !useFile) {</span></pre></td></tr>"
  "CheckstyleAntTask.java.html:<td class='covered'><pre><span  class='survived'>            if (toFile == null || !useFile) {</span></pre></td></tr>"
  "CheckstyleAntTask.java.html:<td class='covered'><pre><span  class='survived'>            log(&#34;Adding standalone file for audit&#34;, Project.MSG_VERBOSE);</span></pre></td></tr>"
  "CheckstyleAntTask.java.html:<td class='covered'><pre><span  class='survived'>            log(&#34;To process the files took &#34; + (processingEndTime - processingStartTime)</span></pre></td></tr>"
  "CheckstyleAntTask.java.html:<td class='covered'><pre><span  class='survived'>            log(&#34;Total execution took &#34; + (endTime - startTime) + TIME_SUFFIX,</span></pre></td></tr>"
  "CheckstyleAntTask.java.html:<td class='covered'><pre><span  class='survived'>        log(&#34;To locate the files took &#34; + (endTime - startTime) + TIME_SUFFIX,</span></pre></td></tr>"
  "CheckstyleAntTask.java.html:<td class='uncovered'><pre><span  class=''>        catch (CheckstyleException ex) {</span></pre></td></tr>"
  "CheckstyleAntTask.java.html:<td class='uncovered'><pre><span  class='survived'>            throw new BuildException(&#34;Unable to process files: &#34; + files, ex);</span></pre></td></tr>"
  );
  checkPitestReport "${ignoredItems[@]}"
  ;;

pitest-indentation)
  mvn -e -P$1 clean test org.pitest:pitest-maven:mutationCoverage;
  declare -a ignoredItems=(
  "AbstractExpressionHandler.java.html:<td class='covered'><pre><span  class='survived'>            &#38;&#38; tree.getColumnNo() &#60; realStart.getColumnNo()</span></pre></td></tr>"
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
  checkPitestReport "${ignoredItems[@]}"
  ;;

pitest-javadoc)
  mvn -e -P$1 clean test org.pitest:pitest-maven:mutationCoverage;
  declare -a ignoredItems=(
  "AbstractJavadocCheck.java.html:<td class='covered'><pre><span  class='survived'>        beginJavadocTree(root);</span></pre></td></tr>"
  "AbstractJavadocCheck.java.html:<td class='covered'><pre><span  class='survived'>        finishJavadocTree(root);</span></pre></td></tr>"
  "AbstractJavadocCheck.java.html:<td class='covered'><pre><span  class='survived'>        javadocTokens.clear();</span></pre></td></tr>"
  "TagParser.java.html:<td class='covered'><pre><span  class='survived'>                while (column &#60; currentLine.length()</span></pre></td></tr>"
  );
  checkPitestReport "${ignoredItems[@]}"
  ;;

pitest-tree-walker)
  mvn -e -P$1 clean test org.pitest:pitest-maven:mutationCoverage;
  declare -a ignoredItems=(
  "TreeWalker.java.html:<td class='covered'><pre><span  class='survived'>            if (!commentChecks.isEmpty()) {</span></pre></td></tr>"
  "TreeWalker.java.html:<td class='covered'><pre><span  class='survived'>            if (!ordinaryChecks.isEmpty()) {</span></pre></td></tr>"
  "TreeWalker.java.html:<td class='covered'><pre><span  class='survived'>            if (filters.isEmpty()) {</span></pre></td></tr>"
  );
  checkPitestReport "${ignoredItems[@]}"
  ;;

pitest-utils)
  mvn -e -P$1 clean test org.pitest:pitest-maven:mutationCoverage;
  declare -a ignoredItems=(
  "CommonUtil.java.html:<td class='uncovered'><pre><span  class=''>                catch (final URISyntaxException ex) {</span></pre></td></tr>"
  "CommonUtil.java.html:<td class='uncovered'><pre><span  class='survived'>                    throw new CheckstyleException(UNABLE_TO_FIND_EXCEPTION_PREFIX + filename, ex);</span></pre></td></tr>"
  );
  checkPitestReport "${ignoredItems[@]}"
  ;;

pitest-JavaAstVisitor)
  mvn -e -P$1 clean test org.pitest:pitest-maven:mutationCoverage;
  declare -a ignoredItems=(
"JavaAstVisitor.java.html:<td class='covered'><pre><span  class='survived'>                    .forEach(semi -&#62; addLastSibling(typeDeclaration, create(semi)));</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='covered'><pre><span  class='survived'>                .filter(child -&#62; !(child instanceof JavaParser.ArrayDeclaratorContext))</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='covered'><pre><span  class='survived'>                if (child instanceof Token) {</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='covered'><pre><span  class='survived'>                || expStatRoot.getType() == TokenTypes.SUPER_CTOR_CALL) {</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='covered'><pre><span  class='survived'>            DetailAstPair.addASTChild(currentAST,  visit(ctx.annotations()));</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='covered'><pre><span  class='survived'>            annotations.setColumnNo(ctx.anno.get(0).start.getCharPositionInLine());</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='covered'><pre><span  class='survived'>            annotations.setColumnNo(ctx.getParent().start.getCharPositionInLine());</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='covered'><pre><span  class='survived'>            annotations.setLineNo(ctx.anno.get(0).start.getLine());</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='covered'><pre><span  class='survived'>            annotations.setLineNo(ctx.getParent().start.getLine());</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='covered'><pre><span  class='survived'>            annotations.setLineNo(qualifiedName.getLineNo());</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='covered'><pre><span  class='survived'>            ctx.cStyleArrDec.forEach(child -&#62; typeAst.addChild(visit(child)));</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='covered'><pre><span  class='survived'>            if (ctx.children.get(i + 1) != null) {</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='covered'><pre><span  class='survived'>        copy.setColumnNo(ast.getColumnNo());</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='covered'><pre><span  class='survived'>        copy.setLineNo(ast.getLineNo());</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='covered'><pre><span  class='survived'>        enumDef.setLineNo(enumDef.getFirstChild().getLineNo());</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='covered'><pre><span  class='survived'>        for (int i = 0; i &#60; ctx.COMMA().size(); i++) {</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='covered'><pre><span  class='survived'>        for (int i = 1; ctx.children != null &#38;&#38; i &#60; ctx.children.size() - 1; i += 2) {</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='covered'><pre><span  class='survived'>        if (!TokenUtil.isOfType(root, TokenTypes.CTOR_CALL, TokenTypes.SUPER_CTOR_CALL)) {</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='covered'><pre><span  class='survived'>        if (binOpList.isEmpty()) {</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='covered'><pre><span  class='survived'>        if (children != null) {</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='covered'><pre><span  class='survived'>        if (ctx.SEMI() == null) {</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='covered'><pre><span  class='survived'>        if (ctx.annotations() != null) {</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='covered'><pre><span  class='survived'>        if (ctx.annotations() != null) {</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='covered'><pre><span  class='survived'>        if (ctx.arrayDeclarator() != null) {</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='covered'><pre><span  class='survived'>        if (ctx.cStyleArrDec != null) {</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='covered'><pre><span  class='survived'>        if (ctx.cStyleArrDec != null) {</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='covered'><pre><span  class='survived'>        if (ctx.typeArgumentsOrDiamond() != null) {</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='covered'><pre><span  class='survived'>        if (expStatRoot.getType() == TokenTypes.CTOR_CALL</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='covered'><pre><span  class='survived'>        if (startToken != null) {</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='covered'><pre><span  class='survived'>        interfaceDef.setLineNo(interfaceDef.getFirstChild().getLineNo());</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='covered'><pre><span  class='survived'>        root.addChild(visit(ctx.typeArguments()));</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='covered'><pre><span  class='survived'>        type.setLineNo(type.getFirstChild().getLineNo());</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='covered'><pre><span  class='survived'>        while (firstExpression instanceof JavaParser.BinOpContext) {</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class=''>                    (Token) ctx.LPAREN().getPayload());</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class=''>                    .orElse(createImaginary(TokenTypes.ELIST, ctx.RPAREN()));</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class=''>                    ast =  visit((ParseTree) child);</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class=''>                (Token) ctx.DOUBLE_COLON().getPayload());</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class=''>                (Token) ctx.GT().getPayload()));</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class=''>                (Token) ctx.LITERAL_super().getPayload());</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class=''>                (Token) ctx.LITERAL_super().getPayload());</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class=''>                (Token) ctx.LITERAL_this().getPayload());</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class=''>                (Token) ctx.LT().getPayload()));</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class=''>                .collect(Collectors.toList());</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class=''>                .orElse(createImaginary(TokenTypes.ELIST, ctx.RPAREN()));</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class=''>                .orElse(createImaginary(TokenTypes.ELIST, ctx.RPAREN()));</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class=''>                createImaginary(TokenTypes.TYPE_ARGUMENTS, ctx.LT());</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class=''>            final DetailAstImpl dot = create(ctx.DOT());</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class=''>            final DetailAstImpl expressionList = Optional.ofNullable(visit(ctx.expressionList()))</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class=''>            final DetailAstImpl ident =  visit(ctx.typeType(0));</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class=''>            final DetailAstImpl typeArgument = copy(TokenTypes.TYPE_ARGUMENT, ident);</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class=''>            returnTree = create(ctx.SEMI());</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class=''>            root = create(TokenTypes.METHOD_CALL,</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class=''>            root = create(ctx.DOT());</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class=''>            superSuffixParent.getFirstChild()</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class=''>        DetailAstImpl expressionList = Optional.ofNullable(visit(ctx.expressionList()))</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class=''>        DetailAstImpl expressionList = Optional.ofNullable(visit(ctx.expressionList()))</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class=''>        final DetailAstImpl bop = create(ctx.bop);</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class=''>        final DetailAstImpl ctorCall = create(TokenTypes.CTOR_CALL,</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class=''>        final DetailAstImpl doubleColon = create(TokenTypes.METHOD_REF,</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class=''>        final DetailAstImpl root = create(TokenTypes.SUPER_CTOR_CALL,</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class=''>        final DetailAstImpl superCall = create(TokenTypes.SUPER_CTOR_CALL,</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class=''>        final DetailAstImpl superSuffixParent =  visit(ctx.superSuffix());</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class=''>        final DetailAstImpl typeArguments =</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class=''>        final List&#60;ParseTree&#62; children = ctx.children.stream()</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>                    .getFirstChild().addPreviousSibling(bop);</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>                .filter(child -&#62; !child.equals(ctx.DOUBLE_COLON()))</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>                else if (child instanceof RuleContext) {</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>            &#38;&#38; superSuffixParent.getFirstChild().getFirstChild() != null) {</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>            addLastSibling(firstTypeArgument, create(ctx.COMMA(i)));</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>            addLastSibling(firstTypeArgument, typeArgument);</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>            dot.addChild(create(ctx.IDENT()));</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>            expStatRoot.addChild(create(ctx.SEMI()));</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>            root.addChild(create(ctx.IDENT()));</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>            root.addChild(create(ctx.RPAREN()));</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>            root.addChild(dot);</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>            root.addChild(expressionList);</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>            typeArgument.addChild(ident);</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>        bop.addChild(create(ctx.LITERAL_super()));</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>        bop.addChild(visit(ctx.expr()));</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>        ctorCall.addChild(create((Token) ctx.LPAREN().getPayload()));</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>        ctorCall.addChild(create((Token) ctx.RPAREN().getPayload()));</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>        ctorCall.addChild(expressionList);</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>        if (ctx.DOT() != null) {</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>        if (ctx.LPAREN() != null) {</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>        if (superSuffixParent.getFirstChild() != null</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>        processChildren(doubleColon, children);</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>        return ctorCall;</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>        return doubleColon;</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>        return flattenedTree(ctx);</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>        return flattenedTree(ctx);</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>        return flattenedTree(ctx);</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>        return root;</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>        return root;</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>        return superCall;</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>        return superSuffixParent;</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>        return typeArguments;</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>        root.addChild(create(ctx.DOT()));</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>        root.addChild(create(ctx.SEMI()));</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>        root.addChild(visit(ctx.arguments()));</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>        root.addChild(visit(ctx.expr()));</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>        root.addChild(visit(ctx.typeArguments()));</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>        superCall.addChild(create(ctx.LPAREN()));</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>        superCall.addChild(create(ctx.RPAREN()));</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>        superCall.addChild(expressionList);</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>        typeArguments.addChild(create(TokenTypes.GENERIC_START,</span></pre></td></tr>"
"JavaAstVisitor.java.html:<td class='uncovered'><pre><span  class='survived'>        typeArguments.addChild(create(TokenTypes.GENERIC_START,</span></pre></td></tr>"
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


