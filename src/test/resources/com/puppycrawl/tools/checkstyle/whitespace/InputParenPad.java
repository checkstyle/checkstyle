package com.puppycrawl.tools.checkstyle.whitespace;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class InputParenPad
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
            this( 0);
        }

        ParenPadSpaceLeft( int i) {
            super( );
        }

        @SuppressWarnings( "")
        void method( boolean status) {
            try ( Writer writer = new StringWriter( )) {
                do {
                    writer.append("a");
                } while ( status);
            } catch ( IOException e) {
                while ( status) {
                    for ( int i = 0; i < ( long) ( 2 * ( 4 / 2)); i++) {
                        if ( i > 2) {
                            synchronized ( this) {
                                switch ( i) {
                                    case 3:
                                    case ( 4):
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

    class ParenPadSpaceRight {
        ParenPadSpaceRight( ) {
            this(0 );
        }

        ParenPadSpaceRight(int i ) {
            super( );
        }

        @SuppressWarnings("" )
        void method(boolean status ) {
            try (Writer writer = new StringWriter( ) ) {
                do {
                    writer.append("a" );
                } while (status );
            } catch (IOException e ) {
                while (status ) {
                    for (int i = 0; i < (long ) (2 * (4 / 2 ) ); i++ ) {
                        if (i > 2 ) {
                            synchronized (this ) {
                                switch (i ) {
                                    case 3:
                                    case (4 ):
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
