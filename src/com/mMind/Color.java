package com.mMind;

public enum Color {
    BLEU ("bleu",0),
    ROUGE ("rouge",1),
    VERT("vert",2),
    VIOLET("violet",3),
    ROSE("rose",4),
    BLANC("blanc",5);

    private final String string;
    private final int id;

    Color(String s, int i){
        this.string = s;
        this.id = i;
    }

    public final String string() {return string;}
    public final int id() {return id;}

}
