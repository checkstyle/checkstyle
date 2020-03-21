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
|pitest-coding)
  mvn -e -P$1 clean test org.pitest:pitest-maven:mutationCoverage;
  declare -a ignoredItems=();
  checkPitestReport "${ignoredItems[@]}"
  ;;

pitest-regexp)
  mvn -e -P$1 clean test org.pitest:pitest-maven:mutationCoverage;
  declare -a ignoredItems=(
  "DetectorOptions.java.html:<td class='covered'><pre><span  class='survived'>        if (pattern == null) {</span></pre></td></tr>"
  "RegexpCheck.java.html:<td class='covered'><pre><span  class='survived'>        if (message == null) {</span></pre></td></tr>"
  "RegexpCheck.java.html:<td class='covered'><pre><span  class='survived'>        return errorCount &#60;= errorLimit - 1</span></pre></td></tr>"
  );
  checkPitestReport "${ignoredItems[@]}"
  ;;

pitest-header)
  mvn -e -P$1 clean test org.pitest:pitest-maven:mutationCoverage;
  declare -a ignoredItems=(
  "AbstractHeaderCheck.java.html:<td class='uncovered'><pre><span  class=''>            catch (final IOException ex) {</span></pre></td></tr>"
  "AbstractHeaderCheck.java.html:<td class='uncovered'><pre><span  class='survived'>                throw new IllegalArgumentException(&#34;unable to load header&#34;, ex);</span></pre></td></tr>"
  "HeaderCheck.java.html:<td class='covered'><pre><span  class='survived'>        if (list.length == 0) {</span></pre></td></tr>"
  "RegexpHeaderCheck.java.html:<td class='covered'><pre><span  class='survived'>        if (list.length == 0) {</span></pre></td></tr>"
  "RegexpHeaderCheck.java.html:<td class='covered'><pre><span  class='survived'>                    isMatch = headerLineNo == headerSize</span></pre></td></tr>"
  "RegexpHeaderCheck.java.html:<td class='covered'><pre><span  class='survived'>                            || isMatch(line, headerLineNo);</span></pre></td></tr>"
  );
  checkPitestReport "${ignoredItems[@]}"
  ;;

pitest-imports)
  mvn -e -P$1 clean test org.pitest:pitest-maven:mutationCoverage;
  declare -a ignoredItems=(
  "CustomImportOrderCheck.java.html:<td class='covered'><pre><span  class='survived'>        if (bestMatch.group.equals(NON_GROUP_RULE_GROUP)) {</span></pre></td></tr>"
  "IllegalImportCheck.java.html:<td class='covered'><pre><span  class='survived'>        if (!result &#38;&#38; illegalClasses != null) {</span></pre></td></tr>"
  "IllegalImportCheck.java.html:<td class='covered'><pre><span  class='survived'>        if (!result) {</span></pre></td></tr>"
  "ImportControlLoader.java.html:<td class='covered'><pre><span  class='survived'>        else if (ALLOW_ELEMENT_NAME.equals(qName) || &#34;disallow&#34;.equals(qName)) {</span></pre></td></tr>"
  "ImportOrderCheck.java.html:<td class='covered'><pre><span  class='survived'>                else if (matcher.start() == bestPos &#38;&#38; matcher.end() &#62; bestEnd) {</span></pre></td></tr>"
  "ImportOrderCheck.java.html:<td class='covered'><pre><span  class='survived'>        final boolean separatorBetween = isStatic != lastImportStatic</span></pre></td></tr>"
  "ImportOrderCheck.java.html:<td class='covered'><pre><span  class='survived'>        if (caseSensitive) {</span></pre></td></tr>"
  "ImportOrderCheck.java.html:<td class='covered'><pre><span  class='survived'>                if (!CommonUtil.endsWithChar(pkg, &#39;.&#39;)) {</span></pre></td></tr>"
  "ImportOrderCheck.java.html:<td class='covered'><pre><span  class='survived'>        if (isStatic) {</span></pre></td></tr>"
  "ImportOrderCheck.java.html:<td class='covered'><pre><span  class='survived'>        return !beforeFirstImport &#38;&#38; line - lastImportLine &#62; 1;</span></pre></td></tr>"
  "ImportOrderCheck.java.html:<td class='covered'><pre><span  class='survived'>            staticImportSeparator = isStatic &#38;&#38; separated;</span></pre></td></tr>"
  "PkgImportControl.java.html:<td class='covered'><pre><span  class='survived'>        if (alreadyRegex) {</span></pre></td></tr>"
  "PkgImportControl.java.html:<td class='covered'><pre><span  class='survived'>        if (regex || parent.regex) {</span></pre></td></tr>"
  "PkgImportControl.java.html:<td class='covered'><pre><span  class='survived'>        if (regex) {</span></pre></td></tr>"
  "PkgImportRule.java.html:<td class='covered'><pre><span  class='survived'>        if (isRegExp()) {</span></pre></td></tr>"
  "UnusedImportsCheck.java.html:<td class='covered'><pre><span  class='survived'>            if (collect) {</span></pre></td></tr>"
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
  "AbstractExpressionHandler.java.html:<td class='covered'><pre><span  class='survived'>            if (colNum == null || thisLineColumn &#60; colNum) {</span></pre></td></tr>"
  "AbstractExpressionHandler.java.html:<td class='covered'><pre><span  class='survived'>        if (currLine &#60; realStart) {</span></pre></td></tr>"
  "AbstractExpressionHandler.java.html:<td class='covered'><pre><span  class='survived'>            if (toTest.getColumnNo() &#60; first.getColumnNo()) {</span></pre></td></tr>"
  "ArrayInitHandler.java.html:<td class='covered'><pre><span  class='survived'>        if (firstChildPos &#62;= 0) {</span></pre></td></tr>"
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
  "IndentLevel.java.html:<td class='covered'><pre><span  class='survived'>            for (int i = levels.nextSetBit(0); i &#62;= 0;</span></pre></td></tr>"
  "MethodDefHandler.java.html:<td class='covered'><pre><span  class='survived'>            if (node.getLineNo() &#60; lineStart) {</span></pre></td></tr>"
  "MethodDefHandler.java.html:<td class='covered'><pre><span  class='survived'>            if (node.getType() == TokenTypes.ANNOTATION) {</span></pre></td></tr>"
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
  "AbstractJavadocCheck.java.html:<td class='covered'><pre><span  class='survived'>            Arrays.sort(acceptableJavadocTokens);</span></pre></td></tr>"
  "AbstractJavadocCheck.java.html:<td class='covered'><pre><span  class='survived'>            Arrays.sort(defaultJavadocTokens);</span></pre></td></tr>"
  "AbstractJavadocCheck.java.html:<td class='covered'><pre><span  class='survived'>        beginJavadocTree(root);</span></pre></td></tr>"
  "AbstractJavadocCheck.java.html:<td class='covered'><pre><span  class='survived'>        finishJavadocTree(root);</span></pre></td></tr>"
  "AbstractJavadocCheck.java.html:<td class='covered'><pre><span  class='survived'>        javadocTokens.clear();</span></pre></td></tr>"
  "JavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>        if (!currentClassName.isEmpty()) {</span></pre></td></tr>"
  "JavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>        if (classInfo == null) {</span></pre></td></tr>"
  "JavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>                 child != null;</span></pre></td></tr>"
  "JavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>                if (child.getType() == TokenTypes.TYPE_PARAMETER) {</span></pre></td></tr>"
  "JavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>            if (classInfo != null) {</span></pre></td></tr>"
  "JavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>            if (dotIdx == -1) {</span></pre></td></tr>"
  "JavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>        currentTypeParams.clear();</span></pre></td></tr>"
  "JavadocMethodCheck.java.html:<td class='covered'><pre><span  class='survived'>        while (iterator.hasNext()) {</span></pre></td></tr>"
  "JavadocTagInfo.java.html:<td class='covered'><pre><span  class='survived'>            .collect(Collectors.toMap(JavadocTagInfo::getName, tagName -&#62; tagName)));</span></pre></td></tr>"
  "JavadocTagInfo.java.html:<td class='covered'><pre><span  class='survived'>            .collect(Collectors.toMap(JavadocTagInfo::getText, tagText -&#62; tagText)));</span></pre></td></tr>"
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



