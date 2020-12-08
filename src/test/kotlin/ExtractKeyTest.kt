import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class ExtractKeyTest {
    @Test
    fun extractNothingTest() {
        val result = ExtractKey.from("just some text") { key ->
            when (key) {
                "" -> "empty"
                else -> ">${key}<"
            }
        }
        Assertions.assertEquals(result, "just some text")
    }

    @Test
    fun extractSingleTest() {
        val result = ExtractKey.from("just {{ some }} text") { key ->
            when (key) {
                "some" -> "single"
                else -> ">${key}<"
            }
        }
        Assertions.assertEquals(result, "just single text")
    }

    @Test
    fun extractMultipleTest() {
        val result = ExtractKey.from("just {{ some }}-{{text}}") { key ->
            val parts: List<String> = key.split('.').toList()

            when (parts.first()) {
                "record" ->
                    when (parts[1]) {
                        "value" -> TODO()
                        "key" -> TODO()
                        "timestamp" -> TODO()
                        else -> TODO()
                    }
                "env" -> System.getenv()[parts[1]]
                else -> TODO()
            }
            when (key) {
                "some" -> "many"
                "text" -> "values"
                else -> ">${key}<"
            }
        }
        Assertions.assertEquals(result, "just many-values")
    }

    @Test
    fun extractOnlyKey() {
        val result = ExtractKey.from("{{only}}") { key ->
            when (key) {
                "only" -> "FOuND"
                else -> ">${key}<"
            }
        }
        Assertions.assertEquals(result, "FOuND")
    }

    @Test
    fun extractNoMatch() {
        val result = ExtractKey.from("{{only}") { key ->
            when (key) {
                "only" -> "FOuND"
                else -> ">${key}<"
            }
        }
        Assertions.assertEquals(result, "{{only}")
    }
}