"())(".toList.foldLeft(0){(acc:Int,c:Char) => c match {
  case '(' if (acc < 0) =>  acc + 1
  case ')' => acc -1
  case _ => acc
}}

def balance (chars:List[Char]):Int =
  chars.foldLeft(0){(acc:Int,c:Char) =>  c match {
    case '(' if (acc >=0) => acc + 1
    case ')' => acc - 1
    case _ => acc
  }}

val strings = List("(if (zero? x) max (/ 1 x))",
  "I told him (that it's not (yet) done). (But he wasn't listening)",
  "(o_()",
  ":-)",
  "(()()                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ")

strings.map(chars => balance(chars.toList))