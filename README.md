# SecureStorageApp - devsec4

This Android application demonstrates different methods for storing data securely and managing files using modern Android APIs.

## Features

- Internal Storage: Saving private application data.
- External Storage: Managing files in public directories (pre-Android 10) and app-specific directories.
- Scoped Storage: Using MediaStore for shared media content.
- Encrypted Database: Room persistence with SQLCipher for full database encryption.
- Encrypted Files: Using Jetpack Security (Tink) to encrypt and decrypt sensitive files.
- User Authentication: Secure password hashing before database storage.

## Project Structure

- InternalStorageFragment: Demonstrates standard private storage.
- ExternalStorageFragment: Demonstrates storage on the SD card.
- ScopedStorageFragment: Demonstrates the use of MediaStore API.
- DatabaseFragment: Demonstrates encrypted user registration and notes management.
- EncryptedFileFragment: Demonstrates file encryption with MasterKeys.

## Setup

1. Clone the repository.
2. Open with Android Studio (Ladybug or newer recommended).
3. Sync Gradle.
4. Run on an emulator or physical device (API 24+).

## Demo Video


https://github.com/user-attachments/assets/91eab744-85d2-4f3f-9ae2-317171e3ec62



---
Realise par : CHARRAJ Mouad (Zero-XR7)

