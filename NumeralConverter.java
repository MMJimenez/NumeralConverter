package org.example;

import java.text.Normalizer;

public class NumeralConverter {
    private final String TAG = "NumeralConverter";
    private final String textNumeral;
    private Integer numberResult = 0;
    private Integer startCharPos = 0;

    public NumeralConverter(String textNumeral) {
        textNumeral = textNumeral.replace("y", "");
        textNumeral = textNumeral.replace(" ", "");
        this.textNumeral = stripAccents(textNumeral.toLowerCase());
    }

    public Integer convert() throws NullPointerException {
        int attempts = 0;

        if (isNumber(textNumeral)) {
            try {
                return Integer.valueOf(textNumeral);
            } catch (NumberFormatException ex) {

            }
        }

        if (textNumeral.equals("cero")) return 0;

        while (startCharPos < textNumeral.length() && attempts <= 3) {
            findInUnit();
            findInLargeNum();
            findInUniquesTens();
            if (numberResult == 0) {
                numberResult = null;
                break; // Not obtained any number in fist interation
            }
            attempts++;
        }
        return numberResult;
    }

    private void findInUnit() {
        String remainingWords = (startCharPos < textNumeral.length()) ? textNumeral.substring(startCharPos) : "";
        switch (remainingWords) {
            case "un":
                numberResult += 1;
                startCharPos += "un".length();
                break;
            case "una":
                numberResult += 1;
                startCharPos += "una".length();
                break;
            case "uno":
                numberResult += 1;
                startCharPos += "uno".length();
                break;
            case "dos":
                numberResult += 2;
                startCharPos += "dos".length();
                break;
            case "tres":
                numberResult += 3;
                startCharPos += "tres".length();
                break;
            case "cuatro":
                numberResult += 4;
                startCharPos += "cuatro".length();
                break;
            case "cinco":
                numberResult += 5;
                startCharPos += "cinco".length();
                break;
            case "seis":
                numberResult += 6;
                startCharPos += "seis".length();
                break;
            case "siete":
                numberResult += 7;
                startCharPos += "siete".length();
                break;
            case "ocho":
                numberResult += 8;
                startCharPos += "ocho".length();
                break;
            case "nueve":
                numberResult += 9;
                startCharPos += "nueve".length();
                break;
        }
    }

    private void findInUniquesTens() {
        String word = (startCharPos < textNumeral.length()) ? textNumeral.substring(startCharPos) : "";
        switch (word) {
            case "diez":
                numberResult += 10;
                startCharPos += "diez".length();
                break;
            case "once":
                numberResult += 11;
                startCharPos += "once".length();
                break;
            case "doce":
                numberResult += 12;
                startCharPos += "doce".length();
                break;
            case "trece":
                numberResult += 13;
                startCharPos += "trece".length();
                break;
            case "catorce":
                numberResult += 14;
                startCharPos += "catorce".length();
                break;
            case "quince":
                numberResult += 15;
                startCharPos += "quince".length();
                break;
            default:
                break;
        }
    }

    private void findInLargeNum() {
        if (startCharPos >= textNumeral.length() && textNumeral.length() > 3) return;
        String remainingWords = textNumeral.substring(startCharPos);
        if (remainingWords.length() < 3) return;
        switch (remainingWords.substring(0, 3)) {
            case "die": // 10
                if (remainingWords.equals("diez")) {
                    startCharPos += "diez".length();
                    numberResult += 10;
                } else if (remainingWords.startsWith("dieci")) {
                    startCharPos += "dieci".length();
                    numberResult += 10;
                }
                break;

            case "cie": // 100
                if (remainingWords.equals("cien")) {
                    startCharPos += "cien".length();
                    numberResult += 100;
                } else if (remainingWords.startsWith("ciento")) {
                    startCharPos += "ciento".length();
                    numberResult += 100;
                }
                break;

            case "vei": // 20
                if (remainingWords.startsWith("veinte") || remainingWords.startsWith("veinti")) {
                    startCharPos += "veint-".length();
                    numberResult += 20;
                }
                break;

            case "dos": // 200
                if (remainingWords.startsWith("doscientos") || remainingWords.startsWith("doscientas")) {
                    startCharPos += "doscientos".length();
                    numberResult += 200;
                }
                break;

            case "tre":
                // 300...
                if (remainingWords.startsWith("trescientos") || remainingWords.startsWith("trescientas")) {
                    numberResult += 300;
                    countHundredsTermination(remainingWords);
                    break;
                }
                // 30
                if (remainingWords.startsWith("treinti") || remainingWords.startsWith("treinta")) {
                    startCharPos += "treint-".length();
                    numberResult += 30;
                }
                break;

            case "cua":
                // 400
                 if (remainingWords.startsWith("cuatrocientos") || remainingWords.startsWith("cuatrocientas")) {
                    startCharPos += "cuatrocientos".length();
                    numberResult += 400;
                }
                // 40...
                if (remainingWords.startsWith("cuarenta") || remainingWords.startsWith("cuarenti")) {
                    startCharPos += "cuarent-".length();
                    numberResult += 40;
                }
                break;
            case "cin": // 50
                if (remainingWords.startsWith("cincuenta") || remainingWords.startsWith("cincuenti")) {
                    startCharPos += "cincuenta".length();
                    numberResult += 50;
                }
                break;

            case "qui": // 500
                if (remainingWords.startsWith("quinientos") || remainingWords.startsWith("quinientas")) {
                    startCharPos += "quinientos".length();
                    numberResult += 500;
                }
                break;

            case "sei": // 600
                if (remainingWords.startsWith("seiscientos") || remainingWords.startsWith("seiscientas")) {
                    numberResult += 600;
                    countHundredsTermination(remainingWords);
                }
                break;

            case "ses": // 60
                if (remainingWords.startsWith("sesenta") || remainingWords.startsWith("sesenti")) {
                    numberResult += 60;
                    countTensTermination(remainingWords);
                }
                break;

            case "set": // 70 - 700
                if (remainingWords.startsWith("setenta") || remainingWords.startsWith("setenti")) {
                    numberResult += 70;
                    countTensTermination(remainingWords);
                } else if (remainingWords.startsWith("setecientos") || remainingWords.startsWith("setecientas")) {
                    numberResult += 700;
                    countHundredsTermination(remainingWords);
                }
                break;

            case "och": // 80 - 800
                if (remainingWords.startsWith("ochenta") || remainingWords.startsWith("ochenti")) {
                    numberResult += 80;
                    countTensTermination(remainingWords);
                } else if (remainingWords.startsWith("ochocientos") || remainingWords.startsWith("ochocientas")) {
                    numberResult += 800;
                    countHundredsTermination(remainingWords);
                }
                break;

            case "nov": // 90 - 900
                if (remainingWords.startsWith("noventa") || remainingWords.startsWith("noventi")) {
                    numberResult += 90;
                    countTensTermination(remainingWords);
                } else if (remainingWords.startsWith("novecientos") || remainingWords.startsWith("novecientas")) {
                    numberResult += 900;
                    countHundredsTermination(remainingWords);
                }
                break;
        }
    }

    // This method only affects the numbers between 41 and 99, exclude multiple of ten
    private void countTensTermination(String tempRemainingWords) {
        if (tempRemainingWords.length() < 3) {
            return;
        }
        tempRemainingWords = tempRemainingWords.substring(3);
        if (tempRemainingWords.startsWith("enta") || tempRemainingWords.startsWith("enti")) {
            startCharPos += "---enta".length();
        }
    }

    // Only for 300, 600, 700, 800, 900
    private void countHundredsTermination(String tempRemainingWords) {
        if (tempRemainingWords.length() < 4) {
            return;
        }
        tempRemainingWords = tempRemainingWords.substring(4);
        if (tempRemainingWords.startsWith("cientos") || tempRemainingWords.startsWith("cientas")) {
            startCharPos += "----cientos".length();
        }
    }

    private String stripAccents(String string) {
        string = Normalizer.normalize(string, Normalizer.Form.NFD);
        string = string.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return string;
    }

    public static boolean isNumber(String num) {
        return num == null ? false : num.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?+(\\,[0-9]+)?)+");
    }
}