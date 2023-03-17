import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        System.out.println("JDCP-6 + Евгений Орлов + ДЗ-22 + Stream API. Потоки, повторные вызовы, основные методы\n");

        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }
// 1.
        System.out.println("1. Найти количество несовершеннолетних (т.е. людей младше 18 лет).");
        long littleAge = persons.stream()
                .filter(person -> person.getAge() < 18)
                .count();
        System.out.println("Найдено всего: " + littleAge + " человек(ОВ:))");
// 2.
        System.out.println("\n2. Получить список фамилий призывников (т.е. мужчин от 18 и до 27 лет).");
        List<String> LuckyFamilies = persons.stream()
                .filter(person -> person.getSex().equals(Sex.MAN))
                .filter(person -> person.getAge() >= 18 & person.getAge() < 27)
                .map(person -> person.getFamily())
                .collect(Collectors.toList());
        System.out.printf("Получен список фамилий счастливчиков в колличестве: %s\n",
                LuckyFamilies.size());
// 3.
        System.out.println("\n3. Получить отсортированный по фамилии список " +
                "потенциально работоспособных людей с высшим образованием в выборке ");
        System.out.println("(т.е. людей с высшим образованием " +
                "от 18 до 60 лет для женщин и до 65 лет для мужчин).");
        List<String> DoomedToPlow = persons.stream()
                .filter(person -> person.getEducation().equals(Education.HIGHER))
                .filter(person -> (person.getSex().equals(Sex.MAN)) ?
                        (person.getAge() >= 18 & person.getAge() < 65) :
                        (person.getAge() >= 18 & person.getAge() < 60))
                .sorted(Comparator.comparing(Person::getFamily))
                .map(Person::toString)
                .toList();
        System.out.printf("Что учились, то зря: %s человек(а) ;)\n", DoomedToPlow.size());

//        if (!DoomedToPlow.isEmpty()) {// для контроля
//            System.out.println("\nПервые два человека списка:");
//            DoomedToPlow.stream()
//                    .limit(2)
//                    .forEach(System.out::println);
//        }
    }
}