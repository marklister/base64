package com.github.marklister.base64

import scala.scalajs.js.JSApp

object RunMicroBench extends JSApp{
  def main () = Benchmark.run(Array.empty)
}
