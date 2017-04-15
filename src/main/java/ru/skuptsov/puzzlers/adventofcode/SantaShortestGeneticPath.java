package ru.skuptsov.puzzlers.adventofcode;

import ru.skuptsov.algorythms.graph.travellingsalesmanproblem.GenericTCP;
import ru.skuptsov.algorythms.graph.travellingsalesmanproblem.TravellingSalesmanProblem;
import ru.skuptsov.puzzlers.MapUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author Sergey Kuptsov
 * @since 18/04/2016
 */
public class SantaShortestGeneticPath {

  public static void main(String[] args) throws IOException, URISyntaxException {

    Stream<String> lines = Files.lines(Paths.get(SantaShortestGeneticPath.class.getResource("/santaCities").toURI()));


    Map<String, Map<String, Double>> distances = new HashMap<>();
    lines.forEach(line -> {
      String[] params = line.split(" ");
      String cityFrom = params[0];
      String cityTo = params[2];

      double distance = new Double(params[4]);

      Map<String, Double> distanceMap = MapUtils.putIfAbsent(distances, cityFrom, new HashMap<>());

      Map<String, Double> reverseDistanceMap = MapUtils.putIfAbsent(distances, cityTo, new HashMap<>());

      distanceMap.put(cityTo, distance);
      reverseDistanceMap.put(cityFrom, distance);
    });


    TravellingSalesmanProblem<String> tcp = new GenericTCP<>(distances);
    tcp.buildShortestRoute();
  }
}
