package org.git.tests;

import org.git.password.ResetPassword;
import org.git.testdata.ResetPasswordDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ResetPasswordTest {

    ResetPassword resetPassword = new ResetPassword();

    @Test(dataProvider = "InvalidPasswordData",dataProviderClass = ResetPasswordDataProvider.class)
    public void verifyInvalidPasswordFailures(String testMessage, String oldPassword, String newPassword){
        boolean result = resetPassword.changePassword(oldPassword,newPassword);
        Assert.assertFalse(result,testMessage);
    }
}
