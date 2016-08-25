package reductions

import scala.annotation._
import org.scalameter._
import common._

object ParallelParenthesesBalancingRunner {

  @volatile var seqResult = false

  @volatile var parResult = false

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 40,
    Key.exec.maxWarmupRuns -> 80,
    Key.exec.benchRuns -> 120,
    Key.verbose -> true
  ) withWarmer(new Warmer.Default)

  def main(args: Array[String]): Unit = {
    val length = 100000000
    val chars = new Array[Char](length)
    val threshold = 10000
    val seqtime = standardConfig measure {
      seqResult = ParallelParenthesesBalancing.balance(chars)
    }
    println(s"sequential result = $seqResult")
    println(s"sequential balancing time: $seqtime ms")

    val fjtime = standardConfig measure {
      parResult = ParallelParenthesesBalancing.parBalance(chars, threshold)
    }
    println(s"parallel result = $parResult")
    println(s"parallel balancing time: $fjtime ms")
    println(s"speedup: ${seqtime / fjtime}")
  }
}

object ParallelParenthesesBalancing {

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
   */
  def balance(chars: Array[Char]): Boolean =
    chars.foldLeft(0){(acc:Int,c:Char) => c match {
      case '(' if (acc >=0) => acc + 1
      case ')' => acc - 1
      case _ => acc
    }} == 0

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
   */
  def parBalance(chars: Array[Char], threshold: Int): Boolean = {

    def traverse(idx: Int, until: Int, openBracketsLeft: Int, closedBracketsLeft: Int) : (Int,Int) = {
      if (idx>=until) (openBracketsLeft,closedBracketsLeft)
      else {
        chars(idx) match {
          case '(' => traverse(idx+1,until,openBracketsLeft+1,closedBracketsLeft)
          case ')' =>
            if (openBracketsLeft>0) traverse(idx+1,until,openBracketsLeft-1,closedBracketsLeft)
            else traverse(idx+1,until,openBracketsLeft,closedBracketsLeft-1)
          case _ => traverse(idx+1,until,openBracketsLeft,closedBracketsLeft)
        }
      }
    }

    def reduce(from: Int, until: Int) :Int = {
      if (until-from < threshold) {
        0
      } else {
        val mid = (from+until)/2
        val (left,right) = parallel(reduce(from,mid),reduce(mid,until))
        left + right
      }
    }

    reduce(0, chars.length) == ???
  }

  // For those who want more:
  // Prove that your reduction operator is associative!

}
