package org.git.validator;

import org.apache.log4j.Logger;
import org.git.constants.PasswordConstants;
import org.git.util.StringHelper;

import java.util.Map;
import java.util.Set;

public class NewPasswordValidator implements PasswordConstants {

    final static Logger logger = Logger.getLogger(NewPasswordValidator.class);
    private String newPassword;
    private char[] charPassword;
    private Map<Character, Integer> charFrequencyMap;

    public NewPasswordValidator(String newPassword)
    {
        this.newPassword = newPassword;
        charPassword = newPassword.toCharArray();
        charFrequencyMap = StringHelper.buildCharacterFrequencyMap(newPassword);
    }
    public boolean isNewPasswordLengthy() {
        //Validate password length should not be less than 18 characters
        if (newPassword.length() < MIN_LENGTH_OF_PASSWORD) {
            logger.warn("New Password length is less than 18 characters");
            return false;
        }
        return true;
    }

    public boolean newPasswordHasRequiredCharacters() {
        boolean hasUppercase = false, hasLowercase = false, hasNumeric = false, hasSpecialCharacter = false;

        for (char c : charPassword) {
            if (!hasUppercase && Character.isUpperCase(c)) //Check if password has an uppercase alphabet
                hasUppercase = true;
            if (!hasLowercase && Character.isLowerCase(c)) //Check if password has a lowecase alphabet
                hasLowercase = true;
            if (!hasNumeric && Character.isDigit(c))  //Check if password has a lowercase alphabet
                hasNumeric = true;
            if (!hasSpecialCharacter && SPECIAL_CHARS.contains(Character.toString(c))) //check if password contains special character
                hasSpecialCharacter = true;
        }
        if (!hasUppercase)
            logger.warn("New Password does not have an uppercase alphabet");
        if (!hasLowercase)
            logger.warn("New Password does not have a lowercase alphabet");
        if (!hasNumeric)
            logger.warn("New Password does not have a number");
        if (!hasSpecialCharacter)
            logger.warn("New Password does not have a special character");
        return hasUppercase && hasLowercase && hasNumeric && hasSpecialCharacter;
    }

    public boolean validateDuplicateCharacterCount() {
        Set<Character> charsInPassword = charFrequencyMap.keySet();
        for (char c : charsInPassword) {
            if (charFrequencyMap.get(c) > MAX_REPEATING_CHARS) {
                logger.warn(c + " repeats more than " + MAX_REPEATING_CHARS + " times");
                return false;
            }
        }
        return true;
    }

    public boolean validateSpecialCharacterCount() {
        int specialCharCount = 0;
        Set<Character> charsInPassword = charFrequencyMap.keySet();
        for (char c : charsInPassword) {
            if (SPECIAL_CHARS.contains(Character.toString(c)))
                specialCharCount = specialCharCount + charFrequencyMap.get(c);
            if (specialCharCount > MAX_SPECIAL_CHARS) {
                logger.warn("More than " + MAX_REPEATING_CHARS + " special characters are present");
                return false;
            }
        }
        return true;
    }
    public boolean validateDigitsCount(){
        int numbersCount=0;
        int allowedNumberCount = (newPassword.length() * MAX_DIGITS_ALLOWED_PCT)/100;
        Set<Character> charsInPassword = charFrequencyMap.keySet();
        for (char c : charsInPassword) {
            if (Character.isDigit(c))
                numbersCount = numbersCount + charFrequencyMap.get(c);
            if (numbersCount >= allowedNumberCount) {
                logger.warn("More than " + MAX_DIGITS_ALLOWED_PCT + " percent numerals are present");
                return false;
            }
        }
        return true;
    }
    public boolean validateSpecialCharsAndSpaces(){
        for(char c: charPassword){
            if(!Character.isLetter(c) && !Character.isDigit(c)){
                if(Character.isSpaceChar(c)){
                    logger.warn("New Password Contains a Space");
                    return false;
                }
                else{
                    if(!SPECIAL_CHARS.contains(String.valueOf(c))){
                        logger.warn("Password Contains Invalid Special Character");
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
