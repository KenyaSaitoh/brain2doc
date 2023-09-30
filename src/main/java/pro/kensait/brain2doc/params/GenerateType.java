package pro.kensait.brain2doc.params;

public enum GenerateType {
    SPEC("spec"),
    SUMMARY("summary"),
    REVIEW("review"),
    OTHERS("others");

    private String name;
    private GenerateType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static GenerateType getGenerateTypeByName(String name) {
        for (GenerateType type : GenerateType.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return null;
    }
}