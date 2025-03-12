package com.duskio.features.indexer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RestClient;
import org.hibernate.search.backend.elasticsearch.ElasticsearchBackend;
import org.hibernate.search.engine.backend.Backend;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.mapping.SearchMapping;
import org.hibernate.search.mapper.orm.schema.management.SearchSchemaManager;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor @Slf4j
public class MassIndexerService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void reinitializeIndexes() throws InterruptedException, IOException {
//        deleteAllIndices();
        SearchSession searchSession = Search.session(entityManager);
        SearchSchemaManager schemaManager = searchSession.schemaManager();
        schemaManager.dropAndCreate();
        searchSession.massIndexer().startAndWait();
    }

    private void deleteAllIndices() throws IOException {
        SearchMapping searchMapping = Search.mapping(entityManager.getEntityManagerFactory());
        Backend backend = searchMapping.backend();
        ElasticsearchBackend elasticsearchBackend = backend.unwrap(ElasticsearchBackend.class);
        try (RestClient restClient = elasticsearchBackend.client(RestClient.class)) {
            restClient.performRequest(new Request("DELETE", "/_all"));
        }
    }
}
