fun main() {
    val input = {}::class.java.getResource("Day8.txt")?.readText()
    if (input == null) {
        println("No input file found.")
        return
    }

    val letters = input.split("\n").map { l ->
        l.split(" | ").map { s ->
            s.split(" ").map { n ->
                n.trim()
            }
        }
    }

    Day8().also {
        it.partOne(letters)
        it.partTwo(letters)
    }
}

class Day8 {
    fun partOne(letters: List<List<List<String>>>) {
        println(letters.sumOf { l ->
            l[1].count { n ->
                listOf(2, 3, 4, 7).contains(n.length)
            }
        })

    }

    fun partTwo(letters: List<List<List<String>>>) {
        var sum = 0
        for (l in letters) {
            val map = getCodeMap(l[0])
            var num = 0
            for (n in l[1]) {
                val value = map[n.toCharArray().sorted().joinToString("")]!!
                num = num * 10 + value
            }
            sum += num
        }
        println(sum)
    }

    private fun getCodeMap(clues : List<String>) : Map<String, Int> {
        val one = clues.find { c -> c.length == 2 }!!
        val four = clues.find { c -> c.length == 4 }!!
        val seven = clues.find { c -> c.length == 3 }!!
        val eight = clues.find { c -> c.length == 7 }!!

        val unknown = clues.filter { c ->
            !listOf(2, 3, 4, 7).contains(c.length)
        }.toMutableList()

        val nine = unknown.find { c -> c.length == 6 && four.all { d -> c.contains(d) } }!!
        unknown.remove(nine)
        val three = unknown.find { c -> c.length == 5 && one.all { d -> c.contains(d) } }!!
        unknown.remove(three)
        val zero = unknown.find { c -> c.length == 6 && one.all { d -> c.contains(d) } }!!
        unknown.remove(zero)
        val six = unknown.find { c -> c.length == 6 }!!
        unknown.remove(six)
        val five = unknown.find { c -> c.length == 5 && c.all { d -> six.contains(d) } }!!
        unknown.remove(five)
        val two = unknown[0]

        val nums = listOf(zero, one, two, three, four, five, six, seven, eight, nine)
        return nums.associate { n ->
            n.toCharArray().sorted().joinToString("") to nums.indexOf(n)
        }
    }
}