package labmatematicasdiscretas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class DiGraph {

    private final int vertices, aristas;
    private final boolean[][] adj;
    private final int[] resCap;
    private final int[][] cap, flow;

    ;
    public DiGraph() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        System.out.println("Digite numero de vertices y aristas");
        st = new StringTokenizer(br.readLine());
        vertices = Integer.parseInt(st.nextToken());
        aristas = Integer.parseInt(st.nextToken());
//        this.vertices = vertices;
//        this.aristas = aristas;
        adj = new boolean[vertices + 1][vertices + 1];
        cap = new int[vertices + 1][vertices + 1];
        flow = new int[vertices + 1][vertices + 1];
        resCap = new int[vertices + 1];
        int a, b, c;
        System.out.println("Digite las aristas:");
        for (int i = 0; i < aristas; i++) {
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            c = Integer.parseInt(st.nextToken());
            this.addEdge(a, b, c);
        }
        System.out.println("Maximo Flujo: " + maxFlow(1, vertices));
    }

    public final void addEdge(int v1, int v2, int capacity) {
        adj[v1][v2] = true;
        cap[v1][v2] = capacity;
    }

    public int maxFlow(int source, int target) {
        boolean incrementPathFound;
        int maxFlow = 0;
        do {
            incrementPathFound = false;
            int[] parents = new int[vertices + 1];
            LinkedList<Integer> cola = new LinkedList();
            cola.add(source);
            while (!cola.isEmpty()) {
                int ver = cola.poll();
                for (int i = 1; i <= vertices; ++i) {
                    if (!cola.contains(i)) {
                        int aux = cap[ver][i] - flow[ver][i];
                        if (adj[ver][i] && aux > 0) {//forward edge
                            cola.add(i);
                            parents[i] = ver;
                            if (resCap[ver] < aux) {
                                resCap[i] = resCap[ver];
                            } else {
                                resCap[i] = aux;
                            }
                            if (i == target) {
                                syncFlow(source, target, parents);
                                maxFlow += resCap[target];
                                incrementPathFound = true;
                            }
                        }
                        aux = flow[i][ver];
                        if (adj[i][ver] && aux > 0) {//backward edge
                            cola.add(i);
                            parents[i] = -1 * ver;
                            if (resCap[ver] < aux) {
                                resCap[i] = resCap[ver];
                            } else {
                                resCap[i] = aux;
                            }
                            if (i == target) {
                                syncFlow(source, target, parents);
                                maxFlow += resCap[target];
                                incrementPathFound = true;
                            }
                        }
                    }
                }
            }
        } while (incrementPathFound);
        return maxFlow;
    }

    private void syncFlow(int source, int target, int[] parents) {
        int v = target;
        int rc = resCap[target];
        do {
            int aux = parents[v];
            if (aux > 0) {
                flow[aux][v] += rc;
            } else {
                aux *= -1;;
                flow[aux][v] -= rc;
            }
            v = parents[v];
        } while (v != source);
    }

}
