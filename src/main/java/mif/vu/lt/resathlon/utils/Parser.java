package mif.vu.lt.resathlon.utils;

import mif.vu.lt.resathlon.models.athletes.Athlete;

public abstract class Parser {
    
    public abstract Athlete parse(String str) throws Exception;
    
}