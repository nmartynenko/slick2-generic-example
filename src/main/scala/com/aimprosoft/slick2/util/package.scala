package com.aimprosoft.slick2

import scala.util.Try

package object util {
  /**
   * Try enhancement's value class (by Viktor Klang)
   */
  implicit class TryOps[T](val t: Try[T]) extends AnyVal {
    def eventually[Ignore](effect: => Ignore): Try[T] = {
      val ignoring = (_: Any) => { effect; t }
      t transform (ignoring, ignoring)
    }
  }

}
