interface Summable<T> {
    operator fun plus(elem : T) : T
    operator fun minus(elem : T) : T
}