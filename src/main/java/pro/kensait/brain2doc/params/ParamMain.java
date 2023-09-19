package pro.kensait.brain2doc.params;

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
        Parameter.setUp(params);
        Parameter param = Parameter.getParameter();
        System.out.println(param);
    }
}
