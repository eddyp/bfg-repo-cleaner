/*
 * Copyright (c) 2012 Roberto Tyley
 *
 * This file is part of 'BFG Repo-Cleaner' - a tool for removing large
 * or troublesome blobs from Git repositories.
 *
 * BFG Repo-Cleaner is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BFG Repo-Cleaner is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/ .
 */

package com.madgag.git.bfg.model

import org.eclipse.jgit.lib.Constants
import org.eclipse.jgit.util.RawParseUtils

object FileName {

  object ImplicitConversions {
    import language.implicitConversions

    implicit def string2FileName(str: String) = new FileName(Constants.encode(str))

    implicit def filename2String(fileName: FileName) = fileName.string
  }

  def apply(name: String): FileName = {
    require(!name.contains('/'), "File names can not contain '/'.")
    new FileName(Constants.encode(name))
  }
}

class FileName(val bytes: Array[Byte]) {

  override def equals(that: Any): Boolean = that match {
    case that: FileName => java.util.Arrays.equals(bytes, that.bytes)
    case _ => false
  }

  override lazy val hashCode: Int = java.util.Arrays.hashCode(bytes)

  lazy val string = RawParseUtils.decode(bytes)

  override def toString = string

}