package pro.kensait.brain2doc.params;

public enum ScaleType {
    SMALL(50), MEDIUM(300), LARGE(null);

    private Integer charSize;
    private ScaleType(Integer charSize) {
        this.charSize = charSize;
    }

    public Integer getCharSize() {
        return charSize;
    }
}