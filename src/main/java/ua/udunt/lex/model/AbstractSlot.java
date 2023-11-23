package ua.udunt.lex.model;

import ua.udunt.lex.exception.IllegalSlotException;

public abstract class AbstractSlot {

    public abstract void validate() throws IllegalSlotException;

}
