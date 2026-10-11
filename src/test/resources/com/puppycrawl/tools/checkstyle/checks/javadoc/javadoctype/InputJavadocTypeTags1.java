/*
JavadocType
scope = (default)private
excludeScope = (default)null
authorFormat = (default)null
versionFormat = (default)null
allowMissingParamTags = (default)false
allowUnknownTags = (default)false
allowedAnnotations = (default)Generated
violateExecutionOnNonTightHtml = (default)false
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;

class InputJavadocTypeTags1
{
    private int mMissingJavadoc;

    void method1()
    {
    }

    /**
     * @param unused asd
     **/
    void method2()
    {
    }

    /**
     * missing return
     **/
    int method3() {
        return 3;
    }

    /**
     * <p>missing return
     *
     * @param aOne ignored
     **/
    int method4(int aOne) {
        return aOne;
    }

    /**
     * missing throws
     **/
    void method5()
            throws Exception
    {
    }

    /**
     * @see missing throws
     * @see need to see tags to avoid shortcut logic
     **/
    void method6()
            throws Exception
    {
    }

    /**
     * @throws WrongException problem
     **/
    void method7()
            throws Exception, NullPointerException
    {
    }

    /**
     * missing param
     **/
    void method8(int aOne)
    {
    }

    /**
     * @see missing param
     * @see need to see tags to avoid shortcut logic
     **/
    void method9(int aOne)
    {
    }

    /**
     * @param WrongParam problem
     **/
    void method10(int aOne, int aTwo)
    {
    }

    /**
     * @param Unneeded parameter
     * @return also unneeded
     **/
    void method11()
    {
    }

    /**
     * @return first one
     * @return duplicate
     **/
    int method12() {
        return 0;
    }

}
