package org.example.lesson14;


import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class EmpTester {
    public static void main(String[] args) {
        List<Emp> emps = List.of(
                new Emp("Max Petrov", 22, "programmer"),
                new Emp("Ivan Peregudov", 33, "analyst"),
                new Emp("Semen Dezhnev", 55, "manager"),
                new Emp("Katerina Drogova", 31, "programmer"),
                new Emp("Oleg Petrov", 19, "intern"),
                new Emp("Nicolas Spivakov", 23, "analyst"),
                new Emp("Boris Moiseev", 48, "manager"),
                new Emp("Alex Reingard", 33, "analyst"),
                new Emp("Olga Filimonova", 27, "programmer")
        );

        // сохраните в список и распечатайте всех работников с возрастом больше 41
        System.out.println(
                emps.stream()
                        .filter(e -> e.getAge() > 41)
                        .toList()
        );
// распечатайте только имена всех программистов
// Olga Filimonova -> Olga
        System.out.println(
                emps.stream()
                        .filter(p -> p.getPosition() == "programmer").map(n -> n.getName()).map(n -> n.split(" ")[0])
                        .toList()
        );
// посчитайте количество аналитиков
        System.out.println(
                emps.stream().filter(s -> s.getPosition() == "analyst").count()
        );
// найдите самого молодого работника
        System.out.println(
                emps.stream().min((o1, o2) -> Integer.compare(o1.getAge(), o2.getAge())).orElse(null)
        );
// посчитайте количество уникальных позиций 4
        System.out.println(
                emps.stream().map(p -> p.getPosition()).distinct().count()
        );
// распечатайте 3 самых опытных работников - кто старше всех
        System.out.println(
                emps.stream().map(p -> p.getAge()).sorted().skip(emps.size() - 3).toList()
        );
        // убедитесь что все работники старше 18 лет
        System.out.println(
                emps.stream().map(a -> a.getAge()).allMatch(a -> a > 18)
        );
        // распечатайте фамилии всех женщин - зачанчиваются на 'a'
        System.out.println(
                emps.stream().map(a -> a.getName()).filter(a -> a.endsWith("a"))
                        .map(name -> name.split(" ")[1]).toList()
        );
// найдите средний возраст
// mapToDouble()
// average()
        System.out.println(
                emps.stream().mapToDouble(a -> a.getAge()).average().orElse(0.0)
        );
// посчитайте количество программистов-мужчин - имя не оканчивается на 'a'
        System.out.println(
                emps.stream().filter(p -> p.getPosition() == "programmer")
                        .map(a -> a.getName()).filter(a -> !a.endsWith("a")).count()
        );

        System.out.println(
                emps.stream().sorted(Comparator.comparing(Emp::getPosition).thenComparingInt(Emp::getAge)).toList()
        );

        System.out.println(
                emps.stream().mapToInt(a -> a.getAge()).sum()
        );

//        HomeWork
//        Task1 Объедините в строку имена всех работников
//        "Max, Ivam, Semen, …"
        System.out.println(emps.stream().map(Emp::getName)
                .map(name -> name.split(" ")[0])
                .reduce((name1, name2) -> name1 + ", " + name2)
                .orElse("")
        );

//        Task2 Найдите профессию самого старшего из тех работников,
//        кому менее чем 40 лет
        System.out.println(emps.stream().filter(age -> age.getAge() < 40)
                .sorted(Comparator.comparing(Emp::getAge))
                .max((emp1, emp2) -> Integer.compare(emp1.getAge(), emp2.getAge()))
                .map(Emp::getPosition)
                .orElse("")
        );

//        HomeWork 2
        // Посчитайте средний возраст всех работников используя Collectors.averagingDouble
        System.out.println(
                emps.stream()
                        .collect(
                                Collectors.averagingDouble(
                                        Emp::getAge
                                )
                        )
        );

        // Найдите самого молодого работника используя Collectors.minBy
// и компаратор по возрасту
        System.out.println(
                emps.stream()
                        .collect(
                                Collectors.minBy(
                                        Comparator.comparing(e -> e.getAge())
                                )
                        ).orElse(null)
        );

        // Разбейте работников на группы по возрасту - 10-19 (1), 20-29 (2),
// 30-39 (3), 40-49 (4), 50-59 (5) и так далее лет -
// Map<Integer, List<Emp>> - ключи мапы  1,2,3,4,5  и т.п. -
// количество полных десятков лет в возрасте
        System.out.println(
                emps.stream()
                        .collect(
                                Collectors.groupingBy(
                                        e -> e.getAge()/10
                                )
                        )
        );

        //  Найдите средний возраст мужчин и женщин (считаем что фамилии женщин
//  оканчиваются на 'a') в виде Map<Boolean, Double> - ключ true
//  соответствует женщинам - воспользуйтесь Collectors.partitioningBy и
//  Collectors.averagingDouble
        System.out.println(
                emps.stream()
                        .collect(
                                Collectors.partitioningBy(
                                        e -> e.getName().endsWith("a")
                                )
                        )
        );

        //  Найдите средний возраст мужчин и женщин (считаем что фамилии женщин
//  оканчиваются на 'a') в виде Map<Boolean, Double> - ключ true
//  соответствует женщинам - воспользуйтесь Collectors.partitioningBy и
//  Collectors.averagingDouble
        System.out.println(
                emps.stream()
                        .collect(
                                Collectors.partitioningBy(
                                        e -> e.getName().endsWith("a"),
                                        Collectors.averagingDouble(
                                                e -> e.getAge()
                                        )
                                )
                        )
        );

        // Найдите Map<String, Emp> - работников с самым большим возрастом в
// каждой профессии - воспользуйтесь Collectors.groupingBy и Collectors.maxBy
        System.out.println(
                emps.stream()
                        .collect(
                                Collectors.groupingBy(
                                        e -> e.getPosition(),
                                        Collectors.maxBy(
                                                Comparator.comparing(
                                                        e -> e.getAge()
                                                )
                                        )
                                )
                        )
        );

        // Найдите работников с самым часто встречающимся возрастом в виде List<Emp>
/*
Emp -> Pair<Integer, List<Emp>> ->
 */
        System.out.println(
                emps.stream()
                        .collect(
                                Collectors.groupingBy(
                                        e -> e.getAge()
                                )
                        ) // Map<Integer, List<Emp>>
                        .entrySet().stream()
                        .max(
                                Comparator.comparing(
                                        pair -> pair.getValue().size()
                                )
                        )
                        .map(
                                pair -> pair.getValue()
                        )
                        .orElse(null)
        );

        // Найдите пару работников с самой большой разницей в возрасте -
// воспользуйтесь flatMap для создания потока всех возможных пар
// каждого работника со всеми остальными
        System.out.println(
                emps.stream()
                        .flatMap(
                                e -> emps.stream().map(
                                        emp -> new AbstractMap.SimpleEntry<>(e, emp)
                                )
                        ) // Pair<Emp, Emp>
                        .max(
                                (p1, p2) -> (p1.getKey().getAge() - p1.getValue().getAge()) - (p2.getKey().getAge() - p2.getValue().getAge())
                        )
                        .orElse(null)
        );
    }
}
