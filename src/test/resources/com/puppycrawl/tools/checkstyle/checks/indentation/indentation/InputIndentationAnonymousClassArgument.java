package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;      //indent:0 exp:0

/**                                                                          //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:  //indent:1 exp:1
 *                                                                           //indent:1 exp:1
 * arrayInitIndent = 2                                                       //indent:1 exp:1
 * basicOffset = 2                                                           //indent:1 exp:1
 * braceAdjustment = 0                                                       //indent:1 exp:1
 * caseIndent = 2                                                            //indent:1 exp:1
 * forceStrictCondition = false                                              //indent:1 exp:1
 * lineWrappingIndentation = 4                                               //indent:1 exp:1
 * tabWidth = 8                                                              //indent:1 exp:1
 * throwsIndent = 4                                                          //indent:1 exp:1
 */                                                                          //indent:1 exp:1
public class InputIndentationAnonymousClassArgument {                        //indent:0 exp:0

  public interface Watcher {                                                 //indent:2 exp:2
    void process(Object problem);                                            //indent:4 exp:4
  }                                                                          //indent:2 exp:2

  public InputIndentationAnonymousClassArgument(String name, int count,      //indent:2 exp:2
      Watcher watcher) {                                                     //indent:6 exp:6
  }                                                                          //indent:2 exp:2

  /** Anonymous class as an argument of a wrapped constructor call. */       //indent:2 exp:2
  public InputIndentationAnonymousClassArgument init() {                     //indent:2 exp:2
    InputIndentationAnonymousClassArgument result;                           //indent:4 exp:4
    result =                                                                 //indent:4 exp:4
        new InputIndentationAnonymousClassArgument(                          //indent:8 exp:8
            "after a wrapped assignment",                                    //indent:12 exp:12
            3334,                                                            //indent:12 exp:12
            new Watcher() {                                                  //indent:12 exp:12
              @Override                                                      //indent:14 exp:14
              public void process(Object problem) {}                         //indent:14 exp:14
            });                                                              //indent:12 exp:12

    return new InputIndentationAnonymousClassArgument(                       //indent:4 exp:4
        "in a wrapped return",                                               //indent:8 exp:8
        3334,                                                                //indent:8 exp:8
        new Watcher() {                                                      //indent:8 exp:8
          @Override                                                          //indent:10 exp:10
          public void process(Object problem) {}                             //indent:10 exp:10
        });                                                                  //indent:8 exp:8
  }                                                                          //indent:2 exp:2
}                                                                            //indent:0 exp:0
