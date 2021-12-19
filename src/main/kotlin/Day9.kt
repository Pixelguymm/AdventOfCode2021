fun main() {
    val input = {}::class.java.getResource("Day9.txt")?.readText()
    if (input == null) {
        println("No input file found.")
        return
    }

    val points = input.split("\n").map { l ->
        l.trim().toCharArray().map { n ->
            n.toString().toInt()
        }
    }

    Day9(points).also {
        it.partOne()
        it.partTwo()
    }
}

class Day9(private val points : List<List<Int>>) {
    fun partOne() {
        val lowPoints = getLowest()
        println(lowPoints.sumOf { p -> this.points[p[0]][p[1]] + 1 })
    }

    fun partTwo() {
        val lowPoints = getLowest()
        val basins = mutableListOf<List<List<Int>>>()

        for (point in lowPoints) {
            val basin = mutableListOf(point)
            val next = mutableListOf(point)

            while (next.isNotEmpty()) {
                val current = next.distinct()
                next.clear()

                for (p in current) {
                    val neighbours = getNeighbours(p[0], p[1]).filter { n ->
                        this.points[n[0]][n[1]] != 9
                    }
                    for (n in neighbours) {
                        if (!basin.contains(n)) {
                            next.add(n)
                            basin.add(n)
                        }
                    }
                }
            }
            basins.add(basin.sortedBy { b ->
                b[1]
            }.sortedBy { b ->
                b[0]
            })
        }

        val sizes = basins.distinct().map { b ->
            b.size
        }.sortedDescending()
        println(sizes[0] * sizes[1] * sizes[2])
    }

    private fun getLowest() : List<List<Int>> {
        val lowPoints = mutableListOf<List<Int>>()
        for (x in this.points.indices) {
            for (y in this.points[0].indices) {
                val low = checkLowest(x, y)
                if (low) lowPoints.add(listOf(x, y))
            }
        }

        return lowPoints.toList()
    }

    private fun checkLowest(x : Int, y : Int) : Boolean {
        val point = this.points[x][y]

        if (point == 0) return true
        if (point == 9) return false

        val neighbours = getNeighbours(x, y)

        return neighbours.all { n ->
            this.points[n[0]][n[1]] >= point
        }
    }

    private fun getNeighbours(x : Int, y : Int) : List<List<Int>> {
        val neighbours = mutableListOf<List<Int>>()

        if (x > 0) neighbours.add(listOf(x - 1, y))
        if (x < this.points.size - 1) neighbours.add(listOf(x + 1, y))
        if (y > 0) neighbours.add(listOf(x, y - 1))
        if (y < this.points[0].size - 1) neighbours.add(listOf(x, y + 1))

        return neighbours.toList()
    }
}