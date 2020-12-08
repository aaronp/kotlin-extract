object Resolve {

    fun apply(path: List<String>, value: Any): Any {
        when (value) {
            is java.util.List<*> -> return resolveList(path, value)
            is java.util.Map<*, *> -> {
                return resolveMap(path, value)
    //        } else if (value is Json) {
    //            apply(path, value.value)
            }
            else -> {
                require(path.isEmpty()) {
                    path.joinToString(
                        prefix = "Couldn't resolve '",
                        separator = ".",
                        postfix = "' $value"
                    )
                }
                return value
            }
        }
    }

    private fun resolveMap(path: List<String>, value: java.util.Map<*, *>): Any {
        return if (path.isEmpty()) {
            value
        } else {
            val head = path.first()
            val tail = path.drop(1).toList()
            val next = value.get(head)
            require(next != null) { "null value found for key '$head' from $value" }
            apply(tail, next)
        }
    }

    private fun resolveList(path: List<String>, value: java.util.List<*>): Any {
        return if (path.isEmpty()) {
            value
        } else {
            val head = path.first().toInt()
            val tail = path.drop(1).toList()
            val idx = head.toInt()
            val next = value.get(idx)
            require(next != null) { "Null value found for key $head" }
            apply(tail, next)
        }
    }
}