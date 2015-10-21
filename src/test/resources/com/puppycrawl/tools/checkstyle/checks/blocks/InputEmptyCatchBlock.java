////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.blocks;
import java.io.IOException;
public class InputEmptyCatchBlock
{
    
    private void foo() {
        try {
            throw new RuntimeException();
        } catch (Exception expected) {
            //Expected
        }
    }
    
    private void foo1() {
        try {
            throw new RuntimeException();
        } catch (Exception e) {}
        
    }
    
    private void foo2() {
        try {
            throw new IOException();
        } catch (IOException | NullPointerException | ArithmeticException ignore) {
        }
    }
    
    private void foo3() { // comment
        try {
            throw new IOException();
        } catch (IOException | NullPointerException | ArithmeticException e) { //This is expected
        }
    }
    
    private void foo4() {
        try {
            throw new IOException();
        } catch (IOException | NullPointerException | ArithmeticException e) { /* This is expected*/
        }
    }
    
    private void foo5() {
        try {
            throw new IOException();
        } catch (IOException | NullPointerException | ArithmeticException e) { // Some singleline comment
        }
    }
    
    private void foo6() {
        try {
            throw new IOException();
        } catch (IOException expected) { // This is expected
            int k = 0;
        }
    }
    
    public void testTryCatch()
    {
        try {
            int y=0;
            int u=8;
            int e=u-y;
            return; 
        } 
        catch (Exception e) {
            System.out.println(e);
            return; 
        }
        finally
        {
            return; 
        }
    }
    
    public void testTryCatch2()
    {
        try {
        } 
        catch (Exception e) { //OK
            //This is expected
            /* This is expected */
            /**This is expected */
        }
        finally
        {
        }
    }
    
    public void testTryCatch3()
    {
        try {
            int y=0;
            int u=8;
            int e=u-y;
        } 
        catch (IllegalArgumentException e) {
            System.out.println(e); //some comment
            return; 
        }
        catch (IllegalStateException ex) {
                System.out.println(ex);
                return; 
        }
    }
    
    public void testTryCatch4()
    {
        int y=0;
        int u=8;
        try {
            int e=u-y;
        } 
        catch (IllegalArgumentException e) {
            System.out.println(e);
            return; 
        }
    }
    public void setFormats() {
        try {
            int k = 4;
        } catch (Exception e) {
            Object k = null;
            if (k != null)
                k = "ss";
            else {
                return; 
            }
        }
    }
    public void setFormats1() {
        try {
            int k = 4;
        } catch (Exception e) {
            Object k = null;
            if (k != null) {
                k = "ss";
            } else {
                return; 
            }
        }
    }
    public void setFormats2() {
        try {
            int k = 4;
        } catch (Exception e) {
            Object k = null;
            if (k != null) {
                k = "ss";
                return;
            } 
        }
    }
    public void setFormats3() {
        try {
            int k = 4;
        } catch (Exception e) {
            Object k = null;
            if (k != null) {
                k = "ss";
                
            } 
        }
    }
    
    private void some() {
        try {
            throw new IOException();
        } catch (IOException e) {
            /* ololo
             * blalba
             */
        }
    }
    private void some1() {
        try {
            throw new IOException();
        } catch (IOException e) {
            /* lalala
             * This is expected
             */
        }
    }
    private void some2() {
        try {
            throw new IOException();
        } catch (IOException e) {
            /*
             * This is expected
             * lalala
             */
        }
    }
    private void some3() {
        try {
            throw new IOException();
        } catch (IOException e) {
            // some comment
            //This is expected
        }
    }
    private void some4() {
        try {
            throw new IOException();
        } catch (IOException e) {
            //This is expected
            // some comment
        }
    }
    private void some5() {
        try {
            throw new IOException();
        } catch (IOException e) {
            /* some comment */
            //This is expected
        }
    }
    
    private void emptyMultilineComent() {
        try {
            throw new IOException();
        } catch (IOException e) {
            /*
*/
        }
    }
}
