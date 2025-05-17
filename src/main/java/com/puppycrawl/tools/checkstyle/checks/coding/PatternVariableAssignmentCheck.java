///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.ArrayList;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class PatternVariableAssignmentCheck extends AbstractCheck {

  @Override
  public int[] getRequiredTokens() {
    return new int[]{TokenTypes.LITERAL_INSTANCEOF};
  }

  @Override
  public int[] getDefaultTokens() {
    return getRequiredTokens();
  }

  @Override
  public int[] getAcceptableTokens() {
    return getRequiredTokens();
  }

  @Override
  public void visitToken(DetailAST ast) {

    ArrayList<DetailAST> patternVariablesArray = getArrayOfPatternVariables(ast);

    if (!patternVariablesArray.isEmpty()) {

      DetailAST ifStatementSLIST = ast.getParent().getParent().findFirstToken(TokenTypes.SLIST);

      for (DetailAST expressionToken = ifStatementSLIST.findFirstToken(TokenTypes.EXPR);
           expressionToken != null; expressionToken = expressionToken.getNextSibling()) {

        if (expressionToken.getType() == TokenTypes.EXPR) {
          DetailAST assignToken = expressionToken.findFirstToken(TokenTypes.ASSIGN);

          if (assignToken != null) {
            String variableName = assignToken.findFirstToken(TokenTypes.IDENT).getText();

            for (DetailAST patternVariableAST : patternVariablesArray) {
              DetailAST patternVariableIdent = patternVariableAST.findFirstToken(TokenTypes.IDENT);

              if (variableName.equals(patternVariableIdent.getText())) {
                log(assignToken.getLineNo(), "Assignment of pattern variable is not allowed");
                break;
              }

            }

          }
        }
      }
    }

  }

  /**
    * Gets the list of all pattern variables in instanceof expression.
    *
    * @param ast ast tree of instanceoff to get the lsit from.
    * @return list of pattern variables.
    */
  private ArrayList<DetailAST> getArrayOfPatternVariables(DetailAST ast) {
    ArrayList<DetailAST> patternVariablesArray = new ArrayList<DetailAST>();

    DetailAST outermostPatternVariable = ast.findFirstToken(TokenTypes.PATTERN_VARIABLE_DEF);
    DetailAST recordPatternDef = (ast.getType() == TokenTypes.LITERAL_INSTANCEOF)
        ? (ast.findFirstToken(TokenTypes.RECORD_PATTERN_DEF)) : ast;

    if (outermostPatternVariable != null) {
      patternVariablesArray.add(outermostPatternVariable);
    }
    else if (recordPatternDef != null) {
      DetailAST recordPatternComponents = recordPatternDef
          .findFirstToken(TokenTypes.RECORD_PATTERN_COMPONENTS);

      for (DetailAST innerPatternVariable = recordPatternComponents.getFirstChild();
           innerPatternVariable != null;
           innerPatternVariable = innerPatternVariable.getNextSibling()) {

          if (innerPatternVariable.getType() == TokenTypes.PATTERN_VARIABLE_DEF) {
            patternVariablesArray.add(innerPatternVariable);
          }
          else if (innerPatternVariable.getType() == TokenTypes.RECORD_PATTERN_DEF) {
            patternVariablesArray.addAll(getArrayOfPatternVariables(innerPatternVariable));
          }

      }

    }

    return patternVariablesArray;
  }

}
