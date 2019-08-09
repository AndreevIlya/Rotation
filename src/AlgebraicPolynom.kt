class AlgebraicPolynom() : Multiplyable<AlgebraicPolynom>, Summable<AlgebraicPolynom>, Zeroable, Cloneable {
    private var polynom: MutableList<AlgebraicMonomial> = ArrayList()

    constructor(monomial: AlgebraicMonomial) : this() {
        polynom.add(monomial)
    }

    override operator fun plus(elem: AlgebraicPolynom): AlgebraicPolynom {
        val polynom = clone()
        for (monomial: AlgebraicMonomial in elem.polynom) {
            polynom.polynom.add(monomial)
        }
        return polynom.sumSimilar()
    }

    override operator fun minus(elem: AlgebraicPolynom): AlgebraicPolynom {
        val polynom = clone()
        for (monomial: AlgebraicMonomial in elem.polynom) {
            polynom.polynom.add(monomial.toggleSign())
        }
        return polynom.sumSimilar()
    }

    override operator fun times(elem: AlgebraicPolynom): AlgebraicPolynom {
        val polynom = AlgebraicPolynom()
        for (monomialThis: AlgebraicMonomial in this.polynom) {
            for (monomialThat: AlgebraicMonomial in elem.polynom) {
                polynom.polynom.add(monomialThis * monomialThat)
            }
        }
        return polynom.sumSimilar()
    }

    override fun isZero(): Boolean {
        return polynom.size == 0
    }

    override fun clone(): AlgebraicPolynom {
        val polynom = AlgebraicPolynom()
        polynom.polynom = this.polynom.toMutableList()
        return polynom
    }

    override fun toString(): String {
        val seqString = StringBuilder()
        for ((i, monomial: AlgebraicMonomial) in this.polynom.withIndex()) {
            val monoString = StringBuilder()
            if (monomial.isPositive()){
                if (i != 0) monoString.append(" + ")
                monoString.append(monomial.toString())
            } else {
                monoString.append(" - ")
                monoString.append(monomial.toggleSign().toString())
                monomial.toggleSign()
            }
            seqString.append(monoString)
        }
        return seqString.toString()
    }

    private fun sumSimilar(): AlgebraicPolynom {
        val polynom = AlgebraicPolynom()
        var j: Int
        val out: MutableList<Int> = ArrayList()
        var mono: AlgebraicMonomial
        for ((i, monomial: AlgebraicMonomial) in this.polynom.withIndex()) {
            if (i in out) continue
            j = this.polynom.size - 1
            mono = monomial
            while (j > i) {
                if (monomial.hasVariablesAllEqual(this.polynom[j])) {
                    mono += this.polynom[j]
                    out.add(j)
                }
                j--
            }
            if (!mono.isZero()) polynom.polynom.add(mono)
        }
        return polynom
    }
}