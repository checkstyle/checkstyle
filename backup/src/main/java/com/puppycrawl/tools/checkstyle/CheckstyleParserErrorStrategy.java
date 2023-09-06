///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

package com.puppycrawl.tools.checkstyle;

import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.InputMismatchException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.Token;

/**
 * Extending BailErrorStrategy allows us to report errors while
 * cancelling the parsing operation. If input is not syntactically
 * correct, we do not need to use antlr's recovery strategy, which
 * can add significant overhead.
 *
 */
public class CheckstyleParserErrorStrategy extends BailErrorStrategy {

    @Override
    public Token recoverInline(Parser recognizer) {
        reportError(recognizer, new InputMismatchException(recognizer));
        return super.recoverInline(recognizer);
    }
}
