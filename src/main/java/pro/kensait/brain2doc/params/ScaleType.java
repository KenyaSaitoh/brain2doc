package pro.kensait.brain2doc.params;

public enum ScaleType {
    SMALL("small", 50),
    MEDIUM("medium", 300),
    LARGE("large", null);

    private String name;
    private Integer charSize;
    private ScaleType(String name, Integer charSize) {
        this.name = name;
        this.charSize = charSize;
    }

    public String getName() {
        return name;
    }

    public Integer getCharSize() {
        return charSize;
    }

    public ScaleType getScaleTypeByName(String name) {
        for (ScaleType type : ScaleType.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return null;
    }
}