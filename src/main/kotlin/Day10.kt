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

    Day10(lines).also {
        it.partOne()
        it.partTwo()
    }
}

class Day10(private val lines : List<String>) {
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

    fun partOne() {
        var badChars = ""
        for (l in this.lines) {
            val wrongBracket = checkLine(l)
            if (wrongBracket.corrupted) badChars += wrongBracket
        }

        println(badChars.sumOf { c ->
            this.illegalScores[c] ?: 0
        })
    }

    fun partTwo() {
        val scores = mutableListOf<Long>()
        for (l in this.lines) {
            var score : Long = 0
            val result = checkLine(l)
            if (result.corrupted) continue

            val missing = result.unclosed?.map { c ->
                this.brackets[c]
            }?.reversed()
            missing?.forEach { c ->
                score = score * 5 + (this.autoCompleteScores[c] ?: 0)
            }

            scores.add(score)
        }

        println(scores.sorted()[ceil(scores.size / 2.0).toInt() - 1])
    }

    private fun checkLine(line : String) : TestResult {
        var openings = ""
        for (c in line) {
            if (this.brackets.keys.contains(c)) {
                openings += c
            }
            else if (this.brackets[openings.last()] == c) {
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