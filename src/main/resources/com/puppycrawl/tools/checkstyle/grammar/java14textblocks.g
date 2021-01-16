////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

header {
package com.puppycrawl.tools.checkstyle.grammar;

import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import java.text.MessageFormat;
import antlr.CommonHiddenStreamToken;
import antlr.TokenStreamSelector;
}

//----------------------------------------------------------------------------
// The Java 14 Text Block scanner
//----------------------------------------------------------------------------
class GeneratedTextBlockLexer extends Lexer;

options {
    importVocab=GeneratedJava;  // Use tokens from java.g
    k=2;
    charVocabulary='\u0000'..'\uFFFE';
    filter=true;
}

{
    public TokenStreamSelector selector;

    // explicitly set tab width to 1 (default in ANTLR 2.7.1)
    // in ANTLR 2.7.2a2 the default has changed from 1 to 8
    public void tab()
    {
        setColumn( getColumn() + 1 );
    }
}

protected
NEWLINE
     : ( options {generateAmbigWarnings=false;}
         :   "\r\n"  // Evil DOS
         |   '\r'    // Macintosh
         |   '\n'    // Unix (the right way)
       )
       {this.newline();}
     ;

TEXT_BLOCK_LITERAL_END
   : "\"\"\"" {selector.pop();}
   ;

protected
TWO_DOUBLE_QUOTES
    :   '"''"' ( NEWLINE | ~'"' )
    ;

protected
ONE_DOUBLE_QUOTE
    :   '"' ( NEWLINE | ~'"' )
    ;

TEXT_BLOCK_CONTENT
     :  (       (TWO_DOUBLE_QUOTES )=> TWO_DOUBLE_QUOTES
            |   ONE_DOUBLE_QUOTE
            |   NEWLINE
            |   ~'"'
            |   STD_ESC
        )+
     ;

protected
STD_ESC
    :   '\\'
            (    'n'
            |    'r'
            |    's'
            |    't'
            |    'b'
            |    'f'
            |    '"'
            |    '\''
            |    '\\'
            )
    ;
