fun main() {
    val input = {}::class.java.getResource("Day6.txt")?.readText()
    if (input == null) {
        println("No input file found.")
        return
    }

    val fish = input.split(",").map { f ->
        f.trim().toInt()
    }

    Day6().also {
        it.partOne(fish)
        it.partTwo(fish)
    }
}

class Day6 {
    fun partOne(fish : List<Int>) {
        println(getFish(fish, 80))
    }

    fun partTwo(fish : List<Int>) {
        println(getFish(fish, 256))
    }

    private fun getFish(fish : List<Int>, days : Int) : Long {
        val cycles = mutableMapOf<Int, Long>()
        fish.sorted().forEach { i ->
            cycles[i] = (cycles[i] ?: 0) + 1
        }

        for (d in 1..days) {
            val kids = cycles[0] ?: 0
            for (i in 0..7) {
                cycles[i] = cycles[i + 1] ?: 0
            }
            cycles[8] = kids
            cycles[6] = (cycles[6] ?: 0) + kids
        }

        return cycles.values.sum()
    }
}