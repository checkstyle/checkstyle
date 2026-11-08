/*
ParenPad
option = (default)nospace
tokens = (default)ANNOTATION, ANNOTATION_FIELD_DEF, CTOR_CALL, CTOR_DEF, DOT, ENUM_CONSTANT_DEF, \
         EXPR, LITERAL_CATCH, LITERAL_DO, LITERAL_FOR, LITERAL_IF, LITERAL_NEW, \
         LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_WHILE, METHOD_CALL, METHOD_DEF, QUESTION, \
         RESOURCE_SPECIFICATION, SUPER_CTOR_CALL, LAMBDA, RECORD_DEF, RECORD_PATTERN_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.parenpad;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class InputParenPadLeftRightAndNoSpace1
{
    class ParenPadNoSpace  {
        ParenPadNoSpace() {
            this(0);
        }

        ParenPadNoSpace(int i) {
            super();
        }

        @SuppressWarnings("")
        void method(boolean status) {
            try (Writer writer = new StringWriter()) {
                do {
                    writer.append("a");
                } while (status);
            } catch (IOException e) {
                while (status) {
                    for (int i = 0; i < (long) (2 * (4 / 2)); i++) {
                        if (i > 2) {
                            synchronized (this) {
                                switch (i) {
                                    case 3:
                                    case (4):
                                    case 5:
                                        break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    class ParenPadSpaceLeft {
        ParenPadSpaceLeft( ) {
            // 2 violations above:
            //           ''(' is followed by whitespace.'
            //           '')' is preceded with whitespace.'
            this( 0); // violation ''(' is followed by whitespace.'
        }

        ParenPadSpaceLeft( int i) { // violation ''(' is followed by whitespace.'
            super( );
            // 2 violations above:
            //           ''(' is followed by whitespace.'
            //           '')' is preceded with whitespace.'
        }

        @SuppressWarnings( "") // violation ''(' is followed by whitespace.'
        void method( boolean status) { // violation ''(' is followed by whitespace.'
            try ( Writer writer = new StringWriter( )) {
            // 3 violations above:
            //           ''(' is followed by whitespace.'
            //           ''(' is followed by whitespace.'
            //           '')' is preceded with whitespace.'
                do {
                    writer.append("a");
                } while ( status); // violation ''(' is followed by whitespace.'
            } catch ( IOException e) { // violation ''(' is followed by whitespace.'
                while ( status) { // violation ''(' is followed by whitespace.'
                    for ( int i = 0; i < ( long) ( 2 * ( 4 / 2)); i++) {
            // 3 violations above:
            //           ''(' is followed by whitespace.'
            //           ''(' is followed by whitespace.'
            //           ''(' is followed by whitespace.'
                        if ( i > 2) { // violation ''(' is followed by whitespace.'
                            synchronized ( this) { // violation ''(' is followed by whitespace.'
                                switch ( i) { // violation ''(' is followed by whitespace.'
                                    case 3:
                                    case ( 4): // violation ''(' is followed by whitespace.'
                                    case 5:
                                        break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
