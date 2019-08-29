import java.lang.StringBuilder

class Monomial() : Multiplyable<Monomial>, Summable<Monomial>, Zeroable, Cloneable {
    private var algebraic: AlgebraicPolynom = AlgebraicPolynom()
    private var clifford: CliffordMonomial = CliffordMonomial()

    constructor(alg: AlgebraicPolynom) : this() {
        algebraic = alg
    }

    constructor(alg: AlgebraicPolynom, cliff: CliffordMonomial) : this() {
        algebraic = alg
        clifford = cliff
    }

    /**
     * Arithmetic operators constructed to ensure positive sign of the clifford multiplier
     * */

    override operator fun times(elem: Monomial): Monomial {
        val mono = Monomial()
        mono.clifford = this.clifford * elem.clifford
        if(mono.clifford.isPositive()) {
            mono.algebraic = this.algebraic * elem.algebraic
        } else {
            mono.algebraic = -(this.algebraic * elem.algebraic)
            mono.clifford = -mono.clifford
        }
        return mono
    }

    override operator fun plus(elem: Monomial): Monomial {
        val mono = Monomial()
        if (this.clifford.hasAllVariablesEqual(elem.clifford)) {
            when {
                this.clifford.isPositive() && elem.clifford.isPositive() -> {
                    mono.algebraic = this.algebraic + elem.algebraic
                    mono.clifford = this.clifford
                }
                this.clifford.isPositive() && !elem.clifford.isPositive() -> {
                    mono.algebraic = this.algebraic - elem.algebraic
                    mono.clifford = this.clifford
                }
                !this.clifford.isPositive() && elem.clifford.isPositive() -> {
                    mono.algebraic = elem.algebraic - this.algebraic
                    mono.clifford = elem.clifford
                }
                !this.clifford.isPositive() && !elem.clifford.isPositive() -> {
                    mono.algebraic = -this.algebraic - elem.algebraic
                    mono.clifford = -this.clifford
                }
            }
        } else throw RuntimeException("Summation of the monomials with different clifford bases.")
        return mono
    }

    override operator fun minus(elem: Monomial): Monomial {
        val mono = Monomial()
        if (this.clifford.hasAllVariablesEqual(elem.clifford)) {
            when {
                this.clifford.isPositive() && elem.clifford.isPositive() -> {
                    mono.algebraic = this.algebraic - elem.algebraic
                    mono.clifford = this.clifford
                }
                this.clifford.isPositive() && !elem.clifford.isPositive() -> {
                    mono.algebraic = this.algebraic + elem.algebraic
                    mono.clifford = this.clifford
                }
                !this.clifford.isPositive() && elem.clifford.isPositive() -> {
                    mono.algebraic = -elem.algebraic - this.algebraic
                    mono.clifford = elem.clifford
                }
                !this.clifford.isPositive() && !elem.clifford.isPositive() -> {
                    mono.algebraic = elem.algebraic - this.algebraic
                    mono.clifford = -this.clifford
                }
            }
        } else throw RuntimeException("Summation of the monomials with different clifford bases.")
        return mono
    }

    operator fun unaryMinus(): Monomial{
        val mono = clone()
        mono.algebraic = -mono.algebraic
        return mono
    }

    override fun isZero(): Boolean {
        return algebraic.isZero()
    }

    override fun clone(): Monomial {
        val mono = Monomial()
        mono.algebraic = this.algebraic.clone()
        mono.clifford = this.clifford.clone()
        return mono
    }

    override fun toString(): String {
        val str = StringBuilder()
        str.append("(").append(this.algebraic).append(") ").append(this.clifford)
        return str.toString()
    }

    fun hasSameCliffordBase(elem: Monomial): Boolean{
        return this.clifford.hasAllVariablesEqual(elem.clifford)
    }

    fun hasSameCliffordBase(elem: CliffordMonomial): Boolean{
        return this.clifford.hasAllVariablesEqual(elem)
    }

    fun getMatchingAlgebraicPolynom(elem: CliffordMonomial): AlgebraicPolynom = if (clifford.hasAllVariablesEqual(elem)){
        algebraic
    }else throw RuntimeException("Monomial\'s is not $elem")

    fun getCliffordSize() = clifford.size()
}