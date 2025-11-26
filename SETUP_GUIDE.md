# Setup Guide - Star Wars App

## Quick Start

### Prerequisites
- **Android Studio**: Hedgehog (2023.1.1) or later
- **JDK**: 11 or higher
- **Xcode**: 15.0 or later (for iOS, macOS only)
- **Android SDK**: API 35
- **Minimum Android SDK**: 24

## Step-by-Step Setup

### 1. Clone/Download the Project
```bash
# If using git
git clone <repository-url>
cd StarWarsApp

# Or extract the project folder
```

### 2. Android Setup

1. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an Existing Project"
   - Navigate to the project folder and select it

2. **Sync Gradle**
   - Android Studio should automatically prompt to sync
   - If not, go to: `File → Sync Project with Gradle Files`
   - Wait for Gradle sync to complete

3. **Select Build Variant**
   - Open `Build Variants` panel (View → Tool Windows → Build Variants)
   - Select one of:
     - `blueDebug` - Blue theme, debug build
     - `blueRelease` - Blue theme, release build
     - `redDebug` - Red theme, debug build
     - `redRelease` - Red theme, release build

4. **Run the App**
   - Create an Android Virtual Device (AVD) or connect a physical device
   - Click the "Run" button (green play icon) or press `Shift+F10`
   - The app will build and launch on your device/emulator

### 3. iOS Setup (macOS Required)

#### Option A: Using Xcode (Recommended for Development)

1. **Build the Shared Framework**
   ```bash
   cd /path/to/StarWarsApp
   ./gradlew :shared:build
   ```

2. **Open Xcode Project**
   - Open `iosApp/iosApp.xcodeproj` in Xcode
   - Wait for Xcode to index the project

3. **Configure Framework Linking**
   - The shared Kotlin framework needs to be linked
   - In Xcode, select the project in the navigator
   - Go to the target's "General" tab
   - Under "Frameworks, Libraries, and Embedded Content", add the shared framework
   - The framework should be located in: `shared/build/xcode-frameworks/`

4. **Select Target Device**
   - Choose an iOS Simulator (e.g., iPhone 15) or a connected device
   - Ensure the deployment target is iOS 17.0 or later

5. **Build and Run**
   - Press `⌘R` or click the Run button
   - The app will build and launch

#### Option B: Using Gradle (Alternative)

1. **Build iOS Framework**
   ```bash
   ./gradlew :shared:embedAndSignAppleFrameworkForXcode
   ```

2. **Open in Xcode and follow steps 3-5 from Option A**

### 4. Testing the App

#### Android
1. Launch the app
2. Enter a search term in the search field (e.g., "an", "luke", "vader")
3. Tap "Search"
4. Wait for results to load
5. Tap on any character to see their home planet details
6. Close the planet details dialog

#### iOS
1. Launch the app in simulator or device
2. Enter a search term
3. Tap "Search"
4. View results and tap characters to see planet details

### Common Issues and Solutions

#### Android Build Fails
- **Issue**: Gradle sync fails
- **Solution**: 
  - Check JDK version: `File → Project Structure → SDK Location`
  - Ensure JDK 11+ is selected
  - Invalidate caches: `File → Invalidate Caches...`

#### Android: "Cannot resolve symbol"
- **Solution**: 
  - Clean project: `Build → Clean Project`
  - Rebuild: `Build → Rebuild Project`
  - Sync Gradle again

#### iOS: Framework Not Found
- **Solution**:
  - Build the shared module first: `./gradlew :shared:build`
  - Ensure the framework path is correct in Xcode project settings
  - Clean build folder in Xcode: `Product → Clean Build Folder`

#### iOS: Swift Compilation Errors
- **Solution**:
  - Ensure the shared framework is built and linked
  - Check that iOS deployment target is 17.0+
  - Try cleaning and rebuilding

#### Network Errors
- **Solution**:
  - Ensure device/emulator has internet connection
  - Check that the SWAPI is accessible: https://swapi.dev/api/people/
  - Verify AndroidManifest.xml has INTERNET permission (already included)

#### Empty Search Results
- **Note**: This is expected if the search term doesn't match any characters
- Try searching for: "an", "luke", "leia", "vader", "han"

### Building Release Versions

#### Android Release APK
```bash
./gradlew :androidApp:assembleBlueRelease
./gradlew :androidApp:assembleRedRelease
```
APK will be in: `androidApp/build/outputs/apk/red/release/` or `blue/release/`

#### iOS Release
1. In Xcode, select "Any iOS Device" or a specific device
2. Go to `Product → Archive`
3. Follow the archive and distribution process

### Project Structure Overview

```
StarWarsApp/
├── shared/                    # Shared Kotlin Multiplatform code
│   └── src/commonMain/kotlin/
│       ├── data/              # API client and models
│       └── presentation/      # ViewModel and state management
├── androidApp/                # Android application
│   └── src/main/
│       ├── java/              # Android-specific code
│       └── res/               # Android resources
└── iosApp/                    # iOS application
    └── iosApp/                # SwiftUI code
```

### Next Steps

1. **Customize Colors**: Edit `androidApp/src/main/java/.../ui/theme/Color.kt` for Android
2. **Modify UI**: 
   - Android: Edit Compose files in `androidApp/src/main/java/.../ui/`
   - iOS: Edit SwiftUI files in `iosApp/iosApp/`
3. **Add Features**: Extend the ViewModel in `shared/src/commonMain/kotlin/.../presentation/`

### Support

If you encounter issues:
1. Check the error messages carefully
2. Ensure all prerequisites are installed
3. Try cleaning and rebuilding
4. Verify internet connection for API calls
5. Check that SWAPI is accessible

Good luck with your assessment! 🚀


