val hindi = Map ("one" -> "", "two" -> "Do","three" -> "teen")
val mal = Map("one" -> "onnu","two" -> "randu")

hindi ++ mal

val list = List(1,2,3).map( (_, List[Char]())).toMap


val both = List(1,2,3) zip List (5,6,7)

def ig (p:(Int,Int)) : Boolean = p._2 > p._1
both.forall(ig)