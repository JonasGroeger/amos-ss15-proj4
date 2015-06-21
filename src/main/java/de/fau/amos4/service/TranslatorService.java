package de.fau.amos4.service;

public interface TranslatorService
{
    String translate(String message);

    String translate(String message, Object[] args);
}
