class AlgebraicMonomial() : Arithmetic<AlgebraicMonomial>{
    private var coeff : Int = 1
    private var sequence : HashMap<Variable,Int> = HashMap()

    constructor(variable : Variable) : this(){
        sequence[variable] = 1
    }

    override operator fun times(elem : AlgebraicMonomial){
        coeff *= elem.coeff
        for( variable : Variable in elem.sequence.keys) {
            if (sequence.containsKey(variable)){
                if(sequence[variable] != null && elem.sequence[variable] != null){
                    sequence[variable] = sequence[variable] as Int + elem.sequence[variable] as Int
                }
            }else{
                if(elem.sequence[variable] != null) {
                    sequence[variable] = elem.sequence[variable] as Int
                }
            }
        }
    }

    override operator fun plus(elem: AlgebraicMonomial){
        for( variable : Variable in elem.sequence.keys) {
            if(sequence.containsKey(variable) && sequence[variable] as Int == elem.sequence[variable] as Int){
                coeff += elem.coeff
            }
        }
    }

    override fun toString(): String {
        var seqString = ""
        for( variable : Variable in sequence.keys) {
            seqString += if(sequence[variable] == 1) {
                "$variable "
            }else{
                variable.toString() + "^" + sequence[variable] + " "
            }
        }
        return "$coeff\\ $seqString"
    }
}