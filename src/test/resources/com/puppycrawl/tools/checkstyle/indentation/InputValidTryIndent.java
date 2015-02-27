package com.puppycrawl.tools.checkstyle.indentation; //indent:0 exp:0

/**                                                                            //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:    //indent:1 exp:1
 *                                                                             //indent:1 exp:1
 * arrayInitIndent = 4                                                         //indent:1 exp:1
 * basicOffset = 4                                                             //indent:1 exp:1
 * braceAdjustment = 0                                                         //indent:1 exp:1
 * caseIndent = 4                                                              //indent:1 exp:1
 * forceStrictCondition = false                                                //indent:1 exp:1
 * lineWrappingIndentation = 4                                                 //indent:1 exp:1
 * tabWidth = 4                                                                //indent:1 exp:1
 * throwsIndent = 4                                                            //indent:1 exp:1
 *                                                                             //indent:1 exp:1
 *  @author  jrichard                                                         //indent:1 exp:1
 */                                                                            //indent:1 exp:1
public class InputValidTryIndent { //indent:0 exp:0

    /** Creates a new instance of InputValidTryIndent */ //indent:4 exp:4
    public InputValidTryIndent() { //indent:4 exp:4
    } //indent:4 exp:4

    public void method() { //indent:4 exp:4

        try { //indent:8 exp:8
        } catch (Throwable t) { //indent:8 exp:8
            System.out.println("err"); //indent:12 exp:12
        } //indent:8 exp:8

        try { //indent:8 exp:8
            System.out.println("test"); //indent:12 exp:12
        } finally { //indent:8 exp:8
            System.out.println("finally"); //indent:12 exp:12
        } //indent:8 exp:8

        try { //indent:8 exp:8
        } catch (Throwable t) { //indent:8 exp:8
            System.out.println("err"); //indent:12 exp:12
        } finally { //indent:8 exp:8
        } //indent:8 exp:8

        try { //indent:8 exp:8
        } catch (Exception t) { //indent:8 exp:8
            System.out.println("err"); //indent:12 exp:12
        } catch (Throwable t) { //indent:8 exp:8
            System.out.println("err"); //indent:12 exp:12
        } //indent:8 exp:8

        try { //indent:8 exp:8
        } catch (Exception t) { //indent:8 exp:8
        } catch (Throwable t) { //indent:8 exp:8
        } //indent:8 exp:8


        try { //indent:8 exp:8
            System.out.println("try"); //indent:12 exp:12
        }  //indent:8 exp:8
        catch (Exception t) { //indent:8 exp:8
            System.out.println("err"); //indent:12 exp:12
            System.out.println("err"); //indent:12 exp:12
        }  //indent:8 exp:8
        catch (Throwable t) { //indent:8 exp:8
            System.out.println("err"); //indent:12 exp:12
        }  //indent:8 exp:8
        finally { //indent:8 exp:8
        } //indent:8 exp:8

        try  //indent:8 exp:8
        { //indent:8 exp:8
            System.out.println("try"); //indent:12 exp:12
        }  //indent:8 exp:8
        catch (Exception t)  //indent:8 exp:8
        { //indent:8 exp:8
            System.out.println("err"); //indent:12 exp:12
            System.out.println("err"); //indent:12 exp:12
        }  //indent:8 exp:8
        catch (Throwable t)  //indent:8 exp:8
        { //indent:8 exp:8
            System.out.println("err"); //indent:12 exp:12
        } //indent:8 exp:8
        finally  //indent:8 exp:8
        { //indent:8 exp:8
        } //indent:8 exp:8


    } //indent:4 exp:4

} //indent:0 exp:0
