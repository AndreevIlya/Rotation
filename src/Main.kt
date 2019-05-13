fun main(args: Array<String>){

    val variable1 = Variable('a',"1")
    val variable2 = Variable('a',"2")
    val variable3 = Variable('a',"3")
    val variable4 = Variable('a',"4")

    val mono1 = AlgebraicMonomial(variable1)
    val mono2 = AlgebraicMonomial(variable2)
    val mono3 = AlgebraicMonomial(variable3)
    val mono4 = AlgebraicMonomial(variable4)

    mono1 mult mono2
    mono2 mult mono1
    mono1 sum mono2
    mono1 mult mono3
    mono1 mult mono3

    println(mono1.toString())
}