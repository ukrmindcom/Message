package message.core

import dev.kord.core.Kord
import dev.kord.core.event.interaction.ChatInputCommandInteractionCreateEvent
import dev.kord.core.on
import message.features.ping.PingCommand

/**
 * Handles the loading, registration, and execution of slash commands.
 * @author stabu_
 */
class CommandHandler {
    private val commands: MutableMap<String, Command> = mutableMapOf()

    init {
        loadCommands()
    }

    private fun loadCommands() {
        val pingCommand = PingCommand()
        commands[pingCommand.name] = pingCommand
    }

    /**
     * Registers all loaded commands with the Kord client and sets up the interaction listener.
     * This will first delete all existing global commands to ensure a clean state.
     * @param client The Kord instance.
     */
    suspend fun register(client: Kord) {
        println("Cleaning up old global commands...")
        client.getGlobalApplicationCommands().collect { command ->
            command.delete()
            println("Deleted old command: /${command.name}")
        }

        // Register new commands
        println("Registering new commands...")
        commands.values.forEach { command ->
            client.createGlobalChatInputCommand(command.name, command.description) {
                command.build(this)
            }
            println("Registered command: /${command.name}")
        }

        // Listen for command interactions
        client.on<ChatInputCommandInteractionCreateEvent> {
            val command = commands[interaction.command.rootName]
            command?.execute(interaction)
        }
    }
}