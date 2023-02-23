package org.example;

import java.text.Normalizer;

public class NumeralConverter {
    private final String TAG = "NumeralConverter";
    private final String textNumeral;
    private Integer numberResult = 0;
    private Integer startCharPos = 0;

    public NumeralConverter(String textNumeral) {
        this.textNumeral = stripAccents(textNumeral.toLowerCase());
    }

    public Integer convert() throws NullPointerException {
        int attempts = 0;
        while (startCharPos < textNumeral.length() && attempts <= 3) {
            findInUnit();
            findInLargeNum();
            findInUniquesTens();
            attempts ++;
        }
        return (numberResult == 0 && !textNumeral.equals("cero")) ? null : numberResult;
    }

    private void findInUnit() {
        String remainingWords = (startCharPos < textNumeral.length()) ? textNumeral.substring(startCharPos) : "";
        switch (remainingWords) {
            case "cero":
                startCharPos += "cero".length();
                break;
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
                } else if (remainingWords.startsWith("diez y ")) {
                    startCharPos += "diez y ".length();
                    numberResult += 10;
                } else if (remainingWords.startsWith("dieci ")) {
                    startCharPos += "dieci ".length();
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
                } else if (remainingWords.startsWith("ciento y ")) {
                    startCharPos += "ciento y ".length();
                    numberResult += 100;
                } else if (remainingWords.startsWith("ciento ")) {
                    startCharPos += "ciento ".length();
                    numberResult += 100;
                } else if (remainingWords.startsWith("ciento")) {
                    startCharPos += "ciento".length();
                    numberResult += 100;
                }
                break;

            case "vei": // 20
                if (remainingWords.startsWith("veinte y")
                        || remainingWords.startsWith("veinti y")) {
                    startCharPos += "veint- y ".length();
                    numberResult += 20;
                } else if (remainingWords.startsWith("veinte ")
                        || remainingWords.startsWith("veinti ")) {
                    startCharPos += "veint- ".length();
                    numberResult += 20;
                } else if (remainingWords.startsWith("veinte")
                        || remainingWords.startsWith("veinti")) {
                    startCharPos += "veint-".length();
                    numberResult += 20;
                }
                break;

            case "dos": // 200
                if (remainingWords.startsWith("doscientos y ")) {
                    startCharPos += "doscientos y ".length();
                    numberResult += 200;
                } else if (remainingWords.startsWith("doscientos ")) {
                    startCharPos += "doscientos ".length();
                    numberResult += 200;
                } else if (remainingWords.startsWith("doscientos")) {
                    startCharPos += "doscientos".length();
                    numberResult += 200;
                }
                break;

            case "tre":
                // 300...
                if (remainingWords.startsWith("trescientos")) {
                    numberResult += 300;
                    countHundredsTermination(remainingWords);
                    break;
                }
                // 30
                if (remainingWords.startsWith("treinta y")) {
                    startCharPos += "treinta y ".length();
                    numberResult += 30;
                } else if (remainingWords.startsWith("treinti ")
                        || remainingWords.startsWith("treinta ")) {
                    startCharPos += "treint- ".length();
                    numberResult += 30;
                } else if (remainingWords.startsWith("treinti")
                        || remainingWords.startsWith("treinta")) {
                    startCharPos += "treint-".length();
                    numberResult += 30;
                }
                break;

            case "cua":
                // 400
                if (remainingWords.startsWith("cuatrocientos y ")) {
                    startCharPos += "cuatrocientos y ".length();
                    numberResult += 400;
                } else if (remainingWords.startsWith("cuatrocientos ")) {
                    startCharPos += "cuatrocientos ".length();
                    numberResult += 400;
                } else if (remainingWords.startsWith("cuatrocientos")) {
                    startCharPos += "cuatrocientos".length();
                    numberResult += 400;
                }
                // 40...
                if (remainingWords.startsWith("cuarenta y")) {
                    startCharPos += "cuarenta y ".length();
                    numberResult += 40;
                } else if (remainingWords.startsWith("cuarenta ")) {
                    startCharPos += "cuarenta ".length();
                    numberResult += 40;
                } else if (remainingWords.startsWith("cuarenta")
                         ||remainingWords.startsWith("cuarenti")) {
                    startCharPos += "cuarent-".length();
                    numberResult += 40;
                }
                break;
            case "cin":
                // 50
                if (remainingWords.startsWith("cincuenta y ")) {
                    startCharPos += "cincuenta y ".length();
                    numberResult += 50;
                } else if (remainingWords.startsWith("cincuenta ")) {
                    startCharPos += "cincuenta ".length();
                    numberResult += 50;
                } else if (remainingWords.startsWith("cincuenta")) {
                    startCharPos += "cincuenta".length();
                    numberResult += 50;
                }
                break;

            case "qui": // 500
                if (remainingWords.startsWith("quinientos y ")) {
                    startCharPos += "quinientos y ".length();
                    numberResult += 500;
                } else if (remainingWords.startsWith("quinientos ")) {
                    startCharPos += "quinientos ".length();
                    numberResult += 500;
                } else if (remainingWords.startsWith("quinientos")) {
                    startCharPos += "quinientos".length();
                    numberResult += 500;
                }
                break;

            case "sei": // 600
                if (remainingWords.startsWith("seiscientos")) {
                    numberResult += 600;
                    countHundredsTermination(remainingWords);
                }
                break;

            case "ses": // 60
                if (remainingWords.startsWith("sesenta")) {
                    numberResult += 60;
                    countTensTermination(remainingWords);
                }
                break;

            case "set": // 70 - 700
                if (remainingWords.startsWith("setenta")) {
                    numberResult += 70;
                    countTensTermination(remainingWords);
                } else if (remainingWords.startsWith("setecientos")) {
                    numberResult += 700;
                    countHundredsTermination(remainingWords);
                }
                break;

            case "och": // 80 - 800
                if (remainingWords.startsWith("ochenta")) {
                    numberResult += 80;
                    countTensTermination(remainingWords);
                } else if (remainingWords.startsWith("ochocientos")) {
                    numberResult += 800;
                    countHundredsTermination(remainingWords);
                }
                break;

            case "nov": // 90 - 900
                if (remainingWords.startsWith("noventa")) {
                    numberResult += 90;
                    countTensTermination(remainingWords);
                } else if (remainingWords.startsWith("novecientos")) {
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
        if (tempRemainingWords.startsWith("enta y")) {
            startCharPos += "---enta y ".length();
        } else if (tempRemainingWords.startsWith("enta ")) {
            startCharPos += "---enta ".length();
        } else if (tempRemainingWords.startsWith("enta")) {
            startCharPos += "---enta".length();
        }
    }

    // Only for 300, 600, 700, 800, 900
    private void countHundredsTermination(String tempRemainingWords) {
        if (tempRemainingWords.length() < 4) {
            return;
        }
        tempRemainingWords = tempRemainingWords.substring(4);
        if (tempRemainingWords.startsWith("cientos y")) {
            startCharPos += "----cientos y ".length();
        } else if (tempRemainingWords.startsWith("cientos ")) {
            startCharPos += "----cientos ".length();
        } else if (tempRemainingWords.startsWith("cientos")) {
            startCharPos += "----cientos".length();
        }
    }

    private String stripAccents(String string) {
        string = Normalizer.normalize(string, Normalizer.Form.NFD);
        string = string.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return string;
    }
}
