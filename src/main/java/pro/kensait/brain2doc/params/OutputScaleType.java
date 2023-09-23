package pro.kensait.brain2doc.params;

public enum OutputScaleType {
    SMALL("small", 50),
    MEDIUM("medium", 200),
    LARGE("large", 500),
    NO_LIMIT("no_limit", null);

    private String name;
    private Integer charSize;
    private OutputScaleType(String name, Integer charSize) {
        this.name = name;
        this.charSize = charSize;
    }

    public String getName() {
        return name;
    }

    public Integer getCharSize() {
        return charSize;
    }

    public OutputScaleType getScaleTypeByName(String name) {
        for (OutputScaleType type : OutputScaleType.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return null;
    }
}