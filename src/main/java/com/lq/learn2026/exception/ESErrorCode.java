package com.lq.learn2026.exception;

/**
 * Elasticsearch 异常错误码枚举
 */
public enum ESErrorCode {
    INDEX_CREATE_ERROR(801, "索引创建异常"),
    INDEX_QUERY_ERROR(802, "索引查询异常"),
    INDEX_DELETE_ERROR(803, "索引删除异常"),
    DOCUMENT_CREATE_ERROR(804, "文档创建异常"),
    DOCUMENT_QUERY_ERROR(805, "文档查询异常"),
    DOCUMENT_UPDATE_ERROR(806, "文档更新异常"),
    DOCUMENT_DELETE_ERROR(807, "文档删除异常"),
    DOCUMENT_BATCH_CREATE_ERROR(808, "文档批量创建异常"),
    DOCUMENT_BATCH_DELETE_ERROR(809, "文档批量删除异常"),
    DOCUMENT_QUERY_ALL_ERROR(810, "文档全量查询异常"),
    DOCUMENT_PAGE_QUERY_ERROR(811, "文档分页查询异常"),
    DOCUMENT_SORT_QUERY_ERROR(812, "文档排序查询异常"),
    DOCUMENT_CONDITION_QUERY_ERROR(813, "文档条件查询异常"),
    DOCUMENT_COM_MUST_QUERY_ERROR(814, "文档复合必须查询异常"),
    DOCUMENT_COM_SHOULD_QUERY_ERROR(815, "文档复合应该查询异常"),
    DOCUMENT_RANGE_QUERY_ERROR(816, "文档范围查询异常"),
    DOCUMENT_HIGHLIGHT_QUERY_ERROR(817, "高亮查询异常"),
    DOCUMENT_FUZZYQUERY_QUERY_ERROR(818, "模糊查询异常");

    private final int code;
    private final String message;

    ESErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
