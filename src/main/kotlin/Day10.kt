import kotlin.math.ceil

fun main() {
    val input = {}::class.java.getResource("Day10.txt")?.readText()
    if (input == null) {
        println("No input file found.")
        return
    }

    val lines = input.split("\n").map { l ->
        l.trim()
    }

    Day10().also {
        it.partOne(lines)
        it.partTwo(lines)
    }
}

class Day10 {
    private val brackets = mapOf(
        '(' to ')',
        '[' to ']',
        '{' to '}',
        '<' to '>'
    )

    private val illegalScores = mapOf(
        ')' to 3,
        ']' to 57,
        '}' to 1197,
        '>' to 25137
    )

    private val autoCompleteScores = mapOf(
        ')' to 1,
        ']' to 2,
        '}' to 3,
        '>' to 4
    )

    fun partOne(lines : List<String>) {
        var badChars = ""
        for (l in lines) {
            val wrongBracket = checkLine(l)
            if (wrongBracket.corrupted) badChars += wrongBracket
        }

        println(badChars.sumOf { c ->
            illegalScores[c] ?: 0
        })
    }

    fun partTwo(lines : List<String>) {
        val scores = mutableListOf<Long>()
        for (l in lines) {
            var score : Long = 0
            val result = checkLine(l)
            if (result.corrupted) continue

            val missing = result.unclosed?.map { c ->
                brackets[c]
            }?.reversed()
            missing?.forEach { c ->
                score = score * 5 + (autoCompleteScores[c] ?: 0)
            }

            scores.add(score)
        }

        println(scores.sorted()[ceil(scores.size / 2.0).toInt() - 1])
    }

    private fun checkLine(line : String) : TestResult {
        var openings = ""
        for (c in line) {
            if (brackets.keys.contains(c)) {
                openings += c
            }
            else if (brackets[openings.last()] == c) {
                openings = openings.dropLast(1)
            }
            else {
                return TestResult(true, null, c)
            }
        }
        return TestResult(false, openings, null)
    }
}

data class TestResult(val corrupted : Boolean, val unclosed : String?, val wrongBracket : Char?)