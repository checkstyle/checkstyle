package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.Scope;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.ScopeUtils;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class JavadocTypeCheck
    extends Check
{
    private Scope mScope = Scope.PRIVATE;

    public void setScope(String aFrom)
    {
        mScope = Scope.getInstance(aFrom);
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.INTERFACE_DEF, TokenTypes.CLASS_DEF};
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST)
    {
        final DetailAST mods = aAST.findFirstToken(TokenTypes.MODIFIERS);
        final Scope declaredScope = ScopeUtils.getScopeFromMods(mods);
        final Scope typeScope =
            ScopeUtils.inInterfaceBlock(aAST) ? Scope.PUBLIC : declaredScope;
        if (typeScope.isIn(mScope)) {
            final Scope surroundingScope = ScopeUtils.getSurroundingScope(aAST);
            if ((surroundingScope == null) || surroundingScope.isIn(mScope)) {
                final FileContents contents = getFileContents();
                final String[] cmt =
                    contents.getJavadocBefore(aAST.getLineNo());
                if (cmt == null) {
                    log(aAST.getLineNo(), "javadoc.missing");
                }
//                else if (mInScope.size() == 0) {
//                    // don't check author/version for inner classes
//                    if (!mConfig.isAllowNoAuthor()
//                        && (MATCH_JAVADOC_AUTHOR.grep(cmt).length == 0)) {
//                        mMessages.add(lineNo, "type.missingTag", "@author");
//                    }
//                    if (mConfig.isRequireVersion()
//                        && (MATCH_JAVADOC_VERSION.grep(cmt).length == 0)) {
//                        mMessages.add(lineNo, "type.missingTag", "@version");
//                    }
//                }
            }
        }
    }
}
