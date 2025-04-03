package com.duskio.common.search;

import org.hibernate.search.backend.elasticsearch.analysis.ElasticsearchAnalysisConfigurationContext;
import org.hibernate.search.backend.elasticsearch.analysis.ElasticsearchAnalysisConfigurer;

/**
 * Custom analyzer, registered by hibernate.search.backend.analysis.configurer in application.yml
 */
public class CustomElasticsearchAnalysisConfigurer implements ElasticsearchAnalysisConfigurer {

    @Override
    public void configure(ElasticsearchAnalysisConfigurationContext context) {
        context.analyzer(CustomAnalyzer.ENGLISH_ANALYZER)
               .custom()
               .tokenizer("standard")
               .charFilters("html_strip")
               .tokenFilters("lowercase", "stop", "kstem", "asciifolding");

        context.normalizer(CustomAnalyzer.ENGLISH_NORMALIZER)
               .custom()
               .tokenFilters("lowercase", "asciifolding");

        context.analyzer(CustomAnalyzer.AUTOCOMPLETE_ANALYZER)
               .custom()
               .tokenizer("whitespace")
               .charFilters("html_strip")
               .tokenFilters("lowercase", "asciifolding", "edge_ngram_weight");

        context.tokenFilter("edge_ngram_weight")
               .type("edge_ngram")
               .param("min_gram", 1)
               .param("max_gram", 10);

        context.analyzer(CustomAnalyzer.AUTOCOMPLETE_SEARCH)
               .custom()
               .tokenizer("whitespace")
               .charFilters("html_strip")
               .tokenFilters("lowercase", "asciifolding");
    }
}
