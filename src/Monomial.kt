import java.lang.StringBuilder

class Monomial() : Multiplyable<Monomial>, Summable<Monomial>, Zeroable, Cloneable {
    private var algebraic: AlgebraicPolynom = AlgebraicPolynom()
    private var clifford: CliffordMonomial = CliffordMonomial()

    constructor(alg: AlgebraicPolynom, cliff: CliffordMonomial) : this() {
        algebraic = alg
        clifford = cliff
    }

    override fun times(elem: Monomial): Monomial {
        val mono = Monomial()
        mono.algebraic = this.algebraic * elem.algebraic
        mono.clifford = this.clifford * elem.clifford
        return mono
    }

    override fun plus(elem: Monomial): Monomial {
        val mono = Monomial()
        if (this.clifford.hasAllVariablesEqual(elem.clifford)) {
            mono.algebraic = this.algebraic + elem.algebraic
            mono.clifford = this.clifford
        } else throw RuntimeException("Summation of the monomials with different clifford bases.")
        return mono
    }

    override fun minus(elem: Monomial): Monomial {
        val mono = Monomial()
        if (this.clifford.hasAllVariablesEqual(elem.clifford)) {
            mono.algebraic = this.algebraic - elem.algebraic
            mono.clifford = this.clifford
        } else throw RuntimeException("Summation of the monomials with different clifford bases.")
        return mono
    }

    override fun isZero(): Boolean {
        return algebraic.isZero()
    }

    override fun clone(): Monomial {
        val mono = Monomial()
        mono.algebraic = this.algebraic
        mono.clifford = this.clifford
        return mono
    }

    override fun toString(): String {
        val str = StringBuilder()
        str.append("(").append(this.algebraic).append(") ").append(this.clifford)
        return str.toString()
    }
}