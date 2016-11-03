package com.github.marklister.base64

import utest.TestSuite

object RunMicroBench extends TestSuite{
  val tests= TestSuite{
    Benchmark.run(Array.empty)

  }
}
