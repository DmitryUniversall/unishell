package core.parser

import core.lexer.Token
import core.lexer.TokenType

class CommandParser(
    private val tokens: List<Token>,
) {
    private var position = 0

    fun parse(): ParsedCommand? {
        val words = mutableListOf<Word>()

        while (hasNext()) {
            if (peek().type == TokenType.WHITESPACE) {
                next()
                continue
            }

            words.add(parseWord())
        }

        if (words.isEmpty()) return null

        return ParsedCommand(name = words.first(), arguments = words.drop(1))
    }

    private fun parseWord(): Word {
        val parts = mutableListOf<WordPart>()

        while (hasNext()) {
            when (peek().type) {
                TokenType.WHITESPACE -> break
                TokenType.TEXT, TokenType.VARIABLE -> parts.add(parsePart(next()))
                TokenType.SINGLE_QUOTE -> parts.add(parseSingleQuotedLiteral(next()))
                TokenType.DOUBLE_QUOTE -> parts.addAll(parseDoubleQuotedParts(next()))
            }
        }

        return Word(parts)  // Empty quoted word
    }

    private fun parseSingleQuotedLiteral(openingQuote: Token): WordPart.Literal {
        val text = StringBuilder()

        while (hasNext()) {
            val token = next()

            if (token.type == TokenType.SINGLE_QUOTE) return WordPart.Literal(text.toString())

            text.append(token.text)
        }

        throw IllegalArgumentException("Unclosed single quote at position ${openingQuote.position + 1}.")
    }

    private fun parseDoubleQuotedParts(openingQuote: Token): List<WordPart> {
        val parts = mutableListOf<WordPart>()

        while (hasNext()) {
            val token = next()
            if (token.type == TokenType.DOUBLE_QUOTE) return parts

            parts.add(parsePart(token))
        }

        throw IllegalArgumentException("Unclosed double quote at position ${openingQuote.position + 1}.")
    }

    private fun parsePart(token: Token): WordPart =
        if (token.type == TokenType.VARIABLE) {
            WordPart.Variable(token.text.substring(1))
        } else {
            WordPart.Literal(token.text)
        }

    private fun hasNext(): Boolean = position < tokens.size

    private fun peek(): Token = tokens[position]

    private fun next(): Token {
        val token = tokens[position]
        position++
        return token
    }
}
