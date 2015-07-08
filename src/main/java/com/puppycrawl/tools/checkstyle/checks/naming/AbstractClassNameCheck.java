////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.naming;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.AbstractFormatCheck;

/**
 * <p>
 * Ensures that the names of abstract classes conforming to some
 * regular expression and check that <code>abstract</code> modifier exists.
 * </p>
 * <p>
 * Rationale: Abstract classes are convenience base class
 * implementations of interfaces, not types as such. As such
 * they should be named to indicate this. Also if names of classes
 * starts with 'Abstract' it's very convenient that they will
 * have abstract modifier.
 * </p>
 *
 * @author <a href="mailto:simon@redhillconsulting.com.au">Simon Harris</a>
 * @author <a href="mailto:solid.danil@gmail.com">Danil Lopatin</a>
 */
public final class AbstractClassNameCheck extends AbstractFormatCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String ILLEGAL_ABSTRACT_CLASS_NAME = "illegal.abstract.class.name";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String NO_ABSTRACT_CLASS_MODIFIER = "no.abstract.class.modifier";

    /** Default format for abstract class names */
    private static final String DEFAULT_FORMAT = "^Abstract.+$";

    /** whether to ignore checking the modifier */
    private boolean ignoreModifier;

    /** whether to ignore checking the name */
    private boolean ignoreName;

    /** Creates new instance of the check. */
    public AbstractClassNameCheck() {
        super(DEFAULT_FORMAT);
    }

    /**
     * Whether to ignore checking for the <code>abstract</code> modifier.
     * @param value new value
     */
    public void setIgnoreModifier(boolean value) {
        ignoreModifier = value;
    }

    /**
     * Whether to ignore checking the name.
     * @param value new value.
     */
    public void setIgnoreName(boolean value) {
        ignoreName = value;
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[]{TokenTypes.CLASS_DEF};
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[]{TokenTypes.CLASS_DEF};
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[]{TokenTypes.CLASS_DEF};
    }

    @Override
    public void visitToken(DetailAST ast) {
        visitClassDef(ast);
    }

    /**
     * Checks class definition.
     * @param ast class definition for check.
     */
    private void visitClassDef(DetailAST ast) {
        final String className =
            ast.findFirstToken(TokenTypes.IDENT).getText();
        if (isAbstract(ast)) {
            // if class has abstract modifier
            if (!ignoreName && !isMatchingClassName(className)) {
                log(ast.getLineNo(), ast.getColumnNo(),
                    ILLEGAL_ABSTRACT_CLASS_NAME, className, getFormat());
            }
        }
        else if (!ignoreModifier && isMatchingClassName(className)) {
            log(ast.getLineNo(), ast.getColumnNo(),
                NO_ABSTRACT_CLASS_MODIFIER, className);
        }
    }

    /**
     * @param ast class definition for check.
     * @return true if a given class declared as abstract.
     */
    private boolean isAbstract(DetailAST ast) {
        final DetailAST abstractAST = ast.findFirstToken(TokenTypes.MODIFIERS)
            .findFirstToken(TokenTypes.ABSTRACT);

        return abstractAST != null;
    }

    /**
     * @param className class name for check.
     * @return true if class name matches format of abstract class names.
     */
    private boolean isMatchingClassName(String className) {
        return getRegexp().matcher(className).find();
    }
}
