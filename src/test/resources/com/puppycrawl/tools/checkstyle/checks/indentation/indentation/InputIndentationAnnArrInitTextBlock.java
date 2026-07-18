package                                                             //indent:0 exp:0
    com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:4 exp:4

public class InputIndentationAnnArrInitTextBlock {                  //indent:0 exp:0
    @SuppressWarnings({                                             //indent:4 exp:4
//below indent:8 exp:8
        """
        text block content                                          //indent:8 exp:8
        on multiple lines                                           //indent:8 exp:8
        """                                                         //indent:8 exp:8
    })                                                              //indent:4 exp:4
    public void method() {                                          //indent:4 exp:4
    }                                                               //indent:4 exp:4

    @SuppressWarnings({                                             //indent:4 exp:4
//below indent:8 exp:8
        """
        text block content                                          //indent:8 exp:8
        on multiple lines                                           //indent:8 exp:8
        with text block end                                         //indent:8 exp:8
        not being at start                                          //indent:8 exp:8
        of the line"""                                              //indent:8 exp:8
    })                                                              //indent:4 exp:4
    public void method1() {                                         //indent:4 exp:4
    }                                                               //indent:4 exp:4

    @SuppressWarnings({                                             //indent:4 exp:4
//below indent:8 exp:8
        """
        another text block                                          //indent:8 exp:8
            """                                                     //indent:12 exp:12
    })                                                              //indent:4 exp:4
    public void method2() {                                         //indent:4 exp:4
    }                                                               //indent:4 exp:4

    @SuppressWarnings({                                             //indent:4 exp:4
//below indent:4 exp:8,26,68 warn
    """
    text block with wrong opening delimiter indent                  //indent:4 exp:4
        """                                                         //indent:8 exp:8
    })                                                              //indent:4 exp:4
    public void method4() {                                         //indent:4 exp:4
    }                                                               //indent:4 exp:4

    @SuppressWarnings({                                             //indent:4 exp:4
//below indent:4 exp:8,26,68 warn
    """
    text block with wrong opening and closing delimiter             //indent:4 exp:4
    """                                                             //indent:4 exp:12,30,72 warn
    })                                                              //indent:4 exp:4
    public void method5() {                                         //indent:4 exp:4
    }                                                               //indent:4 exp:4

    @SuppressWarnings(                                              //indent:4 exp:4
        {                                                           //indent:8 exp:8
//below indent:12 exp:8,12
            """
            text block with brace on own line                       //indent:12 exp:12
            and correct indentation                                 //indent:12 exp:12
            """                                                     //indent:12 exp:8,12
        })                                                          //indent:8 exp:8
    public void method6() {                                         //indent:4 exp:4
    }                                                               //indent:4 exp:4

    @SuppressWarnings(                                              //indent:4 exp:4
        {                                                           //indent:8 exp:8
//below indent:12 exp:8,12
            """
            text block with brace on own line                       //indent:12 exp:12
            end delimiter extra indented                            //indent:12 exp:12
                """                                                 //indent:16 exp:16
        })                                                          //indent:8 exp:8
    public void method7() {                                         //indent:4 exp:4
    }                                                               //indent:4 exp:4

    @SuppressWarnings(                                              //indent:4 exp:4
        {                                                           //indent:8 exp:8
//below indent:4 exp:8,12,68 warn
    """
    brace on own line wrong opening indent                          //indent:4 exp:4
    """                                                             //indent:4 exp:12,16,72 warn
    })                                                              //indent:4 exp:4
    public void method8() {                                         //indent:4 exp:4
    }                                                               //indent:4 exp:4

    @SuppressWarnings(                                              //indent:4 exp:4
        {                                                           //indent:8 exp:8
//below indent:4 exp:8,12,68 warn
    """
    brace on own line wrong opening and closing                     //indent:4 exp:4
        """                                                         //indent:8 exp:8,12
    })                                                              //indent:4 exp:4
    public void method9() {                                         //indent:4 exp:4
    }                                                               //indent:4 exp:4
    @interface ApiParam {                                           //indent:4 exp:4
        Example[] examples();                                       //indent:8 exp:8
    }                                                               //indent:4 exp:4
    @interface Example {                                            //indent:4 exp:4
        ExampleProperty[] value();                                  //indent:8 exp:8
    }                                                               //indent:4 exp:4
    @interface ExampleProperty {                                    //indent:4 exp:4
        String value() default "";                                  //indent:8 exp:8
    }                                                               //indent:4 exp:4
    @ApiParam(                                                      //indent:4 exp:4
        examples = @Example(                                        //indent:8 exp:8
            value = {                                               //indent:12 exp:12
                @ExampleProperty(                                   //indent:16 exp:16
//below indent:20 exp:20
                    value = """
                            generic text block content              //indent:28 exp:28
                            on multiple lines                       //indent:28 exp:28
                            """                                     //indent:28 exp:28
                )                                                   //indent:16 exp:16
            }                                                       //indent:12 exp:12
        )                                                           //indent:8 exp:8
    )                                                               //indent:4 exp:4
    public void method10() {                                        //indent:4 exp:4
    }                                                               //indent:4 exp:4
}                                                                   //indent:0 exp:0
