import SwiftUI
import shared

@main
struct iosAppApp: App {
    let viewModel: StarWarsViewModel
    
    init() {
        let api = StarWarsApiImpl(client: StarWarsApiImpl.Companion.create())
        viewModel = StarWarsViewModel(api: api)
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView(viewModel: viewModel)
        }
    }
}


