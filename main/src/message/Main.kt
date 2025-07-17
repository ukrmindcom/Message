@file:JvmName("Main")

package message

import dev.kord.core.Kord
import dev.kord.gateway.Intent
import dev.kord.gateway.PrivilegedIntent
import io.github.cdimascio.dotenv.dotenv
import message.config.loadConfig
import message.core.CommandHandler

/**
 * The entry point of the application.
 * This function initializes all necessary components and starts the bot.
 */
suspend fun main() {
    val dotenv = dotenv()
    val config = loadConfig(dotenv)

    val client = Kord(config.discordToken)

    val commandHandler = CommandHandler()
    commandHandler.register(client)

    client.login {
        @OptIn(PrivilegedIntent::class)
        intents += Intent.MessageContent
    }
}
