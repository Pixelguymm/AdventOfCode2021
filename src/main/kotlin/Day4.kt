fun main() {
    val input = {}::class.java.getResource("Day4.txt")?.readText()
    if (input == null) {
        println("No input file found.")
        return
    }

    val numbers = input.substringBefore("\n").trim().split(",").map { n ->
        n.toInt()
    }
    val boards = input.substringAfter("\n").trim().split("\n\n").map { b ->
        b.split("\n").map { r ->
            r.trim().split(Regex("\\s+")).map { n ->
                n.trim().toInt()
            }
        }
    }

    Day4(numbers, boards).also {
        it.partOne()
        it.partTwo()
    }
}

class Day4(private val numbers : List<Int>, private val boards : List<List<List<Int>>>) {
    fun partOne() {
        var picked = listOf<Int>()
        for (n in this.numbers) {
            picked = picked.plus(n)
            for (b in this.boards) {
                val bingo = evaluateBoard(picked, b)
                if (!bingo) continue

                println(getScore(picked, b))
                return
            }
        }
    }

    fun partTwo() {
        var losers = this.boards
        var picked = listOf<Int>()
        for (n in this.numbers) {
            picked = picked.plus(n)

            if (losers.size == 1) {
                if (evaluateBoard(picked, losers[0])) {
                    break
                }
                else {
                    continue
                }
            }
            else losers = losers.filter { b ->
                !evaluateBoard(picked, b)
            }
        }

        println(getScore(picked, losers[0]))
    }

    private fun evaluateBoard(numbers : List<Int>, board : List<List<Int>>) : Boolean {
        for (i in 0..4) {
            if (
                board[i].all { n -> numbers.contains(n) } ||
                board.all { r -> numbers.contains(r[i]) }
            ) {
                return true
            }
        }
        return false
    }

    private fun getScore(numbers : List<Int>, board : List<List<Int>>) : Int {
        var sum = 0
        for (r in board) {
            for (n in r) {
                if (!numbers.contains(n)) {
                    sum += n
                }
            }
        }
        return sum * numbers.last()
    }
}