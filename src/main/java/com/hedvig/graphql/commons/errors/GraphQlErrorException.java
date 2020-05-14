package com.hedvig.graphql.commons.errors;

import graphql.ErrorClassification;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.List;
import java.util.Map;

public class GraphQlErrorException extends RuntimeException implements GraphQLError {

    private final String errorMessage;
    private final ErrorType errorType;
    private final Map<String, Object> extextions;

    protected GraphQlErrorException(String errorMessage, ErrorType errorType, Map<String, Object> extensions) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.errorType = errorType;
        this.extextions = extensions;
    }


    @Override
    public String getMessage() {
        return errorMessage;
    }

    public List<SourceLocation> getLocations() {
        return null;
    }


    public ErrorClassification getErrorType() {
        return errorType;
    }


    public Map<String, Object> getExtensions() {
        return extextions;
    }
}

