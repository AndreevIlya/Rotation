import java.util.*

class CliffordMonomial() : Multiplyable<CliffordMonomial>, Cloneable {
    private var sequence: LinkedList<Variable> = LinkedList()
    private var sign: Boolean = true

    constructor(variable: Variable) : this() {
        sequence.add(variable)
    }

    override fun times(elem: CliffordMonomial): CliffordMonomial {
        val mono = clone()
        if (mono.sequence.isEmpty()){
            mono.sequence = elem.sequence
            return mono
        }
        var tempSequence: LinkedList<Variable>
        for (monoThat: Variable in elem.sequence) {
            if (!mono.sequence.isEmpty()) {
                var check = true
                val li: Iterator<Variable> = mono.sequence.descendingIterator()
                tempSequence = LinkedList()
                while (li.hasNext()) {
                    val monoThis = li.next()
                    if (check) {
                        when {
                            monoThis == monoThat -> {
                                mono.sign = !mono.sign
                                check = false
                            }
                            monoThis > monoThat -> {
                                tempSequence.add(monoThis)
                                mono.sign = !mono.sign
                            }
                            else -> {
                                tempSequence.add(monoThat)
                                tempSequence.add(monoThis)
                                check = false
                            }
                        }
                    } else tempSequence.add(monoThis)
                }
                if (check) tempSequence.add(monoThat)
                mono.sequence = LinkedList(tempSequence.reversed())
            } else mono.sequence.add(monoThat)
        }
        return mono
    }

    public override fun clone(): CliffordMonomial {
        val mono = CliffordMonomial()
        mono.sign = this.sign
        mono.sequence = LinkedList(this.sequence)
        return mono
    }

    fun isPositive(): Boolean {
        return sign
    }

    operator fun unaryMinus(): CliffordMonomial {
        val mono = clone()
        mono.sign = !mono.sign
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
}