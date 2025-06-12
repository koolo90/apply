package com.brocode.apply.buissness.service;

import com.brocode.apply.buissness.model.Position;
import lombok.NonNull;

import java.text.MessageFormat;

public class ResourceNotFoundException extends RuntimeException {
    private static final String CANNOT_FIND_ENTITY_MESSAGE = "[Cannot find entity]: Service: \"{0}\" was trying to " +
            "accces entity of class:  \"{1}\" with id \"{2}\" but there was no such entity!";

    public ResourceNotFoundException(Class<?> controllerClass, Class<?> entityClass, Object... uniqueIdentifier) {

        super(MessageFormat.format(CANNOT_FIND_ENTITY_MESSAGE, controllerClass.getName(), entityClass.getName(), uniqueIdentifier[0]));
    }
}
