package io.lhuang.pokear.util

import java.lang.IllegalArgumentException
import kotlin.random.Random

/**
 * Given a list of inputs and the relative chance of each item happening, randomly choose
 * exactly one based on the distribution of probabilities.
 */
fun <T> weightedRandomBy(inputs: List<T>, by: (T) -> Double): T {
    if (inputs.isEmpty()) {
        throw IllegalArgumentException("Input must not be empty")
    }

    val total = inputs.fold(0.0, { acc, input -> acc + by.invoke(input) } )
    val diceRoll = Random.nextDouble(total)

    var cur = 0.0
    for (input in inputs) {
        cur += by.invoke(input)
        if (cur >= diceRoll) {
            return input
        }
    }

    // Shouldn't really ever get here...
    return inputs.last()
}
