package cn.yfengtech.libnetwork.common

inline class Priority(val i: Int) {
    companion object {
        val LOW = Priority(0)
        val HIGH = Priority(1)
        val MEDIUM = Priority(2)
        val IMMEDIATE = Priority(99)
    }
}