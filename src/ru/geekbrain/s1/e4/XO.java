package ru.geekbrain.s1.e4;

import java.util.Random;
import java.util.Scanner;

public class XO {
    private static final Scanner scanner = new Scanner(System.in);
    //специально держу дефолтные значения, что бы если что игралось по стандартным правилам
    private static int[] fieldSize = {3, 3};
    private static final char emptyChar = '*';
    private static final char playerChar = 'X';
    private static final char aiChar = 'O';
    private static int difficult = 2;
    private static int winGrid = 3;
    private static int count = 0;
    private static Random rnd = new Random();
    private static char[][] field;
    private static int[] aiMoveArr = new int[2];

    public static void main(String[] args) {
        System.out.println("    ДОБРО ПОЖАЛИВАТЬ В ИГРУ КРЕСТИКИ НОЛИКИ");
        System.out.println("УДЕЛИТЕ МИНУТКУ И ОПРЕДЕЛИТЕ ВИД И ПРАВИЛО ИГРЫ");
        System.out.println("-----------------------------------------------");
        //определяем размер поля (любого по желанию)
        fieldSize = askCoor("1. Введите, через пробел, размер поля где высота - это первая цифра, а длина - вторая: ");

        //определяем условие победы (сколько в ряд крестиков нужно). Но нельзя, что бы цифра превышала длину поля
        while(true) {
            winGrid = ask("2. Сколько в ряд крестиков нужно для победы: ");
            if (winGrid <= Math.max(fieldSize[0], fieldSize[1])) break;
            System.out.println("Ваше число превышает размер поля, а значит не будет победителя. Оно Вам нужно? Попробуйте еще раз");
        }

        //определяем сложность
        difficult = ask("3. Введите цифру сложности, где 1. Я маменькин сынок 2. Я уже не девственник 3. Я воевал, зелень! : ");

        System.out.println("------------------------------------------------------------");
        System.out.println("ДОБРО ПОЖАЛОВАТЬ НА КРАВОПРОЛИТИЕ!");
        System.out.println("Не забывайте, что координаты крестиков вводятся через пробел(или Enter).");
        System.out.println("Первая цифра - вертикальные ячейки, а вторая - горизонтальные.");
        System.out.println("ПУСТЬ ПОБЕДИТ СИЛЬНЕЙШИЙ!");
        System.out.println("------------------------------------------------------------");

        //создаем поле
        field = fieldInit(fieldSize[0], fieldSize[1]);

        //тело самой игры
        while (true) {
            fieldPrint();
            while (true) {
                int[] player = askCoor("Ваш ход: ");
                botComment();
                if (isInField(player) && isEmpty(player)) {
                    shoot("player", player);
                    count++;
                    break;
                }
                System.out.println("Таких координт выбрать нельзя. Попробуйте еще раз. ");
            }
            if (isWin("player")) {
                fieldPrintWin("player");
                break;
            } else if (isDraw()) {
                System.out.println("--------------------------НИЧЬЯ---------------------------");
                System.out.println("Если посудить не самый худший расклад, но скушный. Пока ..");
                break;
            }
            shoot("ai", aiMove());
            if (isWin("ai")) {
                fieldPrintWin("ai");
                break;
            } else if (isDraw()) {
                System.out.println("--------------------------НИЧЬЯ---------------------------");
                System.out.println("Если посудить не самый худший расклад, но скушный. Пока ..");
                break;
            }
        }
    }

    //что бы при вопросе сразу возврощать ответ
    private static int ask(String text) {
        System.out.print(text);
        return scanner.nextInt();
    }

    //запрашиваем координаты, получаем массив
    private static int[] askCoor(String text) {
        System.out.print(text);
        int[] arr = new int[2];
        arr[0] = scanner.nextInt();
        arr[1] = scanner.nextInt();
        return arr;
    }

    //инициализируем поле, заполняем '*'
    private static char[][] fieldInit (int x, int y) {
        char[][] field = new char[x][y];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) field[i][j] = emptyChar;
        }
        return field;
    }

    //рисуем поле
    private static void fieldPrint() {
        //первая строчка
        for (int i = 0; i <= field[0].length; i++) System.out.print(i + " ");
        System.out.println();

        for (int i = 0; i < field.length; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < field[0].length; j++) System.out.print(field[i][j] + " ");
            System.out.println();
        }
    }

    private static void fieldPrintWin(String player) {
        if (player.equals("player")) {
            System.out.println("----------------------------");
            System.out.println("\u001B[32m" + "| You WIN. Congratulation!!|" + "\u001B[0m");
            System.out.println("----------------------------");
            fieldPrint();

            System.out.println("Вы выйграли с " + count + "-ой попытки.");
        } else {
            System.out.println("\u001B[31m" + "You are LOOSER!!!. " + "\u001B[0m");
            fieldPrint();
        }

    };

    //логика хода. Работает как на игрока так и на ИИ
    private static void shoot(String turn, int[] coor) {
        if (turn.equals("player")) field[coor[0] - 1][coor[1] - 1] = playerChar;
        else field[coor[0] - 1][coor[1] - 1] = aiChar;
    }

    //логика ходов ИИ. Зависит от сложности указанной игроком
    private static int[] aiMove() {
        boolean loop = true;
        //сложность N1. random числа
        if (difficult == 1) {
            while (true) {
                aiMoveArr[0] = rnd.nextInt(fieldSize[0]) + 1;
                aiMoveArr[1] = rnd.nextInt(fieldSize[1]) + 1;

                if (isInField(aiMoveArr) && isEmpty(aiMoveArr)) break;
            }
        //сложность N2. проверка на одну клетку
        } else if (difficult == 2) {
            //Что бы циклы не выполнялись подряд если есть результат поиска, добавляю if
            if (loop) {
                //горизонтальное положение
                for (int i = 0; i < field.length; i++) {
                    for (int j = 0; j < field[0].length - (winGrid - 1); j++) {
                        int sum = 0;
                        for (int k = 0; k < winGrid; k++) if (field[i][j + k] == aiChar) sum++;
                        if ( sum == winGrid - 1 && field[i][j] == playerChar) {
                            int[] arr2 = {i + 1, j};
                            int[] arr3 = {i + 1, j + winGrid};
                            loop = checkAiLogic(arr2, arr3);
                        } if (!loop) break;
                    } if (!loop) break;
                }
            }
            if (loop) {
                //вертикально
                for (int i = 0; i < field.length - (winGrid - 1); i++) {
                    for (int j = 0; j < field[0].length; j++) {
                        int sum = 0;
                        for (int k = 0; k < winGrid; k++) if (field[i + k][j] == aiChar) sum++;
                        if ( sum == winGrid - 1 ) {
                            int[] arr2 = {i + 1, j + 1};
                            int[] arr3 = {i + winGrid, j + 1};
                            loop = checkAiLogic(arr2, arr3);
                        } if (!loop) break;
                    } if (!loop) break;
                }
            }
            if (loop) {
                //диагональ лево-права
                for (int i = 0; i < field.length - (winGrid - 1); i++) {
                    for (int j = 0; j < field[0].length - (winGrid - 1); j++) {
                        int sum = 0;
                        for (int k = 0; k < winGrid; k++) if (field[i + k][j + k] == aiChar) sum++;
                        if ( sum == winGrid - 1 ) {
                            int[] arr2 = {i + 1, j + 1};
                            int[] arr3 = {i + winGrid, j + winGrid};
                            loop = checkAiLogic(arr2, arr3);
                        } if (!loop) break;
                    } if (!loop) break;
                }
            }
            if (loop) {
                //диагональ право-лево
                for (int i = winGrid - 1; i < field.length; i++) {
                    for (int j = 0; j < field[0].length - (winGrid - 1); j++) {
                        int sum = 0;
                        for (int k = 0; k < winGrid; k++) if (field[i - k][j + k] == aiChar) sum++;
                        if ( sum == winGrid - 1 ) {
                            int[] arr2 = {i + 1, j + 1};
                            int[] arr3 = {i - 1, j + winGrid};
                            loop = checkAiLogic(arr2, arr3);
                        } if (!loop) break;
                    } if (!loop) break;
                }
            }

            //если не смог найти где закончить игу, проверяем не закончит ли игрок
            if (loop) {
                //горизонтальное положение
                for (int i = 0; i < field.length; i++) {
                    for (int j = 0; j < field[0].length - (winGrid - 1); j++) {
                        int sum = 0;
                        for (int k = 0; k < winGrid; k++) if (field[i][j + k] == playerChar) sum++;
                        if ( sum == winGrid - 1 && field[i][j] == playerChar) {
                            int[] arr2 = {i + 1, j};
                            int[] arr3 = {i + 1, j + winGrid};
                            loop = checkAiLogic(arr2, arr3);
                        } if (!loop) break;
                    } if (!loop) break;
                }
            }
            if (loop) {
                //вертикально
                for (int i = 0; i < field.length - (winGrid - 1); i++) {
                    for (int j = 0; j < field[0].length; j++) {
                        int sum = 0;
                        for (int k = 0; k < winGrid; k++) if (field[i + k][j] == playerChar) sum++;
                        if ( sum == winGrid - 1 ) {
                            int[] arr2 = {i + 1, j + 1};
                            int[] arr3 = {i + winGrid, j + 1};
                            loop = checkAiLogic(arr2, arr3);
                        } if (!loop) break;
                    } if (!loop) break;
                }
            }
            if (loop) {
                //диагональ лево-права
                for (int i = 0; i < field.length - (winGrid - 1); i++) {
                    for (int j = 0; j < field[0].length - (winGrid - 1); j++) {
                        int sum = 0;
                        for (int k = 0; k < winGrid; k++) if (field[i + k][j + k] == playerChar) sum++;
                        if ( sum == winGrid - 1 ) {
                            int[] arr2 = {i + 1, j + 1};
                            int[] arr3 = {i + winGrid, j + winGrid};
                            loop = checkAiLogic(arr2, arr3);
                        } if (!loop) break;
                    } if (!loop) break;
                }
            }
            if (loop) {
                //диагональ право-лево
                for (int i = winGrid - 1; i < field.length; i++) {
                    for (int j = 0; j < field[0].length - (winGrid - 1); j++) {
                        int sum = 0;
                        for (int k = 0; k < winGrid; k++) if (field[i - k][j + k] == playerChar) sum++;
                        if ( sum == winGrid - 1 ) {
                            int[] arr2 = {i + 1, j + 1};
                            int[] arr3 = {i - 1, j + winGrid};
                            loop = checkAiLogic(arr2, arr3);
                        } if (!loop) break;
                    } if (!loop) break;
                }
            }
            //на случай если ничего не найдено
            while(loop) {
                aiMoveArr[0] = rnd.nextInt(fieldSize[0]) + 1;
                aiMoveArr[1] = rnd.nextInt(fieldSize[1]) + 1;

                if (isInField(aiMoveArr) && isEmpty(aiMoveArr)) break;
            }
        //сложность N3. генерим куча рендома за ход
        } else {
            int[] arr = new int[2];
            for (int i = 0; i < (int)((fieldSize[0] * fieldSize[1]) / 4) ; i++) {
                arr[0] = rnd.nextInt(fieldSize[0]) + 1;
                arr[1] = rnd.nextInt(fieldSize[1]) + 1;

                if (isInField(arr) && isEmpty(arr)) {
                    field[arr[0] - 1][arr[1] - 1] = aiChar;
                    //т.к. мы должны отдать массив и отдаем во всех случаях aiMoveArr,
                    // то запомним там помледний удачный вариант
                    aiMoveArr = arr;
                };
            }
        }
        return aiMoveArr;
    }

    //проверяем находятся ли координа на поле
    private static boolean isInField(int[] coor) {
        return coor[0] > 0 && coor[0] <= fieldSize[0] && coor[1] > 0 && coor[1] <= fieldSize[1];
    }

    //проверяем не занята ли ячейка
    private static boolean isEmpty(int[] coor) {
        return field[coor[0] - 1][coor[1] - 1] == emptyChar;
    }

    //универсальная логика победы. Можно будет добавить еще игроков
    private static boolean isWin(String player) {
        char symb;
        char winSymb;
        int sum;

        if (player.equals("player")) {
            symb = playerChar;
            winSymb = 'Ж';
        }
        else {
            symb = aiChar;
            winSymb = 'Ø';
        }

        //горизонтальное условие
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length - (winGrid - 1); j++) {
                sum = 0;
                for (int k = 0; k < winGrid; k++) if (field[i][j + k] == symb) sum++;
                if (sum == winGrid) {
                    for (int k = 0; k < winGrid; k++) field[i][j + k] = winSymb;
                    return true;
                }
            }
        }

        //вертикальное условие
        for (int i = 0; i < field.length - (winGrid - 1); i++) {
            for (int j = 0; j < field[0].length; j++) {
                sum = 0;
                for (int k = 0; k < winGrid; k++) if (field[i + k][j] == symb) sum++;
                if (sum == winGrid) {
                    for (int k = 0; k < winGrid; k++) field[i + k][j] = winSymb;
                    return true;
                }
            }
        }

        //по диагонали cлева на право
        for (int i = 0; i < field.length - (winGrid - 1); i++) {
            for (int j = 0; j < field[0].length - (winGrid - 1); j++) {
                sum = 0;
                for (int k = 0; k < winGrid; k++) if (field[i + k][j + k] == symb) sum++;
                if (sum == winGrid) {
                    for (int k = 0; k < winGrid; k++) field[i + k][j + k] = winSymb;
                    return true;
                }
            }
        }

        //по диагонали справа на лево
        for (int i = winGrid - 1; i < field.length; i++) {
            for (int j = 0; j < field[0].length - (winGrid - 1); j++) {
                sum = 0;
                for (int k = 0; k < winGrid; k++) if (field[i - k][j + k] == symb) sum++;
                if (sum == winGrid) {
                    for (int k = 0; k < winGrid; k++) field[i - k][j + k] = winSymb;
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isDraw() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if (field[i][j] == emptyChar) return false;
            }
        }
        return true;
    }

    //для интереса добавил комментарии бота. Что бы позлить игрока и мотивировать выйграть
    private static void botComment() {
        String[] badComment = new String[10];
        badComment[0] = "Надо же, разобрался как играть?";
        badComment[1] = "А твоя мама знает чем ты занимаешься?";
        badComment[2] = "Ты уверен, что у тебя две руки?";
        badComment[3] = "Зачем стараться, видь я знаю все то, что ты скажешь зарание ..";
        badComment[4] = "Бил Гейтс не смог, думаешь у тебя получиться?";
        badComment[5] = "О б-же .. только не туда ..";
        badComment[6] = "Мне кажется или в твоих действиях сквозит сарказм?";
        badComment[7] = "А какой у тебя IQ? Это я так .. к слову ..";
        badComment[8] = "Моя бабушка в детсаде лучше ходы придувывала!";
        badComment[9] = "Может вздремнуть? [громко зевает]";

        System.out.println("Соперник говорит: " + "\u001B[35m" + badComment[rnd.nextInt(10)] + "\u001B[0m");
    }

    private static boolean checkAiLogic(int[] arr2, int[] arr3) {
        if (isInField(arr2) && isEmpty(arr2) ) {
            aiMoveArr = arr2;
            return false;
        } else if (isInField(arr3) && isEmpty(arr3) ) {
            aiMoveArr = arr3;
            return false;
        }
        return true;
    }
}
