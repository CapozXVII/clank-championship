package it.capozxvii.clankchampionship.model.enums;

import lombok.Getter;

@Getter
public enum CharacterEnum {
    YELLOW("MonkeyBot"),
    ORANGE("Whiskers"),
    RED("Agnet"),
    BLUE("Lenara"),
    GREY("Garignar"),
    GREEN("D'Allan");

    private final String name;

    CharacterEnum(final String name) {
        this.name = name;
    }
}
