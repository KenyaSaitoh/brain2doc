package pro.kensait.brain2doc.params;

public enum OutputType {
    SPEC("spec"),
    SUMMARY("summary"),
    REVIEW("review"),
    WEB_API_LIST("api"),
    CONST_LIST("const"),
    MESSAGE_LIST("message"),
    EXCEPTION_LIST("exception"),
    VALIDATION_LIST("validation"),
    ER_DIAGLAM("er"),
    OTHERS("others");

    private String name;
    private OutputType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static OutputType getOutputTypeByName(String name) {
        for (OutputType type : OutputType.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return null;
    }
}