def factors(money:Int,coin:Int):List[Int] = (for (x <- 1 to money/coin ) yield List.fill(x)(coin).sum).toList
val money = 4
val coins = List(1,2,3)
coins.map(factors(4,_))