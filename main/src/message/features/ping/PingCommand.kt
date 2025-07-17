package message.features.ping

import dev.kord.core.behavior.interaction.respondPublic
import dev.kord.core.entity.interaction.ChatInputCommandInteraction
import dev.kord.rest.builder.interaction.ChatInputCreateBuilder
import kotlinx.coroutines.flow.firstOrNull
import message.core.Command
import kotlin.time.Duration

/**
 * A command to check the bot's latency.
 * @author stabu_
 */
class PingCommand : Command {
    override val name: String = "пінг"
    override val description: String = "Перевіряє затримку бота."

    override fun build(builder: ChatInputCreateBuilder) {
        // This command has no options
    }

    override suspend fun execute(interaction: ChatInputCommandInteraction) {
        val latency: Duration? = interaction.kord.gateway.averagePing
        interaction.respondPublic {
            content = "Понг! Затримка шлюзу: `${latency?.inWholeMilliseconds ?: "N/A"}` мс."
        }
    }
}
