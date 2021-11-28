import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
//支持负数运算，小数运算，分数运算
//异常处理类型较少（由于本人知识匮乏 qwq）
public class Calculator {
    public static void main(String[] args) {
        System.out.println(" 计算器");
        System.out.println("请输入数字 符号 数字的形式 eg：3 + 3");
        System.out.println("输入的字符与数字之间一定留下一个空格，方可正常运行！");
        System.out.println("如输入负数，请一定加括号！");
        Scanner into=new Scanner(System.in);
        try {
            String formula=into.nextLine();
            String[] inArray=formula.split(" ");
            String firstNext,secondNext;
            boolean status = inArray[0].contains("(");   //判断英文括号
            boolean status2 = inArray[0].contains("（"); //判断中文括号
            if(status||status2){
                System.out.println("你输入的第一个数字含有括号，将进行删除...");
                String first=removeCharAt(inArray[0], 0);
                System.out.println(removeCharAt(first, first.length()-1));
                firstNext=removeCharAt(first, first.length()-1);
                System.out.println("删除完毕");
            }
            else{
                firstNext=inArray[0];
            }
            firstNext=deleteSign(firstNext);
            boolean status3 = inArray[2].contains("(");
            boolean status4 = inArray[2].contains("（");
            if(status3||status4){
                System.out.println("你输入的第二个数字含有括号，将进行删除...");
                String second=removeCharAt(inArray[2], 0);
                System.out.println(removeCharAt(second, second.length()-1));
                secondNext=removeCharAt(second, second.length()-1);
                System.out.println("删除完毕");
            } else secondNext=inArray[2];
            secondNext=deleteSign(secondNext);
            System.out.println(firstNext+inArray[1]+secondNext);
            double firstNum ;
            firstNum=Double.parseDouble(firstNext);
            char sign;
            sign=inArray[1].charAt(0);
            double secondNum;
            secondNum=Double.parseDouble(secondNext);
            if (sign==47&&secondNum==0) {
                System.out.println("由于你的算式中含有除以0的异常，无法继续计算");
                System.out.println("****感谢您的使用****");
                System.exit(0);
            }
            else System.out.println("算式输入成功，计算开始...");
            Calculate process= new Calculate();
            process.calculate(firstNum,sign,secondNum);
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("数组越界异常"+e);
        }catch (NumberFormatException e){
            System.out.println("字符串转换成数字异常"+e);
        }
        catch(Exception e){
            System.out.println("其他异常"+e);
        }
        System.out.println("****感谢您的使用****");
    }
    public static String removeCharAt(String s, int pos) {
        return s.substring(0, pos) + s.substring(pos + 1);
    }
    public static String deleteSign(String needDelete){
        String eventualNum;
        boolean status = needDelete.contains("/");
        if(status){
            System.out.println("你输入的数字为分数，将进行小数转换...");
            String[] inArray2=needDelete.split("/");
            double first,second,result;
            first=Double.parseDouble(inArray2[0]);
            second=Double.parseDouble(inArray2[1]);
            if (second==0) {
                System.out.println("由于你的算式中含有除以0的异常，无法继续计算");
                System.exit(0);
            }
            result=first/second;
            eventualNum=Double.toString(result);
            System.out.println("转换完毕");
        }
        else{
            eventualNum=needDelete;
        }
        return eventualNum;
    }

    static class Calculate {
        Scanner into = new Scanner(System.in);

        public void calculate(double firstNum, char sign, double secondNum) {
            double result;
            int i = 0;
            do {
                switch (sign) {
                    case 43 -> result = firstNum + secondNum;//加
                    case 45 -> result = firstNum - secondNum;//减
                    case 42 -> result = firstNum * secondNum;//乘
                    case 47 -> result = firstNum / secondNum;//除
                    default -> {
                        System.out.print("抱歉，无法计算！输入的计算符号不正确！" + "\n" + "再来一次,请只输入运算符号：");
                        i = 1;
                        result = 0;
                        sign = into.next().charAt(0);
                    }
                }
            } while (i == 1);
            String resultBd = format1(result);
            System.out.println(firstNum + " " + sign + " " + secondNum + "结果为" + resultBd);
        }
        //保留小数的 BigDecimal
        public static String format1(double value) {
            BigDecimal bd = new BigDecimal(value);
            bd = bd.setScale(3, RoundingMode.HALF_UP);
            return bd.toString();
        }
    }
}