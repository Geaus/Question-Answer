package com.example.qa_backend.Controller;

import com.example.qa_backend.Entity.Tag;
import com.example.qa_backend.Service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class TagController {
    @Autowired
    TagService tagService;
    @RequestMapping("/listTag")
    @PreAuthorize("@authCheck.authorityCheck(0)")
    public List<Tag> listTag() { return tagService.listTags(); }
}
