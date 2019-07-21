package org.git.tests;

import org.git.password.ResetPassword;
import org.git.testdata.ResetPasswordDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ResetPasswordTest {

    ResetPassword resetPassword = new ResetPassword();

    /**
     * @param testMessage This is the test case name
     * @param oldPassword old password which is passed to changepassword method
     * @param newPassword new password which is passed to changepassword method
     */
    @Test(dataProvider = "InvalidPasswordData", dataProviderClass = ResetPasswordDataProvider.class)
    public void verifyInvalidPasswordFailures(String testMessage, String oldPassword, String newPassword) {
        boolean result = resetPassword.changePassword(oldPassword, newPassword);
        Assert.assertFalse(result, testMessage);
    }

    @Test(dataProvider = "ValidPasswordData", dataProviderClass = ResetPasswordDataProvider.class)
    public void verifyValidPassword(String testMessage, String oldPassword, String newPassword) {
        boolean result = resetPassword.changePassword(oldPassword, newPassword);
        Assert.assertTrue(result, testMessage);
    }
}
