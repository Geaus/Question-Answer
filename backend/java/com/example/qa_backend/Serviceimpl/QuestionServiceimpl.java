package com.example.qa_backend.Serviceimpl;

import com.example.qa_backend.Dao.QuestionDao;
import com.example.qa_backend.Dao.TagQuesDao;
import com.example.qa_backend.Dao.UserDao;
import com.example.qa_backend.Entity.Question;
import com.example.qa_backend.Entity.Tag;
import com.example.qa_backend.Entity.TagQuesRelation;
import com.example.qa_backend.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class QuestionServiceimpl implements QuestionService {
    @Autowired
    QuestionDao questionDao;
    @Autowired
    UserDao userDao;
    @Autowired
    TagQuesDao tagQuesDao;
    @Override
    public List<Question> listQuestions() {
        return questionDao.listQuestions();
    }

    @Override
    public Question findQuestion(int id) {
        return questionDao.getQuestion(id);
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
        return question;
    }

    @Override
    public List<Question> listAsked(int userId) {
        return questionDao.getAsked(userDao.findUser(userId));
    }
}
