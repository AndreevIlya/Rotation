import java.lang.RuntimeException
import java.lang.StringBuilder
import java.util.*

class CliffordMonomial() : Arithmetic<CliffordMonomial>, Cloneable {
    private var sequence: MutableList<Variable> = ArrayList()
    private var sign: Boolean = true

    constructor(variable: Variable) : this() {
        sequence.add(variable)
    }

    override fun plus(elem: CliffordMonomial): CliffordMonomial {
        throw RuntimeException("No plus method for Clifford monomials.")
    }

    override fun times(elem: CliffordMonomial): CliffordMonomial {
        val mono = clone()
        for (monoThat: Variable in elem.sequence) {
            val index: Int = mono.sequence.indexOf(monoThat)
            if (index == -1) {
                mono.sequence.add(monoThat)
            } else {
                if ( (mono.sequence.size - index) % 2 == 1) mono.sign = !mono.sign
                mono.sequence.remove(monoThat)
            }
        }
        return mono
    }

    override fun clone(): CliffordMonomial {
        val mono = CliffordMonomial()
        mono.sequence = this.sequence.toMutableList()
        return mono
    }

    override fun toString(): String {
        val seqString = StringBuilder()
        if (!sign) seqString.append("-\\ ")
        for ((i, variable: Variable) in sequence.withIndex()) {
            seqString.append(variable)
            if (i != sequence.size - 1) seqString.append(" ")
        }
        return seqString.toString()
    }
}