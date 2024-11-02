package com.amberj

import com.amberj.lexer.Lexer
import com.amberj.lexer.token.Token
import com.amberj.lexer.token.TokenType

fun main() {
    val code = """
        const x = 5
        
        console.log(x)
    """.trimIndent()


    val lexer = Lexer(code);
    val tokens = ArrayList<Token>()

    var token: Token

    do {
        token = lexer.nextToken()
        tokens.add(token)
    } while (token.tokenType !== TokenType.EOF)


    println(tokens)
}