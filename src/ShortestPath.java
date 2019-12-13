class ShortestPath {

 /*
There are N network nodes, labelled 1 to N.

Given times, a list of travel times as directed edges times[i] = (u, v, w), where u is the source node, v is the target node, and w is the time it takes for a signal to travel from source to target.

Now, we send a signal from a certain node K. How long will it take for all nodes to receive the signal? If it is impossible, return -1.
*/
    public int networkDelayTime(int[][] times, int N, int K) {
        // Init our graph
        DiGraph g = new DiGraph(N + 1);
        for (int i = 0; i < times.length; i++) {
            g.addEdge(times[i][0], times[i][1], times[i][2]);
        }
        
        // Calculate the shortest Path using Dijkstra's algo
        // from source = K
        SP shortestPath = new SP(g, K);
        
        return shortestPath.longestDistance();
    }
    
    public class SPEdge implements Comparable {
        public int weight;
        public DiEdge edge;
        
        public SPEdge(DiEdge e, int w) {
            this.edge = e;
            this.weight = w;
        }
        
        public int compareTo(Object o) {
            SPEdge  other = (SPEdge)o;
            return this.weight - other.weight;
        }
        
        public boolean equals(Object o) {
            if (this == o) return true;
            SPEdge other = (SPEdge)o;
            return this.edge.equals(other.edge);
        }
    }
    
    public class SP {
        private int [] edgeTo;
        private int [] distTo;
        public SP(DiGraph g, int source) {
            
            distTo = new int[g.V()];
            edgeTo = new int[g.V()];
            
            Arrays.fill(distTo, Integer.MAX_VALUE);
            distTo[source] = 0;
            edgeTo[source] = source;
            
            PriorityQueue<SPEdge> q = new PriorityQueue<>();
            // hacky solution to keep a min Heap
            SPEdge root = new SPEdge(new DiEdge(source, source, 0), 0);
            q.offer(root);
            
            while(!q.isEmpty()) {
                SPEdge current = q.poll();
                for(DiEdge edge: g.getAdj(current.edge.to())){
                    relax(edge, q);
                }
            }
            
        }
        
        private void relax(DiEdge edge, PriorityQueue<SPEdge> q) {
            int from = edge.from();
            int to = edge.to();
            int currentWeight = edge.weight() + distTo[from];
            if (distTo[to] > currentWeight) {
                // update the new shortest distance
                distTo[to] = currentWeight;
                // update the new node that gets to this node
                edgeTo[to] = from;
                
                // New Relaxed Edge
                SPEdge relaxedEdge = new SPEdge(edge, currentWeight);
                // update our queue.
                if (q.contains(relaxedEdge)) {
                    // delete it so that we can inserted updated.
                    q.remove(relaxedEdge);
                } 
                // always update it.
                q.offer(relaxedEdge);
            }
        }
        
        public int distanceTo(int t) {
            if (distTo[t] == Integer.MAX_VALUE) return -1;
            else
                return distTo[t];
        }
        
        public void printPath(int t) {
            Stack<String> path = new Stack<>();
            while (t != edgeTo[t]) {
                path.push(String.valueOf(t));
                t = edgeTo[t];
            }
            path.push(String.valueOf(t));
            
            while (!path.isEmpty()) {
                System.out.print(path.pop() + " -> ");
            }
            System.out.println();
        }
        
        public int longestDistance() {
            System.out.println("V   EdgeTo   DistTo");
            for (int i = 0; i < distTo.length; i ++) {
                System.out.println(String.format("[%d]   [%d]     [%d]", i, edgeTo[i], distTo[i]));
            }
            System.out.println();
            int max = 0;
            int maxIndex = 0;
            for(int i = 0; i < distTo.length; i++) {
                if (max < distanceTo(i)) {
                    max = distanceTo(i);
                    maxIndex = i;
                }
            }
            
            printPath(maxIndex);
            return max;
        }
    }
    
    public class DiGraph {
        private ArrayList[] vertices;
        public DiGraph(int N) {
            vertices = new ArrayList[N];
            for (int i = 0; i < vertices.length; i++) {
                vertices[i] = new ArrayList<>();
            }
        }
        
        public void addEdge(int u, int v, int w) {
            vertices[u].add(new DiEdge(u, v, w));
        }
        
        public List<DiEdge> getAdj(int v) {
            return vertices[v];
        }
        
        public int V() {
            return vertices.length;
        }
        
    }
    
    public class DiEdge {
        public int u;
        public int v;
        public int w;
        public DiEdge(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }
        
        public int from(){
            return u;
        }
        
        public int to() {
            return v;
        }
        
        public int weight() {
            return w;
        }
        
        public boolean equals(Object o) {
            if (this == o) return true;
            DiEdge other = (DiEdge)o;
            
            return this.from() == other.from()
                && this.to() == other.to();
        }
    }
}
