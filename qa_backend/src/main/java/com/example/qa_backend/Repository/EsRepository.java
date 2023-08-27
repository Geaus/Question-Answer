package com.example.qa_backend.Repository;

import com.example.qa_backend.Entity.Es;
import org.elasticsearch.search.SearchHit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface EsRepository extends ElasticsearchRepository<Es, Long> {

    void deleteById(Long id);
}
