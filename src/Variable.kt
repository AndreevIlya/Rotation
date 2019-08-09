class Variable(private val letter : Char, private val index : Int){

    override fun toString() : String{
        if(index < 10) return "${letter}0$index\\"
        return "$letter$index\\"
    }
}