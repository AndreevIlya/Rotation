import java.util.*
import kotlin.Comparator

class CliffordMonomial() : Multiplyable<CliffordMonomial>, Cloneable {
    private var sequence: MutableList<Variable> = ArrayList()
    private var sign: Boolean = true

    constructor(variable: Variable) : this() {
        sequence.add(variable)
    }

    override fun times(elem: CliffordMonomial): CliffordMonomial {
        val mono = clone()
        var index: Int
        for (monoThat: Variable in elem.sequence) {
            index = mono.sequence.indexOf(monoThat)
            if (index == -1) {
                mono.sequence.add(monoThat)
            } else {
                if ((mono.sequence.size - index) % 2 == 1) mono.sign = !mono.sign
                mono.sequence.remove(monoThat)
            }
        }
        return mono.align()
    }

    public override fun clone(): CliffordMonomial {
        val mono = CliffordMonomial()
        mono.sequence = this.sequence.toMutableList()
        return mono
    }

    fun isPositive(): Boolean {
        return sign
    }

    operator fun unaryMinus(): CliffordMonomial {
        val mono = clone()
        sign = !sign
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

    fun hasAllVariablesEqual(mono: CliffordMonomial): Boolean {
        if (this.sequence.size != mono.sequence.size) return false
        for (variable: Variable in mono.sequence) {
            if (!this.sequence.contains(variable)) return false
        }
        return true
    }

    private fun align(): CliffordMonomial {
        val mono = this
        mono.sequence.sortWith(Comparator { o1, o2 ->
            when {
                o1 > o2 -> {
                    mono.sign = !mono.sign
                    1
                }
                o1 == o2 -> 0
                else -> -1
            }
        })
        return mono
    }
}