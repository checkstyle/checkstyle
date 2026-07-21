package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;         //indent:0 exp:0
/**                                                                             //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:     //indent:1 exp:1
 *                                                                              //indent:1 exp:1
 * arrayInitIndent = 4                                                          //indent:1 exp:1
 * basicOffset = 4                                                              //indent:1 exp:1
 * braceAdjustment = 0                                                          //indent:1 exp:1
 * caseIndent = 4                                                               //indent:1 exp:1
 * forceStrictCondition = false                                                 //indent:1 exp:1
 * lineWrappingIndentation = 4                                                  //indent:1 exp:1
 * tabWidth = 4                                                                 //indent:1 exp:1
 * throwsIndent = 4                                                             //indent:1 exp:1
 *                                                                              //indent:1 exp:1
 */                                                                             //indent:1 exp:1
public class InputIndentationTryChildOnSameLine {                               //indent:0 exp:0
    void method() {                                                             //indent:4 exp:4
        try { return;                                                           //indent:8 exp:8
        }                                                                       //indent:8 exp:8
        catch (Exception e) { return;                                           //indent:8 exp:8
        }                                                                       //indent:8 exp:8
    }                                                                           //indent:4 exp:4
    void method2() {                                                            //indent:4 exp:4
        try { doSomething();                                                    //indent:8 exp:8
        }                                                                       //indent:8 exp:8
        catch (RuntimeException e) { handle(e);                                 //indent:8 exp:8
        }                                                                       //indent:8 exp:8
        finally { cleanup();                                                    //indent:8 exp:8
        }                                                                       //indent:8 exp:8
    }                                                                           //indent:4 exp:4
    void method3() {                                                            //indent:4 exp:4
        try { invokeAndWait(new Runnable() { @Override                          //indent:8 exp:8
            public void run() { doSomething(); }                                //indent:12 exp:12
        }, c); } catch (Exception e) { e.printStackTrace(); }                   //indent:8 exp:8
    }                                                                           //indent:4 exp:4
    void method4() {                                                            //indent:4 exp:4
        switch ("x") {                                                          //indent:8 exp:8
            case "8": try { break;                                              //indent:12 exp:12
                } catch (RuntimeException e) {                                  //indent:16 exp:16
                    handle(e);                                                  //indent:20 exp:20
                } finally {                                                     //indent:16 exp:16
                    cleanup();                                                  //indent:20 exp:20
                }                                                               //indent:16 exp:16
                break;                                                          //indent:16 exp:16
            default:                                                            //indent:12 exp:12
                break;                                                          //indent:16 exp:16
        }                                                                       //indent:8 exp:8
    }                                                                           //indent:4 exp:4
    void method5() {                                                            //indent:4 exp:4
        label:                                                                  //indent:8 exp:8,12
        try                                                                     //indent:8 exp:8,12
        {                                                                       //indent:8 exp:8
            return;                                                             //indent:12 exp:12
        }                                                                       //indent:8 exp:8
        catch (RuntimeException e) {                                            //indent:8 exp:8
            handle(e);                                                          //indent:12 exp:12
        }                                                                       //indent:8 exp:8
    }                                                                           //indent:4 exp:4
    private void doSomething() {}                                               //indent:4 exp:4
    private void handle(Exception e) {}                                         //indent:4 exp:4
    private void cleanup() {}                                                   //indent:4 exp:4
    Object c;                                                                   //indent:4 exp:4
    void invokeAndWait(Runnable r, Object o) {}                                 //indent:4 exp:4
}                                                                               //indent:0 exp:0
