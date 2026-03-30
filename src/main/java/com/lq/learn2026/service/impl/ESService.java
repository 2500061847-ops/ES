package com.lq.learn2026.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Result;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import co.elastic.clients.elasticsearch.indices.IndexState;
import co.elastic.clients.json.JsonData;
import com.alibaba.fastjson2.JSON;
import com.lq.learn2026.bean.Student;
import com.lq.learn2026.exception.ESErrorCode;
import com.lq.learn2026.exception.ESException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ESService {
    @Autowired
    private ElasticsearchClient client;

    /**
     * 创建索引
     *
     * @param indexName 索引名称
     * @return 是否创建成功
     * @throws ESException 创建索引异常
     */
    public Boolean createIndex(String indexName) throws ESException {
        try {
            // 检查索引是否存在
            boolean exists = client.indices().exists(e -> e.index(indexName)).value();
            if (exists) {
                System.out.println("索引已存在: " + indexName);
                return false;
            }

            // 创建索引
            CreateIndexResponse createIndexResponse = client.indices().create(v -> v.index(indexName));
            System.out.println("索引创建响应: " + JSON.toJSONString(createIndexResponse));
            if (createIndexResponse.acknowledged()) {
                // 索引创建成功
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new ESException(ESErrorCode.INDEX_CREATE_ERROR);
        }
    }

    /**
     * 查询索引
     *
     * @param indexName 索引名称
     * @return 索引信息JSON字符串
     * @throws ESException 查询索引异常
     */
    public String queryIndex(String indexName) throws ESException {
        try {
            GetIndexResponse getIndexResponse = client.indices().get(v -> v.index(indexName));
            Map<String, IndexState> result = getIndexResponse.result();
            return JSON.toJSONString(result);
        } catch (Exception e) {
            throw new ESException(ESErrorCode.INDEX_QUERY_ERROR);
        }
    }

    /**
     * 删除索引
     *
     * @param indexName 索引名称
     * @return 是否删除成功
     * @throws ESException 删除索引异常
     */
    public Boolean deleteIndex(String indexName) throws ESException {
        try {
            DeleteIndexResponse deleteIndexResponse = client.indices().delete(v -> v.index(indexName));
            return deleteIndexResponse.acknowledged();
        } catch (Exception e) {
            throw new ESException(ESErrorCode.INDEX_DELETE_ERROR);
        }
    }

    /**
     * 创建文档
     *
     * @return 是否创建成功
     * @throws ESException 文档创建异常
     */
    public Boolean createDocument() throws ESException {
        try {
            Student student = new Student();
            student.setId(1L);
            student.setName("张三");
            student.setAge(18);
            student.setEmail("123456@163.com");
            // 创建文档时，必须指定索引和文档id
            CreateResponse createResponse = client.create(doc -> doc.index("student").id("1").document(student));
            Result result = createResponse.result();
            System.out.println("文档创建结果: " + JSON.toJSONString(result));
            return result == Result.Created;
        } catch (Exception e) {
            throw new ESException(ESErrorCode.DOCUMENT_CREATE_ERROR);
        }
    }

    /**
     * 查询文档
     *
     * @return 文档JSON字符串
     * @throws ESException 文档查询异常
     */
    public String queryDocument() throws ESException {
        try {
            // 查询文档，必须指定索引和文档id
            Student student = client.get(g -> g.index("student").id("1"), Student.class).source();
            return JSON.toJSONString(student);
        } catch (Exception e) {
            throw new ESException(ESErrorCode.DOCUMENT_QUERY_ERROR);
        }
    }

    /**
     * 更新文档
     *
     * @return 是否更新成功
     * @throws ESException 文档更新异常
     */
    public Boolean updateDocument() throws ESException {
        try {
            UpdateResponse<Student> updateResponse = client.update(
                    u -> u.index("student")
                            .id("1")
                            .doc(new Student(2L, "李四", 20, "123@qq.com")),
                    Student.class
            );
            Result result = updateResponse.result();
            System.out.println("文档更新结果: " + JSON.toJSONString(result));
            return result == Result.Updated;
        } catch (Exception e) {
            throw new ESException(ESErrorCode.DOCUMENT_UPDATE_ERROR);
        }
    }

    /**
     * 删除文档
     *
     * @return 是否删除成功
     * @throws ESException 文档删除异常
     */
    public Boolean deleteDocument() throws ESException {
        try {
            Result result = client.delete(d -> d.index("student").id("1")).result();
            System.out.println("文档删除结果: " + JSON.toJSONString(result));
            return result == Result.Deleted;
        } catch (Exception e) {
            throw new ESException(ESErrorCode.DOCUMENT_DELETE_ERROR);
        }
    }

    /**
     * 批量创建文档
     *
     * @return 批量操作结果JSON字符串
     * @throws ESException 文档批量创建异常
     */
    public String batchCreateDoc() throws ESException {
        try {
            List<BulkOperation> list = new ArrayList<>();
            list.add(new BulkOperation.Builder().create(doc -> doc.document(new Student(12L, "zhangsan", 18)).id("001").index("student")).build());
            list.add(new BulkOperation.Builder().create(doc -> doc.document(new Student(13L, "lisi", 26)).id("002").index("student")).build());
            list.add(new BulkOperation.Builder().create(doc -> doc.document(new Student(14L, "wangwu", 24)).id("003").index("student")).build());

            BulkResponse bulkResponse = client.bulk(req -> req.index("student").operations(list));
            List<BulkResponseItem> items = bulkResponse.items();
            List<String> collect = items.stream().map(BulkResponseItem::result).collect(Collectors.toList());
            return JSON.toJSONString(collect);
        } catch (Exception e) {
            throw new ESException(ESErrorCode.DOCUMENT_BATCH_CREATE_ERROR);
        }
    }

    /**
     * 批量删除文档
     *
     * @return 批量操作结果JSON字符串
     * @throws ESException 文档批量删除异常
     */
    public String batchDeleteDoc() throws ESException {
        try {
            List<BulkOperation> list = new ArrayList<>();
            list.add(new BulkOperation.Builder().delete(doc -> doc.id("001").index("student")).build());
            list.add(new BulkOperation.Builder().delete(doc -> doc.id("002").index("student")).build());
            list.add(new BulkOperation.Builder().delete(doc -> doc.id("003").index("student")).build());

            BulkResponse bulkResponse = client.bulk(req -> req.index("student").operations(list));
            List<BulkResponseItem> items = bulkResponse.items();
            List<String> collect = items.stream().map(BulkResponseItem::result).toList();
            return JSON.toJSONString(collect);
        } catch (Exception e) {
            throw new ESException(ESErrorCode.DOCUMENT_BATCH_DELETE_ERROR);
        }
    }

    /**
     * 全量查询文档
     *
     * @return 查询结果JSON字符串
     * @throws ESException 文档全量查询异常
     */
    public Object queryAllDocument() throws ESException {
        try {
            SearchResponse<Object> search = client.search(req -> req.index("student").query(v -> v.matchAll(m -> m)), Object.class);
            HitsMetadata<Object> hitsMetadata = search.hits();
            List<Hit<Object>> hits = hitsMetadata.hits();
            List<Object> collect = hits.stream().map(Hit::source).collect(Collectors.toList());
            hits.forEach(hit -> {
                System.out.println("文档查询结果: " + JSON.toJSONString(hit.source()));
            });
            return JSON.toJSONString(collect);
        } catch (Exception e) {
            throw new ESException(ESErrorCode.DOCUMENT_QUERY_ALL_ERROR);
        }
    }

    /**
     * 分页查询文档
     *
     * @return 查询结果JSON字符串
     * @throws ESException 文档分页查询异常
     */
    public Object pageQuery() throws ESException {
        try {
            SearchResponse<Object> search = client.search(req -> req.index("student").query(q -> q.matchAll(m -> m)).from(0).size(2), Object.class);
            HitsMetadata<Object> hitsMetadata = search.hits();
            List<Hit<Object>> hits = hitsMetadata.hits();
            List<Object> collect = hits.stream().map(Hit::source).collect(Collectors.toList());
            return JSON.toJSONString(collect);
        } catch (Exception e) {
            throw new ESException(ESErrorCode.DOCUMENT_PAGE_QUERY_ERROR);
        }
    }

    /**
     * 排序查询文档
     *
     * @return 查询结果JSON字符串
     * @throws ESException 文档排序查询异常
     */
    public Object sortQuery() throws ESException {
        try {
            SearchResponse<Object> search = client.search(req -> req.index("student").query(q -> q.matchAll(m -> m)).sort(s -> s.field(f -> f.field("id").order(SortOrder.Asc))), Object.class);
            HitsMetadata<Object> hitsMetadata = search.hits();
            List<Hit<Object>> hits = hitsMetadata.hits();
            List<Object> collect = hits.stream().map(Hit::source).collect(Collectors.toList());
            return JSON.toJSONString(collect);
        } catch (Exception e) {
            throw new ESException(ESErrorCode.DOCUMENT_SORT_QUERY_ERROR);
        }
    }

    /**
     * 条件查询文档（字段过滤）
     *
     * @return 查询结果JSON字符串
     * @throws ESException 文档条件查询异常
     */
    public Object conditionQuery() throws ESException {
        try {
            SearchResponse<Object> search = client.search(builder -> builder.index("student").query(q -> q.matchAll(m -> m)).source(s -> s.filter(f -> f.includes("id", "name")
                    .excludes(""))), Object.class);
            HitsMetadata<Object> hitsMetadata = search.hits();
            List<Hit<Object>> hits = hitsMetadata.hits();
            List<Object> collect = hits.stream().map(Hit::source).collect(Collectors.toList());
            return JSON.toJSONString(collect);
        } catch (Exception e) {
            throw new ESException(ESErrorCode.DOCUMENT_CONDITION_QUERY_ERROR);
        }
    }

    /**
     * 复合查询文档（必须条件）
     *
     * @return 查询结果JSON字符串
     * @throws ESException 文档复合必须查询异常
     */
    public Object comMustQuery() throws ESException {
        try {
            SearchResponse<Object> search = client.search(builder -> {
                SearchRequest.Builder student = builder.index("student");
                SearchRequest.Builder query = student.query(q -> q.bool(b -> b.must(m -> m.match(t -> t.field("name").query("zhangsan"))).must(m -> m.match(t -> t.field("age").query(18)))));
                return query;
            }, Object.class);

            HitsMetadata<Object> hitsMetadata = search.hits();
            List<Hit<Object>> hits = hitsMetadata.hits();
            List<Object> collect = hits.stream().map(Hit::source).collect(Collectors.toList());
            return JSON.toJSONString(collect);
        } catch (Exception e) {
            throw new ESException(ESErrorCode.DOCUMENT_COM_MUST_QUERY_ERROR);
        }
    }

    /**
     * 复合查询文档（应该条件）
     *
     * @return 查询结果JSON字符串
     * @throws ESException 文档复合应该查询异常
     */
    public Object comShouldQuery() throws ESException {
        try {
            SearchResponse<Object> search = client.search(builder -> {
                SearchRequest.Builder student = builder.index("student");
                SearchRequest.Builder query = student.query(q -> q.bool(b -> b.should(m -> m.match(t -> t.field("name").query("zhangsan"))).should(m -> m.match(t -> t.field("age").query(18)))));
                return query;
            }, Object.class);

            HitsMetadata<Object> hitsMetadata = search.hits();
            List<Hit<Object>> hits = hitsMetadata.hits();
            List<Object> collect = hits.stream().map(Hit::source).collect(Collectors.toList());
            return JSON.toJSONString(collect);
        } catch (Exception e) {
            throw new ESException(ESErrorCode.DOCUMENT_COM_SHOULD_QUERY_ERROR);
        }
    }

    /**
     * 范围查询文档
     *
     * @return 查询结果JSON字符串
     * @throws ESException 文档范围查询异常
     */
    public Object rangeQuery() throws ESException {
        try {
            SearchResponse<Object> search = client.search(builder -> {
                SearchRequest.Builder student = builder.index("student");
                // gte大于等于 lte小于等于 gt大于 lt小于
                SearchRequest.Builder query = student.query(q -> q.range(r -> r.field("age").gte(JsonData.of(18)).lte(JsonData.of(25))));
                return query;
            }, Object.class);

            HitsMetadata<Object> hitsMetadata = search.hits();
            List<Hit<Object>> hits = hitsMetadata.hits();
            List<Object> collect = hits.stream().map(Hit::source).collect(Collectors.toList());
            return JSON.toJSONString(collect);
        } catch (Exception e) {
            throw new ESException(ESErrorCode.DOCUMENT_RANGE_QUERY_ERROR);
        }
    }

    public Object highlight() throws ESException {
        try {
            SearchResponse<Object> search = client.search(builder -> {
                SearchRequest.Builder student = builder.index("student");
                SearchRequest.Builder query = student.query(q -> q.match(m -> m.field("name").query("zhangsan"))).highlight(h -> h.fields("name", f -> f.preTags("<em>").postTags("</em>")));
                return query;
            }, Object.class);

            HitsMetadata<Object> hitsMetadata = search.hits();
            List<Hit<Object>> hits = hitsMetadata.hits();

            for (Hit<Object> hit : search.hits().hits()) {
                Object student = hit.source();
                // 获取高亮字段
                Map<String, List<String>> highlightFields = hit.highlight();
                if (highlightFields != null && highlightFields.containsKey("name")) {
                    List<String> highlights = highlightFields.get("name");
                    // 高亮文本，可能有多个片段
                    String highlightedName = String.join("", highlights);
                    System.out.println("高亮 name: " + highlightedName);
                }
            }
            List<Object> collect = hits.stream().map(Hit::source).collect(Collectors.toList());
            return JSON.toJSONString(collect);
        } catch (Exception e) {
            throw new ESException(ESErrorCode.DOCUMENT_HIGHLIGHT_QUERY_ERROR);
        }
    }

    public Object fuzzyQuery() throws ESException {
        try {
            // .fuzzy(fz -> fz.field(...).value(...)) → 模糊查询构建器
            // fuzziness("AUTO") → 自动编辑距离，允许拼写错误：AUTO（编辑距离 1 或 2）
            // prefixLength(1) → 前缀不允许错
            // Student.class → 反序列化文档对象
            SearchResponse<Student> searchResponse = client.search(s -> s
                            .index("student")
                            .query(q -> q
                                    .fuzzy(fz -> fz
                                            .field("name")
                                            .value("zhanglin")
                                            .fuzziness("AUTO")
                                            .prefixLength(1)
                                    )
                            ),
                    Student.class
            );

            searchResponse.hits().hits().forEach(hit -> {
                Student student = hit.source();
                System.out.println("模糊查询结果: " + JSON.toJSONString(student));
            });
            List<Student> collect = searchResponse.hits().hits().stream().map(Hit::source).collect(Collectors.toList());
            return JSON.toJSONString(collect);
        } catch (Exception e) {
            throw new ESException(ESErrorCode.DOCUMENT_FUZZYQUERY_QUERY_ERROR);
        }
    }
}
