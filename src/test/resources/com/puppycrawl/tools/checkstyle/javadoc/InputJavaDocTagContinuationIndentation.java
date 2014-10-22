package com.puppycrawl.tools.checkstyle.javadoc;

import java.io.Serializable;

/**
 * Some javadoc.
 *
 * @since Some javadoc.
 *     Some javadoc.
 * @version 1.0
 * @deprecated Some javadoc.
 *     Some javadoc.
 * @see Some javadoc.
 * @author max
 *     Some javadoc.
 */
class JavaDocTagContinuationIndentation implements Serializable
{
	/**
     * The client's first name.
     * @serial Some javadoc.
     *     Some javadoc.
     */
    private String fFirstName;
     
    /**
     * The client's first name.
     * @serial
     *     Some javadoc.
     */
    private String sSecondName;
      
    /**
     * The client's first name.
     * @serialField
     *     Some javadoc.
     */
    private String tThirdName;
	
    /**
     * Some text.
     * @param aString Some javadoc.
     *     Some javadoc.
     * @return Some text.
     * @serialData Some javadoc.
     * @deprecated Some text.
     *    Some javadoc. // warn
     * @throws Exception Some text.
     */
    String method(String aString) throws Exception
    {
        return "null";
    }

    /**
     * Some text.
     * @serialData Some javadoc.
     * @return Some text.
     *     Some javadoc.
     * @param aString Some text.
     * @throws Exception Some text.
     */
    String method1(String aString) throws Exception
    {
        return "null";
    }

    /**
     * Some text.
     * @throws Exception Some text.
     *     Some javadoc.
     * @param aString Some text.
     */
    void method2(String aString) throws Exception {}
    
    /**
     * Some text.
     * @deprecated Some text.
     * @throws Exception Some text.
     *     Some javadoc.
     */
    void method3() throws Exception {}
    
    /**
     * Some text.
     * @return Some text.
     * @throws Exception Some text.
     */
    String method4() throws Exception
    {
        return "null";
    }

    /**
     * Some text.
     * @deprecated Some text.
     * @return Some text.
     * @param aString Some text.
     */
    String method5(String aString)
    {
        return "null";
    }
    
    /**
     * Some text.
     * @param aString Some text.
     * @return Some text.
     *    Some javadoc. // warn
     * @serialData Some javadoc.
     * @param aInt Some text.
     *    Some javadoc. // warn
     * @throws Exception Some text.
     * @param aBoolean Some text.
     * @deprecated Some text.
     */
    String method6(String aString, int aInt, boolean aBoolean) throws Exception
    {
        return "null";
    }
    
    /**
     * Some javadoc.
     * 
     * @version 1.0
     * @since Some javadoc.
     * @serialData Some javadoc.
     * @author max
     */
    class InnerClassWithAnnotations
    {
        /**
         * Some text.
         * @return Some text.
         * @deprecated Some text.
         *     Some javadoc.
         * @param aString Some text.
         * @throws Exception Some text.
         *     Some javadoc.
         */
        String method(String aString) throws Exception
        {
            return "null";
        }

        /**
         * Some text.
         * @throws Exception Some text.
         *     Some javadoc.
         * @return Some text.
         * @param aString Some text.
         *     Some javadoc.
         */
        String method1(String aString) throws Exception
        {
            return "null";
        }

        /**
         * Some text.
         * @serialData Some javadoc.
         *     Some javadoc.
         * @param aString Some text.
         *     Some javadoc.
         * @throws Exception Some text.
         */
        void method2(String aString) throws Exception {}
        
        /**
         * Some text.
         * @deprecated Some text.
         * @throws Exception Some text.
         */
        void method3() throws Exception {}
        
        /**
         * Some text.
         * @throws Exception Some text.
         * @serialData Some javadoc.
         * @return Some text.
         */
        String method4() throws Exception
        {
            return "null";
        }

        /**
         * Some text.
         * @param aString Some text.
         * @deprecated Some text.
         * @return Some text.
         */
        String method5(String aString)
        {
            return "null";
        }
        
        /**
         * Some text.
         * @param aString Some text.
         * @return Some text.
         * @param aInt Some text.
         *    Some javadoc. // warn
         * @throws Exception Some text.
         * @param aBoolean Some text.
         *    Some javadoc. // warn
         * @deprecated Some text.
         */
        String method6(String aString, int aInt, boolean aBoolean) throws Exception
        {
            return "null";
        }
    }
    
    InnerClassWithAnnotations anon = new InnerClassWithAnnotations()
    {
        /**
         * Some text.
         * @throws Exception Some text.
         * @param aString Some text.
         *   Some javadoc. // warn
         * @serialData Some javadoc.
         *    Some javadoc. // warn
         * @deprecated Some text.
         * @return Some text.
         */
        String method(String aString) throws Exception
        {
            return "null";
        }

        /**
         * Some text.
         * @param aString Some text.
         *     Some javadoc.
         * @throws Exception Some text.
         * @return Some text.
         */
        String method1(String aString) throws Exception
        {
            return "null";
        }

        /**
         * Some text.
         * @throws Exception Some text.
         *     Some javadoc.
         * @param aString Some text.
         */
        void method2(String aString) throws Exception {}
        
        /**
         * Some text.
         * @deprecated Some text.
         *     Some javadoc.
         * @throws Exception Some text.
         */
        void method3() throws Exception {}
        
        /**
         * Some text.
         * @throws Exception Some text.
         * @return Some text.
         */
        String method4() throws Exception
        {
            return "null";
        }

        /**
         * Some text.
         * @deprecated Some text.
         * @return Some text.
         * @param aString Some text.
         */
        String method5(String aString)
        {
            return "null";
        }
        
        /**
         * Some text.
         *       Some javadoc. // warn
         * @param aString Some text.
         *    Some javadoc. // warn
         * @return Some text.
         * @param aInt Some text.
         *    Some javadoc. // warn
         * @throws Exception Some text.
         *    Some javadoc. // warn
         * @param aBoolean Some text.
         * @deprecated Some text.
         */
        String method6(String aString, int aInt, boolean aBoolean) throws Exception
        {
            return "null";
        }
    };
}

/**
 * Some javadoc.
 * 
 * @since Some javadoc.
 * @version 1.0
 * @deprecated Some javadoc.
 *     Some javadoc.
 *     Some javadoc.
 * @see Some javadoc.
 *    Some javadoc. // warn
 * @author max
 */
enum Foo {}

/**
 * Some javadoc.
 * 
 * @version 1.0
 * @since Some javadoc.
 *     Some javadoc.
 * @serialData Some javadoc.
 *   Some javadoc. // warn
 * @author max
 */
interface FooIn {}