package com.google.checkstyle.test.chapter4formatting.rule4842fallthrough;

/** Some javadoc. */
public class InputFallThrough {
  void method(int i, int j, boolean cond) {
    while (true) {
      switch (i) {
        case 0: // no problem
        case 1:
          i++;
          break;
        case 2:
          i++;
        case 3: // violation 'Fall through from previous branch of the switch statement.'
          i++;
          break;
        case 4:
          return;
        case 5:
          throw new RuntimeException("");
        case 6:
          continue;
        case 7:
          { // violation 'should be on the previous line'
            break;
          }
        case 8:
          { // violation 'should be on the previous line'
            return;
          }
        case 9:
          { // violation 'should be on the previous line'
            throw new RuntimeException("");
          }
        case 10:
          { // violation 'should be on the previous line'
            continue;
          }
        case 11:
          { // violation 'should be on the previous line'
            i++;
          }
        case 12: // violation 'Fall through from previous branch of the switch statement.'
          if (false) {
            break;
          } else {
            break;
          }
        case 13:
          if (true) {
            return;
          }
        case 14: // violation 'Fall through from previous branch of the switch statement.'
          if (true) {
            return;
          } else {
            // do nothing
          }
        case 15: // violation 'Fall through from previous branch of the switch statement.'
          do {
            System.identityHashCode("something");
            return;
          } while (true);
        case 16:
          for (int j1 = 0; j1 < 10; j1++) {
            System.identityHashCode("something");
            return;
          }
        case 17:
          while (true) {
            throw new RuntimeException("");
          }
        case 18:
          while (cond) {
            break;
          }
        case 19: // violation 'Fall through from previous branch of the switch statement.'
          try {
            i++;
            break;
          } catch (RuntimeException e) {
            break;
          } catch (Error e) {
            return;
          }
        case 20:
          try {
            i++;
            break;
          } catch (RuntimeException e) {
            // do nothing
          } catch (Error e) {
            return;
          }
        case 21: // violation 'Fall through from previous branch of the switch statement.'
          try {
            i++;
          } catch (RuntimeException e) {
            i--;
          } finally {
            break;
          }
        case 22:
          try {
            i++;
            break;
          } catch (RuntimeException e) {
            i--;
            break;
          } finally {
            i++;
          }
        case 23:
          switch (j) {
            case 1:
              continue;
            case 2:
              return;
            default:
              return;
          }
        case 24:
          switch (j) {
            case 1:
              continue;
            case 2:
              break;
            default:
              return;
          }
        default: // violation 'Fall through from previous branch of the switch statement.'
          // this is the last label
          i++;
      }
    }
  }

  /* Like above, but all fall throughs with relief comment */
  void methodFallThru(int i, int j, boolean cond) {
    while (true) {
      switch (i) {
        case -1: // FALLTHRU

        case 0: // no problem
        case 1:
          i++;
          break;
        case 2:
          i++;
          // fallthru
        case 3:
          i++;
          break;
        case 4:
          return;
        case 5:
          throw new RuntimeException("");
        case 6:
          continue;
        case 7:
          { // violation 'should be on the previous line'
            break;
          }
        case 8:
          { // violation 'should be on the previous line'
            return;
          }
        case 9:
          { // violation 'should be on the previous line'
            throw new RuntimeException("");
          }
        case 10:
          { // violation 'should be on the previous line'
            continue;
          }
        case 11:
          { // violation 'should be on the previous line'
            i++;
          }
          // fallthru
        case 12:
          if (false) {
            break;
          } else {
            break;
          }
        case 13:
          if (true) {
            return;
          }
        case 14: // violation 'Fall through from previous branch of the switch statement.'
          if (true) {
            return;
          } else {
            // do nothing
          }
          // fallthru
        case 15:
          do {
            System.identityHashCode("something");
            return;
          } while (true);
        case 16:
          for (int j1 = 0; j1 < 10; j1++) {
            System.identityHashCode("something");
            return;
          }
        case 17:
          while (cond) {
            throw new RuntimeException("");
          }
        case 18:
          while (cond) {
            break;
          }
          // fallthru
        case 19:
          try {
            i++;
            break;
          } catch (RuntimeException e) {
            break;
          } catch (Error e) {
            return;
          }
        case 20:
          try {
            i++;
            break;
          } catch (RuntimeException e) {
            return;
          } catch (Error e) {
            return;
          }
          // fallthru
        case 21:
          try {
            i++;
          } catch (RuntimeException e) {
            i--;
          } finally {
            break;
          }
        case 22:
          try {
            i++;
            break;
          } catch (RuntimeException e) {
            i--;
            break;
          } finally {
            i++;
          }
          /* fallthru */
        case 23:
          switch (j) {
            case 1:
              continue;
            case 2:
              return;
            default:
              return;
          }
        case 24:
          i++;
          // violation below 'incorrect indentation level 10, expected level should be 8'
          /* fallthru */ case 25:
          i++;
          break;

        case 26:
          switch (j) {
            case 1:
              continue;
            case 2:
              break;
            default:
              return;
          }
          // fallthru
        default:
          // this is the last label
          i++;
          // fallthru
      }
    }
  }

  /* Test relief comment. */
  void methodFallThruCc(int i, int j, boolean cond) {
    while (true) {
      switch (i) { // violation 'switch without "default" clause.'
        case 0:
          i++; // fallthru

        case 1:
          i++;
          // fallthru
        case 2:
          { // violation 'should be on the previous line'
            i++;
          }
          // fallthru
        case 3:
          i++;
          // violation below 'incorrect indentation level 10, expected level should be 8'
          /* fallthru */ case 4:
          break;
        case 5:
          i++;
          // fallthru
      }
    }
  }

  /* Like above, but C-style comments. */
  void methodFallThruC(int i, int j, boolean cond) {
    while (true) {
      switch (i) { // violation 'switch without "default" clause.'
        case 0:
          i++; /* fallthru */

        case 1:
          i++;
          /* fallthru */
        case 2:
          i++;
          // violation below 'incorrect indentation level 10, expected level should be 8'
          /* fallthru */ case 3:
          break;
        case 4:
          i++;
          /* fallthru */
      }
    }
  }

  /* Like above, but C-style comments with no spaces. */
  void methodFallThruC2(int i, int j, boolean cond) {
    while (true) {
      switch (i) { // violation 'switch without "default" clause.'
        case 0:
          i++; /*fallthru*/

        case 1:
          i++;
          /*fallthru*/
        case 2:
          i++;
          // violation below 'incorrect indentation level'
          /*fallthru*/ case 3:
          break;
        case 4:
          i++;
          /*fallthru*/
      }
    }
  }

  /* C-style comments with other default fallthru-comment. */
  void methodFallThruOtherWords(int i, int j, boolean cond) {
    while (true) {
      switch (i) { // violation 'switch without "default" clause.'
        case 0:
          i++; /* falls through */

        case 1:
          i++;
          /* falls through */
        case 2:
          i++;
          // violation below 'incorrect indentation level'
          /* falls through */ case 3:
          break;
        case 4:
          i++;
          /* falls through */
      }
    }
  }

  /* C-style comments with custom fallthru-comment. */
  void methodFallThruCustomWords(int i, int j, boolean cond) {
    while (true) {
      switch (i) { // violation 'switch without "default" clause.'
        case 0:
          i++; /* Continue with next case */

        case 1: // violation 'Fall through from previous branch of the switch statement.'
          i++;
          /* Continue with next case.  */
        case 2: // violation 'Fall through from previous branch of the switch statement.'
          i++;
          /* Continue with next case. */ case 3:
          // 2 violations above:
          //   'Fall through from previous branch of the switch statement.'
          //   'incorrect indentation level'
          break;
        case 4:
          i++;
          /* Continue with next case */
      }
    }
  }

  void methodFallThruLastCaseGroup(int i, int j, boolean cond) {
    while (true) {
      switch (i) { // violation 'switch without "default" clause.'
        case 0:
          i++; // fallthru
      }
      switch (i) { // violation 'switch without "default" clause.'
        case 0:
          i++;
          // fallthru
      }
      switch (i) { // violation 'switch without "default" clause.'
        case 0:
          i++;
          /* fallthru */
      }
    }
  }

  void method1472228(int i) {
    switch (i) {
      case 2:
        // do nothing
        break;
      default:
    }
  }

  /* Test relief comment. */
  void methodFallThruWithDash(int i, int j, boolean cond) {
    while (true) {
      switch (i) {
        case 0:
          i++; // fallthru
        case 1:
          i++; // fall thru
        case 2:
          i++; // fall-thru
        case 3:
          i++; // fallthrough
        case 4:
          i++; // fall through
        case 5:
          i++; // fall-through
        case 6:
          i++; // fallsthru
        case 7:
          i++; // falls thru
        case 8:
          i++; // falls-thru
        case 9:
          i++; // fallsthrough
        case 10:
          i++; // falls through
        case 11:
          i++; // falls-through
        case 12:
          i++; // fall--through
        case 13: // violation 'Fall through from previous branch of the switch statement.'
          i++; // fall+through
        case 14: // violation 'Fall through from previous branch of the switch statement.'
          i++; // falls_thru
        case 15: // violation 'Fall through from previous branch of the switch statement.'
          i++; // falls=through
        case 16: // violation 'Fall through from previous branch of the switch statement.'
          i++; // falls-throug
        default: // violation 'Fall through from previous branch of the switch statement.'
          throw new RuntimeException();
      }
    }
  }
}
