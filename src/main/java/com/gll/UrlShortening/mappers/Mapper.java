package com.gll.UrlShortening.mappers;

public interface Mapper<A, B> {

    B mapFrom(A a);
    A mapTo(B b);
}
