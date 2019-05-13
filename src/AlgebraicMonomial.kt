class AlgebraicMonomial() {
    private var coeff : Int = 1
    private var sequence : HashMap<Variable,Int> = HashMap()

    constructor(variable : Variable) : this(){
        sequence[variable] = 1
    }

    infix fun mult(monomial : AlgebraicMonomial){
        coeff *= monomial.coeff
        for( variable : Variable in monomial.sequence.keys) {
            if (sequence.containsKey(variable)){
                if(sequence[variable] != null && monomial.sequence[variable] != null){
                    sequence[variable] = sequence[variable] as Int + monomial.sequence[variable] as Int
                }
            }else{
                if(monomial.sequence[variable] != null) {
                    sequence.put(variable, monomial.sequence[variable] as Int)
                }
            }
        }
    }

    infix fun sum(monomial: AlgebraicMonomial){
        for( variable : Variable in monomial.sequence.keys) {
            if(sequence.containsKey(variable) && sequence[variable] as Int == monomial.sequence[variable] as Int){
                coeff += monomial.coeff
            }
        }
    }

    override fun toString(): String {
        var seqString = ""
        for( variable : Variable in sequence.keys) {
            seqString += if(sequence[variable] == 1) {
                variable.toString() + " "
            }else{
                variable.toString() + "^" + sequence[variable] + " "
            }
        }
        return coeff.toString() + "\\ " + seqString
    }
}