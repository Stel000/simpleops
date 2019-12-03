package com.stelo.simpleops.common.enums;

public enum BusinessError {
    /**
     * 业务错误编码
     */

    CLIENT_PARAM_ERR("客户端参数不符合要求", CustomHttpStatus.BAD_REQUEST, 40000001),
    DATA_VALIDATION_FAILED("Bean验证不通过", CustomHttpStatus.BAD_REQUEST, 40000002),
    DATA_DUPLICATE_CHECKED_ERR("实体类属性冲突", CustomHttpStatus.BAD_REQUEST, 40000003),
    RESOURCE_NOT_EXISTS("资源不存在", CustomHttpStatus.BAD_REQUEST, 40000004),
    RELATED_RESOURCE_EXISTS("仍有关联资源存在", CustomHttpStatus.BAD_REQUEST, 40000005),

    GENERAL_FORBID_ERR("通用无权限异常", CustomHttpStatus.FORBIDDEN, 40300001),

    GENERAL_SERVER_ERR("通用服务器异常", CustomHttpStatus.INTERNAL_SERVER_ERROR, 50000000),
    GENERAL_CREATE_ERR("通用创建失败", CustomHttpStatus.INTERNAL_SERVER_ERROR, 50000001),
    GENERAL_UPDATE_ERR("通用更新失败", CustomHttpStatus.INTERNAL_SERVER_ERROR, 50000002),
    GENERAL_DELETE_ERR("通用删除失败", CustomHttpStatus.INTERNAL_SERVER_ERROR, 50000003),
    GENERAL_QUERY_ERR("通用查询失败", CustomHttpStatus.INTERNAL_SERVER_ERROR, 500004);

    private String alias;

    private CustomHttpStatus httpStatus;

    private int code;

    BusinessError(String alias, CustomHttpStatus httpStatus, int code) {
        this.alias = alias;
        this.httpStatus = httpStatus;
        this.code = code;
    }

    public String getAlias() {
        return alias;
    }

    public CustomHttpStatus getHttpStatus() {
        return httpStatus;
    }

    public int getCode() {
        return code;
    }
}
