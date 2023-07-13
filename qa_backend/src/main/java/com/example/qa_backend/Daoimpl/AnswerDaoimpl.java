package com.example.qa_backend.Daoimpl;

import com.example.qa_backend.Dao.AnswerDao;
import com.example.qa_backend.Entity.Answer;
import com.example.qa_backend.Entity.Question;
import com.example.qa_backend.Entity.User;
import com.example.qa_backend.Repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AnswerDaoimpl implements AnswerDao {
    @Autowired
    AnswerRepository answerRepository;

    @Override
    public Answer addAnswer(Answer ans) {
        return answerRepository.save(ans);
    }

    @Override
    public List<Answer> findAnswersByPage(int page_id, Question question) {
        return answerRepository.findAnswersByQuestion(PageRequest.of(page_id, 10), question).toList();
    }

    @Override
    public List<Answer> findAnswers(Question question) {
        return answerRepository.findAnswersByQuestion(question);
    }

    @Override
    public List<Answer> findAnswered(int page_id, User user) {
        return answerRepository.findAnswersByUser(PageRequest.of(page_id, 10), user).toList();
    }

    @Override
    public Answer findAnswer(int id) {
        return answerRepository.findAnswerById(id);
    }

    @Override
    public void deleteAnswer(Answer answer) {
        answerRepository.delete(answer);
    }
}
