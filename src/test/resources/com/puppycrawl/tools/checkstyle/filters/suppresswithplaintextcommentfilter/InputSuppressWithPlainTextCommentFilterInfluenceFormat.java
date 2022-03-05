package com.puppycrawl.tools.checkstyle.filters.suppresswithplaintextcommentfilter;

public class InputSuppressWithPlainTextCommentFilterInfluenceFormat {
    // @cs-: ClassDataAbstractionCoupling influence 2
    // @cs-: MagicNumber influence 4
    public class UserService {
        private int value = 10022; // no violations from MagicNumber here
    }
    //BEGIN GENERATED CONTENT
}
