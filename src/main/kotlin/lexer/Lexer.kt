package com.amberj.lexer

import com.amberj.lexer.token.DataType
import com.amberj.lexer.token.Token
import com.amberj.lexer.token.TokenType

class Lexer(
    val code: String
) {
    private val keywords = setOf("const", "let", "var", "if", "for", "while", "switch", "console", "log")
    private val operations = setOf("+", "-", "*", "/", "%", "=")
    private val punctuations = setOf("\"", "'", "$", "{", "}", "(", ")", ".", "&", "|")

    private var position = 0
    private var isStringStarted = false


    fun nextToken(): Token {
        skipWhitespace()

        if(position >= code.length) {
            return Token("", TokenType.EOF, DataType.Special)
        }

        val currentChar = code[position]

        if (currentChar == '"' || currentChar == '\'') {
            isStringStarted = !isStringStarted
        }

        if (punctuations.contains(currentChar.toString())) {
            val punct: String = code.substring(position, position + 1)
            position++
            return Token(punct, TokenType.PUNCTUATION, DataType.Special)
        }

        if (isDigit(currentChar)) {
            val start = position
            while (position < code.length && isDigit(code[position])) {
                position++
            }
            val value: String = code.substring(start, position)
            return Token(value, TokenType.LITERAL,  DataType.Int)
        }

        if (isLetter(currentChar)) {
            val start = position
            while (position < code.length && isLetterOrDigit(code[position])) {
                position++
            }
            val value: String = code.substring(start, position)
            return if (keywords.contains(value)) {
                Token(value, TokenType.KEYWORD, DataType.Special)
            } else if (isStringStarted) {
                Token(value, TokenType.LITERAL, DataType.String)
            } else {
                Token(value, TokenType.IDENTIFIER, DataType.Special)
            }
        }

        if (operations.contains(currentChar.toString())) {
            val operator: String = code.substring(position, position + 1)
            position++
            return Token(operator, TokenType.OPERATOR, DataType.Special)
        }

        throw IllegalArgumentException("Unexpected character: $currentChar")

    }

    private fun isLetter(c: Char): Boolean {
        return Character.isLetter(c)
    }

    private fun isLetterOrDigit(c: Char): Boolean {
        return Character.isLetterOrDigit(c)
    }

    private fun isDigit(c: Char): Boolean {
        return Character.isDigit(c)
    }

    private fun skipWhitespace() {
        while (position < code.length && Character.isWhitespace(code.get(position))) {
            position++
        }
    }
}