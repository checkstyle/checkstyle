/*
TrailingComment
format = (default)^[\\s});]*$
legalComment = ^ this is ok


*/

package com.puppycrawl.tools.checkstyle.checks.trailingcomment;

public class InputTrailingCommentWithEmoji {
    // violation below 'Don't use trailing comments.'
    String a = "🧐🥳😠😨"; /*string with emoji */
    // violation below 'Don't use trailing comments.'
    String b = "👌🏻🤞🏻😂😂🎄"; // another string

    /* yet another */String c = "😂😂🎄👍"; /* this is ok */
    String d = "🧐🥳"; // this is ok
  /*
  * 🎄a🎄b🎄c🎄 // violation below 'Don't use trailing comments.'
  * 🎄 👌🏻 🤘🏻 🎄*/  void test1() { /* some
   🎄  adsad 🎄                  /*comments */
    }
    // violation below 'Don't use trailing comments.'
    /*😂 😂*/ // 🤛🏻🤛🏻

    // violation below 'Don't use trailing comments.'
    /* 🎃 ☠️ */ // 👿 asd😱
    /* 😱 🎄 */ /*🎄 🥶 */

    /**  // comment
     * 🤛🏻q🥳w👆🏻e😠r👇🏻t😨y
     */
    /* 👆🏻 👇🏻    */  void test2() {}
}
