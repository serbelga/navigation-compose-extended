//
//  ContentView.swift
//  sampleiOSApp
//
//  Created by Sergio Belda Galbis on 19/4/24.
//

import SwiftUI
import sample_app

struct ContentView: View {
    var body: some View {
        ComposeView().ignoresSafeArea(.all) // Compose has own keyboard handler
    }
}

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}
