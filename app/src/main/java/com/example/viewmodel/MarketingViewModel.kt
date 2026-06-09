package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.db.MarketingDatabase
import com.example.db.FavoritesRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class MarketingViewModel(application: Application) : AndroidViewModel(application) {
    private val db = MarketingDatabase.getDatabase(application)
    private val repository = FavoritesRepository(db.favoriteDao())

    // Splash screen state
    private val _isSplashActive = MutableStateFlow(true)
    val isSplashActiveState: StateFlow<Boolean> = _isSplashActive.asStateFlow()

    // Navigation Tab state (0 = Experiencia, 1 = Conocimiento)
    private val _currentTab = MutableStateFlow(0)
    val currentTab: StateFlow<Int> = _currentTab.asStateFlow()

    // MODO EXPOSICIÓN States (Screen 1 to 6)
    private val _currentExpoStep = MutableStateFlow(1)
    val currentExpoStep: StateFlow<Int> = _currentExpoStep.asStateFlow()

    // Step 1: Sí / No response decision state
    private val _selectedStep1Answer = MutableStateFlow<String?>(null)
    val selectedStep1Answer: StateFlow<String?> = _selectedStep1Answer.asStateFlow()

    // Step 4: Connecting circles active state (P1, P2, P3, P4)
    private val _activeP4 = MutableStateFlow<String?>("P1")
    val activeP4: StateFlow<String?> = _activeP4.asStateFlow()

    // Step 5: Interactive SWOT 2x2 grid active cell (F, O, D, A)
    private val _activeFodaCell = MutableStateFlow<String?>(null)
    val activeFodaCell: StateFlow<String?> = _activeFodaCell.asStateFlow()

    // Database favorite cards IDs set
    val favoriteIds: StateFlow<Set<String>> = repository.allFavorites
        .map { list -> list.map { it.cardId }.toSet() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptySet()
        )

    init {
        // Automatically hide splash after 2.2 seconds
        viewModelScope.launch {
            delay(2200)
            _isSplashActive.value = false
        }
    }

    fun setTab(tab: Int) {
        _currentTab.value = tab
    }

    fun nextExpoStep() {
        if (_currentExpoStep.value < 6) {
            _currentExpoStep.value += 1
        }
    }

    fun prevExpoStep() {
        if (_currentExpoStep.value > 1) {
            _currentExpoStep.value -= 1
        }
    }

    fun resetExpo() {
        _currentExpoStep.value = 1
        _selectedStep1Answer.value = null
        _activeFodaCell.value = null
        _activeP4.value = "P1"
    }

    fun setStep1Answer(answer: String) {
        _selectedStep1Answer.value = answer
    }

    fun setActiveP4(p: String?) {
        _activeP4.value = p
    }

    fun setActiveFodaCell(cell: String?) {
        _activeFodaCell.value = cell
    }

    fun toggleFavorite(cardId: String) {
        viewModelScope.launch {
            val currentFavs = favoriteIds.value
            if (currentFavs.contains(cardId)) {
                repository.delete(cardId)
            } else {
                repository.insert(cardId)
            }
        }
    }
}
