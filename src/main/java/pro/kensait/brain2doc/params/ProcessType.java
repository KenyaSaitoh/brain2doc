package pro.kensait.brain2doc.params;

public enum ProcessType {
    SPEC("spec"),
    REFACTORING("refactoring"),
    API("api"),
    CONST("const"),
    VALIDATION("validation"),
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