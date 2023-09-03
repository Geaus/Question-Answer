package com.example.qa_backend.Controller;

import com.example.qa_backend.Entity.Question;
import com.example.qa_backend.Entity.Tag;
import com.example.qa_backend.JSON.LoginResult;
import com.example.qa_backend.JSON.QuestionJSON;
import com.example.qa_backend.Service.QuestionService;
import com.example.qa_backend.Service.SensitiveWordService;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.xcontent.XContentBuilder;
import org.elasticsearch.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@CrossOrigin
@RestController
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    SensitiveWordService sensitiveWordService;

    @RequestMapping("/getQuestions")
    @PreAuthorize("@authCheck.authorityCheck(0)")
    public List<QuestionJSON> listQuestions(@RequestParam int page_id, @RequestParam int uid) { return questionService.listQuestions(page_id, uid); }
    @RequestMapping("/findQuestion")
    @PreAuthorize("@authCheck.authorityCheck(0)")
    public QuestionJSON findQuestion(@RequestParam int uid, @RequestParam int qid) { return questionService.findQuestion(uid, qid); }
    @RequestMapping("/askQuestion")
    @PreAuthorize("@authCheck.authorityCheck(0)")
    public Question askQuestion(@RequestParam int uid, @RequestParam String content, @RequestParam String title,
                                @RequestBody List<Tag> tags)  throws IOException {
        if(!sensitiveWordService.isTextValid(content) || !sensitiveWordService.isTextValid(title)) {
            Question question = new Question();
            question.setId(-1);
            question.setContent("提问内容不合法");
            return question;
        }
        return questionService.askQuestion(uid, content, title, tags);
    }
    @RequestMapping("/getAsked")
    @PreAuthorize("@authCheck.authorityCheck(0)")
    public List<Question> listAsked(@RequestParam int page_id, @RequestParam int userId) { return questionService.listAsked(page_id, userId); }
    @RequestMapping("/getLikedQuestion")
    @PreAuthorize("@authCheck.authorityCheck(0)")
    public List<Question> getLiked(@RequestParam int page_id, @RequestParam int userId) { return questionService.getLiked(page_id, userId); }
    @RequestMapping("/getDislikedQuestion")
    @PreAuthorize("@authCheck.authorityCheck(0)")
    public List<Question> getDisliked(@RequestParam int page_id, @RequestParam int userId) { return questionService.getDisliked(page_id, userId); }
    @RequestMapping("/getMarkedQuestion")
    @PreAuthorize("@authCheck.authorityCheck(0)")
    public List<Question> getMarked(@RequestParam int page_id, @RequestParam int userId) { return questionService.getMarked(page_id, userId); }
    @RequestMapping("/deleteQuestion")
    @PreAuthorize("@authCheck.authorityCheck(1)")
    public LoginResult deleteQuestion(@RequestParam int qid) throws IOException {
        questionService.deleteQuestion(qid);
        LoginResult result = new LoginResult();
        result.setCode(200);
        result.setToken("删除成功");
        return result;
    }
    @RequestMapping("/searchByTag")
    @PreAuthorize("@authCheck.authorityCheck(0)")
    public List<QuestionJSON> searchByTag(@RequestParam int tag, @RequestParam int uid, @RequestParam int page_id){return questionService.searchByTag(tag, uid, page_id);}
    @RequestMapping("/fullTextSearch")
    @PreAuthorize("@authCheck.authorityCheck(0)")
    public List<QuestionJSON> fullTextSearch(@RequestParam String keyword, @RequestParam int uid, @RequestParam int page_id) throws IOException {return questionService.fullTextSearch(keyword, uid, page_id);}
    @RequestMapping("/esTest")
    public void esTest(@RequestParam int uid, @RequestParam String content, @RequestParam String title) throws IOException {questionService.esTest(uid, content, title);}

    @RequestMapping("/esSearch")
    public List<QuestionJSON> esTest1(@RequestParam String keyword, @RequestParam int limit, @RequestParam int uid, @RequestParam int page_id) throws IOException, ExecutionException, InterruptedException {return questionService.EsSearch(keyword, limit, uid, page_id);}

    @RequestMapping("/faqWrite")
    public void faqWrite(@RequestParam String question, @RequestParam String answer) throws IOException {questionService.faqWrite(question, answer);}

    @RequestMapping("/faqSearch")
    public void faqSearch(@RequestParam String keyword) throws IOException {questionService.faqSearch(keyword);}


    @PostMapping("/ikCreate")
    public void ikCreate(@RequestParam String index) throws Exception {
        // 1. 创建索引的请求
        CreateIndexRequest request = new CreateIndexRequest(index);
        request.settings(Settings.builder()
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 0)
                .put("refresh_interval", "1m")
        );

        // 2. 创建索引映射
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.startObject("properties");
            {
                builder.startObject("title");
                {
                    builder.field("type", "text");
                    builder.field("copy_to", "titleAndContent");
                }
                builder.endObject();

                builder.startObject("content");
                {
                    builder.field("type", "text");
                    builder.field("copy_to", "titleAndContent");
                }
                builder.endObject();

                builder.startObject("titleAndContent");
                {
                    builder.field("type", "text")
                            .field("analyzer", "ik_max_word")
                            .field("search_analyzer", "ik_smart");
                }
                builder.endObject();

            }
            builder.endObject();
        }
        builder.endObject();

        request.mapping("XContentBuilder",builder); // 使用新的mapping方法

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "123456"));
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                                new HttpHost("localhost", 9200, "http"))
                        .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                                httpClientBuilder.disableAuthCaching();
                                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                            }
                        }));
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    @PostMapping("/faqCreate")
    public void faqCreate(@RequestParam String index) throws Exception {
        // 1. 创建索引的请求
        CreateIndexRequest request = new CreateIndexRequest(index);
        request.settings(Settings.builder()
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 0)
                .put("refresh_interval", "1m")
        );

        // 2. 创建索引映射
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.startObject("properties");
            {
                builder.startObject("question");
                {
                    builder.field("type", "text");
                    builder.field("copy_to", "question_and_answer");
                }
                builder.endObject();

                builder.startObject("answer");
                {
                    builder.field("type", "text");
                    builder.field("copy_to", "question_and_answer");
                }
                builder.endObject();

                builder.startObject("question_and_answer");
                {
                    builder.field("type", "text")
                            .field("analyzer", "ik_max_word")
                            .field("search_analyzer", "ik_smart");
                }
                builder.endObject();

            }
            builder.endObject();
        }
        builder.endObject();

        request.mapping("XContentBuilder",builder); // 使用新的mapping方法

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "123456"));
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                                new HttpHost("localhost", 9200, "http"))
                        .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                                httpClientBuilder.disableAuthCaching();
                                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                            }
                        }));
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }

}
