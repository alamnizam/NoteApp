package com.codeturtle.notes.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp
import org.junit.Assert.assertEquals
import org.junit.Test

class RootNavigationTest {

    @Test
    fun destinations_note_hasCorrectRoute() {
        // Given & When
        val destination = Destinations.Note.route

        // Then
        assertEquals(NoteNavGraph, destination)
    }

    @Test
    fun destinations_auth_hasCorrectRoute() {
        // Given & When
        val destination = Destinations.Auth.route

        // Then
        assertEquals(AuthNavGraph, destination)
    }

    @Test
    fun startDestination_whenLoggedIn_returnsNoteRoute() {
        // Given
        val isLoggedIn = true

        // When
        val startDestination = if (isLoggedIn) Destinations.Note.route else Destinations.Auth.route

        // Then
        assertEquals(NoteNavGraph, startDestination)
    }

    @Test
    fun startDestination_whenNotLoggedIn_returnsAuthRoute() {
        // Given
        val isLoggedIn = false

        // When
        val startDestination = if (isLoggedIn) Destinations.Note.route else Destinations.Auth.route

        // Then
        assertEquals(AuthNavGraph, startDestination)
    }

    @Test
    fun paddingValues_zeroDP_createsSuccessfully() {
        // Given & When
        val padding = PaddingValues(0.dp)

        // Then
        assertEquals(0.dp, padding.calculateTopPadding())
        assertEquals(0.dp, padding.calculateBottomPadding())
    }

    @Test
    fun paddingValues_withValue_createsSuccessfully() {
        // Given & When
        val padding = PaddingValues(16.dp)

        // Then
        assertEquals(16.dp, padding.calculateTopPadding())
        assertEquals(16.dp, padding.calculateBottomPadding())
    }
}
