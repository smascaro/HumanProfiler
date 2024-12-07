package mas.ca.humanprofiler.data.utils

import mas.ca.humanprofiler.domain.entities.Name
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class NameSanitizerTest {

    private val nameSanitizer = NameSanitizer

    @Test
    fun `sanitize() handles basic names correctly`() {
        assertSanitizedName("John Doe", "John Doe")
        assertSanitizedName("Fernando", "Fernando")
    }

    @Test
    fun `sanitize() handles names with special characters`() {
        assertSanitizedName("Jean-Pierre", "Jean-Pierre")
        assertSanitizedName("O'Brien", "O'Brien")
        assertSanitizedName("Müller", "Müller")
        assertSanitizedName("García", "García")
    }

    @Test
    fun `sanitize() handles names with multiple spaces`() {
        assertSanitizedName("John Doe", "John   Doe")
        assertSanitizedName("Mary Jane Smith", " Mary  Jane   Smith ")
    }

    @Test
    fun `sanitize() handles names with leading and trailing spaces`() {
        assertSanitizedName("John Doe", "  John Doe  ")
        assertSanitizedName("Jane Smith", "   Jane Smith")
    }

    @Test
    fun `sanitize() throws an exception on empty names`() {
        assertThrows(IllegalArgumentException::class.java) {
            nameSanitizer.sanitize(Name(""))
        }
        assertThrows(IllegalArgumentException::class.java) {
            nameSanitizer.sanitize(Name("    "))
        }
    }

    @Test
    fun `sanitize() handles names with numbers`() {
        assertSanitizedName("John Doe 3rd", "John Doe 3rd") // Allows numbers
        assertSanitizedName("Agent 47", "Agent 47")
    }

    @Test
    fun `sanitize() handles names with hyphens and apostrophes`() {
        assertSanitizedName("Mary-Kate Olsen", "Mary-Kate Olsen")
        assertSanitizedName("D'Angelo", "D'Angelo")
    }

    @Test
    fun `sanitize() handles names with diacritics`() {
        assertSanitizedName("Åke", "Åke")
        assertSanitizedName("Çınar", "Çınar")
        assertSanitizedName("Élise", "Élise")
        assertSanitizedName("Ñusta", "Ñusta")
        assertSanitizedName("Øyvind", "Øyvind")
        assertSanitizedName("Ümit", "Ümit")
    }

    @Test
    fun `sanitize() handles names with Cyrillic characters`() {
        assertSanitizedName("Иван Иванов", "Иван Иванов")
        assertSanitizedName("Анна Петрова", "Анна Петрова")
    }

    @Test
    fun `sanitize() handles names with Chinese characters`() {
        assertSanitizedName("李小龙", "李小龙")
        assertSanitizedName("成龙", "成龙")
    }

    @Test
    fun `sanitize() handles names with Japanese characters`() {
        assertSanitizedName("田中太郎", "田中太郎")
        assertSanitizedName("佐藤花子", "佐藤花子")
    }

    @Test
    fun `sanitize() handles names with Korean characters`() {
        assertSanitizedName("김철수", "김철수")
        assertSanitizedName("박영희", "박영희")
    }

    private fun assertSanitizedName(expected: String, input: String) {
        assertEquals(Name(expected), nameSanitizer.sanitize(Name(input)))
    }
}
