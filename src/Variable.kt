class Variable(private val letter : Char, private val index : Int): Comparable<Variable>{
    override fun compareTo(other: Variable): Int {
        return when {
            this.index > other.index -> 1
            this.index == other.index -> 0
            else -> -1
        }
    }

    override fun toString() : String{
        if(index < 10) return "${letter}0$index\\"
        return "$letter$index\\"
    }


}