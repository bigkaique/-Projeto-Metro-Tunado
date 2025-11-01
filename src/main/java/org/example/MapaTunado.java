package org.example;

import org.example.model.MapaMetro;
import org.example.view.ViewCalculadoraRota;

import javax.swing.SwingUtilities;


public class MapaTunado {


    public static void main(String[] args) {

        // SwingUtilities.invokeLater garante que a interface gráfica (alguns itens eu recorri ao chat para entender)
        // seja criada e executada na "thread" correta do Java para GUIs.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {


                System.out.println("Carregando dados do mapa...");
                MapaMetro mapa = new MapaMetro();
                System.out.println("Mapa carregado com sucesso!");

                //  Cria a View (a Janela)
                ViewCalculadoraRota view = new ViewCalculadoraRota(mapa);

                //  Mostra a janela para o usuário
                System.out.println("Iniciando interface gráfica...");
                view.setVisible(true);
            }
        });
    }
}