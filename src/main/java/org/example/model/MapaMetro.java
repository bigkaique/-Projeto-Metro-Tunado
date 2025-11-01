package org.example.model;
import java.text.Normalizer;
import java.util.List;

public class MapaMetro {
    private Grafo<String> grafo;

    public MapaMetro() {
        this.grafo = new Grafo<String>();
        carregarVertices();
        carregarArestas();
    }

    public List<String> calcularRota(String inputOrigem, String inputDestino) {

        String origemNormalizada = normalizeString(inputOrigem);
        String destinoNormalizado = normalizeString(inputDestino);

        String estacaoOrigem = encontrarNomeEstacao(origemNormalizada);
        String estacaoDestino = encontrarNomeEstacao(destinoNormalizado);

        if (estacaoOrigem == null) {
            throw new IllegalArgumentException("Estação de origem \"" + inputOrigem + "\" não encontrada.");
        }
        if (estacaoDestino == null) {
            throw new IllegalArgumentException("Estação de destino \"" + inputDestino + "\" não encontrada.");
        }

        return grafo.caminhoMaisCurto(estacaoOrigem, estacaoDestino);
    }

    private void adicionarArestas(double peso, String estacao1, String estacao2) {
        grafo.adicionarAresta(Double.valueOf(peso), estacao1, estacao2);
        grafo.adicionarAresta(Double.valueOf(peso), estacao2, estacao1);
    }

    private String normalizeString(String input) {
        if (input == null) return null;
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        // Remove acentos e converte para minúsculas
        return normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();
    }

    private String encontrarNomeEstacao(String nomeNormalizado) {
        // Usa o getter do Grafo
        for (Vertice<String> v : grafo.getVertices()) {
            String nomeReal = v.getDado();
            if (normalizeString(nomeReal).equals(nomeNormalizado)) {
                return nomeReal;
            }
        }
        return null;
    }


    private void carregarVertices() {
        grafo.adicionarVertice("Tucuruvi");
        grafo.adicionarVertice("Parada Inglesa");
        grafo.adicionarVertice("Jardim São Paulo-Ayrton Senna");
        grafo.adicionarVertice("Santana");
        grafo.adicionarVertice("Carandiru");
        grafo.adicionarVertice("Portuguesa-Tietê");
        grafo.adicionarVertice("Armênia");
        grafo.adicionarVertice("Tiradentes");
        grafo.adicionarVertice("Luz");
        grafo.adicionarVertice("São Bento");
        grafo.adicionarVertice("Sé");
        grafo.adicionarVertice("Japão-Liberdade");
        grafo.adicionarVertice("São Joaquim");
        grafo.adicionarVertice("Vergueiro");
        grafo.adicionarVertice("Paraíso");
        grafo.adicionarVertice("Ana Rosa");
        grafo.adicionarVertice("Vila Mariana");
        grafo.adicionarVertice("Santa Cruz");
        grafo.adicionarVertice("Praça da Árvore");
        grafo.adicionarVertice("Saúde-Ultrafarma");
        grafo.adicionarVertice("São Judas");
        grafo.adicionarVertice("Conceição");
        grafo.adicionarVertice("Jabaquara");

        grafo.adicionarVertice("Vila Madalena");
        grafo.adicionarVertice("Sumaré");
        grafo.adicionarVertice("Clínicas");
        grafo.adicionarVertice("Consolação");
        grafo.adicionarVertice("Trianon-Masp");
        grafo.adicionarVertice("Brigadeiro");
        grafo.adicionarVertice("Chácara Klabin");
        grafo.adicionarVertice("Santos-Imigrantes");
        grafo.adicionarVertice("Alto do Ipiranga");
        grafo.adicionarVertice("Sacomã");
        grafo.adicionarVertice("Tamanduateí");
        grafo.adicionarVertice("Vila Prudente");

        grafo.adicionarVertice("Palmeiras-Barra Funda");
        grafo.adicionarVertice("Marechal Deodoro");
        grafo.adicionarVertice("Santa Cecília");
        grafo.adicionarVertice("República");
        grafo.adicionarVertice("Anhangabaú");
        grafo.adicionarVertice("Pedro II");
        grafo.adicionarVertice("Brás");
        grafo.adicionarVertice("Bresser-Mooca");
        grafo.adicionarVertice("Belém");
        grafo.adicionarVertice("Tatuapé");
        grafo.adicionarVertice("Carrão-Assaí Atacadista");
        grafo.adicionarVertice("Penha-Lojas Besni");
        grafo.adicionarVertice("Vila Matilde");
        grafo.adicionarVertice("Guilhermina-Esperança");
        grafo.adicionarVertice("Patriarca-Vila Ré");
        grafo.adicionarVertice("Artur Alvim");
        grafo.adicionarVertice("Corinthians-Itaquera");

        grafo.adicionarVertice("Higienópolis-Mackenzie");
        grafo.adicionarVertice("Paulista-Pernambucanas");
        grafo.adicionarVertice("Oscar Freire");
        grafo.adicionarVertice("Fradique Coutinho");
        grafo.adicionarVertice("Faria Lima");
        grafo.adicionarVertice("Pinheiros");
        grafo.adicionarVertice("Butantã");
        grafo.adicionarVertice("São Paulo-Morumbi");
        grafo.adicionarVertice("Vila Sônia-Talita de Castro");

        grafo.adicionarVertice("Capão Redondo");
        grafo.adicionarVertice("Campo Limpo");
        grafo.adicionarVertice("Vila das Belezas");
        grafo.adicionarVertice("Giovanni Gronchi");
        grafo.adicionarVertice("Santo Amaro");
        grafo.adicionarVertice("Largo Treze");
        grafo.adicionarVertice("Adolfo Pinheiro");
        grafo.adicionarVertice("Alto da Boa Vista");
        grafo.adicionarVertice("Borba Gato");
        grafo.adicionarVertice("Brooklin");
        grafo.adicionarVertice("Campo Belo");
        grafo.adicionarVertice("Eucaliptos");
        grafo.adicionarVertice("Moema");
        grafo.adicionarVertice("AACD-Servidor");
        grafo.adicionarVertice("Hospital São Paulo");

        grafo.adicionarVertice("Oratório");
        grafo.adicionarVertice("São Lucas");
        grafo.adicionarVertice("Camilo Haddad");
        grafo.adicionarVertice("Vila Tolstói");
        grafo.adicionarVertice("Vila União");
        grafo.adicionarVertice("Jardim Planalto");
        grafo.adicionarVertice("Sapopemba");
        grafo.adicionarVertice("Fazenda da Juta");
        grafo.adicionarVertice("São Mateus");
        grafo.adicionarVertice("Jardim Colonial");

        grafo.adicionarVertice("Água Branca");
        grafo.adicionarVertice("Lapa");
        grafo.adicionarVertice("Piqueri");
        grafo.adicionarVertice("Pirituba");
        grafo.adicionarVertice("Vila Clarice");
        grafo.adicionarVertice("Jaraguá");
        grafo.adicionarVertice("Vila Aurora");
        grafo.adicionarVertice("Perus");
        grafo.adicionarVertice("Caieiras");
        grafo.adicionarVertice("Franco da Rocha");
        grafo.adicionarVertice("Baltazar Fidélis");
        grafo.adicionarVertice("Francisco Morato");
        grafo.adicionarVertice("Botujuru");
        grafo.adicionarVertice("Campo Limpo Paulista");
        grafo.adicionarVertice("Várzea Paulista");
        grafo.adicionarVertice("Jundiaí");

        grafo.adicionarVertice("Júlio Prestes");
        grafo.adicionarVertice("Domingos de Moraes");
        grafo.adicionarVertice("Imperatriz Leopoldina");
        grafo.adicionarVertice("Presidente Altino");
        grafo.adicionarVertice("Osasco");
        grafo.adicionarVertice("Comandante Sampaio");
        grafo.adicionarVertice("Quitaúna");
        grafo.adicionarVertice("General Miguel Costa");
        grafo.adicionarVertice("Carapicuíba");
        grafo.adicionarVertice("Santa Terezinha");
        grafo.adicionarVertice("Antônio João");
        grafo.adicionarVertice("Barueri");
        grafo.adicionarVertice("Jardim Belval");
        grafo.adicionarVertice("Jardim Silveira");
        grafo.adicionarVertice("Jandira");
        grafo.adicionarVertice("Sagrado Coração");
        grafo.adicionarVertice("Engenheiro Cardoso");
        grafo.adicionarVertice("Itapevi");
        grafo.adicionarVertice("Santa Rita");
        grafo.adicionarVertice("Ambuita");
        grafo.adicionarVertice("Amador Bueno");

        grafo.adicionarVertice("Ceasa");
        grafo.adicionarVertice("Villa-Lobos-Jaguaré");
        grafo.adicionarVertice("Cidade Universitária");
        grafo.adicionarVertice("Hebraica-Rebouças");
        grafo.adicionarVertice("Cidade Jardim");
        grafo.adicionarVertice("Vila Olímpia");
        grafo.adicionarVertice("Berrini");
        grafo.adicionarVertice("Morumbi");
        grafo.adicionarVertice("Granja Julieta");
        grafo.adicionarVertice("João Dias");
        grafo.adicionarVertice("Socorro");
        grafo.adicionarVertice("Jurubatuba");
        grafo.adicionarVertice("Autódromo");
        grafo.adicionarVertice("Interlagos");
        grafo.adicionarVertice("Grajaú");
        grafo.adicionarVertice("Mendes-Vila Natal");
        grafo.adicionarVertice("Varginha");

        grafo.adicionarVertice("Juventus Mooca");
        grafo.adicionarVertice("Ipiranga");
        grafo.adicionarVertice("São Caetano do Sul");
        grafo.adicionarVertice("Utinga");
        grafo.adicionarVertice("Prefeito Saladino");
        grafo.adicionarVertice("Prefeito Celso Daniel-Santo André");
        grafo.adicionarVertice("Capuava");
        grafo.adicionarVertice("Mauá");
        grafo.adicionarVertice("Guapituba");
        grafo.adicionarVertice("Ribeirão Pires-Antônio Bespalec");
        grafo.adicionarVertice("Rio Grande da Serra");

        grafo.adicionarVertice("Dom Bosco");
        grafo.adicionarVertice("José Bonifácio");
        grafo.adicionarVertice("Guaianases");
        grafo.adicionarVertice("Antônio Gianetti Neto");
        grafo.adicionarVertice("Ferraz de Vasconcelos");
        grafo.adicionarVertice("Poá");
        grafo.adicionarVertice("Calmon Viana");
        grafo.adicionarVertice("Suzano");
        grafo.adicionarVertice("Jundiapeba");
        grafo.adicionarVertice("Braz Cubas");
        grafo.adicionarVertice("Mogi das Cruzes");
        grafo.adicionarVertice("Estudantes");

        grafo.adicionarVertice("Engenheiro Goulart");
        grafo.adicionarVertice("USP Leste");
        grafo.adicionarVertice("Comendador Ermelino");
        grafo.adicionarVertice("São Miguel Paulista");
        grafo.adicionarVertice("Jardim Helena-Vila Mara");
        grafo.adicionarVertice("Itaim Paulista");
        grafo.adicionarVertice("Jardim Romano");
        grafo.adicionarVertice("Engenheiro Manoel Feio");
        grafo.adicionarVertice("Itaquaquecetuba");
        grafo.adicionarVertice("Aracaré");

        grafo.adicionarVertice("Guarulhos-Cecap");
        grafo.adicionarVertice("Aeroporto-Guarulhos");
    }

    private void carregarArestas() {
        adicionarArestas(1.0, "Tucuruvi", "Parada Inglesa");
        adicionarArestas(1.0, "Parada Inglesa", "Jardim São Paulo-Ayrton Senna");
        adicionarArestas(1.0, "Jardim São Paulo-Ayrton Senna", "Santana");
        adicionarArestas(1.0, "Santana", "Carandiru");
        adicionarArestas(1.0, "Carandiru", "Portuguesa-Tietê");
        adicionarArestas(1.0, "Portuguesa-Tietê", "Armênia");
        adicionarArestas(1.0, "Armênia", "Tiradentes");
        adicionarArestas(1.0, "Tiradentes", "Luz");
        adicionarArestas(1.0, "Luz", "São Bento");
        adicionarArestas(1.0, "São Bento", "Sé");
        adicionarArestas(1.0, "Sé", "Japão-Liberdade");
        adicionarArestas(1.0, "Japão-Liberdade", "São Joaquim");
        adicionarArestas(1.0, "São Joaquim", "Vergueiro");
        adicionarArestas(1.0, "Vergueiro", "Paraíso");
        adicionarArestas(1.0, "Paraíso", "Ana Rosa");
        adicionarArestas(1.0, "Ana Rosa", "Vila Mariana");
        adicionarArestas(1.0, "Vila Mariana", "Santa Cruz");
        adicionarArestas(1.0, "Santa Cruz", "Praça da Árvore");
        adicionarArestas(1.0, "Praça da Árvore", "Saúde-Ultrafarma");
        adicionarArestas(1.0, "Saúde-Ultrafarma", "São Judas");
        adicionarArestas(1.0, "São Judas", "Conceição");
        adicionarArestas(1.0, "Conceição", "Jabaquara");

        adicionarArestas(1.0, "Vila Madalena", "Sumaré");
        adicionarArestas(1.0, "Sumaré", "Clínicas");
        adicionarArestas(1.0, "Clínicas", "Paulista-Pernambucanas");
        adicionarArestas(1.0, "Paulista-Pernambucanas","Consolação");
        adicionarArestas(1.0, "Consolação", "Trianon-Masp");
        adicionarArestas(1.0, "Trianon-Masp", "Brigadeiro");
        adicionarArestas(1.0, "Brigadeiro", "Paraíso");
        adicionarArestas(1.0, "Paraíso", "Ana Rosa");
        adicionarArestas(1.0, "Ana Rosa", "Chácara Klabin");
        adicionarArestas(1.0, "Chácara Klabin", "Santos-Imigrantes");
        adicionarArestas(1.0, "Santos-Imigrantes", "Alto do Ipiranga");
        adicionarArestas(1.0, "Alto do Ipiranga", "Sacomã");
        adicionarArestas(1.0, "Sacomã", "Tamanduateí");
        adicionarArestas(1.0, "Tamanduateí", "Vila Prudente");

        adicionarArestas(1.0, "Palmeiras-Barra Funda", "Marechal Deodoro");
        adicionarArestas(1.0, "Marechal Deodoro", "Santa Cecília");
        adicionarArestas(1.0, "Santa Cecília", "República");
        adicionarArestas(1.0, "República", "Anhangabaú");
        adicionarArestas(1.0, "Anhangabaú", "Sé");
        adicionarArestas(1.0, "Sé", "Pedro II");
        adicionarArestas(1.0, "Pedro II", "Brás");
        adicionarArestas(1.0, "Brás", "Bresser-Mooca");
        adicionarArestas(1.0, "Bresser-Mooca", "Belém");
        adicionarArestas(1.0, "Belém", "Tatuapé");
        adicionarArestas(1.0, "Tatuapé", "Carrão-Assaí Atacadista");
        adicionarArestas(1.0, "Carrão-Assaí Atacadista", "Penha-Lojas Besni");
        adicionarArestas(1.0, "Penha-Lojas Besni", "Vila Matilde");
        adicionarArestas(1.0, "Vila Matilde", "Guilhermina-Esperança");
        adicionarArestas(1.0, "Guilhermina-Esperança", "Patriarca-Vila Ré");
        adicionarArestas(1.0, "Patriarca-Vila Ré", "Artur Alvim");
        adicionarArestas(1.0, "Artur Alvim", "Corinthians-Itaquera");

        adicionarArestas(1.0, "Luz", "República");
        adicionarArestas(1.0, "República", "Higienópolis-Mackenzie");
        adicionarArestas(1.0, "Higienópolis-Mackenzie", "Paulista-Pernambucanas");
        adicionarArestas(1.0, "Paulista-Pernambucanas", "Oscar Freire");
        adicionarArestas(1.0, "Oscar Freire", "Fradique Coutinho");
        adicionarArestas(1.0, "Fradique Coutinho", "Faria Lima");
        adicionarArestas(1.0, "Faria Lima", "Pinheiros");
        adicionarArestas(1.0, "Pinheiros", "Butantã");
        adicionarArestas(1.0, "Butantã", "São Paulo-Morumbi");
        adicionarArestas(1.0, "São Paulo-Morumbi", "Vila Sônia-Talita de Castro");

        adicionarArestas(1.0, "Capão Redondo", "Campo Limpo");
        adicionarArestas(1.0, "Campo Limpo", "Vila das Belezas");
        adicionarArestas(1.0, "Vila das Belezas", "Giovanni Gronchi");
        adicionarArestas(1.0, "Giovanni Gronchi", "Santo Amaro");
        adicionarArestas(1.0, "Santo Amaro", "Largo Treze");
        adicionarArestas(1.0, "Largo Treze", "Adolfo Pinheiro");
        adicionarArestas(1.0, "Adolfo Pinheiro", "Alto da Boa Vista");
        adicionarArestas(1.0, "Alto da Boa Vista", "Borba Gato");
        adicionarArestas(1.0, "Borba Gato", "Brooklin");
        adicionarArestas(1.0, "Brooklin", "Campo Belo");
        adicionarArestas(1.0, "Campo Belo", "Eucaliptos");
        adicionarArestas(1.0, "Eucaliptos", "Moema");
        adicionarArestas(1.0, "Moema", "AACD-Servidor");
        adicionarArestas(1.0, "AACD-Servidor", "Hospital São Paulo");
        adicionarArestas(1.0, "Hospital São Paulo", "Santa Cruz");
        adicionarArestas(1.0, "Santa Cruz","Chácara Klabin" );


        adicionarArestas(1.0, "Vila Prudente", "Oratório");
        adicionarArestas(1.0, "Oratório", "São Lucas");
        adicionarArestas(1.0, "São Lucas", "Camilo Haddad");
        adicionarArestas(1.0, "Camilo Haddad", "Vila Tolstói");
        adicionarArestas(1.0, "Vila Tolstói", "Vila União");
        adicionarArestas(1.0, "Vila União", "Jardim Planalto");
        adicionarArestas(1.0, "Jardim Planalto", "Sapopemba");
        adicionarArestas(1.0, "Sapopemba", "Fazenda da Juta");
        adicionarArestas(1.0, "Fazenda da Juta", "São Mateus");
        adicionarArestas(1.0, "São Mateus", "Jardim Colonial");

        adicionarArestas(1.0, "Brás", "Luz");
        adicionarArestas(1.0, "Luz", "Palmeiras-Barra Funda");
        adicionarArestas(1.0, "Palmeiras-Barra Funda", "Água Branca");
        adicionarArestas(1.0, "Água Branca", "Lapa");
        adicionarArestas(1.0, "Lapa", "Piqueri");
        adicionarArestas(1.0, "Piqueri", "Pirituba");
        adicionarArestas(1.0, "Pirituba", "Vila Clarice");
        adicionarArestas(1.0, "Vila Clarice", "Jaraguá");
        adicionarArestas(1.0, "Jaraguá", "Vila Aurora");
        adicionarArestas(1.0, "Vila Aurora", "Perus");
        adicionarArestas(1.0, "Perus", "Caieiras");
        adicionarArestas(1.0, "Caieiras", "Franco da Rocha");
        adicionarArestas(1.0, "Franco da Rocha", "Baltazar Fidélis");
        adicionarArestas(1.0, "Baltazar Fidélis", "Francisco Morato");
        adicionarArestas(1.0, "Francisco Morato", "Botujuru");
        adicionarArestas(1.0, "Botujuru", "Campo Limpo Paulista");
        adicionarArestas(1.0, "Campo Limpo Paulista", "Várzea Paulista");
        adicionarArestas(1.0, "Várzea Paulista", "Jundiaí");

        adicionarArestas(1.0, "Júlio Prestes", "Palmeiras-Barra Funda");
        adicionarArestas(1.0, "Palmeiras-Barra Funda", "Lapa");
        adicionarArestas(1.0, "Lapa", "Domingos de Moraes");
        adicionarArestas(1.0, "Domingos de Moraes", "Imperatriz Leopoldina");
        adicionarArestas(1.0, "Imperatriz Leopoldina", "Presidente Altino");
        adicionarArestas(1.0, "Presidente Altino", "Osasco");
        adicionarArestas(1.0, "Osasco", "Comandante Sampaio");
        adicionarArestas(1.0, "Comandante Sampaio", "Quitaúna");
        adicionarArestas(1.0, "Quitaúna", "General Miguel Costa");
        adicionarArestas(1.0, "General Miguel Costa", "Carapicuíba");
        adicionarArestas(1.0, "Carapicuíba", "Santa Terezinha");
        adicionarArestas(1.0, "Santa Terezinha", "Antônio João");
        adicionarArestas(1.0, "Antônio João", "Barueri");
        adicionarArestas(1.0, "Barueri", "Jardim Belval");
        adicionarArestas(1.0, "Jardim Belval", "Jardim Silveira");
        adicionarArestas(1.0, "Jardim Silveira", "Jandira");
        adicionarArestas(1.0, "Jandira", "Sagrado Coração");
        adicionarArestas(1.0, "Sagrado Coração", "Engenheiro Cardoso");
        adicionarArestas(1.0, "Engenheiro Cardoso", "Itapevi");
        adicionarArestas(1.0, "Itapevi", "Santa Rita");
        adicionarArestas(1.0, "Santa Rita", "Ambuita");
        adicionarArestas(1.0, "Ambuita", "Amador Bueno");

        adicionarArestas(1.0, "Osasco", "Presidente Altino");
        adicionarArestas(1.0, "Presidente Altino", "Ceasa");
        adicionarArestas(1.0, "Ceasa", "Villa-Lobos-Jaguaré");
        adicionarArestas(1.0, "Villa-Lobos-Jaguaré", "Cidade Universitária");
        adicionarArestas(1.0, "Cidade Universitária", "Pinheiros");
        adicionarArestas(1.0, "Pinheiros", "Hebraica-Rebouças");
        adicionarArestas(1.0, "Hebraica-Rebouças", "Cidade Jardim");
        adicionarArestas(1.0, "Cidade Jardim", "Vila Olímpia");
        adicionarArestas(1.0, "Vila Olímpia", "Berrini");
        adicionarArestas(1.0, "Berrini", "Morumbi");
        adicionarArestas(1.0, "Morumbi", "Granja Julieta");
        adicionarArestas(1.0, "Granja Julieta", "João Dias");
        adicionarArestas(1.0, "João Dias", "Santo Amaro");
        adicionarArestas(1.0, "Santo Amaro", "Socorro");
        adicionarArestas(1.0, "Socorro", "Jurubatuba");
        adicionarArestas(1.0, "Jurubatuba", "Autódromo");
        adicionarArestas(1.0, "Autódromo", "Interlagos");
        adicionarArestas(1.0, "Interlagos", "Grajaú");
        adicionarArestas(1.0, "Grajaú", "Mendes-Vila Natal");
        adicionarArestas(1.0, "Mendes-Vila Natal", "Varginha");

        adicionarArestas(1.0, "Brás", "Juventus Mooca");
        adicionarArestas(1.0, "Juventus Mooca", "Ipiranga");
        adicionarArestas(1.0, "Ipiranga", "Tamanduateí");
        adicionarArestas(1.0, "Tamanduateí", "São Caetano do Sul");
        adicionarArestas(1.0, "São Caetano do Sul", "Utinga");
        adicionarArestas(1.0, "Utinga", "Prefeito Saladino");
        adicionarArestas(1.0, "Prefeito Saladino", "Prefeito Celso Daniel-Santo André");
        adicionarArestas(1.0, "Prefeito Celso Daniel-Santo André", "Capuava");
        adicionarArestas(1.0, "Capuava", "Mauá");
        adicionarArestas(1.0, "Mauá", "Guapituba");
        adicionarArestas(1.0, "Guapituba", "Ribeirão Pires-Antônio Bespalec");
        adicionarArestas(1.0, "Ribeirão Pires-Antônio Bespalec", "Rio Grande da Serra");

        adicionarArestas(1.0, "Luz", "Brás");
        adicionarArestas(1.0, "Brás", "Tatuapé");
        adicionarArestas(1.0, "Tatuapé", "Corinthians-Itaquera");
        adicionarArestas(1.0, "Corinthians-Itaquera", "Dom Bosco");
        adicionarArestas(1.0, "Dom Bosco", "José Bonifácio");
        adicionarArestas(1.0, "José Bonifácio", "Guaianases");
        adicionarArestas(1.0, "Guaianases", "Antônio Gianetti Neto");
        adicionarArestas(1.0, "Antônio Gianetti Neto", "Ferraz de Vasconcelos");
        adicionarArestas(1.0, "Ferraz de Vasconcelos", "Poá");
        adicionarArestas(1.0, "Poá", "Calmon Viana");
        adicionarArestas(1.0, "Calmon Viana", "Suzano");
        adicionarArestas(1.0, "Suzano", "Jundiapeba");
        adicionarArestas(1.0, "Jundiapeba", "Braz Cubas");
        adicionarArestas(1.0, "Braz Cubas", "Mogi das Cruzes");
        adicionarArestas(1.0, "Mogi das Cruzes", "Estudantes");

        adicionarArestas(1.0, "Brás", "Tatuapé");
        adicionarArestas(1.0, "Tatuapé", "Engenheiro Goulart");
        adicionarArestas(1.0, "Engenheiro Goulart", "USP Leste");
        adicionarArestas(1.0, "USP Leste", "Comendador Ermelino");
        adicionarArestas(1.0, "Comendador Ermelino", "São Miguel Paulista");
        adicionarArestas(1.0, "São Miguel Paulista", "Jardim Helena-Vila Mara");
        adicionarArestas(1.0, "Jardim Helena-Vila Mara", "Itaim Paulista");
        adicionarArestas(1.0, "Itaim Paulista", "Jardim Romano");
        adicionarArestas(1.0, "Jardim Romano", "Engenheiro Manoel Feio");
        adicionarArestas(1.0, "Engenheiro Manoel Feio", "Itaquaquecetuba");
        adicionarArestas(1.0, "Itaquaquecetuba", "Aracaré");
        adicionarArestas(1.0, "Aracaré", "Calmon Viana");

        adicionarArestas(1.0, "Engenheiro Goulart", "Guarulhos-Cecap");
        adicionarArestas(1.0, "Guarulhos-Cecap", "Aeroporto-Guarulhos");
    }
}
