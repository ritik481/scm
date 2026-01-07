# TODO: Fix CustomOAuth2UserService.java

- [x] Inject UserService into CustomOAuth2UserService
- [x] Implement logic to check if user exists by email
- [x] If user exists, update user details
- [x] If user doesn't exist, create new user with OAuth2 details
- [x] Set provider based on registrationId (GOOGLE or GITHUB)
- [x] Set providerUserId from OAuth2 user attributes
- [x] Set emailVerified to true for OAuth2 users
- [x] Assign default role (ROLE_USER)
- [x] Set a dummy password for OAuth2 users
