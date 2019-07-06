package com.puppycrawl.tools.checkstyle.filters.suppresswithnearbycommentfilter;

public class InputSuppressWithNearbyCommentFilterById {

    private int A1; // @cs-: ignore (reason)

    private static final int abc = 5; // @cs-: violation (No NPE here)

    int line_length = 100; // Suppression @cs-: ignore (reason)

    private long ID = 1; // Suppression @cs-:
    /*
        Suppression @cs-: ignore (reason)*/private long ID3 = 1;

    private int DEF = 4; // @cs-: ignore (allow DEF)
    private int XYZ = 3; // @cs-: ignore (allow xyz)
}
