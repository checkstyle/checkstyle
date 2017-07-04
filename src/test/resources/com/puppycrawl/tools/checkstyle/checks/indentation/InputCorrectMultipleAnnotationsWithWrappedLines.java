package com.puppycrawl.tools.checkstyle.checks.indentation; //indent:0 exp:0




@WrappedLinesAnnotation1 //indent:0 exp:0
@WrappedLinesAnnotation3( //indent:0 exp:0
    "value" //indent:4 exp:4
) //indent:0 exp:0
public class InputCorrectMultipleAnnotationsWithWrappedLines { //indent:0 exp:0

    @WrappedLinesAnnotation1 //indent:4 exp:4
    @WrappedLinesAnnotation3( //indent:4 exp:4
        "value" //indent:8 exp:8
    ) //indent:4 exp:4
    public String value; //indent:4 exp:4

    @WrappedLinesAnnotation1 //indent:4 exp:4
    @WrappedLinesAnnotation3( //indent:4 exp:4
        "value" //indent:8 exp:8
    ) //indent:4 exp:4
    public String method() { //indent:4 exp:4
        return "value"; //indent:8 exp:8
    }; //indent:4 exp:4

} //indent:0 exp:0

@WrappedLinesAnnotation1 //indent:0 exp:0
@WrappedLinesAnnotation3( //indent:0 exp:0
    "value" //indent:4 exp:4
) //indent:0 exp:0
class InputCorrectMultipleAnnotationsWithWrappedLines2 { //indent:0 exp:0

    @WrappedLinesAnnotation1 //indent:4 exp:4
    @WrappedLinesAnnotation3( //indent:4 exp:4
        "value" //indent:8 exp:8
    ) //indent:4 exp:4
    public String value; //indent:4 exp:4

    @WrappedLinesAnnotation1 //indent:4 exp:4
    @WrappedLinesAnnotation3( //indent:4 exp:4
        "value" //indent:8 exp:8
    ) //indent:4 exp:4
    public String method() { //indent:4 exp:4
        return "value"; //indent:8 exp:8
    }; //indent:4 exp:4

} //indent:0 exp:0

@WrappedLinesAnnotation1 //indent:0 exp:0
@WrappedLinesAnnotation3( //indent:0 exp:0
    "value" //indent:4 exp:4
) //indent:0 exp:0
@WrappedLinesAnnotation2 //indent:0 exp:0
class InputCorrectMultipleAnnotationsWithWrappedLines3 { //indent:0 exp:0

    @WrappedLinesAnnotation1 //indent:4 exp:4
    @WrappedLinesAnnotation3( //indent:4 exp:4
        "value" //indent:8 exp:8
    ) //indent:4 exp:4
    @WrappedLinesAnnotation2 //indent:4 exp:4
    public String value; //indent:4 exp:4

    @WrappedLinesAnnotation1 //indent:4 exp:4
    @WrappedLinesAnnotation3( //indent:4 exp:4
        "value" //indent:8 exp:8
    ) //indent:4 exp:4
    @WrappedLinesAnnotation2 //indent:4 exp:4
    public String method() { //indent:4 exp:4
        return "value"; //indent:8 exp:8
    }; //indent:4 exp:4

} //indent:0 exp:0

@WrappedLinesAnnotation1 //indent:0 exp:0
@WrappedLinesAnnotation3( //indent:0 exp:0
    "value" //indent:4 exp:4
) @WrappedLinesAnnotation2 //indent:0 exp:0
class InputCorrectMultipleAnnotationsWithWrappedLines4 { //indent:0 exp:0

    @WrappedLinesAnnotation1 //indent:4 exp:4
    @WrappedLinesAnnotation3( //indent:4 exp:4
        "value" //indent:8 exp:8
    ) @WrappedLinesAnnotation2 //indent:4 exp:4
    public String value; //indent:4 exp:4

    @WrappedLinesAnnotation1 //indent:4 exp:4
    @WrappedLinesAnnotation3( //indent:4 exp:4
        "value" //indent:8 exp:8
    ) @WrappedLinesAnnotation2 //indent:4 exp:4
    public String method() { //indent:4 exp:4
        return "value"; //indent:8 exp:8
    }; //indent:4 exp:4

} //indent:0 exp:0

@WrappedLinesAnnotation3( //indent:0 exp:0
    "value" //indent:4 exp:4
) //indent:0 exp:0
@WrappedLinesAnnotation2 //indent:0 exp:0
class InputCorrectMultipleAnnotationsWithWrappedLines5 { //indent:0 exp:0

    @WrappedLinesAnnotation3( //indent:4 exp:4
        "value" //indent:8 exp:8
    ) //indent:4 exp:4
    @WrappedLinesAnnotation2 //indent:4 exp:4
    public String value; //indent:4 exp:4

    @WrappedLinesAnnotation3( //indent:4 exp:4
        "value" //indent:8 exp:8
    ) //indent:4 exp:4
    @WrappedLinesAnnotation2 //indent:4 exp:4
    public String method() { //indent:4 exp:4
        return "value"; //indent:8 exp:8
    }; //indent:4 exp:4

} //indent:0 exp:0

@WrappedLinesAnnotation1 //indent:0 exp:0
@WrappedLinesAnnotation4( //indent:0 exp:0
    { "value" } //indent:4 exp:4
) //indent:0 exp:0
@WrappedLinesAnnotation2 //indent:0 exp:0
class InputCorrectMultipleAnnotationsWithWrappedLines6 { //indent:0 exp:0

    @WrappedLinesAnnotation1 //indent:4 exp:4
    @WrappedLinesAnnotation4( //indent:4 exp:4
        { "value" } //indent:8 exp:8
    ) //indent:4 exp:4
    @WrappedLinesAnnotation2 //indent:4 exp:4
    public String value; //indent:4 exp:4

    @WrappedLinesAnnotation1 //indent:4 exp:4
    @WrappedLinesAnnotation4( //indent:4 exp:4
        { "value" } //indent:8 exp:8
    ) //indent:4 exp:4
    @WrappedLinesAnnotation2 //indent:4 exp:4
    public String method() { //indent:4 exp:4
        return "value"; //indent:8 exp:8
    }; //indent:4 exp:4

} //indent:0 exp:0

@WrappedLinesAnnotation1 //indent:0 exp:0
@WrappedLinesAnnotation4({ //indent:0 exp:0
    "value" //indent:4 exp:4
}) //indent:0 exp:0
@WrappedLinesAnnotation2 //indent:0 exp:0
class InputCorrectMultipleAnnotationsWithWrappedLines7 { //indent:0 exp:0

    @WrappedLinesAnnotation1 //indent:4 exp:4
    @WrappedLinesAnnotation4({ //indent:4 exp:4
        "value" //indent:8 exp:8
    }) //indent:4 exp:4
    @WrappedLinesAnnotation2 //indent:4 exp:4
    public String value; //indent:4 exp:4

    @WrappedLinesAnnotation1 //indent:4 exp:4
    @WrappedLinesAnnotation4({ //indent:4 exp:4
        "value" //indent:8 exp:8
    }) //indent:4 exp:4
    @WrappedLinesAnnotation2 //indent:4 exp:4
    public String method() { //indent:4 exp:4
        return "value"; //indent:8 exp:8
    }; //indent:4 exp:4

} //indent:0 exp:0

@WrappedLinesAnnotation1 //indent:0 exp:0
@WrappedLinesAnnotationOuter( //indent:0 exp:0
    @WrappedLinesAnnotationInner //indent:4 exp:4
) //indent:0 exp:0
@WrappedLinesAnnotation2 //indent:0 exp:0
//indent:0 exp:0
class InputCorrectMultipleAnnotationsWithWrappedLines8 { //indent:0 exp:0

    @WrappedLinesAnnotation1 //indent:4 exp:4
    @WrappedLinesAnnotationOuter( //indent:4 exp:4
        @WrappedLinesAnnotationInner //indent:8 exp:8
    ) //indent:4 exp:4
    @WrappedLinesAnnotation2 //indent:4 exp:4
    public String value; //indent:4 exp:4

    @WrappedLinesAnnotation1 //indent:4 exp:4
    @WrappedLinesAnnotationOuter( //indent:4 exp:4
        @WrappedLinesAnnotationInner //indent:8 exp:8
    ) //indent:4 exp:4
    @WrappedLinesAnnotation2 //indent:4 exp:4
    public String method() { //indent:4 exp:4
        return "value"; //indent:8 exp:8
    }; //indent:4 exp:4

} //indent:0 exp:0

@interface WrappedLinesAnnotation1 {} //indent:0 exp:0
@interface WrappedLinesAnnotation2 {} //indent:0 exp:0
@interface WrappedLinesAnnotation3 { //indent:0 exp:0
    String value(); //indent:4 exp:4
} //indent:0 exp:0
@interface WrappedLinesAnnotation4 { //indent:0 exp:0
    String[] value(); //indent:4 exp:4
} //indent:0 exp:0
@interface WrappedLinesAnnotationInner {} //indent:0 exp:0
@interface WrappedLinesAnnotationOuter { //indent:0 exp:0
    WrappedLinesAnnotationInner[] value(); //indent:4 exp:4
} //indent:0 exp:0
