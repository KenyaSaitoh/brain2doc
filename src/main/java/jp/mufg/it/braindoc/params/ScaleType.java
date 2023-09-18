package jp.mufg.it.braindoc.params;

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