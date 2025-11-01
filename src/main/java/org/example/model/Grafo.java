package org.example.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.List;
import java.util.LinkedList;
import java.util.Collections;

/**
 * Classe genérica que representa um grafo direcionado com pesos nas arestas.
 *
 * @param <TIPO> Tipo dos dados armazenados em cada vértice (por exemplo, String com o nome da estação).
 *
 * Esta implementação fornece métodos para adicionar vértices e arestas, executar o algoritmo
 * de Dijkstra para obter distâncias mínimas e recuperar o caminho mais curto entre dois nós.
 */
public class Grafo<TIPO>{
    // Lista de vértices do grafo
    private ArrayList<Vertice<TIPO>> vertices;

    // Lista de arestas (manter também a lista facilita iterações/depuração, embora não seja estritamente necessária)
    private ArrayList<Aresta<TIPO>> arestas;

    // Construtor: inicializa as coleções internas
    public Grafo(){
        this.vertices = new ArrayList<Vertice<TIPO>>();
        this.arestas = new ArrayList<Aresta<TIPO>>();
    }

    /** Retorna a lista de vértices (pode ser usada para iteração ou inspeção). */
    public ArrayList<Vertice<TIPO>> getVertices() {
        return vertices;
    }

    /**
     * Adiciona um vértice ao grafo com o dado associado.
    */
    public void adicionarVertice(TIPO dado){
        Vertice<TIPO> novoVertice = new Vertice<TIPO>(dado);
        this.vertices.add(novoVertice);
    }

    /**
     * Adiciona uma aresta direcionada do vértice de "dadoInicio" para o vértice de "dadoFim"
     * com o peso fornecido. Lança IllegalArgumentException se algum dos vértices não existir.
     */
    public void adicionarAresta(Double peso, TIPO dadoInicio, TIPO dadoFim){
        Vertice<TIPO> inicio = this.getVertice(dadoInicio);
        Vertice<TIPO> fim = this.getVertice(dadoFim);

        if (inicio == null) {
            throw new IllegalArgumentException(" Início não Encontrado no grafo: " + dadoInicio);
        }
        if (fim == null) {
            throw new IllegalArgumentException(" Fim não encontrado no grafo: " + dadoFim);
        }

        // Cria a aresta e vincula-a às listas de entradas/saídas dos vértices envolvidos
        Aresta<TIPO> aresta = new Aresta<TIPO>(peso, inicio, fim);
        inicio.adicionarArestaSaida(aresta);
        fim.adicionarArestaEntrada(aresta);
        this.arestas.add(aresta);
    }


    /**
     * Recupera o vértice que contém o dado especificado (comparação por equals).
     * Retorna null se nenhum vértice corresponder.
     */
    public Vertice<TIPO> getVertice(TIPO dado) {
        Vertice<TIPO> vertice = null;
        for (int i = 0; i < this.vertices.size(); i++) {
            if (this.vertices.get(i).getDado().equals(dado)) {
                vertice = this.vertices.get(i);
                break;
            }
        }
        return vertice;
    }


    /**
     * Classe interna simples usada para retornar tanto as distâncias quanto os predecessores
     * após a execução do algoritmo de Dijkstra. Assim a API expõe resultados em termos de
     * dados (TIPO) em vez de objetos Vertice.
     */
    public static class DijkstraResultado<TIPO> {
        private final Map<TIPO, Double> distancias;
        private final Map<TIPO, TIPO> predecessores;

        public DijkstraResultado(Map<TIPO, Double> distancias, Map<TIPO, TIPO> predecessores) {
            this.distancias = distancias;
            this.predecessores = predecessores;
        }
    }

    /**
     * Implementação do algoritmo de Dijkstra a partir de um dado de origem.
     * Retorna um objeto contendo as distâncias mínimas e um mapa de predecessores
     * (para reconstruir caminhos).
     *
     * Observações de implementação:
     * - Usa uma PriorityQueue personalizada que armazena nós com distância atual.
     * - Mantém um conjunto 'visitados' para ignorar entradas antigas na fila (lazy deletion).
     *    */
    public DijkstraResultado<TIPO> dijkstra(TIPO dadoOrigem) {
        Vertice<TIPO> origem = this.getVertice(dadoOrigem);
        if (origem == null) {
            throw new IllegalArgumentException("Origem não Encontrado: " + dadoOrigem);
        }

        // Mapas auxiliares para distâncias e predecessores no grafo de Vertice
        Map<Vertice<TIPO>, Double> distVert = new HashMap<>();
        Map<Vertice<TIPO>, Vertice<TIPO>> prevVert = new HashMap<>();
        HashSet<Vertice<TIPO>> visitados = new HashSet<>();

        // Inicializa distâncias com infinito e predecessores null
        for (Vertice<TIPO> v : this.vertices) {
            distVert.put(v, Double.POSITIVE_INFINITY);
            prevVert.put(v, null);
        }
        distVert.put(origem, 0.0);

        // Node é uma estrutura auxiliar para a fila de prioridade (vértice + distância)
        class Node {
            Vertice<TIPO> vert;
            Double dist;
            Node(Vertice<TIPO> vert, Double dist) { this.vert = vert; this.dist = dist; }
        }

        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> Double.compare(a.dist, b.dist));
        pq.add(new Node(origem, 0.0));

        // Loop principal: extrai o vértice com menor distância conhecida
        while (!pq.isEmpty()) {
            Node node = pq.poll();
            Vertice<TIPO> u = node.vert;

            // Se já visitado, pular (isso evita remoções da fila quando a distância é atualizada)
            if (visitados.contains(u)) continue;
            visitados.add(u);

            Double distU = distVert.get(u);
            if (distU.isInfinite()) continue;

            for (Aresta<TIPO> aresta : u.getArestasSaida()) {
                Vertice<TIPO> v = aresta.getFim();
                double peso = (aresta.getPeso() != null) ? aresta.getPeso() : 0.0;
                double alt = distU + peso;
                // se caminho via U for melhor, atualiza distância e predecessor
                if (alt < distVert.get(v)) {
                    distVert.put(v, alt);
                    prevVert.put(v, u);
                    pq.add(new Node(v, alt));
                }
            }
        }

        // Converte os resultados para tipos públicos (TIPO) em vez de Vertice<TIPO>
        Map<TIPO, Double> distancias = new HashMap<>();
        Map<TIPO, TIPO> predecessores = new HashMap<>();
        for (Vertice<TIPO> v : this.vertices) {
            distancias.put(v.getDado(), distVert.getOrDefault(v, Double.POSITIVE_INFINITY));
            Vertice<TIPO> p = prevVert.get(v);
            predecessores.put(v.getDado(), (p == null) ? null : p.getDado());
        }

        return new DijkstraResultado<TIPO>(distancias, predecessores);
    }

    /**
     * Reconstrói o caminho mais curto (lista de dados TIPO) entre origem e destino.
     * Retorna uma lista vazia se não houver caminho ou se algum dos vértices não existir.
     */
    public List<TIPO> caminhoMaisCurto(TIPO origem, TIPO destino) {
        if (this.getVertice(origem) == null) {
            throw new IllegalArgumentException(" Origem não Encontrado: " + origem);
        }
        if (this.getVertice(destino) == null) {
            throw new IllegalArgumentException(" Destino não Encontrado: " + destino);
        }

        DijkstraResultado<TIPO> res = dijkstra(origem);
        Map<TIPO, Double> dist = res.distancias;
        Map<TIPO, TIPO> prev = res.predecessores;

        Double d = dist.get(destino);
        if (d == null || d.isInfinite()) {
            return Collections.emptyList();
        }

        // Reconstrói o caminho a partir do mapa de predecessores (inserindo no início da lista)
        LinkedList<TIPO> caminho = new LinkedList<>();
        TIPO at = destino;
        while (at != null) {
            caminho.addFirst(at);
            if (at.equals(origem)) break;
            at = prev.get(at);
        }
        // Validação final: se o primeiro elemento não for a origem, não há caminho válido
        if (caminho.isEmpty() || !caminho.getFirst().equals(origem)) return Collections.emptyList();
        return caminho;
    }
}
