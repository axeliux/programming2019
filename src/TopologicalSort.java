import java.util.*;

public class TopologicalSort {

	public static void main(String [] args) {
		System.out.println("This is a topological sort");
		DiGraph g = new DiGraph(5);
		g.addEdge(0,4);
		g.addEdge(1,4);
		g.addEdge(2,3);
	 	g.addEdge(3,4);	
		g.addEdge(4,4);

		g.print();
		
		List<Integer> result = sort(g);
	
		System.out.println(Arrays.toString(result.toArray()));

	}

	public static List<Integer> sort(DiGraph g) {
		boolean visited [] = new boolean[g.size()];
		List<Integer> list = new ArrayList<>();
		for (int v = 0; v < g.size(); v++) {
			if(!visited[v]) {
				dfs(g, v, visited, list);
				list.add(v);
			}
		}
		return list;
	}
	
	public static  void dfs(DiGraph g, int v, boolean [] visited, List<Integer> list) {
		visited[v] = true;
		for(Integer w: g.getAdj(v)) {
			if (!visited[w]) {
				dfs(g, w, visited, list);
				list.add(w);
			}
		}
	}

	public static class GSort {
	
	}

	public static class DiGraph {
		private List<Integer> [] vertices;
		public DiGraph(int n) {
			vertices = new ArrayList[n];
			for(int v = 0; v < n ; v++) {
				vertices[v] = new ArrayList<Integer>();
			}
		}

		public void addEdge(int v, int w){
			vertices[v].add(w);
		}

		public List<Integer> getAdj(int v){
			return vertices[v];
		}

		public int size() {
			return vertices.length;		
		}

		public void print() {
			for(int v = 0; v < vertices.length; v++){
				System.out.print("("+v+") -> ");
				for(Integer w : getAdj(v)) {
					System.out.print("("+w+"), ");
				}
				System.out.println();	
			}
		}
	}

	public static class Graph {
		private List<Integer> [] vertices;
		public Graph(int n){
			vertices = new ArrayList[n];
			for (int v = 0; v < vertices.length; v++) {
				vertices[v] = new ArrayList<Integer>();
			}
		}

		public void addEdge(int v, int w) {
			vertices[v].add(w);
			vertices[w].add(v);
		}
		
		public List<Integer> getAdj(int v) {
			return vertices[v];
		}
	
		public void print(){
			for(int v = 0; v < vertices.length; v++) {
				System.out.print("("+ v +") -> ");
				for(Integer w : getAdj(v)) {
					System.out.print("("+w+"), ");
				}
				System.out.println();
			}
		}
	}
}
