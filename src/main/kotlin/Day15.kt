import kotlin.math.abs
import java.util.PriorityQueue

fun main() {
    val input = {}::class.java.getResource("Day15.txt")?.readText()
    if (input == null) {
        println("No input file found.")
        return
    }

    val grid = input.split("\n").map { l ->
        l.trim().toCharArray().map { c ->
            c.digitToInt()
        }
    }

    Day15(grid).also {
        it.partOne()
        it.partTwo()
    }
}

class Day15(private val grid : List<List<Int>>) {
    fun partOne() {
        println(search(this.grid))
    }

    fun partTwo() {
        val row = mutableListOf<List<Int>>()
        val largeGrid = mutableListOf<List<Int>>()
        for (i in this.grid.indices) {
            val line = mutableListOf<Int>()
            for (j in 0..4) {
                line.addAll(grid[i].map { if (it + j > 9) it + j - 9 else it + j })
            }
            row.add(line)
        }
        for (i in 0..4) {
            largeGrid.addAll(row.map { r -> r.map { if (it + i > 9) it + i - 9 else it + i } } )
        }

        println(search(largeGrid))
    }

    private fun search(grid: List<List<Int>>) : Int {
        this.queue.clear()
        this.finished.clear()

        this.queue.add(Node(Point(0, 0), grid, 0))
        while (!this.queue.first().isEnd) {
            this.queue.first().also {
                if (!finished.contains(it.point)) {
                    this.finished.add(it.point)

                    this.queue.addAll(it.getNeighbours().filter { n ->
                        !this.finished.contains(n.point)
                    })
                }
                this.queue.remove(it)
            }

        }
        return this.queue.first().risk
    }

    private var queue = PriorityQueue<Node>() {a, b -> a.priority - b.priority}
    private var finished = mutableSetOf<Point>()
}

class Node(val point: Point, private val grid : List<List<Int>>, val risk : Int) {
    private val distance = abs(this.point.x - this.point.y)
    val priority = this.distance + this.risk
    val isEnd = this.point.x == this.grid.size - 1 && this.point.y == this.grid.size - 1

    fun getNeighbours() : MutableList<Node> {
        val neighbours = mutableListOf<List<Int>>()

        if (this.point.x > 0) neighbours.add(listOf(this.point.x - 1, this.point.y))
        if (this.point.x < this.grid.size - 1) neighbours.add(listOf(this.point.x + 1, this.point.y))
        if (this.point.y > 0) neighbours.add(listOf(this.point.x, this.point.y - 1))
        if (this.point.y < this.grid[0].size - 1) neighbours.add(listOf(this.point.x, this.point.y + 1))

        return neighbours.map { n ->
            Node(Point(n[0], n[1]), this.grid, this.risk + this.grid[n[0]][n[1]])
        }.toMutableList()
    }
}

data class Point(val x : Int, val y : Int)