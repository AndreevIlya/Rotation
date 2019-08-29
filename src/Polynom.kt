import java.lang.RuntimeException

class Polynom() : Multiplyable<Polynom>,Summable<Polynom>,Zeroable, Cloneable {
    private var polynom: MutableList<Monomial> = ArrayList()

    constructor(monomial: Monomial): this(){
        polynom.add(monomial)
    }

    override fun plus(elem: Polynom): Polynom {
        val polynom = clone()
        for (monomial: Monomial in elem.polynom) {
            polynom.polynom.add(monomial)
        }
        return polynom.sumSimilar()
    }

    override fun minus(elem: Polynom): Polynom {
        val polynom = clone()
        for (monomial: Monomial in elem.polynom) {
            polynom.polynom.add(-monomial)
        }
        return polynom.sumSimilar()
    }

    override operator fun times(elem: Polynom): Polynom {
        val polynom = Polynom()
        for (monomialThis: Monomial in this.polynom) {
            for (monomialThat: Monomial in elem.polynom) {
                polynom.polynom.add(monomialThis * monomialThat)
            }
        }
        return polynom.sumSimilar()
    }

    override fun clone(): Polynom {
        val polynom = Polynom()
        polynom.polynom = this.polynom.toMutableList()
        return polynom
    }

    override fun isZero(): Boolean {
        return polynom.size == 0
    }

    override fun toString(): String {
        val seqString = StringBuilder()
        for ((i, monomial: Monomial) in this.polynom.withIndex()) {
            if (i != 0) seqString.append(" + ")
            seqString.append(monomial.toString())
        }
        return seqString.toString()
    }

    private fun sumSimilar(): Polynom{
        val polynom = Polynom()
        var j: Int
        val out: MutableList<Int> = ArrayList()
        var mono: Monomial
        for ((i, monomial: Monomial) in this.polynom.withIndex()) {
            if (i in out) continue
            j = this.polynom.size - 1
            mono = monomial
            while (j > i) {
                if (monomial.hasSameCliffordBase(this.polynom[j])) {
                    mono += this.polynom[j]
                    out.add(j)
                }
                j--
            }
            if (!mono.isZero()) polynom.polynom.add(mono)
        }
        return polynom
    }

    fun extractVector(): List<AlgebraicPolynom>{
        val vec: MutableList<AlgebraicPolynom> = ArrayList()
        val cliff: MutableList<CliffordMonomial> = ArrayList()
        var dimension = 0
        for (mono: Monomial in this.polynom){
            if(mono.getCliffordSize() == 1) dimension++
        }
        var i = 0
        var count = 0
        while (i < dimension) {
            cliff.add(CliffordMonomial(Variable('H', i)))
            i++
        }
        for (cliffMono: CliffordMonomial in cliff){
            for (mono: Monomial in polynom){
                if (mono.hasSameCliffordBase(cliffMono)){
                    vec.add(mono.getMatchingAlgebraicPolynom(cliffMono))
                    count++
                    break
                }
            }
        }
        if (count == dimension) return vec
        else throw RuntimeException("Not enough components: $count of $dimension.")
    }
}