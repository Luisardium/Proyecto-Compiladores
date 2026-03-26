import static codigo.Tokens.*;
%%
%class Lexer
%type Tokens

L=[a-zA-Z]
D=[0-9]
espacio=[ \t\r\n]+
cadena = \"[^\"]*\"
%{
    public String lexeme;

      // Método para saber si la palabra tiene las letras asdfg
          private boolean esCombinacionAsdfg(String texto) {
              if (texto.length() != 5) return false;
              char[] letras = texto.toLowerCase().toCharArray();
              java.util.Arrays.sort(letras);
              // a, s, d, f, g ordenadas alfabéticamente son a, d, f, g, s
              return new String(letras).equals("adfgs");
          }

%}
%%
//Ignora Espacios
{espacio} {/*Ignore*/}

//Palabras Reservadas
"if" | "else" | "for" | "print" | "int" { lexeme=yytext(); return Reservadas; }

// Operador de asignación
":=" { lexeme=yytext(); return Asignacion; }

// Operadores aritméticos
"+" { lexeme=yytext(); return Suma; }
"-" { lexeme=yytext(); return Resta; }
"*" { lexeme=yytext(); return Multiplicacion; }
"/" { lexeme=yytext(); return Division; }

// Operadores relacionales
">=" | "<=" | ">" | "<" | "=" | "<>" { lexeme=yytext(); return Relacional; }

// Símbolos especiales
"{" | "}" | "[" | "]" | "(" | ")" | "," | ";" | ".." { lexeme=yytext(); return Simbolo; }

// Cadenas de caracteres (Solo válidas si tienen asdfg)
{cadena} {
    lexeme=yytext();
    if (lexeme.contains("asdfg")) {
        return Cadena;
    } else {
        return ERROR_CADENA; //Para la tabla de errores
    }
}

// Números (Validando que estén entre 0 y 100)
{D}+ {
    lexeme=yytext();
    try {
        int valor = Integer.parseInt(lexeme);
        if (valor >= 0 && valor <= 100) {
            return Numero;
        } else {
            return ERROR_RANGO; //Para la tabla de errores
        }
    } catch (Exception e) {
         return ERROR_RANGO; //Para la tabla de errores
    }
}

// Identificadores (Validando longitud y anagramas de asdfg)
{L}({L}|{D})* {
    lexeme=yytext();
    if (esCombinacionAsdfg(lexeme)) {
        return Reservadas;
    } else if (lexeme.length() <= 10) {
        return Identificador;
    } else {
        return ERROR_LONGITUD; //Para la tabla de errores
    }
}

// Fallo de reconocimiento
. { lexeme=yytext(); return ERROR_DESCONOCIDO; }