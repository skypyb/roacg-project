package com.roacg.core.utils.sejwt.jwt.token;

import com.roacg.core.utils.sejwt.cryptography.Codec;
import com.roacg.core.utils.sejwt.jwt.en.JwtAttribute;
import com.roacg.core.utils.JsonUtil;

import java.util.*;

/**
 * Token 基类
 * Create by skypyb on 2019-12-13
 */
public abstract class AbstractToken implements Token {

    protected static final Codec BASE64URL_CODEC = Codec.Type.Base64URL.create();

    private String header;
    private String payload;
    private String signature;

    private String headerJson;
    private String payloadJson;

    private String secret;
    private Codec codec;

    private Map<String, String> attrMap;

    public AbstractToken(String header, String payload, String signature) {
        this.header = header;
        this.payload = payload;
        this.signature = signature;
    }

    protected final String getHeader() {
        return header;
    }

    protected final String getPayload() {
        return payload;
    }

    protected final String getSignature() {
        return signature;
    }

    protected final String getSecret() {
        return secret;
    }

    protected final Codec getCodec() {
        return codec;
    }

    protected final boolean decodeHeader() {
        if (Objects.nonNull(this.headerJson)) return true;

        try {
            this.headerJson = BASE64URL_CODEC.decrypt(this.header, null);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected final boolean decodePayload() {
        if (Objects.nonNull(this.payloadJson)) return true;

        try {
            this.payloadJson = BASE64URL_CODEC.decrypt(this.payload, null);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private final void initAttributeMap() {
        if (Objects.nonNull(this.attrMap)) return;

        if (decodeHeader() && decodePayload()) {
            this.attrMap = new LinkedHashMap<>();
            JsonUtil.fromJsonToObject(this.headerJson, attrMap.getClass()).ifPresent(this.attrMap::putAll);
            JsonUtil.fromJsonToObject(this.payloadJson, attrMap.getClass()).ifPresent(this.attrMap::putAll);
        } else {
            this.attrMap = Collections.emptyMap();
        }
    }

    @Override
    public final String getHeaderJson() {
        return decodeHeader() ? this.headerJson : null;
    }

    @Override
    public final String getPayloadJson() {
        return decodePayload() ? this.payloadJson : null;
    }

    @Override
    public final Optional<String> getAttributeValue(JwtAttribute jwtAttribute) {
        if (Objects.isNull(jwtAttribute)) return Optional.empty();

        if (jwtAttribute.getClass().isEnum()) {
            return getAttributeValue(((Enum) jwtAttribute).name().toLowerCase());
        }

        return Optional.empty();
    }

    @Override
    public final Optional<String> getAttributeValue(String attribute) {
        if (Objects.isNull(attribute)) return Optional.empty();
        initAttributeMap();
        final Object result = attrMap.get(attribute);
        if (Objects.isNull(result)) return Optional.empty();
        return Optional.ofNullable(String.valueOf(result));
    }

    @Override
    public Token signatureSecret(String secret) {
        this.secret = secret;
        return this;
    }

    @Override
    public Token useSpecifiedCodec(Codec codec) {
        this.codec = codec;
        return this;
    }
}
