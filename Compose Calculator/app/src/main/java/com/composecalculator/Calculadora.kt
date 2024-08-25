package com.composecalculator
/**
 * Laboratorio 2 - Programación de Plataformas Móviles
 * Descripción: Creación de una calculadora científica con la capacidad de resolver potencias y raíces usando postfix.
 * Autores: Leonardo Mejía; Maria José Girón; Cristian Túnchez
 * Fecha: 30 de julio de 2024
 * Fuentes: HoodLab. (11 de mayo de 2022). Lets code a calculator app in Android Studio| InFix to post fix (Video). YouTube. https://www.youtube.com/watch?v=vepeiRB31mU
 */
import java.util.Stack
import kotlin.math.*

class Calculadora {

    // Método para evaluar una expresión aritmética
    fun evaluar(expresion: String): Double {
        // Convertimos la expresión en un array de caracteres sin espacios
        val tokens = expresion.replace(" ", "").toCharArray()
        // Pila para valores numéricos
        val valores = Stack<Double>()
        // Pila para operadores
        val ops = Stack<Char>()

        var i = 0
        while (i < tokens.size) {
            // Si el token es un número o un punto decimal
            if (tokens[i].isDigit() || tokens[i] == '.') {
                val sb = StringBuilder()
                // Extraer el número completo (incluyendo decimales)
                while (i < tokens.size && (tokens[i].isDigit() || tokens[i] == '.')) {
                    sb.append(tokens[i++])
                }
                // Convertir y almacenar el número en la pila de valores
                valores.push(sb.toString().toDouble())
                i--
                // Si el token es un paréntesis de apertura
            } else if (tokens[i] == '(') {
                ops.push(tokens[i])
                // Si el token es un paréntesis de cierre
            } else if (tokens[i] == ')') {
                // Procesar la expresión dentro de los paréntesis
                while (ops.isNotEmpty() && ops.peek() != '(') {
                    valores.push(aplicarOperacion(ops.pop(), valores.pop(), valores.pop()))
                }
                // Eliminar el paréntesis de apertura de la pila de operadores
                if (ops.isNotEmpty()) ops.pop()
                // Si el token es el operador de raíz cuadrada
            } else if (tokens[i] == '√') {
                ops.push(tokens[i])
                // Si el token es un operador aritmético
            } else if (tokens[i] in listOf('+', '-', '*', '/', '^')) {
                // Aplicar operadores de mayor o igual precedencia
                while (ops.isNotEmpty() && tienePrecedencia(tokens[i], ops.peek())) {
                    valores.push(aplicarOperacion(ops.pop(), valores.pop(), valores.pop()))
                }
                ops.push(tokens[i])
            }
            i++
        }

        // Procesar los operadores restantes
        while (ops.isNotEmpty()) {
            valores.push(aplicarOperacion(ops.pop(), valores.pop(), valores.pop()))
        }

        // Retornar el resultado final
        return if (valores.isNotEmpty()) valores.pop() else 0.0
    }

    // Método para aplicar una operación a dos valores
    private fun aplicarOperacion(op: Char, b: Double, a: Double): Double {
        return when (op) {
            '+' -> a + b
            '-' -> a - b
            '*' -> a * b
            '/' -> {
                if (b == 0.0) throw UnsupportedOperationException("No se puede dividir por cero")
                a / b
            }
            '^' -> a.pow(b)
            '√' -> sqrt(b)
            else -> throw UnsupportedOperationException("Operación no soportada")
        }
    }

    // Método para determinar la precedencia de los operadores
    private fun tienePrecedencia(op1: Char, op2: Char): Boolean {
        if (op2 == '(' || op2 == ')') return false
        if ((op1 == '*' || op1 == '/' || op1 == '^' || op1 == '√') && (op2 == '+' || op2 == '-')) return false
        return true
    }
}

// Función para probar la calculadora
fun main() {
    val calculadora = Calculadora()
    val expresion = "(454+(34/2)^3)+5"
    try {
        val resultado = calculadora.evaluar(expresion)
        println("El resultado de la expresión es: $resultado")
    } catch (e: Exception) {
        println("Error al evaluar la expresión, verifique numero de operandos, operadores y parentesis. ${e.message}.")
    }
}
