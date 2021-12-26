fun main() {
    val input = {}::class.java.getResource("Day16.txt")?.readText()
    if (input == null) {
        println("No input file found.")
        return
    }

    val binary = input.map { "$it".toInt(16).toString(2).padStart(4, '0') }.joinToString("")

    Day16(binary).also {
        it.partOne()
        it.partTwo()
    }
}

class Day16(private var binary : String) {
    private val packets = PacketReader(binary).readAllPackets()

    fun partOne() {
        println(packets.sumOf { it.versionSum })
    }

    fun partTwo() {
        println(packets[0].calculatedValue)
    }
}

class PacketReader(private var binary : String) {
    fun readAllPackets() : List<Packet> {
        val packets = mutableListOf<Packet>()
        while (this.binary.contains('1')) {
            packets.add(readNextPacket())
        }
        return packets
    }

    private fun readNextPacket() : Packet {
        val version = this.binary.slice(0..2).toInt(2)
        val type = this.binary.slice(3..5).toInt(2)
        this.binary = this.binary.removeRange(0..5)

        if (type == 4) {
            var number = ""
            var digit = ""
            while (!digit.startsWith("0")) {
                digit = this.binary.slice(0..4)
                number += digit.removeRange(0..0)

                this.binary = this.binary.removeRange(0..4)
            }
            return Packet(version, type, number.toLong(2), null)
        }
        else {
            val lengthType = this.binary.slice(0..0).toInt(2)
            if (lengthType == 0) {
                val length = this.binary.slice(1..15).toInt(2)

                this.binary = this.binary.removeRange(0..15)

                val subBinary = this.binary.slice(0 until length)
                val subPackets = PacketReader(subBinary).readAllPackets()

                this.binary = this.binary.removeRange(0 until length)

                return Packet(version, type, null, subPackets)
            }
            else {
                val length = this.binary.slice(1..11).toInt(2)

                this.binary = this.binary.removeRange(0..11)

                val subPackets = mutableListOf<Packet>()
                for (i in 1..length) {
                    subPackets.add(readNextPacket())
                }

                return Packet(version, type, null, subPackets)
            }
        }

    }
}

class Packet(private val version : Int, private val type : Int, private val value : Long?, private val subPackets : List<Packet>?) {
    val versionSum : Int
        get() {
            return this.version + (this.subPackets?.sumOf { it.versionSum } ?: 0)
        }

    val calculatedValue : Long
        get() {
            return when (this.type) {
                0 -> this.subPackets?.sumOf { it.calculatedValue } ?: 0
                1 -> this.subPackets?.fold(1L) { total, it -> total * it.calculatedValue } ?: 0
                2 -> this.subPackets?.minOf { it.calculatedValue } ?: 0
                3 -> this.subPackets?.maxOf { it.calculatedValue } ?: 0
                4 -> this.value ?: 0
                5 -> if ((this.subPackets?.get(0)?.calculatedValue ?: 0) > (this.subPackets?.get(1)?.calculatedValue ?: 0)) 1 else 0
                6 -> if ((this.subPackets?.get(0)?.calculatedValue ?: 0) < (this.subPackets?.get(1)?.calculatedValue ?: 0)) 1 else 0
                7 -> if ((this.subPackets?.get(0)?.calculatedValue ?: 0) == (this.subPackets?.get(1)?.calculatedValue ?: 0)) 1 else 0
                else -> return 0
            }
        }
}