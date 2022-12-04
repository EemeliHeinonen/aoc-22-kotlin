fun sumByElf(input: List<String>): List<Int> = input.fold(mutableListOf(0)) { sums, currentString ->
    if (currentString == "") {
        sums.add(0)
    }
    else {
        sums[sums.lastIndex] = sums.last().plus(currentString.toInt())
    }
    sums
}

fun main() {
    fun part1(input: List<String>): Int {
        return sumByElf(input).max()
    }

    fun part2(input: List<String>): Int {
        return sumByElf(input)
            .sorted()
            .takeLast(3)
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
