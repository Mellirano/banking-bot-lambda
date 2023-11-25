package ua.udunt.lex.support;

import ua.udunt.lex.exception.IllegalSlotException;

@FunctionalInterface
public interface LexProcessor<T, R> {

    R process(T slots) throws IllegalSlotException;

}
