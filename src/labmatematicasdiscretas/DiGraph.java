package labmatematicasdiscretas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class DiGraph {

    private final int vertices, aristas;
    private final boolean[][] adj;
    private final int[] resCap;
    private final int[][] cap, flow;

    ;
    public DiGraph(int vertices, int aristas) {
        this.vertices = vertices;
        this.aristas = aristas;
        adj = new boolean[vertices + 1][vertices + 1];
        cap = new int[vertices + 1][vertices + 1];
        flow = new int[vertices + 1][vertices + 1];
        resCap = new int[vertices + 1];
    }

    public final void addEdge(int v1, int v2, int capacity) {
        adj[v1][v2] = true;
        cap[v1][v2] = capacity;
    }

    public int maxFlow(int source, int target) {
        boolean incrementPathFound;
        int maxFlow = 0;
        do {
            int k = 0;
            int[] parents = new int[vertices + 1];
            incrementPathFound = false;
            ArrayList<Integer> cola = new ArrayList();
            cola.add(source);
            do {
                int ver = cola.get(k);
                for (int i = 1; i <= vertices; ++i) {
                    if (!cola.contains(i)) {
                        int aux = cap[ver][i] - flow[ver][i];
                        if (adj[ver][i] && aux > 0) {//forward edge
                            cola.add(i);
                            parents[i] = ver;
                            if (resCap[ver] < aux && ver != source) {
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
                k++;
            } while (k < cola.size());
        } while (incrementPathFound);
        return maxFlow;
    }

    private void syncFlow(int source, int target, int[] parents) {
        int v = target;
        int rc = resCap[target];
        do {
            if (parents[v] > 0) {
                flow[parents[v]][v] += rc;
            } else {
                flow[-1 * parents[v]][v] -= rc;
            }
            v = Math.abs(parents[v]);
        } while (v != source);
    }

}
