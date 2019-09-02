class AlgebraicMonomial() : Multiplyable<AlgebraicMonomial>,Summable<AlgebraicMonomial>,Zeroable, Cloneable {
    private var coefficient: Int = 1
    private var sequence: MutableMap<Variable, Int> = HashMap()

    constructor(variable: Variable) : this() {
        sequence[variable] = 1
    }

    override operator fun times(elem: AlgebraicMonomial): AlgebraicMonomial {
        val monomial = clone()
        monomial.coefficient *= elem.coefficient
        for (variable: Variable in elem.sequence.keys) {
            monomial.sequence[variable] = if (monomial.sequence.containsKey(variable)) {
                monomial.sequence[variable]!! + elem.sequence[variable]!!
            } else {
                elem.sequence[variable]!!
            }
        }
        return monomial
    }

    operator fun div(elem: AlgebraicMonomial): AlgebraicMonomial{
        val monomial = clone()
        monomial.coefficient /= elem.coefficient
        for (variable: Variable in elem.sequence.keys) {
            if (monomial.sequence.containsKey(variable)) {
                monomial.sequence[variable] = monomial.sequence[variable]!! - elem.sequence[variable]!!
                if (monomial.sequence[variable] == 0) monomial.sequence.remove(variable)
            } else throw RuntimeException("Exception in algebraic monomials division.")
        }
        return monomial
    }

    override operator fun plus(elem: AlgebraicMonomial): AlgebraicMonomial {
        return plusMinus(0,elem)
    }

    override operator fun minus(elem: AlgebraicMonomial): AlgebraicMonomial {
        return plusMinus(1,elem)
    }

    private fun plusMinus(action: Int,elem: AlgebraicMonomial): AlgebraicMonomial{
        var fault = false
        var temp: Boolean
        val monomial = clone()
        for (variable: Variable in elem.sequence.keys) {
            temp = sequence.containsKey(variable) &&
                    sequence[variable] == elem.sequence[variable]
            fault = fault && !temp
        }
        if (!fault) {
            if (action == 0) monomial.coefficient += elem.coefficient
            else monomial.coefficient -= elem.coefficient
        } else throw RuntimeException("Summation of the different algebraic monomials")
        return monomial
    }

    operator fun unaryMinus(): AlgebraicMonomial{
        val mono = clone()
        mono.coefficient = -mono.coefficient
        return mono
    }

    override fun isZero(): Boolean {
        return this.coefficient == 0
    }

    fun isPositive(): Boolean {
        return this.coefficient > 0
    }

    override fun clone(): AlgebraicMonomial {
        val monomial = AlgebraicMonomial()
        monomial.coefficient = this.coefficient
        monomial.sequence = this.sequence.toMutableMap()
        return monomial
    }

    override fun toString(): String {
        val seqString = StringBuilder()
        if (sequence.size > 1) seqString.append("RowBox[{")
        if (coefficient != 1) seqString.append("\"$coefficient\", \" \", ")
        for ((i, variable: Variable) in sequence.keys.withIndex()) {
            seqString.append(
                if (sequence[variable] == 1) "\"$variable\""
                else "SuperscriptBox[\"$variable\", \"${sequence[variable]}\"]"
            )
            if (sequence.size > 1) seqString.append(
                if (i != sequence.keys.size - 1) ", \" \", "
                else "}]"
            )
        }
        return seqString.toString()
    }

    fun hasAllVariablesEqual(monomial : AlgebraicMonomial): Boolean{
        if (this.sequence.size != monomial.sequence.size) return false
        for (variable: Variable in monomial.sequence.keys) {
            if(!this.sequence.containsKey(variable)) return false
            if(this.sequence[variable] != monomial.sequence[variable]) return false
        }
        return true
    }

    override fun equals(other: Any?): Boolean {
        if(other !is AlgebraicMonomial) return false
        return this.coefficient == other.coefficient &&
                this.sequence == other.sequence
    }

    override fun hashCode(): Int {
        var result = coefficient
        result = 31 * result + sequence.hashCode()
        return result
    }

    fun isDividable(elem: AlgebraicMonomial): Boolean{
        var check = true
        for (variable: Variable in elem.sequence.keys) {
            check = check && this.sequence.containsKey(variable)
        }
        return check
    }
}