package pro.kensait.gpt_doc_creator.chatgpt.client;

public class BizException extends RuntimeException {
    private final Integer code;
    private final String msg;

    public BizException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BizException(Error error) {
        super(error.getMsg());
        this.code = error.getCode();
        this.msg = error.getMsg();
    }
    
    public Integer getCode() {
        return code;
    }
}
