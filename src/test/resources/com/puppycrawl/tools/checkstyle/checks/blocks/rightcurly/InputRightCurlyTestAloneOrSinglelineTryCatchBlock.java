/*
RightCurly
option = ALONE_OR_SINGLELINE
tokens = LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

import java.io.BufferedReader;
import java.io.IOException;

public class InputRightCurlyTestAloneOrSinglelineTryCatchBlock {

     private void foo() {

            try {
                int i = 5; int b = 10;
            }
            catch (Exception e) { }
     }
     private void testSingleLineTryBlock() {
         try { } catch (Exception e) { }
         try {int x = 5;} catch (RuntimeException e) { } catch (Exception e) { }
         try { } catch (RuntimeException e) { } catch (Exception e) { } finally { foo();}
         try (BufferedReader br1 = new BufferedReader(null)) {} catch (IOException e) { ; }
    }

}
