#!/bin/bash
# Attention, there is no "-x" to avoid problems on Wercker
set -e

###############################
function checkPitestReport() {
  ignored=("$@")
  fail=0
  SEARCH_REGEXP="(span  class='survived'|class='uncovered'><pre>)"
  actual=($(grep -irE "$SEARCH_REGEXP" target/pit-reports | sed -E 's/.*\/([A-Za-z]+.java.html)/\1/' | sort))
  A=${actual[@]};
  B=${ignored[@]};
  if [ "$(diff -q -u -w <( echo "$A" ) <( echo "$B" ))" != "" ] ; then
      fail=1
      echo "Diff:"
      diff -u -w <( echo "$A" ) <( echo "$B" ) | cat
      echo "Actual:" ;
      grep -irE "$SEARCH_REGEXP" target/pit-reports | sed -E 's/.*\/([A-Za-z]+.java.html)/\1/' | sort
      echo "Ignore:" ;
      printf '%s\n' "${ignored[@]}"
  fi;
  if [ "$fail" -ne "0" ]; then
    echo "Survived items found in reports, build will be failed"
  fi
  sleep 5s
  exit $fail
}
###############################

case $1 in

pitest-checkstyle-xpath|pitest-checkstyle-filters|pitest-checks-imports|pitest-checkstyle-api \
|pitest-checks-metrics|pitest-checks-regexp|pitest-checks-sizes|pitest-checks-misc \
|pitest-checks-design|pitest-checks-annotation|pitest-checks-header \
|pitest-checks-modifier|pitest-checks-naming|pitest-checkstyle-tree-walker|pitest-checkstyle-main \
|pitest-checks-whitespace|pitest-checkstyle-utils)
  mvn -e -P$1 clean test org.pitest:pitest-maven:mutationCoverage;
  declare -a ignoredItems=();
  checkPitestReport "${ignoredItems[@]}"
  ;;

pitest-checks-blocks)
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

pitest-checks-coding)
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

pitest-checkstyle-common)
  mvn -e -P$1 clean test org.pitest:pitest-maven:mutationCoverage;
  declare -a ignoredItems=(
  "DefaultConfiguration.java.html:<td class='uncovered'><pre><span  class=''>            attributeMap.put(attributeName, current + &#34;,&#34; + value);</span></pre></td></tr>"
  );
  checkPitestReport "${ignoredItems[@]}"
  ;;

pitest-checkstyle-gui|pitest-checks-indentation|pitest-checks-javadoc)
  mvn -e -P$1 clean test org.pitest:pitest-maven:mutationCoverage;
  # too much supprssions , post validation is skipped
  ;;

*)
  echo "Unexpected argument: $1"
  sleep 5s
  false
  ;;

esac



