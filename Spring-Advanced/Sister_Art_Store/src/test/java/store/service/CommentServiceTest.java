package store.service;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import store.error.CategoryNotFoundException;
import store.error.CommentNotFoundException;
import store.model.entity.Category;
import store.model.entity.Comment;
import store.model.service.ArticleServiceModel;
import store.model.service.CategoryServiceModel;
import store.model.service.CommentServiceModel;
import store.repository.CommentRepository;
import store.service.impl.CategoryServiceImpl;
import store.service.impl.CommentServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;
    @InjectMocks
    CommentServiceImpl commentService;
    @Mock
    ModelMapper modelMapper;
    Comment comment;
   CommentServiceModel model;

    @Before
    public void initTests() {
        comment = mock(Comment.class);
        model = mock(CommentServiceModel.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findById_nullInput_throwsIllegalArgumentException() {
        Mockito.when(commentRepository.findById(null))
                .thenThrow(new IllegalArgumentException());

        commentService.findCommentById(null);
    }

    @Test(expected = CommentNotFoundException.class)
    public void findById_invalidId_throwsCategoryNotFoundException() {
        Mockito.when(commentRepository.findById("id"))
                .thenReturn(Optional.empty());

        commentService.findCommentById("id");
    }

    @Test
    public void extractAll_WhenExist_ShouldWork() {
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        Mockito.when(commentRepository.findAll()).thenReturn(comments);
        int result = commentService.findAllComments().size();
        Assert.assertEquals(comments.size(), result);
    }

    @Test
    public void extractAll_WhenNo_ShouldWork() {
        Mockito.when(commentRepository.findAll()).thenReturn(List.of());
        int result = commentRepository.findAll().size();
        Assert.assertEquals(0, result);
    }
}