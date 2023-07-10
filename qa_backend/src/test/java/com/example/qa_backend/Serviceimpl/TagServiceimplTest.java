package com.example.qa_backend.Serviceimpl;

import com.example.qa_backend.Dao.TagDao;
import com.example.qa_backend.Entity.Question;
import com.example.qa_backend.Entity.Tag;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TagServiceimplTest {

    private TagServiceimpl tagServiceimpl;
    @Mock
    private TagDao tagDao;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        tagServiceimpl = new TagServiceimpl();
        tagServiceimpl.tagDao = tagDao;
    }

    @AfterEach
    void tearDown() {
        this.tagServiceimpl = null;
    }

    @Test
    void listTags() {
        List<Tag> expectedTagList = new ArrayList<>();
        Tag tag1 = new Tag();
        tag1.setId(1);
        tag1.setContent("content1");
        List<Question> questions = new ArrayList<>();
        Question question1 = new Question();
        questions.add(question1);
        tag1.setQuestions(questions);
        expectedTagList.add(tag1);
        Tag tag2 = new Tag();
        tag2.setId(1);
        tag2.setContent("content2");
        tag2.setQuestions(questions);
        expectedTagList.add(tag2);
        when(tagDao.listTag()).thenReturn(expectedTagList);

        List<Tag> tagList = tagServiceimpl.listTags();

        assertEquals(2, tagList.size());
    }
}