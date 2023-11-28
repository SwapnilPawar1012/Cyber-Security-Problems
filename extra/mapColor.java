package extra;

import java.util.*;

public class mapColor {

  // Define the regions (variables) and their neighbors (constraints)
  enum Region {
    A,
    B,
    C,
    D,
    E,
    F,
    G,
  }

  // Define the domains for each region
  static Map<Region, List<String>> colorDomains = new HashMap<>();

  static {
    colorDomains.put(Region.A, Arrays.asList("Red", "Green", "Blue"));
    colorDomains.put(Region.B, Arrays.asList("Red", "Green", "Blue"));
    colorDomains.put(Region.C, Arrays.asList("Red", "Green", "Blue"));
    colorDomains.put(Region.D, Arrays.asList("Red", "Green", "Blue"));
    colorDomains.put(Region.E, Arrays.asList("Red", "Green", "Blue"));
    colorDomains.put(Region.F, Arrays.asList("Red", "Green", "Blue"));
    colorDomains.put(Region.G, Arrays.asList("Red", "Green", "Blue"));
  }

  // Define neighbors of the regions
  static Map<Region, List<Region>> neighboringRegions = new HashMap<>();

  static {
    neighboringRegions.put(Region.A, Arrays.asList(Region.B, Region.C));
    neighboringRegions.put(
      Region.B,
      Arrays.asList(Region.A, Region.C, Region.D)
    );
    neighboringRegions.put(
      Region.C,
      Arrays.asList(Region.A, Region.B, Region.D, Region.E, Region.F)
    );
    neighboringRegions.put(
      Region.D,
      Arrays.asList(Region.B, Region.C, Region.E)
    );
    neighboringRegions.put(
      Region.E,
      Arrays.asList(Region.C, Region.D, Region.F)
    );
    neighboringRegions.put(
      Region.F,
      Arrays.asList(Region.C, Region.E, Region.G)
    );
    neighboringRegions.put(Region.G, Arrays.asList(Region.F));
  }

  // Constraint function: Check if two regions have different colors
  static boolean isDifferentColor(
    Region region1,
    String color1,
    Region region2,
    String color2
  ) {
    return !color1.equals(color2);
  }

  // Backtracking algorithm to solve Map Coloring Problem
  static boolean mapColoring(Map<Region, String> assignment) {
    if (assignment.size() == colorDomains.size()) {
      return true; // all regions are colored
    }

    Region unassignedRegion = null;
    for (Region region : colorDomains.keySet()) {
      if (!assignment.containsKey(region)) {
        unassignedRegion = region;
        System.out.println(unassignedRegion);
        break;
      }
    }

    if (unassignedRegion != null) {
      for (String color : colorDomains.get(unassignedRegion)) {
        boolean consistent = true;
        assignment.put(unassignedRegion, color);

        for (Region neighbor : neighboringRegions.get(unassignedRegion)) {
          if (
            assignment.containsKey(neighbor) &&
            !isDifferentColor(
              unassignedRegion,
              color,
              neighbor,
              assignment.get(neighbor)
            )
          ) {
            System.out.println("consistent false");
            consistent = false;
            break;
          }
        }

        System.out.println("***********");
        if (consistent && mapColoring(assignment)) {
          return true;
        }

        assignment.remove(unassignedRegion);
      }
    }
    return false;
  }

  public static void main(String[] args) {
    Map<Region, String> assignment = new HashMap<>();

    if (mapColoring(assignment)) {
      System.out.println("Solution Found: ");
      for (Map.Entry<Region, String> entry : assignment.entrySet()) {
        System.out.println(entry.getKey() + " -> " + entry.getValue());
      }
    } else {
      System.out.println("Solution Not Found.");
    }
  }
}
// Solution found:
// C -> Red
// E -> Green
// A -> Blue
// G -> Red
// F -> Blue
// B -> Green
// D -> Blue
