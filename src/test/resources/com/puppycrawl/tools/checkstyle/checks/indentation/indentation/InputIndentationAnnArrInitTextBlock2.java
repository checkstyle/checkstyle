package                                                             //indent:0 exp:0
    com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:4 exp:4

public class InputIndentationAnnArrInitTextBlock2 {                 //indent:0 exp:0

    @interface PropertySourceAnnotation {                           //indent:4 exp:4
        String[] properties();                                      //indent:8 exp:8
    }                                                               //indent:4 exp:4

//below indent:4 exp:4
    @PropertySourceAnnotation(properties = {
//below indent:12 exp:8 warn
            """
            foo = bar                                               //indent:12 exp:12
            """,                                                    //indent:12 exp:12
//below indent:12 exp:8 warn
            """
            bar = baz                                               //indent:12 exp:12
            """,                                                    //indent:12 exp:12
//below indent:12 exp:8 warn
            """
            baz = quux                                              //indent:12 exp:12
            """                                                     //indent:12 exp:12
    })                                                              //indent:4 exp:4
    public void method11() {                                        //indent:4 exp:4
    }                                                               //indent:4 exp:4

//below indent:4 exp:4
    @PropertySourceAnnotation(properties = {
    })                                                              //indent:4 exp:4
    public void method12() {                                        //indent:4 exp:4
    }                                                               //indent:4 exp:4

//below indent:4 exp:4
    @PropertySourceAnnotation(properties = {
//below indent:12 exp:8 warn
            "prefix" + """
                    text content                                    //indent:20 exp:20
    """                                                             //indent:4 exp:12 warn
    })                                                              //indent:4 exp:4
    public void method13() {                                        //indent:4 exp:4
    }                                                               //indent:4 exp:4
}                                                                   //indent:0 exp:0
