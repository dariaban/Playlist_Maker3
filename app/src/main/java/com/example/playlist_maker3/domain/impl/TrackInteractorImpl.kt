package com.example.playlist_maker3.domain.impl


import com.example.playlist_maker3.domain.api.TrackRepository
import com.example.playlist_maker3.domain.api.TracksInteractor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TrackInteractorImpl(private val repository: TrackRepository) : TracksInteractor {
    private val executor = Executors.newCachedThreadPool()
    override fun searchTracks(expression: String, consumer: TracksInteractor.TracksConsumer) {
        executor.execute {
            try {
                consumer.consume(repository.searchTracks(expression))
            } catch (t: Throwable) {
                consumer.onFailure(t)
            }
        }

    }
}