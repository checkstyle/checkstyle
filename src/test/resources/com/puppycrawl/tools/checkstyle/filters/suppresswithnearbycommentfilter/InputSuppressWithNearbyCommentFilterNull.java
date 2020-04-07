package com.puppycrawl.tools.checkstyle.filters.suppresswithnearbycommentfilter;

public class InputSuppressWithNearbyCommentFilterNull {
    void method() {
        int x = 7;    // -@csl[FinalLocalVariable](5) my comment
        String s = "";
    }
}
