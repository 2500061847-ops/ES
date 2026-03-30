package com.lq.learn2026.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ESConfig {

    @Bean
    public ElasticsearchClient elasticsearchClient() {
        // 创建底层RestClient
        RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200)).build();

        // 创建传输层
        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        // 创建高层客户端
        return new ElasticsearchClient(transport);
    }


}
