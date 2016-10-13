package labmatematicasdiscretas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;
import sun.misc.Queue;

public class Main {
    
    public static void main(String[] args) throws IOException {
       
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        while(true){
            System.out.println("Digite numero de vertices y aristas");
            st = new StringTokenizer(br.readLine());
            int v = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            int a, b;
            Graph g = new Graph(v, e);
            System.out.println("Digite las aristas:");
            for (int i = 0; i < e; i++) {
                st = new StringTokenizer(br.readLine());
                a = Integer.parseInt(st.nextToken());
                b = Integer.parseInt(st.nextToken()); 
                g.addEdge(a, b);
            }
            g.MostrarMatriz();
            System.out.println("Es conexo: "+g.esconexo());
            System.out.println("Es bipartido: "+g.bipartidotest());
            System.out.println("Es regular: "+g.esRegular());
            System.out.println("Es euleriano: "+g.esEuleriano());
    //        System.out.println(g.tipo());
    //        System.out.println(g.minGrado());
    //        System.out.println(g.maxGrado());
            //System.out.println(g.grado(2));
            //System.out.println(g.gradoL(2));
            //System.out.println("Es Arbol: "+g.esArbol());
            //System.out.println("Numero de Componentes: "+g.componentes());
    //        g.BFSdistancia();
            /*
            
            HOOOOOOLAAAAAAAAAAAAAAAAAAAAAAAAAAA
            CAMBIOOOOOOOOOOOOOOOO PARAAAA GIIIIIIIIIIIIIIIIITTTT
            
            */
        }
    }
}
class Graph {
    int vertices, aristas;
    boolean[][] adj;
    ArrayList<ArrayList<Integer>> adjl;
    public Graph(int Vertices, int Aristas) {
        this.vertices = Vertices;
        this.aristas = Aristas;
        adj = new boolean[Vertices + 1][Vertices + 1];
        adjl = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i <= Vertices; i++) {
            adjl.add(new ArrayList<Integer>());
        }
    }
    
    public int tipo(){
        for(int i = 1; i <= this.vertices; i++){
            if(adj[i][i]){
                return 1;
            }
        }
        for(int i = 1; i <= this.vertices; i++){
            for(int v : adjl.get(i)){
                if(adjl.get(i).indexOf(v)!=adjl.get(i).lastIndexOf(v)){
                    return 2;
                }
            }
        }
        return 0;
    }
    
    public int maxGrado(){
        int max = this.gradoL(1);
        for(int i = 2; i<= vertices; i++){
            int grad = this.gradoL(i);
            if(max < grad){
                max = grad;
            }
        }
        return max;
    }
    public int minGrado(){
        int min = this.gradoL(1);
        for(int i = 2; i<= vertices; i++){
            int grad = this.gradoL(i);
            if(min > grad){
                min = grad;
            }
        }
        return min;
    }
    
    public void addEdge(int a, int b) {
        adj[a][b] = adj[b][a] = true;
        adjl.get(a).add(b);
        adjl.get(b).add(a);
    }
    
    public void MostrarMatriz(){
        for (int i = 1; i <= vertices; i++) {
            for (int j = 1; j <= vertices; j++) {
                System.out.print((adj[i][j]?1:0)+" ");
            }
            System.out.println();
        }
    }
    public void MostrarMatrizAdjl(){
        for (int i = 0; i < adjl.size(); i++) {
//            System.out.println("i="+i);
            for (int j = 0; j < adjl.get(i).size(); j++) {
//                System.out.println("j="+j);
                System.out.print(adjl.get(i).get(j)+" ");
            }
            System.out.println();
        }
    }
    public int grado(int v){
        int cont = 0;
        for (int i = 1; i <= vertices; i++) {
            cont+=adj[v][i]?1:0;
        }
        return cont;
    }
    
    public int gradoL(int v){
        return adjl.get(v).size();
    }
    
    boolean[] visitado;
    public void DFS(int v){
        System.out.println("Estoy en el vertice "+v);
        visitado[v]=true;
        for(int i=1;i<=vertices;i++){
            if(adj[v][i] && !visitado[i]){
                System.out.println("entre con i="+i+" en el v="+v);
                DFS(i);
            }
        }
    }
    //Aporte Juan David
    //Aporte Juan David
    //Aporte Juan David
    boolean[] visitado2;
    int[] bipartido;
    public boolean bipartidotest(){
        bipartido = new int[vertices+1];
        visitado2 = new boolean[vertices+1];
        for(int i = 1; i <= vertices; i++){
            if(!visitado2[i]){
                DFS_A(i);
            }
        }
        for (int i = 1; i <= vertices; i++) {
            for (int j = 1; j <= vertices; j++) {
                if (adj[i][j] && (bipartido[i]==bipartido[j]) ) {
                    return false;
                }
            }
        }
        return true;
    }
    public void DFS_A(int v){
        System.out.println("Estoy en el vertice "+v);
        visitado2[v]=true;
        bipartido[v]=1;
        for(int i=1;i<=vertices;i++){
            if(adj[v][i] && !visitado2[i]){
                System.out.println("entre con i="+i+" en el v="+v);
                DFS_B(i);
            }
        }
    }
    public void DFS_B(int v){
        System.out.println("Estoy en el vertice "+v);
        visitado2[v]=true;
        bipartido[v]=2;
        for(int i=1;i<=vertices;i++){
            if(adj[v][i] && !visitado2[i]){
                System.out.println("entre con i="+i+" en el v="+v);
                DFS_A(i);
            }
        }
    }
    
    public boolean esRegular(){
        int r=gradoL(1);
        for (int i = 2; i <= vertices; i++) {         
            if (r!=gradoL(i)) {
                return false;
            }
        }
        return true;
    }   
    
    public boolean esEuleriano(){
        for (int i = 1; i <= vertices; i++) {
            if ((gradoL(i)%2) !=0) {
                return false;
            }
        }
        return true;
    }
    //Aporte Juan David
    //Aporte Juan David
    //Aporte Juan David    
    
    public boolean esconexo(){
        visitado = new boolean[vertices+1];
        DFS(1);
        for (int i = 1; i <= vertices; i++) {
            if(!visitado[i])
                return false;
        }
        return true;
    }
    
    public boolean esArbol(){
        return esconexo() && aristas == vertices-1;
    }
    
    public int componentes(){
        int cont = 0;
        visitado = new boolean[vertices+1];
        for (int i = 1; i <= vertices; i++) {
            if(!visitado[i]){
                DFS(i);
                cont++;
            }
        }
        return cont;
    }
    
    public void BFS(){
        visitado = new boolean[vertices+1];
        LinkedList<Integer> cola = new LinkedList<Integer>();
        cola.add(1);
        visitado[1]=true;
        while(!cola.isEmpty()){
            int ver = cola.poll();            
            System.out.println("Estoy en el vertice: "+ver);
            for(int i=1;i<=vertices;++i){
                if(adj[ver][i] && !visitado[i]){
                    visitado[i]=true;
                    cola.add(i);
                }
            }
        }
    }
    
    public void BFSdistancia(){
        visitado = new boolean[vertices+1];
        LinkedList<pair> cola = new LinkedList<pair>();
        cola.add(new pair(1, 0));
        visitado[1]=true;
        while(!cola.isEmpty()){
            pair ver = cola.poll();            
            System.out.println("Estoy en el vertice: "+ver.f+" y distancia "+ver.s);
            for(int i=1;i<=vertices;++i){
                if(adj[ver.f][i] && !visitado[i]){
                    visitado[i]=true;
                    cola.add(new pair(i, ver.s+1));
                }
            }
        }
    }
}
class pair{
    int f,s;
    public pair(int f, int s) {
        this.f = f;
        this.s = s;
    }
    
}