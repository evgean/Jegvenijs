package ru.geekbrain.s1.e5;

public class Main {
    private static String[] vowelsLetters = {"а", "у", "о", "ы", "и", "э", "я", "ю", "ё", "е"};
    private static String[] consonantsLetters = {"б", "в", "г", "д", "ж", "з", "к", "л", "м", "н", "п", "р", "с", "т", "ф", "х", "ц", "ч", "ш", "щ"};
    private static String[] surNameEnd = { "ан", "ын", "ин", "ских", "ов", "ев", "ской", "цкой", "их", "ых"};
    private static String[] positions = { "Высший менеджер компании", "Генеральный директор", "Исполнительный директор", "Финансовый директор", "Коммерческий директор", "Директор по персоналу", "Директор по маркетингу", "Директор по логистике"};
    private static final int workersCount = 5;

    public static void main(String[] args) {
        Worker[] workersArr = new Worker[workersCount];

        addWorkers(workersArr, workersCount);

        printWorkerByAge(workersArr, 40);
    }

    //метод генерирует случайное имя с фамилией
    private static String generateFullName() {
        StringBuilder fullName = new StringBuilder();
        if (generator("bool") == 0) {
            for (int i = 0; i < generator("nameLength"); i++) {
                fullName.append(vowelsLetters[generator("vowelsLetters")]);
                fullName.append(consonantsLetters[generator("consonantsLetters")]);
            }
        } else {
            for (int i = 0; i < generator("nameLength"); i++) {
                fullName.append(consonantsLetters[generator("consonantsLetters")]);
                fullName.append(vowelsLetters[generator("vowelsLetters")]);
            }
        }
        fullName.append(consonantsLetters[generator("vowelsLetters")]).append(" ");

        for (int i = 0; i < generator("nameLength"); i++) {
            fullName.append(vowelsLetters[generator("vowelsLetters")]);
            fullName.append(consonantsLetters[generator("consonantsLetters")]);
        }
        fullName.append(surNameEnd[generator("surNameEnd")]);

        return fullName.toString();
    }

    //генерация случайных индексов и чисел для определенных нужд
    private static int generator(String name) {
        if (name.equals("vowelsLetters")) return (int) (Math.random() * vowelsLetters.length);
        else if (name.equals("bool")) return (int)(Math.random() * 2);
        else if (name.equals("consonantsLetters")) return (int) (Math.random() * consonantsLetters.length);
        else if (name.equals("surNameEnd")) return (int) (Math.random() * surNameEnd.length);
        else if (name.equals("nameLength")) return 2 + ((int) (Math.random() * 3));
        else if (name.equals("salary")) return 1000 + ((int) (Math.random() * 100000));
        else if (name.equals("age")) return 18 + ((int) (Math.random() * 76));
        else if (name.equals("positions")) return (int) (Math.random() * positions.length);
        else if (name.equals("phone")) {
            StringBuilder number = new StringBuilder();
            number.append("89");
            for (int i = 0; i < 7; i++) {
                number.append(String.valueOf((int)(Math.random() * 10)));
            } return Integer.parseInt(number.toString());
        }
        return -1;
    };

    //добовления работников в массив
    private static Worker[] addWorkers(Worker[] arr, int count) {
        for (int i = 0; i < count; i++) {
            String fullName = generateFullName();
            arr[i] = new Worker(fullName, positions[generator("positions")], fullName.split(" ")[0] + "@company.com", generator("phone"), generator("salary"), generator("age"));
        } return arr;
    }

    //печать сотрудников по указанному возросту
    private static void printWorkerByAge(Worker[] arr, int age) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].getAge() > age) {
                arr[i].printWorker();
            }
        }
    }

}
