# Testing Guide - Star Wars App

## Quick Test Instructions

### Android Testing (Primary)

#### Step 1: Open Project in Android Studio
1. Launch Android Studio
2. Open the project folder (`D:\Assessment`)
3. Wait for Gradle sync to complete (may take a few minutes on first open)

#### Step 2: Set Up Emulator or Device
**Option A: Android Emulator**
1. Go to `Tools → Device Manager`
2. Click `Create Device`
3. Select a device (e.g., Pixel 7)
4. Select a system image (API 35 recommended)
5. Click `Finish` and wait for download/creation
6. Click the play button next to the emulator to start it

**Option B: Physical Device**
1. Enable Developer Options on your Android phone:
   - Go to `Settings → About Phone`
   - Tap `Build Number` 7 times
2. Enable USB Debugging:
   - Go to `Settings → Developer Options`
   - Enable `USB Debugging`
3. Connect phone via USB
4. Accept the debugging prompt on your phone

#### Step 3: Select Build Variant
1. Open `Build Variants` panel (usually at bottom-left)
2. If not visible: `View → Tool Windows → Build Variants`
3. Under `androidApp`, select:
   - `blueDebug` (for blue theme) OR
   - `redDebug` (for red theme)

#### Step 4: Run the App
1. Click the green `Run` button (▶) or press `Shift + F10`
2. Select your emulator/device when prompted
3. Wait for the app to build and install
4. The app should launch automatically

#### Step 5: Test Basic Functionality

**Test 1: Initial State**
- ✅ App launches without crashing
- ✅ Search input field is visible
- ✅ "Search" button is visible
- ✅ Message shows: "Enter a search term and press Search"

**Test 2: Search Functionality**
1. Type "an" in the search field
2. Click "Search" button
3. ✅ Loading indicator appears (circular progress)
4. ✅ After a few seconds, list of characters appears
5. ✅ List includes characters like: "Anakin Skywalker", "Han Solo", "Lando Calrissian", etc.
6. ✅ List is scrollable

**Test 3: Pagination (Important!)**
1. Search for "an" (this returns multiple pages)
2. ✅ Verify ALL results are shown (should be 12+ characters)
3. ✅ Scroll through the entire list
4. ✅ All characters from all pages are displayed

**Test 4: Planet Details**
1. Tap on any character (e.g., "Anakin Skywalker")
2. ✅ Dialog appears with "Planet Details" title
3. ✅ Loading indicator shows briefly
4. ✅ Planet information displays:
   - Name: (e.g., "Tatooine")
   - Terrain: (e.g., "desert")
   - Gravity: (e.g., "1 standard")
   - Population: (e.g., "200000")
5. ✅ Click "Close" button
6. ✅ Dialog closes

**Test 5: Different Searches**
Try these search terms:
- "luke" → Should find Luke Skywalker
- "vader" → Should find Darth Vader
- "leia" → Should find Leia Organa
- "han" → Should find Han Solo
- "xyz123" → Should show empty list (no results)

**Test 6: Empty Search**
1. Leave search field empty
2. Click "Search"
3. ✅ List should be empty
4. ✅ No error should occur

**Test 7: Error Handling**
1. Turn off WiFi/Mobile data on device/emulator
2. Enter a search term and click "Search"
3. ✅ Error message appears
4. ✅ "Retry" button is visible
5. Turn internet back on
6. Click "Retry"
7. ✅ Search should work again

**Test 8: Product Flavors**
1. Change build variant to `redDebug`
2. Rebuild and run the app
3. ✅ App should have red color scheme
4. Change back to `blueDebug`
5. ✅ App should have blue color scheme
6. ✅ Both can be installed simultaneously (different package names)

### iOS Testing (Bonus - Requires macOS)

#### Step 1: Build Shared Framework
```bash
cd D:\Assessment
./gradlew :shared:build
```

#### Step 2: Open in Xcode
1. Open Xcode
2. File → Open
3. Navigate to `iosApp/iosApp.xcodeproj`
4. Wait for indexing to complete

#### Step 3: Select Target
1. At the top, select a simulator (e.g., iPhone 15)
2. Or connect a physical iOS device

#### Step 4: Build and Run
1. Press `⌘R` or click the Run button
2. Wait for build to complete
3. App launches in simulator/device

#### Step 5: Test iOS App
- Follow similar tests as Android
- Verify SwiftUI interface works
- Test search, list, and planet details

## Automated Testing Checklist

### ✅ Core Requirements Verification

- [ ] App compiles without errors
- [ ] App launches successfully
- [ ] Search input and button are visible
- [ ] Search functionality works
- [ ] Results are displayed in a list
- [ ] List items are clickable
- [ ] Planet details are shown when clicking a character
- [ ] Planet details can be closed
- [ ] Loading indicators appear during API calls
- [ ] Error handling works (test with no internet)
- [ ] Pagination works (all pages fetched)
- [ ] Product flavors work (blue and red)

### ✅ Edge Cases

- [ ] Empty search term handled
- [ ] No results scenario
- [ ] Network errors handled
- [ ] Long lists scroll smoothly
- [ ] Rapid button clicks don't cause issues
- [ ] App doesn't crash on invalid data

### ✅ Performance

- [ ] List scrolls smoothly with many items
- [ ] No lag when loading planet details
- [ ] Loading states are responsive

## Testing Different Scenarios

### Scenario 1: Single Character Search
**Input**: "luke"  
**Expected**: Shows only Luke Skywalker  
**Verify**: Only one result, correct character

### Scenario 2: Multiple Characters Search
**Input**: "an"  
**Expected**: Shows 12+ characters across multiple pages  
**Verify**: All pages fetched, all results shown

### Scenario 3: No Results
**Input**: "nonexistent123"  
**Expected**: Empty list, no error  
**Verify**: Graceful handling

### Scenario 4: Planet Loading
**Input**: Click on "Anakin Skywalker"  
**Expected**: Shows Tatooine details  
**Verify**: All planet fields populated correctly

### Scenario 5: Network Error
**Input**: Disconnect internet, then search  
**Expected**: Error message with retry option  
**Verify**: Error displayed, retry works after reconnecting

## Common Issues and Solutions

### Issue: "Gradle sync failed"
**Solution**:
- Check internet connection
- File → Invalidate Caches → Restart
- Check JDK version (should be 11+)

### Issue: "App crashes on launch"
**Solution**:
- Check logcat for errors
- Ensure minSdk 24 is supported
- Clean and rebuild: `Build → Clean Project`

### Issue: "No results showing"
**Solution**:
- Check internet connection
- Verify SWAPI is accessible: https://swapi.dev/api/people/
- Check logcat for API errors

### Issue: "Loading indicator never stops"
**Solution**:
- Check network connection
- Verify API endpoint is correct
- Check for exceptions in logcat

## Verification Commands

### Check if API is accessible:
Open in browser: https://swapi.dev/api/people/?search=an

Should return JSON with character data.

### Check Android Logs:
1. In Android Studio, open `Logcat` tab
2. Filter by "StarWars" or your package name
3. Look for API calls and errors

### Check Build Variants:
- `Build → Select Build Variant`
- Verify you can switch between blue and red
- Both should build successfully

## Expected API Response Example

When searching for "an", you should see characters like:
- Anakin Skywalker
- Han Solo
- Lando Calrissian
- Leia Organa
- Obi-Wan Kenobi
- etc.

Each character should have a clickable card that shows their home planet.

## Performance Benchmarks

- **Search Response**: Should complete in 1-3 seconds
- **Planet Loading**: Should complete in < 1 second
- **List Scrolling**: Should be smooth (60 FPS)
- **App Launch**: Should be < 2 seconds

## Final Verification

Before submitting, ensure:
1. ✅ App compiles and runs on Android emulator/device
2. ✅ All search scenarios work correctly
3. ✅ Pagination fetches all results
4. ✅ Planet details display correctly
5. ✅ Error handling works
6. ✅ Both product flavors work
7. ✅ No crashes or obvious bugs
8. ✅ Code is clean and well-structured

## Quick Test Script

Run through this checklist quickly:

```
□ Open Android Studio
□ Sync Gradle (wait for completion)
□ Select blueDebug variant
□ Run on emulator/device
□ Search for "an"
□ Verify results appear
□ Click on a character
□ Verify planet details show
□ Close planet details
□ Test error handling (disconnect internet)
□ Switch to redDebug variant
□ Verify red theme appears
```

If all checkboxes pass, your app is ready! 🎉

