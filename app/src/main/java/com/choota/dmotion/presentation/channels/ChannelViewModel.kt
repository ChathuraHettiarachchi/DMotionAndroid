package com.choota.dmotion.presentation.channels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choota.dmotion.domain.use_case.get_channels.GetChannelsUseCase
import com.choota.dmotion.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ChannelViewModel @Inject constructor(private val channelsUseCase: GetChannelsUseCase) :
    ViewModel() {

    // mutable state for api response
    private val _channelState = MutableStateFlow<ChannelDataState>(ChannelDataState())
    val channelState: StateFlow<ChannelDataState> = _channelState

    init {
        getChannels(1)
    }

    /**
     * get channels as user scroll through
     * @param page is the page number
     */
    private fun getChannels(page: Int = 1) {
        channelsUseCase(page).onEach {
            when (it) {
                is Resource.Error -> {
                    _channelState.value = ChannelDataState(
                        isLoading = false,
                        error = it.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _channelState.value = ChannelDataState(isLoading = true)
                }
                is Resource.Success -> {
                    _channelState.value = ChannelDataState(isLoading = false, data = it.data!!)
                }
            }
        }.launchIn(viewModelScope)
    }
}