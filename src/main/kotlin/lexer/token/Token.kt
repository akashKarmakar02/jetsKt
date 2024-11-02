package com.amberj.lexer.token

data class Token(
    val value: String,
    val tokenType: TokenType,
    val dataType: DataType
) {
}