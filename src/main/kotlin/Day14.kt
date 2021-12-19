fun main() {
    val input = {}::class.java.getResource("Day14.txt")?.readText()
    if (input == null) {
        println("No input file found.")
        return
    }

    val start = input.substringBefore("\n\n").trim()

    val insertMap = input.substringAfter("\n\n").split("\n").map { l ->
        l.trim().split(" -> ")
    }.associate {
        it[0] to it[1]
    }

    Day14(start, insertMap).also {
        it.partOne()
        it.partTwo()
    }
}

class Day14(private val start : String, private val insertMap: Map<String, String>) {
    fun partOne() {
        var letters = this.start
        for (i in 1..10) {
            letters = insertLetters(letters)
        }

        val max = letters.toCharArray().distinct().maxOf { char -> letters.count{ c -> c == char } }
        val min = letters.toCharArray().distinct().minOf { char -> letters.count{ c -> c == char } }

        println(max - min)
    }

    fun partTwo() {
        var pairMap = getPairs(this.start)
        var letterMap = this.start.associate { char -> char.toString() to this.start.count { c -> c == char }.toLong() }

        for (i in 1..40) {
            nextStep(pairMap, letterMap).also {
                pairMap = it.pairs
                letterMap = it.letters
            }
        }

        val counts = letterMap.values.sorted()
        println(counts.last() - counts.first())
    }

    private fun insertLetters(start : String) : String {
        var result = ""
        for (i in start.indices) {
            result += start[i]
            if (i < start.length - 1 && this.insertMap.containsKey(start.substring(i, i + 2))) {
                result += this.insertMap[start.substring(i, i + 2)]
            }
        }
        return result
    }

    private fun getPairs(letters : String) : Map<String, Long> {
        val map = mutableMapOf<String, Long>()
        for (i in 0..letters.length - 2) {
            val pair = letters.substring(i, i + 2)
            map[pair] = (map[pair] ?: 0) + 1
        }

        return map
    }

    private fun nextStep(pairs : Map<String, Long>, letters : Map<String, Long>) : LetterData {
        val result = mutableMapOf<String, Long>()
        val letterCount = letters.toMutableMap()
        pairs.forEach { (pair, count) ->
            val inserted = this.insertMap[pair]
            if (inserted != null) {
                result["${pair[0]}$inserted"] = (result["${pair[0]}$inserted"] ?: 0) + count
                result["$inserted${pair[1]}"] = (result["$inserted${pair[1]}"] ?: 0) + count
                letterCount[inserted] = (letterCount[inserted] ?: 0) + count
            }
        }

        return LetterData(result, letterCount)
    }
}

data class LetterData(val pairs : Map<String, Long>, val letters : Map<String, Long>)