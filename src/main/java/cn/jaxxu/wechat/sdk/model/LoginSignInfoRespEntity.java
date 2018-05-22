package cn.jaxxu.wechat.sdk.model;

import lombok.Data;

/**
 * <error>
 * <ret>0</ret>
 * <message></message>
 * <skey>@crypt_e17b1c75_a97009863b20b42a65cdd09913c0f2eb</skey>
 * <wxsid>Ji+f8dFUJeTJNVaP</wxsid>
 * <wxuin>279165593</wxuin>
 * <pass_ticket>e4eUJdFCfdwQdQtWMdaleNT8ST9rzSodP0PafiSt87QOzV3ZbhQZdRTuZb%2Bo5FFV</pass_ticket>
 * <isgrayscale>1</isgrayscale>
 * </error>
 * 微信返回凭证信息实体
 */
@Data
public class LoginSignInfoRespEntity {

    /**
     * skey
     */
    private String skey;

    /**
     * sid
     */
    private String sid;

    /**
     * uin
     */
    private String uin;

    /**
     * passTicket
     */
    private String passTicket;
}
