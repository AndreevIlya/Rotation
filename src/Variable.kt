class Variable(val letter : Char, val index : String){

    override fun toString() : String{
        return "$letter$index "
    }
}