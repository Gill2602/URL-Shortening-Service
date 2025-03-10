package com.gll.UrlShortening.mappers;

public interface Mapper <A, B> {

    A mapTo(B b);

    B mapFrom(A a);
}
