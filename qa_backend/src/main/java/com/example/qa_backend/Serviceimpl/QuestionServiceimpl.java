package com.example.qa_backend.Serviceimpl;

import com.example.qa_backend.Dao.*;
import com.example.qa_backend.Entity.*;
import com.example.qa_backend.JSON.QuestionJSON;
import com.example.qa_backend.Repository.EsRepository;
import com.example.qa_backend.Service.QuestionService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hankcs.hanlp.mining.word2vec.DocVectorModel;
import com.hankcs.hanlp.mining.word2vec.WordVectorModel;
import io.lettuce.core.RedisException;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;


@Service
public class QuestionServiceimpl implements QuestionService {
    @Autowired
    QuestionDao questionDao;
    @Autowired
    UserDao userDao;
    @Autowired
    TagQuesDao tagQuesDao;
    @Autowired
    FeedbackQuestionDao feedbackQuestionDao;
    @Autowired
    FeedbackAnswerDao feedbackAnswerDao;
    @Autowired
    KeywordDao keywordDao;
    @Autowired
    AnswerDao answerDao;
    @Autowired
    EsRepository esRepository;
    @Autowired
    private RestHighLevelClient restClient;

    ActionListener<IndexResponse> listener = new ActionListener<IndexResponse>() {
        @Override
        public void onResponse(IndexResponse indexResponse) {

        }

        @Override
        public void onFailure(Exception e) {
            System.out.println("Es写入出错");
        }
    };

    private final RestHighLevelClient writeRestClient = initRestClient();
    private final RestHighLevelClient searchRestClient = initRestClient();
    private final RestHighLevelClient deleteRestClient = initRestClient();
    private WordVectorModel wordVectorModel;
    private DocVectorModel docVectorModel;
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private static final Map<String, List<QuestionJSON>> searchCache = new ConcurrentHashMap<>();
    private JedisPool jedisPool;

    private RestHighLevelClient initRestClient(){
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "123456"));
        return new RestHighLevelClient(
                RestClient.builder(
                                new HttpHost("localhost", 9200, "http"))
                        .setHttpClientConfigCallback(httpClientBuilder -> {
                            httpClientBuilder.disableAuthCaching();
                            return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                        }));

    }
    @PostConstruct
    public void init() throws IOException {

        List<String> questions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/web_text_zh_train.json"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String title = line.split("\"title\": \"")[1].split("\"")[0].trim();
                questions.add(title);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(String question1 : questions){
            Question question = new Question();
            question.setContent("");
            question.setUser(userDao.findUser(1));
            question.setTitle(question1);
            question = questionDao.addQuestion(question);
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("title", question1);
            jsonMap.put("content", "");
            IndexRequest indexRequest = new IndexRequest("1")
                    .id(String.valueOf(question.getId())).source(jsonMap);
            IndexResponse indexResponse = writeRestClient.index(indexRequest, RequestOptions.DEFAULT);

        }
    }

    @Override
    public void esTest(int userId, String content, String title) throws IOException {
        Question question = new Question();
        question.setContent(content);
        question.setUser(userDao.findUser(userId));
        question.setTitle(title);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        String formattedDate = sdf.format(currentDate);
        question.setCreateTime(formattedDate);
        question = questionDao.addQuestion(question);
        int ques_id = question.getId();

//        IndexRequest request = new IndexRequest("1");
//        request.setRefreshPolicy(WriteRequest.RefreshPolicy.NONE);
//        Es article = new Es();
//        article.setTitle(title);
//        article.setContent(content);
//        article.setId(ques_id);
//        esRepository.save(article);
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("title", title);
        jsonMap.put("content", content);
        jsonMap.put("titleAndContent", title+"&&"+content);
        IndexRequest indexRequest = new IndexRequest("11")
                .id(String.valueOf(ques_id)).source(jsonMap);
        IndexRequest.RefreshPolicy.parse("wait_for");

        writeRestClient.indexAsync(indexRequest, RequestOptions.DEFAULT, listener);
    }

//    @Override
//    public List<QuestionJSON> EsSearch(String keyword, int limit, int uid, int page_id) throws ExecutionException, InterruptedException {
//        CompletableFuture<List<QuestionJSON>> future = new CompletableFuture<>();
//
//        SearchRequest searchRequest = new SearchRequest("11");
//
//        // 高亮
//        HighlightBuilder highlightBuilder = new HighlightBuilder();
//        highlightBuilder.field("titleAndContent");
//        highlightBuilder.requireFieldMatch(false);
//        highlightBuilder.preTags("<span style='color:red'>");
//        highlightBuilder.postTags("</span>");
//
//        // 构建搜索条件
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
//                .query(QueryBuilders.multiMatchQuery(keyword, "titleAndContent"))
//                .sort(SortBuilders.scoreSort().order(SortOrder.DESC))
//                .from((page_id - 1) * 10)
//                .size(10)
//                .highlighter(highlightBuilder);
//        searchRequest.source(searchSourceBuilder);
//
//        searchRestClient.searchAsync(searchRequest, RequestOptions.DEFAULT, new ActionListener<SearchResponse>() {
//            @Override
//            public void onResponse(SearchResponse searchResponse) {
//                List<QuestionJSON> list = new ArrayList<>();
//                for (SearchHit hit : searchResponse.getHits().getHits()) {
//                    QuestionJSON questionJSON = new QuestionJSON();
//
//                    HighlightField titleField = hit.getHighlightFields().get("titleAndContent");
//                    if (titleField != null) {
//                        questionJSON.setTitle(titleField.getFragments()[0].toString().split("&&")[0]);
//                    }
//                    HighlightField contentField = hit.getHighlightFields().get("titleAndContent");
//                    if (contentField != null) {
//                        questionJSON.setContent(contentField.getFragments()[0].toString().split("&&")[1]);
//                    }
//
//                    String id = hit.getId();
//                    Question question = questionDao.getQuestion(Integer.parseInt(id));
//                    questionJSON.setId(question.getId());
//                    question.setCreateTime(question.getCreateTime());
//                    questionJSON.setTags(question.getTags());
//                    questionJSON.setUser(question.getUser());
//                    int likeFlag = 0, markFlag = 0;
//                    FeedbackForQuestion feedback = feedbackQuestionDao.findSpecific(question.getId(), uid);
//                    if (feedback != null) {
//                        if (feedback.getLike() == 1) likeFlag = 1;
//                        else if (feedback.getLike() == -1) likeFlag = -1;
//                        if (feedback.getBookmark() == 1) markFlag = 1;
//                    }
//                    questionJSON.setLike(question.getLike());
//                    questionJSON.setDislike(question.getDislike());
//                    questionJSON.setMark(question.getMark());
//                    questionJSON.setLikeFlag(likeFlag);
//                    questionJSON.setMarkFlag(markFlag);
//                    list.add(questionJSON);
//                }
//                future.complete(list);
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//                future.completeExceptionally(e);
//            }
//        });
//
//        return future.get();
//    }

    @Override
    public List<QuestionJSON> EsSearch(String keyword, int limit, int uid) throws IOException {
        SearchRequest searchRequest = new SearchRequest("question");

        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title");
        highlightBuilder.field("content");
        highlightBuilder.requireFieldMatch(false);
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");

        //构建搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .query(QueryBuilders.multiMatchQuery(keyword, "title", "content"))
                .sort(SortBuilders.scoreSort().order(SortOrder.DESC))
//                .sort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
//                .sort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
//                .sort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                .from(0)// 指定从哪条开始查询
//                .size(limit)// 需要查出的总记录条数
                .highlighter(highlightBuilder);//高亮
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restClient.search(searchRequest, RequestOptions.DEFAULT);

        List<QuestionJSON> list = new ArrayList<>();
        long total = searchResponse.getHits().getTotalHits().value;
        System.out.println(total);
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            QuestionJSON questionJSON = new QuestionJSON();

            // 处理高亮显示的结果
            HighlightField titleField = hit.getHighlightFields().get("title");
            if (titleField != null) {
                questionJSON.setTitle(titleField.getFragments()[0].toString());
            }
            HighlightField contentField = hit.getHighlightFields().get("content");
            if (contentField != null) {
                questionJSON.setContent(contentField.getFragments()[0].toString());
            }
            Question question = questionDao.getQuestion(Integer.parseInt(hit.getId()));
            System.out.println(hit.getId());
            questionJSON.setId(question.getId());
            question.setCreateTime(question.getCreateTime());
            questionJSON.setTags(question.getTags());
            questionJSON.setUser(question.getUser());
            int likeFlag = 0, markFlag = 0;
            FeedbackForQuestion feedback = feedbackQuestionDao.findSpecific(question.getId(), uid);
            if(feedback != null) {
                if(feedback.getLike() == 1)likeFlag = 1;
                else if(feedback.getLike() == -1)likeFlag = -1;
                if(feedback.getBookmark() == 1)markFlag = 1;
            }
            questionJSON.setLike(question.getLike());
            questionJSON.setDislike(question.getDislike());
            questionJSON.setMark(question.getMark());
            questionJSON.setLikeFlag(likeFlag);
            questionJSON.setMarkFlag(markFlag);
            list.add(questionJSON);
        }
        return list;
    }
    @Override
    public List<QuestionJSON> listQuestions(int page_id, int uid) {
        List<Question> ques = questionDao.listQuestions(page_id);
        List<QuestionJSON> resList = new ArrayList<>();
        for(int i = 0; i < ques.size(); i++) {
            Question question = ques.get(i);
            QuestionJSON res = new QuestionJSON();
            res.setId(question.getId());
            res.setContent(question.getContent());
            res.setCreateTime(question.getCreateTime());
            res.setTags(question.getTags());
            res.setTitle(question.getTitle());
            res.setUser(question.getUser());
            int likeFlag = 0, markFlag = 0;
            FeedbackForQuestion feedback = feedbackQuestionDao.findSpecific(question.getId(), uid);
            if(feedback != null) {
                if(feedback.getLike() == 1)likeFlag = 1;
                else if(feedback.getLike() == -1)likeFlag = -1;
                if(feedback.getBookmark() == 1)markFlag = 1;
            }
            res.setLike(question.getLike());
            res.setDislike(question.getDislike());
            res.setMark(question.getMark());
            res.setLikeFlag(likeFlag);
            res.setMarkFlag(markFlag);
            resList.add(res);
        }
        return resList;
    }

    @Override
    public QuestionJSON findQuestion(int uid, int id) {
        Question question = questionDao.getQuestion(id);
        QuestionJSON res = new QuestionJSON();
        res.setId(question.getId());
        res.setContent(question.getContent());
        res.setCreateTime(question.getCreateTime());
        res.setTags(question.getTags());
        res.setTitle(question.getTitle());
        res.setUser(question.getUser());
        int likeFlag = 0, markFlag = 0;
        FeedbackForQuestion feedback = feedbackQuestionDao.findSpecific(id, uid);
        if(feedback != null) {
            if(feedback.getLike() == 1)likeFlag = 1;
            else if(feedback.getLike() == -1)likeFlag = -1;
            if(feedback.getBookmark() == 1)markFlag = 1;
        }
        res.setLike(question.getLike());
        res.setDislike(question.getDislike());
        res.setMark(question.getMark());
        res.setLikeFlag(likeFlag);
        res.setMarkFlag(markFlag);
        return res;
    }

    @Override
    public Question askQuestion(int userId, String content, String title, List<Tag> tags) throws IOException {
//        Jedis jedis = jedisPool.getResource();
//        if (jedis.dbSize() == 0) {
//            // jedis没有内容，跳过操作
//            jedis.close();
//        } else {
//            // jedis有内容，执行flushall操作
//            jedis.flushAll();
//            jedis.close();
//        }
        Question question = new Question();
        question.setContent(content);
        question.setUser(userDao.findUser(userId));
        question.setTitle(title);
        question = questionDao.addQuestion(question);
        int ques_id = question.getId();
        for(int i = 0; i < tags.size(); i++) {
            TagQuesRelation tagQuesRelation = new TagQuesRelation();
            tagQuesRelation.setQuesId(ques_id);
            tagQuesRelation.setTagId(tags.get(i).getId());
            tagQuesDao.addRelation(tagQuesRelation);
        }
        docVectorModel.addDocument(question.getId(), question.getTitle());


        User user = userDao.findUser(userId);
        user.setAsked(user.getAsked() + 1);
        user = userDao.addOne(user);

        return question;
    }

    @Override
    public List<Question> listAsked(int page_id, int userId) {
        return questionDao.getAsked(page_id, userDao.findUser(userId));
    }

    @Override
    public List<Question> getLiked(int page_id, int userId) {
        List<FeedbackForQuestion> feedback = feedbackQuestionDao.listRelatedQuestionLike(page_id, userId);
        List<Question> res = new ArrayList<>();
        for(int i = 0; i < feedback.size(); i++) {
            if(feedback.get(i).getLike() != 1)continue;
            res.add(questionDao.getQuestion(feedback.get(i).getQuesId()));
        }
        return res;
    }

    @Override
    public List<Question> getDisliked(int page_id, int userId) {
        List<FeedbackForQuestion> feedback = feedbackQuestionDao.listRelatedQuestionDislike(page_id, userId);
        List<Question> res = new ArrayList<>();
        for(int i = 0; i < feedback.size(); i++) {
            if(feedback.get(i).getLike() != -1)continue;
            res.add(questionDao.getQuestion(feedback.get(i).getQuesId()));
        }
        return res;
    }

    @Override
    public List<Question> getMarked(int page_id, int userId) {
        List<FeedbackForQuestion> feedback = feedbackQuestionDao.listRelatedQuestionMark(page_id, userId);
        List<Question> res = new ArrayList<>();
        for(int i = 0; i < feedback.size(); i++) {
            if(feedback.get(i).getBookmark() != 1)continue;
            res.add(questionDao.getQuestion(feedback.get(i).getQuesId()));
        }
        return res;
    }

    @Override
    public void deleteQuestion(int qid) throws IOException {
        Question question = questionDao.getQuestion(qid);
        List<Answer> answers = answerDao.findAnswers(question);
        for(int i = 0; i < answers.size(); i++)feedbackAnswerDao.deleteByAns(answers.get(i).getId());
        feedbackQuestionDao.deleteByQues(qid);
        tagQuesDao.deleteRelation(qid);
        questionDao.deleteQuestion(question);
        keywordDao.deleteKeyword(qid);

        DeleteRequest request = new DeleteRequest("110", String.valueOf(qid));
        request.setRefreshPolicy("wait_for");
        DeleteResponse deleteResponse = deleteRestClient.delete(
                request, RequestOptions.DEFAULT);
        if (deleteResponse.getResult() == DocWriteResponse.Result.NOT_FOUND) {
            System.out.println("es未发现需要删除的问题");
        }

    }

    @Override
    public List<QuestionJSON> searchByTag(int tagId, int uid, int page_id){
        List<TagQuesRelation> tagQuesRelations = tagQuesDao.searchByTagId(tagId, page_id);
        List<Question> questions = new ArrayList<>();
        for(TagQuesRelation tagQuesRelation : tagQuesRelations){
            questions.add(questionDao.getQuestion(tagQuesRelation.getQuesId()));
        }

        List<QuestionJSON> resList = new ArrayList<>();
        for(int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            QuestionJSON res = new QuestionJSON();
            res.setId(question.getId());
            res.setContent(question.getContent());
            res.setCreateTime(question.getCreateTime());
            res.setTags(question.getTags());
            res.setTitle(question.getTitle());
            res.setUser(question.getUser());
            List<FeedbackForQuestion> feedback = feedbackQuestionDao.findFeedback(question.getId());
            int like = 0, dislike = 0, mark = 0, likeFlag = 0, markFlag = 0;
            for(int j = 0; j < feedback.size(); j++) {
                if(feedback.get(j).getLike() == -1){
                    if(feedback.get(j).getUserId() == uid)likeFlag = -1;
                    dislike++;
                }
                else if(feedback.get(j).getLike() == 1){
                    if(feedback.get(j).getUserId() == uid)likeFlag = 1;
                    like++;
                }
                if(feedback.get(j).getBookmark() == 1){
                    if(feedback.get(j).getUserId() == uid)markFlag = 1;
                    mark++;
                }
            }
            res.setLike(like);
            res.setDislike(dislike);
            res.setMark(mark);
            res.setLikeFlag(likeFlag);
            res.setMarkFlag(markFlag);
            resList.add(res);
        }
        return resList;
    }

    QuestionJSON questionToQuestionJSON (Question question, int uid, boolean isHighLighter){

        QuestionJSON res = new QuestionJSON();
        res.setId(question.getId());
        res.setContent(question.getContent());
        res.setCreateTime(question.getCreateTime());
        res.setTags(question.getTags());
        if(!isHighLighter) res.setTitle(question.getTitle());
        res.setUser(question.getUser());
        List<FeedbackForQuestion> feedback = feedbackQuestionDao.findFeedback(question.getId());
        int like = 0, dislike = 0, mark = 0, likeFlag = 0, markFlag = 0;
        for (FeedbackForQuestion feedbackForQuestion : feedback) {
            if (feedbackForQuestion.getLike() == -1) {
                if (feedbackForQuestion.getUserId() == uid) likeFlag = -1;
                dislike++;
            } else if (feedbackForQuestion.getLike() == 1) {
                if (feedbackForQuestion.getUserId() == uid) likeFlag = 1;
                like++;
            }
            if (feedbackForQuestion.getBookmark() == 1) {
                if (feedbackForQuestion.getUserId() == uid) markFlag = 1;
                mark++;
            }
        }
        res.setLike(like);
        res.setDislike(dislike);
        res.setMark(mark);
        res.setLikeFlag(likeFlag);
        res.setMarkFlag(markFlag);
        return res;
    }

//    @Override
//    public  List<QuestionJSON> fullTextSearch(String keyWord, int uid, int page_id){
//        List<Question> questions = questionDao.findAllQuestionsByTitle(page_id, "%"+keyWord+"%");
//        List<QuestionJSON> questionJSONS = new ArrayList<>();
//        for(Question question : questions){
//            questionJSONS.add(questionToQuestionJSON(question, uid, false));
//        }
//        return questionJSONS;
//    }
    @Override
    public List<QuestionJSON> fullTextSearch(String keyWord, int uid, int page_id) throws IOException {
        // 检查缓存中是否存在结果
        List<QuestionJSON> cachedResults = getCachedResults(keyWord);
        if (cachedResults != null) {
            int size = cachedResults.size();
            int startIndex = 10 * page_id;
            int endIndex = 10 * (1 + page_id);
            if (startIndex > size) {
                cachedResults = new ArrayList<>();
                return cachedResults;// 调整结束位置的索引
            }
            if(endIndex + 1 > size){
                endIndex = size - 1;
            }
            return cachedResults.subList(startIndex, endIndex + 1);

        }

        List<QuestionJSON> searchList = new ArrayList<>();
        File indexFile = new File("src/main/resources/indexLibrary");
        File[] files = indexFile.listFiles();
        // 没有索引文件，没有查询结果
        if (files == null || files.length == 0) {
            return searchList;
        }

            List<Callable<Void>> tasks = new ArrayList<>();
            for (Map.Entry<Integer, Float> entry : this.docVectorModel.nearest(keyWord)) {
                Callable<Void> task = () -> {
                    Question question = questionDao.getQuestion(entry.getKey());
                    QuestionJSON res = questionToQuestionJSON(question, uid, false);
                    if (!searchList.contains(res)) {
                        searchList.add(res);
                    }
                    return null;
                };
                tasks.add(task);
            }

            try {
                List<Future<Void>> results = executorService.invokeAll(tasks);
                for (Future<Void> result : results) {
                    result.get(); // 等待任务执行完成
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            cacheResults(keyWord, searchList);

            return searchList.subList(0, Math.min(searchList.size(), 10));

    }

    private List<QuestionJSON> getCachedResults(String keyWord) {
        try (Jedis jedis = jedisPool.getResource()) {
            String cachedJson = jedis.get(keyWord);
            if (cachedJson != null) {
                // 创建ObjectMapper对象
                ObjectMapper objectMapper = new ObjectMapper();

                // 将缓存的JSON字符串转换回List<QuestionJSON>对象，并返回结果
                return objectMapper.readValue(cachedJson, new TypeReference<List<QuestionJSON>>() {});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // 缓存未命中或转换失败
    }

    private void cacheResults(String keyWord, List<QuestionJSON> searchResults) {
        try (Jedis jedis = jedisPool.getResource()) {
            // 创建ObjectMapper对象
            ObjectMapper objectMapper = new ObjectMapper();

            // 将搜索结果转换为JSON字符串
            String json = objectMapper.writeValueAsString(searchResults);

            // 使用关键词作为缓存键，将JSON字符串存储到Redis中
            jedis.set(keyWord, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
