package com.gll.UrlShortening.exceptions;

public class InvalidUrlException extends RuntimeException {

  public InvalidUrlException(String message) {
    super(message);
  }
}
