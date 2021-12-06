import kotlin.math.ceil

fun main() {
    val input = {}::class.java.getResource("Day3.txt")?.readText()
    if (input == null) {
        println("No input file found.")
        return
    }

    val binNumbers = input.split("\n").map { n ->
        n.trim()
    }
    println(binNumbers)

    Day3().also {
        it.partOne(binNumbers)
        it.partTwo(binNumbers)
    }
}

class Day3 {
    fun partOne(binNumbers : List<String>) {
        val counters = IntArray(12) { it * 0 }

        for (num in binNumbers) {
            for (i in num.indices) {
                if (num[i] == '1') counters[i]++
            }
        }

        var gamma = ""
        var epsilon = ""
        for (c in counters) {
            gamma += if (c >= binNumbers.size / 2) "1" else "0"
            epsilon += if (c >= binNumbers.size / 2) "0" else "1"
        }

        val g = gamma.toInt(2)
        val e = epsilon.toInt(2)

        println(g * e)
    }

    fun partTwo(binNumbers : List<String>) {
        val o2 = getOxygen(binNumbers, 0).toInt(2)
        val co2 = getCarbonDioxide(binNumbers, 0).toInt(2)

        println(o2 * co2)
    }

    private fun getOxygen(numbers : List<String>, digit : Int) : String {
        val c = getMostFrequent(numbers, digit)
        val filtered = numbers.filter { n -> n[digit] == c }

        return if (filtered.size == 1) filtered[0]
        else getOxygen(filtered, digit + 1)
    }

    private fun getCarbonDioxide(numbers : List<String>, digit : Int) : String {
        val c = getMostFrequent(numbers, digit)
        val filtered = numbers.filter { n -> n[digit] != c }

        return if (filtered.size == 1) filtered[0]
        else getCarbonDioxide(filtered, digit + 1)
    }

    private fun getMostFrequent(numbers : List<String>, digit : Int) : Char {
        var c = 0
        for (n in numbers) {
            if (n[digit] == '1') c++
        }
        return if (c >= ceil(numbers.size.toDouble() / 2)) '1' else '0'
    }
}