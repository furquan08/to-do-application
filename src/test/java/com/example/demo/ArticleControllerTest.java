package com.example.demo;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.furquan.article.entity.ArticleEntity;
import com.furquan.article.repository.ArticleRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ArticleControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleRepository mockRepository;

    /*
        {
            "timestamp":"2019-03-05T09:34:13.280+0000",
            "status":400,
            "errors":["Author is not allowed.","Please provide a price","Please provide a author"]
        }
     ---------
    
    {
    	"title": "",
    	"description": "Ever wonder how?",
    	"body": "You have to believe",
    	"tags": ["java", "Spring Boot", "tutorial"]
    	}
   */ 	
    @Test
    public void testSaveEmptyArticle() throws Exception {

        String bookInJson = "{\"title\":\"\"}";

        mockMvc.perform(post("/api/articles")
                .content(bookInJson)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp", is(notNullValue())))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(3)))
                .andExpect(jsonPath("$.errors", hasItem("Author is not allowed.")))
                .andExpect(jsonPath("$.errors", hasItem("Please provide a author")))
                .andExpect(jsonPath("$.errors", hasItem("Please provide a price")));

        verify(mockRepository, times(0)).save(any(ArticleEntity.class));

    }

    /*
        {
            "timestamp":"2019-03-05T09:34:13.207+0000",
            "status":400,
            "errors":["Author is not allowed."]
        }
        ----
        {
    	"title": "",
    	"description": "Ever wonder how?",
    	"body": "You have to believe"
    	}
     */
    @Test
    public void testSaveInvalidArticle() throws Exception {

        String bookInJson = "{\"title\":\"\", \"description\":\"\",\"body\":\"\"}";

        mockMvc.perform(post("/api/articles")
                .content(bookInJson)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp", is(notNullValue())))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors", hasItem("Author is not allowed.")));

        verify(mockRepository, times(0)).save(any(ArticleEntity.class));

    }

}