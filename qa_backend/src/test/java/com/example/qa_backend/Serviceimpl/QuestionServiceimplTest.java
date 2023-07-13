package com.example.qa_backend.Serviceimpl;

import com.example.qa_backend.Dao.*;
import com.example.qa_backend.Entity.*;
import com.example.qa_backend.JSON.QuestionJSON;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@Component
class QuestionServiceimplTest {
    private QuestionServiceimpl questionServiceimpl;
    @Mock
    private QuestionDao questionDao;
    @Mock
    private UserDao userDao;
    @Mock
    private TagQuesDao tagQuesDao;
    @Mock
    private FeedbackQuestionDao feedbackQuestionDao;
    @Mock
    private FeedbackAnswerDao feedbackAnswerDao;
    @Mock
    private KeywordDao keywordDao;
    @Mock
    private AnswerDao answerDao;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        questionServiceimpl = new QuestionServiceimpl();
        questionServiceimpl.questionDao = questionDao;
        questionServiceimpl.userDao = userDao;
        questionServiceimpl.tagQuesDao = tagQuesDao;
        questionServiceimpl.feedbackQuestionDao = feedbackQuestionDao;
        questionServiceimpl.feedbackAnswerDao = feedbackAnswerDao;
        questionServiceimpl.keywordDao = keywordDao;
        questionServiceimpl.answerDao = answerDao;
    }

    @AfterEach
    void tearDown() {
        this.questionServiceimpl = null;
    }

    @Test
    void listQuestions() {
        List<Question> expectedQuestionList = new ArrayList<>();
        Question question1 = new Question();
        question1.setId(1);
        Question question2 = new Question();
        question2.setId(2);
        expectedQuestionList.add(question1);
        expectedQuestionList.add(question2);
        when(questionDao.listQuestions(1)).thenReturn(expectedQuestionList);

        List<FeedbackForQuestion> expectedFeedbackList1 = new ArrayList<>();
        FeedbackForQuestion feedback1 = new FeedbackForQuestion();
        FeedbackForQuestion feedback2 = new FeedbackForQuestion();
        feedback1.setId(1);
        feedback1.setUserId(1);
        feedback1.setQuesId(1);
        feedback1.setLike(1);
        feedback1.setBookmark(1);
        expectedFeedbackList1.add(feedback1);
        feedback2.setId(2);
        feedback2.setUserId(2);
        feedback2.setQuesId(1);
        feedback2.setLike(1);
        feedback2.setBookmark(0);
        expectedFeedbackList1.add(feedback2);
        when(feedbackQuestionDao.findFeedback(1)).thenReturn(expectedFeedbackList1);

        List<FeedbackForQuestion> expectedFeedbackList2 = new ArrayList<>();
        FeedbackForQuestion feedback3 = new FeedbackForQuestion();
        FeedbackForQuestion feedback4 = new FeedbackForQuestion();
        feedback3.setId(3);
        feedback3.setUserId(1);
        feedback3.setQuesId(2);
        feedback3.setLike(1);
        feedback3.setBookmark(1);
        expectedFeedbackList1.add(feedback3);
        feedback4.setId(4);
        feedback4.setUserId(2);
        feedback4.setQuesId(2);
        feedback4.setLike(1);
        feedback4.setBookmark(0);
        expectedFeedbackList1.add(feedback4);
        when(feedbackQuestionDao.findFeedback(2)).thenReturn(expectedFeedbackList2);

        List<QuestionJSON> questionJSONS = questionServiceimpl.listQuestions(1,1);

        assertEquals(questionJSONS.size(), 2);
        assertEquals(questionJSONS.get(0).getId(), 1);
        assertEquals(questionJSONS.get(1).getId(), 2);
    }

    @Test
    void findQuestion() {
        Question expectedQuestion = new Question();
        expectedQuestion.setId(1);
        expectedQuestion.setLike(2);
        expectedQuestion.setDislike(0);
        expectedQuestion.setMark(1);
        when(questionDao.getQuestion(1)).thenReturn(expectedQuestion);

        List<FeedbackForQuestion> expectedFeedbackList = new ArrayList<>();
        FeedbackForQuestion feedback1 = new FeedbackForQuestion();
        FeedbackForQuestion feedback2 = new FeedbackForQuestion();
        feedback1.setId(1);
        feedback1.setUserId(1);
        feedback1.setQuesId(1);
        feedback1.setLike(1);
        feedback1.setBookmark(1);
        expectedFeedbackList.add(feedback1);
        feedback2.setId(2);
        feedback2.setUserId(2);
        feedback2.setQuesId(1);
        feedback2.setLike(1);
        feedback2.setBookmark(0);
        expectedFeedbackList.add(feedback2);
        when(feedbackQuestionDao.findFeedback(1)).thenReturn(expectedFeedbackList);

        QuestionJSON questionJSON = questionServiceimpl.findQuestion(1, 1);

        assertEquals(questionJSON.getId(), 1);
        assertEquals(questionJSON.getLike(), 2);
        assertEquals(questionJSON.getDislike(), 0);
        assertEquals(questionJSON.getMark(), 1);
    }

    @Test
    void askQuestion() {
        User expectedUser = new User();
        expectedUser.setId(1);
        when(userDao.findUser(1)).thenReturn(expectedUser);

        Question expectedInputQuestion = new Question();
        expectedInputQuestion.setContent("content");
        expectedInputQuestion.setUser(expectedUser);
        expectedInputQuestion.setTitle("title");
        Question expectedQuestion = new Question();
        expectedQuestion.setId(1);
        expectedQuestion.setContent("content");
        expectedQuestion.setUser(expectedUser);
        expectedQuestion.setTitle("title");
        when(questionDao.addQuestion(expectedInputQuestion)).thenReturn(expectedQuestion);

        when(tagQuesDao.addRelation(any(TagQuesRelation.class))).thenReturn(null);
        when(keywordDao.addOne(any(KeywordEntity.class))).thenReturn(null);

        List<Tag> tags = new ArrayList<>();
        Tag tag1 = new Tag();
        tag1.setId(1);
        Tag tag2 = new Tag();
        tag2.setId(2);
        tags.add(tag1);
        tags.add(tag2);
        try {
            Question question = questionServiceimpl.askQuestion(1, "content", "title", tags);
            assertEquals(question.getId(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void listAsked() {
        User expectedUser = new User();
        expectedUser.setId(1);
        when(userDao.findUser(1)).thenReturn(expectedUser);

        List<Question> expectedQuestions = new ArrayList<>();
        expectedQuestions.add(new Question());
        expectedQuestions.add(new Question());
        when(questionDao.getAsked(1, expectedUser)).thenReturn(expectedQuestions);

        List<Question> questions = questionServiceimpl.listAsked(1,1);

        assertEquals(questions.size(), 2);
    }

    @Test
    void getLiked() {
        List<FeedbackForQuestion> expectedFeedback = new ArrayList<>();
        FeedbackForQuestion feedback1 = new FeedbackForQuestion();
        feedback1.setQuesId(1);
        feedback1.setLike(1);
        expectedFeedback.add(feedback1);
        FeedbackForQuestion feedback2 = new FeedbackForQuestion();
        feedback2.setQuesId(2);
        feedback2.setLike(1);
        expectedFeedback.add(feedback2);
        when(feedbackQuestionDao.listRelatedQuestionLike(1,1)).thenReturn(expectedFeedback);

        Question question1 = new Question();
        question1.setId(1);
        Question question2 = new Question();
        question2.setId(2);
        when(questionDao.getQuestion(1)).thenReturn(question1);
        when(questionDao.getQuestion(2)).thenReturn(question2);

        List<Question> questions = questionServiceimpl.getLiked(1,1);

        assertEquals(2, questions.size());
        assertEquals(1, questions.get(0).getId());
        assertEquals(2, questions.get(1).getId());
    }

    @Test
    void getDisliked() {
        List<FeedbackForQuestion> expectedFeedback = new ArrayList<>();
        FeedbackForQuestion feedback1 = new FeedbackForQuestion();
        feedback1.setQuesId(1);
        feedback1.setLike(-1);
        expectedFeedback.add(feedback1);
        FeedbackForQuestion feedback2 = new FeedbackForQuestion();
        feedback2.setQuesId(2);
        feedback2.setLike(-1);
        expectedFeedback.add(feedback2);
        when(feedbackQuestionDao.listRelatedQuestionDislike(1,1)).thenReturn(expectedFeedback);

        Question question1 = new Question();
        question1.setId(1);
        Question question2 = new Question();
        question2.setId(2);
        when(questionDao.getQuestion(1)).thenReturn(question1);
        when(questionDao.getQuestion(2)).thenReturn(question2);

        List<Question> questions = questionServiceimpl.getDisliked(1,1);

        assertEquals(2, questions.size());
        assertEquals(1, questions.get(0).getId());
        assertEquals(2, questions.get(1).getId());
    }

    @Test
    void getMarked() {
        List<FeedbackForQuestion> expectedFeedback = new ArrayList<>();
        FeedbackForQuestion feedback1 = new FeedbackForQuestion();
        feedback1.setQuesId(1);
        feedback1.setBookmark(1);
        expectedFeedback.add(feedback1);
        FeedbackForQuestion feedback2 = new FeedbackForQuestion();
        feedback2.setQuesId(2);
        feedback2.setBookmark(1);
        expectedFeedback.add(feedback2);
        when(feedbackQuestionDao.listRelatedQuestionMark(1,1)).thenReturn(expectedFeedback);

        Question question1 = new Question();
        question1.setId(1);
        Question question2 = new Question();
        question2.setId(2);
        when(questionDao.getQuestion(1)).thenReturn(question1);
        when(questionDao.getQuestion(2)).thenReturn(question2);

        List<Question> questions = questionServiceimpl.getMarked(1,1);

        assertEquals(2, questions.size());
        assertEquals(1, questions.get(0).getId());
        assertEquals(2, questions.get(1).getId());
    }

    @Test
    void searchByTitle() {
    }

    @Test
    void deleteQuestion() {
        Question question1 = new Question();
        when(questionDao.getQuestion(1)).thenReturn(question1);

        List<Answer> answers = new ArrayList<>();
        Answer answer1 = new Answer();
        answer1.setId(1);
        answers.add(answer1);
        Answer answer2 = new Answer();
        answer2.setId(2);
        answers.add(answer2);
        when(answerDao.findAnswers(question1)).thenReturn(answers);

        doNothing().when(feedbackAnswerDao).deleteByAns(any(int.class));
        doNothing().when(feedbackQuestionDao).deleteByQues(any(int.class));
        doNothing().when(tagQuesDao).deleteRelation(any(int.class));
        doNothing().when(questionDao).deleteQuestion(any(Question.class));
        doNothing().when(keywordDao).deleteKeyword(any(int.class));

        questionServiceimpl.deleteQuestion(1);
    }
}