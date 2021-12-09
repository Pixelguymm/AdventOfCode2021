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

    Day9().also {
        it.partOne(points)
        it.partTwo(points)
    }
}

class Day9 {
    fun partOne(points : List<List<Int>>) {
        val lowPoints = getLowest(points)
        println(lowPoints.sumOf { p -> points[p[0]][p[1]] + 1 })
    }

    fun partTwo(points : List<List<Int>>) {
        val lowPoints = getLowest(points)
        val basins = mutableListOf<List<List<Int>>>()

        for (point in lowPoints) {
            val basin = mutableListOf(point)
            val next = mutableListOf(point)

            while (next.isNotEmpty()) {
                val current = next.distinct()
                next.clear()

                for (p in current) {
                    val neighbours = getNeighbours(p[0], p[1], points).filter { n ->
                        points[n[0]][n[1]] != 9
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

    private fun getLowest(points : List<List<Int>>) : List<List<Int>> {
        val lowPoints = mutableListOf<List<Int>>()
        for (x in points.indices) {
            for (y in points[0].indices) {
                val low = checkLowest(x, y, points)
                if (low) lowPoints.add(listOf(x, y))
            }
        }

        return lowPoints.toList()
    }

    private fun checkLowest(x : Int, y : Int, points : List<List<Int>>) : Boolean {
        val point = points[x][y]

        if (point == 0) return true
        if (point == 9) return false

        val neighbours = getNeighbours(x, y, points)

        return neighbours.all { n ->
            points[n[0]][n[1]] >= point
        }
    }

    private fun getNeighbours(x : Int, y : Int, points : List<List<Int>>) : List<List<Int>> {
        val neighbours = mutableListOf<List<Int>>()

        if (x > 0) neighbours.add(listOf(x - 1, y))
        if (x < points.size - 1) neighbours.add(listOf(x + 1, y))
        if (y > 0) neighbours.add(listOf(x, y - 1))
        if (y < points[0].size - 1) neighbours.add(listOf(x, y + 1))

        return neighbours.toList()
    }
}