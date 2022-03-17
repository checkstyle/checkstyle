/*
TrailingComment
format = (default)^[\s});]*$
legalComment = ^ this is ok


*/

package com.puppycrawl.tools.checkstyle.checks.trailingcomment;

public class InputTrailingCommentWithEmoji {
    // violation below
    String a = "🧐🥳😠😨"; /*string with emoji */
    // violation below
    String b = "👌🏻🤞🏻😂😂🎄"; // another string

    /* yet another */String c = "😂😂🎄👍"; /* this is ok */
    String d = "🧐🥳"; // this is ok
  /*
  * 🎄a🎄b🎄c🎄 // violation below
  * 🎄 👌🏻 🤘🏻 🎄*/  void test1() { /* some
   🎄  adsad 🎄                  /*comments */
    }
    // violation below
    /*😂 😂*/ // 🤛🏻🤛🏻

    /* 🎃 ☠️ */ // 👿 asd😱 // violation
    /* 😱 🎄 */ /*🎄 🥶 */ // violation

    /**  // comment
     * 🤛🏻q🥳w👆🏻e😠r👇🏻t😨y
     */
    /* 👆🏻 👇🏻    */  void test2() {} // violation
}
