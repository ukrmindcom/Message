package message.core

import dev.kord.core.entity.interaction.ChatInputCommandInteraction
import dev.kord.rest.builder.interaction.ChatInputCreateBuilder

/**
 * An interface for a slash command.
 * @author stabu_
 */
interface Command {
    /** The name of the command. */
    val name: String

    /** The description of the command. */
    val description: String

    /**
     * Builds the command structure for Discord's API.
     * @param builder The builder to use for creating the command.
     */
    fun build(builder: ChatInputCreateBuilder)

    /**
     * The logic to execute when the command is invoked.
     * @param interaction The interaction event.
     */
    suspend fun execute(interaction: ChatInputCommandInteraction)
}
