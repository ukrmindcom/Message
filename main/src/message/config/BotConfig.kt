package message.config

import dev.kord.common.entity.Snowflake
import io.github.cdimascio.dotenv.Dotenv

/**
 * A data class to hold the bot's configuration, loaded from environment variables.
 * @author stabu_
 */
data class BotConfig(
    val discordToken: String,
    val geminiAPIKey: String,
    val githubToken: String,
    val logChannelTechnical: Snowflake?,
    val logChannelDeletions: Snowflake?,
    val logChannelEdits: Snowflake?,
    val logChannelMembers: Snowflake?,
    val logChannelModActions: Snowflake?,
    val logChannelBotStatus: Snowflake?,
    val logChannelAiSuspicions: Snowflake?
)

/**
 * Loads the configuration from the provided Dotenv instance.
 *
 * @param dotenv The Dotenv instance to load from.
 * @return A BotConfig instance.
 * @throws IllegalStateException if a required environment variable is not set.
 */
fun loadConfig(dotenv: Dotenv): BotConfig {
    val discordToken = dotenv["DISCORD_TOKEN"] ?: error("NO DISCORD_TOKEN FOUND!")

    return BotConfig(
        discordToken = discordToken,
        geminiAPIKey = dotenv["GEMINI_API_KEY"] ?: error("NO GEMINI_API_KEY FOUND!"),
        githubToken = dotenv["GITHUB_TOKEN"] ?: error("NO GITHUB_TOKEN FOUND!"),
        logChannelTechnical = dotenv["LOG_CHANNEL_TECHNICAL"]?.toULongOrNull()?.let { Snowflake(it) },
        logChannelDeletions = dotenv["LOG_CHANNEL_DELETIONS"]?.toULongOrNull()?.let { Snowflake(it) },
        logChannelEdits = dotenv["LOG_CHANNEL_EDITS"]?.toULongOrNull()?.let { Snowflake(it) },
        logChannelMembers = dotenv["LOG_CHANNEL_MEMBERS"]?.toULongOrNull()?.let { Snowflake(it) },
        logChannelModActions = dotenv["LOG_CHANNEL_MOD_ACTIONS"]?.toULongOrNull()?.let { Snowflake(it) },
        logChannelBotStatus = dotenv["LOG_CHANNEL_BOT_STATUS"]?.toULongOrNull()?.let { Snowflake(it) },
        logChannelAiSuspicions = dotenv["LOG_CHANNEL_AI_SUSPICIONS"]?.toULongOrNull()?.let { Snowflake(it) }
    )
}
