import interpreter.Client
import java.lang.RuntimeException

const val IS_DEBUG = true

fun main(args: Array<String>) {
    try {
        Client.run()
    } catch (e: RuntimeException) {
        println("Error: ${e.message}")
        if (IS_DEBUG) e.printStackTrace()
    }
}