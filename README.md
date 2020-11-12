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

