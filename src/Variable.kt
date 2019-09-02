class Variable(private val letter : Char): Comparable<Variable>{
    private var index: Int = 0
    private var strIndex: String = ""

    constructor(letter: Char, index: Int): this(letter){
        this.index = index
    }

    constructor(letter: Char, strIndex: String): this(letter){
        this.strIndex = strIndex
    }

    override fun compareTo(other: Variable): Int {
        return when {
            this.index > other.index -> 1
            this.index == other.index -> 0
            else -> -1
        }
    }

    override fun toString() : String{
        if (strIndex != "") return "$letter$strIndex"
        //if(index < 10) return "${letter}0$index\\"
        return "$letter$index "
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Variable) return false
        return this.letter == other.letter && this.index == other.index && this.strIndex == other.strIndex
    }

    override fun hashCode(): Int {
        var result = letter.hashCode()
        result = 31 * result + index
        result = 31 * result + strIndex.hashCode()
        return result
    }

}