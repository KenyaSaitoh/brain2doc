package pro.kensait.brain2doc.params;

public enum ResourceType {
    JAVA("java", ".java"),
    JAVASCRIPT("js", ".js", ".ts"),
    PYTHON("python", ".py"),
    SQL("sql", ".sql"),
    PAGE("page", ".html", ".htm", ".xhtml", ".jsp"),
    SHELLSCRIPT("shell", ".sh", ".bash", ".ksh", ".bash"),
    OTHERS("others", ".*");

    private String name;
    private String[] exts;
    private ResourceType(String name, String... exts) {
        this.name = name;
        this.exts = exts;
    }

    public String getName() {
        return name;
    }

    public static ResourceType getResourceTypeByName(String name) {
        for (ResourceType type : ResourceType.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return null;
    }

    public String[] getExts() {
        return exts;
    }

    public boolean matchesExt(String target) {
        for (String ext : exts) {
            if (ext.equals(".*")) return true;
            if (target.endsWith(ext)) return true;
        }
        return false;
    }

    public String getMatchExt(String target) {
        for (String ext : exts) {
            if (target.endsWith(ext)) return ext;
        }
        return null;
    }
}