package com.github.damian_git_99.backend.utils;

import java.util.List;
import java.util.stream.Collectors;

public interface AbstractConverter<E, D> {

    E toEntity(D dto);

    D toDto(E entity);

    default List<E> toEntities(List<D> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    default List<D> toDtos(List<E> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
