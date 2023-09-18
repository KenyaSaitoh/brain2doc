package jp.mufg.it.braindoc.params;

public enum ResourceType {
    JAVA(new String[] {".java"}),
    JAVASCRIPT(new String[] {".js"}),
    PYTHON(new String[] {".py"}),
    SQL(new String[] {".sql"}),
    PAGE(new String[] {".html", ".htm", ".xhtml", ".jsp"}),
    SHELLSCRIPT(new String[] {".sh", ".bash", ".ksh", ".bash"}),
    OTHERS(null);

    private String[] exts;
    private ResourceType(String[] exts) {
        this.exts = exts;
    }

    public String[] getExts() {
        return exts;
    }

    public boolean matchesExt(String target) {
        for (String ext : exts) {
            if (ext.equals(target)) return true;
        }
        return false;
    }
}