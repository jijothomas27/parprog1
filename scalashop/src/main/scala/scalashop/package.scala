
import common._

package object scalashop {

  /** The value of every pixel is represented as a 32 bit integer. */
  type RGBA = Int

  type Pixel = (Int,Int,Int,Int)
  type Point = (Int,Int)

  /** Returns the red component. */
  def red(c: RGBA): Int = (0xff000000 & c) >>> 24

  /** Returns the green component. */
  def green(c: RGBA): Int = (0x00ff0000 & c) >>> 16

  /** Returns the blue component. */
  def blue(c: RGBA): Int = (0x0000ff00 & c) >>> 8

  /** Returns the alpha component. */
  def alpha(c: RGBA): Int = (0x000000ff & c) >>> 0

  /** Used to create an RGBA value from separate components. */
  def rgba(r: Int, g: Int, b: Int, a: Int): RGBA = {
    (r << 24) | (g << 16) | (b << 8) | (a << 0)
  }

  /** Restricts the integer into the specified range. */
  def clamp(v: Int, min: Int, max: Int): Int = {
    if (v < min) min
    else if (v > max) max
    else v
  }

  /** Image is a two-dimensional matrix of pixel values. */
  class Img(val width: Int, val height: Int, private val data: Array[RGBA]) {
    def this(w: Int, h: Int) = this(w, h, new Array(w * h))
    def apply(x: Int, y: Int): RGBA = data(y * width + x)
    def update(x: Int, y: Int, c: RGBA): Unit = data(y * width + x) = c
  }

  /** Computes the blurred RGBA value of a single pixel of the input image. */
  def boxBlurKernel(src: Img, x: Int, y: Int, radius: Int): RGBA = {
    def accumulate (acc:Pixel,point:Point):Pixel =
      (acc._1 + red(src(point._1,point._2)),acc._2 + green(src(point._1,point._2)),acc._3 + blue(src(point._1,point._2)),acc._4 + alpha(src(point._1,point._2)))

    val points:Seq[Point] = for {
      px <-  Range(x-radius,x+radius+1)
      py <- Range(y-radius,y+radius+1)
    } yield (clamp(px,0,src.width),clamp(py,0,src.height))

    val avg:Pixel = points.foldLeft((0,0,0,0)) (accumulate)
    rgba(avg._1/points.length,avg._2/points.length,avg._3/points.length,avg._4/points.length)
  }

}
