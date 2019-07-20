package org.git.password;

import org.apache.log4j.Logger;
import org.git.constants.PasswordConstants;
import org.git.oldpassword.OldPasswordHelper;
import org.git.util.StringHelper;
import org.git.validator.NewPasswordValidator;

/**
 * <h1>Password Validation</h1>
 * This class has methods related to password reset validation on the basis
 * of which it is determine whether or not to allow the user to reset password.
 *
 * @author Syed Hasnain Haider
 * @version 1.0
 * @since 2019-07-19
 */

public class ResetPassword implements PasswordConstants {

    final static Logger logger = Logger.getLogger(ResetPassword.class);
    private String newPassword;
    private String oldPassword;
    private NewPasswordValidator passwordValidator;

    /**
     * This method accepts an old password and a new password.
     * It validates the below requirements
     * 1. Old password should match with system
     * 2. New password should be a valid password
     * 3. password is not similar to old password < 80% match.
     *
     * @param oldPassword This is the old password
     * @param newPassword This is the new password
     * @return boolean returns true if the requirements are met, otherwise false.
     */
    public boolean changePassword(String oldPassword, String newPassword) {
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
        if (StringHelper.isNullOrEmpty(oldPassword)) {
            logger.warn("Old Password is Null or Blank");
            return false;
        }
        if (StringHelper.isNullOrEmpty(newPassword)) {
            logger.warn("New Password is Null or Blank");
            return false;
        }

        if (!OldPasswordHelper.getOldPassword().equals(oldPassword)) {
            logger.warn("Old Password is Incorrect");
            return false;
        }
        passwordValidator = new NewPasswordValidator(newPassword);
        if (!isNewPasswordValid())
            return false;
        if(isPasswordSimilar())
            return false;
        return true;
    }

    public boolean isNewPasswordValid() {
        return passwordValidator.isNewPasswordLengthy()
                && passwordValidator.newPasswordHasRequiredCharacters()
                && passwordValidator.validateDuplicateCharacterCount()
                && passwordValidator.validateSpecialCharacterCount()
                && passwordValidator.validateDigitsCount()
                && passwordValidator.validateSpecialCharsAndSpaces();


    }
    public boolean isPasswordSimilar() {
        if(oldPassword.equals(newPassword)){
            logger.warn("Old and new passwords are same");
            return true;
        }
        int similarityCounter = 0;
        String shorterPassword = oldPassword, longerPassword = newPassword;
        if(newPassword.length()>oldPassword.length()) {
            shorterPassword = newPassword;
            longerPassword = oldPassword;
        }
        int shorterPasswordLength = shorterPassword.length();
        int longerPasswordLength = longerPassword.length();
        int lengthDiffInPercent = ((longerPassword.length()-shorterPassword.length())/longerPassword.length())*100;
        if((100-MAX_SIMILARITY_ALLOWED_PCT) < lengthDiffInPercent){ //Checking for too much variation in length differences
            logger.info("Too much length difference for passwords to be similar");
            return false;
        }
        int maxAllowedSameChars = (MAX_SIMILARITY_ALLOWED_PCT/100) * longerPassword.length();

        for(int i=0;i<longerPasswordLength;i++){
            if(shorterPassword.contains(longerPassword.substring(i,maxAllowedSameChars+1)))
                return true;
        }
        return false;
    }
}
