import java.util.regex.Pattern;
import java.util.Scanner;

public class inputChecker{

    public static boolean isRange(String str){
        if(str.indexOf(":")!=-1){
            String[] star = str.split(":");
            if(isInt(star[0])||isDouble(star[0])){
                if(isInt(star[1])||isDouble(star[1])){
                    if(Double.parseDouble(star[0])<=Double.parseDouble(star[1])){
                        return true;
                    }else{
                        return false;
                    }
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public static boolean isDouble(String str){
        String regex = "\\d+[,|.]\\d+";
        if(Pattern.matches(regex,str)||isInt(str)){
            return true;
        }else{
            return false;
        }
    }

    public static boolean isInt(String str){
        String regex = "\\d+";
        if(Pattern.matches(regex,str)){
            return true;
        }else{
            return false;
        }

    }

    public static String readln(String type) {
        Scanner scan = new Scanner(System.in);
        String str = scan.nextLine();
        boolean trueInput=false;
        if(type.equals("int")){
            trueInput=isInt(str);
            if(!trueInput){
                System.out.println("Для целого числа допустимы только обычные(арабские) цифры");
            }
        }else if(type.equals("double")){
            trueInput=isDouble(str);
            if(!trueInput){
                System.out.println("Для дробного числа допустимы только цифры и разделитель в виде точки или запятой");
            }
        }else if(type.equals("range")){
            trueInput=isRange(str);
            if(!trueInput){
                System.out.println("Необходимо ввести диапазон в формате число:число без посторонних символов, например 1:10.24\nОбратите внимание что числа должны быть положительными");
            }
        }
        if(trueInput==false){
            System.out.println("Повторите ввод:");
            str=readln(type);
        }
        return str;
    }
}