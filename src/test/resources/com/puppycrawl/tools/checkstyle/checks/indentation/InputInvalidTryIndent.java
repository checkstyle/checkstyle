package com.puppycrawl.tools.checkstyle.checks.indentation; //indent:0 exp:0

/**                                                                           //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 4                                                        //indent:1 exp:1
 * basicOffset = 4                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * caseIndent = 4                                                             //indent:1 exp:1
 * forceStrictCondition = false                                               //indent:1 exp:1
 * lineWrappingIndentation = 4                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 4                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * @author  jrichard                                                          //indent:1 exp:1
 */                                                                           //indent:1 exp:1
public class InputInvalidTryIndent { //indent:0 exp:0

    /** Creates a new instance of InputInvalidTryIndent */ //indent:4 exp:4
    public InputInvalidTryIndent() { //indent:4 exp:4
    } //indent:4 exp:4

    public void method() { //indent:4 exp:4

         try { //indent:9 exp:8 warn
       } catch (Throwable t) { //indent:7 exp:8 warn
            System.out.println("err"); //indent:12 exp:12
       } //indent:7 exp:8 warn

    try { //indent:4 exp:8 warn
        System.out.println("test"); //indent:8 exp:12 warn
    } finally { //indent:4 exp:8 warn
        System.out.println("finally"); //indent:8 exp:12 warn
        } //indent:8 exp:8

        try { //indent:8 exp:8
        } catch (Throwable t) { //indent:8 exp:8
        System.out.println("err"); //indent:8 exp:12 warn
        } finally { //indent:8 exp:8
        } //indent:8 exp:8

        try { //indent:8 exp:8
          } catch (Exception t) { //indent:10 exp:8 warn
            System.out.println("err"); //indent:12 exp:12
      } catch (Throwable t) { //indent:6 exp:8 warn
            System.out.println("err"); //indent:12 exp:12
        } //indent:8 exp:8

        try { //indent:8 exp:8
        } catch (Exception t) { //indent:8 exp:8
        } catch (Throwable t) { //indent:8 exp:8
     } //indent:5 exp:8 warn


        try { //indent:8 exp:8
            System.out.println("try"); //indent:12 exp:12
        }  //indent:8 exp:8
        catch (Exception t) { //indent:8 exp:8
          System.out.println("err"); //indent:10 exp:12 warn
              System.out.println("err"); //indent:14 exp:12 warn
          System.out.println("err"); //indent:10 exp:12 warn
        }  //indent:8 exp:8
      catch (Throwable t) { //indent:6 exp:8 warn
            System.out.println("err"); //indent:12 exp:12
        }  //indent:8 exp:8
        finally { //indent:8 exp:8
        } //indent:8 exp:8

        try  //indent:8 exp:8
          { //indent:10 exp:8 warn
            System.out.println("try"); //indent:12 exp:12
          }  //indent:10 exp:8 warn
        catch (Exception t)  //indent:8 exp:8
      { //indent:6 exp:8 warn
            System.out.println("err"); //indent:12 exp:12
            System.out.println("err"); //indent:12 exp:12
          }  //indent:10 exp:8 warn
        catch (Throwable t)  //indent:8 exp:8
        { //indent:8 exp:8
          System.out.println("err"); //indent:10 exp:12 warn
        } //indent:8 exp:8
        finally  //indent:8 exp:8
        { //indent:8 exp:8
        } //indent:8 exp:8


    } //indent:4 exp:4
} //indent:0 exp:0
