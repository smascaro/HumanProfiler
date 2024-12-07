package mas.ca.humanprofiler.data.utils

import mas.ca.humanprofiler.domain.entities.Name

object NameSanitizer {
    fun sanitize(name: Name): Name {
        val trimmedName = name.value
            .trim()
            .replace(multiSpaceRegex, " ")
        return if (trimmedName.any { !isCharacterAllowed(it) }) {
            throw IllegalArgumentException("Name contains invalid characters")
        } else {
            Name(trimmedName)
        }
    }

    private fun isCharacterAllowed(character: Char): Boolean {
        return when {
            character.isLetterOrDigit() -> true
            character.isWhitespace() -> true
            character in allowedSpecialCharacters -> true
            else -> false
        }.also {
            println("Character: $character, Allowed: $it")
        }
    }

    private val allowedSpecialCharacters = arrayOf('-', '\'')
    private val multiSpaceRegex = "\\s+".toRegex()
}