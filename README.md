# Global Payment Assessment

Uses Java 8, Spring Boot, and Lombok annotations.

Does CRUD operations on Entity Device

Entity Device has 3 fields
- Serial number
- Machine code
- Device name

Serial Number and Machine code together make composite key.

Serial Number has regex validation [A-Ba-b0-9]*-[A-Ba-b0-9]* .

Machine Code cannot be blank. 

The Postman collection to drive the REST API is at 
https://www.getpostman.com/collections/2f34aa74cb66d983d89f
 