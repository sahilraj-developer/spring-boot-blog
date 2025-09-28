package com.blog.blog.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "blogs")
public class Blog {

    @Id
    private String id;
    private String title;
    private String description;
    private String imageUrl; // image link, empty string if not provided

    public Blog(String title, String description, String imageUrl) {
        this.title = title;
        this.description = description;
        this.imageUrl = (imageUrl != null) ? imageUrl : "";
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
