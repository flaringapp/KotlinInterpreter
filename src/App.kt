import interpreter.Client
import java.lang.RuntimeException

fun main(args: Array<String>) {
    try {
        Client.run()
    } catch (e: RuntimeException) {
        println("Error: ${e.message}")
    }
}