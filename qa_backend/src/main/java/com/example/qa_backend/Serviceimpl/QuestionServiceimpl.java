package com.example.qa_backend.Serviceimpl;

import com.example.qa_backend.Dao.*;
import com.example.qa_backend.Entity.*;
import com.example.qa_backend.JSON.QuestionJSON;
import com.example.qa_backend.Service.QuestionService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.mining.word2vec.DocVectorModel;
import com.hankcs.hanlp.mining.word2vec.WordVectorModel;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.lucene.HanLPAnalyzer;
import io.lettuce.core.RedisException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.hi.HindiNormalizationFilter;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;


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

    private WordVectorModel wordVectorModel;
    private DocVectorModel docVectorModel;
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private static final Map<String, List<QuestionJSON>> searchCache = new ConcurrentHashMap<>();
    private JedisPool jedisPool;
    private Analyzer analyzer;
    @PostConstruct
    public void init() throws IOException {

        try{
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            jedisPool = new JedisPool(poolConfig,"127.0.0.1", 6379, 1000, null);

        } catch (Exception e) {
            throw new RedisException("初始化redisPool失败");   //抛出异常
        }
        Jedis jedis = jedisPool.getResource();
        jedis.flushAll();
        this.wordVectorModel = new WordVectorModel("src/main/resources/sgns.zhihu.word");
        this.docVectorModel = new DocVectorModel(wordVectorModel);
        List<Question> questions = questionDao.listAll();
        for(Question question : questions){
            docVectorModel.addDocument(question.getId(), question.getTitle());
        }
        analyzer = new HanLPAnalyzer();
        Directory directory = FSDirectory.open(Paths.get("src/main/resources/indexLibrary"));
        IndexReader indexReader = DirectoryReader.open(directory);
        this.docVectorModel.nearest("已加载");
//        Jedis jedis = jedisPool.getResource();
//        jedis.flushAll();
//        List<String> questions = new ArrayList<>();
//
////        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/baike_qa_valid.json"))) {
////            String line;
////            while ((line = reader.readLine()) != null) {
////                String title = line.split("\"title\": ")[1].split(", ")[0].trim();
////                questions.add(title);
////            }
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//
//        IndexWriter indexWriter ;
//        IndexReader indexReader ;
//        Directory directory ;
//        Analyzer analyzer ;
//
////创建索引目录文件
//
//        analyzer = new HanLPAnalyzer();
//// 2. 创建Directory对象,声明索引库的位置
//        directory = FSDirectory.open(Paths.get("src/main/resources/indexLibrary"));
//// 3. 创建IndexWriteConfig对象，写入索引需要的配置
//        IndexWriterConfig writerConfig = new IndexWriterConfig(analyzer);
//// 4.创建IndexWriter写入对象
//        indexWriter = new IndexWriter(directory, writerConfig);
//
////        for(String question1 : questions){
////            Question question = new Question();
////            question.setContent("");
////            question.setUser(userDao.findUser(1));
////            question.setTitle(question1);
////            question = questionDao.addQuestion(question);
//
//            Document doc = new Document();
////StringField 不分词 直接建索引 存储
//            doc.add(new StringField("id", String.valueOf(38), Field.Store.YES));
////TextField 分词 建索引 存储
//            doc.add(new TextField("title", "如何评价软件工程这个专业", Field.Store.YES));
////TextField 分词 建索引 存储
//            doc.add(new TextField("content", "rt，如何评价软件工程，就业前景如何，学习难度如何", Field.Store.YES));
//
//            indexWriter.addDocument(doc);
//
////        }
//        if (indexWriter != null) {
//            try {
//                indexWriter.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        if (directory != null) {
//            try {
//                directory.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
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
        Jedis jedis = jedisPool.getResource();
        if (jedis.dbSize() == 0) {
            // jedis没有内容，跳过操作
            jedis.close();
        } else {
            // jedis有内容，执行flushall操作
            jedis.flushAll();
            jedis.close();
        }
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
        IndexWriter indexWriter ;
        Directory directory ;
        Analyzer analyzer ;

        //创建索引目录文件

//        analyzer = new HanLPAnalyzer();
//        // 2. 创建Directory对象,声明索引库的位置
//        directory = FSDirectory.open(Paths.get("src/main/resources/indexLibrary"));
//        // 3. 创建IndexWriteConfig对象，写入索引需要的配置
//        IndexWriterConfig writerConfig = new IndexWriterConfig(analyzer);
//        // 4.创建IndexWriter写入对象
//        indexWriter = new IndexWriter(directory, writerConfig);
//        // 5.写入到索引库，通过IndexWriter添加文档对象document
//        Document doc = new Document();
//        //StringField 不分词 直接建索引 存储
//        doc.add(new StringField("id", String.valueOf(question.getId()), Field.Store.YES));
//        //TextField 分词 建索引 存储
//        doc.add(new TextField("title", question.getTitle(), Field.Store.YES));
//        //TextField 分词 建索引 存储
//        doc.add(new TextField("content", question.getContent(), Field.Store.YES));
//
//        indexWriter.addDocument(doc);
//        if (indexWriter != null) {
//            try {
//                indexWriter.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        if (directory != null) {
//            try {
//                directory.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

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
    public void deleteQuestion(int qid) {
        Jedis jedis = jedisPool.getResource();
        if (jedis.dbSize() == 0) {
            // jedis没有内容，跳过操作
            jedis.close();
        } else {
            // jedis有内容，执行flushall操作
            jedis.flushAll();
            jedis.close();
        }

        Question question = questionDao.getQuestion(qid);
        List<Answer> answers = answerDao.findAnswers(question);
        for(int i = 0; i < answers.size(); i++)feedbackAnswerDao.deleteByAns(answers.get(i).getId());
        feedbackQuestionDao.deleteByQues(qid);
        tagQuesDao.deleteRelation(qid);
        questionDao.deleteQuestion(question);
        keywordDao.deleteKeyword(qid);

        docVectorModel.remove(qid);

        IndexWriter indexWriter = null;
        Directory directory = null;
        try (Analyzer analyzer = new HanLPAnalyzer()) {
            directory = FSDirectory.open(Paths.get("src/main/resources/indexLibrary"));
            IndexWriterConfig writerConfig = new IndexWriterConfig(analyzer);
            indexWriter = new IndexWriter(directory, writerConfig);
            //根据id字段进行删除
            indexWriter.deleteDocuments(new org.apache.lucene.index.Term("id", String.valueOf(qid)));
            indexWriter.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("删除索引库出错：" + e.getMessage());
        } finally {
            if (indexWriter != null) {
                try {
                    indexWriter.commit();
                    indexWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (directory != null) {
                try {
                    directory.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
        try (
             Directory directory = FSDirectory.open(Paths.get("src/main/resources/indexLibrary"));
             IndexReader indexReader = DirectoryReader.open(directory)) {

            List<Term> termList = HanLP.segment(keyWord.toLowerCase());
            CoreStopWordDictionary.apply(termList);
            StringBuilder sentenceBuilder = new StringBuilder();
            for (Term term : termList) {
                String word = term.toString().substring(0, term.length());
                sentenceBuilder.append(word);
            }
            String sentence = sentenceBuilder.toString();

            QueryParser queryParser = new MultiFieldQueryParser(new String[]{"title", "content"}, analyzer);
            Query query = queryParser.parse(!StringUtils.isEmpty(sentence) ? sentence : "*:*");
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);
            TopDocs topDocs = indexSearcher.search(query, 10);
            //高亮显示
            SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<span style='color:red'>", "</span>");
            Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
            Fragmenter fragmenter = new SimpleFragmenter(100);   //高亮后的段落范围在100字内
            highlighter.setTextFragmenter(fragmenter);

            if (topDocs.totalHits > 0 && !StringUtils.isEmpty(sentence)) {
                for (ScoreDoc sd : topDocs.scoreDocs) {
                    Document doc = indexSearcher.doc(sd.doc);
                    Question question = questionDao.getQuestion(Integer.parseInt(doc.get("id")));
                    QuestionJSON res = questionToQuestionJSON(question, uid, true);
                    res.setTitle(highlighter.getBestFragment(analyzer, "title", doc.get("title")));
                    searchList.add(res);
                }
            }
            indexReader.close();

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
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("全文检索出错：" + e.getMessage());
        }
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
