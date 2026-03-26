package Codigo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


public class FrmPrincipal {
    private JTextArea txtEntrada;
    private JButton btnAnallizar;
    private JTextArea txtResultado;
    private JPanel Main;
    private JButton btnLimpiar;
    private JTextArea txtTablaErrores;

    public FrmPrincipal() {

        btnAnallizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                File archivo = new File("Archivo.txt");
                PrintWriter escribir;
                try {
                    escribir = new PrintWriter(archivo);
                    escribir.print(txtEntrada.getText());
                    escribir.close();
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

                try {
                    Reader lector = new BufferedReader(new FileReader("Archivo.txt"));
                    Lexer lexer = new Lexer(lector);


                    // Preparamos los encabezados de nuestras dos tablas
                    String resultado = String.format("%-22s | %s\n", "CATEGORÍA", "LEXEMA");
                    resultado += "-----------------------|-----------------------\n";

                    String tablaErrores = String.format("\n%-20s | %-15s | %s\n", "TIPO DE ERROR", "LEXEMA", "DESCRIPCIÓN");
                    tablaErrores += "---------------------|-----------------|-----------------------------------\n";

                    int contadorErrores = 0;
                    while (true){
                        Tokens tokens = lexer.yylex();

                        if (tokens == null){

                            txtResultado.setText(resultado + "\n=== FIN DEL ANÁLISIS ===");
                                // Al terminar, si hubo errores, pegamos la tabla de errores abajo
                                if (contadorErrores > 0) {
                                    txtTablaErrores.setText("=== SE ENCONTRARON " + contadorErrores + " ERRORES LÉXICOS ===\n\n" + tablaErrores);
                                } else {
                                    txtTablaErrores.setText(tablaErrores + "\n=== ANÁLISIS EXITOSO: 0 ERRORES ===");
                                }
                                return;
                        }
                        switch (tokens){

                            case Reservadas:
                                resultado += String.format("%-22s | %s\n", "[PALABRA RESERVADA]", lexer.lexeme);
                                break;

                            case Identificador:
                                resultado += String.format("%-22s | %s\n", "[ID]", lexer.lexeme);
                                break;

                            case Numero:
                                resultado += String.format("%-22s | %s\n", "[NUMERO ENTERO]", lexer.lexeme);
                                break;

                            case Cadena:
                                resultado += String.format("%-22s | %s\n", "[CADENA]", lexer.lexeme);
                                break;

                            case Asignacion:
                                resultado += String.format("%-22s | %s\n", "[ASIGNACION]", lexer.lexeme);
                                break;

                            case Relacional:
                                resultado += String.format("%-22s | %s\n", "[OP. RELACIONAL]", lexer.lexeme);
                                break;

                            case Simbolo:
                                resultado += String.format("%-22s | %s\n", "[SIMBOLO]", lexer.lexeme);
                                break;

                            case Suma:
                            case Resta:
                            case Multiplicacion:
                            case Division:
                                resultado += String.format("%-22s | %s\n", "[OP. ARITMETICO]", lexer.lexeme);
                                break;

                                /*  Manejo de errores anterior a Tabla de errores
                           case ERROR:
                            resultado += String.format("%-22s | %s (No definido)\n", "[ERROR]", lexer.lexeme);
                               break;
                             --- CASOS DE ERROR (Van a la tabla de errores) ---

                                 */

                            case ERROR_LONGITUD:
                                tablaErrores += String.format("%-20s | %-15s | %s\n", "Longitud Excedida", lexer.lexeme, "El identificador supera los 10 caracteres.");
                                contadorErrores++;
                                break;
                            case ERROR_RANGO:
                                tablaErrores += String.format("%-20s | %-15s | %s\n", "Fuera de Rango", lexer.lexeme, "El número no está entre 0 y 100.");
                                contadorErrores++;
                                break;
                            case ERROR_CADENA:
                                tablaErrores += String.format("%-20s | %-15s | %s\n", "Cadena Inválida", lexer.lexeme, "La cadena no contiene la secuencia 'asdfg'.");
                                contadorErrores++;
                                break;
                            case ERROR_DESCONOCIDO:
                                tablaErrores += String.format("%-20s | %-15s | %s\n", "Carácter Inválido", lexer.lexeme, "Símbolo no pertenece al lenguaje.");
                                contadorErrores++;
                                break;
                            default:
                                resultado += String.format("%-22s | %s\n", "[DESCONOCIDO]", lexer.lexeme);
                                break;
                        }

                    }
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtEntrada.setText("");
                txtResultado.setText("");
                txtTablaErrores.setText("");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Analizador Lexico");
        FrmPrincipal principal = new FrmPrincipal();
        frame.setContentPane(principal.Main);

        frame.setPreferredSize(new Dimension(1000, 800));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
