package cn.jaxxu.wechat.sdk.enums;

public enum UploadMediaTypeEnum {
    PIC("pic"),
    VIDEO("video"),
    DOC("doc"),
    ;
    public String value;


    UploadMediaTypeEnum(String value) {
        this.value = value;
    }
}
