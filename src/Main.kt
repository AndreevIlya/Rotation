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

    var poly5 = AlgebraicPolynom(AlgebraicMonomial(variable1)) + AlgebraicPolynom(AlgebraicMonomial(variable2))
    println(poly5)
    var poly6 = AlgebraicPolynom(AlgebraicMonomial(variable1)) - AlgebraicPolynom(AlgebraicMonomial(variable2))
    println(poly6)
    println((poly5 * poly6).toString() + "\n")

    poly1 += poly2
    poly2 *= poly1
    poly3 -= poly4
    poly3 *= poly3

    println(poly1.toString())
    println(poly2.toString())
    println(poly3.toString())

    val cli1 = Variable('H',"1")
    val cli2 = Variable('H',"2")
    val cli3 = Variable('H',"3")
    val cli4 = Variable('H',"4")

    var monoCli1 = CliffordMonomial(cli1)
    var monoCli2 = CliffordMonomial(cli2)
    var monoCli3 = CliffordMonomial(cli3)
    var monoCli4 = CliffordMonomial(cli4)

    monoCli1 = monoCli1 * monoCli2 * monoCli3
    monoCli2 = monoCli2 * monoCli3 * monoCli4

    var mono_1 = Monomial(poly2,monoCli1)
    var mono_2 = Monomial(poly3,monoCli1)

    println(mono_1 + mono_2)


}