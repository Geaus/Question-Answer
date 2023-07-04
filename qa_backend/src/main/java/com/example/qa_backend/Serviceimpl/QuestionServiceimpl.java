package com.example.qa_backend.Serviceimpl;

import com.example.qa_backend.Dao.*;
import com.example.qa_backend.Entity.*;
import com.example.qa_backend.JSON.QuestionJSON;
import com.example.qa_backend.Service.QuestionService;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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

    @Override
    public List<QuestionJSON> listQuestions(int uid) {
        List<Question> ques = questionDao.listQuestions();
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
    public QuestionJSON findQuestion(int uid, int id) {
        Question question = questionDao.getQuestion(id);
        QuestionJSON res = new QuestionJSON();
        res.setId(question.getId());
        res.setContent(question.getContent());
        res.setCreateTime(question.getCreateTime());
        res.setTags(question.getTags());
        res.setTitle(question.getTitle());
        res.setUser(question.getUser());
        List<FeedbackForQuestion> feedback = feedbackQuestionDao.findFeedback(id);
        int like = 0, dislike = 0, mark = 0, likeFlag = 0, markFlag = 0;
        for(int i = 0; i < feedback.size(); i++) {
            if(feedback.get(i).getLike() == -1){
                if(feedback.get(i).getUserId() == uid)likeFlag = -1;
                dislike++;
            }
            else if(feedback.get(i).getLike() == 1){
                if(feedback.get(i).getUserId() == uid)likeFlag = 1;
                like++;
            }
            if(feedback.get(i).getBookmark() == 1){
                if(feedback.get(i).getUserId() == uid)markFlag = 1;
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
    public Question askQuestion(int userId, String content, String title, List<Tag> tags) {
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

        List<Term> termList = HanLP.segment(title.toLowerCase());
        System.out.println(termList);
        //遍历分词结果
        for (Term term : termList) {
            String word = term.toString().substring(0, term.length());      //词
            String nature = term.toString().substring(term.length() + 1);   //词性

            if (nature.contains("n")) {
                System.out.println(nature);
                KeywordEntity keywordEntity = new KeywordEntity();
                keywordEntity.setKeyword(word);
                keywordEntity.setQuestionId(ques_id);
                keywordDao.addOne(keywordEntity);
            }
        }

        return question;
    }

    @Override
    public List<Question> listAsked(int userId) {
        return questionDao.getAsked(userDao.findUser(userId));
    }

    @Override
    public List<Question> getLiked(int userId) {
        List<FeedbackForQuestion> feedback = feedbackQuestionDao.listRelatedQuestion(userId);
        List<Question> res = new ArrayList<>();
        for(int i = 0; i < feedback.size(); i++) {
            if(feedback.get(i).getLike() != 1)continue;
            res.add(questionDao.getQuestion(feedback.get(i).getQuesId()));
        }
        return res;
    }

    @Override
    public List<Question> getDisliked(int userId) {
        List<FeedbackForQuestion> feedback = feedbackQuestionDao.listRelatedQuestion(userId);
        List<Question> res = new ArrayList<>();
        for(int i = 0; i < feedback.size(); i++) {
            if(feedback.get(i).getLike() != -1)continue;
            res.add(questionDao.getQuestion(feedback.get(i).getQuesId()));
        }
        return res;
    }

    @Override
    public List<Question> getMarked(int userId) {
        List<FeedbackForQuestion> feedback = feedbackQuestionDao.listRelatedQuestion(userId);
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
            System.out.println(keywordEntity.getQuestionId());
            Question question = questionDao.getQuestion(keywordEntity.getQuestionId());

            if (!allQuestions.contains(question)) {
                allQuestions.add(question);
            }
        }

        Map<Question, Double> questionSimilarities = new HashMap<>();

        for (Question question : allQuestions) {
//            List<String> questionKeywords = getSplitWords(question.getTitle());
//            System.out.println(questionKeywords);
            if(question == null){
                List<QuestionJSON> resList = new ArrayList<>();
                return resList;
            }
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
}
