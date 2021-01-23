package company.com;

import java.util.Scanner;

public class TextPatternSearch {

    //объявляем массива текста и паттерна
    public static char[] textArray;
    public static char[] patternArray;

    public static void main(String[] args) {
        final int prime = 11;

        getPattern();
        getText();

        if (patternArray.length > textArray.length) {
            System.out.println("Размер больше образца, чем размер исходного текста!");
            return;
        }

        calculateKarpRabina(textArray, patternArray, prime);
    }

    //получаем образец для поиска
    public static void getPattern() {
        //получаем образец текста для поиска
        Scanner input = new Scanner(System.in);
        System.out.print("Введите образец для поиска: ");
        String pattern = input.nextLine();
        patternArray = pattern.toCharArray();
    }
    //получаем исходный текст
    public static void getText() {
        //получаем текст
        Scanner input = new Scanner(System.in);
        System.out.print("Введите исходный текст: ");
        String text = input.nextLine();
        textArray = text.toCharArray();
    }

    //метод поиска вхождения подстроки Карпа-Рабина
    public static void calculateKarpRabina(char[] text, char[] pattern, int prime) {
        int ptnSize = pattern.length;
        int tSize = text.length;

        //задаем окно поиска, т.е. подстрока для сверки
        char[] currentSubText;
        currentSubText = getSubText(text, 0, ptnSize);

        //вычисляем хэш для образца
        long patternHash = calculateHash(pattern, prime);
        //вычисляем хэш для текущей подстроки сверки
        long currentHash = calculateHash(currentSubText, prime);

        for(int i = 0; i <= tSize - ptnSize; i++) {
            if ( i != 0 ) {
                currentSubText = getSubText(text, i, ptnSize);
                currentHash = recalculateHash(currentSubText, text[i-1], prime, currentHash);
            }
            //если произошло совпадение по хешам и символам выводим индекс первого символа вхождения в тексте
            if (patternHash == currentHash && stringMatch(currentSubText,pattern)){
                System.out.print(i + " ");
            }
        }
    }

    //метод для получения массива подстроки
    public static char[] getSubText(char[] text, int index, int number) {
        char[] subString = new char[number];
        int j = 0;
        for (int i = index; i < index + number; i++) {
            subString[j] = text[i];
            j++;
        }
        return subString;
    }

    //метод для расчета хеша по формуле
    //(1st letter) X (prime) + (2nd letter) X (prime)¹ + (3rd letter) X (prime)²
    public static long calculateHash(char[] str, int prime) {
        long hash = 0;
        int value = 0;
        for(int i = 0; i < str.length; i++ ) {
            value = (int) str[i];
            hash += value * Math.pow(prime,i);
        }
        return hash;
    }

    //пересчет хеша по формуле
    //result = (hash - (1st letter of previous substring)) / prime
    //newHash = result + (current 1st letter) * (prime) pow (substring_size - 1)
    public static long recalculateHash(char[] str, char letter, int prime, long hash) {
        int size = str.length;
        hash = hash - (int) letter;
        hash = hash / prime;
        int value = str[size - 1];
        hash = (long) (hash + value * Math.pow(prime, size - 1));
        return hash;
    }

    //побуквенная сверка совпавших хешей
    public static boolean stringMatch(char[] text, char[] pattern) {
        int size = pattern.length;
        for(int i = 0; i < size; i++) {
            if (text[i] != pattern[i])
                return false;
        }
        return true;
    }
}
