package com.puppycrawl.tools.checkstyle.usage;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;

public final class InputInnerUsedMethod
{
   public static void show()
   {
      JButton b = new JButton(new AbstractAction()
      {
         public void actionPerformed(ActionEvent e)
         {
               doSomething();
         }
      });
   }

   private static void doSomething()
   {
   }
}
