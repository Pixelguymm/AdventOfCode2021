fun main() {
    val input = {}::class.java.getResource("Day13.txt")?.readText()
    if (input == null) {
        println("No input file found.")
        return
    }

    val points = input.substringBefore("\n\n").trim().split("\n").map { l ->
        l.split(",").map { n ->
            n.toInt()
        }.toMutableList()
    }

    val folds = input.substringAfter("\n\n").split("\n").map { l ->
        l.substringAfter("fold along ").split("=")
    }.map { f ->
        Fold(f[0], f[1].toInt())
    }

    Day13(points, folds).also {
        it.partOne()
        it.partTwo()
    }
}

class Day13(private val points : List<List<Int>>, private val folds: List<Fold>) {
    fun partOne() {
        val pts = this.points.map { p ->
            fold(this.folds[0], p)
        }.distinct()
        println(pts.size)
    }

    fun partTwo() {
        var pts = this.points
        for (f in this.folds) {
            pts = pts.map { p ->
                fold(f, p)
            }.distinct()
        }

        println(showLetters(pts))
    }

    private fun fold(fold : Fold, point: List<Int>) : List<Int> {
        if (fold.axis == "x") {
            if (point[0] > fold.location) return listOf(fold.location + (fold.location - point[0]), point[1])
        }
        else {
            if (point[1] > fold.location) return listOf(point[0], 2 * fold.location - point[1])
        }
        return point
    }

    private fun showLetters(points: List<List<Int>>) : String {
        val h = points.maxOf { it[1] }
        val w = points.maxOf { it[0] }
        var letters = ""

        for (i in 0..h) {
            for (j in 0..w) {
                letters += if (points.contains(listOf(j, i))) "# " else ". "
            }
            letters += "\n"
        }

        return letters
    }
}

data class Fold(val axis : String, val location : Int)