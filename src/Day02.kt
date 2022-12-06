import Choice.*
import Result.*

enum class Choice(val firstLetter: Char, val secondLetter: Char, val points: Int) {
    ROCK('A', 'X', 1),
    PAPER('B', 'Y', 2),
    SCISSORS('C', 'Z', 3);

    companion object {
        infix fun fromFirstLetter(firstLetter: Char): Choice = values().first { it.firstLetter == firstLetter }
        infix fun fromSecondLetter(secondLetter: Char): Choice = values().first { it.secondLetter == secondLetter }
    }
}

enum class Result(val letter: Char, val points: Int) {
    LOSE('X',0),
    DRAW('Y',3),
    WIN('Z',6);

    companion object {
        infix fun fromLetter(letter: Char): Result = values().first { it.letter == letter }
    }
}

fun getPointsForRoundByPlayerChoice(firstLetter: Char, secondLetter: Char): Int {
    val opponentChoice = Choice.fromFirstLetter(firstLetter)
    val playerChoice = Choice.fromSecondLetter(secondLetter)
    val choicePoints = playerChoice.points

    return if (playerChoice == opponentChoice) {
        DRAW.points + choicePoints
    } else if ((playerChoice == ROCK && opponentChoice == SCISSORS) ||
               (playerChoice == PAPER && opponentChoice == ROCK) ||
               (playerChoice == SCISSORS && opponentChoice == PAPER)) {
        WIN.points + choicePoints
    } else {
        LOSE.points + choicePoints
    }
}

fun getPointsForRoundByResult(firstLetter: Char, secondLetter: Char): Int {
    val opponentChoice = Choice.fromFirstLetter(firstLetter)
    val result = Result.fromLetter(secondLetter)

    val playerChoice = when (result) {
        DRAW -> opponentChoice
        WIN -> when (opponentChoice) {
            ROCK -> PAPER
            PAPER -> SCISSORS
            SCISSORS -> ROCK
        }
        LOSE -> when (opponentChoice) {
            SCISSORS -> PAPER
            ROCK -> SCISSORS
            PAPER -> ROCK
        }
    }

    return result.points + playerChoice.points
}

fun CharArray.firstAndLast() = Pair(this.first(), this.last())

fun calculatePoints(input: List<String>, calculationFn: (Char, Char) -> Int) = input.fold(0) { total, currentString ->
    val (firstLetter, secondLetter) = currentString.toCharArray().firstAndLast()
    val pointsForRound = calculationFn(firstLetter, secondLetter)
    total + pointsForRound
}

fun main() {
    fun part1(input: List<String>): Int {
        return calculatePoints(input, ::getPointsForRoundByPlayerChoice)
    }

    fun part2(input: List<String>): Int {
        return calculatePoints(input, ::getPointsForRoundByResult)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    println(part1(testInput))
    check(part2(testInput) == 12)
    println(part2(testInput))

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
