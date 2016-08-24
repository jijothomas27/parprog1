import java.util.concurrent._

def scanLeft[A](in:Array[A],out:Array[A])(a:A)(f: (A,A) => A):Array[A] = {
  out(0) = a
  var i = 0;
  while (i < in.length) {
    out(i+1) = f(out(i),in(i))
    i = i + 1;
  }

  out
}

val in = Array(1,3,8)
val out = Array.fill[Int](in.length+1)(0)
scanLeft(Array(1,3,8),out)(100)((a,x) => a+x)

val threshold = 10
