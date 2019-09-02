import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

class Matrix(private val dimension: Int) {

    private val cliffordVector: List<CliffordMonomial> = makeCliffordVector()
    private val xVec: List<AlgebraicMonomial> = makeVector('x')
    private val matrix: List<List<AlgebraicPolynom>> = makeMatrix()

    private fun makeCliffordVector(): List<CliffordMonomial>{
        var i = 0
        val vec: MutableList<CliffordMonomial> = ArrayList(dimension)
        while (i < dimension) {
            vec.add(CliffordMonomial(Variable('H', i)))
            i++
        }
        return vec
    }

    private fun makeVector(letter: Char):  List<AlgebraicMonomial> {
        var i = 0
        val vec: MutableList<AlgebraicMonomial> = ArrayList(dimension)
        while (i < dimension) {
            vec.add(AlgebraicMonomial(Variable(letter, i)))
            i++
        }
        return vec
    }

    private fun makeVectorPolynom(): Polynom {
        var poly = Polynom()
        for ((i, mono: AlgebraicMonomial) in xVec.withIndex()){
            poly += Polynom(Monomial(AlgebraicPolynom(mono), cliffordVector[i]))
        }
        return poly
    }

    private fun makeBiVector(letter: Char, pseudo: Boolean): Polynom {
        var i = 0
        var j: Int
        var poly = Polynom()
        val vec0 = Polynom(Monomial(AlgebraicPolynom(AlgebraicMonomial(Variable(letter, 0)))))
        poly += vec0
        while (i < dimension) {
            j = i + 1
            while (j < dimension) {
                val vec = AlgebraicPolynom(AlgebraicMonomial(Variable(letter, "${i}x$j")))
                val mono = Polynom(
                    Monomial(
                        if (pseudo) vec
                        else -vec,
                        cliffordVector[i] * cliffordVector[j]
                    )
                )
                poly += mono
                j++
            }
            i++
        }
        return poly
    }

    private fun makeMatrix(): List<List<AlgebraicPolynom>>{
        val matrix: MutableList<MutableList<AlgebraicPolynom>> = ArrayList(dimension)
        val xPoly = makeVectorPolynom()
        val leftPoly = makeBiVector('s', true)
        val rightPoly = makeBiVector('s', false)
        val poly = leftPoly * xPoly * rightPoly
        for (cliff: CliffordMonomial in cliffordVector){
            val algPoly = poly.extractPolynom(cliff)
            val matrixRow: MutableList<AlgebraicPolynom> = ArrayList(dimension)
            for (xMono: AlgebraicMonomial in xVec){
                matrixRow.add(algPoly.extractPolynom(xMono))
            }
            matrix.add(matrixRow)
        }
        return matrix
    }

    override fun toString(): String {
        val seqString = StringBuilder()
        seqString.append("Notebook[{\n" +
                "\n" +
                "Cell[BoxData[\n" +
                " RowBox[{\"{\", \n" +
                "  RowBox[{\n")
        for ((i, row: List<AlgebraicPolynom>) in matrix.withIndex()) {
            seqString.append("   RowBox[{\"{\", \n" +
                    "    RowBox[{")
            for ((j, item: AlgebraicPolynom) in row.withIndex()) {
                seqString.append(item.toString())
                if (j != dimension - 1) seqString.append(", \",\", ")
                else seqString.append("}], \"}\"}]")
            }
            if (i != dimension - 1) seqString.append(", \",\",")
        }
        seqString.append("}], \"}\"}]], \"Input\"]}]")
        return seqString.toString()
    }

    fun writeToFile(){
        val file = File("rotation_$dimension.nb")
        val writer = FileWriter(file, false)
        val bw = BufferedWriter(writer)
        bw.write(this.toString())
        bw.close()
    }
}

fun main(args: Array<String>) {
    if (args.isNotEmpty() && Integer.parseInt(args[0]) > 1){
        Matrix(Integer.parseInt(args[0])).writeToFile()
    } else {
        Matrix(9).writeToFile()
    }
}