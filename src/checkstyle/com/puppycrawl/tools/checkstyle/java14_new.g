////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2002  Oliver Burn
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
////////////////////////////////////////////////////////////////////////////////
header {
package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
}


/** Java 1.4 Recognizer
 *
 * Based heavily on the Grammer example that comes with ANTLR. See
 * http://www.antlr.org.
 *
 */
class Java14Recognizer extends JavaRecognizer;

// Options don't get inherited, copy of option block required.
options {
	k = 2;                           // two token lookahead
	exportVocab=Java14;              // Call its vocabulary "Java14"
	codeGenMakeSwitchThreshold = 2;  // Some optimizations
	codeGenBitsetTestThreshold = 3;
	defaultErrorHandler = false;     // Don't generate parser error handlers
	buildAST = true;
}

// overrides the statement production in java.g, adds assertStatement
statement
	:	traditionalStatement
	|	assertStatement
	;

// assert statement, available since JDK 1.4
assertStatement
	:	ASSERT^ expression ( COLON expression )? SEMI!
	;

class Java14Lexer extends JavaLexer;

options {
	exportVocab=Java14;    // call the vocabulary "Java14",
	testLiterals=false;    // don't automatically test for literals
	k=4;                   // four characters of lookahead
	charVocabulary='\u0003'..'\uFFFF';
	codeGenBitsetTestThreshold=20;
}

tokens {
        ASSERT="assert";
}

// antlr expects a definition here: 'unexpected token: null'
// To avoid that message, one definition from GeneratedJavaLexer
// is repeated. Rather inelegant but I didn't find a better solution :-(
// Feel free to improve this...
protected
FLOAT_SUFFIX
	:	'f'|'F'|'d'|'D'
	;
