package org.git.oldpassword;

/**
 * <h1>Mock for providing old password</h1>
 * This class acts as a test stub for providing old password for comparison
 *
 * @author Syed Hasnain Haider
 * @version 1.0
 * @since 2019-07-19
 */

public class OldPasswordHelper {
    private static final String oldPassword = "sampleOldPassword@01";

    public static String getOldPassword() {
        return oldPassword;
    }
}

