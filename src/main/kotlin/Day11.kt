fun main() {
    val input = {}::class.java.getResource("Day11.txt")?.readText()
    if (input == null) {
        println("No input file found.")
        return
    }

    val squids = input.split("\n").map { l ->
        l.trim().toCharArray().map { c ->
            c.toString().toInt()
        }.toMutableList()
    }

    Day11().also {
        it.partOne(squids)
        it.partTwo(squids)
    }
}

class Day11 {
    fun partOne(input : List<MutableList<Int>>) {
        var octopi = input
        var count = 0
        for (i in 1..100) {
            octopi = nextStep(octopi)
            count += octopi.sumOf { l -> l.count { o -> o == 0 } }
        }
        println(count)
    }

    fun partTwo(input : List<MutableList<Int>>) {
        var octopi = input
        var count = 0
        while (true) {
            if (octopi.all { l -> l.all { o -> o == 0 } }) break
            count++
            octopi = nextStep(octopi)
        }
        println(count)
    }

    private fun getNeighbours(x : Int, y : Int, squids : List<List<Int>>) : List<Int> {
        val neighbours = mutableListOf<Int>()

        if (x > 0) {
            neighbours.add(squids[x - 1][y])
            if (y > 0) neighbours.add(squids[x - 1][y - 1])
            if (y < squids[0].size - 1) neighbours.add(squids[x - 1][y + 1])
        }
        if (x < squids.size - 1) {
            neighbours.add(squids[x + 1][y])
            if (y > 0) neighbours.add(squids[x + 1][y - 1])
            if (y < squids[0].size - 1) neighbours.add(squids[x + 1][y + 1])
        }
        if (y > 0) neighbours.add(squids[x][y - 1])
        if (y < squids[0].size - 1) neighbours.add(squids[x][y + 1])

        return neighbours.toList()
    }

    private fun nextStep(input : List<MutableList<Int>>) : List<MutableList<Int>> {
        var octopi = input
        octopi = octopi.map { l ->
            l.map { s ->
                s + 1
            }.toMutableList()
        }

        while (octopi.any { l -> l.any { o -> o >= 10 } }) {
            octopi = octopi.map { l ->
                l.map { o ->
                    if (o >= 10) 0 else if (o == 0) -1 else o
                }.toMutableList()
            }

            for (l in octopi.indices) {
                for (e in octopi[l].indices) {
                    if (octopi[l][e] in 1..9) octopi[l][e] += getNeighbours(l,e, octopi).count { n -> n == 0  }
                }
            }
        }
        octopi = octopi.map { l ->
            l.map { o ->
                if (o == -1) 0 else o
            }.toMutableList()
        }

        return octopi
    }
}