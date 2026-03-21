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
                    String resultado = "";
                    while (true){
                        Tokens tokens = lexer.yylex();
                        if (tokens == null){
                            resultado += "--------FIN DEL ANALISIS--------";
                            txtResultado.setText(resultado);
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

                            case ERROR:
                                resultado += String.format("%-22s | %s (No definido)\n", "[ERROR]", lexer.lexeme);
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
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Analizador Lexico");
        FrmPrincipal principal = new FrmPrincipal();
        frame.setContentPane(principal.Main);

        frame.setPreferredSize(new Dimension(800, 600));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
