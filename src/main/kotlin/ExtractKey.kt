object ExtractKey {

    fun from(text: String, resolve: (String) -> String): String {
        val regex = """(.*?)\{\{(.*?)}}(.*)""".toRegex()
        val found: MatchResult? = regex.find(text)
        return if (found == null) {
            text
        } else {
            val (before, key, after) = found!!.destructured
            val theRest = from(after, resolve)
            "$before${resolve(key.trim())}$theRest"
        }
    }
}