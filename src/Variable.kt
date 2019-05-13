class Variable(private val letter : Char, private val index : String){

    override fun toString() : String{
        return "$letter$index\\"
    }
}