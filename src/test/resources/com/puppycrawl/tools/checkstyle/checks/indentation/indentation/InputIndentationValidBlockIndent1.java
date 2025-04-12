package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

import java.io.Closeable; //indent:0 exp:0
import java.util.stream.Stream; //indent:0 exp:0

/**                                                                           //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 4                                                        //indent:1 exp:1
 * basicOffset = 4                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * caseIndent = 4                                                             //indent:1 exp:1
 * forceStrictCondition = false                                               //indent:1 exp:1
 * lineWrappingIndentation = 4                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 4                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * @author  jrichard                                                         //indent:1 exp:1
 */                                                                           //indent:1 exp:1
public class InputIndentationValidBlockIndent1 { //indent:0 exp:0

    /** Creates a new instance of InputValidBlockIndent */ //indent:4 exp:4
    public InputIndentationValidBlockIndent1() { //indent:4 exp:4
    } //indent:4 exp:4

} //indent:0 exp:0

enum EquivalenceTester { //indent:0 exp:0
    /** //indent:4 exp:4
     * An equivalenster that decides based on {@link Object#equals(Object) equals}. //indent:5 exp:5
     */ //indent:5 exp:5
    OBJECT_ATTRIBUTES { //indent:4 exp:4
        /** //indent:8 exp:8
         * {@inheritDoc} //indent:9 exp:9
         */ //indent:9 exp:9
        public boolean areEqual( final Object first, final Object second ) { //indent:8 exp:8
            return true; //indent:12 exp:12
        } //indent:8 exp:8

        /** //indent:8 exp:8
         * {@inheritDoc} //indent:9 exp:9
         */ //indent:9 exp:9
        public int hashCode( final Object target ) { //indent:8 exp:8
            return 1; //indent:12 exp:12
        } //indent:8 exp:8
    }, //indent:4 exp:4

    /** //indent:4 exp:4
     * An equivalence tester that decides based on {@code ==}. //indent:5 exp:5
     */ //indent:5 exp:5
    OBJECT_IDENTITIES //indent:4 exp:4
    { //indent:4 exp:4
        /** //indent:8 exp:8
         * {@inheritDoc} //indent:9 exp:9
         */ //indent:9 exp:9
        public boolean areEqual( final Object first, final Object second ) { //indent:8 exp:8
            return first == second; //indent:12 exp:12
        } //indent:8 exp:8

        /** //indent:8 exp:8
         * {@inheritDoc} //indent:9 exp:9
         */ //indent:9 exp:9
        public int hashCode( final Object target ) { //indent:8 exp:8
            return System.identityHashCode( target ); //indent:12 exp:12
        } //indent:8 exp:8
    }; //indent:4 exp:4

    /** //indent:4 exp:4
     * Tells whether the two given objects are considered equivalent. //indent:5 exp:5
     * //indent:5 exp:5
     * @param first first comparand //indent:5 exp:5
     * @param second second comparand //indent:5 exp:5
     * @return whether {@code first} and {@code second} are considered equivalent //indent:5 exp:5
     */ //indent:5 exp:5
    public abstract boolean areEqual( Object first, Object second ); //indent:4 exp:4

    /** //indent:4 exp:4
     * Computes a hash code for the given object. //indent:5 exp:5
     * //indent:5 exp:5
     * @param target object to compute a hash for //indent:5 exp:5
     * @return the computed hash //indent:5 exp:5
     */ //indent:5 exp:5
    public abstract int hashCode( Object target ); //indent:4 exp:4
} //indent:0 exp:0

class AnonymousClassWithInitializer { //indent:0 exp:0
    void create() { //indent:4 exp:4
        new Object() { //indent:8 exp:8
            { //indent:12 exp:12
                new Object(); //indent:16 exp:16
            } //indent:12 exp:12
        }; //indent:8 exp:8
    } //indent:4 exp:4
} //indent:0 exp:0

class OneLineCode { //indent:0 exp:0
    void block() { //indent:4 exp:4
        { if (true) return; }  //indent:8 exp:8
    } //indent:4 exp:4
    void lambda() { //indent:4 exp:4
        Stream.of(false).map(x -> { if (x) return 0; else return 1; }); //indent:8 exp:8
    } //indent:4 exp:4
    void tryCatchDoWhile(Closeable is) throws Exception { //indent:4 exp:4
        if (is != null) { try { is.close(); } catch (Exception e) {} } //indent:8 exp:8
        if (is != null) { do { is.close(); } while (is != null); } //indent:8 exp:8
    } //indent:4 exp:4
} //indent:0 exp:0

