fun splitSack(sack: String): Pair<String, String> {
    val split = sack.chunked(sack.length/2)
    return Pair(split[0], split[1])
}

fun generatePriorities(): Map<Char, Int> {
    var lettersAndPriorities = mutableMapOf<Char, Int>()
    var lowerCasePriority = 1
    var upperCasePriority = 27

    // map lowercase values a-z
    for (i in 97..122) {
        lettersAndPriorities[Char(i)] = lowerCasePriority
        lowerCasePriority += 1
    }

    // map uppercase values A-Z
    for (i in 65..90) {
        lettersAndPriorities[Char(i)] = upperCasePriority
        upperCasePriority += 1
    }

    return lettersAndPriorities.toMap()
}

fun getCommonItem(sack: String): Char? {
    val (firstCompartment, secondCompartment) = splitSack(sack)
    val firstCompartmentChars = firstCompartment.chunked(1).toSet()

    for (item in secondCompartment) {
        if (firstCompartmentChars.contains(item.toString())) {
            return item
        }
    }

    return null
}

fun getCommonItemsPriorityTotal(input: List<String>, prioritiesMap: Map<Char, Int>): Int = input.fold(0) { total, currentRucksack ->
    val commonItem = getCommonItem(currentRucksack)
    val priority = commonItem?.let { prioritiesMap[commonItem] } ?: 0
    total + priority
}

fun main() {
    fun part1(input: List<String>): Int {
        val lettersAndPriorities = generatePriorities()
        return getCommonItemsPriorityTotal(input, lettersAndPriorities)
    }

    fun part2(input: List<String>): Int {
        return TODO()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    // check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    // println(part2(input))
}
