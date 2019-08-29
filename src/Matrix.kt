class Matrix(private val dimension: Int) {

    fun makeOrt(letter: Char,index: Int): Polynom {
        val ort = CliffordMonomial(Variable('H', index))
        val vec = AlgebraicPolynom(AlgebraicMonomial(Variable(letter, index)))
        return Polynom(Monomial(vec, ort))
    }

    fun makeVector(letter: Char): Polynom {
        var i = 0
        var poly = Polynom()
        while (i < dimension) {
            val ort = CliffordMonomial(Variable('H', i))
            val vec = AlgebraicPolynom(AlgebraicMonomial(Variable(letter, i)))
            val mono = Polynom(Monomial(vec, ort))
            poly += mono
            i++
        }
        return poly
    }

    fun makeBiVector(letter: Char, pseudo: Boolean): Polynom {
        var i = 0
        var j: Int
        var poly = Polynom()
        val vec0 = Polynom(Monomial(AlgebraicPolynom(AlgebraicMonomial(Variable(letter, 0)))))
        poly += vec0
        while (i < dimension) {
            j = i + 1
            while (j < dimension) {
                val ort1 = CliffordMonomial(Variable('H', i))
                val ort2 = CliffordMonomial(Variable('H', j))
                val vec = AlgebraicPolynom(AlgebraicMonomial(Variable(letter, "${i}_$j")))
                val mono = Polynom(
                    Monomial(
                        if (pseudo) vec
                        else -vec,
                        ort1 * ort2
                    )
                )
                poly += mono
                j++
            }
            i++
        }
        return poly
    }
}

fun main() {
    val xVec = Matrix(5).makeOrt('x',0)
    val aVec = Matrix(5).makeVector('a')
    val bVec = Matrix(5).makeVector('b')
    println(aVec*bVec)
    println(bVec*aVec)
    /*println((aVec*bVec) * (bVec*aVec) - aVec*bVec * bVec*aVec)*/

    val leftBiVec = Matrix(5).makeBiVector('s', true)
    println(leftBiVec)
    val rightBiVec = Matrix(5).makeBiVector('s', false)
    println(rightBiVec)
    println(leftBiVec * rightBiVec)
    /*val poly = (aVec * bVec) * xVec * (bVec * aVec)//leftBiVec * xVec * rightBiVec
    println(poly)
    val polyVec = poly.extractVector()
    for (algVec: AlgebraicPolynom in polyVec){
        println(algVec)
    }*/
}