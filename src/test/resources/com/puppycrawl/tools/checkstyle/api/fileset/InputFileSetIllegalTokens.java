/*xml
<module name="Checker">
 <module name="Fileset">
   <module name="Translation">
     <property name="severity" value="warning"/>
   </module>
 </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.api.fileset;

public class InputFileSetIllegalTokens
{
    public void methodWithPreviouslyIllegalTokens()
    {
        int i = 0;
        switch (i)
        {
            default:
                i--;
                i++;
                break;
        }
    }

    public native void nativeMethod();
}
