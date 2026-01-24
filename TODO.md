# TODO: Migrate Contact and User Storage to MongoDB Compass

## Steps to Complete Migration

- [x] Update pom.xml: Add MongoDB dependency, remove JPA/SQL dependencies
- [x] Update application.properties: Disable SQL configs, enable MongoDB
- [x] Convert Contact.java to MongoDB annotations (@Document, @DBRef)
- [x] Convert User.java to MongoDB annotations (@Document, @DBRef)
- [x] Convert SocialLink.java to MongoDB annotations (@Document, @DBRef)
- [x] Update ContactRepo.java to extend MongoRepository
- [x] Update UserRepo.java to extend MongoRepository
- [x] Test the application to ensure contacts and users are saved to MongoDB ✅ SUCCESS
