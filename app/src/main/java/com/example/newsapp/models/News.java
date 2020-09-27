
package com.example.newsapp.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "articles"
})
public class News {
    @JsonProperty("articles")
    private List<Article> articles = null;


    @JsonProperty("articles")
    public List<Article> getArticles() {
        return articles;
    }

    @JsonProperty("articles")
    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }


}
