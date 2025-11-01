package org.example.view;

import org.example.model.MapaMetro;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Classe que representa a janela principal (View) da aplicação de calculadora de rota.
 * Herda de JFrame para criar uma janela gráfica.
 */
public class ViewCalculadoraRota extends JFrame {
    // --- Componentes da Interface Gráfica (Atributos) ---

    private JTextField txtOrigem;
    private JTextField txtDestino;
    private JButton btnCalcular;
    private JTextArea txtResultado;
    private PainelMapaClicavel painelDoMapa;

    // --- Modelo de Dados ---

    private MapaMetro mapa; // Referência ao objeto que contém a estrutura de dados do mapa e a lógica de busca (Model)

    /**
     * Construtor da View.
     * @param mapa O objeto MapaMetro que será usado para calcular as rotas.
     */
    public ViewCalculadoraRota(MapaMetro mapa) {
        this.mapa = mapa;

        // --- Configuração da Janela (JFrame) ---

        setTitle("Calculadora de Rota - Metrô/Trem");
        setSize(1920, 1080); // Define o tamanho inicial da janela (Largura x Altura)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);




        // Cria um JPanel para agrupar os campos de texto e o botão.
        // Usa FlowLayout, que organiza os componentes em uma linha (um após o outro).
        JPanel painelEntrada = new JPanel(new FlowLayout());

        painelEntrada.add(new JLabel("Digite a Origem :")); //  (Label) para a origem

        txtOrigem = new JTextField(15); // Inicializa o campo de texto de Origem com 15 colunas de largura
        painelEntrada.add(txtOrigem);

        painelEntrada.add(new JLabel("Digite o Destino :")); // (Label) para o destino

        txtDestino = new JTextField(15);
        painelEntrada.add(txtDestino); // Adiciona o campo ao painel

        btnCalcular = new JButton("Calcular Rota "); // Inicializa o botão de cálculo
        painelEntrada.add(btnCalcular); // Adiciona o botão ao painel

        add(painelEntrada, BorderLayout.NORTH); // Adiciona o painel de entradas à parte superior (Norte) da janela



        // Inicializa a área de texto que mostrará o caminho encontrado.
        txtResultado = new JTextArea(10, 0); // 10 linhas de altura, 0 colunas
        txtResultado.setEditable(false); // usuário não pode editar o texto (apenas leitura)


        // caso o texto do resultado seja muito longo.
        JScrollPane scrollPane = new JScrollPane(txtResultado);

        add(scrollPane, BorderLayout.SOUTH); // Adiciona a área de resultados à parte inferior (Sul) da janela




        //  responsável por desenhar o mapa e lidar com cliques para selecionar estações.
        painelDoMapa = new PainelMapaClicavel(this.mapa, this.txtResultado);

        add(painelDoMapa, BorderLayout.CENTER);



        //  'Listener' (ouvinte) ao botão que será notificado quando ele for clicado.
        btnCalcular.addActionListener(new ActionListener() {
            @Override
            // O metodo actionPerformed é executado toda vez que o botão é clicado.
            public void actionPerformed(ActionEvent e) {
                // Chama o metodo que contém a lógica de busca e exibição (função "Controller").
                calcularEExibirRotaPorTexto();
            }
        });

        // A janela se torna visível .
        setVisible(true);
    }

    // Metodo "Controller" para a busca por texto
    private void calcularEExibirRotaPorTexto() {
        // 1. Coleta os dados de entrada
        String origem = txtOrigem.getText(); // Pega o texto digitado no campo de origem
        String destino = txtDestino.getText(); // Pega o texto digitado no campo de destino

        try {
            // 2. Chama a lógica de cálculo (Model)
            // O metodo calcularRota do Model retorna uma lista de strings (nomes das estações).
            List<String> caminho = mapa.calcularRota(origem, destino);

            // 3. Exibe o resultado
            txtResultado.setText("--- Rota Encontrada ---\n\n"); // Limpa o JTextArea e adiciona cabeçalho

            // Itera sobre a lista de estações para formatar e exibir o caminho.
            for (String estacao : caminho) {
                txtResultado.append("  -> " + estacao + "\n"); // Adiciona cada estação em uma nova linha
            }

            // Adiciona o total de estações ao final.
            txtResultado.append("\nTotal de estações: " + caminho.size());


        } catch (IllegalArgumentException ex) {
            // Trata exceções específicas (por exemplo, estação não encontrada ou inválida)
            txtResultado.setText("ERRO: \n" + ex.getMessage());
        } catch (Exception ex) {
            // Trata quaisquer outras exceções inesperadas
            txtResultado.setText("ERRO INESPERADO: \n" + ex.getMessage());
        }
    }
}