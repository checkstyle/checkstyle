
// Transmogrify License
// 
// Copyright (c) 2001, ThoughtWorks, Inc.
// All rights reserved.
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions
// are met:
// - Redistributions of source code must retain the above copyright notice,
//   this list of conditions and the following disclaimer.
// - Redistributions in binary form must reproduce the above copyright
// notice, this list of conditions and the following disclaimer in the
// documentation and/or other materials provided with the distribution.
// Neither the name of the ThoughtWorks, Inc. nor the names of its
// contributors may be used to endorse or promote products derived from this
// software without specific prior written permission.
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
// "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
// TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
// PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
// CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
// EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
// PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
// OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
// WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
// OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
// ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

package com.puppycrawl.tools.checkstyle.checks.usage.transmogrify;

import java.io.File;




/**
 * <code>Occurrence</code> contains file and line number information.
 * It is used to denote the location of various <code>Definintion</code>s
 * and <code>Reference</code>s
 *
 * @see Reference
 * @see Definition
 */

public class Occurrence implements Comparable {
  private File _file;
  private int _line;
  private int _column;

  public Occurrence(File file, int line, int column) {
    _file = file;
    _line = line;
    _column = column;
  }

  public Occurrence(SymTabAST node) {
    _file = node.getFile();
    _line = node.getLineNo();
    _column = node.getColumnNo();
  }

  /**
   * returns the File of this <code>Occurrence</code>
   *
   * @return File
   */
  public File getFile() {
    return _file;
  }

  /**
   * returns the line number of this <code>Occurrence</code>
   *
   * @return the line number of this <code>Occurrence</code>
   */
  public int getLine() {
    return _line;
  }

  /**
   * returns the column that this token starts at
   *
   * @return the column that this token starts at
   */
  public int getColumn() {
    return _column;
  }

  public int compareTo(Object o) {
    if (!(o instanceof Occurrence)) {
      throw new ClassCastException(getClass().getName());
    }

    Occurrence other = (Occurrence)o;

    int result = 0;

    result = getFile().compareTo(other.getFile());

    if (result == 0) {
      result = getLine() - other.getLine();
    }
    if (result == 0) {
      result = getColumn() - other.getColumn();
    }

    return result;
  }

  public boolean equals(Object o) {
    boolean result = false;
    
    if (o instanceof Occurrence) {
      Occurrence occ = (Occurrence)o;
      result = (getFile().equals(occ.getFile())
                && getLine() == occ.getLine()
                && getColumn() == occ.getColumn());
    }

    return result;
  }

  public int hashCode() {
    return getFile().hashCode();
  }

  public String toString() {
    return "[" + getFile() + ":" + getLine() + "," + getColumn() + "]";
  }

}
