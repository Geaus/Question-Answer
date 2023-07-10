package com.example.qa_backend.Serviceimpl;

import com.example.qa_backend.Dao.*;
import com.example.qa_backend.Entity.*;
import com.example.qa_backend.JSON.AnswerJSON;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class AnswerServiceimplTest {
    private AnswerServiceimpl answerServiceimpl;
    @Mock
    QuestionDao questionDao;
    @Mock
    UserDao userDao;
    @Mock
    AnswerDao answerDao;
    @Mock
    FeedbackAnswerDao feedbackAnswerDao;
    @Mock
    FollowDao followDao;
    private
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        answerServiceimpl = new AnswerServiceimpl();
        answerServiceimpl.questionDao = questionDao;
        answerServiceimpl.userDao = userDao;
        answerServiceimpl.answerDao = answerDao;
        answerServiceimpl.feedbackAnswerDao = feedbackAnswerDao;
        answerServiceimpl.followDao = followDao;
    }

    @AfterEach
    void tearDown() {
        this.answerServiceimpl = null;
    }

    @Test
    void getAnswer() {
        Question expectedQuestion = new Question();
        expectedQuestion.setId(1);
        when(questionDao.getQuestion(1)).thenReturn(expectedQuestion);

        List<Answer> expectedAnswers = new ArrayList<>();

        Answer answer1 = new Answer();
        answer1.setId(1);
        User user2 = new User();
        user2.setId(2);
        answer1.setUser(user2);
        expectedAnswers.add(answer1);

        Answer answer2 = new Answer();
        answer2.setId(2);
        User user3 = new User();
        user3.setId(3);
        answer2.setUser(user3);
        expectedAnswers.add(answer2);

        when(answerDao.findAnswers(expectedQuestion)).thenReturn(expectedAnswers);

        List<FeedbackForAnswer> feedbackForAnswers = new ArrayList<>();
        when(feedbackAnswerDao.findFeedback(1)).thenReturn(feedbackForAnswers);
        when(feedbackAnswerDao.findFeedback(2)).thenReturn(feedbackForAnswers);

        Follow follow12 = new Follow();
        follow12.setId(1);
        follow12.setUser1Id(1);
        follow12.setUser2Id(2);
        Follow follow13 = new Follow();
        follow13.setId(2);
        follow13.setUser1Id(1);
        follow13.setUser2Id(3);
        when(followDao.check(1,2)).thenReturn(follow12);
        when(followDao.check(1,3)).thenReturn(follow13);

        List<AnswerJSON> answerJSONS = answerServiceimpl.getAnswer(1, 1);

        assertEquals(answerJSONS.size(), 2);
        assertEquals(answerJSONS.get(0).getId(), 1);
        assertEquals(answerJSONS.get(1).getId(), 2);
    }

    @Test
    void addAnswer() {
        User expectedUser = new User();
        expectedUser.setId(1);
        when(userDao.findUser(1)).thenReturn(expectedUser);

        Question expectedQuestion = new Question();
        expectedQuestion.setId(1);
        when(questionDao.getQuestion(1)).thenReturn(expectedQuestion);

        Answer expectedAnswer = new Answer();
        expectedAnswer.setContent("content");
        expectedAnswer.setUser(expectedUser);
        expectedAnswer.setQuestion(expectedQuestion);
        when(answerDao.addAnswer(any(Answer.class))).thenReturn(expectedAnswer);

        Answer answer = answerServiceimpl.addAnswer(1, 1, "content");

        assertEquals(answer.getUser().getId(), 1);
        assertEquals(answer.getQuestion().getId(), 1);
        assertEquals(answer.getContent(), "content");
    }

    @Test
    void getAsked() {
        User expectedUser = new User();
        expectedUser.setId(1);
        when(userDao.findUser(1)).thenReturn(expectedUser);

        List<Answer> expectedAnswers = new ArrayList<>();
        Answer answer1 = new Answer();
        answer1.setId(1);
        Answer answer2 = new Answer();
        answer2.setId(2);
        expectedAnswers.add(answer1);
        expectedAnswers.add(answer2);
        when(answerDao.findAnswered(expectedUser)).thenReturn(expectedAnswers);

        List<Answer> answers = answerServiceimpl.getAsked(1);

        assertEquals(answers.size(), 2);
        assertEquals(answers.get(0).getId(), 1);
        assertEquals(answers.get(1).getId(), 2);
    }

    @Test
    void getLiked() {
        List<FeedbackForAnswer> expectedFeedback = new ArrayList<>();
        FeedbackForAnswer feedbackForAnswer1 = new FeedbackForAnswer();
        feedbackForAnswer1.setId(1);
        feedbackForAnswer1.setLike(1);
        feedbackForAnswer1.setUserId(1);
        feedbackForAnswer1.setAnsId(1);
        expectedFeedback.add(feedbackForAnswer1);
        FeedbackForAnswer feedbackForAnswer2 = new FeedbackForAnswer();
        feedbackForAnswer2.setAnsId(2);
        feedbackForAnswer2.setLike(1);
        feedbackForAnswer2.setUserId(1);
        feedbackForAnswer2.setAnsId(2);
        expectedFeedback.add(feedbackForAnswer2);
        when(feedbackAnswerDao.listRelatedAns(1)).thenReturn(expectedFeedback);

        Answer answer1 = new Answer();
        answer1.setId(1);
        when(answerDao.findAnswer(1)).thenReturn(answer1);
        Answer answer2 = new Answer();
        answer2.setId(2);
        when(answerDao.findAnswer(2)).thenReturn(answer2);

        List<Answer> answers = answerServiceimpl.getLiked(1);

        assertEquals(answers.size(), 2);
        assertEquals(answers.get(0).getId(), 1);
        assertEquals(answers.get(1).getId(), 2);
    }

    @Test
    void getDisliked() {
        List<FeedbackForAnswer> expectedFeedback = new ArrayList<>();
        FeedbackForAnswer feedbackForAnswer1 = new FeedbackForAnswer();
        feedbackForAnswer1.setId(1);
        feedbackForAnswer1.setLike(-1);
        feedbackForAnswer1.setUserId(1);
        feedbackForAnswer1.setAnsId(1);
        expectedFeedback.add(feedbackForAnswer1);
        FeedbackForAnswer feedbackForAnswer2 = new FeedbackForAnswer();
        feedbackForAnswer2.setAnsId(2);
        feedbackForAnswer2.setLike(-1);
        feedbackForAnswer2.setUserId(1);
        feedbackForAnswer2.setAnsId(2);
        expectedFeedback.add(feedbackForAnswer2);
        when(feedbackAnswerDao.listRelatedAns(1)).thenReturn(expectedFeedback);

        Answer answer1 = new Answer();
        answer1.setId(1);
        when(answerDao.findAnswer(1)).thenReturn(answer1);
        Answer answer2 = new Answer();
        answer2.setId(2);
        when(answerDao.findAnswer(2)).thenReturn(answer2);

        List<Answer> answers = answerServiceimpl.getDisliked(1);

        assertEquals(answers.size(), 2);
        assertEquals(answers.get(0).getId(), 1);
        assertEquals(answers.get(1).getId(), 2);
    }

    @Test
    void deleteAnswer() {
        doNothing().when(feedbackAnswerDao).deleteByAns(1);
        Answer answer = new Answer();
        answer.setId(1);
        when(answerDao.findAnswer(1)).thenReturn(answer);
        doNothing().when(answerDao).deleteAnswer(answer);
    }
}