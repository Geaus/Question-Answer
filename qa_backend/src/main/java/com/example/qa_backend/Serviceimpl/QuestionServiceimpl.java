package com.example.qa_backend.Serviceimpl;

import com.example.qa_backend.Dao.*;
import com.example.qa_backend.Entity.*;
import com.example.qa_backend.JSON.QuestionJSON;
import com.example.qa_backend.Service.QuestionService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hankcs.hanlp.mining.word2vec.DocVectorModel;
import com.hankcs.hanlp.mining.word2vec.WordVectorModel;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.index.query.functionscore.ScriptScoreFunctionBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
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

import javax.annotation.PostConstruct;
import java.io.File;
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

    ActionListener<DeleteResponse> listener1 = new ActionListener<DeleteResponse>() {
        @Override
        public void onResponse(DeleteResponse indexResponse) {

        }

        @Override
        public void onFailure(Exception e) {
            System.out.println("Es删除出错");
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
//        this.wordVectorModel = new WordVectorModel("src/main/resources/sgns.zhihu.word");
//        this.docVectorModel = new DocVectorModel(wordVectorModel);

//        List<String> questions = new ArrayList<>();
//        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/web_text_zh_train.json"))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String title = line.split("\"title\": \"")[1].split("\"")[0].trim();
//                questions.add(title);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        for(String question1 : questions){
//            Question question = new Question();
//            question.setContent("");
//            question.setUser(userDao.findUser(1));
//            question.setTitle(question1);
//            question = questionDao.addQuestion(question);
//            Map<String, Object> jsonMap = new HashMap<>();
//            jsonMap.put("title", question1);
//            jsonMap.put("content", " ");
//            IndexRequest indexRequest = new IndexRequest("1")
//                    .id(String.valueOf(question.getId())).source(jsonMap);
//            IndexResponse indexResponse = writeRestClient.index(indexRequest, RequestOptions.DEFAULT);
//
//        }
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
//        float[] arr = this.docVectorModel.query(title).getElementArray();
//        jsonMap.put("vector", arr);
        IndexRequest indexRequest = new IndexRequest("test1")
                .id(String.valueOf(ques_id)).source(jsonMap);
        IndexRequest.RefreshPolicy.parse("wait_for");

        writeRestClient.indexAsync(indexRequest, RequestOptions.DEFAULT, listener);
    }

    @Override
    public List<QuestionJSON> EsSearch(String keyword, int limit, int uid, int page_id) throws ExecutionException, InterruptedException {
        CompletableFuture<List<QuestionJSON>> future = new CompletableFuture<>();

        SearchRequest searchRequest = new SearchRequest("11");

        // 高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("titleAndContent");
        highlightBuilder.requireFieldMatch(false);
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");

        // 构建搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .query(QueryBuilders.multiMatchQuery(keyword, "titleAndContent"))
                .sort(SortBuilders.scoreSort().order(SortOrder.DESC))
                .from((page_id - 1) * 10)
                .size(10)
                .highlighter(highlightBuilder);
        searchRequest.source(searchSourceBuilder);

        searchRestClient.searchAsync(searchRequest, RequestOptions.DEFAULT, new ActionListener<SearchResponse>() {
            @Override
            public void onResponse(SearchResponse searchResponse) {
                List<QuestionJSON> list = new ArrayList<>();
                for (SearchHit hit : searchResponse.getHits().getHits()) {
                    QuestionJSON questionJSON = new QuestionJSON();

                    HighlightField titleField = hit.getHighlightFields().get("titleAndContent");
                        String title = titleField.getFragments()[0].toString().split("&&")[0];
                        String content = titleField.getFragments()[0].toString().split("&&")[1];
                                        if (title != null && !title.equals("")) {
                            questionJSON.setTitle(title);
                        }
                                        if (content != null && !content.equals("")) {
                            questionJSON.setContent(content);
                        }

                    String id = hit.getId();
                    Question question = questionDao.getQuestion(Integer.parseInt(id));
                    questionJSON.setId(question.getId());
                    question.setCreateTime(question.getCreateTime());
                    questionJSON.setTags(question.getTags());
                    questionJSON.setUser(question.getUser());
                    int likeFlag = 0, markFlag = 0;
                    FeedbackForQuestion feedback = feedbackQuestionDao.findSpecific(question.getId(), uid);
                    if (feedback != null) {
                        if (feedback.getLike() == 1) likeFlag = 1;
                        else if (feedback.getLike() == -1) likeFlag = -1;
                        if (feedback.getBookmark() == 1) markFlag = 1;
                    }
                    questionJSON.setLike(question.getLike());
                    questionJSON.setDislike(question.getDislike());
                    questionJSON.setMark(question.getMark());
                    questionJSON.setLikeFlag(likeFlag);
                    questionJSON.setMarkFlag(markFlag);
                    list.add(questionJSON);
                }
                future.complete(list);
            }

            @Override
            public void onFailure(Exception e) {
                future.completeExceptionally(e);
            }
        });

        return future.get();
    }

    @Override
    public List<QuestionJSON> EsSearch1(String keyword, int limit, int uid) throws IOException {
        String indexName = "test1";
        List<QuestionJSON> list = new ArrayList<>();
        try {

            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field("title");
            highlightBuilder.field("content");
            highlightBuilder.requireFieldMatch(false);
            highlightBuilder.preTags("<span style='color:red'>");
            highlightBuilder.postTags("</span>");
            // 构建查询请求
            SearchRequest searchRequest = new SearchRequest(indexName);

            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(QueryBuilders.matchAllQuery());

            Map<String, Object> params = new HashMap<>();
            float[] arr = this.docVectorModel.query(keyword).getElementArray(); // 将 float[] 转换为 double[]
            params.put("query_vector", arr);

            Script script = new Script(
                    ScriptType.INLINE,
                    "painless",
                    "cosineSimilarity(params.query_vector, 'vector') + 1.0",
                    params
            );

            ScriptScoreFunctionBuilder scriptScoreFunction = ScoreFunctionBuilders.scriptFunction(script);

            FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(QueryBuilders.matchAllQuery(), scriptScoreFunction);
            sourceBuilder.query(functionScoreQueryBuilder);
            searchRequest.source(sourceBuilder);

            // 执行查询
            SearchResponse searchResponse = searchRestClient.search(searchRequest, RequestOptions.DEFAULT);

            // 处理查询结果
            System.out.println(searchResponse.getHits().getTotalHits().value);

            for (SearchHit hit : searchResponse.getHits().getHits()) {
                QuestionJSON questionJSON = new QuestionJSON();

                HighlightField titleField = hit.getHighlightFields().get("title");
                String title = titleField.getFragments()[0].toString();
                HighlightField contentField = hit.getHighlightFields().get("content");
                String content = contentField.getFragments()[0].toString();
                if (title != null && !title.equals("")) {
                    questionJSON.setTitle(title);
                }
                if (content != null && !content.equals("")) {
                    questionJSON.setContent(content);
                }

                String id = hit.getId();
                Question question = questionDao.getQuestion(Integer.parseInt(id));
                questionJSON.setId(question.getId());
                question.setCreateTime(question.getCreateTime());
                questionJSON.setTags(question.getTags());
                questionJSON.setUser(question.getUser());
                int likeFlag = 0, markFlag = 0;
                FeedbackForQuestion feedback = feedbackQuestionDao.findSpecific(question.getId(), uid);
                if (feedback != null) {
                    if (feedback.getLike() == 1) likeFlag = 1;
                    else if (feedback.getLike() == -1) likeFlag = -1;
                    if (feedback.getBookmark() == 1) markFlag = 1;
                }
                questionJSON.setLike(question.getLike());
                questionJSON.setDislike(question.getDislike());
                questionJSON.setMark(question.getMark());
                questionJSON.setLikeFlag(likeFlag);
                questionJSON.setMarkFlag(markFlag);
                list.add(questionJSON);
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void faqWrite(String question, String answer){

        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("question", question);
        jsonMap.put("answer", answer);
        jsonMap.put("question_and_answer", question+"&&"+answer);
        IndexRequest indexRequest = new IndexRequest("faqRobot")
                .source(jsonMap);

        writeRestClient.indexAsync(indexRequest, RequestOptions.DEFAULT, listener);
    }

    @Override
    public String faqSearch(String keyword) throws IOException {
        CompletableFuture<List<QuestionJSON>> future = new CompletableFuture<>();

        SearchRequest searchRequest = new SearchRequest("faqRobot");

        // 高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("question_and_answer");
        highlightBuilder.requireFieldMatch(false);
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");

        // 构建搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .query(QueryBuilders.multiMatchQuery(keyword, "question_and_answer"))
                .sort(SortBuilders.scoreSort().order(SortOrder.DESC))
                .size(1)
                .highlighter(highlightBuilder);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = searchRestClient.search(searchRequest, RequestOptions.DEFAULT);
        String res = "";
        for (SearchHit hit : searchResponse.getHits().getHits()) {
//                    System.out.println(hit.getScore());
            HighlightField answerField = hit.getHighlightFields().get("question_and_answer");
            if (answerField != null) {
                res = answerField.getFragments()[0].toString().split("&&")[1];
            }
        }
        return res;
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
//        docVectorModel.addDocument(question.getId(), question.getTitle());


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
        deleteRestClient.deleteAsync(request, RequestOptions.DEFAULT, listener1);

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
