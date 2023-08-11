package com.example.qa_backend.Serviceimpl;

import com.example.qa_backend.Dao.*;
import com.example.qa_backend.Entity.*;
import com.example.qa_backend.JSON.AnswerJSON;
import com.example.qa_backend.JSON.QuestionJSON;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class FeedbackServiceimplTest {
    private FeedbackServiceimpl feedbackServiceimpl;
    @Mock
    private FeedbackQuestionDao feedbackQuestionDao;
    @Mock
    private FeedbackAnswerDao feedbackAnswerDao;
    @Mock
    private FollowDao followDao;
    @Mock
    private QuestionDao questionDao;
    @Mock
    private AnswerDao answerDao;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        feedbackServiceimpl = new FeedbackServiceimpl();
        feedbackServiceimpl.feedbackQuestionDao = feedbackQuestionDao;
        feedbackServiceimpl.feedbackAnswerDao = feedbackAnswerDao;
        feedbackServiceimpl.followDao = followDao;
        feedbackServiceimpl.questionDao = questionDao;
        feedbackServiceimpl.answerDao = answerDao;
    }

    @AfterEach
    void tearDown() {
        this.feedbackServiceimpl = null;
    }

    @Test
    void changeQuestionFeedback() {
        FeedbackForQuestion expectedFeedbackForQuestion = new FeedbackForQuestion();
        expectedFeedbackForQuestion.setUserId(1);
        expectedFeedbackForQuestion.setQuesId(1);
        expectedFeedbackForQuestion.setId(1);
        expectedFeedbackForQuestion.setLike(0);
        expectedFeedbackForQuestion.setBookmark(0);
        when(feedbackQuestionDao.findSpecific(1,1)).thenReturn(expectedFeedbackForQuestion);

        FeedbackForQuestion expectedLike = new FeedbackForQuestion();
        expectedLike.setUserId(1);
        expectedLike.setQuesId(1);
        expectedLike.setId(1);
        expectedLike.setLike(1);
        expectedLike.setBookmark(0);
        when(feedbackQuestionDao.addOne(expectedLike)).thenReturn(expectedLike);

        FeedbackForQuestion expectedDislike = new FeedbackForQuestion();
        expectedDislike.setUserId(1);
        expectedDislike.setQuesId(1);
        expectedDislike.setId(1);
        expectedDislike.setLike(-1);
        expectedDislike.setBookmark(0);
        when(feedbackQuestionDao.addOne(expectedDislike)).thenReturn(expectedDislike);

        FeedbackForQuestion expectedNoLike = new FeedbackForQuestion();
        expectedNoLike.setUserId(1);
        expectedNoLike.setQuesId(1);
        expectedNoLike.setId(1);
        expectedNoLike.setLike(0);
        expectedNoLike.setBookmark(0);
        when(feedbackQuestionDao.addOne(expectedNoLike)).thenReturn(expectedNoLike);

        FeedbackForQuestion expectedStar = new FeedbackForQuestion();
        expectedStar.setUserId(1);
        expectedStar.setQuesId(1);
        expectedStar.setId(1);
        expectedStar.setLike(0);
        expectedStar.setBookmark(1);
        when(feedbackQuestionDao.addOne(expectedStar)).thenReturn(expectedStar);

        FeedbackForQuestion expectedUnstar = new FeedbackForQuestion();
        expectedUnstar.setUserId(1);
        expectedUnstar.setQuesId(1);
        expectedUnstar.setId(1);
        expectedUnstar.setLike(0);
        expectedUnstar.setBookmark(0);
        when(feedbackQuestionDao.addOne(expectedUnstar)).thenReturn(expectedUnstar);

        doNothing().when(feedbackQuestionDao).deleteSpecific(1, 1);

        Question expectedQuestion = new Question();
        expectedQuestion.setId(1);
        expectedQuestion.setContent("content");
        expectedQuestion.setCreateTime("2023-7-10 11:14:03");
        List<Tag> tagList = new ArrayList<>();
        expectedQuestion.setTags(tagList);
        expectedQuestion.setTitle("title");
        User expectedUser = new User();
        expectedUser.setId(1);
        expectedQuestion.setUser(expectedUser);
        when(questionDao.getQuestion(1)).thenReturn(expectedQuestion);

        List<FeedbackForQuestion> expectedFeedbackList = new ArrayList<>();
        FeedbackForQuestion feedback1 = new FeedbackForQuestion();
        feedback1.setLike(1);
        feedback1.setBookmark(1);
        feedback1.setUserId(1);
        expectedFeedbackList.add(feedback1);
        FeedbackForQuestion feedback2 = new FeedbackForQuestion();
        feedback2.setLike(1);
        feedback2.setBookmark(0);
        feedback2.setUserId(2);
        expectedFeedbackList.add(feedback2);
        FeedbackForQuestion feedback3 = new FeedbackForQuestion();
        feedback3.setLike(1);
        feedback3.setBookmark(0);
        feedback3.setUserId(3);
        expectedFeedbackList.add(feedback3);
        when(feedbackQuestionDao.findFeedback(1)).thenReturn(expectedFeedbackList);

        QuestionJSON questionJSON1 = feedbackServiceimpl.changeQuestionFeedback(1, 1, 1);
        expectedFeedbackForQuestion.setLike(0);
        QuestionJSON questionJSON2 = feedbackServiceimpl.changeQuestionFeedback(1, 1, 2);

        assertEquals(3, questionJSON1.getLike());
        assertEquals(1, questionJSON2.getMark());
    }

    @Test
    void changeAnswerFeedback() {
        FeedbackForAnswer expectedFeedbackForAnswer = new FeedbackForAnswer();
        expectedFeedbackForAnswer.setId(1);
        expectedFeedbackForAnswer.setUserId(1);
        expectedFeedbackForAnswer.setAnsId(1);
        expectedFeedbackForAnswer.setLike(0);
        when(feedbackAnswerDao.findSpecific(1,1)).thenReturn(expectedFeedbackForAnswer);

        FeedbackForAnswer expectedLike = new FeedbackForAnswer();
        expectedLike.setId(1);
        expectedLike.setAnsId(1);
        expectedLike.setUserId(1);
        expectedLike.setLike(1);
        when(feedbackAnswerDao.addOne(expectedLike)).thenReturn(expectedLike);

        doNothing().when(feedbackAnswerDao).deleteSpecific(1, 1);

        Answer expectedAnswer = new Answer();
        expectedAnswer.setId(1);
        expectedAnswer.setContent("content");
        Question expectedQuestion = new Question();
        expectedAnswer.setQuestion(expectedQuestion);
        User expectedUser = new User();
        expectedAnswer.setUser(expectedUser);
        expectedAnswer.setCreateTime("2023:07:10 14:54:12");
        when(answerDao.findAnswer(1)).thenReturn(expectedAnswer);

        List<FeedbackForAnswer> expectedFeedbackList = new ArrayList<>();
        FeedbackForAnswer feedback1 = new FeedbackForAnswer();
        feedback1.setId(1);
        feedback1.setAnsId(1);
        feedback1.setUserId(1);
        feedback1.setLike(1);
        expectedFeedbackList.add(feedback1);
        FeedbackForAnswer feedback2 = new FeedbackForAnswer();
        feedback2.setId(2);
        feedback2.setAnsId(1);
        feedback2.setUserId(2);
        feedback2.setLike(1);
        expectedFeedbackList.add(feedback2);
        FeedbackForAnswer feedback3 = new FeedbackForAnswer();
        feedback3.setId(3);
        feedback3.setAnsId(1);
        feedback3.setUserId(3);
        feedback3.setLike(0);
        expectedFeedbackList.add(feedback3);
        when(feedbackAnswerDao.findFeedback(1)).thenReturn(expectedFeedbackList);

        Follow expectedFollow = new Follow();
        when(followDao.check(1, 1)).thenReturn(expectedFollow);

        AnswerJSON answerJSON = feedbackServiceimpl.changeAnswerFeedback(1, 1, 1);

        assertEquals(2, answerJSON.getLike());
    }

    @Test
    void changeFollow() {
        doNothing().when(followDao).addOne(any(Follow.class));
        doNothing().when(followDao).deleteOne(1, 2);
        int follow = feedbackServiceimpl.changeFollow(1, 2, 1);
        int unfollow = feedbackServiceimpl.changeFollow(1, 2, -1);
        assertEquals(1, follow);
        assertEquals(-1, unfollow);
    }
}