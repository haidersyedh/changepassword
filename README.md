Summary:

This is a program which verifies whether the user should be able to proceed with password reset. It checks for the below constaints:

1. Old password should match with system
2. New password should be a valid password
3. password is not similar to old password < 80% match.

Password requirement
1. At least 18 alphanumeric characters and list of special chars !@#$&*
2. At least 1 Upper case, 1 lower case ,least 1 numeric, 1 special character
3. No duplicate repeat characters more than 4
4. No more than 4 special characters
5. 50 % of password should not be a number

Important Note: Old Password has been implemented as a mock as of now.

Set Up Instructions

1. Download the project from Git
2. Run mvn clean install to generate JAR
3. Add this JAR to your project dependency

Supported Test Frameworks:
1. TestNG

Dependencies:
1. log4j

Method Signature:

The method returns true if all the conditions are satisfied. Returns false if any of the condition fails.

     * @param oldPassword This is the old password
     * @param newPassword This is the new password
     * @return boolean returns true if the requirements are met, otherwise false.
     */
    public boolean changePassword(String oldPassword, String newPassword)
   


