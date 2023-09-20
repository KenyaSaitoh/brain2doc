package pro.kensait.brain2doc.params;

public enum ProcessType {
    SPEC("spec"),
    REFACTORING("refactoring"),
    WEB_API_LIST("api"),
    CONST_LIST("const"),
    MESSAGE_LIST("message"),
    EXCEPTION_LIST("exception"),
    VALIDATION_LIST("validation"),
    ER_DIAGLAM("er"),
    OTHERS("others");

    private String name;
    private ProcessType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ProcessType getProcessTypeByName(String name) {
        for (ProcessType type : ProcessType.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return null;
    }
}