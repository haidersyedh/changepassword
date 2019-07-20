package org.git.testdata;

import org.testng.annotations.DataProvider;

public class ResetPasswordDataProvider {
    @DataProvider(name = "InvalidPasswordData")
    public static Object[][] getInvalidPasswordData() {
        return new Object[][]{
                {"Verify old password blank check", "", "thisIsaValidNewPass@01"},
                {"Verify old password null check", null, "thisIsaValidNewPass@01"},
                {"Verify new password blank check", "sampleOlePassword@01", ""},
                {"Verify new password null check", "sampleOlePassword@01", null},
                {"Verify old password mismatch", "sampleOlePassword@01", "thisIsaValidNewPass@01"},
                {"Verify password Length", "sampleOldPassword@01", "thisIsInValid@012"},
                {"Verify one uppercase validation", "sampleOldPassword@01", "thisssinvalidnewpass@01"},
                {"Verify one lowercase validation", "sampleOldPassword@01", "THISISINVALIDNEWPASS@01"},
                {"Verify one special char validation", "sampleOldPassword@01", "thisIsaValidNewPass01"},
                {"Verify repeating characters validation", "sampleOldPassword@01", "thisIsaInVaslidNewPass@01"},
                {"Verify special characters max limit validation", "sampleOldPassword@01", "thisI#aI$Va@lidN*wPass@01"},
                {"Verify space character validation", "sampleOldPassword@01", "thisIaIVa lidN*wPass@01"},
                {"Verify invalid special character validation", "sampleOldPassword@01", "thisIaIVa~lidN*wPass@01"},
                {"Verify digits count validation", "sampleOldPassword@01", "3ahr4rt5#6e3yH98r24"},
        };
    }

    @DataProvider(name = "ValidPasswordData")
    public static Object[][] getValidPasswordData() {
        return new Object[][]{
                {"Verify that valid password is accepted", "sampleOldPassword@01", "thisIsaValidNewPass@01"}
        };
    }
}
