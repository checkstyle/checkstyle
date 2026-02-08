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
        with text end beign                                         //indent:8 exp:8
        not on start of line"""                                     //indent:8 exp:8
    })                                                              //indent:4 exp:4
    public void method1() {                                         //indent:4 exp:4
    }                                                               //indent:4 exp:4

    @MyAnnotation(value = {                                         //indent:4 exp:4
//below indent:8 exp:8
        """                                                         //indent:8 exp:8
        another text block                                          //indent:8 exp:8
            """                                                     //indent:12 exp:8 warn
    })                                                              //indent:4 exp:4
    public void method2() {                                         //indent:4 exp:4
    }                                                               //indent:4 exp:4

    @SuppressWarnings({                                             //indent:4 exp:4
        "example"                                                   //indent:8 exp:8
    })                                                              //indent:4 exp:4
    public void method3() {                                         //indent:4 exp:4
    }                                                               //indent:4 exp:4

    @interface MyAnnotation {                                       //indent:4 exp:4
        String[] value();                                           //indent:8 exp:8
    }                                                               //indent:4 exp:4
}                                                                   //indent:0 exp:0
