package com.brocode.apply.buissness.service;

import com.brocode.apply.buissness.model.Position;
import lombok.NonNull;

import java.text.MessageFormat;

public class ResourceNotFoundException extends RuntimeException {
    private static final String CANNOT_FIND_ENTITY_MESSAGE = "[Cannot find entity]: Service: {0} was trying to accces entity of class:  {1} with id {2} but there was no such entity persited yet!";;

    public ResourceNotFoundException(Class<? extends ExperienceController> aClass, Class<Position> positionClass, @NonNull Long id) {

        super(MessageFormat.format(CANNOT_FIND_ENTITY_MESSAGE, aClass.getName(), positionClass.getName(), id));
    }
}
