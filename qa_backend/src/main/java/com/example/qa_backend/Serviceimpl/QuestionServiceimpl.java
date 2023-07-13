package com.example.qa_backend.Serviceimpl;

import com.example.qa_backend.Config.Config;
import com.example.qa_backend.Dao.*;
import com.example.qa_backend.Entity.*;
import com.example.qa_backend.JSON.QuestionJSON;
import com.example.qa_backend.Service.QuestionService;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.mining.word2vec.DocVectorModel;
import com.hankcs.hanlp.mining.word2vec.WordVectorModel;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.lucene.HanLPAnalyzer;
import org.apache.lucene.analysis.Analyzer;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.File;
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
    @Autowired
    Config config;

    private WordVectorModel wordVectorModel;
    private DocVectorModel docVectorModel;
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private static final Map<String, List<QuestionJSON>> searchCache = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() throws IOException {
        this.wordVectorModel = new WordVectorModel("src/main/resources/sgns.zhihu.word");
        this.docVectorModel = new DocVectorModel(wordVectorModel);
        List<Question> questions = questionDao.listAll();
        for(Question question : questions){
            docVectorModel.addDocument(question.getId(), question.getTitle());
        }
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

        IndexWriter indexWriter ;
        IndexReader indexReader ;
        Directory directory ;
        Analyzer analyzer ;

        //创建索引目录文件

        analyzer = new HanLPAnalyzer();
        // 2. 创建Directory对象,声明索引库的位置
        directory = FSDirectory.open(Paths.get("src/main/resources/indexLibrary"));
        // 3. 创建IndexWriteConfig对象，写入索引需要的配置
        IndexWriterConfig writerConfig = new IndexWriterConfig(analyzer);
        // 4.创建IndexWriter写入对象
        indexWriter = new IndexWriter(directory, writerConfig);
        // 5.写入到索引库，通过IndexWriter添加文档对象document
        Document doc = new Document();
        //StringField 不分词 直接建索引 存储
        doc.add(new StringField("id", String.valueOf(question.getId()), Field.Store.YES));
        //TextField 分词 建索引 存储
        doc.add(new TextField("title", question.getTitle(), Field.Store.YES));
        //TextField 分词 建索引 存储
        doc.add(new TextField("content", question.getContent(), Field.Store.YES));

        indexWriter.addDocument(doc);

        if (indexWriter != null) {
            try {
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

    private double calculateSimilarity(String sentence1, String sentence2) {
        System.out.println("Step1. 分词");
        List<String> sent1Words = getSplitWords(sentence1.toLowerCase());
        System.out.println(sentence1 + "\n分词结果：" + sent1Words);
        List<String> sent2Words = getSplitWords(sentence2.toLowerCase());
        System.out.println(sentence2 + "\n分词结果：" + sent2Words);

        System.out.println("Step2. 取并集");
        List<String> allWords = mergeList(sent1Words, sent2Words);
        System.out.println(allWords);


        int[] statistic1 = statistic(allWords, sent1Words);
        int[] statistic2 = statistic(allWords, sent2Words);

        // 向量A与向量B的点乘
        double dividend = 0;
        // 向量A所有维度值的平方相加
        double divisor1 = 0;
        // 向量B所有维度值的平方相加
        double divisor2 = 0;
        // 余弦相似度 算法
        for (int i = 0; i < statistic1.length; i++) {
            dividend += statistic1[i] * statistic2[i];
            divisor1 += Math.pow(statistic1[i], 2);
            divisor2 += Math.pow(statistic2[i], 2);
        }

        System.out.println("Step3. 计算词频向量");
        for(int i : statistic1) {
            System.out.print(i+",");
        }
        System.out.println();
        for(int i : statistic2) {
            System.out.print(i+",");
        }
        System.out.println();

        // 向量A与向量B的点乘 / （向量A所有维度值的平方相加后开方 * 向量B所有维度值的平方相加后开方）
        return dividend / (Math.sqrt(divisor1) * Math.sqrt(divisor2));

    }

    // 3. 计算词频
    private static int[] statistic(List<String> allWords, List<String> sentWords) {
        int[] result = new int[allWords.size()];
        for (int i = 0; i < allWords.size(); i++) {
            // 返回指定集合中指定对象出现的次数
            result[i] = Collections.frequency(sentWords, allWords.get(i));
        }
        return result;
    }

    // 2. 取并集
    private static List<String> mergeList(List<String> list1, List<String> list2) {
        List<String> result = new ArrayList<>();
        result.addAll(list1);
        result.addAll(list2);
        return result.stream().distinct().collect(Collectors.toList());
    }

    // 1. 分词
    private static List<String> getSplitWords(String sentence) {
        // 标点符号会被单独分为一个Term，去除之
        return HanLP.segment(sentence).stream().map(a -> a.word).filter(s -> !"`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？ ".contains(s)).collect(Collectors.toList());
    }

    @Override
    public List<QuestionJSON> searchByTitle(String searchTerm, int uid){

//        List<String> queryKeywords = getSplitWords(searchTerm);
//        System.out.println(queryKeywords);
        List<Term> termList = HanLP.segment(searchTerm.toLowerCase());
        List<String> keywordList = new ArrayList<>();
        System.out.println(termList);
        //遍历分词结果
        for (Term term : termList) {
            String word = term.toString().substring(0, term.length());      //词
            String nature = term.toString().substring(term.length() + 1);   //词性

            if (nature.contains("n")) {
                System.out.println(nature);
                keywordList.add(word);
            }
        }
        List<KeywordEntity> keywordEntities = new ArrayList<>();
        for(String keyword : keywordList){
            keywordEntities.addAll(keywordDao.findKeyword(keyword));
        }
        List<Question> allQuestions = new ArrayList<>();
        for(KeywordEntity keywordEntity : keywordEntities){
            Question question = questionDao.getQuestion(keywordEntity.getQuestionId());

            if (!allQuestions.contains(question)) {
                allQuestions.add(question);
            }
        }

        Map<Question, Double> questionSimilarities = new HashMap<>();

        for (Question question : allQuestions) {
//            List<String> questionKeywords = getSplitWords(question.getTitle());
//            System.out.println(questionKeywords);
            double similarity = calculateSimilarity(searchTerm, question.getTitle());
            System.out.println(question.getTitle()+" similarity:"+similarity);
            if(similarity > 0.2
            ){
                questionSimilarities.put(question, similarity);
            }
        }

        List<Question> sortedQuestions = questionSimilarities.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<QuestionJSON> resList = new ArrayList<>();
        for(int i = 0; i < sortedQuestions.size(); i++) {
            Question question = sortedQuestions.get(i);
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

    @Override
    public void deleteQuestion(int qid) {
        Question question = questionDao.getQuestion(qid);
        List<Answer> answers = answerDao.findAnswers(question);
        for(int i = 0; i < answers.size(); i++)feedbackAnswerDao.deleteByAns(answers.get(i).getId());
        feedbackQuestionDao.deleteByQues(qid);
        tagQuesDao.deleteRelation(qid);
        questionDao.deleteQuestion(question);
        keywordDao.deleteKeyword(qid);

        IndexWriter indexWriter = null;
        Directory directory = null;
        try (Analyzer analyzer = new HanLPAnalyzer()) {
            directory = FSDirectory.open(Paths.get("src/main/resources/indexLibrary"));
            IndexWriterConfig writerConfig = new IndexWriterConfig(analyzer);
            indexWriter = new IndexWriter(directory, writerConfig);
            //根据id字段进行删除
            indexWriter.deleteDocuments(new org.apache.lucene.index.Term("id", String.valueOf(qid)));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("删除索引库出错：" + e.getMessage());
        } finally {
            if (indexWriter != null) {
                try {
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
    public List<QuestionJSON> searchByTag(int tagId, int uid){
        List<TagQuesRelation> tagQuesRelations = tagQuesDao.searchByTagId(tagId);
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

//    private QuestionJSON questionToQuestionJSON(Question question)

//    @Override
//    public List<QuestionJSON> fullTextSearch(String keyWord, int uid) throws IOException {
////        Word2VecTrainer trainerBuilder = new Word2VecTrainer();
////        WordVectorModel wordVectorModel = trainerBuilder.train("D:/web/Qa/qa_backend/src/main/resources/polyglot-zh/polyglot-zh.txt", "D:/web/Qa/qa_backend/src/main/resources/polyglot-zh/result.txt");
////        WordVectorModel wordVectorModel = new WordVectorModel("src/main/resources/polyglot-zh.txt");
//
//        List<QuestionJSON> searchList = new ArrayList<>(10);
//        //PageData<ArticleEntity> pageData = new PageData<>();
//        File indexFile = new File("D:/web/Qa/indexLibrary");
//        File[] files = indexFile.listFiles();
//        //沒有索引文件，不然沒有查詢結果
//        if (files == null || files.length == 0) {
//            List<QuestionJSON> questionJSONS = null;
//            return questionJSONS;
//        }
//        IndexReader indexReader = null;
//        Directory directory = null;
//        try (Analyzer analyzer = new HanLPAnalyzer()) {
//            directory = FSDirectory.open(Paths.get("D:/web/Qa/indexLibrary"));
//
//            List<Term> termList = HanLP.segment(keyWord.toLowerCase());
//            System.out.println(termList);
//            CoreStopWordDictionary.apply(termList);
//            String sentence = "";
//            //遍历分词结果
//            for (Term term : termList) {
//                String word = term.toString().substring(0, term.length());      //词
//                sentence += word;
//            }
//            System.out.println(sentence);
//
//            //多项查询条件
//            QueryParser queryParser = new MultiFieldQueryParser(new String[]{"title", "content"}, analyzer);
//            //单项
//            Query query = queryParser.parse(!StringUtils.isEmpty(sentence) ? sentence : "*:*");
//            indexReader = DirectoryReader.open(directory);
//            IndexSearcher indexSearcher = new IndexSearcher(indexReader);
//            TopDocs topDocs = indexSearcher.search(query, 10);
//            //高亮显示
//            SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<span style='color:red'>", "</span>");
//            Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
//            Fragmenter fragmenter = new SimpleFragmenter(100);   //高亮后的段落范围在100字内
//            highlighter.setTextFragmenter(fragmenter);
//
//            System.out.println("totalHints"+topDocs.totalHits);
//
//            if(topDocs.totalHits > 0 && sentence != "") {
//                for (ScoreDoc sd : topDocs.scoreDocs) {
//                    Document doc = indexSearcher.doc(sd.doc);
//                    Question question = questionDao.getQuestion(Integer.parseInt(doc.get("id")));
//                    QuestionJSON res = new QuestionJSON();
//                    res.setId(question.getId());
//                    res.setContent(question.getContent());
//                    res.setCreateTime(question.getCreateTime());
//                    res.setTags(question.getTags());
//                    res.setTitle(highlighter.getBestFragment(new HanLPAnalyzer(), "title", doc.get("title")));
//                    res.setUser(question.getUser());
//                    List<FeedbackForQuestion> feedback = feedbackQuestionDao.findFeedback(question.getId());
//                    int like = 0, dislike = 0, mark = 0, likeFlag = 0, markFlag = 0;
//                    for (int j = 0; j < feedback.size(); j++) {
//                        if (feedback.get(j).getLike() == -1) {
//                            if (feedback.get(j).getUserId() == uid) likeFlag = -1;
//                            dislike++;
//                        } else if (feedback.get(j).getLike() == 1) {
//                            if (feedback.get(j).getUserId() == uid) likeFlag = 1;
//                            like++;
//                        }
//                        if (feedback.get(j).getBookmark() == 1) {
//                            if (feedback.get(j).getUserId() == uid) markFlag = 1;
//                            mark++;
//                        }
//                    }
//                    res.setLike(like);
//                    res.setDislike(dislike);
//                    res.setMark(mark);
//                    res.setLikeFlag(likeFlag);
//                    res.setMarkFlag(markFlag);
//                    searchList.add(res);
//
//                }
//            }
//
//            if(topDocs.totalHits<10 || sentence == "") {
////                termList = HanLP.segment(keyWord.toLowerCase());
////                System.out.println(termList);
////                //遍历分词结果
////                for (Term term : termList) {
////                    String word = term.toString().substring(0, term.length());      //词
////                    String nature = term.toString().substring(term.length() + 1);   //词性
////                    if (nature.contains("n") || nature.contains("g") || nature.contains("m")) {
////                        keywordList.add(word);
////                    }
////                }
//
//                List<Question> questions = new ArrayList<>(questionDao.listQuestions());
//
//                List<Map.Entry<Integer, Float>> entryList = this.docVectorModel.nearest(keyWord);
//                for (Map.Entry<Integer, Float> entry : entryList)
//                {
//                    if(entry.getValue()>0.7) {
//                        Question question = questionDao.getQuestion(entry.getKey());
//                        System.out.println(entry.getValue());
//                        QuestionJSON res = new QuestionJSON();
//
//                        System.out.println(docVectorModel.similarity(keyWord, question.getTitle()));
//                        System.out.println(question.getTitle());
//
//                        res.setId(question.getId());
//                        res.setContent(question.getContent());
//                        res.setCreateTime(question.getCreateTime());
//                        res.setTags(question.getTags());
//                        res.setTitle(question.getTitle());
//                        res.setUser(question.getUser());
//                        List<FeedbackForQuestion> feedback = feedbackQuestionDao.findFeedback(question.getId());
//                        int like = 0, dislike = 0, mark = 0, likeFlag = 0, markFlag = 0;
//                        for (int j = 0; j < feedback.size(); j++) {
//                            if (feedback.get(j).getLike() == -1) {
//                                if (feedback.get(j).getUserId() == uid) likeFlag = -1;
//                                dislike++;
//                            } else if (feedback.get(j).getLike() == 1) {
//                                if (feedback.get(j).getUserId() == uid) likeFlag = 1;
//                                like++;
//                            }
//                            if (feedback.get(j).getBookmark() == 1) {
//                                if (feedback.get(j).getUserId() == uid) markFlag = 1;
//                                mark++;
//                            }
//                        }
//                        res.setLike(like);
//                        res.setDislike(dislike);
//                        res.setMark(mark);
//                        res.setLikeFlag(likeFlag);
//                        res.setMarkFlag(markFlag);
//                        System.out.println(searchList.size());
//                        if (!searchList.contains(res)) {
//                            System.out.println(searchList.size());
//                            searchList.add(res);
//                        }
//                    }
//                }
//            }
//            return searchList;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("全文檢索出错：" + e.getMessage());
//        } finally {
//            if (indexReader != null) {
//                try {
//                    indexReader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (directory != null) {
//                try {
//                    directory.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

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

    @Override
    public List<QuestionJSON> fullTextSearch(String keyWord, int uid) throws IOException {
        if (searchCache.containsKey(keyWord)) {
            return searchCache.get(keyWord);
        }

        List<QuestionJSON> searchList = new ArrayList<>(10);
        File indexFile = new File("src/main/resources/indexLibrary");
        File[] files = indexFile.listFiles();
        // 没有索引文件，没有查询结果
        if (files == null || files.length == 0) {
            return searchList;
        }

        try (Analyzer analyzer = new HanLPAnalyzer();
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
                    res.setTitle(highlighter.getBestFragment(new HanLPAnalyzer(), "title", doc.get("title")));
                    searchList.add(res);
                }
            }

            if (topDocs.totalHits < 10 || StringUtils.isEmpty(sentence)) {

                List<Callable<Void>> tasks = new ArrayList<>();
                for (Map.Entry<Integer, Float> entry : this.docVectorModel.nearest(keyWord)) {
                    if (entry.getValue() > 0.7) {
                        Callable<Void> task = () -> {
                            Question question = questionDao.getQuestion(entry.getKey());
                            System.out.println(entry.getValue());
                            System.out.println(question.getTitle());

                            QuestionJSON res = questionToQuestionJSON(question, uid, false);
                            if (!searchList.contains(res)) {
                                searchList.add(res);
                            }
                            return null;
                        };
                        tasks.add(task);
                    }
                }

                try {
                    List<Future<Void>> results = executorService.invokeAll(tasks);
                    for (Future<Void> result : results) {
                        result.get(); // 等待任务执行完成
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            // 将结果添加到缓存
            searchCache.put(keyWord, searchList);

            return searchList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("全文检索出错：" + e.getMessage());
        }
    }

}
