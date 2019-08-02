fun main(args: Array<String>){

    val variable1 = Variable('a',"1")
    val variable2 = Variable('a',"2")
    val variable3 = Variable('a',"3")
    val variable4 = Variable('a',"4")

    var mono1 = AlgebraicMonomial(variable1)
    var mono2 = AlgebraicMonomial(variable2)
    val mono3 = AlgebraicMonomial(variable3)
    val mono4 = AlgebraicMonomial(variable4)

    mono1 = mono1 * mono2 * mono3
    mono1 += mono1
    mono2 = mono2 * mono3 * mono4

    println(mono1.toString())
    println(mono2.toString() + '\n')

    var poly1 = AlgebraicPolynom(mono1)
    var poly2 = AlgebraicPolynom(mono2)
    var poly3 = AlgebraicPolynom(mono3)
    var poly4 = AlgebraicPolynom(mono4)

    poly1 += poly2
    poly2 *= poly1
    poly3 += poly4
    poly3 *= poly3
    poly3 *= poly3

    println(poly1.toString())
    println(poly2.toString())
    println(poly3.toString())
}