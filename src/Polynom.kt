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

    fun extractPolynom(cliff: CliffordMonomial): AlgebraicPolynom{
        for (mono: Monomial in polynom){
            if (mono.hasSameCliffordBase(cliff)){
                return mono.getMatchingAlgebraicPolynom(cliff)
            }
        }
        throw RuntimeException("Unable to extract polynom with $cliff base.")
    }
}