package com.example.qa_backend.Daoimpl;

import com.example.qa_backend.Dao.QuestionDao;
import com.example.qa_backend.Entity.Question;
import com.example.qa_backend.Entity.User;
import com.example.qa_backend.Repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuestionDaoimpl implements QuestionDao {
    @Autowired
    QuestionRepository questionRepository;
    @Override
    public List<Question> listQuestions(int page_id) {
        return questionRepository.findAll(PageRequest.of(page_id, 10)).toList();
    }

    @Override
    public List<Question> listAll() {
        return questionRepository.findAll();
    }

    @Override
    public Question getQuestion(int id) {
        return questionRepository.findQuestionById(id);
    }

    @Override
    public Question addQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public List<Question> getAsked(int page_id, User user) {
        return questionRepository.findQuestionsByUser(PageRequest.of(page_id, 10), user).toList();
    }

    @Override
    public void deleteQuestion(Question question) {
        questionRepository.delete(question);
    }

}
