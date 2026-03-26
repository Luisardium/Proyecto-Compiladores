package Codigo;

import javax.swing.*;
import java.io.File;

public class Main {
    public static void main(String[] args) {
       String ruta = "src/Codigo/Lexer.flex";
       generarLexer(ruta);

    }

    public static void generarLexer(String ruta){
        File archivo = new File(ruta);
        try {
            JFlex.Main.generate(archivo);

            System.out.println("Lexer generado exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al generar: " + e.getMessage());
        }
    }
}
