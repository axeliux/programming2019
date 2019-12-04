import java.util.*;

public class Problem {	
	public static void main(String arg[]){
		System.out.println("Problems 2019");
		Graph g = new Graph(6);
		//System.out.println(g.count());
		List<Edge> list = new ArrayList<>();
		list.add(new Edge(0,1));
		list.add(new Edge(1,2));
		list.add(new Edge(1,3));
		list.add(new Edge(3, 4));
		list.add(new Edge(1, 4));
		list.add(new Edge(4, 5));

		for(Edge edge : list) {
			g.addEdge(edge.v, edge.w);
		}

		boolean visited [] = new  boolean[g.count()];
	    for (int v = 0; v < g.count(); v++) {
			for(Integer w: g.getAdj(v)) {
				CC cc = new CC(g, new Edge(v, w));
				//System.out.println("CC =" + cc.count());
				if (cc.count() > 1 && !(visited[v] && visited[w])) {
					visited[v] = true;
					visited[w] = true;
					System.out.print("("+v + "," + w +"), ");
				}
			}
		}
		System.out.println();
	
		//CC cc = new CC(g, new Edge(1, 3));
		//System.out.println("Connected Component: " + cc.count());

	}	

	
   static class Graph {
		private List<Integer> [] vertices;
		
		public Graph(int n) {
			vertices = new ArrayList[n];			
			for (int i = 0; i < n; i ++) {
				vertices[i] =  new ArrayList<>();
			}
		}



		public void addEdge(int v, int w) {
			vertices[v].add(w);
			vertices[w].add(v);
		}
		
		public List<Integer> getAdj(int v) {
			return vertices[v];
		}
		
		public List<Integer>[] V() {
			return vertices;
		}
	
		public int count() {
			return vertices.length;
		}
		
		public void print(){
			for(int i = 0; i < vertices.length; i++) {
				System.out.print("("+i+") -> ");
				for(Integer v : getAdj(i)){
					System.out.print(v + ",");
				}
				System.out.println();
			}
		}
		
	}

	static class Edge {
		public int v;
		public int w;
		public Edge(int v, int w){
			this.v = v;
			this.w = w;
		}
	
		public boolean equals(Object o) {
			Edge other = (Edge) o;
			return this.v == other.v && this.w == other.w || this.v == other.w && this.w == other.v;
		}
	
		public String  toString() {
			return "(" + v + "," + w + ")";
		}
	}

	static class CC {
		private boolean [] marked;
		private int [] group;
		private int count;
		private Edge blacklist;

		public CC(Graph g, Edge blacklist) {
			marked = new boolean[g.count()];
			group = new int[g.count()];
			this.blacklist = blacklist;

			for (int v= 0; v < g.count(); v++) {
				if (!marked[v]) {
					dfs(g, v);
					count++;
				}
			}
		}

		public int count(){
			return count;
		}

		public void dfs(Graph g, int v){
		
			marked[v] = true;
			for(Integer w : g.getAdj(v)){
				if(!marked[w] && isEdgeAllow(v, w)){
					dfs(g, w);
				}
			}
		}

		public boolean isEdgeAllow(int v, int w) {
			return ! ((blacklist.v == v && blacklist.w == w) 
				|| (blacklist.v == w && blacklist.w ==v));
		}
	}
}
