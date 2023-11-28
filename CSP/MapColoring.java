package CSP;

import java.util.*;

public class MapColoring {

  enum Region {
    A,
    B,
    C,
    D,
    E,
    F,
    G,
  }

  // Define color domains
  static Map<Region, List<String>> colorDomains = new HashMap<>();

  static {
    colorDomains.put(Region.A, Arrays.asList("Red", "Blue", "Green"));
    colorDomains.put(Region.B, Arrays.asList("Red", "Blue", "Green"));
    colorDomains.put(Region.C, Arrays.asList("Red", "Blue", "Green"));
    colorDomains.put(Region.D, Arrays.asList("Red", "Blue", "Green"));
    colorDomains.put(Region.E, Arrays.asList("Red", "Blue", "Green"));
    colorDomains.put(Region.F, Arrays.asList("Red", "Blue", "Green"));
    colorDomains.put(Region.G, Arrays.asList("Red", "Blue", "Green"));
  }

  // Define Region's neighboring nodes
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

  static boolean isDifferentColor(
    Region region1,
    String color1,
    Region region2,
    String color2
  ) {
    return !color1.equals(color2);
  }

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
            consistent = false;
            break;
          }
        }

        System.out.println("************");
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
      System.out.println("Path Found: ");
      for (Map.Entry<Region, String> entry : assignment.entrySet()) {
        System.out.println(entry.getKey() + " -> " + entry.getValue());
      }
    } else {
      System.out.println("No path found.");
    }
  }
}
/*
Path Found:
F -> Red
A -> Red
D -> Red
B -> Blue
E -> Blue
G -> Blue
C -> Green
 */
