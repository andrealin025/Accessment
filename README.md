# Star Wars App - Kotlin Multiplatform

A white-label Star Wars app built with Kotlin Multiplatform, featuring Android (Jetpack Compose) and iOS (SwiftUI) implementations. The app allows users to search for Star Wars characters and view their home planet details using the [SWAPI (Star Wars API)](https://swapi.dev/).

## Features

- ✅ **Kotlin Multiplatform** - Shared business logic between Android and iOS
- ✅ **Android App** - Built with Jetpack Compose
- ✅ **iOS App** - Built with SwiftUI
- ✅ **Product Flavors** - Two different color schemes (Blue and Red)
- ✅ **Star Wars API Integration** - Search people and view planet details
- ✅ **Pagination Support** - Automatically fetches all matching results across pages
- ✅ **Loading Indicators** - Shows progress during API calls
- ✅ **Error Handling** - Graceful error handling with retry options
- ✅ **Ktor Client** - Modern HTTP client with JSON serialization
- ✅ **Coroutines** - Asynchronous operations with Kotlin Coroutines

## Project Structure

```
StarWarsApp/
├── shared/                          # Shared Kotlin Multiplatform module
│   └── src/commonMain/kotlin/
│       ├── data/
│       │   ├── api/                 # Star Wars API client
│       │   └── model/                # Data models (Person, Planet)
│       └── presentation/
│           └── StarWarsViewModel.kt  # Shared ViewModel
├── androidApp/                      # Android application
│   └── src/main/
│       ├── java/com/starwarsapp/android/
│       │   ├── MainActivity.kt
│       │   └── ui/                   # Compose UI components
│       └── res/                       # Android resources
└── iosApp/                          # iOS application
    └── iosApp/
        ├── iosAppApp.swift
        └── ContentView.swift
```

## Requirements

### Android
- Android Studio Hedgehog (2023.1.1) or later
- JDK 11 or higher
- Android SDK 35
- Minimum SDK: 24

### iOS
- Xcode 15.0 or later
- iOS 17.0 or later
- macOS (for building iOS app)

## Setup Instructions

### Android Setup

1. Open the project in Android Studio
2. Sync Gradle files (File → Sync Project with Gradle Files)
3. Select a build variant:
   - `blueDebug` or `blueRelease` for Blue theme
   - `redDebug` or `redRelease` for Red theme
4. Run the app on an emulator or physical device

### iOS Setup

1. Open Terminal in the project root
2. Build the shared framework:
   ```bash
   ./gradlew :shared:embedAndSignAppleFrameworkForXcode
   ```
3. Open `iosApp/iosApp.xcodeproj` in Xcode
4. Select a target device or simulator
5. Build and run (⌘R)

**Note:** The iOS app requires the shared Kotlin framework to be built first. You may need to configure the Xcode project to link against the shared framework. For a production setup, consider using CocoaPods or Swift Package Manager integration.

## Building the Project

### Android

```bash
# Build debug APK
./gradlew :androidApp:assembleBlueDebug
./gradlew :androidApp:assembleRedDebug

# Build release APK
./gradlew :androidApp:assembleBlueRelease
./gradlew :androidApp:assembleRedRelease
```

### iOS

1. Build the shared framework:
   ```bash
   ./gradlew :shared:build
   ```
2. Open Xcode and build the iOS app

## Product Flavors

The Android app supports two product flavors with different color schemes:

- **Blue** (`blueDebug`, `blueRelease`): Blue-themed app
- **Red** (`redDebug`, `redRelease`): Red-themed app

Each flavor has a different application ID suffix, allowing both to be installed simultaneously on the same device.

## API Integration

The app uses the [Star Wars API (SWAPI)](https://swapi.dev/) to fetch data:

- **People Search**: `GET https://swapi.dev/api/people/?search={term}`
- **Planet Details**: `GET https://swapi.dev/api/planets/{id}/`

The app automatically handles pagination when searching for people, fetching all results across multiple pages.

## Architecture

- **Shared Module**: Contains all business logic, API client, and ViewModel
- **Platform-Specific UI**: 
  - Android: Jetpack Compose
  - iOS: SwiftUI
- **Data Flow**: 
  - UI → ViewModel → API → Response → ViewModel → UI (via StateFlow)

## Key Technologies

- **Kotlin Multiplatform**: Shared code between platforms
- **Jetpack Compose**: Modern Android UI toolkit
- **SwiftUI**: Modern iOS UI framework
- **Ktor**: HTTP client for API calls
- **Kotlinx Serialization**: JSON serialization
- **Coroutines**: Asynchronous programming
- **StateFlow**: Reactive state management

## Edge Cases Handled

- Empty search terms
- Network errors
- API pagination (fetches all pages automatically)
- Missing or null data fields
- Loading states for both people search and planet details
- Error states with retry functionality

## Testing

To test the app:

1. **Android**: Run on an emulator or device, enter a search term (e.g., "an", "luke", "vader")
2. **iOS**: Build and run in Xcode simulator or device

Example search terms:
- "an" - Returns multiple characters
- "luke" - Returns Luke Skywalker
- "vader" - Returns Darth Vader
- "leia" - Returns Leia Organa

## Troubleshooting

### Android Build Issues
- Ensure JDK 11+ is installed and configured
- Sync Gradle files
- Clean and rebuild: `./gradlew clean build`

### iOS Build Issues
- Ensure the shared framework is built first
- Check that Xcode can find the shared framework
- For CocoaPods integration, run `pod install` in the iosApp directory

## License

This project is created as a test assessment for Lecturio.

## Author

Created as part of the Android Developer assessment task.

