package com.test.demo.base;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author NotoChen
 */
public interface BaseEnum<K, V> {

    static <K, V, T extends BaseEnum<K, V>> Stream<T> conversion(Class<T> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants());
    }

    static <K, V, T extends Enum<?> & BaseEnum<K, V>> K key(T t) {
        return t.getKey();
    }

    static <K, V, T extends Enum<?> & BaseEnum<K, V>> V value(T t) {
        return t.getValue();
    }

    static <K, V, T extends Enum<?> & BaseEnum<K, V>> boolean ifKeyExist(Class<T> enumClass, K key) {
        return conversion(enumClass).anyMatch(baseEnum -> baseEnum.getKey().equals(key));
    }

    static <K, V, T extends Enum<?> & BaseEnum<K, V>> boolean ifKeyNotExist(Class<T> enumClass, K key) {
        return conversion(enumClass).noneMatch(baseEnum -> baseEnum.getKey().equals(key));
    }

    static <K, V, T extends Enum<?> & BaseEnum<K, V>> boolean ifValueExist(Class<T> enumClass, V value) {
        return conversion(enumClass).anyMatch(baseEnum -> baseEnum.getValue().equals(value));
    }

    static <K, V, T extends Enum<?> & BaseEnum<K, V>> boolean ifValueNotExist(Class<T> enumClass, V value) {
        return conversion(enumClass).noneMatch(baseEnum -> baseEnum.getValue().equals(value));
    }

    static <K, V, T extends Enum<?> & BaseEnum<K, V>> List<T> getListByEnum(Class<T> enumClass) {
        return Arrays.asList(enumClass.getEnumConstants());
    }

    static <K, V, T extends BaseEnum<K, V>> List<K> getKeysByEnum(Class<T> enumClass) {
        return conversion(enumClass)
                .map(BaseEnum::getKey)
                .collect(Collectors.toList());
    }

    static <K, V, T extends BaseEnum<K, V>> List<V> getValuesByEnum(Class<T> enumClass) {
        return conversion(enumClass)
                .map(BaseEnum::getValue)
                .collect(Collectors.toList());
    }

    static <K, V, T extends BaseEnum<K, V>> Map<K, V> getMapByEnum(Class<T> enumClass) {
        return conversion(enumClass)
                .collect(Collectors.toMap(BaseEnum::getKey, BaseEnum::getValue));
    }

    static <K, V, T extends Enum<?> & BaseEnum<K, V>> T getEnumByKey(Class<T> enumClass, K key) {
        return conversion(enumClass)
                .filter(t -> t.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(""));
    }

    static <K, V, T extends Enum<?> & BaseEnum<K, V>> T getEnumByValue(Class<T> enumClass, V value) {
        return conversion(enumClass)
                .filter(t -> t.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(""));
    }

    static <K, V, T extends Enum<?> & BaseEnum<K, V>> V getValueByKey(Class<T> enumClass, K key) {
        return conversion(enumClass)
                .filter(t -> t.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(""))
                .getValue();
    }

    static <K, V, T extends Enum<?> & BaseEnum<K, V>> K getKeyByValue(Class<T> enumClass, V value) {
        return conversion(enumClass)
                .filter(t -> t.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(""))
                .getKey();
    }

    K getKey();

    V getValue();

}
