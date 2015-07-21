package com.puppycrawl.tools.checkstyle.javadoc;
/** Javadoc for import */
import java.io.Serializable;

/**
 * Some javadoc.
 * 
 * @since Some javadoc.
 * @version 1.0 //warn //warn
 * @deprecated Some javadoc.
 * @see Some javadoc. //warn
 * @author max //warn
 */
class WithAnnotations1 implements Serializable
{
	/**
     * The client's first name.
     * @serial
     */
    private String fFirstName;
     
    /**
     * The client's first name.
     * @serial
     */
    private String sSecondName;
      
    /**
     * The client's first name.
     * @serialField
     */
    private String tThirdName;
	
    /**
     * Some text.
     * @param aString Some text.
     * @return Some text.
     * @serialData Some javadoc.
     * @deprecated Some text.
     * @throws Exception Some text. //warn
     */
    String method(String aString) throws Exception
    {
        return "null";
    }

    /**
     * Some text.
     * @serialData Some javadoc.
     * @return Some text. //warn
     * @param aString Some text. //warn
     * @throws Exception Some text.
     */
    String method1(String aString) throws Exception
    {
        return "null";
    }

    /**
     * Some text.
     * @throws Exception Some text.
     * @param aString Some text. //warn
     */
    void method2(String aString) throws Exception {}
    
    /**
     * Some text.
     * @deprecated Some text.
     * @throws Exception Some text. //warn
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
     * @return Some text. //warn
     * @param aString Some text. //warn
     */
    String method5(String aString)
    {
        return "null";
    }
    
    /**
     * Some text.
     * @param aString Some text.
     * @return Some text.
     * @serialData Some javadoc.
     * @param aInt Some text. //warn
     * @throws Exception Some text.
     * @param aBoolean Some text. //warn
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
     * @author max //warn
     */
    class InnerClassWithAnnotations
    {
        /**
         * Some text.
         * @return Some text.
         * @deprecated Some text.
         * @param aString Some text. //warn
         * @throws Exception Some text.
         */
        String method(String aString) throws Exception
        {
            return "null";
        }

        /**
         * Some text.
         * @throws Exception Some text.
         * @return Some text. //warn
         * @param aString Some text. //warn
         */
        String method1(String aString) throws Exception
        {
            return "null";
        }

        /**
         * Some text.
         * @serialData Some javadoc.
         * @param aString Some text. //warn
         * @throws Exception Some text.
         */
        void method2(String aString) throws Exception {}
        
        /**
         * Some text.
         * @deprecated Some text.
         * @throws Exception Some text. //warn
         */
        void method3() throws Exception {}
        
        /**
         * Some text.
         * @throws Exception Some text.
         * @serialData Some javadoc.
         * @return Some text. //warn
         */
        String method4() throws Exception
        {
            return "null";
        }

        /**
         * Some text.
         * @param aString Some text.
         * @deprecated Some text.
         * @return Some text. //warn
         */
        String method5(String aString)
        {
            return "null";
        }
        
        /**
         * Some text.
         * @param aString Some text.
         * @return Some text.
         * @param aInt Some text. //warn
         * @throws Exception Some text.
         * @param aBoolean Some text. //warn
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
         * @param aString Some text. //warn
         * @serialData Some javadoc.
         * @deprecated Some text.
         * @return Some text. //warn
         */
        String method(String aString) throws Exception
        {
            return "null";
        }

        /**
         * Some text.
         * @param aString Some text.
         * @throws Exception Some text.
         * @return Some text. //warn
         */
        String method1(String aString) throws Exception
        {
            return "null";
        }

        /**
         * Some text.
         * @throws Exception Some text.
         * @param aString Some text. //warn
         */
        void method2(String aString) throws Exception {}
        
        /**
         * Some text.
         * @deprecated Some text.
         * @throws Exception Some text. //warn
         */
        void method3() throws Exception {}
        
        /**
         * Some text.
         * @throws Exception Some text.
         * @return Some text. //warn
         */
        String method4() throws Exception
        {
            return "null";
        }

        /**
         * Some text.
         * @deprecated Some text.
         * @return Some text. //warn
         * @param aString Some text. //warn
         */
        String method5(String aString)
        {
            return "null";
        }
        
        /**
         * Some text.
         * @param aString Some text.
         * @return Some text.
         * @param aInt Some text. //warn
         * @throws Exception Some text.
         * @param aBoolean Some text. //warn
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
 * @version 1.0 //warn //warn
 * @deprecated Some javadoc.
 * @see Some javadoc. //warn
 * @author max //warn
 */
enum Foo4 {}

/**
 * Some javadoc.
 * 
 * @version 1.0
 * @since Some javadoc.
 * @serialData Some javadoc.
 * @author max //warn
 */
interface FooIn {
    /**
     * @value tag without specified order by default
     */
    int CONSTANT = 0;
}
