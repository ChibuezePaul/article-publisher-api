package io.isoft.article.publisher.models.request;

public record RegisterRequest(String firstName, String lastName, String email, String password) {}