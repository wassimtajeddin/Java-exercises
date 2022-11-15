package exercises;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Chapter8 {
    public static void main(String[] args) {


    }

    private static void task19() {
        double sum = getCountries().stream()
                .filter(i -> i.countryName.length() == 7)
                .mapToDouble(i -> i.population)
                .sum();
        System.out.println(sum);
    }

    private static void task18() {
        double sixSmallest = getCountries().stream()
                .sorted(Comparator.comparing(Country::area))
                .limit(6)
                .mapToDouble(Country::population)
                .sum();

        double threeLargest = getCountries().stream()
                .sorted(Comparator.comparing(Country::area).reversed())
                .limit(3)
                .mapToDouble(Country::population)
                .sum();

        System.out.println("The sum of the six smallest (by area) countries' populations is: " + sixSmallest);
        System.out.println("The sum of the three greatest (by area) countries' pupulations is: " + threeLargest);
    }

    private static void task17() {
        getCountries().stream()
                .sorted(Comparator.comparing(Chapter8::getReverse).reversed())
                .forEach(s -> System.out.println(s.countryName() + " " + getReverse(s)));
    }

    private static String getReverse(Country country) {
        return new StringBuilder(country.capital).reverse().toString();
    }

    private static void task16() {
        //        getCountries().forEach(country -> System.out.println(
//                        country.countryName() + ", population: " + (int)(country.population()*1000_000)));

        getCountries().stream()
                .map(Statistics::of)
                .sorted(Comparator.comparingDouble(Statistics::density))
                .forEach(s -> System.out.println(s.countryName() + ", density: " + s.density()));
    }

    record Statistics(String countryName, double density) {
        private static Statistics of(Country country) {
            return new Statistics(country.countryName(), country.population() * 1000_000 / country.area());
        }
    }


    private static void task15() {
        var groupedByPopulation = getCountries().stream()
                .sorted(Comparator.comparing(Country::countryName))
                .collect(Collectors.groupingBy(country -> (int) country.population));

        groupedByPopulation.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(Chapter8::printCountryGroups);
    }


    private static void getListByPopulationSorted(List<Country> countryList, int x) {
        List<Country> countryListSorted = countryList.stream()
                .filter(country -> Math.round(country.population) == x)
                .sorted(Comparator.comparing(country -> country.countryName))
                .toList();

        System.out.println("Länder med " + x + " miljoner invånare:");
        countryListSorted.forEach(country -> System.out.println(country.countryName));
    }

    private static void task14() {
        getCountries().stream()
                .collect(Collectors.groupingBy(c -> c.countryName().charAt(0), Collectors.counting()))
                .forEach((character, count) -> System.out.println(character + " : " + count));

        Map<String, Long> collectByLetter = getCountries().stream()
                .collect(Collectors.groupingBy(country -> country.countryName.substring(0, 1), Collectors.counting()));
        System.out.println(collectByLetter);

// .collect(Collectors.groupingBy(c -> c.name().charAt(0), Collectors.counting()))


        //String concatenate
        //You'll get a string result from + if one of the operands is a string.
//        String s = "" + (10 + 10) + " : ";
//        String s2 = 10 + 10 + " : ";
//        String intToString = 10 + "";
//        System.out.println(s);
    }


    private static void task13() {
        getCountries().stream()
                .filter(country -> country.area >= 500_000)
                .sorted(Comparator.comparing(Country::countryName).reversed())
                .limit(3)
                .map(Country::countryName)
                .forEach(System.out::println);
    }

    private static void task12() {
        getCountries().stream()
                .filter(country -> country.population > 7)
                .sorted(Comparator.comparing(Country::population))
                .limit(3)
                .map(Country::countryName)
                .forEach(System.out::println);
    }

    private static void task11() {
        getCountries().stream()
                .sorted(Comparator.comparingDouble(Country::population))
                .limit(5)
                .map(Country::countryName)
                .forEach(System.out::println);
    }

    private static void task10() {
        getCountries().stream()
                .filter(c -> c.countryName().length() > c.capital().length())
                .map(Country::countryName)
                .forEach(System.out::println);
    }

    private static void task9() {
        getCountries().stream()
                //.filter(country -> !country.countryName().isEmpty() && !country.capital().isEmpty() && country.countryName().charAt(0) == country.capital().charAt(0))
                .filter(country -> country.countryName().charAt(0) == country.capital().charAt(0))
                .forEach(country -> System.out.println(country.countryName() + " " + country.capital()));
//                .filter(country -> country.countryName.startsWith(String.valueOf(country.capital.charAt(0))))
//                .forEach(country -> System.out.println(country.countryName() + ": " + country.capital()));
//                .filter(country -> country.countryName.substring(0, 1).equals(country.capital.substring(0, 1)))
//                .forEach(country -> System.out.println(country.countryName() + ": " + country.capital()));
    }

    private static void task8Complicated() {
        for (int i = 10_000; i < 1_000_001; i = i * 10) {
            int finalI = i;
            long count2 = getCountries().stream()
                    .filter(country -> country.area() > finalI)
                    .count();
            System.out.println(count2);
        }
    }

    private static void task8() {
        populationOver(getCountries(), 10_000);
        populationOver(getCountries(), 100_000);
        populationOver(getCountries(), 1_000_000);
    }

    public static void populationOver(List<Country> countries, int x) {
        long count = countries.stream()
                .filter(country -> country.area > x)
                .count();
        System.out.println(count);
    }

    private static void task7() {
        getCountries().stream()
                .filter(country -> (country.population() < 5))
                .map(Country::countryName)
                .forEach(System.out::println);
    }

    private static void task6() {
        double averageArea = getCountries().stream()
                .mapToInt(Country::area)
                .average().orElse(0);
        System.out.printf("Average area is: %.2f\n", averageArea);

        long nBelowAverage = getCountries().stream()
                .filter(country -> country.area() < averageArea)
                .count();

        System.out.println("Number of countries below average are: " + nBelowAverage);
    }

    private static void task5() {
        double v = getCountries().stream()
                .mapToDouble(Country::population)
                .max()
                .orElse(0.0);
        System.out.println(v);
    }

    private static void task4() {
        getCountries().stream()
                .sorted(Comparator.comparingDouble(Country::population).reversed())
                .map(Country::countryName)
                .forEach(System.out::println);
    }

    private static void task3() {
        getCountries().stream()
                .sorted(Comparator.comparing(Country::countryName))
                .map(Country::countryName)
                .forEach(System.out::println);
    }

    private static void task2() {
        String s = getCountries().stream()
                .map(Country::countryName)
                .findFirst()
                .orElse("");
        System.out.println(s);

        String s1 = getCountries().stream()
                .skip(getCountries().size() - 1)
                .map(Country::countryName)
                .findFirst()
                .orElse("");
        System.out.println(s1);

        String s2 = getCountries().stream()
                .reduce((first, second) -> second)
                .map(Country::countryName)
                .orElse("");
        System.out.println(s2);
    }

    private static List<Country> getCountries() {
        return List.of(
                new Country("Sverige", "Stockholm", 10.07, 450295),
                new Country("Norge", "Oslo", 5.27, 323802),
                new Country("Island", "Reykjavik", 0.33, 102775),
                new Country("Danmark", "Köpenhamn", 5.75, 42931),
                new Country("Finland", "Helsinki", 5.51, 338424),
                new Country("Belgien", "Bryssel", 11.30, 30528),
                new Country("Tyskland", "Berlin", 82.18, 357168),
                new Country("Frankrike", "Paris", 66.99, 640679),
                new Country("Storbritannien", "London", 60.80, 209331),
                new Country("Niue", "Alofi", 0.0016, 261),
                new Country("Mongoliet", "Ulan Batar", 3.08, 1566000),
                new Country("Polen", "Warszawa", 38.63, 312679),
                new Country("Spanien", "Madrid", 46.5, 505990),
                new Country("Portugal", "Lissabon", 10.31, 92212),
                new Country("Italien", "Rom", 60.59, 301338),
                new Country("Grekland", "Aten", 11.18, 131957),
                new Country("Luxemburg", "Luxemburg", 0.58, 2586),
                new Country("Liechtenstein", "Vaduz", 0.038, 160));
    }

    private static void printCountryGroups(Map.Entry<Integer, List<Country>> entrySet) {
        System.out.println("Länder med " + entrySet.getKey() + " miljoner invånader:");
        entrySet.getValue().forEach(country -> System.out.println(" - " + country.countryName));
    }


    record Country(String countryName, String capital, double population, int area) {
    }
}
