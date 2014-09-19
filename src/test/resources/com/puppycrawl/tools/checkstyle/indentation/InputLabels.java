package InputLabels; // indent:0 ; exp:0; ok

class InputLabels { // indent:0 ; exp:0; ok
  void foo() { // indent:2 ; exp:2; ok
//    OUT: // indent:4 ; exp:4; ok
    while (true) { // indent:4 ; exp:4; ok
    } // indent:4 ; exp:4; ok
  } // indent:2 ; exp:2; ok
  
  void foo2() { // indent:2 ; exp:2; ok
    positions: while (true) { // indent:4 ; exp:4; ok
    } // indent:4 ; exp:4; ok
  } // indent:2 ; exp:2; ok
  
  void foo3() { // indent:2 ; exp:2; ok
    OUT1: // indent:4 ; exp:4; warn
    for (;;) { // indent:4 ; exp:4; ok
      if (true){ // indent:6 ; exp:6; ok
        break OUT1; // indent:8 ; exp:8; ok
      } // indent:6 ; exp:6; ok
    } // indent:4 ; exp:4; ok
  } // indent:2 ; exp:2; ok
  
  void foo4() { // indent:2 ; exp:2; ok
    OUT1: for (;;) { // indent:4 ; exp:4; ok
      if (true){ // indent:6 ; exp:6; ok
        break OUT1; // indent:8 ; exp:8; ok
      } // indent:6 ; exp:6; ok
    } // indent:4 ; exp:4; ok
  } // indent:2 ; exp:2; ok
  
  void fooo() { // indent:2 ; exp:2; ok
    IN: if (true) { // indent:4 ; exp:4; ok
    } // indent:4 ; exp:4; ok
  } // indent:2 ; exp:2; ok
  
  void fooo1() { // indent:2 ; exp:2; ok
    IN: // indent:4 ; exp:4; ok
    if (true) { // indent:4 ; exp:4; ok
    } // indent:4 ; exp:4; ok
  } // indent:2 ; exp:2; ok
  
  void foooo() { // indent:2 ; exp:2; ok
    IN: do {} while (true); // indent:4 ; exp:4; ok
  } // indent:2 ; exp:2; ok
  
  void foooo1() { // indent:2 ; exp:2; ok
    IN: // indent:4 ; exp:4; ok
    do {} while (true); // indent:4 ; exp:4; ok
  } // indent:2 ; exp:2; ok

  class Inner { // indent:2 ; exp:2; ok
    void foo() { // indent:4 ; exp:4; ok
      OUT: while (true) { // indent:6 ; exp:6; ok
      } // indent:6 ; exp:6; ok
    } // indent:4 ; exp:4; ok

    void foo2() { // indent:4 ; exp:4; ok
      positions: // indent:6 ; exp:6; ok
      while (true) { // indent:6 ; exp:6; ok
      } // indent:6 ; exp:6; ok
    } // indent:4 ; exp:4; ok
    
    void foo5() { // indent:4 ; exp:4; ok
      OUT1: // indent:6 ; exp:6; ok
      for (;;) { // indent:6 ; exp:6; ok
        if (true){ // indent:8 ; exp:8; ok
          break OUT1; // indent:10 ; exp:10; ok
        } // indent:8 ; exp:8; ok
      } // indent:6 ; exp:6; ok
    } // indent:4 ; exp:4; ok
    
    void foo6() { // indent:4 ; exp:4; ok
      OUT1: for (;;) { // indent:6 ; exp:6; ok
        if (true){ // indent:8 ; exp:8; ok
          break OUT1; // indent:10 ; exp:10; ok
        } // indent:8 ; exp:8; ok
      } // indent:6 ; exp:6; ok
    } // indent:4 ; exp:4; ok
    
    void fooo11() { // indent:4 ; exp:4; ok
      IN: if (true) { // indent:6 ; exp:6; ok
      } // indent:6 ; exp:6; ok
    } // indent:4 ; exp:4; ok
    
    void fooo12() { // indent:4 ; exp:4; ok
      IN: // indent:6 ; exp:6; ok
      if (true) { // indent:6 ; exp:6; ok
      } // indent:6 ; exp:6; ok
    } // indent:4 ; exp:4; ok
    
    void foooo3() { // indent:4 ; exp:4; ok
      IN: do {} while (true); // indent:6 ; exp:6; ok
    } // indent:4 ; exp:4; ok
      
    void foooo4() { // indent:4 ; exp:4; ok
      IN: // indent:6 ; exp:6; ok
      do {} while (true); // indent:6 ; exp:6; ok
    } // indent:4 ; exp:4; ok
  } // indent:2 ; exp:2; ok
  
  InputLabels anon = new InputLabels() { // indent:2 ; exp:2; ok
    void foo() { // indent:4 ; exp:4; ok
      OUT: while (true) { // indent:6 ; exp:6; ok
      } // indent:6 ; exp:6; ok
    } // indent:4 ; exp:4; ok

    void foo2() { // indent:4 ; exp:4; ok
      positions:
      while (true) { // indent:6 ; exp:6; ok
      } // indent:6 ; exp:6; ok
    } // indent:4 ; exp:4; ok
    
    void foo5() { // indent:4 ; exp:4; ok
      OUT1: // indent:6 ; exp:6; ok
      for (;;) { // indent:6 ; exp:6; ok
        if (true){ // indent:8 ; exp:8; ok
          break OUT1; // indent:10 ; exp:10; ok
        } // indent:8 ; exp:8; ok
      } // indent:6 ; exp:6; ok
    } // indent:4 ; exp:4; ok
      
    void foo6() { // indent:4 ; exp:4; ok
      OUT1: for (;;) { // indent:6 ; exp:6; ok
        if (true){ // indent:8 ; exp:8; ok
          break OUT1; // indent:10 ; exp:10; ok
        } // indent:8 ; exp:8; ok
      } // indent:6 ; exp:6; ok
    } // indent:4 ; exp:4; ok
      
    void fooo11() { // indent:4 ; exp:4; ok
      IN: if (true) { // indent:6 ; exp:6; ok
      } // indent:6 ; exp:6; ok
    } // indent:4 ; exp:4; ok
      
    void fooo12() { // indent:4 ; exp:4; ok
      IN: // indent:6 ; exp:6; ok
      if (true) { // indent:6 ; exp:6; ok
      } // indent:6 ; exp:6; ok
    } // indent:4 ; exp:4; ok
    
    void foooo3() { // indent:4 ; exp:4; ok
      IN: do {} while (true); // indent:6 ; exp:6; ok
    } // indent:4 ; exp:4; ok
        
    void foooo4() { // indent:4 ; exp:4; ok
      IN: // indent:6 ; exp:6; ok
      do {} while (true); // indent:6 ; exp:6; ok
    } // indent:4 ; exp:4; ok
  }; // indent:2 ; exp:2; ok
} // indent:0 ; exp:0; ok