package labmatematicasdiscretas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

//JAVIER RONCALLO 4021-02
//JUAN DAVID LAGARES, ELVIS PAVA 4021-01
public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int aux, a, b, c, v, e;
        do {
            do {
                System.out.println("Usted desea usar un grafo simple(1), un digrafo[maximo flujo](2), salir(3)");
                st = new StringTokenizer(br.readLine());
                aux = Integer.parseInt(st.nextToken());
            } while (aux != 1 && aux != 2 && aux != 3);
            switch (aux) {
                case 1:
                    System.out.println("Digite numero de vertices y aristas");
                    st = new StringTokenizer(br.readLine());
                    v = Integer.parseInt(st.nextToken());
                    e = Integer.parseInt(st.nextToken());
                    Graph g = new Graph(v, e);
                    System.out.println("Digite las aristas:");
                    for (int i = 0; i < e; i++) {
                        st = new StringTokenizer(br.readLine());
                        a = Integer.parseInt(st.nextToken());
                        b = Integer.parseInt(st.nextToken());
                        g.addEdge(a, b);
                    }
                    int op;
                    do {
                        System.out.println("Seleccione una opcion:");
                        System.out.println("1. Tipo del Grafo");
                        System.out.println("2. Matriz de Adyacencia");
                        System.out.println("3. Menor y Mayor grado del grafo");
                        System.out.println("4. ¿Es Conexo?. En caso contrario Numero de Componentes");
                        System.out.println("5. Diametro del grafo y un Recorrido entre dos vertices");
                        System.out.println("6. ¿Es bipartido?, ¿Es regular?, ¿es Arbol? y un Arbol de Expansion");
                        System.out.println("7. ¿Es Euleriano o Hamiltoniano?");
                        System.out.println("8. Bloques, Puentes y Puntos de corte");
                        System.out.println("9. λ(G) y κ(G)");
                        System.out.println("10. Volver al menu grafo/digrafo");
                        st = new StringTokenizer(br.readLine());
                        op = Integer.parseInt(st.nextToken());
                        switch (op) {
                            case 1:
                                System.out.println("Tipo de Grafo: " + g.tipo());
                                break;
                            case 2:
                                g.MostrarMatriz();
                                break;
                            case 3:
                                System.out.println(g.minGrado());
                                System.out.println(g.maxGrado());
                                break;
                            case 4:
                                if (g.esconexo()) {
                                    System.out.println("El Grafo es Conexo");
                                } else {
                                    System.out.println("El Grafo NO es conexo");
                                    System.out.println("Tiene " + g.componentes() + " componente(s)");
                                }
                                break;
                            case 5:
                                System.out.println("El diametro es: " + g.diametro());
                                g.recorrido();
                                break;
                            case 6:
                                System.out.println("El grafo es bipartido: " + g.bipartidotest());
                                System.out.println("El grafo es regular: " + g.esRegular());
                                System.out.println("El grafo es Arbol: " + g.esArbol());
                                if (g.esconexo()) {
                                    g.ArbolExpansion();
                                } else {
                                    System.out.println("El grafo no es conexo, no se puede calcular un arbol de expansion");
                                }
                                break;
                            case 7:
                                if (g.esconexo()) {
                                    System.out.println("El grafo es euleriano: " + g.esEuleriano());
                                    System.out.println("El grafo es hamiltoniano: " + g.isHamiltonian());
                                } else {
                                    System.out.println("El grafo no es conexo, no se puede calcular este item");
                                }
                                break;
                            case 8:
                                if (g.esconexo()) {
                                    g.bloques();
                                } else {
                                    System.out.println("El grafo debe ser conexo para poder calcular este item");
                                }
                                break;
                            case 9:
                                g.ConjMinAristasCorte();
                                g.ConjMinVertisesCorte();
                                break;
                            case 10:
                                break;
                            default:
                                System.out.println("Digite opcion valida");
                                break;
                        }
                        if (op != 10) {
                            System.out.println("Presione enter para continuar");
                            br.readLine();
                        }
                    } while (op != 10);
                    break;
                case 2:
                    System.out.println("Digite numero de vertices y aristas");
                    st = new StringTokenizer(br.readLine());
                    v = Integer.parseInt(st.nextToken());
                    e = Integer.parseInt(st.nextToken());
                    int source,
                     target;
                    do {
                        System.out.println("Digite vertice fuente y meta en siguiente forma: fuente meta");
                        st = new StringTokenizer(br.readLine());
                        source = Integer.parseInt(st.nextToken());
                        target = Integer.parseInt(st.nextToken());
                        if (target > v || source > v || target < 1 || source < 1) {
                            System.out.println("el vertice fuente o meta estan fuera de rango");
                        }
                    } while (target > v || source > v || target < 1 || source < 1);
                    DiGraph dg = new DiGraph(v, e, source, target);
                    System.out.println("Digite las aristas en el siguiente formato (vertice1 vertice2 capacidad)[La direccion de la arista sera vertice1 --> vertice2]:");
                    for (int i = 0; i < e; i++) {
                        st = new StringTokenizer(br.readLine());
                        a = Integer.parseInt(st.nextToken());
                        b = Integer.parseInt(st.nextToken());
                        c = Integer.parseInt(st.nextToken());
                        if (a != target) {
                            dg.addEdge(a, b, c);
                        } else {
                            System.out.println("Arista no agregada, el vertice meta no debe tener aristas de salida");
                        }
                    }
                    System.out.println("Maximo flujo " + dg.maxFlow());
                    break;
            }
        } while (aux != 3);
    }
}

class Graph {

    private final int vertices, aristas;
    private final boolean[][] adj;
    private final ArrayList<ArrayList<Integer>> adjl;

    public Graph(int Vertices, int Aristas) {
        this.vertices = Vertices;
        this.aristas = Aristas;
        adj = new boolean[Vertices + 1][Vertices + 1];
        adjl = new ArrayList<>();
        NombAristas = new ArrayList<>();
        for (int i = 0; i <= Vertices; i++) {
            adjl.add(new ArrayList<Integer>());
        }
    }

    public void addEdge(int a, int b) {
        adj[a][b] = adj[b][a] = true;
        NombAristas.add(new ArrayList<Integer>());
        NombAristas.get(Cont).add(a);
        NombAristas.get(Cont).add(b);
        Cont++;
        adjl.get(a).add(b);
        adjl.get(b).add(a);
    }

    public int grado(int v) {
        int cont = 0;
        for (int i = 1; i <= vertices; i++) {
            cont += adj[v][i] ? 1 : 0;
        }
        return cont;
    }

    public int gradoL(int v) {
        return adjl.get(v).size();
    }

    public void MostrarMatriz() {
        System.out.println("La matriz de adyacencia es:");
        for (int i = 1; i <= vertices; i++) {
            for (int j = 1; j <= vertices; j++) {
                System.out.print((adj[i][j] ? 1 : 0) + " ");
            }
            System.out.println();
        }
    }

    public void MostrarMatrizAdjl() {
        for (ArrayList<Integer> adjl1 : adjl) {
            for (Integer adjl11 : adjl1) {
                System.out.print(adjl11 + " ");
            }
            System.out.println();
        }
    }

    public String tipo() {
        for (int i = 1; i <= this.vertices; i++) {
            if (adj[i][i]) {
                return "Pseudografo";
            }
        }
        for (int i = 1; i <= this.vertices; i++) {
            for (int v : adjl.get(i)) {
                if (adjl.get(i).indexOf(v) != adjl.get(i).lastIndexOf(v)) {
                    return "Multigrafo";
                }
            }
        }
        return "Grafo Simple";
    }

    public String maxGrado() {
        String ret = "";
        ArrayList<Integer> ver = new ArrayList();
        int max = this.gradoL(1);
        for (int i = 1; i <= vertices; i++) {
            int grad = this.gradoL(i);
            if (max < grad) {
                max = grad;
                ver.clear();
            }
            if (max == grad) {
                ver.add(i);
            }
        }
        ret += "Maximo grado: " + max + ", los vertices con este grado son {";
        for (int v : ver) {
            ret += v + ",";
        }
        ret += "\b}";
        return ret;
    }

    public String minGrado() {
        String ret = "";
        ArrayList<Integer> ver = new ArrayList();
        int min = this.gradoL(1);
        for (int i = 1; i <= vertices; i++) {
            int grad = this.gradoL(i);
            if (min > grad) {
                min = grad;
                ver.clear();
            }
            if (min == grad) {
                ver.add(i);
            }
        }
        ret += "Minimo grado: " + min + ", los vertices con este grado son {";
        for (int v : ver) {
            ret += v + ",";
        }
        ret += "\b}";
        return ret;
    }

    private boolean[] visitado;

    public void DFS(int v) {
        visitado[v] = true;
        for (int i = 1; i <= vertices; i++) {
            if (adj[v][i] && !visitado[i]) {
                DFS(i);
            }
        }
    }

    //HAMILTONIANO
    private LinkedList<Integer> hamilCycle;

    public boolean isHamiltonian() {
        hamilCycle = new LinkedList();
        for (int i = 1; i <= vertices; i++) {
            visitado = new boolean[vertices + 1];
            hamiltonianCycleSinceV(i);
            if (hamilCycle.size() == vertices && adj[hamilCycle.getFirst()][hamilCycle.getLast()]) {
                return true;
            }
        }
        return false;
    }

    private void hamiltonianCycleSinceV(int vi) {
        if (!hamilCycle.contains(vi)) {
            hamilCycle.add(vi);
        }
        visitado[vi] = true;
        for (int i = 1; i <= vertices; i++) {
            if (adj[vi][i] && !visitado[i]) {
                hamilCycle.add(i);
                hamiltonianCycleSinceV(i);
            }
        }
        if (hamilCycle.size() != vertices || !adj[hamilCycle.getFirst()][hamilCycle.getLast()]) {
            visitado[hamilCycle.pollLast()] = false;
        }
    }
    //HAMILTONIANO

    public boolean esEuleriano() {
        for (int i = 1; i <= vertices; i++) {
            if ((gradoL(i) % 2) != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean esconexo() {
        visitado = new boolean[vertices + 1];
        DFS(1);
        for (int i = 1; i <= vertices; i++) {
            if (!visitado[i]) {
                return false;
            }
        }
        return true;
    }

    public int componentes() {
        int cont = 0;
        visitado = new boolean[vertices + 1];
        for (int i = 1; i <= vertices; i++) {
            if (!visitado[i]) {
                DFS(i);
                cont++;
            }
        }
        return cont;
    }

    //BLOQUES, PUENTES Y PUNTOS DE CORTE
    //Puentes
    public void puentes() {
        ArrayList<Integer> puentes = new ArrayList();
        for (int i = 1; i <= vertices; i++) {
            for (int j = 1; j <= vertices; j++) {
                if (adj[i][j]) {
                    adj[i][j] = false;
                    if (!esconexo()) {
                        puentes.add(i);
                        puentes.add(j);
                    }
                    adj[i][j] = true;
                }
            }
        }
        if (puentes.size() > 0) {
            System.out.print("los puentes son:");
            for (int l = 0; l < puentes.size(); l = l + 2) {
                System.out.print(" " + puentes.get(l) + "-" + puentes.get(l + 1));
            }
            System.out.println("");
        } else {
            System.out.println("no hay puentes");
        }
    }

    public void DFS_puentes(int v, boolean[][] adj_2) {
        visitado[v] = true;
        for (int i = 1; i <= vertices; i++) {
            if (adj_2[v][i] && !visitado[i]) {
                DFS_puentes(i, adj_2);
            }
        }
    }

    public boolean esconexo_puente(boolean[][] adj_2) {
        visitado = new boolean[vertices + 1];
        DFS_puentes(1, adj_2);
        for (int i = 1; i <= vertices; i++) {
            if (!visitado[i]) {
                return false;
            }
        }
        return true;
    }

    //Puntos de corte
    public void puntosdecorte(ArrayList<Integer> PtoCorte, ArrayList<Integer> Eliminados) {
        for (int i = 1; i <= vertices; i++) {
            if (!Eliminados.contains(i)) {
                if (!esconexo_ptocorte(i, Eliminados)) {
                    PtoCorte.add(i);
                }
            }
        }
    }

    public void DFS_VerticesCortes(int v, int elimin, ArrayList<Integer> Eliminados) {
        visitado[v] = true;
        for (int i = 1; i <= vertices; i++) {
            if (adj[v][i] && !visitado[i] && elimin != i && !Eliminados.contains(i)) {
                DFS_VerticesCortes(i, elimin, Eliminados);
            }
        }
    }

    public boolean esconexo_ptocorte(int elimin, ArrayList<Integer> Eliminados) {
        int j = 0;
        visitado = new boolean[vertices + 1];
        do {
            j++;
        } while (Eliminados.contains(j) || elimin == j);
        DFS_VerticesCortes(j, elimin, Eliminados);
        for (int i = 1; i <= vertices; i++) {
            if (i != elimin && !Eliminados.contains(i)) {
                if (!visitado[i]) {
                    return false;
                }
            }
        }
        return true;
    }

    //Bloques
    public void bloques() {
        ArrayList<Integer> PtoCorte = new ArrayList<>();
        ArrayList<Integer> Eliminados = new ArrayList<>();
        ArrayList<ArrayList<Integer>> Bloques = new ArrayList<>();
        ArrayList<Integer> temporal = new ArrayList<>();
        puentes();
        puntosdecorte(PtoCorte, Eliminados);
        if (PtoCorte.size() > 0) {
            System.out.print("los vertices de corte son:");
            for (Integer pto : PtoCorte) {
                System.out.print(pto + " ");
            }
            System.out.println("");
        } else {
            System.out.println("no hay vertices de corte");
        }
        int i, cont = -1;
        do {
            i = 0;
            do {
                i++;
            } while (Eliminados.contains(i) || PtoCorte.contains(i));
            visitado = new boolean[vertices + 1];
            DFS_bloques(i, Eliminados, PtoCorte, temporal);
            if (!Arraylistcontain(Bloques, temporal)) {
                cont++;
                Bloques.add(new ArrayList<Integer>());
                for (Integer t : temporal) {
                    Bloques.get(cont).add(t);
                }
            }
            for (Integer j : temporal) {
                if (!PtoCorte.contains(j)) {
                    Eliminados.add(j);
                }
            }
            PtoCorte.clear();
            temporal.clear();
            puntosdecorte(PtoCorte, Eliminados);
        } while (Eliminados.size() < vertices);

        System.out.println("Los Bloques son: ");
        for (ArrayList<Integer> bloque : Bloques) {
            System.out.print("{ ");
            for (Integer integer : bloque) {
                System.out.print(integer + " ");
            }
            System.out.print("}  ");
        }
        System.out.println("");
    }

    public void DFS_bloques(int v, ArrayList<Integer> Eliminados, ArrayList<Integer> PtoCorte, ArrayList<Integer> temp) {
        visitado[v] = true;
        temp.add(v);
        if (!PtoCorte.contains(v)) {
            for (int i = 1; i <= vertices; i++) {
                if (adj[v][i] && !visitado[i] && !Eliminados.contains(i)) {
                    DFS_bloques(i, Eliminados, PtoCorte, temp);
                }
            }
        }
    }

    public boolean Arraylistcontain(ArrayList<ArrayList<Integer>> bloques, ArrayList<Integer> temp) {
        for (ArrayList<Integer> bloq : bloques) {
            if (bloq.containsAll(temp)) {
                return true;
            }
        }
        return false;
    }
    //BLOQUES, PUENTES Y PUNTOS DE CORTE

    //BIPARTIDO, REGULAR, ARBOL, ARBOL DE EXPANSION
    //BIPARTIDO
    int[] bipartido;

    public boolean bipartidotest() {
        bipartido = new int[vertices + 1];
        visitado = new boolean[vertices + 1];
        for (int i = 1; i <= vertices; i++) {
            if (!visitado[i]) {
                DFS_A(i);
            }
        }
        for (int i = 1; i <= vertices; i++) {
            for (int j = 1; j <= vertices; j++) {
                if (adj[i][j] && (bipartido[i] == bipartido[j])) {
                    return false;
                }
            }
        }
        return true;
    }

    public void DFS_A(int v) {
        visitado[v] = true;
        bipartido[v] = 1;
        for (int i = 1; i <= vertices; i++) {
            if (adj[v][i] && !visitado[i]) {
                DFS_B(i);
            }
        }
    }

    public void DFS_B(int v) {
        visitado[v] = true;
        bipartido[v] = 2;
        for (int i = 1; i <= vertices; i++) {
            if (adj[v][i] && !visitado[i]) {
                DFS_A(i);
            }
        }
    }
    //BIPARTIDO

    public boolean esRegular() {
        int gr = gradoL(1);
        for (int i = 2; i <= vertices; i++) {
            if (gr != gradoL(i)) {
                return false;
            }
        }
        return true;
    }

    public boolean esArbol() {
        return esconexo() && aristas == vertices - 1;
    }

    public void ArbolExpansion() {//Prim    
        //inicializar el array    
        boolean[] visit = new boolean[vertices + 1];
        int prim[][] = new int[vertices + 1][3];//columna 1 costo columna 2 nodo adyacente con menor costo.
        for (int i = 0; i <= vertices; i++) { //inicializar...
            visit[i] = false;
            prim[i][1] = (int) Double.MAX_VALUE;
        }

        int select = 1;//seleccionamos el nodo con el que empezaremos.
        prim[select][1] = 0;

        while (select != (int) Double.MAX_VALUE) {
            visit[select] = true;
            for (int i = 1; i <= vertices; i++) {
                if (adj[select][i] == true) {
                    if (visit[i] == false) {
                        if (prim[i][1] > 1) {
                            prim[i][1] = 1;
                            prim[i][2] = select;
                        }
                    }
                }
            }
            select = (int) Double.MAX_VALUE;
            for (int i = 1; i <= vertices; i++) {
                if ((visit[i] == false) && (prim[i][1] < select)) {
                    select = i;
                }
            }
        }
        boolean arbol[][] = new boolean[vertices + 1][vertices + 1];
        for (int i = 1; i <= vertices; i++) {
            for (int j = 1; j <= vertices; j++) {
                arbol[i][j] = (prim[i][2] == j);
            }
        }
        for (int i = 1; i <= vertices; i++) {
            for (int j = 1; j <= vertices; j++) {
                if (arbol[i][j]) {
                    arbol[j][i] = arbol[i][j];
                }
            }
        }
        System.out.println("La matriz de adyacencia de el arbol de expansion es:");
        for (int i = 1; i <= vertices; i++) {
            for (int j = 1; j <= vertices; j++) {
                System.out.print((arbol[i][j] ? 1 : 0) + " ");
            }
            System.out.println();
        }
    }
    //BIPARTIDO, REGULAR, ARBOL, ARBOL DE EXPANSION

    //RECORRIDO ENTRE VERTICES, DIAMETRO
    int d[][];//disancia de i a j;
    int r[][];//ultimo nodo para llegar de i a j;

    public void floydwarshall() {
        d = new int[vertices + 1][vertices + 1];
        r = new int[vertices + 1][vertices + 1];
        int inf = (int) Double.MAX_VALUE;
        for (int i = 0; i <= vertices; i++) {
            for (int j = 0; j <= vertices; j++) {
                if (i != j) {
                    if (adj[i][j] == false) {//no son adyacentes
                        r[i][j] = -1;//numero arvitrario;
                        d[i][j] = inf;
                    } else {
                        r[i][j] = j;//Atraves de j
                        d[i][j] = 1;//distancia de i a j como son adyacentes es 1;
                    }
                } else {
                    d[i][j] = 0;
                    r[i][j] = i;
                }
            }
        }

        for (int k = 1; k <= vertices; k++) { //for k from 1 to |V| // standard Floyd-Warshall implementation
            for (int i = 1; i <= vertices; i++) {
                for (int j = 1; j <= vertices; j++) {
                    int numd = d[i][k] + d[k][j];
                    int numr = r[i][k];
                    if ((r[i][k] != -1) && (r[k][j] != -1)) {
                        if ((d[i][k] + d[k][j] < d[i][j])) {

                            d[i][j] = d[i][k] + d[k][j];
                            r[i][j] = r[i][k];
                        }
                    }
                }
            }
        }
    }

    public void recorrido() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Introduzca el primer vertice, luego el segundo para calcular el camino ");
        StringTokenizer st;
        st = new StringTokenizer(br.readLine());
        int b = Integer.parseInt(st.nextToken());
        int a = Integer.parseInt(st.nextToken());

        this.floydwarshall();

        ArrayList<Integer> recorrido = new ArrayList<>();

        int V;
        V = a;
        recorrido.add(V);
        for (int i = 0; i < d[a][b]; i++) {
            V = r[V][b];
            recorrido.add(V);

        }

        //Apartir de aquí empieza a mostrar el recorrido:
        String s = "";
        for (int i = recorrido.size() - 1; i >= 0; i--) {
            s += " " + recorrido.get(i);
        }
        System.out.println(s);
    }

    public int diametro() {
        floydwarshall();
        int diametro = (int) Double.MIN_VALUE;
        for (int i = 0; i <= vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                if (d[i][j] > diametro && d[i][j] != (int) Double.MAX_VALUE) {
                    diametro = d[i][j];
                }
            }
        }
        return diametro;
    }
    //RECORRIDO ENTRE VERTICES, DIAMETRO

    //LAMBDA Y KAPPA
    ArrayList<ArrayList<Integer>> Combinaciones;
    ArrayList<ArrayList<Integer>> temp;
    ArrayList<Integer> Numeros;

    public void Combinatoria(int n) {
        temp = new ArrayList<>();
        Combinaciones = new ArrayList<>();
        ArrayList<Integer> T = new ArrayList<>();
        for (int i = 0; i < Numeros.size(); i++) {
            Combinaciones.add(new ArrayList<Integer>());
            Combinaciones.get(i).add(Numeros.get(i));
        }
        int cont;
        for (int i = 1; i < n; i++) {
            CopiarArray();
            cont = 0;
            for (ArrayList<Integer> tem : temp) {
                CArray(T, tem);
                for (Integer Numero : Numeros) {
                    T.add(Numero);
                    if (!Arraylistcontain(Combinaciones, T) && !tem.contains(Numero)) {
                        Combinaciones.add(new ArrayList<Integer>());
                        for (int l = 0; l < T.size(); l++) {
                            Combinaciones.get(cont).add(T.get(l));
                        }
                        cont++;
                    }
                    T.remove(T.size() - 1);
                }
            }
        }
    }

    public void CArray(ArrayList<Integer> T, ArrayList<Integer> dest) {
        T.clear();
        for (Integer integer : dest) {
            T.add(integer);
        }
    }

    public void CopiarArray() {
        temp.clear();
        for (ArrayList<Integer> Combinacione : Combinaciones) {
            temp.add(Combinacione);
        }
        Combinaciones.clear();
    }

    public void ConjMinVertisesCorte() {
        if (GCompleto() || !esconexo()) {
            System.out.println("k(G) no existe para Grafos completo o no conexos");
        } else {
            boolean sw = true;
            int k = 0, j;
            Numeros = new ArrayList<>();
            for (int i = 1; i <= vertices; i++) {
                Numeros.add(i);
            }
            do {
                k++;
                Combinatoria(k);
                j = 0;
                while (sw && j < Combinaciones.size()) {
                    sw = esconexo_ptocorte(0, Combinaciones.get(j));
                    j++;
                }
            } while (sw && k < vertices - 1);
            System.out.println("k(G)= " + k);
        }
    }

    ArrayList<ArrayList<Integer>> NombAristas;
    int Cont = 0;

    public void ConjMinAristasCorte() {
        if (!esconexo()) {
            System.out.println("λ(G) no existe para Grafos no conexos");
        } else {
            boolean sw = true;
            int k = 0, j;
            boolean[][] adj2 = new boolean[vertices + 1][vertices + 1];
            Numeros = new ArrayList<>();
            for (int i = 1; i <= aristas; i++) {
                Numeros.add(i);
            }
            do {
                k++;
                Combinatoria(k);
                j = 0;
                while (sw && j < Combinaciones.size()) {
                    for (int i = 1; i < adj.length; i++) {
                        for (int l = 1; l < adj[0].length; l++) {
                            adj2[i][l] = adj[i][l];
                        }
                    }
                    for (Integer integer : Combinaciones.get(j)) {
                        adj2[NombAristas.get(integer - 1).get(0)][NombAristas.get(integer - 1).get(1)] = false;
                        adj2[NombAristas.get(integer - 1).get(1)][NombAristas.get(integer - 1).get(0)] = false;
                    }
                    sw = esconexo_puente(adj2);
                    j++;
                }
            } while (sw && k < vertices - 1);
            System.out.println("λ(G)= " + k);
        }
    }

    public boolean GCompleto() {
        visitado = new boolean[vertices + 1];
        visitado[1] = true;
        for (int i = 1; i <= vertices; i++) {
            if (adj[1][i] && !visitado[i]) {
                visitado[i] = true;
            }
        }
        for (int i = 1; i <= vertices; i++) {
            if (!visitado[i]) {
                return false;
            }

        }
        return true;
    }
    //LAMBDA Y KAPPA

    public void BFS() {
        visitado = new boolean[vertices + 1];
        LinkedList<Integer> cola = new LinkedList();
        cola.add(1);
        visitado[1] = true;
        while (!cola.isEmpty()) {
            int ver = cola.poll();
            System.out.println("Estoy en el vertice: " + ver);
            for (int i = 1; i <= vertices; ++i) {
                if (adj[ver][i] && !visitado[i]) {
                    visitado[i] = true;
                    cola.add(i);
                }
            }
        }
    }
}
