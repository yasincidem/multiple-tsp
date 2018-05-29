package edu.anadolu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static edu.anadolu.TurkishNetwork.cities;
import static edu.anadolu.TurkishNetwork.distance;

/**
 * Created by yasin_000 on 26.5.2018.
 */
class Solution {
    private final Random rand = new Random();
    List<Integer> depots;
    List<List<Integer>> routes;
    int cost ;

    Solution(List<Integer> depots, List<List<Integer>> routes) {
        this.depots = depots;
        this.routes = routes;
        cost();
    }

    Solution(Solution solution) {
        List<Integer> depotList = new ArrayList<>();
        List<List<Integer>> routeList = new ArrayList<>();
        for (int i = 0; i < solution.depots.size(); i++) {
            depotList.add(solution.depots.get(i));
        }
        for (int i = 0; i < solution.routes.size(); i++) {
            routeList.add(new ArrayList<Integer>());
            for (int j = 0; j < solution.routes.get(i).size(); j++) {
                routeList.get(i).add(solution.routes.get(i).get(j));
            }
        }
        this.depots = depotList;
        this.routes = routeList;
        cost();
    }

    void swapNodesInRoute() {
        final int i = rand.nextInt(routes.size());



        if (routes.get(i).size() > 3 ) {
            final List<Integer> collect = rand.ints(1, routes.get(i).size() - 1)
                    .distinct()
                    .limit(2)
                    .boxed()
                    .collect(Collectors.toList());

            Collections.swap(routes.get(i), collect.get(0), collect.get(1));


        }
        cost();
    }

    void swapHubWithNodeInRoute() {
        final List<Integer> collect = rand.ints(0, depots.size())
                .distinct()
                .limit(2)
                .boxed()
                .collect(Collectors.toList());
        Collections.swap(depots, collect.get(0), collect.get(1));
        List<Integer> first = new ArrayList<>();
        List<Integer> second = new ArrayList<>();
        for (int j = 0; j < routes.size(); j++) {
            if (routes.get(j).get(0) == depots.get(collect.get(0)) ) {
                first.add(j);
            }
            if (routes.get(j).get(0) == depots.get(collect.get(1))) {
                second.add(j);
            }
        }
        for (int i = 0; i < first.size(); i++) {
            routes.get(first.get(i)).set(0, depots.get(collect.get(1)));
            routes.get(first.get(i)).set(routes.get(first.get(i)).size() - 1, depots.get(collect.get(1)));

            routes.get(second.get(i)).set(0, depots.get(collect.get(0)));
            routes.get(second.get(i)).set(routes.get(second.get(i)).size() - 1, depots.get(collect.get(0)));
        }


        cost();
    }

    void swapNodesBetweenRoutes() {
        final List<Integer> collect = rand.ints(0, routes.size())
                .distinct()
                .limit(2)
                .boxed()
                .collect(Collectors.toList());
        if (routes.get(collect.get(0)).size() > 2 && routes.get(collect.get(1)).size() > 2) {
            int first = rand.nextInt(routes.get(collect.get(0)).size() - 1 - 1) + 1;
            int second = rand.nextInt(routes.get(collect.get(1)).size() - 1 - 1) + 1;


            int felem = routes.get(collect.get(0)).get(first);
            int selem = routes.get(collect.get(1)).get(second);




            routes.get(collect.get(0)).set(first, selem);
            routes.get(collect.get(1)).set(second, felem);


        }
        cost();
    }

    void insertNodeInRoute() {
        final int i = rand.nextInt(routes.size());



        if (routes.get(i).size() > 4 ) {
        final List<Integer> collect = rand.ints(1, routes.get(i).size() - 2 )
                .distinct()
                .limit(2)
                .boxed()
                .collect(Collectors.toList());




            Integer elemWillRemove =  routes.get(i).get(collect.get(0));
            Integer second =  routes.get(i).get(collect.get(1));


            routes.get(i).remove(elemWillRemove);

            routes.get(i).add((collect.get(1))  , elemWillRemove);



        }

        cost();
    }

    void insertNodeBetweenRoutes() {

        final List<Integer> collect = rand.ints(0, routes.size())
                .distinct()
                .limit(2)
                .boxed()
                .collect(Collectors.toList());
        if (routes.get(collect.get(0)).size() > 2 && routes.get(collect.get(1)).size() > 2) {
        int first = rand.nextInt(routes.get(collect.get(0)).size() - 1 - 1) + 1;
        int second = rand.nextInt(routes.get(collect.get(1)).size() - 1 - 1) + 1;


        int felem = routes.get(collect.get(0)).get(first);
        int selem = routes.get(collect.get(1)).get(second);




            if (routes.get(collect.get(0)).size() > 3 ) {

                routes.get(collect.get(0)).set(first, selem);
                routes.get(collect.get(1)).set(second, felem);

                Integer elemWillRemove = routes.get(collect.get(0)).get(first);

                routes.get(collect.get(0)).remove(elemWillRemove);

                routes.get(collect.get(1)).add(second + 1, elemWillRemove);




            }

        }


        cost();
    }

    void print(int numSalesmen) {

        for (int i = 0; i < routes.size(); i++) {
            final List<Integer> list = routes.get(i);

            if ((i%numSalesmen) == 0) {
                System.out.println("Depot" + (i / numSalesmen + 1)+ ": " + cities[depots.get(i / numSalesmen)]);
            }
            System.out.print("  Route:" + (i%numSalesmen + 1) + " ");

            for (int j = 0; j < list.size(); j++) {
                if (j != 0 && j != list.size() - 1)
                    System.out.print(cities[list.get(j)] + (j != list.size() - 2 ? ", " : " " ));
            }
            System.out.println();
        }
    }

    void cost () {
        cost = 0;
        for (final List<Integer> list : routes) {
            for (int j = 0; j < list.size() - 1; j++) {
                cost += distance[list.get(j)][list.get(j + 1)];
            }
        }
    }
}
