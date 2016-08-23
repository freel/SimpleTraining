package com.freel.simpletraining;

import java.util.Objects;

/**
 * Created by user on 22.08.2016.
 */
public class StringWithId {
    public String string;
    public int id;

    public StringWithId(String stringPart, int idPart){
        string = stringPart;
        id = idPart;
    }

    @Override
    public String toString(){
        return string;
    }
}
