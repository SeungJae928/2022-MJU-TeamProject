package com.example.teamproject1;


import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Vector;

class FindRoute{
    private Integer cost;
    private ArrayList<Integer> route;

    public FindRoute(int cost, ArrayList<Integer> route) {
        this.cost = cost;
        this.route = route;
    }

    public Integer getCost() {
        return cost;
    }

    public ArrayList<Integer> getRoute() {
        return route;
    }
}

public class Dijkstra {
    static int V, E;
    //static int start, end;
    static ArrayList<ArrayList<Node>> graph = null;
    static Map<Integer, Integer> stations = null;

    private static final String T = "time";
    private static final String D = "dis";
    private static final String C = "cost";

    Context context;

    static class Node {// 자신의 이름과 다음 노드의 인덱스, 그 노드로 가는데 필요한 비용을 담고 있다.
        public int idx, name;
        public HashMap<String, Integer> data;

        Node(int idx, int name) {
            this.idx = idx;
            this.name = name;
            data = new HashMap<>();
        }

        void setData(int t, int d, int c){
            data.put(T, t);
            data.put(D, d);
            data.put(C, c);
        }

        int getData(String s){
            return data.get(s);
        }
    }

    public Dijkstra(Context c) {
        context = c;
    }

    public String[] getDetails(ArrayList<Integer> route) {
        int cost;
        int walk;
        int time;

        String[] res = new String[3];

        walk = (int)(route.size() * 0.8);
        cost = getTotal(route, C);
        time = getTotal(route, T);

        res[0] = time/3600 + "시간 " + (time % 3600) / 60 + "분";
        res[1] = "도보 " + walk + "분";
        res[2] = cost + "원";

        return res;
    }

    public String[] getDetails(ArrayList<Integer> route, ArrayList<Integer> route2) {
        int cost;
        int walk;
        int time;

        String[] res = new String[3];

        walk = (int)(route.size() * 0.8) + (int)(route2.size() * 0.8);
        cost = getTotal(route, C) + getTotal(route2, C);
        time = getTotal(route, T) + getTotal(route2, T);

        res[0] = time/3600 + "시간 " + (time % 3600) / 60 + "분";
        res[1] = "도보 " + walk + "분";
        res[2] = cost + "원";

        return res;
    }

    public int getTotal(ArrayList<Integer> route, String type) {
        HashMap<String, Integer> data;
        ArrayList<Node> temp;
        int sum = 0;
        Node next = null;
        for (int i = 0; i < route.size() - 1; i++) {
            temp = graph.get(stations.get(route.get(i)));
            Iterator<Node> it = temp.iterator();
            while (it.hasNext()){
                next = it.next();
                if (next.name == route.get(i+1)) {
                    break;
                }
            }
            data = next.data;
            sum += data.get(type);
        }
        return sum;
    }

    public FindRoute dijkstra(int start, int end, int tp) throws Exception {
        // 초기화

        // KeyBoard 입력
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        StringTokenizer st = new StringTokenizer(br.readLine());
//        V = Integer.parseInt(st.nextToken());
//        E = Integer.parseInt(st.nextToken());
//        st = new StringTokenizer(br.readLine());
//        start = Integer.parseInt(st.nextToken());
//        end = Integer.parseInt(st.nextToken());
//        graph = new ArrayList<ArrayList<Node>>();
//        for (int i = 0; i < V + 1; i++) {
//            graph.add(new ArrayList<Node>());
//        }
//        for (int i = 0; i < E; i++) {
//            st = new StringTokenizer(br.readLine());
//            int s = Integer.parseInt(st.nextToken());
//            int e = Integer.parseInt(st.nextToken());
//            int t = Integer.parseInt(st.nextToken());
//            int d = Integer.parseInt(st.nextToken());
//            int c = Integer.parseInt(st.nextToken());
//            // 문제에서는 유향 그래프에서의 다익스트라 알고리즘(이 조건도 문제에 따라 중요하다!).
//            Node n1 = new Node(e);
//            Node n2 = new Node(s);
//            n1.setData(t, d, c);
//            n2.setData(t, d, c);
//            graph.get(s).add(n1);
//            graph.get(e).add(n2);
//        }

        // File 입력
        stations = new HashMap<>();

        Scanner sc = new Scanner(context.getResources().openRawResource(R.raw.data));

        graph = new ArrayList<ArrayList<Node>>();
        graph.add(new ArrayList<Node>());

        int sn = 0;
        int ln = 0;
        while (sc.hasNextInt()) {
            ln++;
            int s = sc.nextInt();
            int e = sc.nextInt();
            int t = sc.nextInt();
            int d = sc.nextInt();
            int c = sc.nextInt();

            if (stations.get(s) == null) {
                stations.put(s, sn++);
                graph.add(new ArrayList<Node>());
            }
            if (stations.get(e) == null) {
                stations.put(e, sn++);
                graph.add(new ArrayList<Node>());
            }

            Node n1 = new Node(stations.get(e), e);
            Node n2 = new Node(stations.get(s), s);
            n1.setData(t, d, c);
            n2.setData(t, d, c);

            graph.get(stations.get(s)).add(n1);
            graph.get(stations.get(e)).add(n2);
        }
        V = stations.size();
        E = ln;

        if (stations.get(start) == null) {
            throw new Exception("출발지가 존재하지 않습니다.");
        }
        if (stations.get(end) == null) {
            throw new Exception("목적지가 존재하지 않습니다.");
        }

//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        StringTokenizer st = new StringTokenizer(br.readLine());
//        start = Integer.parseInt(st.nextToken());
//        end = Integer.parseInt(st.nextToken());
//        System.out.print("0: time, 1: distance, 2: cost\ntype type: ");
//        st = new StringTokenizer(br.readLine());
//        int t = Integer.parseInt(st.nextToken());
        String type;
        switch (tp) {
            case 0:
                type = T;
                break;
            case 1:
                type = D;
                break;
            case 2:
                type = C;
                break;
            default:
                type = T;
                break;
        }

        // 다익스트라 알고리즘 초기화
        int[] dist = new int[V + 1]; // 최소 비용을 저장할 배열
        Node[] route = new Node [V + 1]; // 직전에 도착한 노드를 저장할 배열 (길을 찾기 위함)
        for (int i = 0; i < V + 1; i++) {
            dist[i] = Integer.MAX_VALUE;
        }

        PriorityQueue<Node> q = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            q = new PriorityQueue<Node>((o1, o2) -> Integer.compare(o1.getData(type), o2.getData(type)));
        }
        // 시작 노드에서, 시작 노드로 가는 값이 초기에 가장 짧은 비용을 갖는 노드이다.
        // 즉, 도착 정점은 start, 비용은 0인 노드를 가장 먼저 선택할 것이다.
        Node temp = new Node(stations.get(start), start);
        temp.setData(0, 0,0);
        q.offer(temp);
        // 해당 노드를 선택한 것이나 마찬가지 이므로, dist[start] = 0으로 갱신.
        dist[stations.get(start)] = 0;
        while (!q.isEmpty()) {
            Node curNode = q.poll();
            if (curNode.idx == stations.get(end)) { // 목표 노드가 구해졌다면 탈출
                break;
            }

            if (dist[curNode.idx] < curNode.getData(type)) { // 더 큰 비용으로 가는 방법은 고려할 필요가 없으므로 무시
                continue;
            }

            // 선택된 노드의 모든 주변 노드를 고려
            for (int i = 0; i < graph.get(curNode.idx).size(); i++) {
                Node nxtNode = graph.get(curNode.idx).get(i);

                // 현재까지 밝혀진 비용과 현재 발견한 비용을 비교하여 더 작은 값을 선택
                if (dist[nxtNode.idx] > curNode.getData(type) + nxtNode.getData(type)) {
                    // 갱신된 경우에만 큐에 넣는다.
                    dist[nxtNode.idx] = curNode.getData(type) + nxtNode.getData(type);
                    Node n = new Node(nxtNode.idx, nxtNode.name);
                    switch (tp) {
                        case 0:
                            n.setData(dist[nxtNode.idx], 0, 0);
                            break;
                        case 1:
                            n.setData(0, dist[nxtNode.idx], 0);
                            break;
                        case 2:
                            n.setData(0, 0, dist[nxtNode.idx]);
                            break;
                        default:
                            n.setData(dist[nxtNode.idx], 0, 0);
                            break;
                    }
                    route[nxtNode.idx] = curNode;
                    q.offer(n);
                }
            }
        }

        // 결과 출력
        //System.out.println(Arrays.toString(dist));
        //System.out.println(start + " -> " + end + ": " + dist[stations.get(end)]);
        Vector<Integer> routes = new Vector<>();
        int ior = 0;
        routes.add(end);
        for (int idx = stations.get(end); idx != stations.get(start); idx = route[idx].idx) {
            routes.add(route[idx].name);
        }
        //System.out.println(routes.size());
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = routes.size() - 1; i >= 0; i--){
            //System.out.print(routes.get(i));
            res.add(routes.get(i));
//            if (i != 0) {
//                System.out.print(" -> ");
//            }
        }

        return new FindRoute(dist[stations.get(end)], res);
    }
}
