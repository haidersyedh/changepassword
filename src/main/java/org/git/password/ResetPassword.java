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
        if (isPasswordSimilar())
            return false;
        logger.info("Password Validation is Successful");
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
        String shorterPassword = newPassword, longerPassword = oldPassword;
        if (newPassword.length() > oldPassword.length()) {
            shorterPassword = oldPassword;
            longerPassword = newPassword;
        }
        if (longerPassword.contains(shorterPassword)) {
            logger.warn("Old/New Password cannot contain same sequence of characters");
            return true;
        }

        int longerPasswordLength = longerPassword.length();
        int lengthDiffInPercent = (longerPassword.length() - shorterPassword.length()) * 100 / longerPassword.length();
        //Checking for too much variation in length differences
        if ((100 - MAX_SIMILARITY_ALLOWED_PCT) < lengthDiffInPercent) {
            logger.info("Too much length difference for passwords to be similar");
            return false;
        }
        int maxAllowedSameChars = (MAX_SIMILARITY_ALLOWED_PCT * longerPassword.length()) / 100;

        for (int i = 0; i < longerPasswordLength - maxAllowedSameChars; i++) {
            String longerPasswordSubStr = longerPassword.substring(i, maxAllowedSameChars + 1 + i);
            if (shorterPassword.contains(longerPasswordSubStr)) {
                logger.warn(longerPasswordSubStr + " is contained in both old and new passwords");
                return true;
            }
        }
        return false;
    }
}
