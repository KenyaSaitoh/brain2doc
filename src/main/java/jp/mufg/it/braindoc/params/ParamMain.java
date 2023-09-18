package jp.mufg.it.braindoc.params;

public class ParamMain {
    public static void main(String[] args) {
        String parameter = "" +
                "--apikey " +
                "aaaaaaaa " +
                "--stopOnFailure " + 
                "--src " +
                "C:\\Users\\kenya\\AppData\\Local\\Programs\\Python\\Python311\\Lib " +
                "--scale " +
                "MEDIUM" + 
                "";
        String[] params = parameter.split(" ");
        for (String s : params) {
            System.out.println(s);
        }
        Parameter param = Parameter.of(params);
        System.out.println(param);
    }
}
