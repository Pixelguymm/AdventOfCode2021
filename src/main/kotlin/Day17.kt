import kotlin.math.abs
import kotlin.math.max

fun main() {
    val input = {}::class.java.getResource("Day17.txt")?.readText()
    if (input == null) {
        println("No input file found.")
        return
    }

    val field = input.substringAfter("target area: ").split(", ").map {
        it.substringAfter("=").split("..").map { n -> n.toInt() }.sortedBy { n -> abs(n) }
    }.let {
        Field(
            it.first().first()..it.first().last(),
            it.last().first()..it.last().last()
        )
    }

    Day17(field).also {
        it.partOne()
        it.partTwo()
    }
}

class Day17(private val field : Field) {
    private val trajectories = this.field.let {
        val hits = mutableListOf<Probe>()
        var yVel = it.yRange.last
        var xVel = 1

        while (true) {
            println("$xVel, $yVel")
            val endPoint = Probe(xVel, yVel, it).getEndPoint()
            println("-> ${endPoint.xPos}, ${endPoint.yPos}")

            if (endPoint.passesField) {
                hits.add(endPoint)
                yVel++
            }
            else {
                if (endPoint.xVel > it.xRange.last) break

                if (
                    endPoint.xPos >= it.xRange.first &&
                    yVel <= abs(it.yRange.last)
                ) yVel++
                else {
                    xVel++
                    yVel = it.yRange.last
                }
            }
        }

        hits
    }

    fun partOne() {
        println(this.trajectories.maxOf { it.highest })
    }

    fun partTwo() {
        println(this.trajectories.size)
    }
}

class Probe(var xVel : Int, var yVel : Int, private val field: Field) {
    var xPos = 0
    var yPos = 0
    var highest = 0
    var passesField = false

    fun getEndPoint() : Probe {
        while (inField() == -1 && !(this.xVel == 0 && this.xPos < this.field.xRange.first)) {
            nextStep()
        }
        this.passesField = (inField() == 0)
        return this
    }

    private fun nextStep() {
        this.xPos += this.xVel
        this.yPos += this.yVel
        this.xVel -= if (this.xVel > 0) 1 else 0
        this.yVel -= 1

        this.highest = max(this.highest, this.yPos)
    }

    private fun inField() : Int { // 0: in field, 1: past field, -1: before field
        if (this.xPos < this.field.xRange.first || this.yPos > this.field.yRange.first) return -1
        if (this.xPos > this.field.xRange.last || this.yPos < this.field.yRange.last) return 1
        return 0
    }
}

data class Field(val xRange : IntRange, val yRange : IntRange)