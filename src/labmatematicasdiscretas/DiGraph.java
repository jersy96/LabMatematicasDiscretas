package labmatematicasdiscretas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class DiGraph {

    private final int vertices, aristas;
    private final boolean[][] adj;
    private boolean[] visitado;
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
        int a, b, c;
        System.out.println("Digite las aristas:");
        for (int i = 0; i < aristas; i++) {
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            c = Integer.parseInt(st.nextToken());
            this.addEdge(a, b, c);
        }
        System.out.println("Maximo Flujo: "+maxFlow(1, 6));
    }

    public final void addEdge(int v1, int v2, int capacity) {
        adj[v1][v2] = true;
        cap[v1][v2] = capacity;
    }

    private int[] BFSMaxFlow(int source, int target) {
        //ir guardadno los padres y que apenas se encuentre a target empezar a volver desde target hasta source y encontrar cual fue la arista con capacidad residual menor y en esa cantidad es que se va a incrementar el flujo
        visitado = new boolean[vertices + 1];
        int[] parents = new int[vertices + 1];
        LinkedList<Integer> cola = new LinkedList();
        cola.add(source);
        visitado[1] = true;
        while (!cola.isEmpty() && !visitado[target]) {
            int ver = cola.poll();
            for (int i = 1; i <= vertices; ++i) {
                if (adj[ver][i] && !visitado[i] && (cap[ver][i] - flow[ver][i]) > 0) {
                    visitado[i] = true;
                    parents[i] = ver;
                    cola.add(i);
                }
            }
        }
        if (visitado[target]) {
            return parents;
        } else {
            return null;
        }
    }

    public int maxFlow(int source, int target) {
        int maxFlow = 0;
        int[] parents;
        parents = BFSMaxFlow(source, target);
        while (parents != null) {
//            int flowIncrement = Integer.MAX_VALUE;
            int flowIncrement = cap[parents[target]][target] - flow[parents[target]][target];
            int i = parents[target];
            int aux;
            while (i != source) {
                aux = cap[parents[i]][i] - flow[parents[i]][i];
                if (aux < flowIncrement) {
                    flowIncrement = aux;
                }
                i = parents[i];
            }
            i = target;
            while (i != source) {
                flow[parents[i]][i] += flowIncrement;
                i = parents[i];
            }
            maxFlow += flowIncrement;
            parents = BFSMaxFlow(source, target);
        }
        return maxFlow;
    }

}
