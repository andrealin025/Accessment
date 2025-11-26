import SwiftUI
import shared

struct ContentView: View {
    @ObservedObject var viewModel: ObservableViewModel
    
    init(viewModel: StarWarsViewModel) {
        self.viewModel = ObservableViewModel(viewModel)
    }
    
    @State private var searchText = ""
    
    var body: some View {
        NavigationView {
            VStack(spacing: 16) {
                // Search Section
                HStack(spacing: 8) {
                    TextField("Search People", text: $searchText)
                        .textFieldStyle(RoundedBorderTextFieldStyle())
                    
                    Button("Search") {
                        viewModel.viewModel.searchPeople(searchTerm: searchText)
                    }
                    .disabled(viewModel.isLoading)
                }
                .padding(.horizontal)
                
                // Content
                if viewModel.isLoading {
                    Spacer()
                    ProgressView()
                    Spacer()
                } else if let error = viewModel.error {
                    Spacer()
                    VStack(spacing: 8) {
                        Text("Error: \(error)")
                            .foregroundColor(.red)
                        Button("Retry") {
                            viewModel.viewModel.searchPeople(searchTerm: searchText)
                        }
                    }
                    Spacer()
                } else if viewModel.people.isEmpty {
                    Spacer()
                    Text("Enter a search term and press Search")
                        .foregroundColor(.secondary)
                    Spacer()
                } else {
                    List(viewModel.people, id: \.name) { person in
                        Button(action: {
                            viewModel.viewModel.loadPlanet(homeworldUrl: person.homeworld)
                        }) {
                            Text(person.name)
                                .foregroundColor(.primary)
                        }
                    }
                }
            }
            .navigationTitle("Star Wars")
            .sheet(isPresented: Binding(
                get: { viewModel.showPlanetDetails },
                set: { if !$0 { viewModel.viewModel.closePlanetDetails() } }
            )) {
                PlanetDetailsView(
                    planet: viewModel.selectedPlanet,
                    isLoading: viewModel.isLoadingPlanet,
                    error: viewModel.planetError
                )
            }
        }
    }
}

struct PlanetDetailsView: View {
    let planet: Planet?
    let isLoading: Bool
    let error: String?
    
    var body: some View {
        NavigationView {
            VStack(alignment: .leading, spacing: 16) {
                if isLoading {
                    Spacer()
                    ProgressView()
                    Spacer()
                } else if let error = error {
                    Spacer()
                    Text("Error: \(error)")
                        .foregroundColor(.red)
                    Spacer()
                } else if let planet = planet {
                    VStack(alignment: .leading, spacing: 12) {
                        DetailRow(label: "Name", value: planet.name)
                        DetailRow(label: "Terrain", value: planet.terrain)
                        DetailRow(label: "Gravity", value: planet.gravity)
                        DetailRow(label: "Population", value: planet.population)
                    }
                    .padding()
                    Spacer()
                }
            }
            .navigationTitle("Planet Details")
            .navigationBarTitleDisplayMode(.inline)
        }
    }
}

struct DetailRow: View {
    let label: String
    let value: String
    
    var body: some View {
        HStack {
            Text(label + ":")
                .fontWeight(.semibold)
            Spacer()
            Text(value)
        }
    }
}

class ObservableViewModel: ObservableObject {
    @Published var people: [Person] = []
    @Published var isLoading: Bool = false
    @Published var error: String? = nil
    @Published var selectedPlanet: Planet? = nil
    @Published var isLoadingPlanet: Bool = false
    @Published var planetError: String? = nil
    @Published var showPlanetDetails: Bool = false
    
    let viewModel: StarWarsViewModel
    private var observer: IosStateFlowObserver<StarWarsUiState>? = nil
    
    init(_ viewModel: StarWarsViewModel) {
        self.viewModel = viewModel
        observeState()
    }
    
    private func observeState() {
        // Create a coroutine scope for iOS
        let scope = Kotlinx_coroutines_coreCoroutineScopeKt.CoroutineScope(
            context: Kotlinx_coroutines_coreEmptyCoroutineContext.INSTANCE
        )
        
        observer = IosStateFlowObserver(
            stateFlow: viewModel.uiState,
            scope: scope
        ) { state in
            DispatchQueue.main.async {
                self.people = state.people
                self.isLoading = state.isLoading
                self.error = state.error
                self.selectedPlanet = state.selectedPlanet
                self.isLoadingPlanet = state.isLoadingPlanet
                self.planetError = state.planetError
                self.showPlanetDetails = state.showPlanetDetails
            }
        }
        observer?.start()
    }
    
    deinit {
        observer?.stop()
    }
}

