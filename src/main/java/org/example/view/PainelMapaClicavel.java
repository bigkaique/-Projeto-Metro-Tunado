package org.example.view;

import org.example.model.MapaMetro;

/*
 * Importações:
 * - javax.swing / java.awt: componentes e utilitários gráficos (painel, imagens, cores, etc.).
 * - java.awt.event: tratamento de eventos de mouse.
 * - java.awt.geom.Point2D: utilitário geométrico usado para efeitos visuais (gradiente radial).
 * - java.util.HashMap / List: coleções usadas para mapear estações e armazenar rotas.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;

public class PainelMapaClicavel extends JPanel {

    
    private Image imagemDoMapa;
    private MapaMetro mapaMetro;

    // Área de texto na view onde mensagens e resultados são exibidos
    private JTextArea areaResultado;

    // Mapeia o nome da estação para suas coordenadas na imagem original (sistema de coordenadas da imagem)
    private HashMap<String, Point> coordenadasEstacoes;

    // Estados do clique: origem (primeiro clique) e destino (segundo clique)
    private String estacaoOrigem;
    private String estacaoDestino;

    // Rota calculada (lista de nomes de estações) após a chamada ao mapaMetro.calcularRota(...)
    private List<String> rotaCalculada; 
    private int imgRenderX, imgRenderY, imgRenderW, imgRenderH;

    // Caminho relativo (no projeto) para o arquivo de imagem do mapa. Pode ser alterado conforme a estrutura do projeto.
    private final String NOME_IMAGEM_MAPA = "src/mTunado.jpg";

    public PainelMapaClicavel(MapaMetro mapa, JTextArea areaResultado) {
        // Guarda referências passadas pelo construtor
        this.mapaMetro = mapa;
        this.areaResultado = areaResultado;

        // Inicializa a estrutura de coordenadas e estados de seleção
        this.coordenadasEstacoes = new HashMap<>();
        this.estacaoOrigem = null; 
        this.estacaoDestino = null; 
        this.rotaCalculada = null; 

        // -------------------------
        // Carrega a imagem do mapa
        // -------------------------
        // O código tenta criar um ImageIcon a partir do caminho e extrair a Image.
        // Em alguns casos a imagem pode não carregar corretamente (arquivo ausente ou recurso inválido).
        // Por isso há verificações e um bloco catch que atualiza a área de resultado em caso de falha.
        try {
            // Cria um ImageIcon e obtém um Image (objeto que podemos desenhar)
            this.imagemDoMapa = new ImageIcon(NOME_IMAGEM_MAPA).getImage();

            // Verifica rapidamente se a imagem parece válida
            if (imagemDoMapa != null) {
                // A linha abaixo reconstrói um ImageIcon a partir da imagem — é redundante,
                // mas foi preservada para ficar fiel ao código original.
                new ImageIcon(imagemDoMapa).getImage();

                // getWidth(null) retorna -1 quando a imagem não foi completamente carregada.
                // Nesse caso, lançamos uma exceção para tratar o erro no catch.
                if (imagemDoMapa.getWidth(null) == -1) {
                    throw new Exception("Falha ao carregar a imagem (getWidth = -1)");
                }
            } else {
                // Se a construção do ImageIcon retornou null por algum motivo, trata como erro.
                throw new Exception("ImageIcon retornou nulo.");
            }
        } catch (Exception e) {
            // Em caso de erro ao carregar a imagem, zera a referência e avisa o usuário
            this.imagemDoMapa = null;
            areaResultado.setText("Erro ao carregar a imagem '" + NOME_IMAGEM_MAPA + "': " + e.getMessage());
        }

        // Linha 1 - Azul

        coordenadasEstacoes.put("Tucuruvi", new Point(946, 486));
        coordenadasEstacoes.put("Parada Inglesa", new Point(946, 515));
        coordenadasEstacoes.put("Jardim São Paulo-Ayrton Senna", new Point(946, 542));
        coordenadasEstacoes.put("Santana", new Point(946, 571));
        coordenadasEstacoes.put("Carandiru", new Point(946, 598));
        coordenadasEstacoes.put("Portuguesa-Tietê", new Point(946, 626));
        coordenadasEstacoes.put("Armênia", new Point(946, 653));
        coordenadasEstacoes.put("Tiradentes", new Point(946, 684));
        coordenadasEstacoes.put("Luz", new Point(946, 804));
        coordenadasEstacoes.put("São Bento", new Point(946, 870));
        coordenadasEstacoes.put("Sé", new Point(946, 922));
        coordenadasEstacoes.put("Japão-Liberdade", new Point(946, 987));
        coordenadasEstacoes.put("São Joaquim", new Point(946, 1052));
        coordenadasEstacoes.put("Vergueiro", new Point(946, 1116));
        coordenadasEstacoes.put("Paraíso", new Point(936, 1176));
        coordenadasEstacoes.put("Ana Rosa", new Point(936, 1214));
        coordenadasEstacoes.put("Vila Mariana", new Point(945, 1261));
        coordenadasEstacoes.put("Santa Cruz", new Point(946, 1297));
        coordenadasEstacoes.put("Praça da Árvore", new Point(946, 1337));
        coordenadasEstacoes.put("Saúde-Ultrafarma", new Point(945, 1368));
        coordenadasEstacoes.put("São Judas", new Point(946, 1400));
        coordenadasEstacoes.put("Conceição", new Point(946, 1432));
        coordenadasEstacoes.put("Jabaquara", new Point(946, 1461));

        // Linha 2 - Verde
        coordenadasEstacoes.put("Vila Madalena", new Point(670, 920));
        coordenadasEstacoes.put("Sumaré", new Point(699, 949));
        coordenadasEstacoes.put("Clínicas", new Point(727, 978));
        coordenadasEstacoes.put("Consolação", new Point(774, 1024));
        coordenadasEstacoes.put("Trianon-Masp", new Point(838, 1088));
        coordenadasEstacoes.put("Brigadeiro", new Point(880, 1131));
        coordenadasEstacoes.put("Paraíso", new Point(936, 1176));
        coordenadasEstacoes.put("Ana Rosa", new Point(936, 1214));
        coordenadasEstacoes.put("Chácara Klabin", new Point(1028, 1237));
        coordenadasEstacoes.put("Santos-Imigrantes", new Point(1131, 1237));
        coordenadasEstacoes.put("Alto do Ipiranga", new Point(1176, 1196));
        coordenadasEstacoes.put("Sacomã", new Point(1229, 1142));
        coordenadasEstacoes.put("Tamanduateí", new Point(1290, 1080));
        coordenadasEstacoes.put("Vila Prudente", new Point(1309, 1043));

        // Linha 3 - Vermelha
        coordenadasEstacoes.put("Palmeiras-Barra Funda", new Point(793, 775));
        coordenadasEstacoes.put("Marechal Deodoro", new Point(819, 848));
        coordenadasEstacoes.put("Santa Cecília", new Point(838, 868));
        coordenadasEstacoes.put("República", new Point(878, 900));
        coordenadasEstacoes.put("Anhangabaú", new Point(915, 908));
        coordenadasEstacoes.put("Sé", new Point(946, 912));
        coordenadasEstacoes.put("Pedro II", new Point(1009, 908));
        coordenadasEstacoes.put("Brás", new Point(1069, 854));
        coordenadasEstacoes.put("Bresser-Mooca", new Point(1121, 864));
        coordenadasEstacoes.put("Belém", new Point(1150, 864));
        coordenadasEstacoes.put("Tatuapé", new Point(1184, 864));
        coordenadasEstacoes.put("Carrão-Assaí Atacadista", new Point(1224, 864));
        coordenadasEstacoes.put("Penha-Lojas Besni", new Point(1261, 864));
        coordenadasEstacoes.put("Vila Matilde", new Point(1297, 864));
        coordenadasEstacoes.put("Guilhermina-Esperança", new Point(1334, 864));
        coordenadasEstacoes.put("Patriarca-Vila Ré", new Point(1370, 864));
        coordenadasEstacoes.put("Artur Alvim", new Point(1407, 864));
        coordenadasEstacoes.put("Corinthians-Itaquera", new Point(1438, 854));

        // Linha 4 - Amarela
        coordenadasEstacoes.put("Luz", new Point(946, 804));
        coordenadasEstacoes.put("República", new Point(878, 900));
        coordenadasEstacoes.put("Higienópolis-Mackenzie", new Point(819, 950));
        coordenadasEstacoes.put("Paulista-Pernambucanas", new Point(760, 1008));
        coordenadasEstacoes.put("Oscar Freire", new Point(720, 1048));
        coordenadasEstacoes.put("Fradique Coutinho", new Point(655, 1048));
        coordenadasEstacoes.put("Faria Lima", new Point(601,1048 ));
        coordenadasEstacoes.put("Pinheiros", new Point(543, 1039));
        coordenadasEstacoes.put("Butantã", new Point(418, 1048));
        coordenadasEstacoes.put("São Paulo-Morumbi", new Point(364, 1048));
        coordenadasEstacoes.put("Vila Sônia-Talita de Castro", new Point(311, 1048));

        // Linha 5 - Lilás
        coordenadasEstacoes.put("Capão Redondo", new Point(303, 1402));
        coordenadasEstacoes.put("Campo Limpo", new Point(363, 1402));
        coordenadasEstacoes.put("Vila das Belezas", new Point(415, 1402));
        coordenadasEstacoes.put("Giovanni Gronchi", new Point(471, 1402));
        coordenadasEstacoes.put("Santo Amaro", new Point(541, 1402));
        coordenadasEstacoes.put("Largo Treze", new Point(575, 1402));
        coordenadasEstacoes.put("Adolfo Pinheiro", new Point(611, 1402));
        coordenadasEstacoes.put("Alto da Boa Vista", new Point(643, 1402));
        coordenadasEstacoes.put("Borba Gato", new Point(677, 1402));
        coordenadasEstacoes.put("Brooklin", new Point(713, 1402));
        coordenadasEstacoes.put("Campo Belo", new Point(765, 1402));
        coordenadasEstacoes.put("Eucaliptos", new Point(811, 1402));
        coordenadasEstacoes.put("Moema", new Point(863, 1382));
        coordenadasEstacoes.put("AACD-Servidor", new Point(891, 1354));
        coordenadasEstacoes.put("Hospital São Paulo", new Point(913, 1330));
        coordenadasEstacoes.put("Santa Cruz", new Point(945, 1298));
        coordenadasEstacoes.put("Chácara Klabin", new Point(1029, 1238));

        // Linha 7 - Rubi
        coordenadasEstacoes.put("Jundiaí", new Point(331, 378));
        coordenadasEstacoes.put("Várzea Paulista", new Point(427, 378));
        coordenadasEstacoes.put("Campo Limpo Paulista", new Point(515, 378));
        coordenadasEstacoes.put("Botujuru", new Point(547, 412));
        coordenadasEstacoes.put("Francisco Morato", new Point(577, 440));
        coordenadasEstacoes.put("Baltazar Fidélis", new Point(612, 476));
        coordenadasEstacoes.put("Franco da Rocha", new Point(612, 504));
        coordenadasEstacoes.put("Caieiras", new Point(612, 534));
        coordenadasEstacoes.put("Perus", new Point(612, 564));
        coordenadasEstacoes.put("Vila Aurora", new Point(612, 592));
        coordenadasEstacoes.put("Jaraguá", new Point(612, 622));
        coordenadasEstacoes.put("Vila Clarice", new Point(612, 650));
        coordenadasEstacoes.put("Pirituba", new Point(612, 678));
        coordenadasEstacoes.put("Piqueri", new Point(612, 708));
        coordenadasEstacoes.put("Lapa", new Point(693, 786));
        coordenadasEstacoes.put("Água Branca", new Point(731, 786));
        coordenadasEstacoes.put("Palmeiras-Barra Funda", new Point(793, 775));
        coordenadasEstacoes.put("Luz", new Point(946, 804));
        coordenadasEstacoes.put("Brás", new Point(1069, 854));

        // Linha 8 - Diamante
        coordenadasEstacoes.put("Amador Bueno", new Point(88, 785));
        coordenadasEstacoes.put("Ambuita", new Point(114, 785));
        coordenadasEstacoes.put("Santa Rita", new Point(138, 785));
        coordenadasEstacoes.put("Itapevi", new Point(168, 805));
        coordenadasEstacoes.put("Engenheiro Cardoso", new Point(197, 805));
        coordenadasEstacoes.put("Sagrado Coração", new Point(221, 805));
        coordenadasEstacoes.put("Jandira", new Point(247, 805));
        coordenadasEstacoes.put("Jardim Silveira", new Point(272, 805));
        coordenadasEstacoes.put("Jardim Belval", new Point(295, 805));
        coordenadasEstacoes.put("Barueri", new Point(321, 805));
        coordenadasEstacoes.put("Antônio João", new Point(344, 805));
        coordenadasEstacoes.put("Santa Terezinha", new Point(369, 805));
        coordenadasEstacoes.put("Carapicuíba", new Point(394, 805));
        coordenadasEstacoes.put("General Miguel Costa", new Point(419, 805));
        coordenadasEstacoes.put("Quitaúna", new Point(443, 805));
        coordenadasEstacoes.put("Comandante Sampaio", new Point(467, 805));
        coordenadasEstacoes.put("Osasco", new Point(493, 805));
        coordenadasEstacoes.put("Presidente Altino", new Point(526, 805));
        coordenadasEstacoes.put("Imperatriz Leopoldina", new Point(623, 805));
        coordenadasEstacoes.put("Domingos de Moraes", new Point(671, 805));
        coordenadasEstacoes.put("Lapa", new Point(693, 786));
        coordenadasEstacoes.put("Palmeiras-Barra Funda", new Point(793, 775));
        coordenadasEstacoes.put("Júlio Prestes", new Point(835, 805));

        // Linha 9 - Esmeralda
        coordenadasEstacoes.put("Varginha", new Point(543, 1650));
        coordenadasEstacoes.put("Mendes-Vila Natal", new Point(543, 1618));
        coordenadasEstacoes.put("Grajaú", new Point(543, 1585));
        coordenadasEstacoes.put("Primavera-Interlagos", new Point(543, 1552));
        coordenadasEstacoes.put("Autódromo", new Point(543, 1520));
        coordenadasEstacoes.put("Jurubatuba", new Point(543, 1487));
        coordenadasEstacoes.put("Socorro", new Point(543, 1454));
        coordenadasEstacoes.put("Santo Amaro", new Point(541, 1402));
        coordenadasEstacoes.put("João Dias", new Point(543, 1366));
        coordenadasEstacoes.put("Granja Julieta", new Point(543, 1320));
        coordenadasEstacoes.put("Morumbi", new Point(543, 1273));
        coordenadasEstacoes.put("Berrini", new Point(543, 1228));
        coordenadasEstacoes.put("Vila Olímpia", new Point(543, 1181));
        coordenadasEstacoes.put("Cidade Jardim", new Point(543, 1135));
        coordenadasEstacoes.put("Hebraica-Rebouças", new Point(543, 1088));
        coordenadasEstacoes.put("Pinheiros", new Point(543, 1039));
        coordenadasEstacoes.put("Cidade Universitária", new Point(543, 984));
        coordenadasEstacoes.put("Villa-Lobos-Jaguaré", new Point(543, 941));
        coordenadasEstacoes.put("Ceasa", new Point(543, 898));
        coordenadasEstacoes.put("Presidente Altino", new Point(526, 805));
        coordenadasEstacoes.put("Osasco", new Point(493, 805));


        // Linha 10 - Turquesa
        coordenadasEstacoes.put("Rio Grande da Serra", new Point(1610, 1400));
        coordenadasEstacoes.put("Ribeirão Pires", new Point(1571, 1361));
        coordenadasEstacoes.put("Guapituba", new Point(1545, 1336));
        coordenadasEstacoes.put("Mauá", new Point(1518, 1310));
        coordenadasEstacoes.put("Capuava", new Point(1492, 1284));
        coordenadasEstacoes.put("Prefeito Celso Daniel-Santo André", new Point(1450, 1240));
        coordenadasEstacoes.put("Prefeito Saladino", new Point(1410, 1200));
        coordenadasEstacoes.put("Utinga", new Point(1375, 1165));
        coordenadasEstacoes.put("São Caetano do Sul", new Point(1340, 1132));
        coordenadasEstacoes.put("Tamanduateí", new Point(1290, 1080));
        coordenadasEstacoes.put("Ipiranga", new Point(1210, 1000));
        coordenadasEstacoes.put("Juventus Mooca", new Point(1166, 958));
        coordenadasEstacoes.put("Brás", new Point(1069, 854));


        // Linha 11 - Coral
        coordenadasEstacoes.put("Palmeiras-Barra Funda", new Point(793, 775));
        coordenadasEstacoes.put("Luz", new Point(946, 804));
        coordenadasEstacoes.put("Brás", new Point(1069, 854));
        coordenadasEstacoes.put("Tatuapé", new Point(1184, 844));
        coordenadasEstacoes.put("Corinthians-Itaquera", new Point(1438, 854));
        coordenadasEstacoes.put("Dom Bosco", new Point(1482, 854));
        coordenadasEstacoes.put("José Bonifácio", new Point(1514, 854));
        coordenadasEstacoes.put("Guaianases", new Point(1544, 854));
        coordenadasEstacoes.put("Antônio Gianetti Neto", new Point(1568, 829));
        coordenadasEstacoes.put("Ferraz de Vasconcelos", new Point(1588, 810));
        coordenadasEstacoes.put("Poá", new Point(1608, 790));
        coordenadasEstacoes.put("Calmon Viana", new Point(1638, 778));
        coordenadasEstacoes.put("Suzano", new Point(1668, 784));
        coordenadasEstacoes.put("Jundiapeba", new Point(1696, 784));
        coordenadasEstacoes.put("Braz Cubas", new Point(1722, 784));
        coordenadasEstacoes.put("Mogi das Cruzes", new Point(1748, 784));
        coordenadasEstacoes.put("Estudantes", new Point(1776, 784));

        // Linha 12 - Safira
        coordenadasEstacoes.put("Brás", new Point(1069, 854));
        coordenadasEstacoes.put("Tatuapé", new Point(1184, 864));
        coordenadasEstacoes.put("Engenheiro Goulart", new Point(1252, 764));
        coordenadasEstacoes.put("USP Leste", new Point(1328, 718));
        coordenadasEstacoes.put("Comendador Ermelino", new Point(1365, 718));
        coordenadasEstacoes.put("São Miguel Paulista", new Point(1402, 718));
        coordenadasEstacoes.put("Jardim Helena-Vila Mara", new Point(1440, 718));
        coordenadasEstacoes.put("Itaim Paulista", new Point(1478, 718));
        coordenadasEstacoes.put("Jardim Romano", new Point(1515, 718));
        coordenadasEstacoes.put("Engenheiro Manoel Feio", new Point(1554, 718));
        coordenadasEstacoes.put("Itaquaquecetuba", new Point(1582, 730));
        coordenadasEstacoes.put("Aracaré", new Point(1602, 752));
        coordenadasEstacoes.put("Calmon Viana", new Point(1638, 778));

        // Linha 13 - Jade
        coordenadasEstacoes.put("Engenheiro Goulart", new Point(1252, 764));
        coordenadasEstacoes.put("Guarulhos-Cecap", new Point(1528, 398));
        coordenadasEstacoes.put("Aeroporto-Guarulhos", new Point(1528, 326));


        // Linha 15 - Prata
        coordenadasEstacoes.put("Vila Prudente", new Point(1309, 1043));
        coordenadasEstacoes.put("Oratório", new Point(1347, 1043));
        coordenadasEstacoes.put("São Lucas", new Point(1382, 1043));
        coordenadasEstacoes.put("Camilo Haddad", new Point(1416, 1043));
        coordenadasEstacoes.put("Vila Tolstói", new Point(1450, 1043));
        coordenadasEstacoes.put("Vila União", new Point(1484, 1043));
        coordenadasEstacoes.put("Jardim Planalto", new Point(1516, 1043));
        coordenadasEstacoes.put("Sapopemba", new Point(1550, 1043));
        coordenadasEstacoes.put("Fazenda da Juta", new Point(1586, 1043));
        coordenadasEstacoes.put("São Mateus", new Point(1630, 1043));
        coordenadasEstacoes.put("Jardim Colonial", new Point(1672, 1043));

        

        // --- "Controller" do Clique no Mapa ---
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point cliqueNaTela = e.getPoint();
                System.out.println("Clique na TELA em: (" + cliqueNaTela.x + ", " + cliqueNaTela.y + ")"); // Debug

                Point cliqueNaImagem = traduzirCliqueTelaParaImagem(cliqueNaTela);
                if (cliqueNaImagem == null) {
                    System.out.println("--> Clicou fora da imagem."); // Debug
                    return;
                }

                System.out.println("--> Traduzido para IMAGEM em: (" + cliqueNaImagem.x + ", " + cliqueNaImagem.y + ")"); // Debug

                String estacaoClicada = encontrarEstacaoProxima(cliqueNaImagem);

                if (estacaoClicada != null) {
                    System.out.println("==> ESTAÇÃO ENCONTRADA: " + estacaoClicada); // Debug
                    processarClique(estacaoClicada);
                } else {
                    System.out.println("--> Nenhuma estação próxima o suficiente."); // Debug
                }
            }
        });
    }

    // --- MÉTODOS DE TRADUÇÃO DE COORDENADAS ---

    private Point traduzirCliqueTelaParaImagem(Point cliqueTela) {
        if (imagemDoMapa == null || imgRenderW <= 0 || imgRenderH <= 0) {
            return null;
        }
        int imgOriginalW = imagemDoMapa.getWidth(null);
        int imgOriginalH = imagemDoMapa.getHeight(null);
        double relativeX = cliqueTela.x - imgRenderX;
        double relativeY = cliqueTela.y - imgRenderY;
        if (relativeX < 0 || relativeX >= imgRenderW || relativeY < 0 || relativeY >= imgRenderH) {
            return null;
        }
        double scaleFactorX = (double) imgOriginalW / imgRenderW;
        double scaleFactorY = (double) imgOriginalH / imgRenderH;
        int imageX = (int) (relativeX * scaleFactorX);
        int imageY = (int) (relativeY * scaleFactorY);
        return new Point(imageX, imageY);
    }

    private Point traduzirPontoImagemParaTela(Point pontoImagem) {
        if (imagemDoMapa == null || imgRenderW <= 0 || imgRenderH <= 0) {
            return pontoImagem;
        }
        int imgOriginalW = imagemDoMapa.getWidth(null);
        int imgOriginalH = imagemDoMapa.getHeight(null);
        if (imgOriginalW <= 0 || imgOriginalH <= 0) return pontoImagem;
        double scaleFactorX = (double) imgRenderW / imgOriginalW;
        double scaleFactorY = (double) imgRenderH / imgOriginalH;
        int telaX = imgRenderX + (int) (pontoImagem.x * scaleFactorX);
        int telaY = imgRenderY + (int) (pontoImagem.y * scaleFactorY);
        return new Point(telaX, telaY);
    }

    // --- MÉTODOS DE LÓGICA  ---

    private String encontrarEstacaoProxima(Point pontoClicadoNaImagem) {
        String estacaoMaisProxima = null;
        double menorDistancia = Double.MAX_VALUE;
       
        final double TOLERANCIA = 15.0;

        for (String nomeEstacao : coordenadasEstacoes.keySet()) {
            Point pontoEstacao = coordenadasEstacoes.get(nomeEstacao);
            if (pontoEstacao == null) continue;
            double distancia = pontoClicadoNaImagem.distance(pontoEstacao);

            System.out.println("Debug: Distância de " + pontoEstacao + " ("+nomeEstacao+") = " + distancia); // Debug

            if (distancia < menorDistancia) {
                menorDistancia = distancia;
                estacaoMaisProxima = nomeEstacao;
            }
        }

        System.out.println("--> Menor distância: " + menorDistancia + " para " + estacaoMaisProxima); // Debug

        if (menorDistancia <= TOLERANCIA) {
            return estacaoMaisProxima;
        }
        return null;
    }

    private void processarClique(String estacaoClicada) {
        if (estacaoOrigem == null) {
            estacaoOrigem = estacaoClicada;
            areaResultado.setText("Origem definida: " + estacaoOrigem + "\nPor favor, clique no destino.");
            estacaoDestino = null;
            rotaCalculada = null;
        }
        else {
            if (estacaoOrigem.equals(estacaoClicada)) return;
            estacaoDestino = estacaoClicada;
            areaResultado.setText("Calculando rota de: " + estacaoOrigem + " -> " + estacaoDestino);
            try {
                this.rotaCalculada = mapaMetro.calcularRota(estacaoOrigem, estacaoDestino);
                areaResultado.append("\n\n--- Rota Encontrada ---\n");
                for (String estacao : this.rotaCalculada) {
                    areaResultado.append("  -> " + estacao + "\n");
                }
                areaResultado.append("\nTotal de estações: " + this.rotaCalculada.size());
            } catch (Exception ex) {
                areaResultado.setText("ERRO: \n" + ex.getMessage());
                this.rotaCalculada = null;
            }
            estacaoOrigem = null;
            estacaoDestino = null;
        }
        repaint();
    }


    // METODO DE DESENHO
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (this.getWidth() <= 0 || this.getHeight() <= 0) {
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            return;
        }

        
        int PADDING = 10;

        int areaDisponivelW = this.getWidth() - (PADDING * 2);
        int areaDisponivelH = this.getHeight() - (PADDING * 2);

        // 1. Desenha o mapa (fundo)
        if (imagemDoMapa != null && imagemDoMapa.getWidth(null) > 0 && areaDisponivelW > 0 && areaDisponivelH > 0) {
            double imgAspect = (double) imagemDoMapa.getHeight(null) / imagemDoMapa.getWidth(null);
            double panelAspect = (double) areaDisponivelH / areaDisponivelW;
            if (imgAspect > panelAspect) {
                imgRenderH = areaDisponivelH;
                imgRenderW = (int) (imgRenderH / imgAspect);
                imgRenderX = (this.getWidth() - imgRenderW) / 2;
                imgRenderY = PADDING;
            } else {
                imgRenderW = areaDisponivelW;
                imgRenderH = (int) (imgRenderW * imgAspect);
                imgRenderX = PADDING;
                imgRenderY = (this.getHeight() - imgRenderH) / 2;
            }
            g.drawImage(imagemDoMapa, imgRenderX, imgRenderY, imgRenderW, imgRenderH, this);
        } else {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
            imgRenderX = 0; imgRenderY = 0; imgRenderW = 0; imgRenderH = 0;
            if (imagemDoMapa == null) {
                g.setColor(Color.WHITE);
                g.drawString("Imagem '" + NOME_IMAGEM_MAPA + "' não encontrada na raiz do projeto.", 10, 20);
            } else if (areaDisponivelW <= 0 || areaDisponivelH <= 0) {
                g.setColor(Color.YELLOW);
                g.drawString("Erro: O PADDING (" + PADDING + ") é muito grande para o tamanho da tela.", 10, 40);
            }
        }

        // 2. Desenha a Rota
        if (rotaCalculada != null && !rotaCalculada.isEmpty()) {
            // 2a. EFEITO DE LUZ (GLOW)
            for (String nomeEstacao : rotaCalculada) {
                Point pImagem = coordenadasEstacoes.get(nomeEstacao);
                if (pImagem == null) continue;
                Point pTela = traduzirPontoImagemParaTela(pImagem);
                Point2D center = new Point2D.Float(pTela.x, pTela.y);
                float radius = 15f;
                float[] dist = {0.0f, 1.0f};
                Color[] colors = {new Color(255, 255, 200, 200), new Color(255, 200, 0, 0)};
                RadialGradientPaint paint = new RadialGradientPaint(center, radius, dist, colors);
                g2d.setPaint(paint);
                g2d.fillOval(pTela.x - (int)radius, pTela.y - (int)radius, (int)radius * 2, (int)radius * 2);
            }
            // 2b. LINHAS DA ROTA
            g2d.setColor(new Color(255, 0, 80, 180));
            g2d.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            Point pontoTelaAnterior = null;
            for (String nomeEstacao : rotaCalculada) {
                Point pImagem = coordenadasEstacoes.get(nomeEstacao);
                if (pImagem == null) continue;
                Point pontoTelaAtual = traduzirPontoImagemParaTela(pImagem);
                if (pontoTelaAnterior != null) {
                    g2d.drawLine(pontoTelaAnterior.x, pontoTelaAnterior.y, pontoTelaAtual.x, pontoTelaAtual.y);
                }
                pontoTelaAnterior = pontoTelaAtual;
            }
        }

        // 3. Desenha Marcador de Origem 
        g2d.setStroke(new BasicStroke(3));
        if (estacaoOrigem != null && estacaoDestino == null) {
            Point pImagem = coordenadasEstacoes.get(estacaoOrigem);
            if (pImagem != null) {
                Point pTela = traduzirPontoImagemParaTela(pImagem);
                g2d.setColor(Color.GREEN.darker());
                g2d.drawOval(pTela.x - 8, pTela.y - 8, 16, 16);
            }
        }
    }
}