package com.google.checkstyle.test.chapter4formatting.rule4842fallthrow;

public class FallThroughInput
{
    void method(int i, int j, boolean cond) {
        while (true) {
            switch (i) {
            case 0: // no problem
            case 1:
                i++;
                break;
            case 2:
                i++;
            case 3: //warn
                i++;
                break;
            case 4:
                return;
            case 5:
                throw new RuntimeException("");
            case 6:
                continue;
            case 7: {
                break;
            }
            case 8: {
                return;
            }
            case 9: {
                throw new RuntimeException("");
            }
            case 10: {
                continue;
            }
            case 11: {
                i++;
            }
            case 12:  //warn
                if (false)
                    break;
                else
                    break;
            case 13:
                if (true) {
                    return;
                }
            case 14:
                if (true) {
                    return;
                } else {
                    //do nothing
                }
            case 15:  //warn
                do {
                    System.out.println("something");
                    return;
                } while(true);
            case 16:
                for (int j1 = 0; j1 < 10; j1++) {
                    System.err.println("something");
                    return;
                }
            case 17:
                while (true)
                    throw new RuntimeException("");
            case 18:
                while(cond) {
                    break;
                }
            case 19:  //warn
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
                } catch (Error e) {
                    return;
                }
            case 21:  //warn
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
            case 23:  //warn
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
            default:  //warn
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
          case 7: {
              break;
          }
          case 8: {
              return;
          }
          case 9: {
              throw new RuntimeException("");
          }
          case 10: {
              continue;
          }
          case 11: {
              i++;
          }
          // fallthru
          case 12: 
              if (false)
                  break;
              else
                  break;
          case 13:
              if (true) {
                  return;
              }
          case 14:
              if (true) {
                  return;
              } else {
                  //do nothing
              }
              // fallthru
          case 15:
              do {
                  System.out.println("something");
                  return;
              } while(true);
          case 16:
              for (int j1 = 0; j1 < 10; j1++) {
                  System.err.println("something");
                  return;
              }
          case 17:
              while (cond)
                  throw new RuntimeException("");
          case 18:
              while(cond) {
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
   void methodFallThruCC(int i, int j, boolean cond) {
      while (true) {
          switch (i){
          case 0:
              i++; // fallthru

          case 1:
              i++;
          // fallthru
          case 2: {
              i++;
          }
          // fallthru
          case 3:
              i++;
          /* fallthru */case 4:
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
          switch (i){
          case 0:
              i++; /* fallthru */

          case 1:
              i++;
          /* fallthru */
          case 2:
              i++;
          /* fallthru */case 3:
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
          switch (i){
          case 0:
              i++; /*fallthru*/

          case 1:
              i++;
          /*fallthru*/
          case 2:
              i++;
          /*fallthru*/case 3:
                break;
          case 4:
              i++;
          /*fallthru*/
          }
      }
   }
    
   /* C-style comments with other default fallthru-comment. */
   void methodFallThruCOtherWords(int i, int j, boolean cond) {
      while (true) {
          switch (i){
          case 0:
              i++; /* falls through */

          case 1:
              i++;
          /* falls through */
          case 2:
              i++;
          /* falls through */case 3:
                break;
          case 4:
              i++;
          /* falls through */
          }
      }
   }
    
   /* C-style comments with custom fallthru-comment. */
   void methodFallThruCCustomWords(int i, int j, boolean cond) {
      while (true) {
          switch (i){
          case 0:
              i++; /* Continue with next case */

          case 1:  //warn
              i++;
          /* Continue with next case.  */
          case 2:  //warn
              i++;
          /* Continue with next case. */case 3:  //warn
                break;
          case 4:
              i++;
          /* Continue with next case */
          }
      }
   }
   
   void methodFallThruLastCaseGroup(int i, int j, boolean cond) {
       while (true) {
           switch (i){
           case 0:
               i++; // fallthru
           }
           switch (i){
           case 0:
               i++; 
               // fallthru
           }
           switch (i){
           case 0:
               i++; 
           /* fallthru */ }
       }
    }

    void method1472228(int i) {
        switch(i) {
        case 2:
            // do nothing
            break;
        default:
        }
    }
}
